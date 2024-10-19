package com.njuse.battlerankbackend.repository;


import com.njuse.battlerankbackend.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUserId(Integer userId);
    User findByPhone(String phone);
    User findByPhoneAndPassword(String phone, String password);
}
