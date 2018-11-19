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
        if (digitalTime.getYear() == -1 && digitalTimeContext == null) {
            digitalTime.setYear(Calendar.getInstance().get(Calendar.YEAR));
            return;
        }

        //这边之所以能够直接返回是因为如果上下文已经有了时间, 那么必定会填充成4位的年份
        if (digitalTime.getYear() == -1 && digitalTimeContext != null) {
            digitalTime.setYear(digitalTimeContext.getContxt().getYear());
            return;
        }
    }

    @Override
    public BaseEntity parse(String token, BaseEntity baseEntity, Context context) {
        DigitalTime digitalTime = (DigitalTime) baseEntity;
        DigitalTimeContext digitalTimeContext = (DigitalTimeContext) context;
        Calendar calendar = this.getCalendarFromDigitalTime(digitalTime);

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
        this.preferFuture(digitalTime, digitalTimeContext);
        return digitalTime;
    }
}
