package com.friedfish.almost_one_attirbute.attribute;

import com.friedfish.almost_one_attirbute.AlmostOneAttribute;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AttributeRegistry {
    private static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(Registries.ATTRIBUTE, AlmostOneAttribute.MODID);
    public static void register(IEventBus eventBus){
        ATTRIBUTE.register(eventBus);
    }
    public static final DeferredHolder<Attribute,Attribute> DAMAGE_REDUCTION=ATTRIBUTE.register("damage_reduction",
            ()-> new PercentageAttribute("attribute"+AlmostOneAttribute.MODID+"damage_reduction",0.0,-1024,1024)
                    .setSyncable(true)
    );
    public static final DeferredHolder<Attribute,Attribute> DAMAGE_ADDITION=ATTRIBUTE.register("damage_addition",
            ()-> new PercentageAttribute("attribute"+AlmostOneAttribute.MODID+"damage_addition",1.0,-1024,1024)
                    .setSyncable(true)
    );
}
