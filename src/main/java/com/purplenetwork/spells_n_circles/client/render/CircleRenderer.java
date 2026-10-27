package com.purplenetwork.spells_n_circles.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.purplenetwork.spells_n_circles.SpellsNCircles;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class CircleRenderer {

    public static void render(ResourceLocation texture, int radius,
                              PoseStack poseStack, MultiBufferSource bufferSource) {
        poseStack.pushPose();
        radius = Math.max(Math.min(radius, SpellsNCircles.circleMaxSize), SpellsNCircles.circleMinSize);
        // limiting circle radius
        try {
            poseStack.mulPose(new org.joml.Quaternionf().rotationY(0));
            poseStack.translate(0, 0.3, 0);

            float rotationAngle = (float) ((float) Math.sin((2 * Math.PI / 80000) * System.currentTimeMillis()) * 1.8);
            poseStack.mulPose(new org.joml.Quaternionf().rotationY(rotationAngle));

            Matrix4f matrix = poseStack.last().pose();
            RenderType renderType = RenderType.entityTranslucentEmissive(texture, true);
            VertexConsumer consumer = bufferSource.getBuffer(renderType);


            consumer.addVertex(matrix, -radius, 0f,  radius).setColor(255, 255, 255, 255).setUv(0f, 1f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(240, 240).setNormal(0, 1, 0);
            consumer.addVertex(matrix,  radius, 0f,  radius).setColor(255, 255, 255, 255).setUv(1f, 1f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(240, 240).setNormal(0, 1, 0);
            consumer.addVertex(matrix,  radius, 0f, -radius).setColor(255, 255, 255, 255).setUv(1f, 0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(240, 240).setNormal(0, 1, 0);
            consumer.addVertex(matrix, -radius, 0f, -radius).setColor(255, 255, 255, 255).setUv(0f, 0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(240, 240).setNormal(0, 1, 0);

            if (bufferSource instanceof MultiBufferSource.BufferSource bs) {
                bs.endBatch(renderType);
            }
            poseStack.translate(0, 0.1, 0);

        } finally {
            poseStack.popPose(); // pop
        }
    }
}

