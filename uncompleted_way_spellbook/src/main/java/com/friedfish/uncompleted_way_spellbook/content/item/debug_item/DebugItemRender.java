package com.friedfish.uncompleted_way_spellbook.content.item.debug_item;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.friedfish.uncompleted_way_spellbook.content.render.FloatTextRender;
import com.friedfish.uncompleted_way_spellbook.registry.ItemRegistry;
import com.mojang.blaze3d.platform.Window;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.IItemDecorator;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.slf4j.Logger;

@EventBusSubscriber(modid = UncompletedWaySpellbook.MODID)
public class DebugItemRender {

    private static final Logger LOGGER= LogUtils.getLogger();

    @SubscribeEvent
    public static void RegisterItemDecorations(RegisterItemDecorationsEvent event){
        event.register(ItemRegistry.DEBUG.get(), new IItemDecorator() {
            @Override
            public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate(0,0,200);
                guiGraphics.drawString(font, String.valueOf(DebugItem.getDebugType(stack)),xOffset,yOffset-1,-1);

                if(DebugItem.getDebugType(stack)==4){
                    guiGraphics.pose().scale(0.5F,0.5F,0.5F);
                    guiGraphics.renderFakeItem(stack,xOffset*2+16,yOffset*2);
                    guiGraphics.renderFakeItem(Items.ACACIA_LOG.getDefaultInstance(),xOffset*2,yOffset*2+16);
                }
                guiGraphics.pose().popPose();
                return false;
            }
        });
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void LivingDamageEventListener(LivingDamageEvent.Post event){
        if(event.getSource().getDirectEntity() instanceof Player player) {
            if(player.getInventory().contains(itemStack -> DebugItem.isEnabled(itemStack)&&DebugItem.getDebugType(itemStack)==4)) {

                Minecraft minecraft=Minecraft.getInstance();
                Camera camera = minecraft.gameRenderer.getMainCamera();
                Level level=player.level();
                LivingEntity entity=event.getEntity();

                Vec3 lookVec = player.getLookAngle();
                Vec3 pos = entity.getEyePosition()
                        .add(lookVec.scale(-1))
                        .add(new Vec3(camera.getUpVector()).scale(0.6))
                        .add(new Vec3(camera.getLeftVector()).scale(-0.6));



                float scale  = Mth.nextFloat(level.random,0.02f,0.05f);
                float rotate1 = Mth.PI*(level.random.nextFloat()+level.random.nextFloat()-1)/4.0F;
                float rotate2 = Mth.PI*(level.random.nextFloat()+level.random.nextFloat()-1)/8.0F;



                Matrix4f transformation=new Matrix4f()
                        //.rotate(camera.rotation())
                        .rotate(camera.rotation().rotationAxis(rotate1,new Vector3f(0,0,1)))
                        /*.rotate(camera.rotation().rotationAxis(rotate2,new Vector3f(0,1,0)))*/
                        .scale(scale,scale,scale);
                //.rotate(new AxisAngle4f(90f,)));

                Matrix4f original = new Matrix4f(transformation);  // 原始缩放（0.02~0.05）
                Matrix4f target = new Matrix4f(transformation).translate(0,scale/2,0).scale(15f); // 独立副本，放大2倍


                FloatTextRender.renderFloatText(Component.literal(String.valueOf(event.getNewDamage())), pos, original, level.getGameTime() + 60,60,target);
            }
        }
    }
    private static float warpTime = 0f;      // 动画进度 0→1→0
    private static boolean warping = false;
    public static void startWarp() {
        warping = true;
        warpTime = 0f;
    }
    @SubscribeEvent
    public static void onRenderLevel(ViewportEvent.ComputeFov event) {
        // 只在世界渲染阶段（不包括手和UI）应用效果
        //if (event.getStage() != RenderLevelStageEvent.Stage.) return;
        if (!warping) return;

        // 更新动画进度
        warpTime += (float) event.getPartialTick();// 每帧增加
        float t = warpTime / 2000f;            // 持续20 tick
        if (t >= 1f) {
            warping = false;                 // 动画结束
            return;
        }

        // 缩放因子：开始为1，中间最小（0.2），最后回到1
        float f2=Mth.sin(Mth.PI*t);
        float lerp = f2*f2*f2; // 平滑曲线
        // 或者光速飞船效果：从1迅速降到0.2再弹回
        // scale = t < 0.3f ? 1 - t / 0.3f * 0.8f : 0.2f + (t - 0.3f) / 0.7f * 0.8f;

        event.setFOV(Mth.lerp(lerp,event.getFOV(),180));
    }
}
