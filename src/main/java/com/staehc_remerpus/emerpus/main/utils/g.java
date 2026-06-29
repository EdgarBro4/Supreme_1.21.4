package com.staehc_remerpus.emerpus.main.utils;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.client.Minecraft;

public class g {

    private static final Minecraft mc = Minecraft.getInstance();

    public static float[] getRotationsToEntity(Entity target) {
        return getRotationsToPosition(
                target.getX(),
                target.getY() + target.getEyeHeight() / 1.5f,
                target.getZ()
        );
    }

    public static float[] getRotationsToPosition(double x, double y, double z) {
        double dx = x - mc.player.getX();
        double dy = y - (mc.player.getY() + mc.player.getEyeHeight());
        double dz = z - mc.player.getZ();

        double distance = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) (Math.atan2(dz, dx) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float) (-(Math.atan2(dy, distance) * 180.0 / Math.PI));

        return new float[]{yaw, pitch};
    }

    public static float[] smoothRotations(Entity target, float currentYaw, float yawSpeed, float pitchSpeed) {
        float[] targetRotations = getRotationsToEntity(target);
        float targetYaw = targetRotations[0];
        float targetPitch = targetRotations[1];

        boolean canRotateYaw = Mth.abs(Mth.wrapDegrees(targetYaw - currentYaw)) <= 180.0f;
        boolean canRotatePitch = Mth.abs(Mth.wrapDegrees(targetPitch - mc.player.getXRot())) <= 90.0f;

        if (canRotateYaw && canRotatePitch) {
            float yawDiff = Mth.wrapDegrees(targetYaw - currentYaw) * yawSpeed / 100.0f;
            float pitchDiff = Mth.wrapDegrees(targetPitch - mc.player.getXRot()) * pitchSpeed / 100.0f;

            return new float[]{currentYaw + yawDiff, mc.player.getXRot() + pitchDiff};
        }

        return new float[]{currentYaw, mc.player.getXRot()};
    }

    public static float getSensitivity() {
        float sens = (float) (mc.options.sensitivity().get() * 0.6 + 0.2);
        return sens * sens * sens * 8.0f;
    }

    public static float wrapAngleTo180(float angle) {
        return Mth.wrapDegrees(angle);
    }

    // --- Added from MathUtil ---

    public static float lerp(float start, float end, float t) {
        return start + (end - start) * clamp(t, 0.0f, 1.0f);
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}