package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.recipe.drain_mana.DrainManaRecipe;
import com.friedfish.uncompleted_way_spellbook.content.recipe.drain_mana.DrainManaRecipeSerializer;
import com.friedfish.uncompleted_way_spellbook.content.recipe.infuse_mana.InfuseManaRecipe;
import com.friedfish.uncompleted_way_spellbook.content.recipe.infuse_mana.InfuseManaRecipeSerializer;
import com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter.SpellFilterRecipe;
import com.friedfish.uncompleted_way_spellbook.content.recipe.spell_filter.SpellFilterRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RecipeRegistry {

    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES=DeferredRegister.create(Registries.RECIPE_TYPE, UncompletedWaySpellbook.MODID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, UncompletedWaySpellbook.MODID);


    public static void register(IEventBus eventBus){
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);

    }
    public static final Supplier<RecipeSerializer<SpellFilterRecipe>> FILTER_SERIALIZER =
            RECIPE_SERIALIZERS.register("filter", SpellFilterRecipeSerializer::new);

    public static final Supplier<RecipeType<SpellFilterRecipe>> FILTER=
            RECIPE_TYPES.register(
                    "filter",
                    () -> RecipeType.simple(UncompletedWaySpellbook.id("filter"))
            );

    public static final Supplier<RecipeSerializer<InfuseManaRecipe>> INFUSE_MANA_SERIALIZER =
            RECIPE_SERIALIZERS.register("infuse_mana", InfuseManaRecipeSerializer::new);

    public static final Supplier<RecipeType<InfuseManaRecipe>> INFUSE_MANA=
            RECIPE_TYPES.register(
                    "infuse_mana",
                    () -> RecipeType.simple(UncompletedWaySpellbook.id("infuse_mana"))
            );
    public static final Supplier<RecipeSerializer<DrainManaRecipe>> DRAIN_MANA_SERIALIZER =
            RECIPE_SERIALIZERS.register("drain_mana", DrainManaRecipeSerializer::new);

    public static final Supplier<RecipeType<DrainManaRecipe>> DRAIN_MANA=
            RECIPE_TYPES.register(
                    "drain_mana",
                    () -> RecipeType.simple(UncompletedWaySpellbook.id("drain_mana"))
            );


}
