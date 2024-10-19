package com.njuse.battlerankbackend.po;

import com.njuse.battlerankbackend.enums.Category;
import com.njuse.battlerankbackend.vo.ItemVO;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ItemPO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id // key
    @Column(name = "itemid")
    private Integer itemId;

    @Basic
    @Column
    private String itemName;

    @Basic
    @Column
    private Category category;

    @Basic
    @Column
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
        itemVO.setCategory(this.category);
        itemVO.setCollectionId(this.collectionId);
        itemVO.setWinRate(this.winRate);
        itemVO.setVoteCount(this.voteCount);
        itemVO.setDisplayable(this.displayable);
        return itemVO;
    }
}
