package com.sobercoding.controller;

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


    /**
     * 登录测试
     */
    @RequestMapping("/login")
    public String register(){
        LoopAuthSession.login("1");
        return LoopAuthSession.getUserSession().toString();
    }

    /**
     * 强制指定用户所有会话离线
     */
    @RequestMapping("/rlogin")
    public String register1(String loginId){
        LoopAuthSession.forcedOfflineByLoginId(loginId);
        return "1";
    }

    /**
     * 强制指定token离线
     */
    @RequestMapping("/rtoken")
    public String register2(String token){
        LoopAuthSession.forcedOfflineByToken(token);
        return "1";
    }

    /**
     * 代码验证是否登录
     */
    @GetMapping("/islogin")
    public String islogin(){
        LoopAuthSession.isLogin();
        return LoopAuthSession.getUserSession().toString();
    }

    /**
     * 注解验证是否登录
     */
    @CheckLogin
    @GetMapping("/islogin1")
    public String testLogin(){
        return "登录";
    }


    @GetMapping("/out")
    public String register1(){
        LoopAuthSession.logout();
        return "登出了";
    }

}
