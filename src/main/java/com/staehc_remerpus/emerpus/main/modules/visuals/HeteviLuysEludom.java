package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import com.staehc_remerpus.emerpus.main.utils.f;
import com.staehc_remerpus.emerpus.main.utils.e;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.CameraType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import net.minecraftforge.client.event.AddFramePassEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.framegraph.FramePass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.awt.Color;
import java.util.*;

public class HeteviLuysEludom extends Eludom {

    private final Deque<TrailPoint> selfPoints = new ArrayDeque<>();
    private final Map<UUID, Deque<TrailPoint>> playerPoints = new HashMap<>();

    private static final int MAX_POINTS = 15;
    private static final long POINT_LIFETIME = 350L;
    private Vec3 lastSelfPosition = Vec3.ZERO;

    private static class TrailPoint {
        final Vec3 position;
        final float bbHeight;
        final long creationTime;

        TrailPoint(Vec3 position, float bbHeight) {
            this.position = position;
            this.bbHeight = bbHeight;
            this.creationTime = System.currentTimeMillis();
        }
    }

    public HeteviLuysEludom() {
        super(Hex.d("547261696c73"), Eludom.Category.VISUALS);
        registerBoolean("players", false);
    }

    @Override public void onEnable()  { selfPoints.clear(); playerPoints.clear(); }
    @Override public void onDisable() { selfPoints.clear(); playerPoints.clear(); }
    @Override public void onTick()    {}

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent.Post event) {
        if (!isToggled() || mc.level == null || mc.player == null) return;

        long now = System.currentTimeMillis();

        if (mc.options.getCameraType() != CameraType.FIRST_PERSON) {
            selfPoints.removeIf(p -> now - p.creationTime > POINT_LIFETIME);
            Vec3 pos = mc.player.position();
            if (pos.distanceTo(lastSelfPosition) > 0.1) {
                selfPoints.addFirst(new TrailPoint(pos, mc.player.getBbHeight()));
                lastSelfPosition = pos;
                if (selfPoints.size() > MAX_POINTS) selfPoints.removeLast();
            }
        }

        if (getBoolean("players")) {
            for (Player player : mc.level.players()) {
                if (player == mc.player) continue;
                UUID id = player.getUUID();
                Deque<TrailPoint> pts = playerPoints.computeIfAbsent(id, k -> new ArrayDeque<>());
                pts.removeIf(p -> now - p.creationTime > POINT_LIFETIME);
                pts.addFirst(new TrailPoint(player.position(), player.getBbHeight()));
                if (pts.size() > MAX_POINTS) pts.removeLast();
            }
            playerPoints.keySet().removeIf(id ->
                    mc.level.players().stream().noneMatch(p -> p.getUUID().equals(id)));
        } else {
            playerPoints.clear();
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(AddFramePassEvent event) {
        if (!isToggled()) return;
        

        FramePass pass = event.createPass(ResourceLocation.parse("supreme:trails"));
        event.getBundle().replace(net.minecraft.client.renderer.LevelTargetBundle.MAIN_TARGET_ID, pass.readsAndWrites(event.getBundle().get(net.minecraft.client.renderer.LevelTargetBundle.MAIN_TARGET_ID)));
        pass.executes(() -> {
            PoseStack matrixStack = new PoseStack();
            float partialTicks = getPartialTicks();
    
            if (!selfPoints.isEmpty() && mc.options.getCameraType() != CameraType.FIRST_PERSON) {
                Vec3 interpolatedPos = mc.player.getPosition(partialTicks);
                renderTrail(matrixStack, selfPoints, interpolatedPos,
                        mc.player.getBbHeight(), e.MAIN_RED);
            }
    
            if (getBoolean("players") && !playerPoints.isEmpty()) {
                for (Player player : mc.level.players()) {
                    if (player == mc.player) continue;
                    Deque<TrailPoint> pts = playerPoints.get(player.getUUID());
                    if (pts == null || pts.isEmpty()) continue;
                    Vec3 interpolatedPos = new Vec3(
                            player.xOld + (player.getX() - player.xOld) * partialTicks,
                            player.yOld + (player.getY() - player.yOld) * partialTicks,
                            player.zOld + (player.getZ() - player.zOld) * partialTicks);
                    Color trailColor = f.isFriend(player) ? new Color(0, 255, 0) : e.MAIN_RED;
                    renderTrail(matrixStack, pts, interpolatedPos,
                            player.getBbHeight(), trailColor);
                }
            }
        });
    }

    private void renderTrail(PoseStack matrixStack, Deque<TrailPoint> points,
                             Vec3 headPos, float bbHeight, Color color) {
                             
        Vec3 view = mc.gameRenderer.getMainCamera().getPosition();
        int totalPoints = points.size();

        matrixStack.pushPose();
        Vec3 cam = mc.gameRenderer.getMainCamera().getPosition();
        
        Matrix4f pose = matrixStack.last().pose();
        MultiBufferSource.BufferSource buf = mc.renderBuffers().bufferSource();
        VertexConsumer buffer = buf.getBuffer(com.staehc_remerpus.emerpus.main.utils.RenderUtils.QUADS_NO_DEPTH);
        
        int r = color.getRed(), g = color.getGreen(), b = color.getBlue();

        Iterator<TrailPoint> iter = points.iterator();
        TrailPoint prev = null;
        int index = 0;
        
        while (iter.hasNext()) {
            TrailPoint p = iter.next();
            float alpha = 1.0f - (float) index / totalPoints;
            int a = (int)(alpha * 128); // Semi-transparent
            
            if (index == 0) {
                // Draw quad from headPos to first point
                buffer.addVertex(pose, (float)(headPos.x - view.x), (float)(headPos.y + bbHeight - view.y), (float)(headPos.z - view.z)).setColor(r, g, b, 128);
                buffer.addVertex(pose, (float)(headPos.x - view.x), (float)(headPos.y - view.y), (float)(headPos.z - view.z)).setColor(r, g, b, 128);
                buffer.addVertex(pose, (float)(p.position.x - view.x), (float)(p.position.y - view.y), (float)(p.position.z - view.z)).setColor(r, g, b, a);
                buffer.addVertex(pose, (float)(p.position.x - view.x), (float)(p.position.y + p.bbHeight - view.y), (float)(p.position.z - view.z)).setColor(r, g, b, a);
            } else if (prev != null) {
                float prevAlpha = 1.0f - (float) (index - 1) / totalPoints;
                int pa = (int)(prevAlpha * 128);
                
                // Draw quad from previous point to current point
                buffer.addVertex(pose, (float)(prev.position.x - view.x), (float)(prev.position.y + prev.bbHeight - view.y), (float)(prev.position.z - view.z)).setColor(r, g, b, pa);
                buffer.addVertex(pose, (float)(prev.position.x - view.x), (float)(prev.position.y - view.y), (float)(prev.position.z - view.z)).setColor(r, g, b, pa);
                buffer.addVertex(pose, (float)(p.position.x - view.x), (float)(p.position.y - view.y), (float)(p.position.z - view.z)).setColor(r, g, b, a);
                buffer.addVertex(pose, (float)(p.position.x - view.x), (float)(p.position.y + p.bbHeight - view.y), (float)(p.position.z - view.z)).setColor(r, g, b, a);
            }
            
            prev = p;
            index++;
        }
        
        buf.endBatch();
    }
}