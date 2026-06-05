package com.friedfish.uncompleted_way_spellbook.content.render;

import com.friedfish.uncompleted_way_spellbook.UncompletedWaySpellbook;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.joml.Matrix4f;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(Dist.CLIENT)
public class FloatTextRender {
    public static final FloatTextRender instance = new FloatTextRender();

    private record RenderInfo(Component component, Position position, Matrix4f transformation, long expiryTick,
                              int transTime, Matrix4f transTo) {
    }

    private static final Logger LOGGER = LogUtils.getLogger();
    List<RenderInfo> toRender = new ArrayList<>();

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Camera camera = event.getCamera();
        if (!camera.isInitialized()) return;

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();

        // 过期检查
        FloatTextRender.instance.toRender.removeIf(info -> mc.level.getGameTime() >= info.expiryTick);

        float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(!mc.isPaused());

        for (FloatTextRender.RenderInfo info : FloatTextRender.instance.toRender) {
            renderFloatingText(poseStack, bufferSource, info, partialTick);
        }

        bufferSource.endBatch();
    }

    private static void renderFloatingText(PoseStack poseStack, MultiBufferSource bufferSource,
                                           RenderInfo info, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.gameRenderer.getMainCamera();
        Font font = mc.font;

        // 使用 partialTick 计算插值因子
        double lerp = 0.0;
        if (info.transTime != 0) {
            double leftTime = (double) (info.expiryTick - mc.level.getGameTime()) - partialTick;
            lerp = 1.0F - leftTime / (double) info.transTime;
            lerp = Mth.clamp(lerp, 0.0, 1.0); // 确保范围
        }

        // 从初始变换到目标变换的插值矩阵
        Matrix4f currentTransform = smoothLerp(info.transformation, info.transTo, lerp);

        double dx = info.position.x() - camera.getPosition().x;
        double dy = info.position.y() - camera.getPosition().y;
        double dz = info.position.z() - camera.getPosition().z;

        poseStack.pushPose();
        poseStack.translate((float) dx, (float) dy, (float) dz);
        // 面向玩家
        poseStack.mulPose(camera.rotation());
        // 缩放与翻转
        float scale = 1.0F;
        poseStack.scale(scale, -scale, scale);
        // 应用插值后的自定义变换（不再叠加原始 transformation，避免双倍）
        poseStack.mulPose(currentTransform);

        int width = font.width(info.component);
        float xOffset = -width / 2.0F;

        font.drawInBatch(info.component, xOffset, 0, 0xFFFFFF, false,
                poseStack.last().pose(), bufferSource,
                Font.DisplayMode.NORMAL, 0, 15728880);

        poseStack.popPose();
    }

    public static void renderFloatText(Component component, Position position, Matrix4f transformation, long expiryTick) {
        instance.toRender.add(new RenderInfo(component, position, transformation, expiryTick, 0, transformation));
    }

    public static void renderFloatText(Component component, Position position, Matrix4f transformation, long expiryTick,
                                       int transTime, Matrix4f transTo) {
        instance.toRender.add(new RenderInfo(component, position, transformation, expiryTick, transTime, transTo));
    }

    private static Matrix4f smoothLerp(Matrix4f original, Matrix4f to, double lerp) {
        float u = (float) Mth.smoothstep(lerp);
        return new Matrix4f(
                Mth.lerp(u, original.m00(), to.m00()),
                Mth.lerp(u, original.m01(), to.m01()),
                Mth.lerp(u, original.m02(), to.m02()),
                Mth.lerp(u, original.m03(), to.m03()),
                Mth.lerp(u, original.m10(), to.m10()),
                Mth.lerp(u, original.m11(), to.m11()),
                Mth.lerp(u, original.m12(), to.m12()),
                Mth.lerp(u, original.m13(), to.m13()),
                Mth.lerp(u, original.m20(), to.m20()),
                Mth.lerp(u, original.m21(), to.m21()),
                Mth.lerp(u, original.m22(), to.m22()),
                Mth.lerp(u, original.m23(), to.m23()),
                Mth.lerp(u, original.m30(), to.m30()),
                Mth.lerp(u, original.m31(), to.m31()),
                Mth.lerp(u, original.m32(), to.m32()),
                Mth.lerp(u, original.m33(), to.m33())
        );
    }
}