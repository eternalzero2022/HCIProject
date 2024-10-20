package com.njuse.battlerankbackend.service;

import com.njuse.battlerankbackend.vo.VoteRound;
import com.njuse.battlerankbackend.vo.VoteRoundResult;

public interface VoteService {


    Integer startVoteSession(Integer collectionId);

    VoteRound nextVoteRound(Integer sessionId);

    Boolean submitVoteRoundResult(VoteRoundResult voteRoundResult);

    Boolean endVoteSession(Integer sessionId);

    Boolean excludeItem(Integer itemId);

}
