package com.sobercoding.loopauth.abac.util;

import com.sobercoding.loopauth.abac.model.authProperty.IntervalType;

import java.sql.Date;
import java.time.*;


/**
 * 时间匹配
 * @author Sober
 */
public class TimeMate {

    /**
     * 匹配时间是否属于区间
     * @author Sober
     * @param start 开始时间
     * @param end 结束时间
     * @param intervalType 时间区间类型
     * @return boolean
     */
    public static boolean section(long start, long end, IntervalType intervalType) {
        long time = intervalType.creation();
        if (start != 0 && end != 0){
            return start < time && time < end;
        }else {
            return start == 0 ? time < end : start < time;
        }
    }
}
