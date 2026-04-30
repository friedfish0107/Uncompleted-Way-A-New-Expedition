package com.friedfish.extend_iss.setup;

import com.friedfish.extend_iss.ExtendISS;
import com.friedfish.extend_iss.registries.FluidRegistry;
import io.redspace.ironsspellbooks.fluids.SimpleTintedClientFluidType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = ExtendISS.MODID,value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new SimpleTintedClientFluidType(ResourceLocation.fromNamespaceAndPath("neoforge", "block/milk_still"), 0xFF410c5c), FluidRegistry.ANCIENT_INK_TYPE);

    }
}
