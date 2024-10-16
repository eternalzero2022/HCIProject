package com.njuse.battlerankbackend.vo;

import lombok.Data;

import java.util.List;

@Data
public class CollectionVO {
    private Integer collectionId;

    private String collectionName;

    // ID of the creator of the collection
    private Integer createrId;

    // List of items included in this collection
    private List<ItemVO> items;
}
