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
    @Autowired
    private UserRepository userRepository;

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
    public UserVO getUserById(Integer userId){
        User user = userRepos.findById(userId).orElse(null);
        if(user == null){
            throw SelfDefineException.userNotFound();
        }
        user.setPassword("");
        return user.toVO();
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

    @Override
    public Boolean updateUser(UserVO userVO) {
        User user = userRepos.findByUserId(userVO.getUserId());
        if(user == null){
            throw SelfDefineException.userNotFound();
        }
        if (userVO.getPassword() != null) {
            user.setPassword(userVO.getPassword());
        }
        if (userVO.getUsername() != null) {
            user.setUsername(userVO.getUsername());
        }
        if (userVO.getImageUrl() != null) {
            user.setImageUrl(userVO.getImageUrl());
        }
        if (securityUtil.getCurrentUser().getUserId().equals(user.getUserId())) {
            securityUtil.setCurrentUser(user);
        }
        userRepos.save(user);
        return true;
    }

    @Override
    public User getUserPO(Integer userId) {
        return userRepository.findById(userId).orElseThrow(SelfDefineException::userNotFound);
    }
}
