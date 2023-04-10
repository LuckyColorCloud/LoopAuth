package com.sobercoding.loopauth;

import com.sobercoding.loopauth.auth.verify.IpMate;
import com.sobercoding.loopauth.function.VerifyFunction;
import com.sobercoding.loopauth.springbootstarter.pretrain.LoopAuthPretrain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;


/**
 * @author Sober
 */
@LoopAuthPretrain({"com.sobercoding.loopauth.auth.property.*", "com.sobercoding.loopauth.auth.verify.*"})
@SpringBootApplication
public class SpringbootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTestApplication.class, args);
        AtomicReference<VerifyFunction<Object, Object>> function = new AtomicReference<>();
        Arrays.stream(IpMate.class.getFields()).forEach(func -> {
            System.out.println(func.getType());
            try {
                function.set((VerifyFunction<Object, Object>) func.get(null));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        VerifyFunction<Object, Object> func = function.get();
        Long time1 = System.currentTimeMillis();
        for (long i = 9999999; i > 0; i--){
            func.mate("sadas", "sdsa");
        }
        Long time2 = System.currentTimeMillis();
        for (long i = 9999999; i > 0; i--){
            IpMate.equals("sadas", "sdsa");
        }
        Long time3 = System.currentTimeMillis();
        System.out.println(time2 - time1);
        System.out.println(time3 - time2);
    }


}
