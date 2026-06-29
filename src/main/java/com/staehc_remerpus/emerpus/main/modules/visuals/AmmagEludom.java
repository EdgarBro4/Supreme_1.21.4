package com.staehc_remerpus.emerpus.main.modules.visuals;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.utils.Hex;
import net.minecraft.client.Minecraft;

public class AmmagEludom extends Eludom {
    private final Minecraft mc = Minecraft.getInstance();
    private double previousGamma;

    public AmmagEludom() {
        super(Hex.d("47616d6d61"), Category.VISUALS);
    }
    @Override
    public void onEnable() {
        previousGamma = mc.options.gamma().get();
        mc.options.gamma().set(100.0);
    }
    @Override
    public void onDisable() {
        mc.options.gamma().set(previousGamma);
    }
    @Override public void onTick() {}
}