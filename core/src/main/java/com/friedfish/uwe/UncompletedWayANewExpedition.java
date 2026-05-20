package com.friedfish.uwe;

import com.friedfish.uwe.registry.ItemRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(value = UncompletedWayANewExpedition.MODID)
public class UncompletedWayANewExpedition {
    public static final String MODID="uwe";
    public static final Logger LOGGER = LogUtils.getLogger();

    public UncompletedWayANewExpedition(IEventBus modEventBus, ModContainer modContainer) {
        //NeoForge.EVENT_BUS.register(this);

        ItemRegistry.register(modEventBus);
        //FluidRegistry.register(modEventBus);
        //LOGGER.debug("SpellRarities{}", (Object) SpellRarity.values());
    }
    public static ResourceLocation id(@NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath(UncompletedWayANewExpedition.MODID, path);
    }
}
