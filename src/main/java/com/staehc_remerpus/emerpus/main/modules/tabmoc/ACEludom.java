package com.staehc_remerpus.emerpus.main.modules.tabmoc;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ACEludom extends Eludom {

    private final Random random = new Random();

    private ScheduledExecutorService leftScheduler;
    private ScheduledExecutorService rightScheduler;
    private ScheduledFuture<?>       leftTask;
    private ScheduledFuture<?>       rightTask;

    private static final int MAX_JITTER_MS = 15;

    public ACEludom() {
        //super("AutoClicker", Eludom.Category.COMBAT);
        super(d("4175746f436c69636b6572"), Eludom.Category.COMBAT);
        registerFloat("leftCPS",   12.0f, 1.0f, 20.0f);
        registerFloat("rightCPS",  12.0f, 1.0f, 20.0f);
        registerBoolean("leftClick",  true);
        registerBoolean("rightClick", false);
        registerBoolean("onlySword",  false);
        registerBoolean("blockHit",   false);
    }

    private static String d(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2)
            sb.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        return sb.toString();
    }
    @Override
    public void onEnable() {
        leftScheduler  = Executors.newSingleThreadScheduledExecutor();
        rightScheduler = Executors.newSingleThreadScheduledExecutor();
        restartTasks();
    }

    @Override
    public void onDisable() {
        cancelTasks();
        if (leftScheduler  != null) leftScheduler.shutdownNow();
        if (rightScheduler != null) rightScheduler.shutdownNow();
        leftScheduler  = null;
        rightScheduler = null;
    }

    @Override public void onTick() {}

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent.Pre event) {
        if (!isToggled()) return;
        restartTasks();
    }

    private void restartTasks() {
        if (leftScheduler == null || rightScheduler == null) return;

        if (getBoolean("leftClick")) {
            long leftDelay = cpsToDelayMs(getFloat("leftCPS"));
            if (leftTask == null || leftTask.isDone() || leftTask.isCancelled()) {
                leftTask = leftScheduler.scheduleAtFixedRate(
                        this::doLeftClick, 0, leftDelay, TimeUnit.MILLISECONDS);
            }
        } else {
            if (leftTask != null) { leftTask.cancel(false); leftTask = null; }
        }

        if (getBoolean("rightClick")) {
            long rightDelay = cpsToDelayMs(getFloat("rightCPS"));
            if (rightTask == null || rightTask.isDone() || rightTask.isCancelled()) {
                rightTask = rightScheduler.scheduleAtFixedRate(
                        this::doRightClick, 0, rightDelay, TimeUnit.MILLISECONDS);
            }
        } else {
            if (rightTask != null) { rightTask.cancel(false); rightTask = null; }
        }
    }

    private void cancelTasks() {
        if (leftTask  != null) { leftTask.cancel(false);  leftTask  = null; }
        if (rightTask != null) { rightTask.cancel(false); rightTask = null; }
    }

    private void doLeftClick() {
        try {
            if (mc.player == null || mc.level == null || mc.screen != null) return;
            if (!isToggled() || !getBoolean("leftClick")) return;

            boolean held = GLFW.glfwGetMouseButton(
                    mc.getWindow().getWindow(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
            if (!held) return;
            if (getBoolean("onlySword") && !isHoldingSword()) return;

            Entity target = mc.crosshairPickEntity;
            if (target != null && mc.player.distanceToSqr(target) <= 36) {
                mc.player.swing(InteractionHand.MAIN_HAND);
                mc.gameMode.attack(mc.player, target);
            } else {
                mc.player.swing(InteractionHand.MAIN_HAND);
            }
        } catch (Exception ignored) {}
    }

    private void doRightClick() {
        try {
            if (mc.player == null || mc.level == null || mc.screen != null) return;
            if (!isToggled() || !getBoolean("rightClick")) return;

            boolean held = GLFW.glfwGetMouseButton(
                    mc.getWindow().getWindow(), GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
            if (!held) return;

            // Skip food - in 1.21.4, check food properties differently
            if (mc.player.getMainHandItem().getItem().components().has(net.minecraft.core.component.DataComponents.FOOD)) return;
            if (mc.player.getOffhandItem().getItem().components().has(net.minecraft.core.component.DataComponents.FOOD)) return;

            net.minecraft.client.KeyMapping.click(mc.options.keyUse.getKey());
        } catch (Exception ignored) {}
    }

    private long cpsToDelayMs(float cps) {
        long base   = (long) (1000.0f / cps);
        int  maxJ   = (int) Math.min(MAX_JITTER_MS, base / 2);
        int  jitter = maxJ > 0 ? random.nextInt(maxJ * 2 + 1) - maxJ : 0;
        return Math.max(5, base + jitter);
    }

    private boolean isHoldingSword() {
        if (mc.player == null) return false;
        return mc.player.getMainHandItem().getItem() instanceof SwordItem;
    }
}