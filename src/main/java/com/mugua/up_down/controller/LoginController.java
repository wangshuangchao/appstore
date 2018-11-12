package com.mugua.up_down.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class LoginController {

    @RequestMapping("/toLogin")
    public String test1(){
        return "toLogin";
    }

    @PostMapping("/login")
    public String test2(String user_name, String password){
        String user_name_db = "Admin";
        String password_db = "mugua123";
        if(user_name_db.equals(user_name)){
            if(password_db.equals(password)){
                return "upIndex";
            }
        }
        return "error";
    }

}
