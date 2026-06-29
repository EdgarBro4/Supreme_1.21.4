package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import net.minecraftforge.client.event.AddFramePassEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.framegraph.FramePass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LuysovPSEEludom extends Eludom {
    // This requires glowing effect handling. Since GlowESP in 1.16 used Entity.setGlowing(true), we can just use setGlowing.
    public LuysovPSEEludom() {
        super(Hex.d("476c6f77455350"), Category.VISUALS);
        registerBoolean("mobs", false);
        registerBoolean("invis", false);
    }

    @Override public void onEnable() {}
    private final java.util.Map<String, net.minecraft.world.scores.PlayerTeam> originalTeams = new java.util.HashMap<>();

    @Override public void onDisable() {
        if (mc.level != null) {
            net.minecraft.world.scores.Scoreboard scoreboard = mc.level.getScoreboard();
            for (net.minecraft.world.entity.Entity entity : mc.level.entitiesForRendering()) {
                setGlowing(entity, false);
            }
            for (java.util.Map.Entry<String, net.minecraft.world.scores.PlayerTeam> entry : originalTeams.entrySet()) {
                String name = entry.getKey();
                net.minecraft.world.scores.PlayerTeam orig = entry.getValue();
                if (orig != null && scoreboard.getPlayerTeam(orig.getName()) != null) {
                    try { scoreboard.addPlayerToTeam(name, orig); } catch (Exception e) {}
                } else {
                    try { scoreboard.removePlayerFromTeam(name); } catch (Exception e) {}
                }
            }
        }
        originalTeams.clear();
    }
    @Override public void onTick() {}

    @SubscribeEvent
    public void onClientTickPre(net.minecraftforge.event.TickEvent.ClientTickEvent.Pre event) {
        if (mc.level == null) {
            originalTeams.clear();
            return;
        }
        net.minecraft.world.scores.Scoreboard scoreboard = mc.level.getScoreboard();
        for (java.util.Map.Entry<String, net.minecraft.world.scores.PlayerTeam> entry : originalTeams.entrySet()) {
            String name = entry.getKey();
            net.minecraft.world.scores.PlayerTeam orig = entry.getValue();
            if (orig != null && scoreboard.getPlayerTeam(orig.getName()) != null) {
                try { scoreboard.addPlayerToTeam(name, orig); } catch (Exception e) {}
            } else {
                try { scoreboard.removePlayerFromTeam(name); } catch (Exception e) {}
            }
        }
        originalTeams.clear();
    }

    @SubscribeEvent
    public void onTickEvent(net.minecraftforge.event.TickEvent.ClientTickEvent.Post event) {
        if (!isToggled()) return;
        
        if (mc.player == null || mc.level == null) return;
        
        net.minecraft.world.scores.Scoreboard scoreboard = mc.level.getScoreboard();
        net.minecraft.world.scores.PlayerTeam enemyTeam = scoreboard.getPlayerTeam("s_e");
        if (enemyTeam == null) {
            enemyTeam = scoreboard.addPlayerTeam("s_e");
            enemyTeam.setColor(net.minecraft.ChatFormatting.RED);
        }
        
        boolean showMobs = getBoolean("mobs");
        boolean showInvis = getBoolean("invis");
        
        for (net.minecraft.world.entity.Entity entity : mc.level.entitiesForRendering()) {
            if (entity != mc.player) {
                if (!showInvis && entity.isInvisible()) {
                    setGlowing(entity, false);
                    continue;
                }
                
                if (entity instanceof net.minecraft.world.entity.player.Player) {
                    net.minecraft.world.entity.player.Player p = (net.minecraft.world.entity.player.Player) entity;
                    String sbName = entity.getScoreboardName();
                    
                    net.minecraft.world.scores.PlayerTeam currentTeam = scoreboard.getPlayersTeam(sbName);
                    
                    if (com.staehc_remerpus.emerpus.main.utils.f.isFriend(p)) {
                        if (currentTeam == null || !currentTeam.getName().equals("s_f")) {
                            if (!originalTeams.containsKey(sbName)) originalTeams.put(sbName, currentTeam);
                            com.staehc_remerpus.emerpus.main.utils.f.addToFriendTeam(p);
                        }
                        setGlowing(entity, true);
                    } else {
                        if (currentTeam != enemyTeam) {
                            if (!originalTeams.containsKey(sbName)) originalTeams.put(sbName, currentTeam);
                            try { scoreboard.addPlayerToTeam(sbName, enemyTeam); } catch (Exception e) {}
                        }
                        setGlowing(entity, true);
                    }
                } else if (showMobs && (entity instanceof net.minecraft.world.entity.monster.Monster || entity instanceof net.minecraft.world.entity.animal.Animal)) {
                    String sbName = entity.getScoreboardName();
                    net.minecraft.world.scores.PlayerTeam currentTeam = scoreboard.getPlayersTeam(sbName);
                    if (currentTeam != null && !currentTeam.getName().equals("s_e")) {
                        if (!originalTeams.containsKey(sbName)) originalTeams.put(sbName, currentTeam);
                        try { scoreboard.removePlayerFromTeam(sbName); } catch (Exception e) {}
                    }
                    setGlowing(entity, true);
                } else {
                    setGlowing(entity, false);
                }
            }
        }
    }

    private void setGlowing(net.minecraft.world.entity.Entity entity, boolean glowing) {
        entity.setGlowingTag(glowing);
        try {
            java.lang.reflect.Method m;
            try {
                m = net.minecraft.world.entity.Entity.class.getDeclaredMethod("setSharedFlag", int.class, boolean.class);
            } catch (NoSuchMethodException e) {
                m = net.minecraft.world.entity.Entity.class.getDeclaredMethod("m_20116_", int.class, boolean.class);
            }
            m.setAccessible(true);
            m.invoke(entity, 6, glowing);
        } catch (Exception e) {}
    }
}