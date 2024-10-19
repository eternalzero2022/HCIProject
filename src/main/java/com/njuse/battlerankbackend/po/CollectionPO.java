package com.njuse.battlerankbackend.po;

import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ItemVO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private  String collectionName;

    @Basic
    @Column
    private  Integer createrId;


    //@OneToMany: 在User实体中，@OneToMany注解表示一个用户可以有多个地址。
    //mappedBy属性指定了Address实体中维护该关系的属性。
    //@ManyToOne: 在Address实体中，@ManyToOne注解表示多个地址可以关联到一个用户。
    //@JoinColumn注解用于定义外键。
    //CascadeType和orphanRemoval: 你可以通过cascade属性设置级联操作。如果需要删除用户时，自动删除用户的地址，
    // 可以使用orphanRemoval
    @OneToMany(mappedBy = "collectionId",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<ItemPO> items = new ArrayList<>();

    public CollectionVO toVO(){
        CollectionVO collectionVO = new CollectionVO();
        collectionVO.setCollectionId(this.collectionId);
        collectionVO.setCollectionName(this.collectionName);
        collectionVO.setCreaterId(this.createrId);
        List<ItemVO> new_items = new ArrayList<>();
        for (int i = 0;i<items.size();i++){
            new_items.add(items.get(i).toVO());
        }
        collectionVO.setItems(new_items);
        return collectionVO;
    }
}
