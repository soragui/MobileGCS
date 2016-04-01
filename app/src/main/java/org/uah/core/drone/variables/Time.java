package org.uah.core.drone.variables;

/**
 * Created by Gui Zhou on 2016/3/18.
 */
public class Time {


    private int hour;
    private int minute;
    private int second;

    public Time(int second) {
        this.hour = second / 3600;
        this.minute = second / 60 - hour * 60;
        this.second = second - minute * 60 - hour * 3600;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
}
