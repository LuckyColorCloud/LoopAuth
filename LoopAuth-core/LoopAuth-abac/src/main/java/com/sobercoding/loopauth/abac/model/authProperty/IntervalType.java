package com.sobercoding.loopauth.abac.model.authProperty;

import java.util.Calendar;

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
            //获取当前日期
            Calendar calendar = Calendar.getInstance();
            //将小时至0
            calendar.add(Calendar.DAY_OF_MONTH,0);
            //将分钟至0
            calendar.set(Calendar.MINUTE,0);
            //将秒至0
            calendar.set(Calendar.SECOND,0);

            return System.currentTimeMillis() - calendar.getTimeInMillis();
        }
    },
    /**
     * 一周中的时间
     */
    WEEK{
        @Override
        public long creation(){
            //获取当前日期
            Calendar calendar = Calendar.getInstance();
            //本月
            calendar.add(Calendar.MONTH, 0);
            //本周
            calendar.add(Calendar.WEEK_OF_MONTH, 0);
            //将小时至0
            calendar.set(Calendar.DAY_OF_WEEK,1);
            //将分钟至0
            calendar.set(Calendar.MINUTE,0);
            //将秒至0
            calendar.set(Calendar.SECOND,0);

            return System.currentTimeMillis() - calendar.getTimeInMillis();
        }
    },
    /**
     * 一月中的时间
     */
    MONTH{
        @Override
        public long creation(){
            //获取当前日期
            Calendar calendar = Calendar.getInstance();
            //n代表和本月偏移 0本月、1后一月，-1前一月
            calendar.add(Calendar.MONTH, 0);
            //设置为1号,当前日期既为本月第一天
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            //将小时至0
            calendar.set(Calendar.HOUR_OF_DAY,0);
            //将分钟至0
            calendar.set(Calendar.MINUTE,0);
            //将秒至0
            calendar.set(Calendar.SECOND,0);

            return System.currentTimeMillis() - calendar.getTimeInMillis();
        }
    },
    /**
     * 一年中的时间
     */
    YEAR{
        @Override
        public long creation(){
            //获取当前日期
            Calendar calendar = Calendar.getInstance();
            // 本年
            calendar.add(Calendar.YEAR, 0);
            // 月份至0
            calendar.set(Calendar.MONTH, 0);
            //设置为1号,当前日期既为本月第一天
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            //将小时至0
            calendar.set(Calendar.HOUR_OF_DAY,0);
            //将分钟至0
            calendar.set(Calendar.MINUTE,0);
            //将秒至0
            calendar.set(Calendar.SECOND,0);

            return System.currentTimeMillis() - calendar.getTimeInMillis();
        }
    };

    /**
     * 返回类型时间
     * @author Sober
     * @return long
     */
    public abstract long creation();

}
