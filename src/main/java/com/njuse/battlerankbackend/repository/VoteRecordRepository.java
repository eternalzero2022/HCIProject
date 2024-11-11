package com.njuse.battlerankbackend.repository;

import com.njuse.battlerankbackend.po.VoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteRecordRepository extends JpaRepository<VoteRecord, Integer> {

    List<VoteRecord> findByUserIdAndCollectionId(Integer userId, Integer collectionId);

    @Query("SELECT COUNT(v) FROM VoteRecord v " +
            "WHERE v.userId = :userId AND v.collectionId = :collectionId")
    Integer countTotalVoteByUserIdAndCollectionId(Integer userId, Integer collectionId);
}
