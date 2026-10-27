package com.purplenetwork.spells_n_circles.server;

import com.purplenetwork.spells_n_circles.SpellsNCircles;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.registration.NetworkRegistry;

public class ClientConnexionListener {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (NetworkRegistry.hasChannel(
                player.connection,
                ConfigTransmissionPacket.TYPE.id()
        )) PacketDistributor.sendToPlayer(player, new ConfigTransmissionPacket(
                SpellsNCircles.restrictRendering,
                SpellsNCircles.disableMod,
                SpellsNCircles.hideCastedSchool,
                SpellsNCircles.circleMaxSize,
                SpellsNCircles.circleMinSize
        )); // Transmits server config to client if has the mod

    }
}

