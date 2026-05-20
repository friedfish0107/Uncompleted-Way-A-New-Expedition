package com.friedfish.uncompleted_way_spellbook.content.item;

import io.redspace.ironsspellbooks.item.SpellBook;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TooltipSpellBook extends SpellBook {
    protected int tooltipLength;
    public TooltipSpellBook(int maxSpellSlots) {
        super(maxSpellSlots);
        this.tooltipLength=1;
    }
    public TooltipSpellBook(int maxSpellSlots,int tooltipLength) {
        super(maxSpellSlots);
        this.tooltipLength=tooltipLength;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, TooltipContext context, @NotNull List<Component> lines, @NotNull TooltipFlag flag) {
        for (int i = 1; i <=tooltipLength ; i++) {
            lines.add(Component.translatable(this.getDescriptionId()+".desc"+i).withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(itemStack, context, lines, flag);
    }
}
