package com.friedfish.uncompleted_way_spellbook.content.entity.mobs.spell;

import com.friedfish.uncompleted_way_spellbook.registry.EntityRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SummonedMinecart extends AbstractMinecart {
    public SummonedMinecart(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected double getMaxSpeed() {
        return (this.isInWater() ? 8.0 : 16.0) / 20.0;
    }

    public SummonedMinecart(Level level) {
        super(EntityRegistry.SUMMONED_MINECART.get(), level);
    }

    public SummonedMinecart(Level level, double x, double y, double z) {
        super(EntityRegistry.SUMMONED_MINECART.get(), level, x, y, z);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        InteractionResult ret = super.interact(player, hand);
        if (ret.consumesAction()) return ret;
        if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        } else if (this.isVehicle()) {
            return InteractionResult.PASS;
        } else if (!this.level().isClientSide) {
            return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    protected Item getDropItem() {
        return Items.AIR;
    }

    /**
     * Called every tick the minecart is on an activator rail.
     */
    @Override
    public void activateMinecart(int x, int y, int z, boolean receivingPower) {
        if (receivingPower) {
            if (this.isVehicle()) {
                this.ejectPassengers();
            }

            if (this.getHurtTime() == 0) {
                this.setHurtDir(-this.getHurtDir());
                this.setHurtTime(10);
                this.setDamage(50.0F);
                this.markHurt();
            }
        }
    }

    @Override
    public AbstractMinecart.Type getMinecartType() {
        return AbstractMinecart.Type.RIDEABLE;
    }
}
