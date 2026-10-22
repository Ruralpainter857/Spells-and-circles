package com.purplenetwork.spells_n_circles;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue CUSTOM_SPELL_VFX = BUILDER
            .comment("enables/disable custom spells VFX")
            .define("custom_spell_vfx", true);

    static final ModConfigSpec SPEC = BUILDER.build();

}
