package com.njuse.battlerankbackend.service;

import com.njuse.battlerankbackend.vo.CollectionVO;

import java.util.List;

public interface UserProfileService {
    void addVotedCollection(Integer userId, Integer collectionId);

    void addFavouriteCollection(Integer userId, Integer collectionId);

    List<CollectionVO> getFavouriteCollections(Integer userId);

    List<CollectionVO> getVotedCollections(Integer userId);

    List<CollectionVO> getCreatedCollections(Integer userId);
}
