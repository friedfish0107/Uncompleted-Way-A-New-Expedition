package com.friedfish.uncompleted_way_spellbook.content.render;

import com.friedfish.uncompleted_way_spellbook.misc.LogFlags;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.model.data.ModelData;

@EventBusSubscriber(Dist.CLIENT)
public class NullLevelBlockRenderer {
    @SubscribeEvent
    public static void onRenderEvent(RenderLevelStageEvent event){
        /*
        //RenderSystem.setShaderLights();


        if(event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES)return;

        Minecraft minecraft = Minecraft.getInstance();
        BlockRenderDispatcher blockRenderer = minecraft.getBlockRenderer();
        ItemRenderer itemRenderer = minecraft.getItemRenderer();
        //ModelRen
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource bufferSource=minecraft.renderBuffers().bufferSource();
        BakedModel itemModel = itemRenderer.getModel(Items.RAW_IRON.getDefaultInstance(),null,null,0);
        TextureAtlasSprite sprite=itemModel.getParticleIcon(ModelData.EMPTY);

        int colorRGBA=sprite.getPixelRGBA(0,8,8);

        RenderType renderType=RenderType.TRANSLUCENT;
        VertexConsumer consumer = bufferSource.getBuffer(renderType);

        LogFlags.onceLog("use color:"+colorRGBA);

        poseStack.pushPose();

        poseStack.translate(2,0,2);

        float SIZE=0.625f;

// 北面 (Z = 0)
        consumer.addVertex(poseStack.last(), 0, 0, 0).setColor(colorRGBA).setUv(0, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, -1);
        consumer.addVertex(poseStack.last(),SIZE, 0, 0).setColor(colorRGBA).setUv(1, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, -1);
        consumer.addVertex(poseStack.last(),SIZE, SIZE, 0).setColor(colorRGBA).setUv(1, 1).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, -1);
        consumer.addVertex(poseStack.last(),0, SIZE, 0).setColor(colorRGBA).setUv(0, 1).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, -1);

// 南面 (Z = SIZE)
        consumer.addVertex(poseStack.last(),SIZE, 0, SIZE).setColor(colorRGBA).setUv(1, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, 1);
        consumer.addVertex(poseStack.last(),0, 0, SIZE).setColor(colorRGBA).setUv(0, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, 1);
        consumer.addVertex(poseStack.last(),0, SIZE, SIZE).setColor(colorRGBA).setUv(0, 1).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, 1);
        consumer.addVertex(poseStack.last(),SIZE, SIZE, SIZE).setColor(colorRGBA).setUv(1, 1).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 0, 1);

// 西面 (X = 0)
        consumer.addVertex(poseStack.last(),0, 0, SIZE).setColor(colorRGBA).setUv(0, 1).setLight(LightTexture.FULL_BRIGHT).setNormal(-1, 0, 0);
        consumer.addVertex(poseStack.last(),0, 0, 0).setColor(colorRGBA).setUv(0, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(-1, 0, 0);
        consumer.addVertex(poseStack.last(),0, SIZE, 0).setColor(colorRGBA).setUv(1, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(-1, 0, 0);
        consumer.addVertex(poseStack.last(),0, SIZE, SIZE).setColor(colorRGBA).setUv(1, 1).setLight(LightTexture.FULL_BRIGHT).setNormal(-1, 0, 0);

// 东面 (X = SIZE)
        consumer.addVertex(poseStack.last(),SIZE, 0, 0).setColor(colorRGBA).setUv(0, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(1, 0, 0);
        consumer.addVertex(poseStack.last(),SIZE, 0, SIZE).setColor(colorRGBA).setUv(0, 1).setLight(LightTexture.FULL_BRIGHT).setNormal(1, 0, 0);
        consumer.addVertex(poseStack.last(),SIZE, SIZE, SIZE).setColor(colorRGBA).setUv(1, 1).setLight(LightTexture.FULL_BRIGHT).setNormal(1, 0, 0);
        consumer.addVertex(poseStack.last(),SIZE, SIZE, 0).setColor(colorRGBA).setUv(1, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(1, 0, 0);

// 上面 (Y = SIZE)
        consumer.addVertex(poseStack.last(),0, SIZE, 0).setColor(colorRGBA).setUv(0, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 1, 0);
        consumer.addVertex(poseStack.last(),SIZE, SIZE, 0).setColor(colorRGBA).setUv(1, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 1, 0);
        consumer.addVertex(poseStack.last(),SIZE, SIZE, SIZE).setColor(colorRGBA).setUv(1, 1).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 1, 0);
        consumer.addVertex(poseStack.last(),0, SIZE, SIZE).setColor(colorRGBA).setUv(0, 1).setLight(LightTexture.FULL_BRIGHT).setNormal(0, 1, 0);

// 下面 (Y = 0)
        consumer.addVertex(poseStack.last(),0, 0, SIZE).setColor(colorRGBA).setUv(0, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(0, -1, 0);
        consumer.addVertex(poseStack.last(),SIZE, 0, SIZE).setColor(colorRGBA).setUv(0, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(0, -1, 0);
        consumer.addVertex(poseStack.last(),SIZE, 0, 0).setColor(colorRGBA).setUv(0, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(0, -1, 0);
        consumer.addVertex(poseStack.last(),0, 0, 0).setColor(colorRGBA).setUv(0, 0).setLight(LightTexture.FULL_BRIGHT).setNormal(0, -1, 0);
        poseStack.translate(0,0,3);

        minecraft.getBlockRenderer().renderSingleBlock(Blocks.DIRT.defaultBlockState(),poseStack,bufferSource, LightTexture.FULL_BRIGHT,OverlayTexture.NO_OVERLAY,ModelData.EMPTY,renderType);

        poseStack.popPose();

*/
    }
}
