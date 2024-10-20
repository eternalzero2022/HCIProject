package com.njuse.battlerankbackend.vo;

import com.njuse.battlerankbackend.po.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVO {
    private Integer itemId;
    private String itemName;
    // The collection to which this item belongs
    private Integer collectionId;
    // The win rate of this item
    private Float winRate;
    // Total number of votes received for this item
    private Integer voteCount;
    // Total number of votes wom
    private Integer winCount;
    // Should be displayed
    private Boolean displayable;

    public Item toPO(){
        Item item = new Item();
        item.setItemId(this.itemId);
        item.setItemName(this.itemName);
        item.setCollectionId(this.collectionId);
        item.setWinRate(this.winRate);
        item.setVoteCount(this.voteCount);
        item.setWinCount(this.winCount);
        item.setDisplayable(this.displayable);

        return item;
    }
}
