package com.friedfish.uncompleted_way_spellbook;

import com.friedfish.uncompleted_way_spellbook.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(value = UncompletedWaySpellbook.MODID)
public class UncompletedWaySpellbook {
    public static final String MODID="uncompleted_way_spellbook";
    public static final Logger LOGGER = LogUtils.getLogger();

    public UncompletedWaySpellbook(IEventBus modEventBus, ModContainer modContainer) {
        //NeoForge.EVENT_BUS.register(this);

        ItemRegistry.register(modEventBus);
        ComponentRegistry.register(modEventBus);
        MobEffectRegistry.register(modEventBus);
        EntityRegistry.register(modEventBus);
        SpellRegistry.register(modEventBus);
        DataAttachmentRegistry.register(modEventBus);
        //FluidRegistry.register(modEventBus);

        //LOGGER.debug("SpellRarities{}", (Object) SpellRarity.values());
    }
    public static ResourceLocation id(@NotNull String path) {
        return ResourceLocation.fromNamespaceAndPath(UncompletedWaySpellbook.MODID, path);
    }
}
