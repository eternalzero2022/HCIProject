package com.njuse.battlerankbackend.serviceImpl;

import com.njuse.battlerankbackend.exception.SelfDefineException;
import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.repository.ItemRepository;
import com.njuse.battlerankbackend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImp implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Item getItemById(Integer id) {
        return itemRepository.findById(id).orElseThrow(SelfDefineException::invalidItemId);
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getItemsByCollectionId(Integer collectionId) {
        return itemRepository.findByCollectionId(collectionId);
    }
}
