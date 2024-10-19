package com.njuse.battlerankbackend.vo;

import com.njuse.battlerankbackend.enums.Category;
import com.njuse.battlerankbackend.po.ItemPO;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
public class ItemVO {
    private Integer itemId;
    private String itemName;
    private Category category;
    // The collection to which this item belongs
    private Integer collectionId;
    // The win rate of this item
    private Float winRate;
    // Total number of votes received for this item
    private Integer voteCount;
    // Should be displayed
    private Boolean displayable;

    public ItemPO toPO(){
        ItemPO itemPO = new ItemPO();
        itemPO.setItemId(this.itemId);
        itemPO.setItemName(this.itemName);
        itemPO.setCategory(this.category);
        itemPO.setCollectionId(this.collectionId);
        itemPO.setWinRate(this.winRate);
        itemPO.setVoteCount(this.voteCount);
        itemPO.setDisplayable(this.displayable);
        return itemPO;
    }
}
