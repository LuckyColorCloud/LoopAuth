package com.sobercoding.loopauth.springbootstarter.pretrain;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(LoopAuthImportSelector.class)
public @interface LoopAuthPretrain {

    /**
     * 预加载的包路径
     * @return
     */
    String[] value() default {};
}
