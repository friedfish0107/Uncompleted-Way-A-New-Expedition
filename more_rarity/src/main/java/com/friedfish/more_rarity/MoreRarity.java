package com.friedfish.more_rarity;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(MoreRarity.MODID)
public class MoreRarity {
    public static final String MODID="more_rarity";

    public static final Logger LOGGER = LogUtils.getLogger();
    public MoreRarity(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
    }
}