package com.sobercoding.loopauth.abac.model.authProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * 时间区间属性类 可访问的时间区间
 * @author Sober
 */
public class TimeInterval implements Serializable,Comparable<TimeInterval> {

    private static final long serialVersionUID = 1L;

    /**
     * 判断类型,true即属性规则通过则可访问，false即不通过可访问
     */
    private boolean judge;

    /**
     * 时间区间类型
     */
    private IntervalType intervalType;

    /**
     * 时间起点
     */
    private long start;

    /**
     * 时间终点
     */
    private long end;


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

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        if (this == obj) {
            return true;
        }
        TimeInterval timeInterval = (TimeInterval) obj;
        if (Objects.equals(this.judge, timeInterval.judge) &&
                Objects.equals(this.intervalType, timeInterval.intervalType) &&
                Objects.equals(this.start, timeInterval.start) &&
                Objects.equals(this.end, timeInterval.end)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(judge, intervalType, start, end);
    }
    @Override
    public int compareTo(TimeInterval obj) {
        return Integer.compare(this.hashCode() - obj.hashCode(), 0);
    }
}
