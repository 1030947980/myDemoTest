package com.example.userservicenacos.service;

import com.example.entity.user.User;
import com.example.userservicenacos.dao.UserDao;
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