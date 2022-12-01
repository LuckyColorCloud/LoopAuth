package com.sobercoding.loopauth.controller;

import com.sobercoding.loopauth.abac.annotation.AbacProperty;
import com.sobercoding.loopauth.abac.annotation.CheckAbac;
import com.sobercoding.loopauth.rbac.annotation.CheckPermission;
import com.sobercoding.loopauth.rbac.annotation.CheckRole;
import com.sobercoding.loopauth.rbac.carryout.LoopAuthRbac;
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
    @GetMapping("/login")
    public String register(String loginId){
        LoopAuthSession.login(loginId);
        return LoopAuthSession.getUserSession().toString();
    }

    @CheckLogin
    @GetMapping("/islogin1")
    public String testLogin(){
        return "登录";
    }

    @GetMapping("/rlogin")
    public String register1(String loginId){
        LoopAuthSession.forcedOfflineByLoginId(loginId);
        return "1";
    }

    @GetMapping("/out")
    public String register1(){
        LoopAuthSession.logout();
        return "登出了";
    }


    @GetMapping("/rtoken")
    public String register2(String loginId){
        LoopAuthSession.forcedOfflineByToken(loginId);
        return "1";
    }

    @GetMapping("/islogin")
    public String islogin(){
        LoopAuthSession.isLogin();
        return LoopAuthSession.getUserSession().toString();
    }

    @GetMapping("/role")
    public String role(){
        LoopAuthRbac.checkByRole("user");
        return "验证";
    }

    @CheckRole("user1")
    @GetMapping("/role1")
    public String role1(){
        return "验证";
    }

    @GetMapping("/permission")
    public String permission(){
        LoopAuthRbac.checkByPermission("user-add");
        return "验证";
    }

    @CheckPermission("user1-add")
    @GetMapping("/permission1")
    public String permission1(){
        return "验证";
    }

    @GetMapping("/abac")
    public TestEntity abac(){
        TestEntity t = new TestEntity();
        t.setC("c");
        t.setB("b");
        t.setA("a");
        return t;
    }

    @CheckAbac(name = "abac测试", value = {
            @AbacProperty(name = "loginId", value = "1,2,3")
    })
    @GetMapping("/abac1")
    public String abac1(){
        return "检测成功";
    }

}
