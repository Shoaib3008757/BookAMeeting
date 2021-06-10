package com.shoaib.bam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shoaib.bam.R;

public class SplashScreen extends AppCompatActivity {

    ImageView iv_logo;
    RelativeLayout rl_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();

    }
    private void init()
    {
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        Animation rotation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.fade_out_animation);
        rl_main.setAnimation(rotation);

        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                startActivity(new Intent(SplashScreen.this, LoginScreen.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}