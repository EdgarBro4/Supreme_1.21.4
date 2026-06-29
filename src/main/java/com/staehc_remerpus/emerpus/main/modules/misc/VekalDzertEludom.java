package com.staehc_remerpus.emerpus.main.modules.misc;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VekalDzertEludom extends Eludom {
    private int totemSlot = -1;
    private int ticksSinceLastCheck = 0;
    private boolean hasTotemInOffhand = false;
    private boolean legitModeActive = false;
    private int legitModeTimer = 0;
    private int legitStage = 0;
    private boolean autoDisableActive = false;
    private int autoDisableTimer = 0;
    private boolean pendingDisable = false;

    public VekalDzertEludom() {
        super(Hex.d("4175746f546f74656d"), Eludom.Category.MISC);
        registerFloat("health", 10.0f, 2.0f, 20.0f);
        registerBoolean("legit", false);
        registerFloat("delay", 2.0f, 1.0f, 10.0f);
        registerBoolean("autoDisable", false);
    }

    @Override public void onEnable() { pendingDisable = false; autoDisableActive = false; legitModeActive = false; legitStage = 0; }
    @Override public void onDisable() {
        if (mc.player != null && mc.screen instanceof InventoryScreen) mc.player.closeContainer();
    }
    @Override public void onTick() {}

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent.Pre event) {
        if (!isToggled() || mc.player == null || mc.gameMode == null) return;
        boolean autoDisable = getBoolean("autoDisable");
        if (pendingDisable && !legitModeActive && legitStage == 0) { completeAutoDisable(); return; }
        if (autoDisable && autoDisableActive) { handleAutoDisable(); return; }
        if (legitModeActive) { handleLegitMode(); return; }
        ticksSinceLastCheck++;
        if (ticksSinceLastCheck < 5) return;
        ticksSinceLastCheck = 0;
        ItemStack offhand = mc.player.getOffhandItem();
        hasTotemInOffhand = offhand.getItem() == Items.TOTEM_OF_UNDYING;
        if (hasTotemInOffhand) return;
        float currentHealth = mc.player.getHealth();
        float threshold = getFloat("health");
        if (currentHealth <= threshold) {
            findTotemInInventory();
            if (totemSlot != -1) {
                if (getBoolean("legit")) {
                    startLegitMode();
                    if (autoDisable) pendingDisable = true;
                } else {
                    moveTotemToOffhand();
                    if (autoDisable) startAutoDisable();
                }
            }
        }
    }

    private void findTotemInInventory() {
        totemSlot = -1;
        Inventory inventory = mc.player.getInventory();
        for (int i = 9; i < 36; i++) { if (inventory.getItem(i).getItem() == Items.TOTEM_OF_UNDYING) { totemSlot = i; return; } }
        for (int i = 0; i < 9; i++) { if (inventory.getItem(i).getItem() == Items.TOTEM_OF_UNDYING) { totemSlot = i; return; } }
    }

    private void moveTotemToOffhand() {
        if (totemSlot == -1 || mc.player == null) return;
        AbstractContainerMenu container = mc.player.containerMenu;
        int containerSlot = totemSlot < 9 ? 36 + totemSlot : totemSlot;
        mc.gameMode.handleInventoryMouseClick(container.containerId, containerSlot, 40, ClickType.SWAP, mc.player);
        hasTotemInOffhand = true;
    }

    private void startLegitMode() {
        if (mc.player == null) return;
        if (!(mc.screen instanceof InventoryScreen)) {
            mc.setScreen(new InventoryScreen(mc.player));
            legitModeActive = true; legitStage = 1; legitModeTimer = 0;
        }
    }

    private void handleLegitMode() {
        if (mc.player == null) return;
        legitModeTimer++;
        switch (legitStage) {
            case 1:
                if (legitModeTimer >= (int) getFloat("delay") && mc.screen instanceof InventoryScreen) {
                    AbstractContainerMenu container = mc.player.containerMenu;
                    int containerSlot = totemSlot < 9 ? 36 + totemSlot : totemSlot;
                    mc.gameMode.handleInventoryMouseClick(container.containerId, containerSlot, 0, ClickType.PICKUP, mc.player);
                    legitStage = 2; legitModeTimer = 0;
                }
                break;
            case 2:
                if (legitModeTimer >= (int) getFloat("delay")) {
                    mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, 45, 0, ClickType.PICKUP, mc.player);
                    hasTotemInOffhand = true; legitStage = 3; legitModeTimer = 0;
                }
                break;
            case 3:
                if (legitModeTimer >= (int) getFloat("delay")) {
                    if (mc.screen instanceof InventoryScreen) mc.player.closeContainer();
                    legitModeActive = false; legitStage = 0; legitModeTimer = 0;
                }
                break;
        }
    }

    private void startAutoDisable() { autoDisableActive = true; autoDisableTimer = 0; }
    private void handleAutoDisable() { autoDisableTimer++; if (autoDisableTimer >= 1) completeAutoDisable(); }
    private void completeAutoDisable() { this.toggle(); autoDisableActive = false; pendingDisable = false; autoDisableTimer = 0; }
}