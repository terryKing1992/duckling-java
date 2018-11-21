package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.constants.PartOfDayEnum;
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
        Calendar calendar = this.getCalendarFromDigitalTime(digitalTime);
        Pattern pattern = Pattern.compile(rule);
        Matcher match = pattern.matcher(token);
        if (match.find()) {
            digitalTime.setDay(Integer.parseInt(match.group()));
        }

        rule = "[0-9]?[0-9]?[0-9]{2}-((10)|(11)|(12)|([1-9]))-((?<!\\d))([0-3][0-9]|[1-9])";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            String matchGroup = match.group();
            String[] args = matchGroup.split("-");
            digitalTime.setYear(Integer.parseInt(args[0]));
            digitalTime.setMonth(Integer.parseInt(args[1]));
            digitalTime.setDay(Integer.parseInt(args[2]));
        }

        rule = "((10)|(11)|(12)|([1-9]))/((?<!\\d))([0-3][0-9]|[1-9])/[0-9]?[0-9]?[0-9]{2}";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            String matchGroup = match.group();
            String[] args = matchGroup.split("/");
            digitalTime.setYear(Integer.parseInt(args[0]));
            digitalTime.setMonth(Integer.parseInt(args[1]));
            digitalTime.setDay(Integer.parseInt(args[2]));
        }

        /*
         * 增加了:固定形式时间表达式 年.月.日 的正确识别
         * add by 曹零
         */
        rule = "[0-9]?[0-9]?[0-9]{2}\\.((10)|(11)|(12)|([1-9]))\\.((?<!\\d))([0-3][0-9]|[1-9])";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            String matchGroup = match.group();
            String[] args = matchGroup.split("\\.");
            digitalTime.setYear(Integer.parseInt(args[0]));
            digitalTime.setMonth(Integer.parseInt(args[1]));
            digitalTime.setDay(Integer.parseInt(args[2]));
        }

        rule = "((10)|(11)|(12)|([1-9]))(月|\\.|\\-)([0-3][0-9]|[1-9])";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            String matchStr = match.group();
            Pattern p = Pattern.compile("(月|\\.|\\-)");
            Matcher m = p.matcher(matchStr);
            if (m.find()) {
                int splitIndex = m.start();
                String month = matchStr.substring(0, splitIndex);
                String date = matchStr.substring(splitIndex + 1);
                digitalTime.setMonth(Integer.parseInt(month));
                digitalTime.setDay(Integer.parseInt(date));
            }
        }


        rule = "\\d(?=(天))";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            calendar.add(Calendar.DATE, Integer.parseInt(match.group()));
            this.setDigitalTime(digitalTime, calendar);
        }

        rule = "(1)(?=(整天))";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            digitalTime.setHour(PartOfDayEnum.WORK_TIME_END.getHour());
        }

        rule = "半天";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            if (digitalTime.getHour() > 12) {
                digitalTime.setHour(PartOfDayEnum.WORK_TIME_END.getHour());
            } else {
                digitalTime.setHour(PartOfDayEnum.MIDDLEDAY.getHour());
            }
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
