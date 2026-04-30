package com.friedfish.extend_iss.registries;

import com.friedfish.extend_iss.ExtendISS;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.InkItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS=DeferredRegister.create(Registries.ITEM, ExtendISS.MODID);
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
    /**
     * 新墨水
     */
    public static final DeferredHolder<Item,Item> ANCIENT_INK=ITEMS.register("ancient_ink",()->new InkItem(SpellRarity.EPIC, FluidRegistry.ANCIENT_INK));
}
