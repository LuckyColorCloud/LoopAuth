package com.sobercoding.loopauth.controller;

import com.sobercoding.loopauth.model.constant.LoopAuthVerifyMode;
import com.sobercoding.loopauth.annotation.LoopAuthPermission;
import com.sobercoding.loopauth.annotation.LoopAuthRole;
import com.sobercoding.loopauth.annotation.LoopAutoCheckLogin;
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
    @RequestMapping("/login")
    public String register(){
        LoopAuthFaceImpl.login("1");
        return LoopAuthFaceImpl.getUserSession().toString();
    }

    @RequestMapping("/rlogin")
    public String register1(String value){
        LoopAuthFaceImpl.forcedOfflineByLoginId(value);
        return "1";
    }

    @RequestMapping("/rtoken")
    public String register2(String value){
        LoopAuthFaceImpl.forcedOfflineByToken(value);
        return "1";
    }

    @GetMapping("/islogin")
    public String islogin(){
        LoopAuthFaceImpl.isLogin();
        return LoopAuthFaceImpl.getUserSessionByLoginId(LoopAuthFaceImpl.getTokenModel().getLoginId()).toString();
    }


    @GetMapping("/out")
    public String register1(){
        LoopAuthFaceImpl.isLogin();
        LoopAuthFaceImpl.logout();
        return LoopAuthFaceImpl.getUserSession().toString();
    }

    @LoopAutoCheckLogin
    @GetMapping("/testLogin")
    public String testLogin(){
        return "检测成功";
    }

    @LoopAuthPermission(value="user-add",mode = LoopAuthVerifyMode.OR)
    @GetMapping("/testPermission")
    public String testPermission(){
        return "检测成功";
    }

    @LoopAuthRole(value="user",mode = LoopAuthVerifyMode.OR)
    @GetMapping("/testRole")
    public String testRole(){
        return "检测成功";
    }
}
