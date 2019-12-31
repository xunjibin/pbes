package com.example.firstsbc.controller;


import com.example.firstsbc.listener.MyHttpSessionListener;
import com.example.firstsbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
/**
* @Author: pengbing 
* @Date: 2019/1/8 
*/
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/find")
    @ResponseBody
    public  String findUser(@RequestParam(defaultValue = "611") Integer id){
       String str = userService.findUserByid(id).toString();

        return  "用户"+str;
    }
    @Value("${application.message:Hello World}")
    private String message ;

    @GetMapping("/asd/{name}")
    @ResponseBody
    public String welcome(@PathVariable String name, Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", this.message);
        return "welcome";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Object foo() {

        return  "login";
    }

    @RequestMapping("/index")

    public Object index(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("zxc", "zxc");
        System.out.println("index.jsp");
        return  "index";
    }

    @RequestMapping("/online")
    @ResponseBody
    public Object online() {
        return  "当前在线人数：" + MyHttpSessionListener.online + "人";
    }
}