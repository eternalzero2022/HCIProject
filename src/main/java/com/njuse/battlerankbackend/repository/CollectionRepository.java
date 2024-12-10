package com.njuse.battlerankbackend.repository;

import com.njuse.battlerankbackend.po.CollectionPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CollectionRepository extends JpaRepository<CollectionPO, Integer> {
    CollectionPO findByCollectionId(Integer CollectionId);
    CollectionPO findByCollectionNameAndCreatorId(String name, Integer createrId);

    @Query("SELECT c FROM CollectionPO c WHERE c.isPublic = true AND LOWER(c.collectionName) LIKE LOWER(CONCAT('%', :content, '%'))")
    List<CollectionPO> findByCollectionNameContainingIgnoreCase(@Param("content") String content);

    @Query("SELECT c FROM CollectionPO c WHERE c.isPublic = true")
    List<CollectionPO> findAllPublicCollections();
}
