package com.njuse.battlerankbackend.vo;

import com.njuse.battlerankbackend.po.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer itemId;
    private String itemName;
    private String description;
    private String imageUrl;
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
        item.setDescription(this.description);
        item.setImageUrl(this.imageUrl);
        item.setCollectionId(this.collectionId);
        item.setWinRate(this.winRate);
        item.setVoteCount(this.voteCount);
        item.setWinCount(this.winCount);
        item.setDisplayable(this.displayable);

        return item;
    }
}
