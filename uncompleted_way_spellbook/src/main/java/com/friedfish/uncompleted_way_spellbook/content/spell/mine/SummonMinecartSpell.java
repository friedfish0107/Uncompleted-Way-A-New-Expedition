package com.friedfish.uncompleted_way_spellbook.content.spell.mine;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.entity.mobs.spell.SummonedMinecart;
import com.friedfish.uncompleted_way_spellbook.misc.LogFlags;
import com.friedfish.uncompleted_way_spellbook.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SummonMinecartSpell extends AbstractSpell {
    private final ResourceLocation spellId = UncompletedWaySpellbook.id("summon_minecart");

    public SummonMinecartSpell() {
        this.manaCostPerLevel = 2;
        this.baseSpellPower = 100 - 15;
        this.spellPowerPerLevel = 15;
        this.castTime = 20;
        this.baseManaCost = 50;
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.MINE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(20)
            .build();

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public ICastDataSerializable getEmptyCastData() {
        return new SummonedEntitiesCastData();
    }

    @Override
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return 2;
    }

    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        if (SummonManager.recastFinishedHelper(serverPlayer, recastInstance, recastResult, castDataSerializable)) {
            super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);
        }
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        PlayerRecasts recasts = playerMagicData.getPlayerRecasts();
        if (!recasts.hasRecastForSpell(this)) {
            SummonedEntitiesCastData summonedEntitiesCastData = new SummonedEntitiesCastData();
            int summonTime = 20 * 60 * 10;
            BlockHitResult result = Utils.getTargetBlock(world,entity, ClipContext.Fluid.NONE,32);
            Vec3 spawn = result.getLocation();
            Vec3 forward = entity.getForward().normalize().scale(1.5f);
            spawn.add(forward.x, 0.15f, forward.z);

            SummonedMinecart minecart = new SummonedMinecart(world);
            SummonManager.setOwner(minecart,entity);
            minecart.setPos(spawn);

            //setAttributes(horse, getSpellPower(spellLevel, entity) / 100f);
            //var creature = NeoForge.EVENT_BUS.post(new SpellSummonEvent<>(entity, minecart, this.spellId, spellLevel)).getCreature();
            
            world.addFreshEntity(minecart);
            SummonManager.initSummon(entity, minecart, summonTime, summonedEntitiesCastData);

            RecastInstance recastInstance = new RecastInstance(this.getSpellId(), spellLevel, getRecastCount(spellLevel, entity), summonTime, castSource, summonedEntitiesCastData);
            recasts.addRecast(recastInstance, playerMagicData);
        }

        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        if (playerMagicData.getPlayerRecasts().hasRecastForSpell(this))return true;
        var rotation=entity.getLookAngle().normalize().scale(32);
        var pos=entity.getEyePosition();
        var dest=rotation.add(pos);
        BlockHitResult hitResult=level.clip(new ClipContext(pos,dest, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE,entity));
        LogFlags.spellDebugLog(level.getBlockState(hitResult.getBlockPos()).getBlock().toString());
        return level.getBlockState(hitResult.getBlockPos()).getBlock() instanceof BaseRailBlock;
    }

}
