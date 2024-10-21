package com.njuse.battlerankbackend.repository;

import com.njuse.battlerankbackend.po.CollectionPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<CollectionPO, Integer> {
    CollectionPO findByCollectionId(Integer CollectionId);
    CollectionPO findByCollectionNameAndCreatorId(String name, Integer createrId);
}
