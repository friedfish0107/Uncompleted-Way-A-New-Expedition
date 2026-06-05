package com.friedfish.uncompleted_way_spellbook.content.spell.mine;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.mixin.SpellRarityMixin;
import com.friedfish.uncompleted_way_spellbook.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.thrown_item.ThrownItemProjectile;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.List;

public class ThrowStoneSpell extends AbstractSpell {
    private final ResourceLocation spellId = UncompletedWaySpellbook.id("throw_stone");
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.MINE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(5)
            .build();

    public ThrowStoneSpell() {
        this.baseSpellPower=0;
        this.baseManaCost=15;
        this.manaCostPerLevel=0;
        this.spellPowerPerLevel=16;
        this.castTime=0;
    }
    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 1)));
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return getSpellPower(spellLevel,caster)*5+10;
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
        ThrownItemProjectile thrownItem = new ThrownItemProjectile(level, new ItemStack(Items.STONE));
        thrownItem.setOwner(entity);
        thrownItem.setPos(entity.position().add(0, entity.getEyeHeight() - thrownItem.getBoundingBox().getYsize() * .5f, 0));
        thrownItem.shoot(entity.getLookAngle());
        thrownItem.setDamage(getDamage(spellLevel, entity));
        thrownItem.setScale(entity.getScale());
        level.addFreshEntity(thrownItem);
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }
}
