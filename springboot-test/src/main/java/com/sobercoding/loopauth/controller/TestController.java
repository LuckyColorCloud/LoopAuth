package com.sobercoding.loopauth.controller;

import com.sobercoding.loopauth.face.LoopAuthFaceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/30 23:18
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/login")
    public String register(){
        LoopAuthFaceImpl.login("1","PHONE");
        return "登录成功";
    }

    @GetMapping("/islogin")
    public String islogin(){
        if (LoopAuthFaceImpl.isLoginNow()){
            return "登录了";
        }
        return "000";
    }


    @GetMapping("/out")
    public String register1(){
        LoopAuthFaceImpl.logout();
        return "注销成功";
    }
}
