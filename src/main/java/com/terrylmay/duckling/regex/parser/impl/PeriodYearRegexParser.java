package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.BaseEntity;
import com.terrylmay.duckling.entity.DigitalTime;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeriodYearRegexParser extends TimeRegexParser {
    @Override
    public void preferFuture(DigitalTime digitalTime, DigitalTimeContext digitalTimeContext) {

    }

    @Override
    public BaseEntity parse(String token, BaseEntity baseEntity, Context context) {
        DigitalTime digitalTime = (DigitalTime) baseEntity;
        DigitalTimeContext digitalTimeContext = (DigitalTimeContext) context;
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(digitalTime.getYear(), digitalTime.getMonth() - 1, digitalTime.getDay(), 0, 0, 0);

        String rule = "前年";
        Pattern pattern = Pattern.compile(rule);
        Matcher match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.YEAR, -2);
            digitalTime.setYear(calendar.get(Calendar.YEAR));
        }

        rule = "去年|上一年";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.YEAR, -1);
            digitalTime.setYear(calendar.get(Calendar.YEAR));
        }

        rule = "今年";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            digitalTime.setYear(calendar.get(Calendar.YEAR));
        }

        rule = "明年|下一年";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.YEAR, 1);
            digitalTime.setYear(calendar.get(Calendar.YEAR));
        }

        rule = "后年";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.YEAR, 2);
            digitalTime.setYear(calendar.get(Calendar.YEAR));
        }

        return digitalTime;
    }
}
