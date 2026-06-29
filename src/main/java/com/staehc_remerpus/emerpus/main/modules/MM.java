package com.staehc_remerpus.emerpus.main.modules;

import com.staehc_remerpus.emerpus.main.modules.tabmoc.*;
import com.staehc_remerpus.emerpus.main.modules.visuals.*;
import com.staehc_remerpus.emerpus.main.modules.movement.*;
import com.staehc_remerpus.emerpus.main.modules.misc.*;

import java.util.ArrayList;
import java.util.List;

public class MM {

    private static final List<Eludom> modules = new ArrayList<>();

    public static void init() {
        // Combat
        modules.add(new XpiSaxinEludom());      // KillAura
        modules.add(new NayiXpiEludom());        // TriggerBot
        modules.add(new ACEludom());             // AutoClicker
        modules.add(new AAEludom());             // AimAssist
        modules.add(new V0Eludom());             // Velocity
        modules.add(new XpiMecEludom());         // HitBox
        modules.add(new ACryEludom());           // AutoCrystal

        // Visuals
        modules.add(new DuhEludom());            // HUD
        modules.add(new PSEEludom());            // ESP
        modules.add(new LuysovPSEEludom());      // GlowESP
        modules.add(new VesheriPSEEludom());     // ItemESP
        modules.add(new GciknerEludom());        // Tracers
        modules.add(new CHEludom());             // ChinaHat
        modules.add(new HeteviLuysEludom());     // Trails
        modules.add(new NamaknerEludom());       // Notifications
        modules.add(new ErkarDzernerEludom());   // ViewModel
        modules.add(new XRayEludom());           // X-Ray
        modules.add(new AmmagEludom());          // Gamma
        modules.add(new VochmiKrakEludom());     // NoFire
        modules.add(new BacPaterEludom());       // WallHack

        // Movement
        modules.add(new RunVasyaRunEludom());    // Sprint/Timer
        modules.add(new AragSarqiEludom());      // AutoSneak
        modules.add(new SkazlaRunVasyaEludom()); // Timer/Sprint

        // Misc
        modules.add(new MiYngiEludom());         // NoFall
        modules.add(new VekalDzertEludom());     // AutoTotem
        modules.add(new TriVriEludom());         // FreeCam
        modules.add(new TeleportationEludom());  // MiddlePearl
        modules.add(new HaqiTevertEludom());     // ElytraSwap
        modules.add(new YngersAxpersEludom());   // Middle Friend
        modules.add(new AragMknikEludom());      // ItemScroller

        // Setup notification callback
        Eludom.NamaknerNotifier.setCallback((name, toggled) -> {
            Eludom notifications = getModule("Notifications");
            if (notifications instanceof NamaknerEludom) {
                ((NamaknerEludom) notifications).addNotification(name, toggled);
            }
        });

    }

    public static List<Eludom> getModules() {
        return modules;
    }

    public static Eludom getModule(String name) {
        for (Eludom m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public static List<Eludom> getModulesByCategory(Eludom.Category category) {
        List<Eludom> result = new ArrayList<>();
        for (Eludom m : modules) {
            if (m.getCategory() == category) {
                result.add(m);
            }
        }
        return result;
    }
}
