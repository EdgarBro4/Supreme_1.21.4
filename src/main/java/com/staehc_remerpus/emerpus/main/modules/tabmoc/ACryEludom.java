package com.staehc_remerpus.emerpus.main.modules.tabmoc;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.util.Random;

public class ACryEludom extends Eludom {
    private static final float HARD_REACH_CAP = 3.0f;
    private static final int JITTER_TICKS = 2;
    private final Minecraft mc = Minecraft.getInstance();
    private final Random random = new Random();
    private int tickTimer = 0;
    private int cycleDelay = 1;

    public ACryEludom() {
        super(d("4175746f4372797374616c"), Category.COMBAT);
        registerFloat("explode", 3.0f, 1.0f, 4.5f);
        registerFloat("delay", 3.0f, 1.0f, 20.0f);
    }
    private static String d(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2)
            sb.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        return sb.toString();
    }
    @Override public void onEnable() { tickTimer = 0; cycleDelay = computeDelay(); }
    @Override public void onDisable() { tickTimer = 0; }
    @Override public void onTick() {}

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent.Pre event) {
        if (!isToggled()) return;
        if (mc.player == null || mc.level == null || mc.gameMode == null) return;
        if (mc.player.isSpectator()) return;
        tickTimer++;
        if (tickTimer < cycleDelay) return;
        tickTimer = 0;
        cycleDelay = computeDelay();
        EndCrystal target = findNearestCrystal();
        if (target == null) return;
        mc.player.swing(InteractionHand.MAIN_HAND);
        mc.gameMode.attack(mc.player, target);
    }

    private EndCrystal findNearestCrystal() {
        float range = Math.min(getFloat("explode"), HARD_REACH_CAP);
        float rangeSq = range * range;
        EndCrystal nearest = null;
        double nearestSq = Double.MAX_VALUE;
        for (Entity e : mc.level.entitiesForRendering()) {
            if (!(e instanceof EndCrystal)) continue;
            if (!e.isAlive()) continue;
            double dsq = mc.player.distanceToSqr(e);
            if (dsq > rangeSq) continue;
            if (dsq < nearestSq) { nearestSq = dsq; nearest = (EndCrystal) e; }
        }
        return nearest;
    }

    private int computeDelay() {
        int base = (int) getFloat("delay");
        int jitter = random.nextInt(JITTER_TICKS * 2 + 1) - JITTER_TICKS;
        return Math.max(1, base + jitter);
    }
}