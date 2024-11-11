package com.njuse.battlerankbackend.service;

import com.njuse.battlerankbackend.po.Item;

import java.util.List;

public interface ItemService {
    Item getItemById(Integer id);

    Item saveItem(Item item);

    List<Item> getItemsByCollectionId(Integer collectionId);
}
