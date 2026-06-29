package com.staehc_remerpus.emerpus.main.utils;

public class l {
    long mc;

    public l() { this.mc = System.currentTimeMillis(); }
    public void reset() { this.mc = System.currentTimeMillis(); }
    public long getMc() { return System.currentTimeMillis() - this.mc; }
    public boolean hasReached(final long n) { return System.currentTimeMillis() - this.mc > n; }
}
