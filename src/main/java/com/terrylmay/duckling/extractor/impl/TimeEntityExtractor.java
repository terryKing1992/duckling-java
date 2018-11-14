package com.terrylmay.duckling.extractor.impl;

import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.DigitalTime;
import com.terrylmay.duckling.extractor.EntityExtractor;
import com.terrylmay.duckling.regex.parser.RegexParser;
import com.terrylmay.duckling.regex.parser.impl.*;
import com.terrylmay.duckling.tokenizer.Tokenizer;
import com.terrylmay.duckling.tokenizer.impl.TimeTokenizer;
import com.terrylmay.duckling.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TimeEntityExtractor implements EntityExtractor {

    Tokenizer tokenizer;

    static List<RegexParser> regexParserChain = new ArrayList<>();

    static {
        regexParserChain.add(new YearRegexParser());
        regexParserChain.add(new MonthRegexParser());
        regexParserChain.add(new DayRegexParser());
        regexParserChain.add(new HourRegexParser());
        regexParserChain.add(new MinuteRegexParser());
        regexParserChain.add(new SecondRegexParser());
        regexParserChain.add(new PartOfDayRegexParser());
    }

    public TimeEntityExtractor() {
        this.tokenizer = new TimeTokenizer();
    }

    @Override
    public List<DigitalTime> extract(String target) {
        target = StringUtils.numberTranslator(target);// 大写数字转化
        List<String> tokens = this.tokenizer.cut(target);
        List<DigitalTime> digitalTimes = new ArrayList<>();

        Context digitalTimeContext = null;
        for (String token : tokens) {
            DigitalTime digitalTime = this.extractFromToken(token, digitalTimeContext);
            digitalTimeContext = new DigitalTimeContext(digitalTime.clone());
            digitalTimes.add(digitalTime);
        }

        return digitalTimes;
    }

    private DigitalTime extractFromToken(String token, Context contextDigitalTime) {
        DigitalTime digitalTime = new DigitalTime();
        for (RegexParser regexParser : regexParserChain) {
            digitalTime = (DigitalTime) regexParser.parse(token, digitalTime, contextDigitalTime);
        }
        return digitalTime;
    }

    public static void main(String[] args) {
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        System.out.println(timeEntityExtractor.extract("17年1月到12月"));
    }
}
