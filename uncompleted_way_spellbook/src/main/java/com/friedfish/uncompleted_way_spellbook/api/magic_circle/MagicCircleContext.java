package com.friedfish.uncompleted_way_spellbook.api.magic_circle;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MagicCircleContext{
    private LivingEntity owner;
    private Level level;
    private int duration;
    private int tickCount;
    private Vec3 pos;
    private double radius;
    private double height;
    private boolean removed;

    public MagicCircleContext(LivingEntity owner, Level level, int duration, int tickCount, Vec3 pos, double radius, double height) {
        this.owner = owner;
        this.level = level;
        this.duration = duration;
        this.tickCount = tickCount;
        this.pos = pos;
        this.radius = radius;
        this.height = height;
        this.removed=false;
    }
    public MagicCircleContext() {
        // 无参构造，用于反序列化时先创建空对象，再填充数据
        this.owner = null;
        this.level = null;
        this.duration = 0;
        this.tickCount = 0;
        this.pos = Vec3.ZERO;
        this.radius = 0;
        this.height = 0;
        this.removed = false;
    }
    public AABB getBoundBox(){
        return AABB.ofSize(pos.add(0,height/2,0),radius*2,height,radius*2);
    }
    public boolean isExpired() {
        return duration != -1 && tickCount >= duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Vec3 getPos() {
        return pos;
    }

    public void setPos(Vec3 pos) {
        this.pos = pos;
    }

    public int getTickCount() {
        return tickCount;
    }

    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public LivingEntity getOwner() {
        return owner;
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void addTickCount(){
        this.tickCount++;
    }

    //序列化，用于持久化存储
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (owner != null) tag.putUUID("ownerUUID", owner.getUUID());
        tag.putDouble("posX", pos.x);
        tag.putDouble("posY", pos.y);
        tag.putDouble("posZ", pos.z);
        tag.putDouble("radius", radius);
        tag.putDouble("height", height);
        tag.putInt("tickCount", tickCount);
        tag.putInt("duration", duration);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag, Level level) {
        if(tag==null)return;
        this.level = level;
        if (level instanceof ServerLevel serverLevel) this.owner= (LivingEntity) serverLevel.getEntity(tag.getUUID("ownerUUID"));
        this.pos = new Vec3(tag.getDouble("posX"), tag.getDouble("posY"), tag.getDouble("posZ"));
        this.radius = tag.getDouble("radius");
        this.height = tag.getDouble("height");
        this.tickCount = tag.getInt("tickCount");
        this.duration = tag.getInt("duration");
    }
}
