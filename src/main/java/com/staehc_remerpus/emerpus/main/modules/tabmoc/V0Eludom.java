package com.staehc_remerpus.emerpus.main.modules.tabmoc;

import com.staehc_remerpus.emerpus.main.modules.Eludom;

public class V0Eludom extends Eludom {

    public V0Eludom() {
        //super("Velocity", Category.COMBAT);
        super(d("56656c6f63697479"), Category.COMBAT);
        registerFloat("horizontal", 0.0f, 0.0f, 100.0f);
        registerFloat("vertical", 0.0f, 0.0f, 100.0f);
    }
    private static String d(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2)
            sb.append((char) Integer.parseInt(hex.substring(i, i + 2), 16));
        return sb.toString();
    }
    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public void onTick() {}
}