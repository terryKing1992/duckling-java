package com.terrylmay.duckling.regex.parser.impl;

import com.terrylmay.duckling.context.DigitalTimeContext;
import com.terrylmay.duckling.entity.DigitalTime;
import com.terrylmay.duckling.regex.parser.RegexParser;

public abstract class TimeRegexParser implements RegexParser {
    /**
     * 如果用户选项是倾向于未来时间，检查checkTimeIndex所指的时间是否是过去的时间，如果是的话，将大一级的时间设为当前时间的+1。
     * <p>
     * 如在晚上说“早上8点看书”，则识别为明天早上;
     * 12月31日说“3号买菜”，则识别为明年1月的3号。
     *
     * @param checkTimeIndex _tp.tunit时间数组的下标
     */
    public void preferFuture(DigitalTime digitalTime, DigitalTimeContext context, int checkTimeIndex) {
    }
}
