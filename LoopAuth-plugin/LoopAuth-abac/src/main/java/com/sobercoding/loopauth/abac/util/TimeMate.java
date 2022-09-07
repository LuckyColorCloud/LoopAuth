package com.sobercoding.loopauth.abac.util;

import com.sobercoding.loopauth.abac.model.authProperty.IntervalType;

import java.sql.Date;
import java.time.*;


/**
 * 时间匹配
 * @author Sober
 */
public class TimeMate {

    public static boolean section(long start, long end, IntervalType intervalType) {
        long time = intervalType.creation();
        if (start != 0 && end != 0){
//            if (start <  &&  < end){
//                return true;
//            }
        }

        return false;
    }
}
