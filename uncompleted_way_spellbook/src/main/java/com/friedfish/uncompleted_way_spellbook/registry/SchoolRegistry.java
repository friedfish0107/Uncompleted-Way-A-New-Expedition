package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.datagen.DamageTypeProvider;
import com.friedfish.uncompleted_way_spellbook.content.util.UWSTags;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static io.redspace.ironsspellbooks.api.registry.SchoolRegistry.SCHOOL_REGISTRY_KEY;

public class SchoolRegistry {
    private static final DeferredRegister<SchoolType> SCHOOLS = DeferredRegister.create(SCHOOL_REGISTRY_KEY, UncompletedWaySpellbook.MODID);

    public static void register(IEventBus eventBus) {
        SCHOOLS.register(eventBus);
    }

    private static Supplier<SchoolType> registerSchool(SchoolType schoolType) {
        return SCHOOLS.register(schoolType.getId().getPath(), () -> schoolType);
    }

    public static final ResourceLocation RECIPE_RESOURCE = UncompletedWaySpellbook.id("craft");
    public static final ResourceLocation MINE_RESOURCE = UncompletedWaySpellbook.id("mine");

    public static final Supplier<SchoolType> RECIPE = registerSchool(new SchoolType(
            RECIPE_RESOURCE,
            UWSTags.RECIPE_FOCUS,
            Component.translatable("school."+UncompletedWaySpellbook.MODID+".craft").withStyle(Style.EMPTY.withColor(0xc29d62)),
            AttributeRegistry.CRAFT_SPELL_POWER,
            AttributeRegistry.CRAFT_MAGIC_RESIST,
            new Holder.Direct<>(SoundEvents.CRAFTER_CRAFT),
            DamageTypeProvider.CRAFT_MAGIC
    ));

    public static final Supplier<SchoolType> MINE = registerSchool(new SchoolType(
            MINE_RESOURCE,
            UWSTags.MINE_FOCUS,
            Component.translatable("school."+UncompletedWaySpellbook.MODID+".mine").withStyle(Style.EMPTY.withColor(0x7f7f7f)),
            AttributeRegistry.MINE_SPELL_POWER,
            AttributeRegistry.MINE_MAGIC_RESIST,
            new Holder.Direct<>(SoundEvents.STONE_BREAK),
            DamageTypeProvider.MINE_MAGIC
    ));
}
