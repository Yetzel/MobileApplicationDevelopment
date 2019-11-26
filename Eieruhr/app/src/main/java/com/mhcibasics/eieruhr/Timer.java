package com.mhcibasics.eieruhr;

import android.net.Uri;
import android.os.CountDownTimer;

public class Timer {

    Uri picture;
    String name;
    long expirationTime;
    //TODO add ringtone

    public Timer(/*Uri picture,*/ String name, long timeLeft) {
        this.picture = picture;
        this.name = name;
        this.expirationTime = timeLeft;
    }

    public Uri getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }

    public long getTimeLeft() {
        return expirationTime;
    }
}
