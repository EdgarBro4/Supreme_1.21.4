package com.staehc_remerpus.emerpus.main.modules.tabmoc;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.staehc_remerpus.emerpus.main.utils.f;

public class XpiMecEludom extends Eludom {

    public XpiMecEludom() {
        super(d("486974426f78"), Category.COMBAT);
        //super("HitBox", Category.COMBAT);
        registerFloat("size", 0.5f, 0.1f, 2.0f);
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
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!isToggled()) return;

        Player player = event.player;
        if (player != mc.player) {
            // Don't modify hitbox for friends
            if (player instanceof Player && f.isFriend((Player) player)) {
                return;
            }

            float size = getFloat("size");
            // Apply new hitbox size
            player.setBoundingBox(new AABB(
                    player.getX() - size,
                    player.getBoundingBox().minY,
                    player.getZ() - size,
                    player.getX() + size,
                    player.getBoundingBox().maxY,
                    player.getZ() + size
            ));
        }
    }
}
