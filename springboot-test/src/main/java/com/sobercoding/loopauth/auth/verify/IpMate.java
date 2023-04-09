package com.sobercoding.loopauth.auth.verify;

import com.sobercoding.loopauth.abac.annotation.VerifyPrestrain;

/**
 * ip匹配
 *
 * @author Sober
 */
@VerifyPrestrain
public class IpMate {

    /**
     * IP 匹配规则
     */
    public static boolean equals(String ip1, String ip2) {
        return ip1.equals(ip2);
    }

    public static String equals1(String ip1, String ip2) {
        return "2";
    }

}
