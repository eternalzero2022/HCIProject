package com.njuse.battlerankbackend.serviceImpl;

import com.njuse.battlerankbackend.exception.SelfDefineException;
import com.njuse.battlerankbackend.po.User;
import com.njuse.battlerankbackend.repository.UserRepository;
import com.njuse.battlerankbackend.service.UserService;
import com.njuse.battlerankbackend.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepos;

    @Override
    public Boolean register(UserVO userVO){
        User user = userRepos.findByPhone(userVO.getPhone());
        if(user != null){
            throw SelfDefineException.phoneAlreadyExits();
        }
        User newUser = userVO.toPO();
        userRepos.save(newUser);
        return true;
    }


    @Override
    public Boolean login(UserVO userVO){
        User user;
        if(userRepos.findByPhone(userVO.getPhone()) == null){
            throw SelfDefineException.loginFaultPhone();
        }else{
            user = userRepos.findByPhoneAndPassword(userVO.getPhone(), userVO.getPassword());
        }
        if(user == null){
            throw SelfDefineException.loginFaultPassword();
        }
        return true;
    }

    @Override
    public UserVO getUser(HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        return user.toVO();
    }

}
