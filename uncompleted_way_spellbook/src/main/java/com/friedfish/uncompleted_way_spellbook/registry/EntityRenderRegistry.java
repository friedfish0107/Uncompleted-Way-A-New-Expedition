package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.uncompleted_way_spellbook.content.entity.bloodAreaCircle.BloodAreaCircleRender;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber
public class EntityRenderRegistry {
    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.BLOOD_AREA_CIRCLE_ENTITY.get(), BloodAreaCircleRender::new);
        event.registerEntityRenderer(EntityRegistry.SUMMONED_MINECART.get(), context -> new MinecartRenderer<>(context, ModelLayers.MINECART));
    }
}
