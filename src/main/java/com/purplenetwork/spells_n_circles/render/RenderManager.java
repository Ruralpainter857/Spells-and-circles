package com.purplenetwork.spells_n_circles.render;

import com.purplenetwork.spells_n_circles.Helper;
import com.purplenetwork.spells_n_circles.SpellsNCircles;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Vector3f;

import java.util.ArrayList;

@EventBusSubscriber(modid = SpellsNCircles.MODID, value = Dist.CLIENT)
public class RenderManager {
    private static final ArrayList<MagicCircle> CIRCLES = new ArrayList<>();
    private static final ArrayList<MagicCircle> CIRCLES_FOR_REMOVAL = new ArrayList<>();

    public static void buildAndAddCircle(int lifetimeTicks, Vector3f color, LivingEntity castingEntity, int radius){
        CIRCLES.add(new MagicCircle(lifetimeTicks,
                Helper.getCircleTexture(Helper.getSchoolFromColor(color)), castingEntity, radius));
    }
    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) return;

        PoseStack poseStack = event.getPoseStack();
        Camera camera = event.getCamera();
        float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);

        CIRCLES.forEach(magicCircle -> {
            Vec3 pos = magicCircle.getCastingEntity().getPosition(partialTick).subtract(camera.getPosition());

            poseStack.pushPose();
            poseStack.translate(pos.x, pos.y, pos.z);
            CircleRenderer.render(magicCircle.getTexture(), magicCircle.getRadius(), poseStack,
                    Minecraft.getInstance().renderBuffers().bufferSource());
            poseStack.popPose();
        });
    }
    public static void removeCircle(MagicCircle circle){
        CIRCLES_FOR_REMOVAL.add(circle);
    }
    @SubscribeEvent
    public static void onClientTickEvent(ClientTickEvent.Post event){
        CIRCLES_FOR_REMOVAL.forEach(CIRCLES::remove);
        CIRCLES.forEach(MagicCircle::tick);
    }
}