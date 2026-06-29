package com.staehc_remerpus.emerpus.main.modules.misc;

import com.staehc_remerpus.emerpus.main.modules.MM;
import com.staehc_remerpus.emerpus.main.modules.visuals.LuysovPSEEludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import com.staehc_remerpus.emerpus.main.utils.f;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class YngersAxpersEludom extends Eludom {
    private final Minecraft mc = Minecraft.getInstance();

    public YngersAxpersEludom() {
        super(Hex.d("4d6964646c6520467269656e64"), Category.MISC);
    }

    @Override public void onEnable() {}
    @Override public void onDisable() {}
    @Override public void onTick() {}

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseButton.Post event) {
        if (!isToggled() || mc.player == null || mc.level == null) return;
        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_MIDDLE && event.getAction() == GLFW.GLFW_PRESS) {
            if (mc.hitResult != null && mc.hitResult.getType() == HitResult.Type.ENTITY) {
                if (mc.hitResult instanceof EntityHitResult) {
                    EntityHitResult entityResult = (EntityHitResult) mc.hitResult;
                    if (entityResult.getEntity() instanceof Player) {
                        Player player = (Player) entityResult.getEntity();
                        String playerName = player.getName().getString();
                        if (f.isFriend(player)) {
                            f.removeFriend(playerName);
                            mc.player.playSound(SoundEvents.NOTE_BLOCK_BASEDRUM.value(), 1.0f, 0.5f);
                        } else {
                            f.addFriend(playerName);
                            mc.player.playSound(SoundEvents.NOTE_BLOCK_PLING.value(), 1.0f, 2.0f);
                        }
                    }
                }
            }
        }
    }
}