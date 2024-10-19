package com.njuse.battlerankbackend.po;

import com.njuse.battlerankbackend.enums.Category;
import com.njuse.battlerankbackend.vo.ItemVO;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Embeddable
public class Item {
    @Basic
    @Column(name = "itemid")
    private Integer itemId;

    @Basic
    @Column
    private String itemName;

    @Basic
    // The collection to which this item belongs
    private Integer collectionId;

    @Basic
    @Column
    // The win rate of this item
    private Float winRate;

    @Basic
    @Column
    // Total number of votes received for this item
    private Integer voteCount;

    @Basic
    @Column
    // Should be displayed
    private Boolean displayable;

    public ItemVO toVO(){
        ItemVO itemVO = new ItemVO();
        itemVO.setItemId(this.itemId);
        itemVO.setItemName(this.itemName);
        itemVO.setCollectionId(this.collectionId);
        itemVO.setWinRate(this.winRate);
        itemVO.setVoteCount(this.voteCount);
        itemVO.setDisplayable(this.displayable);
        return itemVO;
    }
}
