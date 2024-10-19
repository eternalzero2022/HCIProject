package com.njuse.battlerankbackend.service;

import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ItemVO;

import java.util.List;

public interface CollectionService {
    Integer creatCollection(String collectionName,
                            Integer creatorId,
                            List<ItemVO> items);

    CollectionVO getCollection(Integer collectionId);

    List<Item> getCollectionItems(Integer collectionId);

    List<CollectionVO> getCollectionList(String category);
}
