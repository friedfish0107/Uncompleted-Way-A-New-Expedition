package com.friedfish.uncompleted_way_spellbook.content.spell.functional;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.gui.overlay.ItemAnimationOverlay;
import com.friedfish.uncompleted_way_spellbook.content.recipe.drain_mana.DrainManaRecipe;
import com.friedfish.uncompleted_way_spellbook.misc.LogFlags;
import com.friedfish.uncompleted_way_spellbook.registry.RecipeRegistry;
import com.friedfish.uncompleted_way_spellbook.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class BlastSpell extends AbstractSpell {

    private final ResourceLocation spellId = UncompletedWaySpellbook.id("blast");
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.RECIPE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(30)
            .build();

    public BlastSpell() {
        this.baseSpellPower=0;
        this.baseManaCost=0;
        this.manaCostPerLevel=0;
        this.spellPowerPerLevel=16;
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
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundEvents.BLASTFURNACE_FIRE_CRACKLE);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if(entity instanceof Player player){
            ItemStack mainHandItem = player.getMainHandItem();
            Optional<BlastingRecipe> recipes= level.getRecipeManager().getRecipeFor(
                    RecipeType.BLASTING,
                    new SingleRecipeInput(mainHandItem),
                    level)
                    .map(RecipeHolder::value);
            if (recipes.isEmpty()) return;

            LogFlags.spellDebugLog("debug");

            BlastingRecipe recipe = recipes.get();
            ItemStack result= recipe.getResultItem(level.registryAccess());
            int count = mainHandItem.getCount();

            int processCount= Math.min(
                    count,
                    maxProcessCount(spellLevel,entity)
            );
            LogFlags.spellDebugLog("%d,%d,%d".formatted(count,maxProcessCount(spellLevel,entity),processCount));

            ContainerHelper.clearOrCountMatchingItems(mainHandItem,item->true,processCount,false);
            LogFlags.spellDebugLog("debug_give_item%b".formatted(player.addItem(result.copyWithCount(processCount))));
        }
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return (entity instanceof Player player)&&level.getRecipeManager().getRecipeFor(RecipeType.BLASTING,new SingleRecipeInput(player.getMainHandItem()),level).isPresent();
    }

    @Override
    public void onClientPreCast(Level level, int spellLevel, LivingEntity entity, InteractionHand hand, @Nullable MagicData playerMagicData) {
        if(entity instanceof Player player) {
            ItemStack result= level.getRecipeManager().getRecipeFor(RecipeType.BLASTING,new SingleRecipeInput(player.getMainHandItem()),level).get().value().getResultItem(level.registryAccess());
            ItemAnimationOverlay.playSmithingAnimation(entity.getMainHandItem(), result);
        }
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui."+UncompletedWaySpellbook.MODID+".max_process",maxProcessCount(spellLevel,caster)));
    }

    private int maxProcessCount(int spellLevel,LivingEntity entity){
        return Math.max((int) getSpellPower(spellLevel,entity)*16,16);
    }
}
