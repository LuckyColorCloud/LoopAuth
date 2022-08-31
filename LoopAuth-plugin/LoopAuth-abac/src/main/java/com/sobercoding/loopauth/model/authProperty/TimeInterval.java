package com.sobercoding.loopauth.model.authProperty;

/**
 * 时间区间属性类 可访问的时间区间
 * @author Sober
 */
public class TimeInterval {

    /**
     * 时间区间类型
     */
    private IntervalType intervalType = IntervalType.NONE;

    /**
     * 时间起点
     */
    private long start;

    /**
     * 时间终点
     */
    private long end;

    /**
     * 判断类型,true即属性规则通过则可访问，false即不通过可访问
     */
    private boolean judge = true;


    public IntervalType getIntervalType() {
        return intervalType;
    }

    public TimeInterval setIntervalType(IntervalType intervalType) {
        this.intervalType = intervalType;
        return this;
    }

    public long getStart() {
        return start;
    }

    public TimeInterval setStart(long start) {
        this.start = start;
        return this;
    }

    public long getEnd() {
        return end;
    }

    public TimeInterval setEnd(long end) {
        this.end = end;
        return this;
    }

    public boolean isJudge() {
        return judge;
    }

    public TimeInterval setJudge(boolean judge) {
        this.judge = judge;
        return this;
    }
}
