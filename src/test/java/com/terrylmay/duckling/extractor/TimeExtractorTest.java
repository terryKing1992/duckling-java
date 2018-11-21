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
        Assert.assertEquals(2019, digitalTimeList.get(0).getYear());
    }

    @Test
    public void extract_one_year_from_full_express_token() {
        String token = "2019年我要去搞分布式";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimeList = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimeList.size());
        Assert.assertEquals(2019, digitalTimeList.get(0).getYear());
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
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        Assert.assertEquals(1, digitalTimes.size());
        Assert.assertEquals(calendar.get(Calendar.YEAR), digitalTimes.get(0).getYear());
        Assert.assertEquals(calendar.get(Calendar.DATE) + 1, digitalTimes.get(0).getDay());
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime);
        }
    }

    @Test
    public void extract_part_of_day_hour_from_token() {
        String token = "晚上11点";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        Assert.assertEquals(1, digitalTimes.size());
        Assert.assertEquals(calendar.get(Calendar.YEAR), digitalTimes.get(0).getYear());
        Assert.assertEquals(calendar.get(Calendar.DATE), digitalTimes.get(0).getDay());
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime);
        }
    }

    @Test
    public void extract_before_two_year_from_token() {
        String token = "前年";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.YEAR, -2);
        Assert.assertEquals(1, digitalTimes.size());
        Assert.assertEquals(calendar.get(Calendar.YEAR), digitalTimes.get(0).getYear());
    }

    @Test
    public void extract_before_one_year_from_token() {
        String token = "去年";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.YEAR, -1);
        Assert.assertEquals(1, digitalTimes.size());
        Assert.assertEquals(calendar.get(Calendar.YEAR), digitalTimes.get(0).getYear());
    }

    @Test
    public void extract_current_year_from_token() {
        String token = "今年";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        Assert.assertEquals(1, digitalTimes.size());
        Assert.assertEquals(calendar.get(Calendar.YEAR), digitalTimes.get(0).getYear());
    }

    @Test
    public void extract_next_year_from_token() {
        String token = "明年";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.YEAR, 1);

        Assert.assertEquals(1, digitalTimes.size());
        Assert.assertEquals(calendar.get(Calendar.YEAR), digitalTimes.get(0).getYear());
    }

    @Test
    public void extract_next_two_year_from_token() {
        String token = "后年";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.YEAR, 2);
        Assert.assertEquals(1, digitalTimes.size());
        Assert.assertEquals(calendar.get(Calendar.YEAR), digitalTimes.get(0).getYear());
    }

    @Test
    public void extract_year_and_month_from_token() {
        String token = "明年11月份";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        Assert.assertEquals(1, digitalTimes.size());
        Assert.assertEquals(calendar.get(Calendar.YEAR) + 1, digitalTimes.get(0).getYear());
        Assert.assertEquals(11, digitalTimes.get(0).getMonth());
    }

    @Test
    public void extract_two_entity_year_and_month_from_token() {
        String token = "明年11月份到12月份";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Assert.assertEquals(2, digitalTimes.size());
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        Assert.assertEquals(calendar.get(Calendar.YEAR) + 1, digitalTimes.get(0).getYear());
        Assert.assertEquals(11, digitalTimes.get(0).getMonth());
        Assert.assertEquals(calendar.get(Calendar.YEAR) + 1, digitalTimes.get(1).getYear());
        Assert.assertEquals(12, digitalTimes.get(1).getMonth());
    }

    @Test
    public void extract_month_period_and_month_from_token() {
        String token = "下个月我要去参加朋友婚礼";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimes.size());
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.MONTH, 1);

        Assert.assertEquals(calendar.get(Calendar.YEAR), digitalTimes.get(0).getYear());
        Assert.assertEquals(calendar.get(Calendar.MONTH) + 1, digitalTimes.get(0).getMonth());
    }

    @Test
    public void extract_next_two_month_period_and_month_from_token() {
        String token = "下下个月我要去参加朋友婚礼";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimes.size());
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        calendar.add(Calendar.MONTH, 2);
        Assert.assertEquals(calendar.get(Calendar.YEAR), digitalTimes.get(0).getYear());
        Assert.assertEquals(calendar.get(Calendar.MONTH) + 1, digitalTimes.get(0).getMonth());
    }

    @Test
    public void extract_next_two_week_period_and_month_from_token() {
        String token = "下下个星期日我要去参加朋友婚礼";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimes.size());
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.WEEK_OF_MONTH, 2);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        Assert.assertEquals(calendar.get(Calendar.YEAR), digitalTimes.get(0).getYear());
        Assert.assertEquals(calendar.get(Calendar.MONTH) + 1, digitalTimes.get(0).getMonth());
        Assert.assertEquals(calendar.get(Calendar.DATE), digitalTimes.get(0).getDay());
    }

    @Test
    public void extract_next_two_day_period_and_month_from_token() {
        String token = "大后天就能休息了";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        Assert.assertEquals(1, digitalTimes.size());
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.add(Calendar.DATE, 3);
        Assert.assertEquals(calendar.get(Calendar.YEAR), digitalTimes.get(0).getYear());
        Assert.assertEquals(calendar.get(Calendar.MONTH) + 1, digitalTimes.get(0).getMonth());
        Assert.assertEquals(calendar.get(Calendar.DATE), digitalTimes.get(0).getDay());
    }

    @Test
    public void extract_next_two_day1_period_and_month_from_token() {
        String token = "明天去后天回";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test1() {
        String token = "Hi，all。下周一下午三点开会";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test2() {
        String token = "周一开会";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test3() {
        String token = "下下周一开会";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test4() {
        String token = "6:30 起床";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test5() {
        String token = "6-3 春游";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test6() {
        String token = "6月3日 春游";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test7() {
        String token = "明天早上跑步";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test8() {
        String token = "本周日到下周日出差";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test9() {
        String token = "周四下午三点到五点开会";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test10() {
        String token = "昨天上午";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test11() {
        String token = "今年";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test12() {
        String token = "今天";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test13() {
        String token = "下午2点半";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test14() {
        String token = "明天早上帮我请两个小时的假";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test15() {
        String token = "明天开始请两天的假";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test16() {
        String token = "明天请一整天的假";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test17() {
        String token = "明天请半天的假";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test18() {
        String token = "明天早上请1个小时的假";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }


    @Test
    public void extract_time_from_token_test19() {
        String token = "明天早上请两个小时的假";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test20() {
        String token = "明天早上晚到一个小时";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test21() {
        String token = "明天下午请1个小时的假";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }

    @Test
    public void extract_time_from_token_test22() {
        String token = "明天下午请1个半小时的假";
        TimeEntityExtractor timeEntityExtractor = new TimeEntityExtractor();
        List<DigitalTime> digitalTimes = timeEntityExtractor.extract(token);
        for (DigitalTime digitalTime : digitalTimes) {
            System.out.println(digitalTime.toString());
        }
    }
}
