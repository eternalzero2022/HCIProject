package com.njuse.battlerankbackend.po;

import com.njuse.battlerankbackend.enums.Category;
import com.njuse.battlerankbackend.vo.CollectionVO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity // 支出该Java类为实体类,将映射到指定数据库
public class CollectionPO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cid")
    private Integer collectionId;

    @Basic
    @Column
    private String collectionName;

    @Basic
    @Column
    @Enumerated(EnumType.ORDINAL)
    private Category category;

    @Basic
    @Column
    private String description;

    @Basic
    @Column
    private String imageUrl;

    @Basic
    @Column
    private Integer creatorId;

    @Basic
    @Column
    private Integer voteCount;

    @ElementCollection(fetch = FetchType.EAGER)  //表示使用容器作为属性（新建子表）
    @CollectionTable(name = "itemRepository", //设置生成的子表名和外键列
            joinColumns = @JoinColumn(name = "cid"))
    @Column(name = "item")
    private List<Item> items;//新建子表中嵌入Comment中的几条属性comment是embeddable的

    public CollectionVO toVO() {
        CollectionVO collectionVO = new CollectionVO();
        collectionVO.setCollectionId(this.collectionId);
        collectionVO.setCollectionName(this.collectionName);
        collectionVO.setDescription(this.description);
        collectionVO.setImageUrl(this.imageUrl);
        collectionVO.setCreatorId(this.creatorId);
        collectionVO.setCategory(this.category);
        collectionVO.setVoteCount(this.voteCount);
        collectionVO.setItems(items.stream().map(Item::toVO).collect(Collectors.toList()));
        return collectionVO;
    }
}
