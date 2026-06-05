package com.friedfish.uncompleted_way_spellbook.mixin;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import java.util.List;
import java.util.ArrayList;

@Mixin(TooltipsUtils.class)
public abstract class TooltipsUtilsMixin {

    @Inject(
            method = "formatScrollTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0,          // 第一次 add（即添加 title 的那次）
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private static void addCustomLineAfterLevel(
            ItemStack stack, Player player, CallbackInfoReturnable<List<Component>> cir, ISpellContainer spellList, SpellData spellData, AbstractSpell spell, int spellLevel, MutableComponent levelText, MutableComponent title, List uniqueInfo, MutableComponent whenInSpellBook, MutableComponent manaCost, MutableComponent cooldownTime, MutableComponent castType, List lines) {
        // 确保 lines 是可变的 ArrayList（目标方法内确实如此）
        if (lines instanceof List) {
            // 构建自定义文本（保持缩进风格，加一个空格前缀）
            Component customLine = Component.literal(" ")
                    .append(Component.translatable("stage.uncompleted_way_spellbook.1"))
                    .withStyle(ChatFormatting.GRAY);  // 可替换为其他颜色/样式

            // 在索引 1 处插入（第 1 行是 title，第 2 行变成自定义行）
            lines.add(1, customLine);
        }
    }
}