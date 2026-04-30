package com.friedfish.uncompleted_way_spellbook.core.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

import static com.friedfish.uncompleted_way_spellbook.registry.DataAttachmentRegistry.ADVENTURE_INVENTORY;

public class AdventureInventoryHelper {
    public static void swapInventory(Player player){
        ItemStackHandler data=player.getData(ADVENTURE_INVENTORY);
        Inventory inventory=player.getInventory();
        for(int i=0;i<data.getSlots();i++){
            ItemStack stack=data.getStackInSlot(i);
            data.setStackInSlot(i,inventory.getItem(i));
            inventory.setItem(i,stack);
        }
    }
}
