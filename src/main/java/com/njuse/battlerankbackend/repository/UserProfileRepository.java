package com.njuse.battlerankbackend.repository;

import com.njuse.battlerankbackend.po.CollectionPO;
import com.njuse.battlerankbackend.po.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile findByUserId(Integer userId);
}
