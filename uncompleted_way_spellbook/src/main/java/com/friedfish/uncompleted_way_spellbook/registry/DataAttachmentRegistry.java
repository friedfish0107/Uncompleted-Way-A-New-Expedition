package com.friedfish.uncompleted_way_spellbook.registry;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class DataAttachmentRegistry {
    // Create the DeferredRegister for attachment types
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, UncompletedWaySpellbook.MODID);

    public static void register(IEventBus eventBus) {
            ATTACHMENT_TYPES.register(eventBus);
        }
    // Serialization via ValueIOSerializable
    public static final Supplier<AttachmentType<ItemStackHandler>> ADVENTURE_INVENTORY = ATTACHMENT_TYPES.register(
            "adventure_inventory", () -> AttachmentType.serializable(() -> new ItemStackHandler(36)).copyOnDeath().build()
    );
}
