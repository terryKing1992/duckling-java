package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.BaseEntity;
import com.terrylmay.duckling.entity.DigitalTime;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonthRegexParser extends TimeRegexParser {
    @Override
    public BaseEntity parse(String token, BaseEntity baseEntity, Context context) {
        DigitalTime digitalTime = (DigitalTime) baseEntity;
        DigitalTimeContext digitalTimeContext = (DigitalTimeContext) context;
        String rule = "((10)|(11)|(12)|([1-9]))(?=月)";
        Pattern pattern = Pattern.compile(rule);
        Matcher match = pattern.matcher(token);
        if (match.find()) {
            digitalTime.setMonth(Integer.parseInt(match.group()));
        }

        preferFuture(digitalTime, digitalTimeContext);
        return digitalTime;
    }

    @Override
    public void preferFuture(DigitalTime digitalTime, DigitalTimeContext context) {
        if (digitalTime.getMonth() == -1 && context == null) {
            digitalTime.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
        }

        if (digitalTime.getMonth() == -1 && context != null) {
            digitalTime.setMonth(context.getContxt().getMonth());
        }
    }
}
