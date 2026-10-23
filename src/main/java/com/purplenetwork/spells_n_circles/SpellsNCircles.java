package com.purplenetwork.spells_n_circles;

import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.List;


@Mod(SpellsNCircles.MODID)
public class SpellsNCircles {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "spells_n_circles";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static List<SchoolType> LOADED_SCHOOL_TYPES;


    public SpellsNCircles(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        //NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }
    @EventBusSubscriber(modid = SpellsNCircles.MODID, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Spells'n circles loaded.");
        }
        @SubscribeEvent
        static void onClientLoaded(FMLLoadCompleteEvent event){
            LOADED_SCHOOL_TYPES = Helper.getLoadedSchoolTypes(); // We're doing it here so we don't call this method at each casted spell
        }
    }
}
