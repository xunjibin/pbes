package com.example.firstsbc.controller;


import com.example.firstsbc.utils.RedisAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/redis")
public class RedisController{
    @Autowired
    RedisAPI redisUtil;


    @RequestMapping(value = "getRedis",method = RequestMethod.GET)
    @ResponseBody
    public String getRedis(){
        redisUtil.set("20182018","helloworld", 1);
        redisUtil.expire("20182018", 60, 1);//设置key过期时间
        String res = redisUtil.get("20182018", 1);
        return  "执行成功"+res;
    }

}

