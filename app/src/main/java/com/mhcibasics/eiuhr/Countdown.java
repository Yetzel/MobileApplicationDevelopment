package com.mhcibasics.eiuhr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Countdown implements Serializable {
    String name;
    long expirationTime;
    private String uriPicture;
    private String uriRingtone;
    private boolean isFinished = false;
    Bitmap test;

    public Countdown(String name, long expirationTime, Uri image, Uri ringTone) {
        this.name = name;
        this.expirationTime = expirationTime;
        if(image != null){
            this.uriPicture = image.toString();
        }
        if(ringTone != null) {
            this.uriRingtone = ringTone.toString();
        }
        System.out.println(this.uriRingtone);
    }

    @NonNull
    @Override
    public String toString() {

        return "Name : " + name + " Time Left : " + expirationTime;
    }

    public Uri getUriPicture(){
        return Uri.parse(uriPicture);
    }

    public Uri getUriRingTone(){
        return Uri.parse(uriRingtone);
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean flag) {
        isFinished = flag;
    }
}
