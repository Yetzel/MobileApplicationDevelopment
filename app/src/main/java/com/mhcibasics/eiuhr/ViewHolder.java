package com.mhcibasics.eiuhr;

import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
    TextView tvProduct;
    TextView tvTimeRemaining;
    Countdown countdown;
    ImageView ivPicture;

    public void setData(Countdown item) {
        countdown = item;
        tvProduct.setText(item.name);
        updateTimeRemaining(System.currentTimeMillis());
    }

    public void updateTimeRemaining(long currentTime) {
        long timeDiff = countdown.expirationTime - currentTime;
        if (timeDiff > 0) {
            int seconds = (int) (timeDiff / 1000) % 60;
            int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
            int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);
            tvTimeRemaining.setText(hours + " hrs " + minutes + " mins " + seconds + " sec");
        } else {
            tvTimeRemaining.setText("Expired!!");
        }
    }
}