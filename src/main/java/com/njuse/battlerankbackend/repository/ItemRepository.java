package com.njuse.battlerankbackend.repository;

import com.njuse.battlerankbackend.po.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByCollectionId(Integer collectionId);
}
