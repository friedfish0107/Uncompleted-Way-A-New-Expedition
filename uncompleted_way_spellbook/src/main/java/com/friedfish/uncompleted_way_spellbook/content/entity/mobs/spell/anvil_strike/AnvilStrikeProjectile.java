package com.friedfish.uncompleted_way_spellbook.content.entity.mobs.spell.anvil_strike;

import com.friedfish.uncompleted_way_spellbook.registry.EntityRegistry;
import com.friedfish.uncompleted_way_spellbook.registry.SchoolRegistry;
import com.friedfish.uncompleted_way_spellbook.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.entity.spells.AbstractShieldEntity;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.entity.spells.ShieldPart;
import io.redspace.ironsspellbooks.entity.spells.ice_spike.IceSpikeEntity;
import io.redspace.ironsspellbooks.entity.spells.thrown_item.ThrownItemProjectile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author friedfish
 * 介绍：
 * 1. 生成一个铁砧样实体，在目标生物上方悬停约30ticks
 * 2. 随重力下落/平移向下，穿过方块，直到达到生物的高度(约消耗5ticks)
 * 3. 在下落和落地时对生物造成伤害，产生地震粒子，持续存在一段时间(约10ticks)
 * 4. 消失
 */
public class AnvilStrikeProjectile extends AoeEntity {
    private static final EntityDataAccessor<Float> DATA_SCALE = SynchedEntityData.defineId(AnvilStrikeProjectile.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_TARGET_Y= SynchedEntityData.defineId(AnvilStrikeProjectile.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> DATA_LADING_TIME = SynchedEntityData.defineId(AnvilStrikeProjectile.class, EntityDataSerializers.INT);

    public static final int FLOATING_TIME = 20;
    public static final int LANDING_TIME = 30;

    public static final float FALLING_DISTANCE=1.5f;
    public static final float TARGET_Y_OFFSET=5f;

    private final List<Entity> victims;

    private CameraShakeData cameraShakeData;

    public float getScale() {
        return entityData.get(DATA_SCALE);
    }

    public void setScale(float scale) {
        entityData.set(DATA_SCALE, scale);
    }

    public int getFloatingTime() {
        return entityData.get(DATA_LADING_TIME);
    }

    public void setFloatingTime(int floatingTime) {
        entityData.set(DATA_LADING_TIME, floatingTime);
    }

    public float getTargetY() {
        return entityData.get(DATA_TARGET_Y);
    }

    public void setTargetY(float targetY) {
        entityData.set(DATA_TARGET_Y, targetY);
    }

    @Override
    public void tick() {
        this.refreshDimensions();
        this.setOnGround(false);
        applyGravity();
        move(MoverType.SELF, this.getDeltaMovement());
        handlePortal();
        if (tickCount==FLOATING_TIME) {
            if(!level().isClientSide){
                if(!this.isSilent()){
                    level().playSound(null,this.blockPosition(),SoundEvents.ANVIL_FALL, SoundSource.NEUTRAL,1*getScale(),1f);
                }
                this.setNoGravity(false);
            }
        } else if (getY() <= getTargetY()) {
            this.noPhysics=false;
        }
        if (onGround()) {
            createScreenShake();
            if(!level().isClientSide){
                if(!this.isSilent()){
                    level().playSound(null,this.blockPosition(),SoundEvents.ANVIL_LAND, SoundSource.NEUTRAL,1*getScale(),1f);
                }
                this.setNoGravity(true);

                setFloatingTime(tickCount);

                AABB boundingBox = this.getBoundingBox();
                for (int i = (int)boundingBox.minX; i < (int)boundingBox.maxX; i++) {
                    for (int j = (int)boundingBox.minZ; j < (int)boundingBox.maxZ; j++) {
                        BlockPos pos=new BlockPos(i,(int)boundingBox.minY,j).below().below();
                        float distance = (float) boundingBox.getCenter().distanceTo(pos.getCenter());
                        float strength = (2f+0.1f*getScale())*(1-distance/getScale());
                        Utils.createTremorBlock(level(),pos,strength);
                    }
                }
            }
        }
        else if (tickCount>getFloatingTime()+LANDING_TIME) {
            if(!level().isClientSide){
                if(!this.isSilent()){
                    level().playSound(null,this.blockPosition(),SoundEvents.ANVIL_BREAK, SoundSource.NEUTRAL,1*getScale(),1f);
                }
            }
            discard();
        }

        if (tickCount>FLOATING_TIME) {
            AABB damager = this.getBoundingBox();
            for (Entity entity : level().getEntities(this, damager).stream().filter(target -> canHitEntity(target) && !victims.contains(target)).collect(Collectors.toSet())) {
                if (DamageSources.applyDamage(entity, damage, SpellRegistry.ANVIL_STRIKE.get().getDamageSource(this, getOwner()))) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add(0, this.getScale() * 0.1, 0));
                    entity.hurtMarked = true;
                }
                victims.add(entity);
                if (entity instanceof ShieldPart || entity instanceof AbstractShieldEntity) {
                    discard();
                    return;
                }
            }
        }
        setDeltaMovement(getDeltaMovement().scale(0.98));
    }
    protected void createScreenShake() {
        if (!this.level().isClientSide && !this.isRemoved()) {
            this.cameraShakeData = new CameraShakeData(level(), this.duration - this.tickCount, this.position(), 15);
            CameraShakeManager.addCameraShake(cameraShakeData);
        }
    }

    @Override
    public void remove(RemovalReason pReason) {
        super.remove(pReason);
        if (!level().isClientSide) {
            CameraShakeManager.removeCameraShake(this.cameraShakeData);
        }
    }
    @Override
    protected double getDefaultGravity() {
        return .04;
    }

    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    @Override
    public void applyEffect(LivingEntity target) {

    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_SCALE, 1f);
        pBuilder.define(DATA_LADING_TIME, 600);
        pBuilder.define(DATA_TARGET_Y, 0f);
    }

    @Override
    public float getParticleCount() {
        return 0;
    }

    @Override
    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(this.getScale(),this.getScale());
    }

    public AnvilStrikeProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.victims = new ArrayList<>();
    }


    public AnvilStrikeProjectile(Level pLevel, float damage, float scale) {
        this(EntityRegistry.ANVIL_STRIKE_PROJECTILE.get(), pLevel);
        setDamage(damage);
        setScale(scale);
        setNoGravity(true);

        //setBoundingBox(getBoundingBox().inflate(scale/2));
    }
}
