package com.staehc_remerpus.emerpus.main;

import com.staehc_remerpus.emerpus.main.gui.ClickGuiManager;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.modules.MM;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class c {

    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (mc.player == null) return;
        if (mc.screen != null) return;

        if (event.getAction() == GLFW.GLFW_PRESS) {
            int key = event.getKey();

            // Open Click GUI on Right Shift
            if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
                mc.setScreen(new ClickGuiManager());
                return;
            }

            // Toggle modules by keybind
            for (Eludom m : MM.getModules()) {
                if (m.getKey() == key) {
                    m.toggle();
                }
            }
        }
    }
}