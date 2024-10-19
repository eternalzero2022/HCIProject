package com.njuse.battlerankbackend.vo;

import com.njuse.battlerankbackend.po.CollectionPO;
import com.njuse.battlerankbackend.po.ItemPO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class CollectionVO {
    private Integer collectionId;
    private String collectionName;
    // ID of the creator of the collection
    private Integer createrId;
    // List of items included in this collection
    private List<ItemVO> items = new ArrayList<>();
    public CollectionPO toPO(){
        CollectionPO collectionPO = new CollectionPO();
        collectionPO.setCollectionId(this.collectionId);
        collectionPO.setCollectionName(this.collectionName);
        collectionPO.setCreaterId(this.createrId);
        List<ItemPO> new_items = new ArrayList<>();
        for (int i = 0;i<items.size();i++){
            new_items.add(items.get(i).toPO());
        }
        collectionPO.setItems(new_items);
        return collectionPO;
    }
}
