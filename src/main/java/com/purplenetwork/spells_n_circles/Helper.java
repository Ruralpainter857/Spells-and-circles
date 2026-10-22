package com.purplenetwork.spells_n_circles;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.HealingAoe;
import io.redspace.ironsspellbooks.entity.spells.target_area.TargetedAreaEntity;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;


public class Helper {
    private static final ResourceLocation FALLBACK_CIRCLE_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            SpellsNCircles.MODID, "textures/magic_circle/evocation_ring.png");

    public static ResourceLocation getCircleTexture(@Nullable SchoolType type) {
        if (type == null) {
            return FALLBACK_CIRCLE_TEXTURE; // So that it don't fkin' crash
        }
        String path = "textures/magic_circle/" + type.getId().getPath() + "_ring.png"; //Because spell path is basically their id
        return ResourceLocation.fromNamespaceAndPath(SpellsNCircles.MODID, path);
    }
    @Nullable
    public static SchoolType getSchoolFromEntity(TargetedAreaEntity entity) {
        List<SchoolType> schoolTypes = SpellsNCircles.LOADED_SCHOOL_TYPES;
        if (schoolTypes == null || schoolTypes.isEmpty()) {
            schoolTypes = getLoadedSchoolTypes();
        }
        return schoolTypes.stream()
                .filter(type -> sameColor(type.getTargetingColor(), entity.getColor()))
                .findFirst()
                .orElse(null);
    }
    public static List<SchoolType> getLoadedSchoolTypes() {
        return SchoolRegistry.REGISTRY.stream().toList();
    }
    private static boolean sameColor(Vector3f first, Vector3f second) {
        //return (Utils.packRGB(first) & 0xFFFFFF) == (Utils.packRGB(second) & 0xFFFFFF);
        return first.distanceSquared(second) <= 0.003f;
    }
}
