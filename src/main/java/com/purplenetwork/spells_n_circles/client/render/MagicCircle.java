package com.purplenetwork.spells_n_circles.client.render;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class MagicCircle {
    private final ResourceLocation texture;
    private final LivingEntity castingEntity;
    private int lifetimeTicks;
    private final int radius;
    public MagicCircle(int lifetimeTicks, ResourceLocation texture, LivingEntity castingEntity, int radius){
        this.castingEntity = castingEntity;
        this.texture = texture;
        this.radius = radius;
        this.lifetimeTicks = lifetimeTicks;
    }

    public int getLifetimeTicks() {return lifetimeTicks;}
    public LivingEntity getCastingEntity() {return castingEntity;}
    public ResourceLocation getTexture() {return texture;}
    public int getRadius() {return radius;}

    public void tick(){
        this.lifetimeTicks--;
        if (this.lifetimeTicks <= 0) RenderManager.removeCircle(this);
    }
}
