package com.njuse.battlerankbackend.service;

import com.njuse.battlerankbackend.po.CollectionPO;
import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ItemVO;

import java.util.List;

public interface CollectionService {
    Integer creatCollection(CollectionVO collection);

    CollectionVO getCollection(Integer collectionId);

    CollectionPO getCollectionPO(Integer collectionId);

    List<Item> getCollectionItems(Integer collectionId);

    List<CollectionVO> getCollectionList(String category, List<Integer> excludeList, Integer retNum);

    List<CollectionVO> getCollectionListPrivate(String category);

    List<CollectionVO> getCollectionHot(Integer retNum);

    List<CollectionVO> getCollectionRecommend(List<Integer> excludeList, Integer retNum);

    void increaseVoteCount(Integer collectionId, Integer voteCount);

    CollectionVO getUserRankCollection(Integer userId, Integer collectionId);

    List<CollectionVO> searchCollections(String content);

    List<CollectionVO> getCollectionsByCreator(Integer userId);

    List<CollectionVO> getUserVotedCollections(Integer userId);

    Boolean likeCollection(Integer userId, Integer collectionId);

    Boolean unlikeCollection(Integer userId, Integer collectionId);

    Boolean favoriteCollection(Integer userId, Integer collectionId);

    Boolean unfavoriteCollection(Integer userId, Integer collectionId);

    Boolean removeCollection(Integer collectionId);
}
