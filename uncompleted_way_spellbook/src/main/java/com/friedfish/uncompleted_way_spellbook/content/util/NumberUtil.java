package com.friedfish.uncompleted_way_spellbook.content.util;

public class NumberUtil {
    public static String getPercentString(float value) {
        return String.format("%.0f%%", value * 100);
    }
}
