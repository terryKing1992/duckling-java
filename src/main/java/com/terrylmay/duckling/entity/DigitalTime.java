package com.terrylmay.duckling.entity;

public class DigitalTime extends BaseEntity implements Cloneable {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    private static final int initValue = -1;

    public DigitalTime() {
        this(initValue, initValue, initValue, initValue, initValue, initValue);
    }

    public DigitalTime(DigitalTime config) {
        this(config.getYear(), config.getMonth(), config.getDay(), config.getHour(), config.getMinute(), config.getSecond());
    }

    public DigitalTime(int year, int month, int day) {
        this(year, month, day, 0, 0, 0);
    }

    public DigitalTime(int year, int month, int day, int hour, int minute, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public DigitalTime clone() {
        return new DigitalTime(this);
    }

    @Override
    public String toString() {
        return "DigitalTime{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                '}';
    }
}
