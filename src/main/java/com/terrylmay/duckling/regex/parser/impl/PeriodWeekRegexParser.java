package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.BaseEntity;
import com.terrylmay.duckling.entity.DigitalTime;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeriodWeekRegexParser extends TimeRegexParser {
    @Override
    public void preferFuture(DigitalTime digitalTime, DigitalTimeContext digitalTimeContext) {

    }

    @Override
    public BaseEntity parse(String token, BaseEntity baseEntity, Context context) {
        DigitalTime digitalTime = (DigitalTime) baseEntity;

        Calendar calendar = this.getCalendarFromDigitalTime(digitalTime);

        String rule = "(?<=(上上(周|星期)))[1-7]?";
        Pattern pattern = Pattern.compile(rule);
        Matcher match = pattern.matcher(token);
        if (match.find()) {
            int week;
            try {
                week = Integer.parseInt(match.group());
            } catch (NumberFormatException e) {
                week = 1;
            }
            week = (week == 7 ? 1 : ++week);
            calendar.add(Calendar.WEEK_OF_MONTH, -2);
            calendar.set(Calendar.DAY_OF_WEEK, week);
            this.setDigitalTime(digitalTime, calendar);

        }

        rule = "(?<=((?<!上)上(周|星期)))[1-7]?";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            int week;
            try {
                week = Integer.parseInt(match.group());
            } catch (NumberFormatException e) {
                week = 1;
            }
            week = (week == 7 ? 1 : ++week);

            calendar.add(Calendar.WEEK_OF_MONTH, -1);
            calendar.set(Calendar.DAY_OF_WEEK, week);
            this.setDigitalTime(digitalTime, calendar);

        }

        rule = "(?<=((?<!下)下(周|星期)))[1-7]?";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            int week;
            try {
                week = Integer.parseInt(match.group());
            } catch (NumberFormatException e) {
                week = 1;
            }
            week = (week == 7 ? 1 : ++week);

            calendar.add(Calendar.WEEK_OF_MONTH, 1);
            calendar.set(Calendar.DAY_OF_WEEK, week);
            this.setDigitalTime(digitalTime, calendar);
        }

        rule = "(?<=(下下(个)?(周|星期)))[1-7]?";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            int week;
            try {
                week = Integer.parseInt(match.group());
            } catch (NumberFormatException e) {
                week = 1;
            }
            week = (week == 7 ? 1 : ++week);

            calendar.add(Calendar.WEEK_OF_MONTH, 2);
            calendar.set(Calendar.DAY_OF_WEEK, week);
            this.setDigitalTime(digitalTime, calendar);

        }

        rule = "(?<=((?<!(上|下))(周|星期)))[1-7]?";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find() && !"".equals(match.group())) {
            int week;
            try {
                week = Integer.parseInt(match.group());
            } catch (NumberFormatException e) {
                week = 1;
            }
            week = (week == 7 ? 1 : ++week);
            if (calendar.get(Calendar.DAY_OF_WEEK) > week) {
                calendar.add(Calendar.WEEK_OF_MONTH, 1);
            } else {
                calendar.add(Calendar.WEEK_OF_MONTH, 0);
            }
            calendar.set(Calendar.DAY_OF_WEEK, week);
            this.setDigitalTime(digitalTime, calendar);
        }

        return digitalTime;
    }
}
