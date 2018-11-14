package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.BaseEntity;
import com.terrylmay.duckling.entity.DigitalTime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondRegexParser extends TimeRegexParser {

    @Override
    public BaseEntity parse(String token, BaseEntity baseEntity, Context context) {

        DigitalTime digitalTime = (DigitalTime) baseEntity;

        String rule = "([0-5]?[0-9](?=秒))|((?<=分)[0-5]?[0-9])";

        Pattern pattern = Pattern.compile(rule);
        Matcher match = pattern.matcher(token);
        if (match.find()) {
            digitalTime.setSecond(Integer.parseInt(match.group()));
        }
        return digitalTime;
    }

    @Override
    public void preferFuture(DigitalTime digitalTime, DigitalTimeContext digitalTimeContext) {

    }
}
