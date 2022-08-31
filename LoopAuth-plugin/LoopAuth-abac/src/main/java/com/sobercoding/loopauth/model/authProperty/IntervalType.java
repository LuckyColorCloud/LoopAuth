package com.sobercoding.loopauth.model.authProperty;

/**
 * 时间区间属性类型
 * @author Sober
 */
public enum IntervalType {
    /**
     * 不区分时间类型，默认日期范围内可访问
     */
    NONE,
    /**
     * 一天中的时间
     */
    DAY,
    /**
     * 一月中的时间
     */
    MONTH,
    /**
     * 一周中的时间
     */
    WEEK,
    /**
     * 一年中的时间
     */
    YEAR;
}
