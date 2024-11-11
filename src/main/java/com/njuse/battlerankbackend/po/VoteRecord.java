package com.njuse.battlerankbackend.po;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "vote_record", indexes = {
        @Index(name = "idx_user_collection", columnList = "user_id, collection_id")
})
public class VoteRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Basic
    @Column(name = "collection_id", nullable = false)
    private Integer collectionId;

    @Basic
    @Column
    private Integer item1Id;

    @Basic
    @Column
    private Integer item2Id;

    @Basic
    @Column
    private Integer winnerId;
}
