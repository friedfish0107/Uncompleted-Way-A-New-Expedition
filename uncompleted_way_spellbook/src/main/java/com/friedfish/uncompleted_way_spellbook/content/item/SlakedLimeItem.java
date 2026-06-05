package com.friedfish.uncompleted_way_spellbook.content.item;

import com.friedfish.uncompleted_way_spellbook.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SlakedLimeItem extends TooltipItem{
    public SlakedLimeItem(Properties properties) {
        super(properties);
    }

    public SlakedLimeItem(Properties properties, int tooltipLength) {
        super(properties, tooltipLength);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if(entity.getAge()>=stack.getCount()*60){//每个物品消耗3秒，64个物品3分钟左右结束
            int count = stack.getCount();
            ItemStack newItemStack=new ItemStack(ItemRegistry.CALCIUM_CARBONATE_DUST_PILE, count);
            entity.setItem(newItemStack);
            entity.playSound(SoundEvents.BREWING_STAND_BREW);
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
