package com.staehc_remerpus.emerpus.main.modules;

import com.staehc_remerpus.emerpus.main.utils.h;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.entity.player.Player;

public class FreeCameraEntity extends RemotePlayer {

    private final Minecraft mc = Minecraft.getInstance();

    public FreeCameraEntity(Player player) {
        super((net.minecraft.client.multiplayer.ClientLevel)player.level(), player.getGameProfile());

        // Copy position and rotation
        this.setPos(player.getX(), player.getY(), player.getZ());
        this.setXRot(player.getXRot());
        this.setYRot(player.getYRot());
        this.yRotO = player.yRotO;
        this.xRotO = player.xRotO;
        this.yHeadRot = player.yHeadRot;
        this.yHeadRotO = player.yHeadRotO;

        // Copy model parts
        this.noPhysics = true;
    }

    @Override
    public void tick() {
        super.tick();

        if (mc.player == null) return;

        // Copy rotation from camera player
        this.setXRot(mc.player.getXRot());
        this.setYRot(mc.player.getYRot());
    }

    public void moveFreeCam(float speed) {
        if (mc.player == null) return;

        boolean forward = mc.options.keyUp.isDown();
        boolean backward = mc.options.keyDown.isDown();
        boolean left = mc.options.keyLeft.isDown();
        boolean right = mc.options.keyRight.isDown();
        boolean up = mc.options.keyJump.isDown();
        boolean down = mc.options.keyShift.isDown();
        boolean sprinting = mc.options.keySprint.isDown();

        h.doMotion(this, speed, speed / 2.0,
                forward, backward, left, right,
                up, down, sprinting);
    }
}
