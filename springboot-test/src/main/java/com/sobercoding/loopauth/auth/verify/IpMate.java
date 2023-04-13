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
    public final static VerifyFunction<String, String> equals = String::equals;

}
