package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.BaseEntity;
import com.terrylmay.duckling.entity.DigitalTime;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YearRegexParser extends TimeRegexParser {

    @Override
    public DigitalTime parse(String token, BaseEntity baseEntity, Context context) {
        /**假如只有两位数来表示年份*/
        DigitalTime digitalTime = (DigitalTime) baseEntity;
        DigitalTimeContext digitalTimeContext = (DigitalTimeContext) context;
        String rule = "[0-9]{2}(?=年)";
        Pattern pattern = Pattern.compile(rule);
        Matcher match = pattern.matcher(token);
        if (match.find()) {
            digitalTime.setYear(Integer.parseInt(match.group()));
            if (digitalTime.getYear() >= 0 && digitalTime.getYear() < 100) {
                if (digitalTime.getYear() < 30) {
                    digitalTime.setYear(digitalTime.getYear() + 2000);
                } else {
                    digitalTime.setYear(digitalTime.getYear() + 1900);
                }
            }
        }
        /**不仅局限于支持1XXX年和2XXX年的识别，可识别三位数和四位数表示的年份*/
        rule = "[0-9]?[0-9]{3}(?=年)";

        pattern = Pattern.compile(rule);
        match = pattern.matcher(token);
        if (match.find()) {
            /**如果有3位数和4位数的年份，则覆盖原来2位数识别出的年份*/{
                digitalTime.setYear(Integer.parseInt(match.group()));
            }
        }

        this.preferFuture(digitalTime, digitalTimeContext);
        return digitalTime;
    }

    @Override
    public void preferFuture(DigitalTime digitalTime, DigitalTimeContext digitalTimeContext) {

        if (digitalTime.getYear() >= 0 && digitalTime.getYear() < 100) {
            if (digitalTime.getYear() < 30) {
                digitalTime.setYear(digitalTime.getYear() + 2000);
            } else {
                digitalTime.setYear(digitalTime.getYear() + 1900);
            }
        }
    }
}
