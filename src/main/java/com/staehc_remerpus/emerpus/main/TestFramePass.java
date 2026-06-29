package com.staehc_remerpus.emerpus.main;

import net.minecraftforge.client.event.AddFramePassEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.mojang.blaze3d.framegraph.FramePass;
import net.minecraft.resources.ResourceLocation;

public class TestFramePass {
    @SubscribeEvent
    public void onAddFramePass(AddFramePassEvent event) {
        FramePass pass = event.createPass(ResourceLocation.fromNamespaceAndPath("supreme", "test"));
        pass.executes(() -> {
            System.out.println("Executes");
        });
    }
}
