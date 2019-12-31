package com.example.firstsbc.service;


import com.example.firstsbc.mabatis.UserMapper;
import com.example.firstsbc.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;//@Autowired可能报错，工具问题，http://www.cnblogs.com/softidea/p/5763285.html

    public User findUserByid(int id){
        return  userMapper.finduserById(id);
    }
}
