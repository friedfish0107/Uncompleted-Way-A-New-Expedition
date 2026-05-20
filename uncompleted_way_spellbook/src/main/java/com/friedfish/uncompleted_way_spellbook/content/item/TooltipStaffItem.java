package com.friedfish.uncompleted_way_spellbook.content.item;

import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class TooltipStaffItem extends StaffItem {
    protected int tooltipLength;
    public TooltipStaffItem(Properties properties) {
        super(properties);
        this.tooltipLength=1;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        for (int i = 1; i <=tooltipLength ; i++) {
            tooltipComponents.add(Component.translatable(this.getDescriptionId()+".desc"+i).withStyle(ChatFormatting.GRAY));
        }
    }

    public TooltipStaffItem(Properties properties, int tooltipLength) {
        super(properties);
        this.tooltipLength = tooltipLength;
    }

}
