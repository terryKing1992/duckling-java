package com.terrylmay.duckling.extractor;

import com.terrylmay.duckling.entity.DigitalTime;
import com.terrylmay.duckling.extractor.impl.TimeEntityExtractor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Calendar;
import java.util.List;

@RunWith(JUnit4.class)
public class TimeExtractorTest {

    @Test
    public void extract_one_year_from_two_num_year_token() {
        String token = "19年我要搞AI";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimeList = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimeList.size());
        Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR) + 1, digitalTimeList.get(0).getYear());
    }

    @Test
    public void extract_one_year_from_full_express_token() {
        String token = "2019年我要去搞分布式";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimeList = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimeList.size());
        Assert.assertEquals(Calendar.getInstance().get(Calendar.YEAR) + 1, digitalTimeList.get(0).getYear());
    }

    @Test
    public void extract_one_month_from_normal_express_token() {
        String token = "我的生日在12月";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimeList = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimeList.size());
        Assert.assertEquals(12, digitalTimeList.get(0).getMonth());
    }

    @Test
    public void extract_one_day_from_normal_express_token() {
        String token = "我的生日在12月1号";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimeList = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimeList.size());
        Assert.assertEquals(12, digitalTimeList.get(0).getMonth());
        Assert.assertEquals(1, digitalTimeList.get(0).getDay());

    }

    @Test
    public void extract_one_hour_from_normal_express_token() {
        String token = "我的生日在12月1号12点";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimeList = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimeList.size());
        Assert.assertEquals(12, digitalTimeList.get(0).getMonth());
        Assert.assertEquals(1, digitalTimeList.get(0).getDay());
        Assert.assertEquals(12, digitalTimeList.get(0).getHour());
    }

    @Test
    public void extract_one_minute_from_normal_express_token() {
        String token = "我的生日在12月1号12点一刻";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimeList = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimeList.size());
        Assert.assertEquals(12, digitalTimeList.get(0).getMonth());
        Assert.assertEquals(1, digitalTimeList.get(0).getDay());
        Assert.assertEquals(12, digitalTimeList.get(0).getHour());
        Assert.assertEquals(15, digitalTimeList.get(0).getMinute());
    }

    @Test
    public void extract_one_second_from_normal_express_token() {
        String token = "我的生日在12月1号12点一刻零20秒";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimeList = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimeList.size());
        Assert.assertEquals(12, digitalTimeList.get(0).getMonth());
        Assert.assertEquals(1, digitalTimeList.get(0).getDay());
        Assert.assertEquals(12, digitalTimeList.get(0).getHour());
        Assert.assertEquals(15, digitalTimeList.get(0).getMinute());
        Assert.assertEquals(20, digitalTimeList.get(0).getSecond());
    }

    @Test
    public void extract_two_year_from_token() {
        String token = "17年到18年是个大熊市";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Assert.assertEquals(2, digitalTimes.size());
        Assert.assertEquals(2017, digitalTimes.get(0).getYear());
        Assert.assertEquals(2018, digitalTimes.get(1).getYear());
    }

    @Test
    public void extract_part_of_day_from_token() {
        String token = "晚上12点半";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimes.size());
        Assert.assertEquals(2018, digitalTimes.get(0).getYear());
        Assert.assertEquals(Calendar.getInstance().get(Calendar.DATE) + 1, digitalTimes.get(0).getDay());
    }

    @Test
    public void extract_before_year_from_token() {
        String token = "前年";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimes.size());
        Assert.assertEquals(2016, digitalTimes.get(0).getYear());
    }
}
