package com.staehc_remerpus.emerpus.main.utils;

public class k {
    private long lastTime = 0L;
    private long dpk = 0L;
    private boolean kAJ = false;
    public static long WFE = -1L;

    public k() {
        this.lastTime = 0L;
    }

    public boolean hasPassed(double milliseconds) {
        return System.currentTimeMillis() - lastTime >= milliseconds;
    }

    public void reset() {
        lastTime = System.currentTimeMillis();
    }

    public long getTimePassed() {
        return System.currentTimeMillis() - lastTime;
    }

    public long clS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean pJy(double milliseconds) {
        return this.clS() - this.dpk >= milliseconds;
    }

    public void OQP() {
        this.dpk = this.clS();
    }

    public boolean RuA() {
        return this.kAJ;
    }

    public void kWd(boolean waiting) {
        this.kAJ = waiting;
    }

    public void DOK(long value) {
        this.dpk = value;
    }

    public long gcs() {
        return this.clS() - this.dpk;
    }

    public static long ZmR() {
        return System.currentTimeMillis();
    }

    public boolean Eza(float milliseconds) {
        return ZmR() - k.WFE >= milliseconds;
    }

    public boolean CTJ(float milliseconds) {
        return this.gcs() - this.lastTime >= milliseconds;
    }
}