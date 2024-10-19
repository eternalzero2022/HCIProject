package com.njuse.battlerankbackend.vo;

import com.njuse.battlerankbackend.po.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class UserVO {
    private Integer userId;
    private String username;
    private String phone;
    private String password;

    public User toPO(){
        User user = new User();
        user.setUserId(this.userId);
        user.setUsername(this.username);
        user.setPhone(this.phone);
        user.setPassword(this.password);

        return user;
    }
}
