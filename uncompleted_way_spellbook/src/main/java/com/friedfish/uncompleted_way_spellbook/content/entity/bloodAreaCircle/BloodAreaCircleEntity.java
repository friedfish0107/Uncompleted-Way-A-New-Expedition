package com.friedfish.uncompleted_way_spellbook.content.entity.bloodAreaCircle;

import com.friedfish.uncompleted_way_spellbook.api.entity.MagicCircleEntity;
import com.friedfish.uncompleted_way_spellbook.api.magic_circle.MagicCircleContext;
import com.friedfish.uncompleted_way_spellbook.content.magic_circle.BloodArea;
import com.friedfish.uncompleted_way_spellbook.registry.EntityRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BloodAreaCircleEntity extends MagicCircleEntity<BloodArea> {

    public BloodAreaCircleEntity(EntityType<BloodAreaCircleEntity> entityEntityType, Level level) {
        super(entityEntityType,level);
    }
    public static BloodAreaCircleEntity create(Level level, Vec3 pos, LivingEntity owner) {
        // 创建上下文
        MagicCircleContext ctx = new MagicCircleContext(
                owner, level, 36000, 0, pos, 16.0, 2.0  // 半径16，高2，持续200 tick
        );
        BloodArea magicCircle = new BloodArea(ctx);
        BloodAreaCircleEntity entity = new BloodAreaCircleEntity(EntityRegistry.BLOOD_AREA_CIRCLE_ENTITY.get(), level);
        entity.setMagicCircle(magicCircle);
        entity.setPos(pos);
        return entity;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        //super.readAdditionalSaveData(compound);
        if(this.getMagicCircle()==null){
            MagicCircleContext ctx=new MagicCircleContext();
            ctx.deserializeNBT(compound,level());
            this.setMagicCircle(new BloodArea(ctx));
        }
    }
}
