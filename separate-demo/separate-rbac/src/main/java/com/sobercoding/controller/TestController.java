package com.sobercoding.controller;

import com.sobercoding.loopauth.context.LoopAuthContext;
import com.sobercoding.loopauth.rbac.annotation.CheckPermission;
import com.sobercoding.loopauth.rbac.annotation.CheckRole;
import com.sobercoding.loopauth.rbac.carryout.LoopAuthRbac;
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


}
