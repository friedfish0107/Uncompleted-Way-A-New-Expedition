package com.friedfish.uncompleted_way_spellbook.content.spell.functional;

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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class OpenCraftingTableSpell extends AbstractSpell {
    private final ResourceLocation spellId = UncompletedWaySpellbook.id("open_crafting_table");
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.RECIPE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(0)
            .build();

    public OpenCraftingTableSpell() {
        this.baseSpellPower=1;
        this.baseManaCost=0;
        this.manaCostPerLevel=0;
        this.spellPowerPerLevel=0;
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
        if(entity instanceof ServerPlayer player){
            player.openMenu(getMenuProvider());
        }
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }
    private MenuProvider getMenuProvider(){
        return new SimpleMenuProvider(
                (id,inv,p)->new CraftingMenu(id,inv),Component.translatable("container.crafting")
        );
    }
}
