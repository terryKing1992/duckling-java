package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.BaseEntity;
import com.terrylmay.duckling.entity.DigitalTime;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeriodMonthRegexParser extends TimeRegexParser {
    @Override
    public void preferFuture(DigitalTime digitalTime, DigitalTimeContext digitalTimeContext) {

    }

    @Override
    public BaseEntity parse(String token, BaseEntity baseEntity, Context context) {
        DigitalTime digitalTime = (DigitalTime) baseEntity;

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(digitalTime.getYear(), digitalTime.getMonth(), digitalTime.getDay(), 0, 0, 0);

        String rule = "((?<=前)\\d(?=(个)?月))";
        Pattern pattern = Pattern.compile(rule);
        Matcher match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.MONTH, Integer.parseInt(match.group()));
            digitalTime.setYear(calendar.get(Calendar.YEAR));
            digitalTime.setMonth(calendar.get(Calendar.MONTH));
        }

        rule = "上上(个)?月";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.MONTH, -2);
            digitalTime.setYear(calendar.get(Calendar.YEAR));
            digitalTime.setMonth(calendar.get(Calendar.MONTH));
        }

        rule = "上(个)?月";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.MONTH, -1);
            digitalTime.setYear(calendar.get(Calendar.YEAR));
            digitalTime.setMonth(calendar.get(Calendar.MONTH));
        }

        rule = "(本|这个|这)月";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            digitalTime.setMonth(calendar.get(Calendar.MONTH));
        }

        rule = "((?<!下)下(个)?月)";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.MONTH, 1);
            digitalTime.setYear(calendar.get(Calendar.YEAR));
            digitalTime.setMonth(calendar.get(Calendar.MONTH));
        }

        rule = "下下(个)?月";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.MONTH, 2);
            digitalTime.setYear(calendar.get(Calendar.YEAR));
            digitalTime.setMonth(calendar.get(Calendar.MONTH));
        }

        rule = "(?<=(后))\\d(?=(个)?月)";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.MONTH, Integer.parseInt(match.group()));
            digitalTime.setYear(calendar.get(Calendar.YEAR));
            digitalTime.setMonth(calendar.get(Calendar.MONTH));
        }

        return digitalTime;
    }
}
