package com.friedfish.extend_iss.mixin;

import com.friedfish.extend_iss.registries.ItemRegistry;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.InkItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InkItem.class)
public class InkItemMixin {
    @Inject(
            method = "Lio/redspace/ironsspellbooks/item/InkItem;getInkForRarity(Lio/redspace/ironsspellbooks/api/spells/SpellRarity;)Lio/redspace/ironsspellbooks/item/InkItem;",
            at=@At("HEAD"),
            cancellable = true
    )
    private static void modifyRarity(SpellRarity rarity, CallbackInfoReturnable<InkItem> cir){
        cir.setReturnValue((InkItem) ItemRegistry.ANCIENT_INK.value());
    }
}
