package com.friedfish.uncompleted_way_spellbook.content.spell.recipe;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class CrystalSpell extends AbstractSpell {

    private final ResourceLocation spellId = UncompletedWaySpellbook.id("crystal");
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.RECIPE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(120)
            .build();

    public CrystalSpell() {
        this.baseSpellPower=1;
        this.baseManaCost=0;
        this.manaCostPerLevel=0;
        this.spellPowerPerLevel=0;
        this.castTime=600;
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
        if(entity instanceof Player player){
            int mana=(int)playerMagicData.getMana();
            int count=mana/getOutputRate(spellLevel,player)*getOutputCount(spellLevel,player);
            ItemStack result=new ItemStack(ItemRegistry.ARCANE_ESSENCE,count);
            player.addItem(result);
            playerMagicData.setMana(0);


        }
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return playerMagicData.getMana()>=getOutputRate(spellLevel,entity);
    }

    private int getOutputRate(int spellLevel, LivingEntity entity){
        //未来在此处处理结晶升级相关饰品
        return 100;
    }

    private int getOutputCount(int spellLevel,LivingEntity entity){
        float power = getSpellPower(spellLevel,entity);

        if(power>=5){
            return 3;
        } else if (power>=2) {
            return 2;
        }
        return 1;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui."+UncompletedWaySpellbook.MODID+".all_mana"),
                Component.translatable("ui."+UncompletedWaySpellbook.MODID+".mana_per_effect",getOutputRate(spellLevel,caster)),
                Component.translatable("ui."+UncompletedWaySpellbook.MODID+".arcane_essence_count",getOutputCount(spellLevel,caster))
        );
    }
}
