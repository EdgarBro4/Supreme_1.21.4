package com.staehc_remerpus.emerpus.main.modules.movement;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RunVasyaRunEludom extends Eludom {
    public RunVasyaRunEludom() {
        super(Hex.d("54696d6572"), Eludom.Category.MOVEMENT);
        registerFloat("speed", 1.0f, 1.0f, 2.0f);
    }
    @Override public void onEnable() {}
    @Override public void onDisable() {}
    @Override public void onTick() {}

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent.Pre event) {
        if (!isToggled()) return;
        if (mc.player == null) return;
        if (event.player != mc.player) return; // Only apply to local player
        
        float speed = getFloat("speed");
        if (speed != 1.0f) {
            mc.player.input.forwardImpulse *= speed;
            mc.player.input.leftImpulse *= speed;
        }
    }
}
