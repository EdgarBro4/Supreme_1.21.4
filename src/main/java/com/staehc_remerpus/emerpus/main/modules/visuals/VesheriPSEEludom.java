package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
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

public class VesheriPSEEludom extends Eludom {

    private static class LabelData {
        float screenX, screenY;
        String name;
    }

    private final List<LabelData> pendingLabels = new ArrayList<>();

    public VesheriPSEEludom() {
        super(Hex.d("4974656d455350"), Category.VISUALS);
        registerBoolean("names", false);
        registerBoolean("glow", false);
        registerFloat("range", 200.0f);
    }

    @Override public void onEnable() {}
    @Override public void onDisable() {
        if (mc.level != null) {
            for (net.minecraft.world.entity.Entity entity : mc.level.entitiesForRendering()) {
                if (entity instanceof net.minecraft.world.entity.item.ItemEntity) {
                    setGlowing(entity, false);
                }
            }
        }
    }
    @Override public void onTick() {}

    @SubscribeEvent
    public void onTickEvent(net.minecraftforge.event.TickEvent.ClientTickEvent.Post event) {
        if (!isToggled()) return;
        if (mc.player == null || mc.level == null) return;
        if (!getBoolean("glow")) return;
        
        for (net.minecraft.world.entity.Entity entity : mc.level.entitiesForRendering()) {
            if (entity instanceof net.minecraft.world.entity.item.ItemEntity) {
                setGlowing(entity, true);
            }
        }
    }

    private void setGlowing(net.minecraft.world.entity.Entity entity, boolean glowing) {
        entity.setGlowingTag(glowing);
        try {
            java.lang.reflect.Method m;
            try {
                m = net.minecraft.world.entity.Entity.class.getDeclaredMethod("setSharedFlag", int.class, boolean.class);
            } catch (NoSuchMethodException e) {
                m = net.minecraft.world.entity.Entity.class.getDeclaredMethod("m_20116_", int.class, boolean.class);
            }
            m.setAccessible(true);
            m.invoke(entity, 6, glowing);
        } catch (Exception e) {}
    }

    @SubscribeEvent
    public void onRenderWorld(AddFramePassEvent event) {
        if (!isToggled()) return;
        if (mc.player == null || mc.level == null) return;

        boolean showNames = getBoolean("names");
        float range = getFloat("range");

        pendingLabels.clear();

        FramePass pass = event.createPass(ResourceLocation.parse("supreme:vesheri"));
        event.getBundle().replace(net.minecraft.client.renderer.LevelTargetBundle.MAIN_TARGET_ID, pass.readsAndWrites(event.getBundle().get(net.minecraft.client.renderer.LevelTargetBundle.MAIN_TARGET_ID)));
        pass.executes(() -> {
            Vec3 cam = mc.gameRenderer.getMainCamera().getPosition();
            float pt = getPartialTicks();
    
            int sw = mc.getWindow().getGuiScaledWidth();
            int sh = mc.getWindow().getGuiScaledHeight();
    
            Matrix4f viewMat = new Matrix4f(RenderSystem.getModelViewMatrix());
            Matrix4f projMat = new Matrix4f(RenderSystem.getProjectionMatrix());
    
            PoseStack poseStack = new PoseStack();
    
            MultiBufferSource.BufferSource boxBuf = mc.renderBuffers().bufferSource();
            VertexConsumer vb = boxBuf.getBuffer(com.staehc_remerpus.emerpus.main.utils.RenderUtils.LINES_NO_DEPTH);
    
            for (Entity entity : mc.level.entitiesForRendering()) {
                if (!(entity instanceof ItemEntity)) continue;
                if (!entity.isAlive()) continue;
    
                ItemEntity item = (ItemEntity) entity;
                double x = item.xOld + (item.getX() - item.xOld) * pt;
                double y = item.yOld + (item.getY() - item.yOld) * pt;
                double z = item.zOld + (item.getZ() - item.zOld) * pt;
    
                AABB bb = item.getBoundingBox();
                double hw = (bb.maxX - bb.minX) / 2.0;
                double h  =  bb.maxY - bb.minY;
                if (h <= 0 || hw <= 0) continue;
    
                poseStack.pushPose();
                poseStack.translate(x - cam.x, y - cam.y, z - cam.z);
    
                int r = 0, g = 255, b = 255;
    
                Matrix4f pose = poseStack.last().pose();
                drawBox(vb, pose,
                        (float)-hw, 0f, (float)-hw,
                        (float) hw, (float)h, (float)hw,
                        r, g, b, 255);
    
                poseStack.popPose();
    
                if (showNames) {
                    float rx = (float)(x - cam.x);
                    float ry = (float)(y + h + 0.15 - cam.y);
                    float rz = (float)(z - cam.z);
    
                    Vector4f vec = new Vector4f(rx, ry, rz, 1.0f);
                    viewMat.transform(vec);
                    projMat.transform(vec);
    
                    if (vec.w() > 0.0f) {
                        float screenX = ( vec.x() / vec.w() * 0.5f + 0.5f) * sw;
                        float screenY = (-vec.y() / vec.w() * 0.5f + 0.5f) * sh;
    
                        if (screenX >= -200 && screenX <= sw + 200 && screenY >= -200 && screenY <= sh + 200) {
                            LabelData ld = new LabelData();
                            ld.screenX = screenX;
                            ld.screenY = screenY;
                            ld.name = item.getItem().getHoverName().getString();
                            int count = item.getItem().getCount();
                            if (count > 1) {
                                ld.name += " x" + count;
                            }
                            pendingLabels.add(ld);
                        }
                    }
                }
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
                graphics.drawString(mc.font, ld.name, tx, (int) lcy, new Color(0,255,255).getRGB(), true);
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
}