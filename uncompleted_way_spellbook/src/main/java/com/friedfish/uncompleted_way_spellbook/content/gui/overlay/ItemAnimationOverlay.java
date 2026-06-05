package com.friedfish.uncompleted_way_spellbook.content.gui.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Mth;
import net.minecraft.client.renderer.texture.TextureAtlas;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ItemAnimationOverlay implements LayeredDraw.Layer {
    private final Minecraft minecraft;
    public static final ItemAnimationOverlay instance = new ItemAnimationOverlay(Minecraft.getInstance());

    List<ActiveAnimation> animations = new ArrayList<>();
    List<SequencedAnimation> sequences = new ArrayList<>();

    private static final Logger LOGGER = LogUtils.getLogger();

    public ItemAnimationOverlay(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    // ========== 原有简单动画 API ==========
    public static void addRenderItem(ItemStack item, int durationTicks, float offX, float offY, AnimationStrategy strategy) {
        instance.animations.add(new ActiveAnimation(item,
                instance.minecraft.level.getGameTime() + durationTicks,
                durationTicks, offX, offY, strategy));
    }

    public static void addRenderItem(ItemStack item, long expiryTick, int durationTicks, float offX, float offY, AnimationStrategy strategy) {
        instance.animations.add(new ActiveAnimation(item, expiryTick, durationTicks, offX, offY, strategy));
    }

    public static void cleanExpiredAnimation() {
        instance.animations.removeIf(anim ->
                instance.minecraft.level.getGameTime() >= anim.expiryTick
        );
        instance.sequences.removeIf(seq -> seq.hasFinished(instance.minecraft.level.getGameTime()));
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        cleanExpiredAnimation();
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(!minecraft.isPaused());

        // 绘制简单动画
        for (ActiveAnimation anim : animations) {
            renderAnimation(guiGraphics, partialTick, anim);
        }

        // 绘制序列动画
        long gameTime = minecraft.level.getGameTime();
        for (SequencedAnimation seq : sequences) {
            seq.render(guiGraphics, partialTick, gameTime);
        }
    }

    private void renderAnimation(GuiGraphics guiGraphics, float partialTick, ActiveAnimation anim) {
        assert minecraft.level != null;
        long remainingTick = anim.expiryTick - minecraft.level.getGameTime();
        float progress = 1.0f - ((float) remainingTick - partialTick) / (float) anim.duration;
        progress = Mth.clamp(progress, 0.0f, 1.0f);

        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();

        anim.strategy.applyTransform(guiGraphics, poseStack, progress, anim.offX, anim.offY,
                guiGraphics.guiWidth(), guiGraphics.guiHeight(), 0, 0);

        guiGraphics.drawManaged(() -> {
            minecraft.getItemRenderer().renderStatic(
                    anim.item,
                    ItemDisplayContext.FIXED,
                    15728880,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    guiGraphics.bufferSource(),
                    minecraft.level,
                    0
            );
        });

        poseStack.popPose();
    }

    // ========== 序列动画 ==========
    public static void addSequence(SequencedAnimation sequence) {
        sequence.startTick = instance.minecraft.level.getGameTime();
        instance.sequences.add(sequence);
    }

    public static class SequencedAnimation {
        private final List<Phase> phases = new ArrayList<>();
        long startTick;

        public SequencedAnimation addPhase(Phase phase) {
            phases.add(phase);
            return this;
        }

        boolean hasFinished(long gameTime) {
            if (phases.isEmpty()) return true;
            Phase last = phases.get(phases.size() - 1);
            return gameTime >= startTick + last.delayTicks + last.duration;
        }

        void render(GuiGraphics guiGraphics, float partialTick, long gameTime) {
            for (Phase phase : phases) {
                long phaseStart = startTick + phase.delayTicks;
                long phaseEnd = phaseStart + phase.duration;
                if (gameTime >= phaseStart && gameTime < phaseEnd) {
                    long phaseElapsed = gameTime - phaseStart;
                    float progress = Mth.clamp((phaseElapsed + partialTick) / (float) phase.duration, 0.0f, 1.0f);

                    if (phase.renderable != null) {
                        // 自定义渲染（如岩浆）：直接使用 guiGraphics.pose() 进行变换
                        guiGraphics.pose().pushPose();
                        phase.strategy.applyTransform(guiGraphics, guiGraphics.pose(), progress,
                                phase.offX, phase.offY, guiGraphics.guiWidth(), guiGraphics.guiHeight(),
                                phaseElapsed, phase.duration);
                        phase.renderable.render(guiGraphics, Minecraft.getInstance());
                        guiGraphics.pose().popPose();
                    } else if (phase.item != null) {
                        // 物品渲染：使用独立 PoseStack
                        PoseStack poseStack = new PoseStack();
                        poseStack.pushPose();
                        phase.strategy.applyTransform(guiGraphics, poseStack, progress,
                                phase.offX, phase.offY, guiGraphics.guiWidth(), guiGraphics.guiHeight(),
                                phaseElapsed, phase.duration);
                        guiGraphics.drawManaged(() -> {
                            Minecraft.getInstance().getItemRenderer().renderStatic(
                                    phase.item,
                                    ItemDisplayContext.FIXED,
                                    15728880,
                                    OverlayTexture.NO_OVERLAY,
                                    poseStack,
                                    guiGraphics.bufferSource(),
                                    Minecraft.getInstance().level,
                                    0
                            );
                        });
                        poseStack.popPose();
                    }
                }
            }
        }
    }

    public static class Phase {
        final int delayTicks;
        final int duration;
        final ItemStack item;
        final Renderable renderable;
        final float offX, offY;
        final AnimationStrategy strategy;

        private Phase(Builder builder) {
            this.delayTicks = builder.delayTicks;
            this.duration = builder.duration;
            this.item = builder.item;
            this.renderable = builder.renderable;
            this.offX = builder.offX;
            this.offY = builder.offY;
            this.strategy = builder.strategy;
        }

        public static class Builder {
            int delayTicks = 0;
            int duration = 20;
            ItemStack item = null;
            Renderable renderable = null;
            float offX = 0, offY = 0;
            AnimationStrategy strategy = (guiGraphics, pose, p, offX, offY, w, h, elapsed, duration) -> {};

            public Builder delay(int ticks) { this.delayTicks = ticks; return this; }
            public Builder duration(int ticks) { this.duration = ticks; return this; }
            public Builder item(ItemStack stack) { this.item = stack; return this; }
            public Builder customRender(Renderable renderable) { this.renderable = renderable; return this; }
            public Builder offset(float x, float y) { this.offX = x; this.offY = y; return this; }
            public Builder strategy(AnimationStrategy s) { this.strategy = s; return this; }
            public Phase build() { return new Phase(this); }
        }
    }

    @FunctionalInterface
    public interface Renderable {
        void render(GuiGraphics guiGraphics, Minecraft minecraft);
    }

    @FunctionalInterface
    public interface AnimationStrategy {
        void applyTransform(GuiGraphics guiGraphics, PoseStack poseStack, float progress,
                            float offX, float offY, int screenWidth, int screenHeight,
                            long elapsed, int duration);
    }

    // ========== 预设策略 ==========
    // (保留原来的所有策略，此处略去以节省篇幅，实际使用时需保留)
    // ... 你的 ORIGINAL_BOUNCE、FLOAT_UP 等 ...

    // ========== 冶炼动画 ==========
    public static void playSmithingAnimation(ItemStack oldItem, ItemStack newItem) {
        SequencedAnimation seq = new SequencedAnimation();
        float startOffX = 1.3f;
        float startOffY = 1.3f;

        // Phase 1: 旧物品飞出拉回 + 静止 (0 ~ 32 tick)
        seq.addPhase(new Phase.Builder()
                .delay(0)
                .duration(32)
                .item(oldItem)
                .offset(startOffX, startOffY)
                .strategy((gui, pose, progress, offX, offY, w, h, elapsed, duration) -> {
                    float centerX = w / 2.0f;
                    float centerY = h / 2.0f;
                    float x0 = offX * w / 4.0f;
                    float y0 = offY * h / 4.0f;
                    float xExt = -0.12f * w;   // 减小偏移
                    float yExt = -0.05f * h;

                    float px, py, scale, rot;
                    if (elapsed < 10) {
                        float u = elapsed / 10.0f;
                        float alpha = u * u;
                        px = centerX + x0 + (xExt - x0) * alpha;
                        py = centerY + y0 + (yExt - y0) * alpha;
                        scale = 48 - 8 * u;
                        rot = 15f * Mth.sin((float) Math.PI * u);
                    } else if (elapsed < 22) {
                        float v = (elapsed - 10) / 12.0f;
                        float beta = 1 - (1 - v) * (1 - v);
                        px = centerX + xExt + (0 - xExt) * beta;
                        py = centerY + yExt + (0 - yExt) * beta;
                        scale = 40 + 24 * v + 16 * Mth.sin((float) Math.PI * v);
                        rot = -15f * Mth.sin((float) Math.PI * v);
                    } else {
                        px = centerX;
                        py = centerY;
                        scale = 64;
                        rot = 0;
                    }
                    pose.translate(px, py, -50);
                    pose.scale(scale, -scale, scale);
                    if (rot != 0) {
                        pose.mulPose(Axis.XP.rotationDegrees(rot));
                        pose.mulPose(Axis.ZP.rotationDegrees(rot));
                    }
                })
                .build());

        // Phase 2: 岩浆纹理动画 (27 ~ 70 tick)
        ResourceLocation lavaSprite = ResourceLocation.withDefaultNamespace("block/lava_still");
        seq.addPhase(new Phase.Builder()
                .delay(27)
                .duration(43)
                .customRender((gui, mc) -> {
                    TextureAtlasSprite sprite = mc.getModelManager()
                            .getAtlas(TextureAtlas.LOCATION_BLOCKS)
                            .getSprite(lavaSprite);
                    // 当前岩浆尺寸已在 strategy 的平移中设置好，直接绘制 1x1 矩形
                    gui.blit(0, 0, 0, 1, 1, sprite); // 使用第一个 blit 重载 (x,y,blitOffset,w,h,sprite)
                })
                .strategy((gui, pose, progress, offX, offY, w, h, elapsed, duration) -> {
                    float centerX = w / 2.0f;
                    float centerY = h / 2.0f;
                    float itemHalfHeight = 32.0f;  // 物品 scale=64 时的半高
                    float finalMagmaHeight = 76.8f; // 1.2 * 64
                    float topY_full = centerY - itemHalfHeight - 1.6f;

                    float currentHeight, currentTopY;
                    if (elapsed < 5) {
                        // 展开弹回
                        float u = elapsed / 5.0f;
                        float scaleFactor = 1.15f * u + 0.15f * Mth.sin(2 * (float) Math.PI * u);
                        currentHeight = finalMagmaHeight * scaleFactor;
                        currentTopY = topY_full; // 顶部固定
                    } else if (elapsed < 33) {
                        // 停留
                        currentHeight = finalMagmaHeight;
                        currentTopY = topY_full;
                    } else if (elapsed < 36) {
                        // 收缩到底部
                        float u = (elapsed - 33) / 3.0f;
                        float alpha = u * u;
                        currentHeight = finalMagmaHeight + (1.0f - finalMagmaHeight) * alpha;
                        currentTopY = topY_full + (centerY + itemHalfHeight - topY_full) * alpha;
                    } else {
                        // 收缩后静止
                        currentHeight = 1.0f;
                        currentTopY = centerY + itemHalfHeight;
                    }
                    float leftX = centerX - finalMagmaHeight / 2.0f; // 宽度保持不变
                    pose.translate(leftX, currentTopY, -45); // Z 比物品更靠前
                    pose.scale(finalMagmaHeight, currentHeight, 1);
                })
                .build());

        // Phase 3: 新物品飞回 (32 ~ 100 tick)
        seq.addPhase(new Phase.Builder()
                .delay(32)
                .duration(68)
                .item(newItem)
                .offset(0, 0)
                .strategy((gui, pose, progress, offX, offY, w, h, elapsed, duration) -> {

                    float centerX = w / 2.0f;
                    float centerY = h / 2.0f;
                    float targetX = startOffX * w / 4.0f;
                    float targetY = startOffY * h / 4.0f;
                    float u = Mth.clamp((float) (elapsed-38) / 30.0f, 0.0f, 1.0f);
                    float alpha = u < 0.5f ? 2 * u * u : 1 - (float) Math.pow(-2 * u + 2, 2) / 2;
                    float x = alpha * targetX;
                    float y = alpha * targetY;
                    pose.translate(centerX + x, centerY + y, -50);
                    float scale = 64 + 8 * alpha + 8 * Mth.sin((float) Math.PI * alpha);
                    pose.scale(scale, -scale, scale);
                    float rot = 5 * Mth.sin((float) Math.PI * u);
                    pose.mulPose(Axis.ZP.rotationDegrees(rot));
                })
                .build());

        addSequence(seq);
    }

    // 内部记录类
    private record ActiveAnimation(ItemStack item, long expiryTick, int duration, float offX, float offY,
                                   AnimationStrategy strategy) {}
}