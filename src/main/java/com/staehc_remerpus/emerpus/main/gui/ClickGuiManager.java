package com.staehc_remerpus.emerpus.main.gui;

import com.staehc_remerpus.emerpus.main.c;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import com.staehc_remerpus.emerpus.main.utils.j;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import java.awt.Color;
import java.util.ArrayList;

public class ClickGuiManager extends Screen {

    public static String lastCategory = "COMBAT";

    // GUI dimensions
    public static double dragX = 200;
    public static double dragY = 50;
    public static final int GUI_WIDTH = 350;
    public static final int GUI_HEIGHT = 200;
    public static final int CATEGORY_WIDTH = 60;
    public static final int MODULE_WIDTH = 180;
    public static final int DRAG_BAR_HEIGHT = 20;

    // Drag state
    private boolean isDragging = false;
    private double dragOffsetX = 0;
    private double dragOffsetY = 0;

    // Unload button state
    private boolean isUnloadHovered = false;

    public ArrayList<CategoryButton> categoryButtons = new ArrayList<>();

    public ClickGuiManager() {
        super(Component.literal(d("53757072656d65")));
        lastCategory = Eludom.Category.COMBAT.name();
    }

    @Override
    public void init() {
        lastCategory = Eludom.Category.COMBAT.name();
        super.init();
    }
    private static String d(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2)
            sb.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        return sb.toString();
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // --- DRAG START: ONLY if left click on top bar, AND not over unload button ---
        if (button == 0) {
            boolean overTopBar = mouseX >= dragX && mouseX <= dragX + GUI_WIDTH &&
                    mouseY >= dragY && mouseY <= dragY + DRAG_BAR_HEIGHT;

            // Unload button area
            int unloadX = (int) (dragX + 10);
            int unloadY = (int) (dragY + GUI_HEIGHT - 25);
            int unloadWidth = 57;
            int unloadHeight = 20;
            boolean overUnload = mouseX >= unloadX - 10 && mouseX <= unloadX + unloadWidth + 10 &&
                    mouseY >= unloadY + 2 && mouseY <= unloadY + unloadHeight - 2;

            if (overTopBar && !overUnload) {
                isDragging = true;
                dragOffsetX = dragX - mouseX;
                dragOffsetY = dragY - mouseY;
                return true;
            }
        }

        // --- UNLOAD BUTTON ---
        int unloadX = (int) (dragX + 10);
        int unloadY = (int) (dragY + GUI_HEIGHT - 25);
        int unloadWidth = 57;
        int unloadHeight = 20;

        if (button == 0) {
            if (mouseX >= unloadX - 10 && mouseX <= unloadX + unloadWidth + 10 &&
                    mouseY >= unloadY + 2 && mouseY <= unloadY + unloadHeight - 2) {
                Minecraft.getInstance().setScreen(null);
                com.staehc_remerpus.emerpus.main.modules.MM.getModules().forEach(m -> { if(m.isToggled()) m.toggle(); });
                if (com.staehc_remerpus.emerpus.main.b.keyHandler != null) {
                    net.minecraftforge.common.MinecraftForge.EVENT_BUS.unregister(com.staehc_remerpus.emerpus.main.b.keyHandler);
                }
                com.staehc_remerpus.emerpus.main.b.isUnloaded = true;
                return true;
            }
        }

        // --- CATEGORY BUTTONS ---
        for (CategoryButton b : categoryButtons) {
            b.mouseClicked(mouseX, mouseY, button);
        }

