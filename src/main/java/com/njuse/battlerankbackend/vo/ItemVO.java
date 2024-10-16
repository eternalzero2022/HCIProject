package com.njuse.battlerankbackend.vo;

import com.njuse.battlerankbackend.enums.Category;
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
}
