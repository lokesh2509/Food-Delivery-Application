package com.example.food_tanya;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Timer;
import java.util.TimerTask;

public class activity_introductory extends AppCompatActivity {


    ImageView logo, appName, splashImg;
    LottieAnimationView lottieAnimationView;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        logo= findViewById(R.id.logo);
        appName = findViewById(R.id.app_name);
        splashImg = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.lottie);
        timer = new Timer();


        splashImg.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(2050).setDuration(1000).setStartDelay(4000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(activity_introductory.this, activity_on_boarding1.class);
                startActivity(intent);
                finish();
            }
        }, 5000);

    }

}