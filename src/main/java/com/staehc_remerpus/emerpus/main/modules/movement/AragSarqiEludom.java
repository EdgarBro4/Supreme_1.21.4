package com.staehc_remerpus.emerpus.main.modules.movement;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AragSarqiEludom extends Eludom {
    private final Minecraft mc = Minecraft.getInstance();
    private static final double EDGE_CHECK_DIST = 0.5;

    public AragSarqiEludom() {
        super(Hex.d("4175746f536e65616b"), Category.MOVEMENT);
        registerBoolean("edgeOnly", false);
    }

    @Override public void onEnable() { setSneaking(true); }
    @Override public void onDisable() { setSneaking(false); }
    @Override public void onTick() {}

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent.Pre event) {
        if (!isToggled() || mc.player == null || mc.level == null) return;
        if (mc.player.isSpectator() || mc.player.isCreative()) return;
        if (getBoolean("edgeOnly")) {
            setSneaking(isNearEdge());
        } else {
            setSneaking(true);
        }
    }

    private boolean isNearEdge() {
        if (mc.player == null || mc.level == null) return false;
        Vec3 velocity = mc.player.getDeltaMovement();
        double vx = velocity.x;
        double vz = velocity.z;
        if (Math.abs(vx) < 0.01 && Math.abs(vz) < 0.01) return false;
        double speed = Math.sqrt(vx * vx + vz * vz);
        double checkX = mc.player.getX() + (vx / speed) * EDGE_CHECK_DIST;
        double checkZ = mc.player.getZ() + (vz / speed) * EDGE_CHECK_DIST;
        BlockPos groundPos = BlockPos.containing(checkX, mc.player.getY() - 0.01, checkZ);
        return mc.level.isEmptyBlock(groundPos);
    }

    private void setSneaking(boolean sneak) {
        if (mc.player == null) return;
        mc.player.setShiftKeyDown(sneak);
        if (mc.options != null) {
            mc.options.keyShift.setDown(sneak);
        }
    }
}