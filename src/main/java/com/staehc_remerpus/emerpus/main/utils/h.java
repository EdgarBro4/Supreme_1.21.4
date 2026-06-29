package com.staehc_remerpus.emerpus.main.utils;

import net.minecraft.world.entity.Entity;

public class h {

    public static final double DIAGONAL_MULTIPLIER = Math.sqrt(2.0) / 2.0; // 0.7071...

    public static void doMotion(Entity entity, double hSpeed, double vSpeed,
                                boolean forward, boolean backward, boolean left, boolean right,
                                boolean up, boolean down, boolean sprinting) {

        float yaw = entity.getYRot();

        double velX = 0;
        double velY = 0;
        double velZ = 0;

        // Forward/Backward vectors based on yaw
        double forwardX = -Math.sin(Math.toRadians(yaw));
        double forwardZ = Math.cos(Math.toRadians(yaw));

        // Strafe vectors (perpendicular to forward)
        double strafeX = forwardZ;
        double strafeZ = -forwardX;

        // Apply sprint boost
        double speedMultiplier = sprinting ? 1.5 : 1.0;
        hSpeed *= speedMultiplier;

        boolean movingForward = false;
        boolean strafing = false;

        if (forward) {
            velX += forwardX * hSpeed;
            velZ += forwardZ * hSpeed;
            movingForward = true;
        }
        if (backward) {
            velX -= forwardX * hSpeed;
            velZ -= forwardZ * hSpeed;
            movingForward = true;
        }
        if (left) {
            velX += strafeX * hSpeed;
            velZ += strafeZ * hSpeed;
            strafing = true;
        }
        if (right) {
            velX -= strafeX * hSpeed;
            velZ -= strafeZ * hSpeed;
            strafing = true;
        }

        // Apply diagonal multiplier if moving both forward/back and strafing
        if (movingForward && strafing) {
            velX *= DIAGONAL_MULTIPLIER;
            velZ *= DIAGONAL_MULTIPLIER;
        }

        if (up) {
            velY += vSpeed;
        }
        if (down) {
            velY -= vSpeed;
        }

        entity.setDeltaMovement(velX, velY, velZ);
    }
}