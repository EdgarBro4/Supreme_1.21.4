package com.staehc_remerpus.emerpus.main.utils;

public class d {
    public float anim, to, speed;
    long mc = System.currentTimeMillis();

    public d(float speed) {
        this.speed = speed;
    }

    public float getAnim() {
        int count = (int) ((System.currentTimeMillis() - mc) / 5);
        if (count > 0) mc = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            anim = g.lerp(anim, to, speed);
        }
        return anim;
    }
}