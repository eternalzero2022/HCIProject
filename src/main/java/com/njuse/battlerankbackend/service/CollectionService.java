package com.njuse.battlerankbackend.service;

import com.njuse.battlerankbackend.po.ItemPO;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ItemVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CollectionService {
    Integer creatCollection(String collectionName,
                            Integer creatorId,
                            List<ItemVO> items);

    CollectionVO getCollection(Integer collectionId);

    List<ItemPO> getCollectionItems(Integer collectionId);

    List<CollectionVO> getCollectionList(String category);
}
