package com.mhcibasics.eieruhr;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddTimerActivity extends AppCompatActivity {

    TimePicker picker;
    EditText edTextName;
    ImageView ivPicture;
    Button btnChoosePicture;
    TextView tvTimeLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);

        picker = (TimePicker) findViewById(R.id.datePicker1);
        picker.setIs24HourView(true);
        edTextName = (EditText) findViewById(R.id.edTextName);
        ivPicture = (ImageView) findViewById(R.id.imgView_Timer);
        btnChoosePicture = (Button) findViewById(R.id.btnChoosePicture);
        tvTimeLeft = (TextView) findViewById(R.id.tvTimeLeft);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btnActionStartTimer(View view) {
        System.out.println("hours = " + picker.getHour() + "minutes = " + picker.getMinute());
        System.out.println("name = " + edTextName.getText());
        //TODO Start timer and add to ListView

        int hours = picker.getHour();
        int minutes = picker.getMinute();
        int total = 0;

        if(hours > 0){
            total = hours * 60 + minutes;
        } else {
            total = minutes;
        }

        int seconds = total * 60;
        int secondsInMillis = seconds * 1000;

        new CountDownTimer(secondsInMillis, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimeLeft.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tvTimeLeft.setText("done!");
            }

        }.start();

    }

    //to pick a picture
    public void btnActionChoosePicture(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooseIntent = Intent.createChooser(getIntent, "Select Image");
        chooseIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooseIntent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                Uri selected = data.getData();
                System.out.println(selected);
                ivPicture.setImageURI(selected);
                btnChoosePicture.setVisibility(View.GONE);
            }
        }
    }
}
