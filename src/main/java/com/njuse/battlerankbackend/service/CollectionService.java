package com.njuse.battlerankbackend.service;

import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ItemVO;

import java.util.List;

public interface CollectionService {
    Integer creatCollection(CollectionVO collection);

    CollectionVO getCollection(Integer collectionId);

    List<Item> getCollectionItems(Integer collectionId);

    List<CollectionVO> getCollectionList(String category, List<Integer> excludeList, Integer retNum);

    List<CollectionVO> getCollectionListPrivate(String category);

    List<CollectionVO> getCollectionHot(Integer retNum);

    List<CollectionVO> getCollectionRecommend(List<Integer> excludeList, Integer retNum);

    void increaseVoteCount(Integer collectionId, Integer voteCount);

    CollectionVO getUserRankCollection(Integer userId, Integer collectionId);

    List<CollectionVO> searchCollections(String content);
}
