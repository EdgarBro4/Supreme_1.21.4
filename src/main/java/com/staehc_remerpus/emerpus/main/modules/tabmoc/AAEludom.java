package com.staehc_remerpus.emerpus.main.modules.tabmoc;

import com.staehc_remerpus.emerpus.main.events.AttackEvent;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.g;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.staehc_remerpus.emerpus.main.utils.f;

public class AAEludom extends Eludom {

    private final Minecraft mc = Minecraft.getInstance();
    private Player target = null;

    public AAEludom() {
        super(d("41696d417373697374"), Category.COMBAT);
        registerFloat("distance", 5.0f, 2.0f, 7.0f);
        registerFloat("yawSpeed", 30.0f, 1.0f, 200.0f);
        registerFloat("pitchSpeed", 15.0f, 1.0f, 100.0f);
        registerBoolean("invisible", false);
        registerBoolean("onlySword", false);
    }

    @Override public void onEnable() { target = null; }
    @Override public void onDisable() { target = null; }
    @Override public void onTick() {}

    private static String d(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2)
            sb.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        return sb.toString();
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent.Post event) {
        if (!isToggled() || mc.player == null || mc.level == null) return;
        if (getBoolean("onlySword") && !isHoldingSword()) { target = null; return; }
        float distance = getFloat("distance");
        if (target != null && (!target.isAlive() || mc.player.distanceTo(target) > distance)) { target = null; return; }
        if (target == null) findTarget();
        if (target != null && target.isAlive()) aimAtTarget();
    }

    @SubscribeEvent
    public void onAttack(AttackEvent event) {
        if (isToggled() && event.target instanceof Player) {
            if (getBoolean("onlySword") && !isHoldingSword()) return;
            target = (Player) event.target;
        }
    }

    private void findTarget() {
        float distance = getFloat("distance");
        boolean invisible = getBoolean("invisible");
        if (getBoolean("onlySword") && !isHoldingSword()) return;
        for (Player player : mc.level.players()) {
            if (player == mc.player) continue;
            if (!invisible && player.hasEffect(MobEffects.INVISIBILITY)) continue;
            if (f.isFriend(player)) continue;
            if (mc.player.distanceTo(player) <= distance) { target = player; break; }
        }
    }

    private boolean isHoldingSword() {
        if (mc.player == null) return false;
        return mc.player.getMainHandItem().getItem() instanceof net.minecraft.world.item.SwordItem
            || mc.player.getOffhandItem().getItem() instanceof net.minecraft.world.item.SwordItem;
    }

    private void aimAtTarget() {
        float yawSpeed = getFloat("yawSpeed");
        float pitchSpeed = getFloat("pitchSpeed");
        float[] rotations = g.smoothRotations(target, mc.player.getYRot(), yawSpeed / 2.0f, pitchSpeed / 2.0f);
        if (yawSpeed > 0) {
            float yawDiff = g.wrapAngleTo180(rotations[0] - mc.player.getYRot());
            mc.player.setYRot(mc.player.getYRot() + yawDiff * 0.1f);
        }
        if (pitchSpeed > 0) {
            float pitchDiff = rotations[1] - mc.player.getXRot();
            mc.player.setXRot(mc.player.getXRot() + pitchDiff * 0.1f);
        }
        mc.player.setXRot(Math.max(-90, Math.min(90, mc.player.getXRot())));
    }
}