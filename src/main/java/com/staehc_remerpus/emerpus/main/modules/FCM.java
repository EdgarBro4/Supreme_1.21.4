package com.staehc_remerpus.emerpus.main.modules;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FCM {

    private static final Minecraft mc = Minecraft.getInstance();
    private static boolean enabled = false;
    private static float speed = 0.5f;
    private static FreeCameraEntity freeCameraEntity;

    public static boolean isEnabled() { return enabled; }

    public static void toggle(float speed) {
        if (!enabled) {
            enable(speed);
        } else {
            disable();
        }
    }

    private static void enable(float spd) {
        if (mc.player == null || mc.level == null) return;

        speed = spd;
        enabled = true;

        freeCameraEntity = new FreeCameraEntity(mc.player);
        freeCameraEntity.setId(-4200); // Give it a unique fake ID
        mc.level.addEntity(freeCameraEntity);
        mc.setCameraEntity(freeCameraEntity);

        MinecraftForge.EVENT_BUS.register(FCM.class);
    }

    private static void disable() {
        enabled = false;
        
        if (freeCameraEntity != null && mc.level != null) {
            mc.level.removeEntity(freeCameraEntity.getId(), net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
        }
        if (mc.player != null) {
            mc.setCameraEntity(mc.player);
        }
        
        freeCameraEntity = null;

        try {
            MinecraftForge.EVENT_BUS.unregister(FCM.class);
        } catch (Exception ignored) {}
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent.Post event) {
        if (!enabled) return;
        if (mc.player == null || mc.level == null) return;
        if (freeCameraEntity == null) return;

        freeCameraEntity.moveFreeCam(speed);
        freeCameraEntity.tick();
    }
}