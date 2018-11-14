package com.terrylmay.duckling.constants;

public enum PartOfDayEnum {
    BREAK_OF_DAY(1, -1),
    EARLY_MORNING(8, 30), //早上
    MORNING(10), //上午
    MIDDLEDAY(12), //中午、午间
    AFTERNOON(13), //下午、午后
    NIGHT(19);//晚上、傍晚、晚、晚间


    private int hour;
    private int minute;

    PartOfDayEnum(int hour) {
        this(hour, -1);
    }

    PartOfDayEnum(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
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
}
