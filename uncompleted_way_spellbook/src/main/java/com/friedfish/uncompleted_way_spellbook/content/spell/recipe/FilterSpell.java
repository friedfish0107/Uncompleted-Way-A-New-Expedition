package com.friedfish.uncompleted_way_spellbook.content.spell.recipe;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter.SpellFilterRecipe;
import com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter.SpellFilterRecipeInput;
import com.friedfish.uncompleted_way_spellbook.registry.RecipeRegistry;
import com.friedfish.uncompleted_way_spellbook.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class FilterSpell extends AbstractSpell {

    private final ResourceLocation spellId = UncompletedWaySpellbook.id("filter");
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(SchoolRegistry.RECIPE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(10)
            .build();

    public FilterSpell() {
        this.baseSpellPower=1;
        this.baseManaCost=100;
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
        var blockHitResult = Utils.getTargetBlock(level,entity, ClipContext.Fluid.NONE,16);
        BlockPos pos=blockHitResult.getBlockPos();
        BlockState blockState = level.getBlockState(pos);

        SpellFilterRecipeInput input=new SpellFilterRecipeInput(blockState,spellLevel);

        RecipeManager recipes=level.getRecipeManager();
        RandomSource randomSource=level.random;

        Optional<RecipeHolder<SpellFilterRecipe>> optional=recipes.getRecipeFor(
                RecipeRegistry.FILTER.get(),
                input,
                level
        );


        Optional<List<ItemStack>> results=optional.map(RecipeHolder::value)
                .map(e->e.getResults(randomSource));
        if(results.isPresent()){
            level.removeBlock(pos,false);
            results.get().forEach(result->{
                ItemEntity itemEntity = new ItemEntity(level,
                        // Center of pos.
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        result);
                level.addFreshEntity(itemEntity);
            });
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }
}
