package com.njuse.battlerankbackend.service;

import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ItemVO;
import com.njuse.battlerankbackend.vo.VoteSession;

import java.util.List;

public interface VoteRecordService {
    Integer getVoteCount(Integer userId, Integer collectionId);

    List<ItemVO> getRankByUser(Integer userId, Integer collectionId);

    void saveVoteRecord(VoteSession voteSession);
}
