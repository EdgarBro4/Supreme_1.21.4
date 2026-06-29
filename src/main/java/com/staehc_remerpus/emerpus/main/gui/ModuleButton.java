package com.staehc_remerpus.emerpus.main.gui;

import com.staehc_remerpus.emerpus.main.gui.settings.*;
import com.staehc_remerpus.emerpus.main.gui.settings.tabmoc.*;
import com.staehc_remerpus.emerpus.main.gui.settings.misc.*;
import com.staehc_remerpus.emerpus.main.gui.settings.movement.AragSarqiSettings;
import com.staehc_remerpus.emerpus.main.gui.settings.movement.RunVasyaRunSettings;
import com.staehc_remerpus.emerpus.main.gui.settings.movement.SkazlaRunVasyaSettings;
import com.staehc_remerpus.emerpus.main.gui.settings.visuals.*;
import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.e;
import com.staehc_remerpus.emerpus.main.utils.j;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import org.lwjgl.glfw.GLFW;
import java.awt.Color;

import static com.staehc_remerpus.emerpus.main.gui.ClickGuiManager.*;

public class ModuleButton {
    // String decoder - keeps module names out of memory as plain text
    private static String d(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2)
            sb.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        return sb.toString();
    }

    public Minecraft mc = Minecraft.getInstance();
    public int x, y, width, height;
    public Eludom eludom;

    public static boolean showSettings = false;
    public static Eludom selectedEludom = null;
    public static boolean isBinding = false;
    public static Eludom bindingEludom = null;

    // Settings panel scroll
    private static int settingsScrollOffset = 0;
    // Slider drag state
    private static boolean isDraggingSlider = false;
    private static Eludom sliderEludom = null;
    private static String sliderKey = null;
    private static float sliderMin = 0, sliderMax = 0;

    public ModuleButton(int x, int y, int width, int height, Eludom eludom) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.eludom = eludom;
    }

    public void startDragging(Eludom eludom, String key, float min, float max) {
        isDraggingSlider = true;
        sliderEludom = eludom;
        sliderKey = key;
        sliderMin = min;
        sliderMax = max;
    }

    public void render(GuiGraphics graphics) {
        // --- TOGGLE INDICATOR: RED OUTLINE ---
        if (eludom.isToggled()) {
            j.drawSmoothRect(graphics, x - 11, y + 1, x + width + 11, y + 2, e.accentColor.getRGB());
            j.drawSmoothRect(graphics, x - 11, y + height - 2, x + width + 11, y + height - 1, e.accentColor.getRGB());
            j.drawSmoothRect(graphics, x - 11, y + 1, x - 10, y + height - 1, e.accentColor.getRGB());
            j.drawSmoothRect(graphics, x + width + 10, y + 1, x + width + 11, y + height - 1, e.accentColor.getRGB());
        }

        // Button background
        int bgColor = eludom.isToggled() ? new Color(45, 45, 45).getRGB() : e.buttonBgColor.getRGB();

        j.drawSmoothRect(graphics, x - 10, y + 2, x + width + 10, y + height - 2, bgColor);

        // Tiny red square indicator
        j.drawSmoothRect(graphics, x - 6, y + height / 2 - 1, x - 4, y + height / 2 + 1,
                eludom.isToggled() ? e.accentColor.getRGB() : new Color(80, 80, 80).getRGB());

        // Module name
        graphics.drawString(mc.font, eludom.getName(), x - 2, y + height / 2 - 9 / 2,
                eludom.isToggled() ? e.accentColor.getRGB() : e.textColor);

        // --- SETTINGS PANEL ---
        if (showSettings && selectedEludom == eludom) {
            int panelX = (int) (dragX + GUI_WIDTH + 5);
            int panelY = (int) (dragY + 15);
            int panelWidth = 105;
            int panelHeight = GUI_HEIGHT - 30;

            // Panel background
            j.drawSmoothRect(graphics, panelX, panelY,
                    panelX + panelWidth, panelY + panelHeight, e.bgColor.getRGB());

            // Panel border
            j.drawSmoothRect(graphics, panelX - 1, panelY - 1, panelX + panelWidth + 1, panelY, e.accentColor.getRGB());
            j.drawSmoothRect(graphics, panelX - 1, panelY + panelHeight, panelX + panelWidth + 1, panelY + panelHeight + 1,
                    e.accentColor.getRGB());
            j.drawSmoothRect(graphics, panelX - 1, panelY, panelX, panelY + panelHeight, e.accentColor.getRGB());
            j.drawSmoothRect(graphics, panelX + panelWidth, panelY, panelX + panelWidth + 1, panelY + panelHeight,
                    e.accentColor.getRGB());

            // Module title
            graphics.drawString(mc.font, selectedEludom.getName(), panelX + 5, panelY + 5, e.accentColor.getRGB(), false);

            // --- BIND SETTING ---
            graphics.drawString(mc.font, "Bind:", panelX + 5, panelY + 20, e.textColor, false);

            String bindText = "NONE";
            if (isBinding && bindingEludom == eludom) {
                bindText = "?";
            } else if (selectedEludom.getKey() != 0) {
                String keyName = GLFW.glfwGetKeyName(selectedEludom.getKey(), 0);
                bindText = keyName != null ? keyName.toUpperCase() : String.valueOf(selectedEludom.getKey());
            }

            int bindColor = (isBinding && bindingEludom == eludom) ? e.accentColor.getRGB() : e.textColor;
            graphics.drawString(mc.font, bindText, panelX + 45, panelY + 20, bindColor);

            // DEL remove hint
            if (isBinding && bindingEludom == eludom) {
                graphics.drawString(mc.font, "DEL=remove", panelX + 5, panelY + 32, new Color(180, 180, 180).getRGB());
            }

            // --- SETTINGS CONTENT (SCROLLABLE) ---
            int currentY = panelY + 45 - settingsScrollOffset;

            // Get the appropriate settings panel
            SettingsPanel settingsPanel = null;
            String moduleName = selectedEludom.getName();

            if (moduleName.equals(d("4b696c6c41757261") /* KillAura */)) {
                settingsPanel = new XpiSaxinSettings(selectedEludom, this);
            } else if (moduleName.equals(d("54726967676572426f74") /* TriggerBot */)) {
                settingsPanel = new NayiXpiSettings(selectedEludom, this);
            } else if (moduleName.equals(d("56656c6f63697479") /* Velocity */)) {
                settingsPanel = new V0Settings(selectedEludom, this);
            } else if (moduleName.equals(d("54696d6572") /* Timer */)) {
                settingsPanel = new SkazlaRunVasyaSettings(selectedEludom, this);
            } else if (moduleName.equals(d("537072696e74") /* Sprint */)) {
                settingsPanel = new RunVasyaRunSettings(selectedEludom, this);
            } else if (moduleName.equals(d("486974426f78") /* HitBox */)) {
                settingsPanel = new XpiMecSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4175746f546f74656d") /* AutoTotem */)) {
                settingsPanel = new VekalDzertSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4175746f436c69636b6572") /* AutoClicker */)) {
                settingsPanel = new ACSettings(selectedEludom, this);
            } else if (moduleName.equals(d("485544") /* HUD */)) {
                settingsPanel = new DuhSettings(selectedEludom, this);
            } else if (moduleName.equals(d("455350") /* ESP */)) {
                settingsPanel = new PSESettings(selectedEludom, this);
            } else if (moduleName.equals(d("476c6f77455350") /* GlowESP */)) {
                settingsPanel = new LuysovPSESettings(selectedEludom, this);
            } else if (moduleName.equals(d("4e6f46697265") /* NoFire */)) {
                settingsPanel = new VochmiKrakSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4672656543616d") /* FreeCam */)) {
                settingsPanel = new TriVriSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4d6964646c65506561726c") /* MiddlePearl */)) {
                settingsPanel = new TeleportationSettings(selectedEludom, this);
            } else if (moduleName.equals(d("456c7974726153776170") /* ElytraSwap */)) {
                settingsPanel = new HaqiTevertSettings(selectedEludom, this);
            } else if (moduleName.equals(d("41696d417373697374") /* AimAssist */)) {
                settingsPanel = new AASettings(selectedEludom, this);
            } else if (moduleName.equals(d("4974656d455350") /* ItemESP */)) {
                settingsPanel = new VesheriPSESettings(selectedEludom, this);
            } else if (moduleName.equals(d("47616d6d61") /* Gamma */)) {
                settingsPanel = new AmmagSettings(selectedEludom, this);
            } else if (moduleName.equals(d("54726163657273") /* Tracers */)) {
                settingsPanel = new GciknerSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4368696e61486174") /* ChinaHat */)) {
                settingsPanel = new CHSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4d4350") /* MCP */)) {
                settingsPanel = new YngersAxpersSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4175746f4372797374616c") /* AutoCrystal */)) {
                settingsPanel = new ACrySettings(selectedEludom, this);
            } else if (moduleName.equals(d("4175746f536e65616b") /* AutoSneak */)) {
                settingsPanel = new AragSarqiSettings(selectedEludom, this);
            } else if (moduleName.equals(d("566965774d6f64656c") /* ViewModel */)) {
                settingsPanel = new ErkarDzernerSettings(selectedEludom, this);
            } else if (moduleName.equals(d("547261696c73") /* Trails */)) {
                settingsPanel = new HeteviLuysSettings(selectedEludom, this);
            } else if (moduleName.equals(d("582d526179") /* X-Ray */)) {
                settingsPanel = new XRaySettings(selectedEludom, this);
            }

            if (settingsPanel != null) {
                settingsPanel.updatePosition(panelX, panelY, panelWidth, panelHeight,
                        panelY + 45, panelY + panelHeight - 10, currentY);

                // Scissor: clip rendering strictly inside the panel content area
                graphics.enableScissor(panelX, panelY + 45, panelX + panelWidth, panelY + panelHeight - 10);

                settingsPanel.render(graphics);

                graphics.disableScissor();

                // --- SCROLLBAR ---
                int contentHeight = settingsPanel.getHeight();
                int visibleHeight = panelHeight - 45 - 10;
                if (contentHeight > visibleHeight) {
                    int scrollbarX = panelX + panelWidth - 3;
                    int scrollbarTrackY = panelY + 45;
                    int scrollbarTrackH = visibleHeight;

                    // Clamp scroll offset so it never exceeds content
                    int maxScroll = contentHeight - visibleHeight;
                    settingsScrollOffset = Math.max(0, Math.min(settingsScrollOffset, maxScroll));

                    // Track (dark background) — strictly inside panel
                    j.drawSmoothRect(graphics, scrollbarX, scrollbarTrackY,
                            scrollbarX + 2, scrollbarTrackY + scrollbarTrackH,
                            new java.awt.Color(40, 40, 40).getRGB());

                    // Thumb — proportional size, clamped position
                    int thumbH = Math.max(10, (int) ((float) visibleHeight / contentHeight * scrollbarTrackH));
                    int thumbY = scrollbarTrackY
                            + (int) ((float) settingsScrollOffset / maxScroll * (scrollbarTrackH - thumbH));
                    thumbY = Math.max(scrollbarTrackY, Math.min(thumbY, scrollbarTrackY + scrollbarTrackH - thumbH));

                    j.drawSmoothRect(graphics, scrollbarX, thumbY,
                            scrollbarX + 2, thumbY + thumbH,
                            e.accentColor.getRGB());
                }
            }
        }
    }

    public void keyTyped(int key) {
        if (isBinding && bindingEludom == eludom) {
            if (key == 261) { // DEL - REMOVE BIND
                eludom.setKey(0);

            } else {
                eludom.setKey(key);

            }
            isBinding = false;
            bindingEludom = null;
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (HoverUtils.hovered((int) mouseX, (int) mouseY, x - 10, y + 2, x + width + 10, y + height - 2)) {
            if (button == 0) { // LEFT CLICK - Toggle module
                if (mc.player != null) {
                    mc.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1.5F, 1.5F);
                }
                if (eludom.getName().equals("Configs")) {
                    boolean opening = selectedEludom != eludom;
                    showSettings = opening;
                    selectedEludom = showSettings ? eludom : null;
                    isBinding = false;
                    if (opening)
                        settingsScrollOffset = 0;
                } else {
                    eludom.toggle();
                }
            } else if (button == 1) { // RIGHT CLICK - Open settings
                boolean opening = selectedEludom != eludom;
                showSettings = opening;
                selectedEludom = showSettings ? eludom : null;
                isBinding = false;
                if (opening)
                    settingsScrollOffset = 0;
            } else if (button == 2) { // MIDDLE CLICK - Bind key
                isBinding = !isBinding;
                bindingEludom = this.eludom;
                if (isBinding) {
                    mc.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1.5F, 0.5F);
                }
            }
        }

        // --- SETTINGS PANEL INTERACTIONS ---
        if (showSettings && selectedEludom == eludom) {
            int panelX = (int) (dragX + GUI_WIDTH + 5);
            int panelY = (int) (dragY + 15);
            int panelWidth = 105;
            int panelHeight = GUI_HEIGHT - 30;

            // --- BIND CLICK ---
            if (mouseX >= panelX + 45 && mouseX <= panelX + 70 &&
                    mouseY >= panelY + 20 && mouseY <= panelY + 30) {
                if (button == 0) {
                    isBinding = !isBinding;
                    bindingEludom = selectedEludom;
                }
            }

            // Forward to settings panel
            String moduleName = selectedEludom.getName();
            SettingsPanel settingsPanel = null;

            if (moduleName.equals(d("4b696c6c41757261") /* KillAura */)) {
                settingsPanel = new XpiSaxinSettings(selectedEludom, this);
            } else if (moduleName.equals(d("54726967676572426f74") /* TriggerBot */)) {
                settingsPanel = new NayiXpiSettings(selectedEludom, this);
            } else if (moduleName.equals(d("56656c6f63697479") /* Velocity */)) {
                settingsPanel = new V0Settings(selectedEludom, this);
            } else if (moduleName.equals(d("54696d6572") /* Timer */)) {
                settingsPanel = new SkazlaRunVasyaSettings(selectedEludom, this);
            } else if (moduleName.equals(d("537072696e74") /* Sprint */)) {
                settingsPanel = new RunVasyaRunSettings(selectedEludom, this);
            } else if (moduleName.equals(d("486974426f78") /* HitBox */)) {
                settingsPanel = new XpiMecSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4175746f546f74656d") /* AutoTotem */)) {
                settingsPanel = new VekalDzertSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4175746f436c69636b6572") /* AutoClicker */)) {
                settingsPanel = new ACSettings(selectedEludom, this);
            } else if (moduleName.equals(d("485544") /* HUD */)) {
                settingsPanel = new DuhSettings(selectedEludom, this);
            } else if (moduleName.equals(d("455350") /* ESP */)) {
                settingsPanel = new PSESettings(selectedEludom, this);
            } else if (moduleName.equals(d("476c6f77455350") /* GlowESP */)) {
                settingsPanel = new LuysovPSESettings(selectedEludom, this);
            } else if (moduleName.equals(d("4e6f46697265") /* NoFire */)) {
                settingsPanel = new VochmiKrakSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4672656543616d") /* FreeCam */)) {
                settingsPanel = new TriVriSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4d6964646c65506561726c") /* MiddlePearl */)) {
                settingsPanel = new TeleportationSettings(selectedEludom, this);
            } else if (moduleName.equals(d("456c7974726153776170") /* ElytraSwap */)) {
                settingsPanel = new HaqiTevertSettings(selectedEludom, this);
            } else if (moduleName.equals(d("41696d417373697374") /* AimAssist */)) {
                settingsPanel = new AASettings(selectedEludom, this);
            } else if (moduleName.equals(d("4974656d455350") /* ItemESP */)) {
                settingsPanel = new VesheriPSESettings(selectedEludom, this);
            } else if (moduleName.equals(d("4368696e61486174") /* ChinaHat */)) {
                settingsPanel = new CHSettings(selectedEludom, this);
            } else if (moduleName.equals(d("47616d6d61") /* Gamma */)) {
                settingsPanel = new AmmagSettings(selectedEludom, this);
            } else if (moduleName.equals(d("54726163657273") /* Tracers */)) {
                settingsPanel = new GciknerSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4d4350") /* MCP */)) {
                settingsPanel = new YngersAxpersSettings(selectedEludom, this);
            } else if (moduleName.equals(d("4175746f4372797374616c") /* AutoCrystal */)) {
                settingsPanel = new ACrySettings(selectedEludom, this);
            } else if (moduleName.equals(d("4175746f536e65616b") /* AutoSneak */)) {
                settingsPanel = new AragSarqiSettings(selectedEludom, this);
            } else if (moduleName.equals(d("566965774d6f64656c") /* ViewModel */)) {
                settingsPanel = new ErkarDzernerSettings(selectedEludom, this);
            } else if (moduleName.equals(d("547261696c73") /* Trails */)) {
                settingsPanel = new HeteviLuysSettings(selectedEludom, this);
            } else if (moduleName.equals(d("582d526179") /* X-Ray */)) {
                settingsPanel = new XRaySettings(selectedEludom, this);
            }

            if (settingsPanel != null) {
                int currentY = panelY + 45 - settingsScrollOffset;
                settingsPanel.updatePosition(panelX, panelY, panelWidth, panelHeight,
                        panelY + 45, panelY + panelHeight - 10, currentY);
                // Only forward clicks that are inside the visible panel content area
                if (mouseX >= panelX && mouseX <= panelX + panelWidth &&
                        mouseY >= panelY + 45 && mouseY <= panelY + panelHeight - 10) {
                    settingsPanel.mouseClicked(mouseX, mouseY, button, mouseY);
                }
            }
        }
    }

    public void mouseDragged(double mouseX, double mouseY, int button) {
        if (button == 0 && isDraggingSlider && sliderEludom == eludom && sliderKey != null) {
            int panelX = (int) (dragX + GUI_WIDTH + 5);

            float percent = (float) ((mouseX - (panelX + 5)) / 55.0);
            percent = Math.max(0, Math.min(1, percent));
            float value = sliderMin + (sliderMax - sliderMin) * percent;

            eludom.registerFloat(sliderKey, value, sliderMin, sliderMax);
        }
    }

    public boolean handleScrolled(double mouseX, double mouseY, double delta) {
        if (!showSettings || selectedEludom != eludom)
            return false;

        int panelX = (int) (dragX + GUI_WIDTH + 5);
        int panelY = (int) (dragY + 15);
        int panelWidth = 105;
        int panelHeight = GUI_HEIGHT - 30;

        // Only consume scroll if mouse is over the settings panel
        if (mouseX >= panelX && mouseX <= panelX + panelWidth &&
                mouseY >= panelY && mouseY <= panelY + panelHeight) {

            int contentHeight = 0;
            // Reuse same panel lookup to get content height
            String mn = selectedEludom.getName();
            SettingsPanel sp = null;
            if (mn.equals(d("4b696c6c41757261") /* KillAura */))
                sp = new XpiSaxinSettings(selectedEludom, this);
            else if (mn.equals(d("54726967676572426f74") /* TriggerBot */))
                sp = new NayiXpiSettings(selectedEludom, this);
            else if (mn.equals(d("56656c6f63697479") /* Velocity */))
                sp = new V0Settings(selectedEludom, this);
            else if (mn.equals(d("54696d6572") /* Timer */))
                sp = new SkazlaRunVasyaSettings(selectedEludom, this);
            else if (mn.equals(d("537072696e74") /* Sprint */))
                sp = new RunVasyaRunSettings(selectedEludom, this);
            else if (mn.equals(d("486974426f78") /* HitBox */))
                sp = new XpiMecSettings(selectedEludom, this);
            else if (mn.equals(d("4175746f546f74656d") /* AutoTotem */))
                sp = new VekalDzertSettings(selectedEludom, this);
            else if (mn.equals(d("4175746f436c69636b6572") /* AutoClicker */))
                sp = new ACSettings(selectedEludom, this);
            else if (mn.equals(d("485544") /* HUD */))
                sp = new DuhSettings(selectedEludom, this);
            else if (mn.equals(d("455350") /* ESP */))
                sp = new PSESettings(selectedEludom, this);
            else if (mn.equals(d("476c6f77455350") /* GlowESP */))
                sp = new LuysovPSESettings(selectedEludom, this);
            else if (mn.equals(d("4e6f46697265") /* NoFire */))
                sp = new VochmiKrakSettings(selectedEludom, this);
            else if (mn.equals(d("4672656543616d") /* FreeCam */))
                sp = new TriVriSettings(selectedEludom, this);
            else if (mn.equals(d("4d6964646c65506561726c") /* MiddlePearl */))
                sp = new TeleportationSettings(selectedEludom, this);
            else if (mn.equals(d("456c7974726153776170") /* ElytraSwap */))
                sp = new HaqiTevertSettings(selectedEludom, this);
            else if (mn.equals(d("41696d417373697374") /* AimAssist */))
                sp = new AASettings(selectedEludom, this);
            else if (mn.equals(d("4974656d455350") /* ItemESP */))
                sp = new VesheriPSESettings(selectedEludom, this);
            else if (mn.equals(d("47616d6d61") /* Gamma */))
                sp = new AmmagSettings(selectedEludom, this);
            else if (mn.equals(d("54726163657273") /* Tracers */))
                sp = new GciknerSettings(selectedEludom, this);
            else if (mn.equals(d("4368696e61486174") /* ChinaHat */))
                sp = new CHSettings(selectedEludom, this);
            else if (mn.equals(d("4d4350") /* MCP */))
                sp = new YngersAxpersSettings(selectedEludom, this);
            else if (mn.equals(d("4175746f4372797374616c") /* AutoCrystal */))
                sp = new ACrySettings(selectedEludom, this);
            else if (mn.equals(d("4175746f536e65616b") /* AutoSneak */))
                sp = new AragSarqiSettings(selectedEludom, this);
            else if (mn.equals(d("566965774d6f64656c") /* ViewModel */))
                sp = new ErkarDzernerSettings(selectedEludom, this);
            else if (mn.equals(d("547261696c73") /* Trails */))
                sp = new HeteviLuysSettings(selectedEludom, this);
            else if (mn.equals(d("582d526179") /* X-Ray */))
                sp = new XRaySettings(selectedEludom, this);
            if (sp != null)
                contentHeight = sp.getHeight();

            int visibleHeight = panelHeight - 45 - 10;
            int maxScroll = Math.max(0, contentHeight - visibleHeight);

            if (delta < 0) { // scroll down
                settingsScrollOffset = Math.min(settingsScrollOffset + 8, maxScroll);
            } else { // scroll up
                settingsScrollOffset = Math.max(0, settingsScrollOffset - 8);
            }
            return true; // consumed — stop propagation
        }
        return false;
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && isDraggingSlider) {
            isDraggingSlider = false;
            sliderEludom = null;
            sliderKey = null;
        }
    }

    public void mouseMoved(double mouseX, double mouseY) {
    }
}
