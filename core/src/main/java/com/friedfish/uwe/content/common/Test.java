package com.friedfish.uwe.content.common;

import io.redspace.ironsspellbooks.item.ILecternPlaceable;
import io.redspace.ironsspellbooks.item.SpellBook;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class Test {
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event){
        event.getEntity().sendSystemMessage(Component.literal("test"));
        //event.getEntity().hurt();
    }
    @SubscribeEvent
    public static void toDebug(ServerChatEvent event){
        if(event.getMessage().getString().equals("a")){
            ItemStack stack=event.getPlayer().getMainHandItem();
            event.getPlayer().displayClientMessage(Component.literal(String.valueOf(stack.isEmpty())), false);
        }
    }
}
