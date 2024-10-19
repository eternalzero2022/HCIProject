package com.njuse.battlerankbackend.vo;

import com.njuse.battlerankbackend.po.Item;
import lombok.Data;

@Data
public class ItemVO {
    private Integer itemId;
    private String itemName;
    // The collection to which this item belongs
    private Integer collectionId;
    // The win rate of this item
    private Float winRate;
    // Total number of votes received for this item
    private Integer voteCount;
    // Should be displayed
    private Boolean displayable;

    public Item toPO(){
        Item item = new Item();
        item.setItemId(this.itemId);
        item.setItemName(this.itemName);
        item.setCollectionId(this.collectionId);
        item.setWinRate(this.winRate);
        item.setVoteCount(this.voteCount);
        item.setDisplayable(this.displayable);
        return item;
    }
}
