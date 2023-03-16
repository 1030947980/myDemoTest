package com.example.userservice.service;

import com.example.entity.user.User;
import com.example.userservice.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User queryById(Long id) {
        return userDao.findById(id.intValue());
    }
}