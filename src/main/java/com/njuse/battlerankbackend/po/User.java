package com.njuse.battlerankbackend.po;


import com.njuse.battlerankbackend.vo.UserVO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity // 支出该Java类为实体类,将映射到指定数据库
@Table(name = "\"user\"")
public class User {
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

    public UserVO toVO(){
        UserVO userVO = new UserVO();
        userVO.setUserId(this.userId);
        userVO.setUsername(this.username);
        userVO.setPhone(this.phone);
        userVO.setPassword(this.password);
        userVO.setImageUrl(this.imageUrl);
        return userVO;
    }
}
