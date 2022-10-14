package com.sobercoding.loopauth.controller;

import com.sobercoding.loopauth.model.LoopAuthVerifyMode;
import com.sobercoding.loopauth.rbac.annotation.CheckPermission;
import com.sobercoding.loopauth.rbac.annotation.CheckRole;
import com.sobercoding.loopauth.session.annotation.CheckLogin;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
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
        LoopAuthSession.login("2");
        return LoopAuthSession.getUserSession().toString();
    }

    @RequestMapping("/rlogin")
    public String register1(String value){
        LoopAuthSession.forcedOfflineByLoginId(value);
        return "1";
    }

    @RequestMapping("/rtoken")
    public String register2(String value){
        LoopAuthSession.forcedOfflineByToken(value);
        return "1";
    }

    @GetMapping("/islogin")
    public String islogin(){
        LoopAuthSession.isLogin();
        return LoopAuthSession.getUserSessionByLoginId(LoopAuthSession.getTokenModel().getLoginId()).toString();
    }


    @GetMapping("/out")
    public String register1(){
        LoopAuthSession.isLogin();
        LoopAuthSession.logout();
        return "登出了";
    }

    @CheckLogin
    @GetMapping("/testLogin")
    public String testLogin(){
        return "检测成功";
    }

    @CheckPermission(value="user-add", mode = LoopAuthVerifyMode.OR)
    @GetMapping("/testPermission")
    public String testPermission(){
        return "检测成功";
    }

    @CheckRole(value="user",mode = LoopAuthVerifyMode.OR)
    @GetMapping("/testRole")
    public String testRole(){
        return "检测成功";
    }

    @GetMapping("/abac1")
    public String abac1(){
        return "检测成功";
    }
}
