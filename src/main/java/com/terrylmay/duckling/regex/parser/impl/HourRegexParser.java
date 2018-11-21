package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.constants.PartOfDayEnum;
import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.BaseEntity;
import com.terrylmay.duckling.entity.DigitalTime;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HourRegexParser extends TimeRegexParser {

    @Override
    public BaseEntity parse(String token, BaseEntity baseEntity, Context context) {
        DigitalTime digitalTime = (DigitalTime) baseEntity;
        DigitalTimeContext digitalTimeContext = (DigitalTimeContext) context;

        String rule = "(?<!(周|星期))([0-2]?[0-9])(?=(点|时))";

        Pattern pattern = Pattern.compile(rule);
        Matcher match = pattern.matcher(token);
        if (match.find()) {
            int hourTime = Integer.parseInt(match.group());
            if (digitalTime.getHour() > 12 && hourTime >= 12) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, digitalTime.getYear());
                calendar.set(Calendar.MONTH, digitalTime.getMonth());
                calendar.set(Calendar.DATE, digitalTime.getDay() + 1);

                digitalTime.setYear(calendar.get(Calendar.YEAR));
                digitalTime.setMonth(calendar.get(Calendar.MONTH) + 1);
                digitalTime.setDay(calendar.get(Calendar.DATE));
                digitalTime.setHour(0);
            } else if (digitalTime.getHour() > 12 && hourTime < 12) {
                digitalTime.setHour(12 + hourTime);
            } else {
                digitalTime.setHour(hourTime);
            }
        }

        rule = "(?<!(周|星期))([0-2]?[0-9]):[0-5]?[0-9]:[0-5]?[0-9]";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            String matchGroup = match.group();
            String[] args = matchGroup.split(":");
            digitalTime.setHour(Integer.parseInt(args[0]));
            digitalTime.setMinute(Integer.parseInt(args[1]));
            digitalTime.setSecond(Integer.parseInt(args[2]));
        } else {
            rule = "(?<!(周|星期))([0-2]?[0-9]):[0-5]?[0-9]";
            pattern = Pattern.compile(rule);
            match = pattern.matcher(token);
            if (match.find()) {
                String matchGroup = match.group();
                String[] args = matchGroup.split(":");
                digitalTime.setHour(Integer.parseInt(args[0]));
                digitalTime.setMinute(Integer.parseInt(args[1]));
            }
        }

        this.preferFuture(digitalTime, digitalTimeContext);

        rule = "(半)(?=(个?)(小时))";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            if (digitalTime.getHour() > 12) {
                digitalTime.setHour(14);
                digitalTime.setMinute(30);
            } else {
                digitalTime.setHour(9);
                digitalTime.setMinute(0);
            }
        }

        rule = "\\d(?=(个?)(半)(小时))";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            if (digitalTime.getHour() > 12) {
                digitalTime.setHour(14 + Integer.parseInt(match.group()));
                digitalTime.setMinute(30);
            } else {
                digitalTime.setHour(9 + Integer.parseInt(match.group()));
                digitalTime.setMinute(0);
            }
        }

        rule = "(\\d)(?=(个?)(小时))";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            if (digitalTime.getHour() > 12) {
                digitalTime.setHour(Integer.parseInt(match.group()) + 14);
            } else {
                digitalTime.setHour(Integer.parseInt(match.group()) + 8);
                digitalTime.setMinute(30);
            }
        }
        return digitalTime;
    }

    @Override
    public void preferFuture(DigitalTime digitalTime, DigitalTimeContext digitalTimeContext) {
        if (digitalTime.getHour() == -1 && digitalTimeContext == null) {
            digitalTime.setHour(PartOfDayEnum.EARLY_MORNING.getHour());
        }

        if (digitalTime.getHour() == -1 && digitalTimeContext != null) {
            digitalTime.setHour(digitalTimeContext.getContxt().getHour());
        }

        if (digitalTimeContext != null
                && digitalTime.getYear() == digitalTimeContext.getContxt().getYear()
                && digitalTime.getMonth() == digitalTimeContext.getContxt().getMonth()
                && digitalTime.getDay() == digitalTimeContext.getContxt().getDay()) {
            if (digitalTime.getHour() < digitalTimeContext.getContxt().getHour()) {
                digitalTime.setHour(digitalTime.getHour() + 12);
            }
        }
    }
}
