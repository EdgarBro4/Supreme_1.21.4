package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VochmiKrakEludom extends Eludom {
    public VochmiKrakEludom() {
        super(Hex.d("4e6f46697265"), Eludom.Category.VISUALS);
    }
    @Override public void onEnable() {}
    @Override public void onDisable() {}
    @Override public void onTick() {}

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent.Post event) {
        if (!isToggled() || mc.player == null) return;
        mc.player.setRemainingFireTicks(0);
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockScreenEffectEvent event) {
        if (!isToggled()) return;
        if (event.getOverlayType() == RenderBlockScreenEffectEvent.OverlayType.FIRE) {
            event.setCanceled(true);
        }
    }
}