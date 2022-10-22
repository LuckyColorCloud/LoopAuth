package com.sobercoding.loopauth.controller;

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
        LoopAuthSession.login("1");
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
        return LoopAuthSession.getUserSession().toString();
    }

    @CheckLogin
    @GetMapping("/islogin1")
    public String testLogin(){
        return "登录";
    }


    @GetMapping("/out")
    public String register1(){
        LoopAuthSession.isLogin();
        LoopAuthSession.logout();
        return "登出了";
    }

}
