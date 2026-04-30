package com.friedfish.extend_iss.registries;

import com.friedfish.extend_iss.ExtendISS;
import io.redspace.ironsspellbooks.fluids.NoopFluid;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FluidRegistry {
    public static final DeferredRegister<Fluid> FLUIDS=DeferredRegister.create(Registries.FLUID, ExtendISS.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES=DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, ExtendISS.MODID);
    public static void register(IEventBus eventBus){
        FLUIDS.register(eventBus);
        FLUID_TYPES.register(eventBus);
    }
    /**
     * 新墨水液体类型
     */
    public static final DeferredHolder<FluidType,FluidType> ANCIENT_INK_TYPE=FLUID_TYPES.register("ancient_ink",()->new FluidType(FluidType.Properties.create()));

    /**
     * 新墨水液体
     */
    public static final DeferredHolder<Fluid, NoopFluid> ANCIENT_INK=registerNoop("ancient_ink",ANCIENT_INK_TYPE::value);

    private static DeferredHolder<Fluid, NoopFluid> registerNoop(String name, Supplier<FluidType> fluidType) {
        DeferredHolder<Fluid, NoopFluid> holder = DeferredHolder.create(Registries.FLUID, ExtendISS.id(name));
        BaseFlowingFluid.Properties properties = new BaseFlowingFluid.Properties(fluidType, holder::value, holder::value).bucket(() -> Items.AIR);
        FLUIDS.register(name, () -> new NoopFluid(properties));
        return holder;
    }
}
