package com.staehc_remerpus.emerpus.main.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public class ConfigStringScreen extends Screen {
    private EditBox editBox;
    private final String titleStr;
    private final Consumer<String> callback;
    private final Screen parent;
    
    public ConfigStringScreen(String title, Screen parent, Consumer<String> callback) {
        super(Component.literal(title));
        this.titleStr = title;
        this.parent = parent;
        this.callback = callback;
    }
    
    @Override
    public void init() {
        this.editBox = new EditBox(Minecraft.getInstance().font, this.width / 2 - 100, this.height / 2 - 10, 200, 20, Component.literal("Config Name"));
        this.editBox.setMaxLength(50);
        this.addRenderableWidget(this.editBox);
        this.setFocused(this.editBox);
    }
    
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
        graphics.drawCenteredString(Minecraft.getInstance().font, titleStr, this.width / 2, this.height / 2 - 30, 0xFFFFFF);
        graphics.drawCenteredString(Minecraft.getInstance().font, "Press ENTER to confirm, ESC to cancel", this.width / 2, this.height / 2 + 20, 0xAAAAAA);
        super.render(graphics, mouseX, mouseY, partialTicks);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            callback.accept(editBox.getValue());
            Minecraft.getInstance().setScreen(parent);
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            Minecraft.getInstance().setScreen(parent);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
