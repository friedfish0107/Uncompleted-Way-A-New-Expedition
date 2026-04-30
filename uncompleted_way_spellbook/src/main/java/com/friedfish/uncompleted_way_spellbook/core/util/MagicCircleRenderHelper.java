package com.friedfish.uncompleted_way_spellbook.core.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class MagicCircleRenderHelper {
    public static void renderSquareBorder(Vec3 pos, float radius, ResourceLocation borderResource, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick,long gameTime){
        //此时的poseStack已经有了实体坐标，无需改位置
        //其中normal为空，pose无变换，但有相对位置的translation(单位：block)
        RenderType renderType = RenderType.entityTranslucent(borderResource, false);
        VertexConsumer consumer = bufferSource.getBuffer(renderType);

        float borderWidth = 1f; // 边框厚度（视觉宽度）
        float length = 2 * radius; // 边长
        float texScrollSpeed = 0.025f; // 滚动速度（单位：纹理循环/秒）

        float offset = (gameTime + partialTick) * texScrollSpeed;
        offset = offset - (float)Math.floor(offset);

        for (int i = 0; i < 4; i++) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(i * 90));
            renderBeam(consumer, poseStack.last(), packedLight,
                    -radius, radius,
                    0, borderWidth,
                    radius,
                    length, offset);
            poseStack.popPose();
        }
    }

    private static void renderBeam(VertexConsumer consumer, PoseStack.Pose pose, int packedLight,
                                   float xMin, float xMax, float yMin, float yMax, float z,
                                   float length, float texOffset) {
        // 四个角点（逆时针顺序，面向 +Z）
        // 左下 (xMin, yMin, z)
        // 左上 (xMin, yMax, z)
        // 右上 (xMax, yMax, z)
        // 右下 (xMax, yMin, z)
        float x0 = xMin, y0 = yMin, z0 = z;
        float x1 = xMin, y1 = yMax, z1 = z;
        float x2 = xMax, y2 = yMax, z2 = z;
        float x3 = xMax, y3 = yMin, z3 = z;

        // 纹理坐标：U 沿 X 方向（长度方向），V 沿 Y 方向（高度/厚度方向）
        // U 范围：0 到 length / (textureRepeatLength) ，假设纹理每单位长度对应 1 个重复
        float uMax = length; // 如果纹理设计为每格 1 个方块，则 U 最大值为 length
        float vMin = 0, vMax = 1; // V 方向固定 0-1，纹理拉伸

        // 加上偏移
        float u0 = 0 + texOffset;
        float u1 = 0 + texOffset;
        float u2 = uMax + texOffset;
        float u3 = uMax + texOffset;

        // 使用 addVertex 添加 4 个顶点（QUADS 模式）
        addVertex(consumer, pose, packedLight, x0, y0, z0, u0, vMin, 0, 0, 1); // 法线 +Z
        addVertex(consumer, pose, packedLight, x1, y1, z1, u1, vMax, 0, 0, 1);
        addVertex(consumer, pose, packedLight, x2, y2, z2, u2, vMax, 0, 0, 1);
        addVertex(consumer, pose, packedLight, x3, y3, z3, u3, vMin, 0, 0, 1);
    }

    private static void addQuad(Quad quad, Direction direction,VertexConsumer consumer, PoseStack.Pose pose, int packedLight){
        if(direction.equals(Direction.UP)){
            addVertex(consumer,pose,packedLight, quad.x1, 0, quad.y1, 0,0);
            addVertex(consumer,pose,packedLight, quad.x2, 0, quad.y2, 0,1);
            addVertex(consumer,pose,packedLight, quad.x4, 0, quad.y4, 1,1);
            addVertex(consumer,pose,packedLight, quad.x3, 0, quad.y3, 1,0);
        }
    }
    private static void addQuad(Quad quad,VertexConsumer consumer, PoseStack.Pose pose, int packedLight){
        addQuad(quad,Direction.UP,consumer,pose,packedLight);
    }

    private static void addVertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight,
                                  float x, float y, float z,
                                  float u, float v
    ){
        addVertex(consumer,pose,packedLight,x,y,z,u,v,0,1,0);
    }
    private static void addVertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight,
                                  float x, float y, float z,
                                  float u, float v,
                                  float nx, float ny, float nz
    ){
        consumer.addVertex(pose,x,y,z)
                .setColor(-1)
                .setUv(u,v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(nx,ny,nz);
    }

    public static void renderIcon(Vec3 pos, float scale, ResourceLocation iconTextureLocation, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Quaternionf cameraQuat) {

        RenderType renderType = RenderType.entityTranslucent(iconTextureLocation, false);
        VertexConsumer consumer = bufferSource.getBuffer(renderType);
        poseStack.pushPose();

        poseStack.translate(0,10,0);

        float x1= -scale/2;
        float x2= scale/2;
        float z1= -scale/2;
        float z2= scale/2;

        poseStack.mulPose(cameraQuat);
        poseStack.scale(1,-1,1);

        addVertex(consumer,poseStack.last(),packedLight,x1,z1,0,0,0);
        addVertex(consumer,poseStack.last(),packedLight,x1,z2,0,0,1);
        addVertex(consumer,poseStack.last(),packedLight,x2,z2,0,1,1);
        addVertex(consumer,poseStack.last(),packedLight,x2,z1,0,1,0);

        poseStack.popPose();
    }

    @OnlyIn(Dist.CLIENT)
    public static final class Quad{
        float x1;
        float y1;
        float x2;
        float y2;
        float x3;
        float y3;
        float x4;
        float y4;
        float u0;
        float v0;
        float u1;
        float v1;

        public Quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, float u0, float v0, float u1, float v1) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.x3 = x3;
            this.y3 = y3;
            this.x4 = x4;
            this.y4 = y4;
            this.u0 = u0;
            this.v0 = v0;
            this.u1 = u1;
            this.v1 = v1;
        }

        public static Quad createBySquare(float x1,float y1,float x2,float y2){
            return new Quad(
                    x1,y1,
                    x1,y2,
                    x2,y1,
                    x2,y2,
                    0,0,
                    1,1
            );
        }
    }
}
