package com.njuse.battlerankbackend.serviceImpl;

import com.njuse.battlerankbackend.po.CollectionPO;
import com.njuse.battlerankbackend.po.UserProfile;
import com.njuse.battlerankbackend.repository.UserProfileRepository;
import com.njuse.battlerankbackend.service.CollectionService;
import com.njuse.battlerankbackend.service.UserProfileService;
import com.njuse.battlerankbackend.vo.CollectionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImp implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private CollectionService collectionService;


    @Override
    public void addVotedCollection(Integer userId, Integer collectionId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile(userId);
        }
        CollectionPO collection = collectionService.getCollectionPO(collectionId);
        userProfile.getVotedCollections().add(collection);
        userProfileRepository.save(userProfile);
    }

    @Override
    public void addFavouriteCollection(Integer userId, Integer collectionId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile(userId);
        }
        CollectionPO collection = collectionService.getCollectionPO(collectionId);
        userProfile.getFavoriteCollections().add(collection);
        userProfileRepository.save(userProfile);
    }

    @Override
    public List<CollectionVO> getFavouriteCollections(Integer userId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile(userId);
            userProfileRepository.save(userProfile);
        }
        return userProfile.getFavoriteCollections()
                .stream().map(CollectionPO::toVO).toList();
    }

    @Override
    public List<CollectionVO> getVotedCollections(Integer userId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile(userId);
            userProfileRepository.save(userProfile);
        }
        return userProfile.getVotedCollections()
                .stream().map(CollectionPO::toVO).toList();
    }

    @Override
    public List<CollectionVO> getCreatedCollections(Integer userId) {
        return collectionService.getCollectionsByCreator(userId);
    }
}
