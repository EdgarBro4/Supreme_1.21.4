package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import com.staehc_remerpus.emerpus.main.utils.f;
import com.staehc_remerpus.emerpus.main.utils.e;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.client.event.AddFramePassEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.framegraph.FramePass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.client.gui.GuiGraphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;

public class PSEEludom extends Eludom {

    private static class LabelData {
        float screenX, screenY;
        String name;
        String health;
        int healthColor;
    }

    private final List<LabelData> pendingLabels = new ArrayList<>();

    public PSEEludom() {
        super(Hex.d("455350"), Eludom.Category.VISUALS);
        registerBoolean("players", true);
        registerBoolean("mobs",    false);
        registerBoolean("names",   false);
        registerBoolean("health",  false);
        registerBoolean("invis",   false);
        registerFloat("range", 200.0f);
    }

    @Override public void onEnable()  {}
    @Override public void onDisable() {}
    @Override public void onTick()    {}

    @SubscribeEvent
    public void onRenderWorld(AddFramePassEvent event) {
        if (!isToggled()) return;
        if (mc.player == null || mc.level == null) return;

        boolean showPlayers = getBoolean("players");
        boolean showMobs    = getBoolean("mobs");
        boolean showNames   = getBoolean("names");
        boolean showHealth  = getBoolean("health");
        boolean showInvis   = getBoolean("invis");
        float   range       = getFloat("range");

        pendingLabels.clear();

        FramePass pass = event.createPass(ResourceLocation.parse("supreme:pse"));
        event.getBundle().replace(net.minecraft.client.renderer.LevelTargetBundle.MAIN_TARGET_ID, pass.readsAndWrites(event.getBundle().get(net.minecraft.client.renderer.LevelTargetBundle.MAIN_TARGET_ID)));
        pass.executes(() -> {
            Vec3 cam = mc.gameRenderer.getMainCamera().getPosition();
            float pt = getPartialTicks();

            int sw = mc.getWindow().getGuiScaledWidth();
            int sh = mc.getWindow().getGuiScaledHeight();

            // Get actual view and projection matrices from RenderSystem
            Matrix4f viewMat = new Matrix4f(RenderSystem.getModelViewMatrix());
            Matrix4f projMat = new Matrix4f(RenderSystem.getProjectionMatrix());

            PoseStack poseStack = new PoseStack();

            MultiBufferSource.BufferSource boxBuf = mc.renderBuffers().bufferSource();
            VertexConsumer vb = boxBuf.getBuffer(com.staehc_remerpus.emerpus.main.utils.RenderUtils.LINES_NO_DEPTH);

            for (Entity entity : mc.level.entitiesForRendering()) {
                try {
                    if (entity == null || entity == mc.player) continue;
                    if (!entity.isAlive()) continue;
                    if (entity.isInvisible() && !showInvis) continue;
                    if (mc.player.distanceTo(entity) > range) continue;

                    boolean draw = false;
                    if (entity instanceof Player && showPlayers) draw = true;
                    else if (showMobs && (entity instanceof Monster || entity instanceof Animal)) draw = true;
                    if (!draw) continue;

                    double x = entity.xOld + (entity.getX() - entity.xOld) * pt;
                    double y = entity.yOld + (entity.getY() - entity.yOld) * pt;
                    double z = entity.zOld + (entity.getZ() - entity.zOld) * pt;

                    AABB bb = entity.getBoundingBox();
                    double hw = (bb.maxX - bb.minX) / 2.0;
                    double h  =  bb.maxY - bb.minY;
                    if (h <= 0 || hw <= 0) continue;

                    // Use the translated pose for box drawing
                    poseStack.pushPose();
                    poseStack.translate(x - cam.x, y - cam.y, z - cam.z);

                    java.awt.Color boxColor = (entity instanceof Player && f.isFriend((Player) entity))
                            ? new java.awt.Color(0, 255, 0)
                            : e.MAIN_RED;

                    int r = boxColor.getRed();
                    int g = boxColor.getGreen();
                    int b = boxColor.getBlue();

                    Matrix4f pose = poseStack.last().pose();
                    drawBox(vb, pose,
                            (float)-hw, 0f, (float)-hw,
                            (float) hw, (float)h, (float)hw,
                            r, g, b, 255);

                    poseStack.popPose();

                    if (showNames || showHealth) {
                        float rx = (float)(x - cam.x);
                        float ry = (float)(y + h + 0.25 - cam.y);
                        float rz = (float)(z - cam.z);

                        Vector4f vec = new Vector4f(rx, ry, rz, 1.0f);
                        viewMat.transform(vec);
                        projMat.transform(vec);

                        if (vec.w() <= 0.0f) continue;

                        float screenX = ( vec.x() / vec.w() * 0.5f + 0.5f) * sw;
                        float screenY = (-vec.y() / vec.w() * 0.5f + 0.5f) * sh;

                        if (screenX < -200 || screenX > sw + 200) continue;
                        if (screenY < -200 || screenY > sh + 200) continue;

                        LabelData ld = new LabelData();
                        ld.screenX = screenX;
                        ld.screenY = screenY;

                        if (showNames) ld.name = getEntityName(entity);
                        if (showHealth && entity instanceof LivingEntity) {
                            LivingEntity le = (LivingEntity) entity;
                            float hp = le.getHealth(), mhp = le.getMaxHealth();
                            if (mhp > 0) {
                                float pct = hp / mhp;
                                ld.health = String.format("%.0f/%.0f", hp, mhp);
                                ld.healthColor = pct > 0.6f ? new Color(0,255,0).getRGB()
                                        : pct > 0.3f ? new Color(255,255,0).getRGB()
                                        : new Color(255,0,0).getRGB();
                            }
                        }
                        pendingLabels.add(ld);
                    }

                } catch (Exception ignored) {}
            }

            boxBuf.endBatch();
        });
    }

