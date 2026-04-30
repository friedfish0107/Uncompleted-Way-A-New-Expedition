package com.friedfish.uncompleted_way_spellbook.content.item.debug_item;

import com.friedfish.uncompleted_way_spellbook.content.render.FloatTextRender;
import com.friedfish.uncompleted_way_spellbook.core.util.AdventureInventoryHelper;
import com.friedfish.uncompleted_way_spellbook.registry.ComponentRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.slf4j.Logger;

import java.util.List;

public class DebugItem extends Item {
    private int debugType;
    public static final int DEBUG_TYPE_COUNT=13;
    private static final Logger LOGGER= LogUtils.getLogger();


    /**
     * 0. 物品渲染测试<p>
     * 1. 聊天栏渲染测试1<p>
     * 2. 聊天栏渲染测试2<p>
     * 3. 悬浮文本控制器渲染测试<p>
     * 4. 伤害数字显示测试<p>
     * 5. 聊天栏渲染测试3<p>
     */


    public DebugItem(Properties properties) {
        super(properties);
    }

    public static boolean getEnabled(ItemStack itemStack){
        return itemStack.getOrDefault(ComponentRegistry.ENABLED,false);
    }

    public static void setEnabled(ItemStack itemStack,boolean enabled){
        itemStack.set(ComponentRegistry.ENABLED,enabled);
    }

    public static void toggleEnabled(ItemStack itemStack){
        setEnabled(itemStack,!getEnabled(itemStack));
    }

    public static int getDebugType(ItemStack itemStack){
        return itemStack.getOrDefault(ComponentRegistry.DEBUG_TYPE,0);
    }

    public static void setDebugType(ItemStack itemStack,int debugType){
        itemStack.set(ComponentRegistry.DEBUG_TYPE,debugType);
    }

    public static void changeDebugType(ItemStack itemStack){
        int debugType=getDebugType(itemStack);

        setDebugType(itemStack,(debugType>=DEBUG_TYPE_COUNT-1)?0:debugType+1);

    }

    public static boolean isEnabled(ItemStack itemStack){return getEnabled(itemStack);}

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack=player.getItemInHand(usedHand);

        if(level.isClientSide){
            if(isEnabled(itemStack)&&getDebugType(itemStack)==3){
                Minecraft minecraft=Minecraft.getInstance();
                Camera camera = minecraft.gameRenderer.getMainCamera();

                Vec3 lookVec = player.getLookAngle();
                Vec3 pos = player.getEyePosition()  // 使用眼睛位置，更精确
                        .add(lookVec.scale(1))
                        .add(new Vec3(camera.getUpVector()).scale(-0.25));


                float scale  = Mth.nextFloat(level.random,0.02f,0.05f);
                float rotate1 = Mth.PI*(level.random.nextFloat()+level.random.nextFloat()-1)/4.0F;
                float rotate2 = Mth.PI*(level.random.nextFloat()+level.random.nextFloat()-1)/4.0F;



                Matrix4f transformation=new Matrix4f()
                        .rotate(camera.rotation())
                        .rotate(camera.rotation().rotationAxis(rotate1,new Vector3f(0,0,1)))
                        .rotate(camera.rotation().rotationAxis(rotate2,new Vector3f(0,1,0)))
                        .scale(scale,scale,scale);
                        //.rotate(new AxisAngle4f(90f,)));


                FloatTextRender.renderFloatText(Component.literal("FRONT"), pos, transformation, level.getGameTime() + 100);
                return InteractionResultHolder.success(itemStack);
            }
            if(isEnabled(itemStack)&&getDebugType(itemStack)==5){
                Minecraft.getInstance().gui.getChat().addMessage(Component.literal("test"));
            }
            return InteractionResultHolder.pass(itemStack);
        }
        else{
            if(isEnabled(itemStack)&&getDebugType(itemStack)==6){
                AdventureInventoryHelper.swapInventory(player);
            }
        }


        if(player.isShiftKeyDown()){
            toggleEnabled(itemStack);
            return InteractionResultHolder.success(itemStack);
        }
        else {
            if(isEnabled(itemStack)){
                return InteractionResultHolder.pass(itemStack);
            }
            else{
                changeDebugType(itemStack);
                player.displayClientMessage(Component.translatable("item.uncompleted_way_spellbook.debug.debugtype",getDebugType(itemStack)).withStyle(ChatFormatting.GREEN),true);
                return InteractionResultHolder.success(itemStack);
            }
        }
    }
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        return;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.uncompleted_way_spellbook.debug.type"+ getDebugType(stack)).withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public int getBarWidth(ItemStack itemStack) {
        return Math.round((float)getDebugType(itemStack) *13.0F /DEBUG_TYPE_COUNT);
    }

    @Override
    public int getBarColor(ItemStack itemStack) {
        return 0x00FF00;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }
}
