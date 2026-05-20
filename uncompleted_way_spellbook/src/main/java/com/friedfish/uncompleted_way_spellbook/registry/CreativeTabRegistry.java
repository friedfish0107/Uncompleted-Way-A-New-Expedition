package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import io.redspace.ironsspellbooks.IronsSpellbooks;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeTabRegistry {
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UncompletedWaySpellbook.MODID);

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EQUIPMENT_TAB = TABS.register("uncompleted_way_spell_book", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + UncompletedWaySpellbook.MODID + ".uncompleted_way_spell_book_tab"))
            .icon(() -> new ItemStack(ItemRegistry.DEBUG.get()))
            .displayItems((enabledFeatures, entries) -> {
                ItemRegistry.getAllUWSItem()
                        .forEach(itemDeferredHolder -> entries.accept(itemDeferredHolder.get()));
            }).withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .build());
}
