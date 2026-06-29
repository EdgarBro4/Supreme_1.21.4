package com.staehc_remerpus.emerpus.main.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;

public class j {

    private static final Minecraft mc = Minecraft.getInstance();

    // Draws a simple rectangle (for GUI) - uses GuiGraphics in 1.21.4
    public static void drawSmoothRect(GuiGraphics graphics, double left, double top, double right, double bottom, int color) {
        graphics.fill((int)left, (int)top, (int)right, (int)bottom, color);
    }

    // Draws a filled circle (as a square placeholder)
    public static void drawFilledCircle(GuiGraphics graphics, double x, double y, float radius, int color) {
        graphics.fill(
                (int)(x - radius), (int)(y - radius),
                (int)(x + radius), (int)(y + radius),
                color);
    }

    // Draws a 3D box around a block (for ESP/XRay)
    public static void drawBlockBox(BlockPos pos, int color) {
        if (mc.player == null) return;

        Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();

        double x = pos.getX() - cameraPos.x;
        double y = pos.getY() - cameraPos.y;
        double z = pos.getZ() - cameraPos.z;

        float r = (float) (color >> 16 & 255) / 255.0f;
        float g = (float) (color >> 8 & 255) / 255.0f;
        float b = (float) (color & 255) / 255.0f;

        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(2.0f);
        GL11.glColor4f(r, g, b, 0.8f);

        // Draw box outline
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x + 1, y, z);
        GL11.glVertex3d(x + 1, y, z + 1);
        GL11.glVertex3d(x, y, z + 1);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3d(x, y + 1, z);
        GL11.glVertex3d(x + 1, y + 1, z);
        GL11.glVertex3d(x + 1, y + 1, z + 1);
        GL11.glVertex3d(x, y + 1, z + 1);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y + 1, z);
        GL11.glVertex3d(x + 1, y, z);
        GL11.glVertex3d(x + 1, y + 1, z);
        GL11.glVertex3d(x + 1, y, z + 1);
        GL11.glVertex3d(x + 1, y + 1, z + 1);
        GL11.glVertex3d(x, y, z + 1);
        GL11.glVertex3d(x, y + 1, z + 1);
        GL11.glEnd();

        GL11.glPopAttrib();
    }
}