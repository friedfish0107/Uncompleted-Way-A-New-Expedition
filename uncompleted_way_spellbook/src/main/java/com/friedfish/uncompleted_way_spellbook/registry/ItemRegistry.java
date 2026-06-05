package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.item.*;
import com.friedfish.uncompleted_way_spellbook.content.item.debug_item.DebugItem;
import com.friedfish.uncompleted_way_spellbook.content.item.weapon.StaffTiers;
import io.redspace.ironsspellbooks.api.item.curios.AffinityData;
import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.Map;

public class ItemRegistry {
    private static final DeferredRegister<Item> ITEMS=DeferredRegister.create(Registries.ITEM, UncompletedWaySpellbook.MODID);
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

    public static final DeferredHolder<Item,Item> DEBUG=ITEMS.register("debug",()->new DebugItem(
            new Item.Properties().stacksTo(1)
                    .component(ComponentRegistry.ENABLED.get(),false)
                    .component(ComponentRegistry.DEBUG_TYPE.get(), 0)));
    public static final DeferredHolder<Item,Item> STICK_STAFF=ITEMS.register("stick_staff",()->new TooltipStaffItem(
            new Item.Properties()
                    .stacksTo(1)
                    .attributes(ExtendedSwordItem.createAttributes(StaffTiers.STICK)),
            3
    ));
    public static final DeferredHolder<Item,Item> ZOMBIE_STAFF=ITEMS.register("zombie_staff",()->new TooltipMagicStaffItem(
            new Item.Properties()
                    .stacksTo(1)
                    .attributes(ExtendedSwordItem.createAttributes(StaffTiers.ZOMBIE_STAFF)),
            SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.RAISE_DEAD_SPELL,1))
            ,2
    ));
    public static final DeferredHolder<Item,Item> MAGIC_PAPER=ITEMS.register("magic_paper",()->new TooltipSpellBook(1,2));
    public static final DeferredHolder<Item,Item> ENCHANT_SPELL_BOOK=ITEMS.register("enchant_spell_book",()->new TooltipSpellBook(6));
    public static final DeferredHolder<Item,Item> OLD_SKULL_ZOMBIE=ITEMS.register("old_skull_zombie",()->new TooltipAffinityCuriosItem(
            new Item.Properties()
                    .stacksTo(1)
                    .component(io.redspace.ironsspellbooks.registries.ComponentRegistry.AFFINITY_COMPONENT,
                            new AffinityData(Map.of(
                                    ResourceLocation.fromNamespaceAndPath("irons_spellbooks","raise_dead"),1
                            ))))
            .withAttributes(CuriosSlotString.HEAD_SLOT,new AttributeContainer(Attributes.ARMOR,2, AttributeModifier.Operation.ADD_VALUE))
    );
    public static final DeferredHolder<Item,Item> MATTER=ITEMS.register("matter",()->new Item(new Item.Properties().stacksTo(99)));
    public static final DeferredHolder<Item,Item> QUICKLIME_DUST=ITEMS.register("quicklime_dust",()->new QuickLimeItem(new Item.Properties()));
    public static final DeferredHolder<Item,Item> QUICKLIME_DUST_PILE=ITEMS.register("quicklime_dust_pile",()->new TooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item,Item> SLAKED_LIME_DUST=ITEMS.register("slaked_lime_dust",()->new SlakedLimeItem(new Item.Properties()));
    public static final DeferredHolder<Item,Item> SLAKED_LIME_DUST_PILE=ITEMS.register("slaked_lime_dust_pile",()->new TooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item,Item> CALCIUM_CARBONATE_DUST=ITEMS.register("calcium_carbonate_dust",()->new TooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item,Item> CALCIUM_CARBONATE_DUST_PILE=ITEMS.register("calcium_carbonate_dust_pile",()->new TooltipItem(new Item.Properties()));
    public static Collection<DeferredHolder<Item, ? extends Item>> getAllUWSItem(){
        return ITEMS.getEntries();
    }
}