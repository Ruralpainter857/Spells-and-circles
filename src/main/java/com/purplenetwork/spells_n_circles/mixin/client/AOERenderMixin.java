package com.purplenetwork.spells_n_circles.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.purplenetwork.spells_n_circles.Helper;
import com.purplenetwork.spells_n_circles.SpellsNCircles;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.target_area.TargetAreaRenderer;
import io.redspace.ironsspellbooks.entity.spells.target_area.TargetedAreaEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TargetAreaRenderer.class, remap = false)
public class AOERenderMixin {

    @Inject(method = "render*", at = @At("HEAD"), cancellable = true)
    public void render(TargetedAreaEntity entity, float pEntityYaw, float pPartialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int light, CallbackInfo ci) {
        ci.cancel();
        poseStack.pushPose();

        try {
            float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);

            poseStack.mulPose(new org.joml.Quaternionf().rotationY(0));
            poseStack.translate(0, 0.3, 0);

            float rotationAngle = (float) ((float) Math.sin((2 * Math.PI / 80000) * System.currentTimeMillis()) * 1.8);
            poseStack.mulPose(new org.joml.Quaternionf().rotationY(rotationAngle));

            Matrix4f matrix = poseStack.last().pose();
            ResourceLocation texture = Helper.getCircleTexture(Helper.getSchoolFromEntity(entity));
            if (entity.getColor().equals(Utils.deconstructRGB(16239960))) texture =
                    ResourceLocation.fromNamespaceAndPath(SpellsNCircles.MODID, "textures/magic_circle/fortify_ring.png");

            //TODO; De-hardcode this and find a solution for every generic case like this
            RenderType renderType = RenderType.entityTranslucentEmissive(texture, true);
            VertexConsumer consumer = bufferSource.getBuffer(renderType);

            float half = entity.getRadius();

            consumer.addVertex(matrix, -half, 0f,  half).setColor(255, 255, 255, 255).setUv(0f, 1f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(240, 240).setNormal(0, 1, 0);
            consumer.addVertex(matrix,  half, 0f,  half).setColor(255, 255, 255, 255).setUv(1f, 1f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(240, 240).setNormal(0, 1, 0);
            consumer.addVertex(matrix,  half, 0f, -half).setColor(255, 255, 255, 255).setUv(1f, 0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(240, 240).setNormal(0, 1, 0);
            consumer.addVertex(matrix, -half, 0f, -half).setColor(255, 255, 255, 255).setUv(0f, 0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(240, 240).setNormal(0, 1, 0);

            if (bufferSource instanceof MultiBufferSource.BufferSource bs) {
                bs.endBatch(renderType);
            }
            poseStack.translate(0, 0.1, 0);
            renderEffects(entity); // the cool asf stuff

        } finally {
            poseStack.popPose(); // pop
        }
    }
    public void renderEffects(TargetedAreaEntity entity){ // Basically the cool particles effects on the AOE
        Level level = entity.level();
        if (Minecraft.getInstance().isPaused()) return;
        RandomSource random = level.random;
        float radius = entity.getRadius();

        float expected = Mth.PI * radius * radius * 0.02f;
        int count = (int) expected + (random.nextFloat() < expected % 1 ? 1 : 0);

        for (int i = 0; i < count; i++) {
            // for the guys who don't know how to math, the square root allow a more or less equal particles repartition
            float dist = radius * Mth.sqrt(random.nextFloat());
            float angle = random.nextFloat() * Mth.TWO_PI;
            double x = entity.getX() + dist * Mth.cos(angle);
            double y = entity.getY() + random.nextFloat() * 2.5f;
            double z = entity.getZ() + dist * Mth.sin(angle);
            level.addParticle(ParticleTypes.ENCHANT, false, x, y, z, 0, -0.1, 0);
        }
    }
}

