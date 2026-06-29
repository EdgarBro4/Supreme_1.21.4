package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.mojang.blaze3d.platform.GlStateManager;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class BacPaterEludom extends Eludom {
    public BacPaterEludom() {
        super(Hex.d("57616c6c4861636b"), Eludom.Category.VISUALS);
    }
    @Override public void onEnable() {}
    @Override public void onDisable() {}
    @Override public void onTick() {}

    @SubscribeEvent
    public void onRenderLiving(RenderLivingEvent.Pre event) {
        if (isToggled()) {
            GlStateManager._clear(GL11.GL_DEPTH_BUFFER_BIT);
        }
    }
}