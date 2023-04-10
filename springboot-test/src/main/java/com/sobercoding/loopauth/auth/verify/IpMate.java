package com.sobercoding.loopauth.auth.verify;

import com.sobercoding.loopauth.abac.annotation.VerifyPrestrain;
import com.sobercoding.loopauth.function.VerifyFunction;

import java.util.Arrays;

/**
 * ip匹配
 *
 * @author Sober
 */
@VerifyPrestrain
public class IpMate {

    /**
     * 字符串校验
     */
    public final static VerifyFunction<String, String> IP_MATE = String::equals;


    public static void main(String[] args) {
        Arrays.stream(IpMate.class.getFields()).forEach(func -> {

            VerifyFunction<Object, Object> function = null;
            try {
                function = (VerifyFunction<Object, Object>) func.get(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            System.out.println(function.mate("1","1"));
        });
    }

    public static boolean equals(String s1, String s2) {
        return s1.equals(s2);
    }
}
