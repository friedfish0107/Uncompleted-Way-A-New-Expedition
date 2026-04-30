package com.friedfish.uncompleted_way_spellbook.content.spell.blood;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.entity.bloodAreaCircle.BloodAreaCircleEntity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class BloodAreaSpell extends AbstractSpell {

    private final ResourceLocation spellId = UncompletedWaySpellbook.id("blood_area");
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(SchoolRegistry.BLOOD_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(10)
            .build();

    public BloodAreaSpell() {
        this.baseSpellPower=1;
        this.baseManaCost=100;
        this.manaCostPerLevel=0;
        this.spellPowerPerLevel=0;
        this.castTime=100;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        var blockHitResult = Utils.getTargetBlock(level,entity, ClipContext.Fluid.NONE,16);
        BlockPos pos=blockHitResult.getBlockPos();
        BloodAreaCircleEntity circleEntity=BloodAreaCircleEntity.create(level,pos.getCenter(),entity);

        circleEntity.moveTo(pos,0,0);
        level.addFreshEntity(circleEntity);
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        var blockHitResult = Utils.getTargetBlock(level,entity, ClipContext.Fluid.NONE,16);
        if(blockHitResult.getType()!= HitResult.Type.BLOCK) {
            if (entity instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.irons_spellbooks.cast_error_target_block").withStyle(ChatFormatting.RED)));
            }
            return false;
        }
        return true;
    }
}
