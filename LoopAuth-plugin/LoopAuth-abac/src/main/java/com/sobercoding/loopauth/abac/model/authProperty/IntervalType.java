package com.sobercoding.loopauth.abac.model.authProperty;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 时间区间属性类型
 * @author Sober
 */
public enum IntervalType {
    /**
     * 不区分时间类型，默认日期范围内可访问
     */
    NONE{
        @Override
        public long creation(){
            return System.currentTimeMillis();
        }
    },
    /**
     * 一天中的时间
     */
    DAY{
        @Override
        public long creation(){
            return System.currentTimeMillis() % 86400000L;
        }
    },
    /**
     * 一周中的时间
     */
    WEEK{
        @Override
        public long creation(){
            return (System.currentTimeMillis() - 259200000L) % 604800000L;
        }
    },
    /**
     * 一月中的时间
     */
    MONTH{
        @Override
        public long creation(){
            return 0;
        }
    },
    /**
     * 一年中的时间
     */
    YEAR{
        @Override
        public long creation(){
            return 0;
        }
    };

    /**
     * 返回类型时间
     * @author Sober
     * @return long
     */
    public abstract long creation();

}
