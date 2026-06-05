package com.friedfish.uncompleted_way_spellbook.mixin;

import com.friedfish.uncompleted_way_spellbook.content.util.UnsafeHelper;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import org.spongepowered.asm.mixin.*;

import java.lang.reflect.Field;
import java.util.Arrays;

@Mixin(SpellRarity.class)
public abstract class SpellRarityMixin {
    /*
    static {
        try {
            // 1. 创建一个新的枚举实例
            //    注意：构造函数参数顺序为 (String enumName, int ordinal, 你的业务参数)
            //    假设原始业务参数是 (String id, int cost, int value)，你需要填写正确的业务值
            SpellRarity newRarity = SpellRarityInvoker.invokeNew(
                    "MYTHIC",
                    5,
                    5
            );

            // 2. 获取原有的 $VALUES 数组
            Field valuesField = SpellRarity.class.getDeclaredField("$VALUES");
            valuesField.setAccessible(true);
            SpellRarity[] oldValues = (SpellRarity[]) valuesField.get(null);

            // 3. 创建新数组，追加新元素
            SpellRarity[] newValues = Arrays.copyOf(oldValues, oldValues.length + 1);
            newValues[oldValues.length] = newRarity;

            // 4. 使用 Unsafe 强行写回（绕过 final）
            UnsafeHelper.setStaticFinalField(valuesField, newValues);

            // 5. （可选）如果枚举中还有其他缓存（如由 VALUES 派生出的 Map），也需要手动更新
            //    例如：SpellRarity 中可能有一个静态 Map<String, SpellRarity> BY_ID，你需要用反射或者 Accessor 去更新它

        } catch (Exception e) {
            throw new RuntimeException("Failed to extend SpellRarity enum", e);
        }
    }
    */
}