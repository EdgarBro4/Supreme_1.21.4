package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import com.staehc_remerpus.emerpus.main.utils.f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.AddFramePassEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.framegraph.FramePass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.joml.Matrix4f;

import java.awt.Color;

public class CHEludom extends Eludom {

    public CHEludom() {
        super(Hex.d("4368696e61486174"), Category.VISUALS);
    }

    @Override public void onEnable() {}
    @Override public void onDisable() {}
    @Override public void onTick() {}

    @SubscribeEvent
    public void onRenderWorldLast(AddFramePassEvent event) {
        if (!isToggled()) return;
        
        if (mc.level == null || mc.player == null) return;

        FramePass pass = event.createPass(ResourceLocation.parse("supreme:chinahat"));
        event.getBundle().replace(net.minecraft.client.renderer.LevelTargetBundle.MAIN_TARGET_ID, pass.readsAndWrites(event.getBundle().get(net.minecraft.client.renderer.LevelTargetBundle.MAIN_TARGET_ID)));
        pass.executes(() -> {
            float pt = getPartialTicks();
            for (Player player : mc.level.players()) {
                renderChinaHat(new PoseStack(), player, pt);
            }
        });
    }

    private void renderChinaHat(PoseStack matrixStack, Player player, float partialTicks) {
        double x = player.xOld + (player.getX() - player.xOld) * partialTicks - mc.getEntityRenderDispatcher().camera.getPosition().x;
        double y = player.yOld + (player.getY() - player.yOld) * partialTicks - mc.getEntityRenderDispatcher().camera.getPosition().y;
        double z = player.zOld + (player.getZ() - player.zOld) * partialTicks - mc.getEntityRenderDispatcher().camera.getPosition().z;

        float[] color1, color2;

        if (player == mc.player) {
            color1 = getColorComponents(new Color(0x6E1F1F).getRGB());
            color2 = getColorComponents(new Color(0x9E4E4E).getRGB());
        } else if (f.isFriend(player)) {
            color1 = getColorComponents(new Color(0x006400).getRGB());
            color2 = getColorComponents(new Color(0x00FF00).getRGB());
        } else {
            color1 = getColorComponents(new Color(0x8B0000).getRGB());
            color2 = getColorComponents(new Color(0xFFD700).getRGB());
        }

        matrixStack.pushPose();
        matrixStack.translate(x, y, z);

        float height = player.getBbHeight() + 0.15f;
        
        MultiBufferSource.BufferSource buf = mc.renderBuffers().bufferSource();
        Matrix4f pose = matrixStack.last().pose();

        VertexConsumer buffer = buf.getBuffer(com.staehc_remerpus.emerpus.main.utils.RenderUtils.TRIANGLES_NO_DEPTH);
        
        int r = (int)(color1[0]*255), g = (int)(color1[1]*255), b = (int)(color1[2]*255);
        int r2 = (int)(color2[0]*255), g2 = (int)(color2[1]*255), b2 = (int)(color2[2]*255);
        
        for (int i = 0; i < 360; i += 5) {
            double rad1 = Math.cos(Math.toRadians(i)) * 0.6;
            double rad2 = Math.sin(Math.toRadians(i)) * 0.6;
            double rad3 = Math.cos(Math.toRadians(i + 5)) * 0.6;
            double rad4 = Math.sin(Math.toRadians(i + 5)) * 0.6;

            // Draw triangle from tip to base segment
            buffer.addVertex(pose, 0.0f, height + 0.25f, 0.0f).setColor(r, g, b, 120);
            buffer.addVertex(pose, (float)rad1, height, (float)rad2).setColor(r2, g2, b2, 80);
            buffer.addVertex(pose, (float)rad3, height, (float)rad4).setColor(r2, g2, b2, 80);
        }
        
        buf.endBatch();
        matrixStack.popPose();
    }

    private float[] getColorComponents(int rgb) {
        return new float[]{
                ((rgb >> 16) & 0xFF) / 255.0f,
                ((rgb >> 8) & 0xFF) / 255.0f,
                (rgb & 0xFF) / 255.0f,
                1.0f
        };
    }
}