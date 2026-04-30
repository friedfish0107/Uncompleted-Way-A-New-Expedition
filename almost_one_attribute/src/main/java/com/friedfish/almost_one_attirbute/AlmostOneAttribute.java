package com.friedfish.almost_one_attirbute;

import com.friedfish.almost_one_attirbute.attribute.AttributeRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = AlmostOneAttribute.MODID)
public class AlmostOneAttribute {
    public static final String MODID="almost_one_attribute";

    public AlmostOneAttribute(IEventBus modEventBus, ModContainer modContainer) {
        AttributeRegistry.register(modEventBus);
    }
}
