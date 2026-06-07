package com.friedfish.uwe.registry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.server.command.CurioArgumentType;

@EventBusSubscriber
public class AttachCurio {
    @SubscribeEvent
    public static void attachCurioCapability(RegisterCapabilitiesEvent event){
        event.registerItem(CuriosCapability.ITEM,
                (stack, context) -> new ICurio(){
                    @Override
                    public ItemStack getStack() {
                        return stack;
                    }

                    @Override
                    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id) {
                        Multimap<Holder<Attribute>, AttributeModifier> map = HashMultimap.create();
                        map.put(Attributes.MAX_HEALTH,new AttributeModifier(ResourceLocation.fromNamespaceAndPath("uwe","diamond"),1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                        return map;
                    }

                },
                Items.DIAMOND
        );
    }
}
