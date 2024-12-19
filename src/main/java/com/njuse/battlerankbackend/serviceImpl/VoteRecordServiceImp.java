package com.njuse.battlerankbackend.serviceImpl;

import com.njuse.battlerankbackend.exception.SelfDefineException;
import com.njuse.battlerankbackend.po.CollectionPO;
import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.po.VoteRecord;
import com.njuse.battlerankbackend.repository.VoteRecordRepository;
import com.njuse.battlerankbackend.service.ItemService;
import com.njuse.battlerankbackend.service.VoteRecordService;
import com.njuse.battlerankbackend.vo.ItemVO;
import com.njuse.battlerankbackend.vo.VoteRound;
import com.njuse.battlerankbackend.vo.VoteSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class VoteRecordServiceImp implements VoteRecordService {

    @Autowired
    private VoteRecordRepository voteRecordRepo;

    @Autowired
    private ItemService itemService;

    @Override
    public Integer getVoteCount(Integer userId, Integer collectionId) {
        return voteRecordRepo.countTotalVoteByUserIdAndCollectionId(userId, collectionId);
    }


    /**
     * Retrieves the ranking of items in a specific collection based on a user's voting records.
     *
     * @return A list of items, sorted in descending order by win count.
     */
    @Override
    public List<ItemVO> getRankByUser(Integer userId, Integer collectionId) {
        List<VoteRecord> voteRecords = voteRecordRepo.findByUserIdAndCollectionId(userId, collectionId);
        List<Item> items = itemService.getItemsByCollectionId(collectionId);

        Map<Integer, ItemVO> itemMap = new HashMap<>();
        List<ItemVO> itemVOS = new ArrayList<>();
        for (Item item : items) {
            ItemVO itemVO = item.toVO();
            itemVO.setWinRate(0f);
            itemVO.setWinCount(0);
            itemVO.setVoteCount(0);
            itemMap.put(itemVO.getItemId(), itemVO);
            itemVOS.add(itemVO);
        }

        for (VoteRecord voteRecord : voteRecords) {
            ItemVO item1 = itemMap.get(voteRecord.getItem1Id());
            ItemVO item2 = itemMap.get(voteRecord.getItem2Id());
            if (item1 == null || item2 == null) {
                throw SelfDefineException.invalidItemId();
            }
            item1.setVoteCount(item1.getVoteCount() + 1);
            item2.setVoteCount(item2.getVoteCount() + 1);
            ItemVO winner = voteRecord.getWinnerId().equals(item1.getItemId()) ? item1 : item2;
            winner.setWinCount(winner.getWinCount() + 1);

            item1.setWinRate(1f * item1.getWinCount() / item1.getVoteCount());
            item2.setWinRate(1f * item2.getWinCount() / item2.getVoteCount());
        }

        itemVOS.sort(CollectionPO::itemComparator);
        return itemVOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveVoteRecord(VoteSession voteSession) {
        Integer collectionId = voteSession.getCollectionVO().getCollectionId();
        Integer userId = voteSession.getUser().getUserId();

        for (VoteRound voteRound : voteSession.getRoundList()) {
            Integer winnerId = voteRound.getWinner();
            VoteRecord voteRecord = new VoteRecord();
            voteRecord.setUserId(userId);
            voteRecord.setCollectionId(collectionId);
            if (winnerId == null) {
                continue;
            }
            List<ItemVO> participants = voteRound.getParticipants();
            Integer participant1 = participants.get(0).getItemId();
            Integer participant2 = participants.get(1).getItemId();
            voteRecord.setItem1Id(participant1);
            voteRecord.setItem2Id(participant2);
            voteRecord.setWinnerId(winnerId);
            voteRecordRepo.save(voteRecord);
        }
    }

    @Override
    public List<VoteRecord> getVoteRecordByUser(Integer userId) {
        return voteRecordRepo.findByUserId(userId);
    }

}
