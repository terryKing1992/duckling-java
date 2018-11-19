package com.terrylmay.duckling.regex.parser.impl;

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
            digitalTime.setHour(Integer.parseInt(match.group()));
            preferFuture(digitalTime, digitalTimeContext);
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

        return digitalTime;
    }

    @Override
    public void preferFuture(DigitalTime digitalTime, DigitalTimeContext digitalTimeContext) {
        if (digitalTime.getHour() == -1 && digitalTimeContext == null) {
            digitalTime.setHour(Calendar.getInstance().get(Calendar.HOUR));
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
