package com.friedfish.uncompleted_way_spellbook.content.util;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class UnsafeHelper {
    private static final Unsafe UNSAFE;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get Unsafe instance", e);
        }
    }

    /**
     * 强制修改 static final 字段的值（无视 final 修饰符）
     * @param field 目标字段（必须是 static 字段）
     * @param newValue 新值
     */
    public static void setStaticFinalField(Field field, Object newValue) {
        try {
            // 获取静态字段的内存偏移量
            Object staticBase = UNSAFE.staticFieldBase(field);
            long offset = UNSAFE.staticFieldOffset(field);
            // 直接写入内存，绕过 final 检查
            UNSAFE.putObject(staticBase, offset, newValue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set static final field: " + field.getName(), e);
        }
    }
}