        // --- MODULE BUTTONS --- only forward if inside visible module list area
        if (mouseY > dragY + 30 && mouseY < dragY + GUI_HEIGHT - 30) {
            for (ModuleButton b : CategoryButton.moduleButtons) {
                b.mouseClicked(mouseX, mouseY, button);
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && isDragging) {
            isDragging = false;
            return true;
        }

        // --- FORWARD TO MODULE BUTTONS FOR SLIDER RELEASE ---
        for (ModuleButton b : CategoryButton.moduleButtons) {
            b.mouseReleased(mouseX, mouseY, button);
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragXAmount, double dragYAmount) {
        if (button == 0 && isDragging) {
            dragX = mouseX + dragOffsetX;
            dragY = mouseY + dragOffsetY;

            // Keep on screen
            dragX = Math.max(0, Math.min(dragX, this.width - GUI_WIDTH));
            dragY = Math.max(0, Math.min(dragY, this.height - GUI_HEIGHT));

            return true;
        }

        // --- FORWARD TO MODULE BUTTONS FOR SLIDER DRAGGING ---
        for (ModuleButton b : CategoryButton.moduleButtons) {
            b.mouseDragged(mouseX, mouseY, button);
        }

        return super.mouseDragged(mouseX, mouseY, button, dragXAmount, dragYAmount);
    }

    // ✅ THIS WAS MISSING — ADD IT!
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        // Settings panel gets priority — if mouse is over it, stop here
        for (ModuleButton b : CategoryButton.moduleButtons) {
            if (b.handleScrolled(mouseX, mouseY, scrollY)) {
                return true;
            }
        }
        // Mouse not over settings panel — scroll the module list
        for (CategoryButton b : categoryButtons) {
            b.mouseScrolled(scrollY);
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float ticks) {
        this.renderBackground(graphics, mouseX, mouseY, ticks);

        // Main window
        j.drawSmoothRect(graphics, dragX, dragY, dragX + GUI_WIDTH, dragY + GUI_HEIGHT, e.bgColor.getRGB());

        // Draggable top bar
        j.drawSmoothRect(graphics, dragX, dragY, dragX + GUI_WIDTH, dragY + DRAG_BAR_HEIGHT,
                isDragging ? e.accentColor.darker().getRGB() : new Color(30, 30, 30).getRGB());

        // Supreme sign
        graphics.drawString(Minecraft.getInstance().font, d("53757072656d65"), (float) dragX + 8, (float) dragY + 6, e.accentColor.getRGB(), false);
        // Red border
        j.drawSmoothRect(graphics, dragX - 1, dragY - 1, dragX + GUI_WIDTH + 1, dragY, e.accentColor.getRGB());
        j.drawSmoothRect(graphics, dragX - 1, dragY + GUI_HEIGHT, dragX + GUI_WIDTH + 1, dragY + GUI_HEIGHT + 1, e.accentColor.getRGB());
        j.drawSmoothRect(graphics, dragX - 1, dragY, dragX, dragY + GUI_HEIGHT, e.accentColor.getRGB());
        j.drawSmoothRect(graphics, dragX + GUI_WIDTH, dragY, dragX + GUI_WIDTH + 1, dragY + GUI_HEIGHT, e.accentColor.getRGB());

        // Category buttons
        categoryButtons.clear();
        double buttonY = dragY + DRAG_BAR_HEIGHT + 5;
        for (Eludom.Category c : Eludom.Category.values()) {
            categoryButtons.add(new CategoryButton((int) (dragX + 10), (int) buttonY, CATEGORY_WIDTH, 20, c));
            buttonY += 22;
        }
        for (CategoryButton b : categoryButtons) b.render(graphics);

        // --- UNLOAD BUTTON ---
        int unloadX = (int) (dragX + 10);
        int unloadY = (int) (dragY + GUI_HEIGHT - 25);
        int unloadWidth = 57;
        int unloadHeight = 20;

        isUnloadHovered = mouseX >= unloadX - 10 && mouseX <= unloadX + unloadWidth + 10 &&
                mouseY >= unloadY + 2 && mouseY <= unloadY + unloadHeight - 2;

        int unloadButtonColor = isUnloadHovered ? new Color(195, 31, 38).getRGB() : new Color(120, 20, 25).getRGB();
        j.drawSmoothRect(graphics, unloadX - 10, unloadY + 2, unloadX + unloadWidth + 10, unloadY + unloadHeight - 2, unloadButtonColor);

        String unloadText = "Unload";
        graphics.drawString(Minecraft.getInstance().font, unloadText,
                unloadX + unloadWidth / 2 - Minecraft.getInstance().font.width(unloadText) / 2,
                unloadY + unloadHeight / 2 - 9 / 2,
                isUnloadHovered ? Color.WHITE.getRGB() : Color.LIGHT_GRAY.getRGB());

        // Drag hint
        if (mouseX >= dragX && mouseX <= dragX + GUI_WIDTH && mouseY >= dragY && mouseY <= dragY + DRAG_BAR_HEIGHT) {
            graphics.drawString(Minecraft.getInstance().font, "⇱", (int)(dragX + GUI_WIDTH - 15), (int)(dragY + 6),
                    isDragging ? Color.WHITE.getRGB() : Color.LIGHT_GRAY.getRGB());
        }

        super.render(graphics, mouseX, mouseY, ticks);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Forward key presses to module buttons for keybinding
        if (ModuleButton.isBinding) {
            for (ModuleButton b : CategoryButton.moduleButtons) {
                b.keyTyped(keyCode);
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override public boolean isPauseScreen() { return false; }
    @Override public boolean shouldCloseOnEsc() { return true; }
}