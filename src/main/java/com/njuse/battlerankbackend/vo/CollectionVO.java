package com.njuse.battlerankbackend.vo;

import com.njuse.battlerankbackend.enums.Category;
import com.njuse.battlerankbackend.po.CollectionPO;
import com.njuse.battlerankbackend.po.Item;
import lombok.Data;

import java.util.List;

@Data
public class CollectionVO {
    private Integer collectionId;
    private String collectionName;
    private Category category;
    // ID of the creator of the collection
    private Integer createrId;
    // List of items included in this collection
    private List<Item> items;
    public CollectionPO toPO(){
        CollectionPO collectionPO = new CollectionPO();
        collectionPO.setCollectionId(this.collectionId);
        collectionPO.setCollectionName(this.collectionName);
        collectionPO.setCategory(this.category);
        collectionPO.setCreaterId(this.createrId);
        collectionPO.setItems(items);
        return collectionPO;
    }
}
