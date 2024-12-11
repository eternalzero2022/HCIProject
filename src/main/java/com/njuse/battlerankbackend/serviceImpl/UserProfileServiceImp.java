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
    public Boolean addFavouriteCollection(Integer userId, Integer collectionId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile(userId);
        }
        if (hasFavouriteCollection(userId, collectionId)) {
            return false;
        }
        CollectionPO collection = collectionService.getCollectionPO(collectionId);
        userProfile.getFavoriteCollections().add(collection);
        userProfileRepository.save(userProfile);
        return true;
    }

    @Override
    public Boolean addLikeCollection(Integer userId, Integer collectionId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile(userId);
        }
        if (hasLikedCollection(userId, collectionId)) {
            return false;
        }
        userProfile.getLikedCollections().add(collectionId);
        userProfileRepository.save(userProfile);
        return true;
    }

    @Override
    public Boolean hasLikedCollection(Integer userId, Integer collectionId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile(userId);
        }
        return userProfile.getLikedCollections().contains(collectionId);
    }

    @Override
    public Boolean hasFavouriteCollection(Integer userId, Integer collectionId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile(userId);
        }
        return userProfile.getFavoriteCollections().stream().anyMatch(collection -> collection.getCollectionId().equals(collectionId));
    }

    @Override
    public Boolean unlikeCollection(Integer userId, Integer collectionId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile(userId);
        }
        if (!hasLikedCollection(userId, collectionId)) {
            return false;
        }
        userProfile.getLikedCollections().remove(collectionId);
        userProfileRepository.save(userProfile);
        return true;
    }

    @Override
    public Boolean unfavouriteCollection(Integer userId, Integer collectionId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        if (userProfile == null) {
            userProfile = new UserProfile(userId);
        }
        if (!hasFavouriteCollection(userId, collectionId)) {
            return false;
        }
        userProfile.getFavoriteCollections().removeIf(collection -> collection.getCollectionId().equals(collectionId));
        userProfileRepository.save(userProfile);
        return true;
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
