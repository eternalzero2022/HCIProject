package com.njuse.battlerankbackend.serviceImpl;

import com.njuse.battlerankbackend.exception.SelfDefineException;
import com.njuse.battlerankbackend.po.User;
import com.njuse.battlerankbackend.repository.UserRepository;
import com.njuse.battlerankbackend.service.UserService;
import com.njuse.battlerankbackend.util.SecurityUtil;
import com.njuse.battlerankbackend.vo.UserVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepos;

    @Autowired
    SecurityUtil securityUtil;

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
        securityUtil.setCurrentUser(user);
        return true;
    }

    @Override
    public UserVO getUser(){
        User user = securityUtil.getCurrentUser();
        if (user == null) {
            return null;
        }
        return user.toVO();
    }

}
