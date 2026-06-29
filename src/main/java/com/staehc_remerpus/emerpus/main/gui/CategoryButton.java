package com.staehc_remerpus.emerpus.main.gui;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.ConfigManager;
import com.staehc_remerpus.emerpus.main.utils.e;
import com.staehc_remerpus.emerpus.main.utils.j;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.staehc_remerpus.emerpus.main.gui.ClickGuiManager.*;

public class CategoryButton {
    public static ArrayList<ModuleButton> moduleButtons = new ArrayList<>();
    public static double scrollOffset = 35;
    public static File[] configs = null;

    public Minecraft mc = Minecraft.getInstance();
    public int x, y, width, height;
    public Eludom.Category category;

    public CategoryButton(int x, int y, int width, int height, Eludom.Category category) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.category = category;
    }

    public static void refreshConfigs() {
        ConfigManager.init();
        configs = ConfigManager.CONFIG_DIR.listFiles((dir, name) -> name.endsWith(".json"));
    }

    public void render(GuiGraphics graphics) {
        boolean isSelected = Objects.equals(lastCategory, category.name());

        if (isSelected) {
            moduleButtons.clear();

            if (category == Eludom.Category.CONFIGS) {
                // Draw Configs UI directly in the center panel!
                if (configs == null) refreshConfigs();

                double currentY = dragY + scrollOffset;
                int panelX = (int) (dragX + CATEGORY_WIDTH + 45);
                int panelWidth = MODULE_WIDTH;

                // Culling helper
                java.util.function.Predicate<Double> isVisible = (y) -> y > dragY + 30 && y < dragY + GUI_HEIGHT - 30;

                // Create New Config Button
                if (isVisible.test(currentY)) {
                    j.drawSmoothRect(graphics, panelX, currentY, panelX + panelWidth, currentY + 20, e.accentColor.getRGB());
                    graphics.drawString(mc.font, "Create New Config", panelX + panelWidth / 2 - mc.font.width("Create New Config") / 2, (float)(currentY + 6), Color.WHITE.getRGB(), false);
                }
                currentY += 25;

                // Open Folder Button
                if (isVisible.test(currentY)) {
                    j.drawSmoothRect(graphics, panelX, currentY, panelX + panelWidth, currentY + 20, new Color(40, 40, 40).getRGB());
                    graphics.drawString(mc.font, "Open Config Folder", panelX + panelWidth / 2 - mc.font.width("Open Config Folder") / 2, (float)(currentY + 6), Color.LIGHT_GRAY.getRGB(), false);
                }
                currentY += 25;

                if (configs != null) {
                    for (File config : configs) {
                        if (isVisible.test(currentY)) {
                            String name = config.getName().replace(".json", "");
                            
                            // Background
                            j.drawSmoothRect(graphics, panelX, currentY, panelX + panelWidth, currentY + 20, new Color(30, 30, 30).getRGB());
                            graphics.drawString(mc.font, name, panelX + 5, (float)(currentY + 6), e.textColor, false);

                            // Load Button
                            int loadX = panelX + panelWidth - 70;
                            j.drawSmoothRect(graphics, loadX, currentY + 2, loadX + 30, currentY + 18, new Color(40, 150, 40).getRGB());
                            graphics.drawString(mc.font, "Load", loadX + 4, (float)(currentY + 5), Color.WHITE.getRGB(), false);

                            // Delete Button
                            int delX = panelX + panelWidth - 35;
                            j.drawSmoothRect(graphics, delX, currentY + 2, delX + 30, currentY + 18, new Color(150, 40, 40).getRGB());
                            graphics.drawString(mc.font, "Del", delX + 7, (float)(currentY + 5), Color.WHITE.getRGB(), false);
                        }
                        currentY += 25;
                    }
                }
            } else {
                List<Eludom> modulesInCat = com.staehc_remerpus.emerpus.main.modules.MM.getModulesByCategory(category);

                double buttonY = dragY + scrollOffset;
                int moduleX = (int) (dragX + CATEGORY_WIDTH + 45);

                for (Eludom eludom : modulesInCat) {
                    moduleButtons.add(new ModuleButton(
                            moduleX,
                            (int) buttonY,
                            MODULE_WIDTH,
                            24,
                            eludom));
                    buttonY += 25;
                }

                for (ModuleButton b : moduleButtons) {
                    if (b.y > dragY + 30 && b.y < dragY + GUI_HEIGHT - 30) {
                        b.render(graphics);
                    }
                }
            }
        }

        // Category button background
        j.drawSmoothRect(graphics, x - 10, y + 2, x + width + 10, y + height - 2,
                isSelected ? e.accentColor.getRGB() : new Color(40, 40, 40).getRGB());

        // Category button text
        graphics.drawString(mc.font, category.name(),
                x + width / 2 - mc.font.width(category.name()) / 2,
                y + height / 2 - 9 / 2,
                isSelected ? Color.WHITE.getRGB() : new Color(180, 180, 180).getRGB());
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (HoverUtils.hovered((int) mouseX, (int) mouseY, x - 10, y + 2, x + width + 10, y + height - 2)) {
            if (button == 0) {
                scrollOffset = 35;
                lastCategory = category.name();
                moduleButtons.clear();
                if (category == Eludom.Category.CONFIGS) refreshConfigs();
            }
        } else if (Objects.equals(lastCategory, category.name()) && category == Eludom.Category.CONFIGS) {
            // Forward clicks to Configs UI if within visible scroll area
            if (mouseY > dragY + 30 && mouseY < dragY + GUI_HEIGHT - 30) {
                double currentY = dragY + scrollOffset;
                int panelX = (int) (dragX + CATEGORY_WIDTH + 45);
                int panelWidth = MODULE_WIDTH;

                // Create New Config
                if (mouseX >= panelX && mouseX <= panelX + panelWidth && mouseY >= currentY && mouseY <= currentY + 20) {
                    if (mc.screen != null) {
                        mc.setScreen(new ConfigStringScreen("Enter New Config Name", mc.screen, name -> {
                            if (name != null && !name.trim().isEmpty()) {
                                ConfigManager.saveConfig(name.trim());
                                refreshConfigs();
                            }
                        }));
                    }
                    return;
                }
                currentY += 25;

                // Open Folder
                if (mouseX >= panelX && mouseX <= panelX + panelWidth && mouseY >= currentY && mouseY <= currentY + 20) {
                    ConfigManager.openFolder();
                    return;
                }
                currentY += 25;

                if (configs != null) {
                    for (File config : configs) {
                        int loadX = panelX + panelWidth - 70;
                        int delX = panelX + panelWidth - 35;

                        if (mouseX >= loadX && mouseX <= loadX + 30 && mouseY >= currentY + 2 && mouseY <= currentY + 18) {
                            ConfigManager.loadConfig(config.getName().replace(".json", ""));
                            return;
                        }
                        if (mouseX >= delX && mouseX <= delX + 30 && mouseY >= currentY + 2 && mouseY <= currentY + 18) {
                            ConfigManager.deleteConfig(config.getName().replace(".json", ""));
                            refreshConfigs();
                            return;
                        }
                        currentY += 25;
                    }
                }
            }
        }
    }

    public void mouseScrolled(double delta) {
        if (delta > 0) { // Scroll UP
            if (scrollOffset != 35) {
                scrollOffset += 1;
            }
        } else { // Scroll DOWN
            if (category == Eludom.Category.CONFIGS) {
                int items = 2 + (configs != null ? configs.length : 0);
                double totalHeight = items * 25;
                if (totalHeight > GUI_HEIGHT - 52) {
                    scrollOffset -= 1;
                }
            } else if (moduleButtons.size() > 0) {
                ModuleButton last = moduleButtons.get(moduleButtons.size() - 1);
                if (!(last.y - dragY <= GUI_HEIGHT - 52)) {
                    scrollOffset -= 1;
                }
            }
        }
    }
}
