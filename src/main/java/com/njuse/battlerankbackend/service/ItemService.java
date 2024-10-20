package com.njuse.battlerankbackend.service;

import com.njuse.battlerankbackend.po.Item;

public interface ItemService {
    Item getItemById(Integer id);

    Item saveItem(Item item);
}
