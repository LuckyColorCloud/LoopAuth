package com.sobercoding.loopauth.util;

import java.util.Set;

/**
 * @author Sober
 */
public class AuthUtil {

    public static boolean checkNon(Set<String> roleSet, String... roles) {
        for (String role : roles) {
            if (LoopAuthUtil.hasElement(roleSet, role)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkOr(Set<String> roleSet, String... roles) {
        for (String role : roles) {
            if (LoopAuthUtil.hasElement(roleSet, role)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkAnd(Set<String> roleSet, String... roles) {
        for (String role : roles) {
            if (!LoopAuthUtil.hasElement(roleSet, role)) {
                return false;
            }
        }
        return true;
    }
}
