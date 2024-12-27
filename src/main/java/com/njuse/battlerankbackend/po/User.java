package com.njuse.battlerankbackend.po;


import com.njuse.battlerankbackend.enums.RoleEnum;
import com.njuse.battlerankbackend.vo.UserVO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity // 支出该Java类为实体类,将映射到指定数据库
@Table(name = "\"user\"")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id // key
    @Column(name = "uid")
    private Integer userId;

    @Basic
    @Column
    private String username;

    @Basic
    @Column
    private String phone;

    @Basic
    @Column
    private String password;

    @Basic
    @Column(length = 511)
    private String imageUrl;

    @Basic
    @Column
    private RoleEnum role;

    public UserVO toVO(){
        UserVO userVO = new UserVO();
        userVO.setUserId(this.userId);
        userVO.setUsername(this.username);
        userVO.setPhone(this.phone);
        userVO.setPassword(this.password);
        userVO.setImageUrl(this.imageUrl);
        userVO.setRole(this.role);
        return userVO;
    }
}
