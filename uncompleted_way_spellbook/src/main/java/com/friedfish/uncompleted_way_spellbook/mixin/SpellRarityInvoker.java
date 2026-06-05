package com.friedfish.uncompleted_way_spellbook.mixin;

import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpellRarity.class)
public interface SpellRarityInvoker {
    @Invoker("<init>") // "<init>" 是 JVM 字节码中构造函数的内部名称
    static SpellRarity invokeNew(String internalName, int ordinal,final int newValue) {
        // 4. 方法体留空或抛出一个 AssertionError 即可。
        //    Mixin 会在编译或运行时自动实现这个方法。
        throw new AssertionError("This method should be implemented by Mixin!");
    }
}
