package com.staehc_remerpus.emerpus.main.modules.misc;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HaqiTevertEludom extends Eludom {
    private final Minecraft mc = Minecraft.getInstance();
    private static final int CHEST_ARMOR_INV_SLOT = 38;
    private static final int CHEST_ARMOR_CONTAINER_SLOT = 6;

    private boolean legitModeActive = false;
    private int legitModeTimer = 0;
    private int legitStage = 0;
    private int targetSlot = -1;
    private int targetContainerSlot = -1;
    private boolean chestSlotEmpty = false;

    private boolean fireworkPending = false;
    private int fireworkWaitTimer = 0;
    private static final int MAX_FIREWORK_WAIT = 120;

    public HaqiTevertEludom() {
        super(Hex.d("456c7974726153776170"), Category.MISC);
        registerFloat("delay", 10.0f, 1.0f, 1000.0f);
        registerBoolean("legit", false);
        registerBoolean("autoFW", false);
    }

    @Override public void onEnable() { resetState(); performSwap(); }
    @Override public void onDisable() {
        if (mc.player != null && mc.screen instanceof InventoryScreen) mc.player.closeContainer();
        resetState();
    }
    @Override public void onTick() {}

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent.Pre event) {
        if (!isToggled() || mc.player == null) return;
        if (legitModeActive) handleLegitMode();
        if (fireworkPending) handleFireworkPending();
    }

    private void performSwap() {
        if (mc.player == null || mc.gameMode == null) return;
        boolean willEquipElytra = willResultInElytra();
        if (getBoolean("legit")) {
            findTargetItem();
            if (targetSlot != -1) startLegitMode(willEquipElytra);
            else this.toggle();
        } else {
            performInstantSwap(willEquipElytra);
        }
    }

    private boolean willResultInElytra() {
        if (mc.player == null) return false;
        ItemStack worn = mc.player.getInventory().getItem(CHEST_ARMOR_INV_SLOT);
        return worn.isEmpty() || isChestplate(worn.getItem());
    }

    private void performInstantSwap(boolean willEquipElytra) {
        if (mc.player == null || mc.gameMode == null) return;
        InventoryMenu container = mc.player.inventoryMenu;
        ItemStack worn = container.slots.get(CHEST_ARMOR_CONTAINER_SLOT).getItem();
        if (!worn.isEmpty()) {
            if (isElytra(worn.getItem())) swapArmorSlotWith(container, this::isChestplate);
            else if (isChestplate(worn.getItem())) swapArmorSlotWith(container, this::isElytra);
        } else {
            for (Slot slot : container.slots) {
                Item item = slot.getItem().getItem();
                if (isElytra(item) || isChestplate(item)) {
                    mc.gameMode.handleInventoryMouseClick(container.containerId, slot.index, 0, ClickType.QUICK_MOVE, mc.player);
                    break;
                }
            }
        }
        if (willEquipElytra && getBoolean("autoFW")) armFirework();
        this.toggle();
    }

    private void swapArmorSlotWith(InventoryMenu container, java.util.function.Predicate<Item> pred) {
        for (Slot slot : container.slots) {
            if (!pred.test(slot.getItem().getItem())) continue;
            mc.gameMode.handleInventoryMouseClick(container.containerId, slot.index, 0, ClickType.PICKUP, mc.player);
            mc.gameMode.handleInventoryMouseClick(container.containerId, CHEST_ARMOR_CONTAINER_SLOT, 0, ClickType.PICKUP, mc.player);
            mc.gameMode.handleInventoryMouseClick(container.containerId, slot.index, 0, ClickType.PICKUP, mc.player);
            break;
        }
    }

    private void findTargetItem() {
        targetSlot = -1; chestSlotEmpty = false;
        if (mc.player == null) return;
        ItemStack worn = mc.player.getInventory().getItem(CHEST_ARMOR_INV_SLOT);
        chestSlotEmpty = worn.isEmpty();
        if (!chestSlotEmpty) {
            if (isElytra(worn.getItem())) findItemInInventory(this::isChestplate);
            else if (isChestplate(worn.getItem())) findItemInInventory(this::isElytra);
        } else findItemInInventory(this::isElytra);
    }

    private void findItemInInventory(java.util.function.Predicate<Item> pred) {
        if (mc.player == null) return;
        Inventory inv = mc.player.getInventory();
        for (int i = 9; i < 36; i++) if (pred.test(inv.getItem(i).getItem())) { targetSlot = i; return; }
        for (int i = 0; i < 9; i++) if (pred.test(inv.getItem(i).getItem())) { targetSlot = i; return; }
    }

    private void startLegitMode(boolean willEquipElytra) {
        if (mc.player == null) return;
        if (!(mc.screen instanceof InventoryScreen)) mc.setScreen(new InventoryScreen(mc.player));
        targetContainerSlot = (targetSlot < 9) ? (36 + targetSlot) : targetSlot;
        if (willEquipElytra && getBoolean("autoFW")) armFirework();
        legitModeActive = true; legitStage = 1; legitModeTimer = 0;
    }

    private void handleLegitMode() {
        if (mc.player == null) return;
        legitModeTimer++;
        switch (legitStage) {
            case 1:
                if (legitModeTimer >= 3 && mc.screen instanceof InventoryScreen) {
                    mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, targetContainerSlot, 0, ClickType.PICKUP, mc.player);
                    legitStage = 2; legitModeTimer = 0;
                } break;
            case 2:
                if (legitModeTimer >= 2) {
                    mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, CHEST_ARMOR_CONTAINER_SLOT, 0, ClickType.PICKUP, mc.player);
                    legitStage = chestSlotEmpty ? 4 : 3; legitModeTimer = 0;
                } break;
            case 3:
                if (legitModeTimer >= 2) {
                    mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId, targetContainerSlot, 0, ClickType.PICKUP, mc.player);
                    legitStage = 4; legitModeTimer = 0;
                } break;
            case 4:
                if (legitModeTimer >= 2) {
                    if (mc.screen instanceof InventoryScreen) mc.player.closeContainer();
                    legitStage = 5; legitModeTimer = 0;
                } break;
            case 5:
                if (legitModeTimer >= getFloat("delay") / 50f) {
                    legitModeActive = false; legitStage = 0; legitModeTimer = 0;
                    this.toggle();
                } break;
        }
    }

    private void armFirework() { fireworkPending = true; fireworkWaitTimer = 0; }

    private void handleFireworkPending() {
        if (mc.player == null) { fireworkPending = false; return; }
        fireworkWaitTimer++;
        if (fireworkWaitTimer > MAX_FIREWORK_WAIT) { fireworkPending = false; return; }
        if (mc.player.onGround() || !mc.player.isFallFlying()) return;
        int rocketSlot = findRocketInInventory();
        if (rocketSlot == -1) { fireworkPending = false; return; }
        int prevHotbar = mc.player.getInventory().selected;
        if (rocketSlot < 9) {
            mc.player.getInventory().selected = rocketSlot;
            mc.gameMode.useItem(mc.player, InteractionHand.MAIN_HAND);
            mc.player.getInventory().selected = prevHotbar;
        }
        fireworkPending = false;
    }

    private int findRocketInInventory() {
        if (mc.player == null) return -1;
        Inventory inv = mc.player.getInventory();
        for (int i = 0; i < 9; i++) if (inv.getItem(i).getItem() == Items.FIREWORK_ROCKET) return i;
        for (int i = 9; i < 36; i++) if (inv.getItem(i).getItem() == Items.FIREWORK_ROCKET) return i;
        return -1;
    }

    private void resetState() {
        legitModeActive = false; legitModeTimer = 0; legitStage = 0;
        targetSlot = -1; targetContainerSlot = -1; chestSlotEmpty = false;
        fireworkPending = false; fireworkWaitTimer = 0;
    }

    private boolean isElytra(Item item) { return item == Items.ELYTRA; }
    private boolean isChestplate(Item item) {
        return item == Items.CHAINMAIL_CHESTPLATE || item == Items.DIAMOND_CHESTPLATE
            || item == Items.GOLDEN_CHESTPLATE || item == Items.IRON_CHESTPLATE
            || item == Items.LEATHER_CHESTPLATE || item == Items.NETHERITE_CHESTPLATE;
    }
}