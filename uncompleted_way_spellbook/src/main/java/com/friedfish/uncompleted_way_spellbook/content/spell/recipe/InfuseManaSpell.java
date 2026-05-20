package com.friedfish.uncompleted_way_spellbook.content.spell.recipe;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.recipe.infuse_mana.InfuseManaRecipe;
import com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter.SpellFilterRecipe;
import com.friedfish.uncompleted_way_spellbook.registry.RecipeRegistry;
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
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public class InfuseManaSpell extends AbstractSpell {

    private final ResourceLocation spellId = UncompletedWaySpellbook.id("infuse_mana");
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.RECIPE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(30)
            .build();

    public InfuseManaSpell() {
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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if(entity instanceof Player player){
            ItemStack mainHandItem = player.getMainHandItem();
            Optional<InfuseManaRecipe> recipes= level.getRecipeManager().getRecipeFor(
                    RecipeRegistry.INFUSE_MANA.get(),
                    new SingleRecipeInput(mainHandItem),
                    level)
                    .map(RecipeHolder::value)
                    .filter(e->e.canCraft(mainHandItem,player));
            if (recipes.isEmpty()) return;

            InfuseManaRecipe recipe = recipes.get();
            ItemStack result= recipe.getResult();
            int count = 1;
            for(ItemStack item:recipe.getInput().getItems()){
                if(mainHandItem.is(item.getItem())){
                    count=item.getCount();
                    break;
                }
            }
            int cost = recipe.getCost();
            int processCount= Math.min(
                    Math.min(mainHandItem.getCount()/count,
                            player.getInventory().countItem(ItemRegistry.ARCANE_ESSENCE.get())/ cost),
                    maxProcessCount(spellLevel,entity)
            );

            ContainerHelper.clearOrCountMatchingItems(player.getInventory(),e->e.is(ItemRegistry.ARCANE_ESSENCE), cost*processCount,false);
            ContainerHelper.clearOrCountMatchingItems(player.getInventory(),e->e.is(mainHandItem.getItem()), count*processCount,false);
            for (int i = 0; i < processCount ; i++) {
                player.addItem(result);
            }
        }
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return (entity instanceof Player player)&&level.getRecipeManager().getRecipeFor(RecipeRegistry.INFUSE_MANA.get(),new SingleRecipeInput(player.getMainHandItem()),level).map(RecipeHolder::value).filter(e->e.canCraft(player.getMainHandItem(), player)).isPresent();
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui."+UncompletedWaySpellbook.MODID+".max_process",maxProcessCount(spellLevel,caster)));
    }

    private int maxProcessCount(int spellLevel,LivingEntity entity){
        return Math.max((int) getSpellPower(spellLevel,entity),1);
    }
}
