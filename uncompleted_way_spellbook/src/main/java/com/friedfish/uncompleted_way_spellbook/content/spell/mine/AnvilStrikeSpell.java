package com.friedfish.uncompleted_way_spellbook.content.spell.mine;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.entity.mobs.spell.anvil_strike.AnvilStrikeProjectile;
import com.friedfish.uncompleted_way_spellbook.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.RaycastBuilder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.entity.spells.blood_needle.BloodNeedle;
import io.redspace.ironsspellbooks.entity.spells.thrown_item.ThrownItemProjectile;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AnvilStrikeSpell extends AbstractSpell {
    private final ResourceLocation spellId = UncompletedWaySpellbook.id("anvil_strike");
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.MINE_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(5)
            .build();

    public AnvilStrikeSpell() {
        this.baseSpellPower=2;
        this.baseManaCost=15;
        this.manaCostPerLevel=5;
        this.spellPowerPerLevel=1;
        this.castTime=0;
    }
    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 1)),
                Component.translatable("ui.irons_spellbooks.radius",getScale(spellLevel,caster)));
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return getSpellPower(spellLevel,caster)*5;
    }

    private float getScale(int spellLevel, LivingEntity caster) {
        return spellLevel;
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
        return CastType.INSTANT;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        Vec3 spawn = null;
        float damage = getDamage(spellLevel, entity);
        float scale = getScale(spellLevel, entity);
        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetData) {
            var targetEntity = targetData.getTarget((ServerLevel) level);
            if (targetEntity != null) {
                spawn = targetEntity.position();
            }
        }

        if (spawn == null) {
            spawn = RaycastBuilder.begin(level, entity)
                    .range(32)
                    .checkForBlocks(true)
                    .bbInflation(.15f)
                    .build()
                    .getLocation();
            spawn = Utils.moveToRelativeGroundLevel(level, spawn, 6);
        }

        AnvilStrikeProjectile anvilStrikeProjectile = new AnvilStrikeProjectile(level,damage,scale);
        anvilStrikeProjectile.setOwner(entity);
        anvilStrikeProjectile.moveTo(spawn.add(0,AnvilStrikeProjectile.FALLING_DISTANCE*scale,0));
        anvilStrikeProjectile.setTargetY((float) (spawn.y+AnvilStrikeProjectile.TARGET_Y_OFFSET));
        level.addFreshEntity(anvilStrikeProjectile);
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }
    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        Utils.preCastTargetHelper(level, entity, playerMagicData, this, 32, .15f,false);
        return true;
    }
}
