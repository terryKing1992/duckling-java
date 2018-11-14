package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.BaseEntity;
import com.terrylmay.duckling.entity.DigitalTime;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayRegexParser extends TimeRegexParser {

    @Override
    public BaseEntity parse(String token, BaseEntity baseEntity, Context context) {
        String rule = "((?<!\\d))([0-3][0-9]|[1-9])(?=(日|号))";

        DigitalTime digitalTime = (DigitalTime) baseEntity;
        DigitalTimeContext digitalTimeContext = (DigitalTimeContext) context;

        Pattern pattern = Pattern.compile(rule);
        Matcher match = pattern.matcher(token);
        if (match.find()) {
            digitalTime.setDay(Integer.parseInt(match.group()));
        }
        this.preferFuture(digitalTime, digitalTimeContext);

        return digitalTime;
    }

    @Override
    public void preferFuture(DigitalTime digitalTime, DigitalTimeContext digitalTimeContext) {
        if (digitalTime.getDay() == -1 && digitalTimeContext == null) {
            digitalTime.setDay(Calendar.getInstance().get(Calendar.DATE));
        }

        if (digitalTime.getDay() == -1 && digitalTimeContext != null) {
            digitalTime.setDay(digitalTimeContext.getContxt().getDay());
        }
    }
}
