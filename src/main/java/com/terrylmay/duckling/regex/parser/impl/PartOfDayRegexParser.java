package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.constants.PartOfDayEnum;
import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.BaseEntity;
import com.terrylmay.duckling.entity.DigitalTime;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartOfDayRegexParser extends TimeRegexParser {
    @Override
    public void preferFuture(DigitalTime digitalTime, DigitalTimeContext digitalTimeContext) {

    }

    @Override
    public BaseEntity parse(String token, BaseEntity baseEntity, Context context) {
        DigitalTime digitalTime = (DigitalTime) baseEntity;
        DigitalTimeContext digitalTimeContext = (DigitalTimeContext) context;

        /*
         * 对关键字：早（包含早上/早晨/早间），上午，中午,午间,下午,午后,晚上,傍晚,晚间,晚,pm,PM的正确时间计算
         * 规约：
         * 1.下午/午后0-11点视为12-23点
         * 2.晚上/傍晚/晚间/晚1-11点视为13-23点，12点视为0点
         * 3.0-11点pm/PM视为12-23点
         *
         */
        String rule = "凌晨";
        Pattern pattern = Pattern.compile(rule);
        Matcher match = pattern.matcher(token);
        if (match.find()) {
            if (digitalTime.getHour() == -1) {
                digitalTime.setHour(PartOfDayEnum.BREAK_OF_DAY.getHour());
                digitalTime.setMinute(PartOfDayEnum.BREAK_OF_DAY.getMinute());
            }
        }

        rule = "早上|早晨|早间|晨间|今早|明早";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            if (digitalTime.getHour() == -1) {
                digitalTime.setHour(PartOfDayEnum.EARLY_MORNING.getHour());
                digitalTime.setMinute(PartOfDayEnum.EARLY_MORNING.getMinute());
            }
        }

        rule = "(?<!(\\d))上午";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            if (digitalTime.getHour() == -1) {
                digitalTime.setHour(PartOfDayEnum.MORNING.getHour());
                digitalTime.setMinute(PartOfDayEnum.MORNING.getMinute());
            }
        }

        rule = "(中午)|(午间)";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            digitalTime.setHour(PartOfDayEnum.MIDDLEDAY.getHour());
            digitalTime.setMinute(PartOfDayEnum.MIDDLEDAY.getMinute());
        }

        rule = "(下午)|(午后)|(pm)|(PM)";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            if (digitalTime.getHour() >= 0 && digitalTime.getHour() <= 11) {
                digitalTime.setHour(digitalTime.getHour() + 12);
            } else if (digitalTime.getHour() == -1) {
                digitalTime.setHour(PartOfDayEnum.AFTERNOON.getHour());
                digitalTime.setMinute(PartOfDayEnum.AFTERNOON.getMinute());
            }
        }

        rule = "晚上|夜间|夜里|今晚|明晚";
        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            digitalTime.setHour(PartOfDayEnum.NIGHT.getHour());
            digitalTime.setMinute(PartOfDayEnum.NIGHT.getMinute());
        }

        return digitalTime;
    }
}
