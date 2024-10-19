package com.njuse.battlerankbackend.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
