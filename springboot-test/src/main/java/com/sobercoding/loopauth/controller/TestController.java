package com.sobercoding.loopauth.controller;

import com.sobercoding.loopauth.config.LoopAuthConfig;
import com.sobercoding.loopauth.face.LoopAuthFace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: LoopAuth
 * @author: Sober
 * @Description:
 * @create: 2022/07/30 23:18
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private LoopAuthConfig loopAuthConfig;

    @GetMapping("/login")
    public String register(){
        return loopAuthConfig.getSecretKey() + "/" + LoopAuthFace.login();
    }
}
