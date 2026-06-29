package com.staehc_remerpus.emerpus.main.modules.movement;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SkazlaRunVasyaEludom extends Eludom {
    public SkazlaRunVasyaEludom() {
        super(Hex.d("537072696e74"), Eludom.Category.MOVEMENT); // Sprint
    }
    @Override public void onEnable() {}
    @Override public void onDisable() {
        if (mc.player != null) mc.player.setSprinting(false);
    }
    @Override public void onTick() {}

    @SubscribeEvent
    public void onTickEvent(net.minecraftforge.event.TickEvent.ClientTickEvent.Post event) {
        if (!isToggled()) return;
        if (mc.player == null) return;
        
        if (mc.player.input.forwardImpulse > 0 && !mc.player.horizontalCollision && !mc.player.isCrouching()) {
            mc.player.setSprinting(true);
        }
    }
}
