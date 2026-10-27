package com.purplenetwork.spells_n_circles;

import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;


@Mod(SpellsNCircles.MODID)
public class SpellsNCircles {
    public static final String MODID = "spells_n_circles";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static List<SchoolType> LOADED_SCHOOL_TYPES;
    public static Boolean disableMod;
    public static Boolean restrictRendering;
    public static Boolean hideCastedSchool;
    public static int circleMinSize;
    public static int circleMaxSize;


    public SpellsNCircles(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        //NeoForge.EVENT_BUS.register(this);
        //We don't need this

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
        @SubscribeEvent
        public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
            resetClientConfig();
        }
        @SubscribeEvent
        public static void onConfigLoading(ModConfigEvent.Loading event) {
            resetClientConfig();
        }
    }
    private static void resetClientConfig() {
        disableMod = Config.DISABLE_MOD.get();
        restrictRendering = Config.RESTRICT_CIRCLE_RENDERING.get();
        hideCastedSchool = Config.HIDE_CASTED_SPELL_SCHOOL.get();
        circleMinSize = Config.CIRCLE_MIN_SIZE.get();
        circleMaxSize = Config.CIRCLE_MAX_SIZE.get();
    }
}
