package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.mojang.blaze3d.vertex.PoseStack;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ErkarDzernerEludom extends Eludom {
    public ErkarDzernerEludom() {
        super(Hex.d("566965774d6f64656c"), Eludom.Category.VISUALS);
        registerFloat("rx", 0.0f);
        registerFloat("ry", 0.0f);
        registerFloat("rz", 0.0f);
        registerFloat("lx", 0.0f);
        registerFloat("ly", 0.0f);
        registerFloat("lz", 0.0f);
    }
    @Override public void onEnable() {}
    @Override public void onDisable() {}
    @Override public void onTick() {}

    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event) {
        if (!isToggled()) return;
        if (!mc.options.getCameraType().isFirstPerson()) return;
        PoseStack matrixStack = event.getPoseStack();
        if (event.getHand() == InteractionHand.MAIN_HAND) {
            matrixStack.translate(getFloat("rx"), getFloat("ry"), getFloat("rz"));
        } else {
            matrixStack.translate(getFloat("lx"), getFloat("ly"), getFloat("lz"));
        }
    }
}
