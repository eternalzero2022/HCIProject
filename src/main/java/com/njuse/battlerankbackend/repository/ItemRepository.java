package com.njuse.battlerankbackend.repository;

import com.njuse.battlerankbackend.po.ItemPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemPO, Integer> {
}
