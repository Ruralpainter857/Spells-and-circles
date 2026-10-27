package com.purplenetwork.spells_n_circles.client;

import com.purplenetwork.spells_n_circles.SpellsNCircles;
import com.purplenetwork.spells_n_circles.server.ConfigTransmissionPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ClientPacketHandler {
    @SubscribeEvent
    public static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(ConfigTransmissionPacket.TYPE,
                ConfigTransmissionPacket.STREAM_CODEC, (packet, ctx) -> {
                    SpellsNCircles.hideCastedSchool = packet.hideCastedSchool();
                    SpellsNCircles.disableMod = packet.disableMod();
                    SpellsNCircles.restrictRendering = packet.restrictRendering();
                    SpellsNCircles.circleMinSize = packet.minCircleSize();
                    SpellsNCircles.circleMaxSize = packet.maxCircleSize();
                    // Update config so it corresponds to the server one
                });
    }
}

