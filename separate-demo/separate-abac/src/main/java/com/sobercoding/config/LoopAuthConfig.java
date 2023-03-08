package com.sobercoding.config;

import com.sobercoding.Impl.AbacInterFaceImpl;
import com.sobercoding.loopauth.abac.AbacStrategy;
import com.sobercoding.loopauth.abac.model.AbacPoAndSu;
import com.sobercoding.loopauth.abac.model.builder.AbacPolicyFunBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sober
 */
@Configuration
public class LoopAuthConfig {


    /**
     * 手动注入bean
     */
    @Autowired(required = false)
    public void setLoopAuthConfig() {
        // 注入Abac获取规则Bean
        AbacStrategy.setAbacInterface(new AbacInterFaceImpl());
        // 初始化规则匹配方式
        // 当前用户id需要与规则匹配才可访问  否则 抛出异常
        AbacStrategy.abacPoAndSuMap = new AbacPolicyFunBuilder()
                // 自定义登录id校验的鉴权规则
                .setPolicyFun("loginId",
                        // 创建规则校验及获取当前值的方式
                        new AbacPoAndSu()
                                // 创建校验方式  value为当前值即setSupplierMap提供的值
                                // rule为规则的值即 Policy setProperty 的值
                                .setMaFunction(Object::equals)
                                // 获得value方式
                                .setSupplierMap(() -> "2")
                ).build();
    }

}
