package com.mhcibasics.eiuhr;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvProduct;
    TextView tvTimeRemaining;
    Countdown countdown;
    ImageView ivPicture;
    Context ctx;

    public ViewHolder(View convertView) {
        super(convertView);
        ctx = convertView.getContext();
    }

    public void setData(Countdown item) {
        countdown = item;
        tvProduct.setText(item.name);
        ivPicture.setImageURI(item.getUriPicture());
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
            if(!countdown.isFinished()){
                tvTimeRemaining.setText("Time over");
                Intent intent = new Intent(ctx, TimerOverActivity.class);
                intent.putExtra("CD", countdown);
                ctx.startActivity(intent);
                countdown.setFinished(true);
            }
        }
    }
}