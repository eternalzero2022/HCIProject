package com.njuse.battlerankbackend.po;

import com.njuse.battlerankbackend.enums.Category;
import com.njuse.battlerankbackend.vo.ItemVO;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Embeddable
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    @Basic
    @Column
    private String itemName;

    @Basic
    @Column
    private String description;

    @Basic
    @Column
    private String imageUrl;

    @Basic
    // The collection to which this item belongs
    private Integer collectionId;

    @Basic
    @Column
    // The win rate of this item
    // winRate = winCount / voteCount
    private Float winRate;

    @Basic
    @Column
    // Total number of votes received for this item
    private Integer voteCount;

    @Basic
    @Column
    // Total number of votes wom
    private Integer winCount;

    @Basic
    @Column
    // Should be displayed
    private Boolean displayable;

    public ItemVO toVO(){
        ItemVO itemVO = new ItemVO();
        itemVO.setItemId(this.itemId);
        itemVO.setItemName(this.itemName);
        itemVO.setDescription(this.description);
        itemVO.setImageUrl(this.imageUrl);
        itemVO.setCollectionId(this.collectionId);
        itemVO.setWinRate(this.winRate);
        itemVO.setVoteCount(this.voteCount);
        itemVO.setWinCount(this.winCount);
        itemVO.setDisplayable(this.displayable);
        return itemVO;
    }
}
