package com.njuse.battlerankbackend.repository;

import com.njuse.battlerankbackend.po.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
