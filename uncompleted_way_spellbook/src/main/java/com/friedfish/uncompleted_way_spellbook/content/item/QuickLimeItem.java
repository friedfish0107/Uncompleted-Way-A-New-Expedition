package com.friedfish.uncompleted_way_spellbook.content.item;

import com.friedfish.uncompleted_way_spellbook.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class QuickLimeItem extends TooltipItem{

    public QuickLimeItem(Properties properties) {
        super(properties);
    }

    public QuickLimeItem(Properties properties, int tooltipLength) {
        super(properties, tooltipLength);
    }


    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if(entity.isInWaterOrRain()){
            int count = stack.getCount();
            ItemStack newItemStack=new ItemStack(ItemRegistry.SLAKED_LIME_DUST, count);
            entity.setItem(newItemStack);
            Level level = entity.level();

            BlockPos blockPos = entity.blockPosition();
            level.levelEvent(1501, blockPos,0);//fizz，岩浆等流体的嘶嘶声与黑烟

            level.getEntitiesOfClass(LivingEntity.class,new AABB(blockPos)).forEach(selectedEntity->{
                selectedEntity.hurt(entity.damageSources().onFire(),count*4);
            });
            level.getEntitiesOfClass(LivingEntity.class,new AABB(blockPos.above())).forEach(selectedEntity->{
                selectedEntity.hurt(entity.damageSources().onFire(),count*2);
            });
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
