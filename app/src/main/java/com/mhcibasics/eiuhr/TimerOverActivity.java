package com.mhcibasics.eiuhr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.TimerTask;

public class TimerOverActivity extends AppCompatActivity {

    Context ctx = null;
    Countdown countdown;
    Intent intent;
    ImageButton imgBtnEndTimer;
    TextView tvTimerName;
    ImageView ivTimerPicture;
    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_over);

        ctx = getApplication();
        intent = getIntent();

        tvTimerName = findViewById(R.id.tvTimerName);
        ivTimerPicture = findViewById(R.id.ivTimerPicture);
        imgBtnEndTimer = findViewById(R.id.imgBtnEnd);

        if (intent.hasExtra("CD")) {
            countdown = (Countdown) intent.getSerializableExtra("CD");
            tvTimerName.setText(countdown.name);
            ivTimerPicture.setImageURI(countdown.getUriPicture());
        }

        try {
            Uri alert = countdown.getUriRingTone();
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(this, alert);
            final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_RING) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (Exception e) {

        }

        //Sound mutes after 10 seconds
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                mMediaPlayer.stop();
            }
        }.start();
    }

    public void btnEndTimer(View view) {
        mMediaPlayer.stop();
        Intent intent = new Intent (ctx, MainActivity.class);
        startActivity(intent);
    }

}
