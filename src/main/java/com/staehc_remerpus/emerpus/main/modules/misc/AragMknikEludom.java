package com.staehc_remerpus.emerpus.main.modules.misc;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class AragMknikEludom extends Eludom {
    public AragMknikEludom() {
        super(Hex.d("4974656d5363726f6c6c6572"), Eludom.Category.MISC);
    }
    @Override public void onEnable() {}
    @Override public void onDisable() {}
    @Override public void onTick() {}

    @SubscribeEvent
    public void onRenderTick(TickEvent.ClientTickEvent.Post event) {
        if (!isToggled() || mc.player == null || mc.level == null || mc.screen == null) return;
        boolean isShiftDown = Screen.hasShiftDown();
        boolean isLeftMouseDown = GLFW.glfwGetMouseButton(mc.getWindow().getWindow(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        if (isShiftDown && isLeftMouseDown && mc.screen instanceof AbstractContainerScreen) {
            AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>) mc.screen;
            Slot slot = screen.getSlotUnderMouse();
            if (slot != null && slot.hasItem() && slot.mayPickup(mc.player)) {
                mc.gameMode.handleInventoryMouseClick(screen.getMenu().containerId, slot.index, 0, ClickType.QUICK_MOVE, mc.player);
            }
        }
    }
}
