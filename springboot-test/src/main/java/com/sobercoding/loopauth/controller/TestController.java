package com.sobercoding.loopauth.controller;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.annotation.LoopAuthMode;
import com.sobercoding.loopauth.annotation.LoopAuthPermission;
import com.sobercoding.loopauth.annotation.LoopAuthRole;
import com.sobercoding.loopauth.annotation.LoopAutoCheckLogin;
import com.sobercoding.loopauth.face.LoopAuthFaceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;


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
        LoopAuthFaceImpl.logoutNow();
        return "注销成功";
    }

    @LoopAutoCheckLogin
    @GetMapping("/testLogin")
    public String testLogin(){
        return "检测成功";
    }

    @LoopAuthPermission(value="user-add",mode = LoopAuthMode.OR)
    @GetMapping("/testPermission")
    public String testPermission(){
        return "检测成功";
    }

    @LoopAuthRole(value="user",mode = LoopAuthMode.OR)
    @GetMapping("/testRole")
    public String testRole(){
        return "检测成功";
    }
}
