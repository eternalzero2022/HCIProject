package com.njuse.battlerankbackend.po;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserProfile {

    @Id
    @Column
    private Integer userId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "profile_favor_collection",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "cid", foreignKey = @ForeignKey(name = "fk_fv_collection"))
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CollectionPO> favoriteCollections;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "profile_voted_collection",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "cid", foreignKey = @ForeignKey(name = "fk_vt_collection"))
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CollectionPO> votedCollections;

    // Deprecated
    @ElementCollection
    private Set<Integer> likedCollections;

    public UserProfile(Integer userId) {
        this.userId = userId;
        this.favoriteCollections = new ArrayList<>();
        this.votedCollections = new ArrayList<>();
        this.likedCollections = new HashSet<>();
    }

}
