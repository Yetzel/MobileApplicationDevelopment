package com.mhcibasics.eiuhr;

import android.media.Image;

public class Countdown {
    String name;
    long expirationTime;
    Image imgTimer;

    public Countdown(String name, long expirationTime) {
        this.name = name;
        this.expirationTime = expirationTime;
    }
}
