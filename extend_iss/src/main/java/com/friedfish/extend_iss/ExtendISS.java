package com.friedfish.extend_iss;

import com.friedfish.extend_iss.registries.FluidRegistry;
import com.friedfish.extend_iss.registries.ItemRegistry;
import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(ExtendISS.MODID)
public class ExtendISS {
    public static final String MODID="extend_iss";

    public static final Logger LOGGER = LogUtils.getLogger();
    public ExtendISS(IEventBus modEventBus, ModContainer modContainer) {
        //NeoForge.EVENT_BUS.register(this);

        ItemRegistry.register(modEventBus);
        FluidRegistry.register(modEventBus);

        LOGGER.debug("SpellRarities{}", (Object) SpellRarity.values());
    }
    public static ResourceLocation id(@NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath(ExtendISS.MODID, path);
    }
}