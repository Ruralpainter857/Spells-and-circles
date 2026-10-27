package com.purplenetwork.spells_n_circles.server;

import com.purplenetwork.spells_n_circles.SpellsNCircles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ConfigTransmissionPacket(boolean restrictRendering,
                                       boolean disableMod,
                                       boolean hideCastedSchool,
                                       int maxCircleSize,
                                       int minCircleSize) implements CustomPacketPayload {

    public static final Type<ConfigTransmissionPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(SpellsNCircles.MODID, "config_transmission_packet"));

    public static final StreamCodec<FriendlyByteBuf, ConfigTransmissionPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL, ConfigTransmissionPacket::restrictRendering,
                    ByteBufCodecs.BOOL, ConfigTransmissionPacket::disableMod,
                    ByteBufCodecs.BOOL, ConfigTransmissionPacket::hideCastedSchool,
                    ByteBufCodecs.INT, ConfigTransmissionPacket::minCircleSize,
                    ByteBufCodecs.INT, ConfigTransmissionPacket::maxCircleSize,
                    ConfigTransmissionPacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}

