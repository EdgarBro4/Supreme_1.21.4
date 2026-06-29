package com.staehc_remerpus.emerpus.main;

import com.staehc_remerpus.emerpus.main.modules.MM;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class b {

    private static boolean initialized = false;
    public static c keyHandler;
    public static boolean isUnloaded = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        if (FMLEnvironment.dist == Dist.CLIENT) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(b::onClientSetup);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(b::onAddGuiOverlays);
        }
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        // Initialize module manager
        MM.init();

        // Register key handler
        keyHandler = new c();
        MinecraftForge.EVENT_BUS.register(keyHandler);
    }

    private static void onAddGuiOverlays(net.minecraftforge.client.event.AddGuiOverlayLayersEvent event) {
        event.getLayeredDraw().add((graphics, pt) -> {
            if (isUnloaded) return;
            for (com.staehc_remerpus.emerpus.main.modules.Eludom m : MM.getModules()) {
                if (m.isToggled()) m.onRender2D(graphics, pt.getGameTimeDeltaPartialTick(true));
            }
        });
    }

    static {
        init();
    }
}