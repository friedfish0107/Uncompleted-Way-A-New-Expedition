package com.friedfish.uncompleted_way_spellbook.content.datagen;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class DamageTypeProvider {
    public static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, UncompletedWaySpellbook.id(name));
    }

    // Spell School Related
    public static final ResourceKey<DamageType> CRAFT_MAGIC = register("craft_magic");
    public static final ResourceKey<DamageType> MINE_MAGIC = register("mine_magic");
    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(CRAFT_MAGIC, new DamageType(CRAFT_MAGIC.location().getPath(), DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0f));
        context.register(MINE_MAGIC, new DamageType(MINE_MAGIC.location().getPath(), DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0f));
    }
}
