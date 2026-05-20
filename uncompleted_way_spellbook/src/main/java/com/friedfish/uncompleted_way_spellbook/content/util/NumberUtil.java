package com.friedfish.uncompleted_way_spellbook.content.util;

public class NumberUtil {

    public static String getPercentString(float value, int decimalPlaces) {
        // 将小数转换为百分数（乘以100），并使用格式化字符串保留指定位数
        String format = "%." + decimalPlaces + "f%%";
        return String.format(format, value * 100);
    }

    public static String getPercentString(float value) {
        return getPercentString(value,0);
    }
}
