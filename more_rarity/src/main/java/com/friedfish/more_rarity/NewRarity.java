package com.friedfish.more_rarity;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class NewRarity {
    public static final EnumProxy<Rarity> CUSTOM_RARITY_ENUM_PROXY = new EnumProxy<>(
            Rarity.class, -1, "more_rarity:custom", ChatFormatting.AQUA
    );
}
