package com.njuse.battlerankbackend.vo;

import com.njuse.battlerankbackend.enums.Category;
import com.njuse.battlerankbackend.po.CollectionPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionVO {
    private Integer collectionId;
    private String collectionName;
    private Category category;
    private String description;
    private String imageUrl;
    // ID of the creator of the collection
    private Integer creatorId;
    private Integer voteCount;
    // List of items included in this collection
    private List<ItemVO> items;

    public CollectionPO toPO() {
        CollectionPO collectionPO = new CollectionPO();
        collectionPO.setCollectionId(this.collectionId);
        collectionPO.setCollectionName(this.collectionName);
        collectionPO.setImageUrl(this.imageUrl);
        collectionPO.setDescription(this.description);
        collectionPO.setCategory(this.category);
        collectionPO.setCreatorId(this.creatorId);
        collectionPO.setVoteCount(this.voteCount);
        collectionPO.setItems(items.stream().map(ItemVO::toPO).collect(Collectors.toList()));
        return collectionPO;
    }
}
