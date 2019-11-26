package com.mhcibasics.eieruhr;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TimerTask;

public class TimerAdapter extends ArrayAdapter<Timer>{

    private ArrayList<ViewHolder> dataset;
    Context mContext;
    private Handler mHandler = new Handler();
    private LayoutInflater lf;
    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (dataset) {
                long currentTime = System.currentTimeMillis();
                for(ViewHolder holder : dataset) {
                    holder.updateTimeRemaining(currentTime);
                }
            }
        }
    };

    private static class ViewHolder {
        Timer timer;
        TextView name;
        TextView timeLeft;
        ImageView picture;

        public void setData(Timer timer) {
            this.timer = timer;
            name.setText(timer.name);
            updateTimeRemaining(System.currentTimeMillis());
        }

        public void updateTimeRemaining(long currentTime) {
            long timeDiff = timer.expirationTime - currentTime;
            if (timeDiff > 0) {
                int seconds = (int) (timeDiff / 1000) % 60;
                int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
                int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);
                timeLeft.setText(hours + " hrs " + minutes + " mins " + seconds + " sec");
            } else {
                timeLeft.setText("Expired!!");
            }
        }
    }

    public TimerAdapter(ArrayList<Timer> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataset = new ArrayList<>();
        this.mContext = context;
        lf = LayoutInflater.from(context);
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        java.util.Timer tmr = new java.util.Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateRemainingTimeRunnable);
            }
        }, 1000, 1000);
    }

    public void onClick(View v) {

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = lf.inflate(R.layout.row_item, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.tvName);
            holder.timeLeft = (TextView) convertView.findViewById(R.id.tvTimeLeft);
            convertView.setTag(holder);
            synchronized (dataset) {
                dataset.add(holder);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.setData(getItem(position));

        return convertView;
    }


}
