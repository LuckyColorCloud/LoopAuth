package com.sobercoding.loopauth.controller;

import com.sobercoding.loopauth.LoopAuthStrategy;
import com.sobercoding.loopauth.face.LoopAuthFaceImpl;
import com.sobercoding.loopauth.model.UserSession;
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
    public UserSession register(){
        LoopAuthFaceImpl.login("1","PHONE");
        return LoopAuthFaceImpl.getUserSession("1");
    }

    @GetMapping("/login1")
    public UserSession register2(){
        LoopAuthFaceImpl.login("1","PC");
        return LoopAuthFaceImpl.getUserSession("1");
    }


    @GetMapping("/out")
    public UserSession register1(){
        LoopAuthFaceImpl.logout("1");
        return LoopAuthFaceImpl.getUserSession("1");
    }
}
