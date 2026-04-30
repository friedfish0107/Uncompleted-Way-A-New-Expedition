package com.friedfish.more_rarity.Mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Rarity.class)
public class RarityMixin {

    @ModifyArgs(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Rarity;<init>(Ljava/lang/String;IILjava/lang/String;Lnet/minecraft/ChatFormatting;)V")
    )
    private static void modifyRareColor(Args args) {
        String name = args.get(3);
        if ("rare".equals(name)) {
            args.set(4, ChatFormatting.GREEN);
        }
    }
}
