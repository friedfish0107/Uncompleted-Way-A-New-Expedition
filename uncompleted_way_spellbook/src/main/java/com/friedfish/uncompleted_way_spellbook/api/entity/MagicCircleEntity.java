package com.friedfish.uncompleted_way_spellbook.api.entity;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.api.magic_circle.IMagicCircle;
import com.friedfish.uncompleted_way_spellbook.api.magic_circle.MagicCircleContext;
import com.friedfish.uncompleted_way_spellbook.misc.LogFlags;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class MagicCircleEntity<T extends IMagicCircle> extends Entity{

    private static final EntityDataAccessor<String> TYPE_ID = SynchedEntityData.defineId(MagicCircleEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(MagicCircleEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> HEIGHT = SynchedEntityData.defineId(MagicCircleEntity.class, EntityDataSerializers.FLOAT);
    private T magicCircle;

    public void setMagicCircle(T magicCircle) {
        this.magicCircle = magicCircle;
        if (!level().isClientSide) {
            syncDataToClient();
        }
    }

    public MagicCircleEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        setNoGravity(true);
    }

    public MagicCircleEntity(EntityType<?> entityType, Level level, T magicCircle) {
        this(entityType, level);
        this.magicCircle=magicCircle;
    }

    /*
    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (magicCircle != null) {
            MagicCircleContext ctx = magicCircle.getContext();
            ctx.deserializeNBT(compound, level());
            if(LogFlags.MAGIC_CIRCLE_DEBUG)
                UncompletedWaySpellbook.LOGGER.debug("readAdditionalSaveData");
        }
    }
    */

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        if (magicCircle != null) {
            CompoundTag ctxTag = magicCircle.getContext().serializeNBT();
            compound.merge(ctxTag);
            if(LogFlags.MAGIC_CIRCLE_DEBUG)
                UncompletedWaySpellbook.LOGGER.debug("addAdditionalSaveData");
        }
    }


    @Override
    @SuppressWarnings("removal")
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        super.deserializeNBT(provider,nbt);
    }

    //SynchedEntityData
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(TYPE_ID, "");          // 暂时无作用，未来可能需要
        builder.define(RADIUS, 0.0f);
        builder.define(HEIGHT, 0.0f);
    }

    // 服务端调用：同步法阵数据到客户端
    public void syncDataToClient() {
        if (magicCircle != null) {
            MagicCircleContext ctx = magicCircle.getContext();
            entityData.set(TYPE_ID, magicCircle.getMagicCircleId());  // 未来可能需要
            entityData.set(RADIUS, (float) ctx.getRadius());
            entityData.set(HEIGHT, (float) ctx.getHeight());
        }
    }

    // 客户端读取方法
    public String getTypeId() { return entityData.get(TYPE_ID); }
    public float getRadius()   { return entityData.get(RADIUS); }
    public float getHeight()   { return entityData.get(HEIGHT); }

    @Override
    public void tick() {
        super.tick();
        if(level().isClientSide)return;
        if(magicCircle==null)return;

        magicCircle.getContext().setPos(position());

        magicCircle.tick();

        if (magicCircle.getContext().isRemoved()) {
            discard();
        }
    }

    public T getMagicCircle() {
        if(magicCircle==null)return null;
        return magicCircle;
    }
}
