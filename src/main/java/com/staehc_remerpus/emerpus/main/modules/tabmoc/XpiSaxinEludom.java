package com.staehc_remerpus.emerpus.main.modules.tabmoc;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.k;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.client.event.AddFramePassEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.framegraph.FramePass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.staehc_remerpus.emerpus.main.utils.f;

public class XpiSaxinEludom extends Eludom {

    private k attackTimer = new k();

    public XpiSaxinEludom() {
        super(d("4b696c6c41757261"), Eludom.Category.COMBAT);
        //super("KillAura", Eludom.Category.COMBAT);
        registerFloat("range", 4.5f, 3.0f, 6.0f);
        registerBoolean("players", true);
        registerBoolean("mobs", false);
        registerBoolean("onlyCrits", false);
        registerBoolean("pvp19", false);
        registerFloat("cps", 12.0f, 1.0f, 20.0f);
        registerBoolean("onlySword", false);
    }
    private static String d(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2)
            sb.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        return sb.toString();
    }
    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public void onTick() {}

    @SubscribeEvent
    public void onRenderLevel(AddFramePassEvent e) {
        
        if (!isToggled()) return;
        if (mc.player == null) return;
        if (mc.level == null) return;

        // Check sword-only mode
        if (getBoolean("onlySword") && !isHoldingSword()) {
            return;
        }

        float range = getFloat("range");
        boolean onlyCritsMode = getBoolean("onlyCrits");
        boolean pvp19 = getBoolean("pvp19");
        float cps = getFloat("cps");

        // Calculate delay between attacks (1000ms / CPS)
        int attackDelay = (int) (1000.0 / cps);

        // Check if enough time has passed since last attack
        if (!attackTimer.hasPassed(attackDelay)) {
            return;
        }

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity == mc.player) continue;
            if (entity.isInvisible()) continue;

            boolean shouldAttack = false;
            boolean attackPlayers = getBoolean("players");
            boolean attackMobs = getBoolean("mobs");

            if (entity instanceof Player && attackPlayers) {
                // Don't attack friends
                if (!f.isFriend((Player) entity)) {
                    shouldAttack = true;
                }
            } else if (attackMobs && (entity instanceof Monster || entity instanceof Animal)) {
                shouldAttack = true;
            }

            if (!shouldAttack) continue;

            double distance = mc.player.distanceTo(entity);
            if (distance <= range) {

                boolean isCritting = mc.player.fallDistance > 0.0f && !mc.player.onGround() && !mc.player.isInWater();
                if (onlyCritsMode && !isCritting) continue;

                // Check cooldown for 1.9 mode
                if (pvp19 && mc.player.getAttackStrengthScale(0.0f) < 1.0f) continue;

                // Attack
                mc.gameMode.attack(mc.player, entity);
                mc.player.swing(InteractionHand.MAIN_HAND);

                if (pvp19) {
                    mc.player.resetAttackStrengthTicker();
                }

                attackTimer.reset();
                break;
            }
        }
    }

    private boolean isHoldingSword() {
        if (mc.player == null) return false;
        return mc.player.getMainHandItem().getItem() instanceof SwordItem;
    }
}