    @Override
    public void onRender2D(GuiGraphics graphics, float partialTicks) {
        if (!isToggled()) return;
        if (pendingLabels.isEmpty()) return;

        int guiW = mc.getWindow().getGuiScaledWidth();
        int guiH = mc.getWindow().getGuiScaledHeight();

        for (LabelData ld : pendingLabels) {
            float lcx = ld.screenX, lcy = ld.screenY;
            if (lcy < 0 || lcy > guiH) continue;

            if (ld.name != null && !ld.name.isEmpty()) {
                int tw = mc.font.width(ld.name);
                int tx = (int) Math.max(1, Math.min(lcx - tw / 2f, guiW - tw - 1));
                graphics.drawString(mc.font, ld.name, tx, (int) lcy, e.MAIN_RED.getRGB(), true);
                lcy += mc.font.lineHeight + 1;
            }

            if (ld.health != null) {
                int tw = mc.font.width(ld.health);
                int tx = (int) Math.max(1, Math.min(lcx - tw / 2f, guiW - tw - 1));
                graphics.drawString(mc.font, ld.health, tx, (int) lcy, ld.healthColor, true);
            }
        }
    }

    private void drawBox(VertexConsumer vb, Matrix4f pose,
                         float x0,float y0,float z0,float x1,float y1,float z1,
                         int r,int g,int b,int a) {
        seg(vb,pose,x0,y0,z0,x1,y0,z0,r,g,b,a); seg(vb,pose,x1,y0,z0,x1,y0,z1,r,g,b,a);
        seg(vb,pose,x1,y0,z1,x0,y0,z1,r,g,b,a); seg(vb,pose,x0,y0,z1,x0,y0,z0,r,g,b,a);
        seg(vb,pose,x0,y1,z0,x1,y1,z0,r,g,b,a); seg(vb,pose,x1,y1,z0,x1,y1,z1,r,g,b,a);
        seg(vb,pose,x1,y1,z1,x0,y1,z1,r,g,b,a); seg(vb,pose,x0,y1,z1,x0,y1,z0,r,g,b,a);
        seg(vb,pose,x0,y0,z0,x0,y1,z0,r,g,b,a); seg(vb,pose,x1,y0,z0,x1,y1,z0,r,g,b,a);
        seg(vb,pose,x1,y0,z1,x1,y1,z1,r,g,b,a); seg(vb,pose,x0,y0,z1,x0,y1,z1,r,g,b,a);
    }

    private void seg(VertexConsumer vb, Matrix4f pose,
                     float x0,float y0,float z0,float x1,float y1,float z1,
                     int r,int g,int b,int a) {
        vb.addVertex(pose,x0,y0,z0).setColor(r,g,b,a).setNormal(0, 1, 0);
        vb.addVertex(pose,x1,y1,z1).setColor(r,g,b,a).setNormal(0, 1, 0);
    }

    private String getEntityName(Entity entity) {
        if (entity instanceof Player) return entity.getName().getString();
        if (entity instanceof Monster || entity instanceof Animal) {
            String t = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath();
            return Character.toUpperCase(t.charAt(0)) + t.substring(1);
        }
        return null;
    }
}