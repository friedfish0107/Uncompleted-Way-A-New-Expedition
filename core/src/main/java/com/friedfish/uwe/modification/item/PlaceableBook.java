package com.friedfish.uwe.modification.item;

import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.registries.BlockRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;


@EventBusSubscriber
public class PlaceableBook {

    @SubscribeEvent
    public static void onBookItemClicked(PlayerInteractEvent.RightClickBlock event){
        if(event.getSide().isClient())return;
        if(!event.getItemStack().is(Items.BOOK)) return;

        Player player = event.getEntity();
        BlockHitResult hitResult= event.getHitVec();//player.pick(player.blockInteractionRange(),1f,false);

        InteractionResult interactionResult = ItemRegistry.BOOK_STACK_BLOCK_ITEM.get()
                .useOn(new UseOnContext(player,event.getHand(),hitResult));

        //if(interactionResult.shouldSwing())player.swing(event.getHand(),true);
        //if(interactionResult.consumesAction())event.getItemStack().consume(1, player);
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onBookStackBlockClicked(PlayerInteractEvent.RightClickBlock event){
        if(event.getSide().isClient())return;
        if(event.getEntity().isShiftKeyDown())return;
        if(event.getLevel().getBlockState(event.getPos()).is(BlockRegistry.BOOK_STACK)){
            Player player=event.getEntity();
            player.addItem(new ItemStack(Items.BOOK));
            player.getCooldowns().addCooldown(Items.BOOK,10);
            player.swing(event.getHand(),true);
            event.getLevel().destroyBlock(event.getPos(),false);
            event.setCancellationResult(InteractionResult.FAIL);
            event.setCanceled(true);
        }
    }
}
