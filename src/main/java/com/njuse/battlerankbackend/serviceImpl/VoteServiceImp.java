package com.njuse.battlerankbackend.serviceImpl;

import com.njuse.battlerankbackend.exception.SelfDefineException;
import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.po.User;
import com.njuse.battlerankbackend.service.*;
import com.njuse.battlerankbackend.serviceImpl.selectionStrategy.RandomOptimizedSelectionStrategy;
import com.njuse.battlerankbackend.serviceImpl.selectionStrategy.RandomSelectionStrategy;
import com.njuse.battlerankbackend.serviceImpl.selectionStrategy.SelectionStrategy;
import com.njuse.battlerankbackend.util.AsyncUtil;
import com.njuse.battlerankbackend.util.SecurityUtil;
import com.njuse.battlerankbackend.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class VoteServiceImp implements VoteService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private ItemService itemService;

    @Autowired
    private AsyncUtil asyncUtil;

    @Autowired
    private VoteRecordService voteRecordService;

    @Autowired
    private UserProfileService userProfileService;

    /**
     * Starts a new voting session for a specified collection.
     *
     * @param collectionId the ID of the collection for which the vote session is being started
     * @return the ID of the newly created voting session
     */
    @Override
    public Integer startVoteSession(Integer collectionId) {


        VoteSession voteSession = new VoteSession();

        Integer sessionId = getNewVoteSessionId();
        voteSession.setSessionId(sessionId);

        User user = securityUtil.getCurrentUser();
        voteSession.setUser(user);
        userProfileService.addVotedCollection(user.getUserId(), collectionId);


        CollectionVO collection = collectionService.getCollection(collectionId);
        voteSession.setCollectionVO(collection);
        voteSession.setCurrentRoundId(0);
        voteSession.setWaitForSubmit(new AtomicInteger(0));
        voteSession.setRoundList(new ArrayList<>());

//        voteSession.setSelectionStrategy(new RandomSelectionStrategy(voteSession));
        voteSession.setSelectionStrategy(new RandomOptimizedSelectionStrategy(voteSession));
        saveVoteSession(voteSession);

        return sessionId;
    }

    /**
     * Advances to the next voting round in the specified voting session.
     *
     * @param sessionId the ID of the voting session
     * @return the details of the next voting round
     */
    @Override
    public VoteRound nextVoteRound(Integer sessionId) {
        VoteSession voteSession = getVoteSession(sessionId);

        VoteRound voteRound = new VoteRound();
        voteRound.setRoundId(voteSession.getCurrentRoundId() + 1);
        voteSession.setCurrentRoundId(voteRound.getRoundId());

        SelectionStrategy selectionStrategy = voteSession.getSelectionStrategy();

        List<ItemVO> participants = selectionStrategy.selectNextTwoItems();

        voteRound.setParticipants(participants);
        if (!participants.isEmpty()) {
            voteSession.getRoundList().add(voteRound);
            voteSession.getWaitForSubmit().addAndGet(1);
        } else {
            if (voteSession.getSelectionStrategy().isFinished()) {
                asyncUtil.executeTaskAsync(() -> {
                    endVoteSessionInner(voteSession);
                });
            }
        }
        saveVoteSession(voteSession);

        return voteRound;
    }

    /**
     * Submits the result of a voting round, indicating the winning item.
     *
     * @param voteRoundResult the result of the voting round, including the winner's ID
     * @return true if the submission is successful
     * @throws SelfDefineException if the provided round ID or winner ID is invalid
     */
    @Override
    public Boolean submitVoteRoundResult(VoteRoundResult voteRoundResult) {
        VoteSession voteSession = getVoteSession(voteRoundResult.getSessionId());
        List<VoteRound> roundList = voteSession.getRoundList();
        if (roundList.size() < voteRoundResult.getRoundId()) {
            throw SelfDefineException.invalidRoundId();
        }

        Integer winnerId = voteRoundResult.getWinnerId();
        VoteRound voteRound = roundList.get(voteRoundResult.getRoundId() - 1);
        List<ItemVO> participants = voteRound.getParticipants();
        if (!winnerId.equals(participants.get(0).getItemId()) &&
                !winnerId.equals(participants.get(1).getItemId())) {
            throw SelfDefineException.invalidWinnerId();
        }
        voteSession.getWaitForSubmit().addAndGet(-1);
        voteRound.setWinner(winnerId);
        saveVoteSession(voteSession);
        return true;
    }

    /**
     * Ends the specified voting session and updates the results.
     *
     * @param sessionId the ID of the voting session to end
     * @return true if the session is successfully ended
     */
    @Override
    public Boolean endVoteSession(Integer sessionId) {
        return endVoteSessionInner(getVoteSession(sessionId));
    }

    /**
     * Ends the voting session and updates the win/loss statistics for the participating items.
     * This method is annotated with @Transactional to ensure database consistency.
     *
     * @param voteSession the vote session to end
     * @return true if the session is successfully ended
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Boolean endVoteSessionInner(VoteSession voteSession) {
        if (voteSession == null) {
            return false;
        }

        // Can use cv to improve this
        if (voteSession.getWaitForSubmit().get() > 1) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (voteSession.getWaitForSubmit().getAndSet(-1) == -1) {
            return false;
        }

        int roundCnt = 0;
        for (VoteRound voteRound : voteSession.getRoundList()) {
            Integer winnerId = voteRound.getWinner();
            if (winnerId == null) {
                continue;
            }
            List<ItemVO> participants = voteRound.getParticipants();
            Item participant1 = itemService.getItemById(participants.get(0).getItemId());
            Item participant2 = itemService.getItemById(participants.get(1).getItemId());
            participant1.setVoteCount(participant1.getVoteCount() + 1);
            participant2.setVoteCount(participant2.getVoteCount() + 1);
            roundCnt++;

            if (winnerId.equals(participant1.getItemId())) {
                participant1.setWinCount(participant1.getWinCount() + 1);
            } else if (winnerId.equals(participant2.getItemId())) {
                participant2.setWinCount(participant2.getWinCount() + 1);
            }

            participant1.setWinRate(1.0f * participant1.getWinCount() / participant1.getVoteCount());
            participant2.setWinRate(1.0f * participant2.getWinCount() / participant2.getVoteCount());
            itemService.saveItem(participant1);
            itemService.saveItem(participant2);
        }
        Integer collectionId = voteSession.getCollectionVO().getCollectionId();
        collectionService.increaseVoteCount(collectionId, roundCnt);
        voteRecordService.saveVoteRecord(voteSession);
//        removeVoteSession(voteSession.getSessionId());
        return true;
    }

    /**
     * Excludes the specified item from the voting session.
     *
     * @param itemId the ID of the item to exclude
     * @return true, if success
     */
    @Override
    public Boolean excludeItem(Integer sessionId, Integer itemId) {
        VoteSession voteSession = getVoteSession(sessionId);
        voteSession.getSelectionStrategy().excludeItem(itemId);
        List<VoteRound> roundList = voteSession.getRoundList();
        VoteRound lastRound = roundList.get(roundList.size() - 1);
        if (!lastRound.getParticipants().get(0).getItemId().equals(itemId) &&
            !lastRound.getParticipants().get(1).getItemId().equals(itemId)) {
            return false;
        }
        roundList.remove(roundList.size() - 1);
        voteSession.setCurrentRoundId(voteSession.getCurrentRoundId() - 1);
        saveVoteSession(voteSession);
        return true;
    }


    /**
     * Generates a new unique vote session ID.
     *
     * @return the new vote session ID
     */
    private Integer getNewVoteSessionId() {
        Integer voteSessionId = (Integer) httpServletRequest.getSession().getAttribute("voteSessionId");
        if (voteSessionId == null) {
            voteSessionId = 1;
        }
        httpServletRequest.getSession().setAttribute("voteSessionId", voteSessionId + 1);
        return voteSessionId;
    }

    /**
     * Saves the given voting session in the HTTP session.
     *
     * @param voteSession the voting session to save
     */
    private void saveVoteSession(VoteSession voteSession) {
        Map<Integer, VoteSession> sessionMap = (Map<Integer, VoteSession>) httpServletRequest.getSession().getAttribute("sessions");
        if (sessionMap == null) {
            sessionMap = new HashMap<>();
        }
        sessionMap.put(voteSession.getSessionId(), voteSession);
        httpServletRequest.getSession().setAttribute("sessions", sessionMap);
    }

    /**
     * Retrieves the voting session corresponding to the specified session ID.
     *
     * @param sessionId the ID of the voting session to retrieve
     * @return the corresponding voting session
     */
    private VoteSession getVoteSession(Integer sessionId) {
        Map<Integer, VoteSession> sessionMap = (Map<Integer, VoteSession>) httpServletRequest.getSession().getAttribute("sessions");
        if (sessionMap == null) {
            throw SelfDefineException.invalidSessionId();
        }
        VoteSession voteSession = sessionMap.get(sessionId);
//        if (voteSession == null) {
//            throw SelfDefineException.invalidSessionId();
//        }
        return voteSession;
    }

//    private void removeVoteSession(Integer sessionId) {
//        Map<Integer, VoteSession> sessionMap = (Map<Integer, VoteSession>) httpServletRequest.getSession().getAttribute("sessions");
//        if (sessionMap == null) {
//            return;
//        }
//        sessionMap.remove(sessionId);
//    }
}
