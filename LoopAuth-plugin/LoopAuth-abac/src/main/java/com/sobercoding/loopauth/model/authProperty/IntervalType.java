package com.sobercoding.loopauth.model.authProperty;

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
        public boolean mate(long start, long end){
            return true;
        }
    },
    /**
     * 一天中的时间
     */
    DAY{
        @Override
        public boolean mate(long start, long end){
            return true;
        }
    },
    /**
     * 一月中的时间
     */
    MONTH{
        @Override
        public boolean mate(long start, long end){
            return true;
        }
    },
    /**
     * 一周中的时间
     */
    WEEK{
        @Override
        public boolean mate(long start, long end){
            return true;
        }
    },
    /**
     * 一年中的时间
     */
    YEAR{
        @Override
        public boolean mate(long start, long end){
            return true;
        }
    };

    public abstract boolean mate(long start, long end);

}
