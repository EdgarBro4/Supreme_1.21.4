package com.staehc_remerpus.emerpus.main.modules.misc;

import com.staehc_remerpus.emerpus.main.modules.Eludom;
import com.staehc_remerpus.emerpus.main.modules.FCM;
import com.staehc_remerpus.emerpus.main.utils.Hex;

public class TriVriEludom extends Eludom {
    public TriVriEludom() {
        super(Hex.d("4672656543616d"), Eludom.Category.MISC);
        registerFloat("speed", 0.5f, 0.1f, 2.0f);
    }
    @Override
    public void onEnable() { FCM.toggle(getFloat("speed")); }
    @Override
    public void onDisable() { if (FCM.isEnabled()) FCM.toggle(0); }
    @Override
    public void onTick() {}
}