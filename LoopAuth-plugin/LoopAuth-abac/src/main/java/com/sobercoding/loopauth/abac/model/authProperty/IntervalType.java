package com.sobercoding.loopauth.abac.model.authProperty;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.TimeZone;

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
            Calendar calendar = Calendar.getInstance();//获取当前日期
            calendar.add(Calendar.MONTH, 0);//n代表和本月偏移 0本月、1后一月，-1前一月
            calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
            calendar.set(Calendar.HOUR_OF_DAY,0);//将小时至0
            calendar.set(Calendar.MINUTE,0);//将分钟至0
            calendar.set(Calendar.SECOND,0);//将秒至0
            long start = calendar.getTime().getTime();
            //下方为月末的最后一天
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.SECOND,-1);
            long end = calendar.getTime().getTime();

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
