package com.mhcibasics.eiuhr;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;

public class AddTimerActivity extends AppCompatActivity {

    EditText edTextName;
    ImageView ivPicture;
    Button btnChoosePicture;
    Intent intent;
    Uri uriPicture;
    Uri uriRingtone;
    Button btnChooseRingtone;
    TextView tvSound;
    Button btnStartTimer;

    EditText edHours;
    EditText edMinutes;
    EditText edSeconds;

    private boolean pictureSet = false;
    private boolean ringtoneSet = false;

    Context ctx;

    Snackbar snackbar;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        setContentView(R.layout.activity_add_timer);
        ctx = getApplication();

        btnStartTimer = (Button) findViewById(R.id.btn_startTimer);
        edTextName = (EditText) findViewById(R.id.edTextName);
        ivPicture = (ImageView) findViewById(R.id.imgView_Timer);
        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnActionChoosePicture(v);
            }
        });
        btnChoosePicture = (Button) findViewById(R.id.btnChoosePicture);
        btnChooseRingtone = (Button) findViewById(R.id.btn_chooseSound);
        tvSound = (TextView) findViewById(R.id.tvSound);
        edHours = (EditText) findViewById(R.id.etHours);
        edMinutes = (EditText) findViewById(R.id.etMinutes);
        edSeconds = (EditText) findViewById(R.id.etSeconds);

        if (intent.hasExtra("CD")) {
            Countdown countdown = (Countdown) intent.getSerializableExtra("CD");
            long timeDiff = countdown.expirationTime - System.currentTimeMillis();
            int seconds = (int) (timeDiff / 1000) % 60;
            int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
            int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);

            System.out.println("SECONDS: " + seconds + "\nMINUTES: " + minutes + "\nHOURS: " + hours);

            edSeconds.setText(String.valueOf(seconds));
            edMinutes.setText(String.valueOf(minutes));
            edHours.setText(String.valueOf(hours));

            edTextName.setText(countdown.name);
            ivPicture.setImageURI(countdown.getUriPicture());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btnActionStartTimer(View view) {
        if (checkForCorrectTimeInput()) {
            int hours = Integer.parseInt(edHours.getText().toString());
            int minutes = Integer.parseInt(edMinutes.getText().toString()) + (60 * hours);
            int seconds = Integer.parseInt(edSeconds.getText().toString()) + (60 * minutes);
            int miliseconds = seconds * 1000;
            String name = edTextName.getText().toString();

            if (uriPicture != null && uriRingtone != null) {
                Countdown countdown = new Countdown(name, System.currentTimeMillis() + miliseconds, uriPicture, uriRingtone);
                intent.putExtra("newTimer", countdown);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        } else {
            snackbar = Snackbar.make(getCurrentFocus(), "Please choose a correct time Format", Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Your Intent here
                }
            });
            snackbar.show();
        }
    }

    private boolean checkForCorrectTimeInput() {
        String regexStr = "^[0-9]*$";

        if (edHours.getText().toString().trim().matches(regexStr) && edMinutes.getText().toString().trim().matches(regexStr) && edSeconds.getText().toString().trim().matches(regexStr)
            && Integer.parseInt(edMinutes.getText().toString()) <= 60 && Integer.parseInt(edSeconds.getText().toString()) <= 60 && Integer.parseInt(edHours.getText().toString()) <= 24) {
            return true;
        } else {
            return false;
        }
    }

    //to pick a picture
    public void btnActionChoosePicture(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooseIntent = Intent.createChooser(getIntent, "Select Image");
        chooseIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooseIntent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("ActivityResult: \nrequestCode = " + requestCode + "\nresultCode = " + resultCode + "\ndata = " + data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                uriPicture = data.getData();
                ivPicture.setImageURI(uriPicture);
                btnChoosePicture.setVisibility(View.GONE);

                pictureSet = true;
                checkForButtonActivation();
            } else if (requestCode == 5) {
                Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

                if (uri != null) {
                    this.uriRingtone = uri;

                    Ringtone ringtone = RingtoneManager.getRingtone(ctx, uri);
                    String title = ringtone.getTitle(this);
                    tvSound.setText(title);

                    ringtoneSet = true;
                    checkForButtonActivation();
                } else {
                    this.uriRingtone = null;
                }
            }
        }
    }

    private void checkForButtonActivation() {
        if (ringtoneSet && pictureSet) {
            btnStartTimer.setEnabled(true);
        }
    }

    public void btnChooseSound(View view) {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        this.startActivityForResult(intent, 5);
    }
}
