package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import org.joml.Matrix4f;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.AddFramePassEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.framegraph.FramePass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class XRayEludom extends Eludom {

    private static final class BlockData {
        final int x, y, z;
        final float r, g, b;

        BlockData(int x, int y, int z, float r, float g, float b) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }

    private final CopyOnWriteArrayList<BlockData> visibleBlocks = new CopyOnWriteArrayList<>();
    private Thread scanThread;
    private volatile boolean scanning = false;

    public XRayEludom() {
        super(Hex.d("582d526179"), Eludom.Category.VISUALS);

        registerBoolean("diamond", true);
        registerBoolean("iron", true);
        registerBoolean("gold", true);
        registerBoolean("emerald", true);
        registerBoolean("lapis", false);
        registerBoolean("redstone", false);
        registerBoolean("coal", false);
        registerBoolean("nether_quartz", false);
        registerBoolean("ancient_debris", true);
        registerBoolean("spawner", true);

        registerFloat("radius", 30.0f, 5.0f, 64.0f);
    }

    @Override
    public void onEnable() {
        visibleBlocks.clear();
        startScanThread();
    }

    @Override
    public void onDisable() {
        stopScanThread();
        visibleBlocks.clear();
    }

    @Override
    public void onTick() {
    }

    private void startScanThread() {
        scanning = true;
        scanThread = new Thread(this::scanLoop, "XRay-ScanThread");
        scanThread.setDaemon(true);
        scanThread.start();
    }

    private void stopScanThread() {
        scanning = false;
        if (scanThread != null) {
            scanThread.interrupt();
            try {
                scanThread.join(2000);
            } catch (InterruptedException ignored) {
            }
            scanThread = null;
        }
    }

    private void scanLoop() {
        while (scanning) {
            try {
                net.minecraft.world.entity.player.Player localPlayer = mc.player;
                net.minecraft.client.multiplayer.ClientLevel localLevel = mc.level;
                if (localPlayer == null || localLevel == null) {
                    Thread.sleep(1000);
                    continue;
                }

                int radius = (int) getFloat("radius");
                int px = (int) localPlayer.getX();
                int py = (int) localPlayer.getY();
                int pz = (int) localPlayer.getZ();

                List<BlockData> newBlocks = new ArrayList<>();

                for (int x = px - radius; x <= px + radius && scanning; x++) {
                    for (int y = Math.max(0, py - radius); y <= Math.min(255, py + radius) && scanning; y++) {
                        for (int z = pz - radius; z <= pz + radius && scanning; z++) {
                            if (localLevel == null)
                                break;

                            BlockPos pos = new BlockPos(x, y, z);
                            Block block;
                            try {
                                block = localLevel.getBlockState(pos).getBlock();
                            } catch (Exception e) {
                                continue;
                            }

                            String id = block.getDescriptionId();

                            float[] color = getOreColor(id);
                            if (color != null) {
                                newBlocks.add(new BlockData(x, y, z, color[0], color[1], color[2]));
                            }
                        }
                    }
                    if (scanning)
                        Thread.sleep(2);
                }

                if (scanning) {
                    visibleBlocks.clear();
                    visibleBlocks.addAll(newBlocks);
                }

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private float[] getOreColor(String id) {
        if (id == null)
            return null;

        boolean isOre = id.contains("ore");
        boolean isDebris = id.contains("ancient_debris");
        boolean isSpawner = id.contains("spawner");
        if (!isOre && !isDebris && !isSpawner)
            return null;

        if (id.contains("diamond") && getBoolean("diamond"))
            return new float[] { 0f, 1f, 1f };

        if (id.contains("emerald") && getBoolean("emerald"))
            return new float[] { 0f, 1f, 0f };

        if (id.contains("gold") && getBoolean("gold"))
            return new float[] { 1f, 0.84f, 0f };

        if (id.contains("iron") && getBoolean("iron"))
            return new float[] { 1f, 0.78f, 0.59f };

        if (id.contains("lapis") && getBoolean("lapis"))
            return new float[] { 0f, 0.2f, 1f };

        if (id.contains("redstone") && getBoolean("redstone"))
            return new float[] { 1f, 0f, 0f };

        if (id.contains("coal") && getBoolean("coal"))
            return new float[] { 0.31f, 0.31f, 0.31f };

        if (id.contains("quartz") && getBoolean("nether_quartz"))
            return new float[] { 1f, 1f, 1f };

        if (isDebris && getBoolean("ancient_debris"))
            return new float[] { 0.7f, 0.16f, 0f };

        if (isSpawner && getBoolean("spawner"))
            return new float[] { 1f, 0.39f, 0.39f };

        return null;
    }

    @SubscribeEvent
    public void onRenderWorld(AddFramePassEvent event) {
        if (!isToggled()) return;
        
        if (mc.player == null || mc.level == null) return;
        if (visibleBlocks.isEmpty()) return;

        FramePass pass = event.createPass(ResourceLocation.parse("supreme:xray"));
        event.getBundle().replace(net.minecraft.client.renderer.LevelTargetBundle.MAIN_TARGET_ID, pass.readsAndWrites(event.getBundle().get(net.minecraft.client.renderer.LevelTargetBundle.MAIN_TARGET_ID)));
        pass.executes(() -> {
            PoseStack ms = new PoseStack();
            Vec3 cam = mc.gameRenderer.getMainCamera().getPosition();
    
            MultiBufferSource.BufferSource buf = mc.renderBuffers().bufferSource();
            VertexConsumer vb = buf.getBuffer(com.staehc_remerpus.emerpus.main.utils.RenderUtils.LINES_NO_DEPTH);
    
            for (BlockData bd : visibleBlocks) {
                ms.pushPose();
                ms.translate(bd.x - cam.x, bd.y - cam.y, bd.z - cam.z);
                Matrix4f pose = ms.last().pose();
                int r = (int)(bd.r * 255);
                int g = (int)(bd.g * 255);
                int b = (int)(bd.b * 255);
                drawBox(vb, pose, 0f, 0f, 0f, 1f, 1f, 1f, r, g, b, 255);
                ms.popPose();
            }
    
            buf.endBatch();
        });
    }

    private void drawBox(VertexConsumer vb, Matrix4f pose,
            float x0, float y0, float z0,
            float x1, float y1, float z1,
            int r, int g, int b, int a) {
        seg(vb, pose, x0, y0, z0, x1, y0, z0, r, g, b, a);
        seg(vb, pose, x1, y0, z0, x1, y0, z1, r, g, b, a);
        seg(vb, pose, x1, y0, z1, x0, y0, z1, r, g, b, a);
        seg(vb, pose, x0, y0, z1, x0, y0, z0, r, g, b, a);
        seg(vb, pose, x0, y1, z0, x1, y1, z0, r, g, b, a);
        seg(vb, pose, x1, y1, z0, x1, y1, z1, r, g, b, a);
        seg(vb, pose, x1, y1, z1, x0, y1, z1, r, g, b, a);
        seg(vb, pose, x0, y1, z1, x0, y1, z0, r, g, b, a);
        seg(vb, pose, x0, y0, z0, x0, y1, z0, r, g, b, a);
        seg(vb, pose, x1, y0, z0, x1, y1, z0, r, g, b, a);
        seg(vb, pose, x1, y0, z1, x1, y1, z1, r, g, b, a);
        seg(vb, pose, x0, y0, z1, x0, y1, z1, r, g, b, a);
    }

    private void seg(VertexConsumer vb, Matrix4f pose,
            float x0, float y0, float z0,
            float x1, float y1, float z1,
            int r, int g, int b, int a) {
        vb.addVertex(pose, x0, y0, z0).setColor(r, g, b, a).setNormal(0, 1, 0);
        vb.addVertex(pose, x1, y1, z1).setColor(r, g, b, a).setNormal(0, 1, 0);
    }
}
