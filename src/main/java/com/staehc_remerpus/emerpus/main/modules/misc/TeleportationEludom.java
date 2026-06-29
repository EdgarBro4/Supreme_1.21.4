package com.staehc_remerpus.emerpus.main.modules.misc;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import com.staehc_remerpus.emerpus.main.utils.k;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.util.Random;

public class TeleportationEludom extends Eludom {
    private int previousSlot = 0;
    private final k timer = new k();
    private boolean waiting = false;

    public TeleportationEludom() {
        super(Hex.d("4d6964646c65506561726c"), Eludom.Category.MISC);
        registerFloat("delay", 50.0f, 30.0f, 500.0f);
        registerBoolean("randomize", true);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent.Post event) {
        if (!isToggled() || mc.player == null || mc.level == null) return;
        if (waiting) {
            int randomAdd = 0;
            if (getBoolean("randomize")) randomAdd = 50 + new Random().nextInt(100);
            if (timer.hasPassed(getFloat("delay") + randomAdd)) {
                mc.player.getInventory().selected = previousSlot;
                waiting = false;
                timer.kWd(false);
            }
        }
    }

    @SubscribeEvent
    public void onMiddleClick(InputEvent.MouseButton.Post event) {
        if (!isToggled() || mc.player == null || mc.level == null || waiting) return;
        if (event.getButton() == 2 && event.getAction() == 1) {
            previousSlot = mc.player.getInventory().selected;
            for (int slot = 0; slot < 9; slot++) {
                ItemStack stack = mc.player.getInventory().getItem(slot);
                if (stack.getItem() == Items.ENDER_PEARL && mc.screen == null) {
                    mc.player.getInventory().selected = slot;
                    mc.gameMode.useItem(mc.player, InteractionHand.MAIN_HAND);
                    timer.reset();
                    timer.kWd(true);
                    waiting = true;
                    break;
                }
            }
        }
    }

    @Override public void onEnable() {}
    @Override public void onDisable() {}
    @Override public void onTick() {}
}