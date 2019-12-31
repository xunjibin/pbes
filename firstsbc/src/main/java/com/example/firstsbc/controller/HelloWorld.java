package com.example.firstsbc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorld {
    @RequestMapping("/world")
    public  String  helloWord(){
        return "helloWorld";
    }





        @Value("${spring.datasource.username}")
        private String username;

        @Value("${spring.datasource.password}")
        private String password;

        @RequestMapping("/getRealInfo")
        String getRealInfo() {
            return "username: " + username + ", " + "password: " + password;
        }
    }


