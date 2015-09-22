package service;

/**
 * Created by andrew on 9/22/15.
 */
public class GenConfig {
    long endTime;
    long interval;

    public GenConfig(long endTime, long interval) {
        this.endTime = endTime;
        this.interval = interval;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
