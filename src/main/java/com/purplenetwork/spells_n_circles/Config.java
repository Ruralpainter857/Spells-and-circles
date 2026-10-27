package com.purplenetwork.spells_n_circles;

import net.neoforged.neoforge.common.ModConfigSpec;

// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DISABLE_MOD = BUILDER
            .comment("enables/disable custom spells VFX")
            .define("custom_spell_vfx", false);

    public static final ModConfigSpec.BooleanValue HIDE_CASTED_SPELL_SCHOOL = BUILDER
            .comment("if enabled, all the magic circles will have the same texture, the evocation ring")
            .define("hide_spell_school", false);

    public static final ModConfigSpec.BooleanValue RESTRICT_CIRCLE_RENDERING = BUILDER
            .comment("if enabled, magic circles won't appear on some situation, such as when the player is invisible")
            .define("restrict_circle_rendering", false);

    public static final ModConfigSpec.IntValue CIRCLE_MAX_SIZE = BUILDER
            .comment("Maximum magic circle size")
            .define("circle_max_size", 10).next().defineInRange("range_max", 10, 1, 10);

    public static final ModConfigSpec.IntValue CIRCLE_MIN_SIZE = BUILDER
            .comment("minimum circle size")
            .define("circle_min_size", 10).next().defineInRange("range_min", 10, 1, 10);

    static final ModConfigSpec SPEC = BUILDER.build();
}
