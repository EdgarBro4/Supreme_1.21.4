package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.modules.MM;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import com.staehc_remerpus.emerpus.main.utils.e;
import com.staehc_remerpus.emerpus.main.utils.j;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.event.TickEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DuhEludom extends Eludom {

    private static int x = 10;
    private static int y = 40;
    private static int dragX = 0;
    private static int dragY = 0;
    private static boolean isDragging = false;

    private static int watermarkX = 10;
    private static int watermarkY = 10;
    private static int watermarkDragX = 0;
    private static int watermarkDragY = 0;
    private static boolean isDraggingWatermark = false;

    public DuhEludom() {
        super(Hex.d("485544"), Eludom.Category.VISUALS);

        registerBoolean("showWatermark", true);
        registerBoolean("showModuleList", true);
        registerBoolean("rainbow", false);
        registerBoolean("showFPS", true);
        registerBoolean("showUsername", true);
    }

    @Override public void onEnable() {}
    @Override public void onDisable() {}

    @Override
    public void onTick() {
        if (!isToggled() || mc.player == null) return;
        if (isDraggingWatermark || isDragging) {
            double mouseX = mc.mouseHandler.xpos() * mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth();
            double mouseY = mc.mouseHandler.ypos() * mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight();

            if (isDraggingWatermark) {
                watermarkX = (int) (mouseX + watermarkDragX);
                watermarkY = (int) (mouseY + watermarkDragY);
                watermarkX = Math.max(2, Math.min(watermarkX, mc.getWindow().getGuiScaledWidth() - 150));
                watermarkY = Math.max(2, Math.min(watermarkY, mc.getWindow().getGuiScaledHeight() - 30));
            }

            if (isDragging) {
                x = (int) (mouseX + dragX);
                y = (int) (mouseY + dragY);
                x = Math.max(0, Math.min(x, mc.getWindow().getGuiScaledWidth() - 80));
                y = Math.max(10, Math.min(y, mc.getWindow().getGuiScaledHeight() - 100));
            }
        }
    }

    @Override
    public void onRender2D(GuiGraphics graphics, float partialTicks) {
        if (!isToggled()) return;
        if (mc.player == null) return;

        

        if (getBoolean("showWatermark")) {
            renderWatermark(graphics);
        }

        if (getBoolean("showModuleList")) {
            renderModuleList(graphics);
        }
    }

    private void renderWatermark(GuiGraphics graphics) {
        StringBuilder text = new StringBuilder(d("53757072656d65"));

        if (getBoolean("showUsername")) {
            text.append(" §7- §c").append(mc.player.getName().getString());
        }

        if (getBoolean("showFPS")) {
            String fps = mc.fpsString.split(" fps")[0];
            text.append(" §7- §f").append(fps).append(" fps");
        }

        String watermarkText = text.toString();
        int textWidth = mc.font.width(watermarkText);

        boolean rainbow = getBoolean("rainbow");
        int accentColor = rainbow ?
                e.rainbow(1).getRGB() :
                e.accentColor.getRGB();

        j.drawSmoothRect(graphics, watermarkX - 2, watermarkY - 2,
                watermarkX + textWidth + 2, watermarkY + 10,
                new Color(20, 20, 20, 180).getRGB());

        j.drawSmoothRect(graphics, watermarkX - 2, watermarkY - 3,
                watermarkX + textWidth + 2, watermarkY - 2, accentColor);

        graphics.drawString(mc.font, watermarkText, watermarkX, watermarkY, Color.WHITE.getRGB(), false);

        if (isDraggingWatermark) {
            j.drawSmoothRect(graphics, watermarkX - 3, watermarkY - 4,
                    watermarkX + textWidth + 3, watermarkY - 3,
                    new Color(255, 255, 255, 100).getRGB());
        }
    }

    private void renderModuleList(GuiGraphics graphics) {
        List<Eludom> toggledEludoms = new ArrayList<>();
        for (Eludom m : MM.getModules()) {
            if (m.isToggled() && !m.getName().equals("HUD")) {
                toggledEludoms.add(m);
            }
        }

        toggledEludoms.sort(Comparator.comparing(Eludom::getName));

        boolean rainbow = getBoolean("rainbow");
        int accentColor = rainbow ?
                e.rainbow(1).getRGB() :
                e.accentColor.getRGB();

        if (isDragging) {
            int height = toggledEludoms.size() * 10 + 5;
            graphics.fill(x - 10, y - 5, x + 70, y + height,
                    new Color(20, 20, 20, 180).getRGB());
            graphics.fill(x - 11, y - 6, x + 71, y - 5, accentColor);
        }

        int currentY = y;
        for (Eludom m : toggledEludoms) {
            graphics.fill(x - 9, currentY, x - 7, currentY + 9, accentColor);
            graphics.drawString(mc.font, m.getName(), x, currentY, Color.WHITE.getRGB(), false);
            currentY += 10;
        }
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseButton.Post event) {
        if (!isToggled()) return;
        if (mc.player == null) return;
        if (mc.screen != null) return;

        double mouseX = mc.mouseHandler.xpos() * mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth();
        double mouseY = mc.mouseHandler.ypos() * mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight();

        if (event.getButton() == 0) {
            if (event.getAction() == GLFW.GLFW_PRESS) {
                if (getBoolean("showWatermark")) {
                    String text = d("53757072656d65");
                    if (getBoolean("showUsername")) text += " - " + mc.player.getName().getString();
                    if (getBoolean("showFPS")) text += " - " + mc.fpsString.split(" fps")[0] + " fps";
                    int textWidth = mc.font.width(text);

                    if (mouseX >= watermarkX - 2 && mouseX <= watermarkX + textWidth + 2 &&
                            mouseY >= watermarkY - 2 && mouseY <= watermarkY + 12) {
                        isDraggingWatermark = true;
                        watermarkDragX = (int) (watermarkX - mouseX);
                        watermarkDragY = (int) (watermarkY - mouseY);
                        return;
                    }
                }

                if (getBoolean("showModuleList")) {
                    if (mouseX >= x - 10 && mouseX <= x + 70 &&
                            mouseY >= y - 5 && mouseY <= y + 10) {
                        isDragging = true;
                        dragX = (int) (x - mouseX);
                        dragY = (int) (y - mouseY);
                    }
                }

            } else if (event.getAction() == GLFW.GLFW_RELEASE) {
                isDragging = false;
                isDraggingWatermark = false;
            }
        }
    }

    private static String d(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2)
            sb.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        return sb.toString();
    }
}