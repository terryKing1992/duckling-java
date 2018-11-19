package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.BaseEntity;
import com.terrylmay.duckling.entity.DigitalTime;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeriodDayRegexParser extends TimeRegexParser {
    @Override
    public void preferFuture(DigitalTime digitalTime, DigitalTimeContext digitalTimeContext) {

    }

    @Override
    public BaseEntity parse(String token, BaseEntity baseEntity, Context context) {
        DigitalTime digitalTime = (DigitalTime) baseEntity;

        Calendar calendar = getCalendarFromDigitalTime(digitalTime);

        String rule = "大前天";
        Pattern pattern = Pattern.compile(rule);
        Matcher match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.DATE, -3);
            this.setDigitalTime(digitalTime, calendar);

        }

        rule = "(?<!大)前天";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.DATE, -2);
            this.setDigitalTime(digitalTime, calendar);

        }

        rule = "昨";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.DATE, -1);
            this.setDigitalTime(digitalTime, calendar);

        }

        rule = "今(?!年)";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.DATE, 0);
            this.setDigitalTime(digitalTime, calendar);

        }

        rule = "明(?!年)";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.DATE, 1);
            this.setDigitalTime(digitalTime, calendar);

        }

        rule = "(?<!大)后天";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.DATE, 2);
            this.setDigitalTime(digitalTime, calendar);

        }

        rule = "大后天";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.DATE, 3);
            this.setDigitalTime(digitalTime, calendar);
        }

        return digitalTime;
    }
}
