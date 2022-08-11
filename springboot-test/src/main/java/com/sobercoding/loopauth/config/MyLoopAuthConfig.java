//package com.sobercoding.loopauth.config;
//
//import com.sobercoding.loopauth.LoopAuthStrategy;
//import com.sobercoding.loopauth.config.LoopAuthConfig;
//import com.sobercoding.loopauth.service.IUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import javax.annotation.Resource;
//
///**
// * @program: LoopAuth
// * @author: Sober
// * @Description:
// * @create: 2022/08/09 15:22
// */
//@Configuration
//public class MyLoopAuthConfig {
//
//    @Resource
//    private IUserService userService;
//
//    @Autowired
//    public void rewriteLoopAuthStrategy(){
//        LoopAuthStrategy.getSecretKey = userId -> userService.getById(userId).getSecretKey();
//    }
//
//}
