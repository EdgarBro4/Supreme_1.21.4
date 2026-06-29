package com.staehc_remerpus.emerpus.main.gui.settings.visuals;

import com.staehc_remerpus.emerpus.main.gui.ModuleButton;
import com.staehc_remerpus.emerpus.main.gui.settings.SettingsPanel;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import net.minecraft.client.gui.GuiGraphics;

public class XRaySettings extends SettingsPanel {

    // All settings in display order
    private static final String[] BOOL_KEYS = {
            "diamond", "iron", "gold", "emerald",
            "lapis", "redstone", "coal", "nether_quartz",
            "ancient_debris", "spawner"
    };

    private static final String[] BOOL_LABELS = {
            "diamond:", "iron:", "gold:", "emerald:",
            "lapis:", "redstone:", "coal:", "n.quartz:",
            "anc.debris:", "spawner:"
    };

    private static final int LINE = 14; // px per row
    private static final float RADIUS_MIN = 5f;
    private static final float RADIUS_MAX = 64f;

    public XRaySettings(Eludom eludom, ModuleButton button) {
        super(eludom, button);
    }

    // ── Rendering ─────────────────────────────────────────────────────────────

    @Override
    public void render(GuiGraphics graphics) {
        int toggleX = panelX + 65;
        int y = currentY;

        // ── Boolean toggles ────────────────────────────────────────────────
        for (int i = 0; i < BOOL_KEYS.length; i++) {
            if (isVisible(y)) {
                graphics.drawString(mc.font, BOOL_LABELS[i], panelX + 5, y, e.textColor, false);
                drawToggle(graphics, toggleX, y - 4, eludom.getBoolean(BOOL_KEYS[i]));
            }
            y += LINE;
        }

        // ── Radius slider ──────────────────────────────────────────────────
        if (isVisible(y)) {
            graphics.drawString(mc.font, "radius:", panelX + 5, y, e.textColor, false);
            float radius = eludom.getFloat("radius");
            // slider track from x+5, width 55, height 4; value label to its right
            drawSlider(graphics,
                    panelX + 5, y + 10, 55, 4,
                    radius, RADIUS_MIN, RADIUS_MAX,
                    panelX + 62, y + 9,
                    "");
        }
        // slider row takes extra space (label line + slider line)
        // y += LINE * 2; (not needed — getHeight handles total)
    }

    // ── Mouse input ───────────────────────────────────────────────────────────

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button, double adjustedMouseY) {
        int y = currentY;

        // Boolean toggles
        for (String key : BOOL_KEYS) {
            checkToggle(mouseX, adjustedMouseY, y, panelX, key, button);
            y += LINE;
        }

        // Radius slider — click starts drag
        int sliderX = panelX + 5;
        int sliderY = y + 10;
        if (mouseX >= sliderX && mouseX <= sliderX + 55 &&
                adjustedMouseY >= sliderY - 3 && adjustedMouseY <= sliderY + 7) {
            startSliderDrag("radius", RADIUS_MIN, RADIUS_MAX);
        }
    }

    // ── Height ────────────────────────────────────────────────────────────────

    @Override
    public int getHeight() {
        // 10 toggle rows + 1 label row for radius + 1 slider row
        return BOOL_KEYS.length * LINE + LINE * 2;
    }
}
