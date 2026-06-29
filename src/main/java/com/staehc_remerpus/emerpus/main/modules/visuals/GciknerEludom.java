package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import net.minecraftforge.client.event.AddFramePassEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.framegraph.FramePass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GciknerEludom extends Eludom {

    public GciknerEludom() {
        super(Hex.d("54726163657273"), Category.VISUALS);
    }

    @Override public void onEnable() {}
    @Override public void onDisable() {}
    @Override public void onTick() {}

    @SubscribeEvent
    public void onRenderWorldLast(AddFramePassEvent event) {
        if (!isToggled()) return;
        if (mc.level == null || mc.player == null) return;

        FramePass pass = event.createPass(ResourceLocation.parse("supreme:gcikner"));
        event.getBundle().replace(net.minecraft.client.renderer.LevelTargetBundle.MAIN_TARGET_ID, pass.readsAndWrites(event.getBundle().get(net.minecraft.client.renderer.LevelTargetBundle.MAIN_TARGET_ID)));
        pass.executes(() -> {
            float pt = getPartialTicks();
            
            PoseStack matrixStack = new PoseStack();
            Vec3 cameraPos = mc.gameRenderer.getMainCamera().getPosition();

            Matrix4f matrix = matrixStack.last().pose();
            Vec3 eyePos = mc.player.getEyePosition(pt);

            MultiBufferSource.BufferSource buf = mc.renderBuffers().bufferSource();
            VertexConsumer buffer = buf.getBuffer(com.staehc_remerpus.emerpus.main.utils.RenderUtils.LINES_NO_DEPTH);

            for (Player player : mc.level.players()) {
                if (player == mc.player) continue;

                Vec3 targetPos = player.getEyePosition(pt);

                float distance = (float) mc.player.distanceTo(player);
                float red = Math.min(1.0f, 2.0f - distance / 8.0f);
                float green = Math.min(1.0f, distance / 8.0f);
                float blue = 0.0f;
                
                int r = (int)(red * 255);
                int g = (int)(green * 255);
                int b = (int)(blue * 255);

                buffer.addVertex(matrix, (float)(eyePos.x - cameraPos.x), (float)(eyePos.y - cameraPos.y), (float)(eyePos.z - cameraPos.z))
                        .setColor(r, g, b, 178).setNormal(0, 1, 0);
                buffer.addVertex(matrix, (float)(targetPos.x - cameraPos.x), (float)(targetPos.y - cameraPos.y), (float)(targetPos.z - cameraPos.z))
                        .setColor(r, g, b, 178).setNormal(0, 1, 0);
            }

            buf.endBatch();
        });
    }
}