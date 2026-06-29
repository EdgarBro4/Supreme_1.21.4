package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import com.staehc_remerpus.emerpus.main.utils.j;
import net.minecraftforge.event.TickEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.gui.GuiGraphics;
import java.awt.Color;

public class NamaknerEludom extends Eludom {

    private static final int NOTIFICATION_WIDTH = 100;
    private static final int NOTIFICATION_HEIGHT = 20;
    private static final int NOTIFICATION_DURATION = 1500;

    private Notification currentNotification = null;
    private long notificationStartTime = 0;

    public NamaknerEludom() {
        super(Hex.d("4e6f74696669636174696f6e73"), Category.VISUALS);
    }

    @Override public void onEnable() {}
    @Override public void onDisable() { currentNotification = null; }

    @Override
    public void onTick() {
        if (currentNotification != null) {
            if (System.currentTimeMillis() - notificationStartTime >= NOTIFICATION_DURATION) {
                currentNotification = null;
            }
        }
    }

    public void addNotification(String moduleName, boolean toggledOn) {
        if (!isToggled()) return;
        currentNotification = new Notification(moduleName, toggledOn);
        notificationStartTime = System.currentTimeMillis();
    }

    @Override
    public void onRender2D(GuiGraphics graphics, float partialTicks) {
        if (!isToggled() || mc.player == null) return;
        if (currentNotification == null) return;

        
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int x = screenWidth / 2 - NOTIFICATION_WIDTH / 2;
        int y = screenHeight - 70;

        renderNotification(graphics, currentNotification, x, y);    }

    private void renderNotification(GuiGraphics graphics, Notification notification, int x, int y) {
        long timeElapsed = System.currentTimeMillis() - notificationStartTime;
        long timeLeft = NOTIFICATION_DURATION - timeElapsed;
        float progress = Math.max(0, Math.min(1, timeLeft / (float) NOTIFICATION_DURATION));
        int alpha = (int) (255 * progress);

        if (alpha <= 10) return;

        int bgColor = new Color(30, 30, 30, alpha).getRGB();
        j.drawSmoothRect(graphics, x, y, x + NOTIFICATION_WIDTH, y + NOTIFICATION_HEIGHT, bgColor);

        int borderColor = notification.toggledOn ?
                new Color(0, 255, 0, alpha).getRGB() :
                new Color(255, 0, 0, alpha).getRGB();

        j.drawSmoothRect(graphics, x, y, x + NOTIFICATION_WIDTH, y + 1, borderColor);
        j.drawSmoothRect(graphics, x, y + NOTIFICATION_HEIGHT - 1, x + NOTIFICATION_WIDTH, y + NOTIFICATION_HEIGHT, borderColor);
        j.drawSmoothRect(graphics, x, y, x + 1, y + NOTIFICATION_HEIGHT, borderColor);
        j.drawSmoothRect(graphics, x + NOTIFICATION_WIDTH - 1, y, x + NOTIFICATION_WIDTH, y + NOTIFICATION_HEIGHT, borderColor);
        j.drawSmoothRect(graphics, x + 3, y + NOTIFICATION_HEIGHT / 2 - 3, x + 7, y + NOTIFICATION_HEIGHT / 2 + 1, borderColor);

        String status = notification.toggledOn ? "ON" : "OFF";
        String displayText = notification.moduleName + " " + status;

        int textColor = notification.toggledOn ?
                new Color(100, 255, 100, alpha).getRGB() :
                new Color(255, 100, 100, alpha).getRGB();

        graphics.drawString(mc.font, displayText, x + 10, y + NOTIFICATION_HEIGHT / 2 - 4, textColor, false);
    }

    private static class Notification {
        private final String moduleName;
        private final boolean toggledOn;

        public Notification(String moduleName, boolean toggledOn) {
            this.moduleName = moduleName;
            this.toggledOn = toggledOn;
        }
    }
}