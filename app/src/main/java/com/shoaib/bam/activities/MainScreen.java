package com.shoaib.bam.activities;

import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shoaib.bam.R;

public class MainScreen extends BaseActivityForDrawer {

    RelativeLayout rl_view_bookings, rl_create_booking;
    ImageView iv_open_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_screen);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_main_screen, null, false);
        mDrawerLayout.addView(contentView, 0);

        init();
        openDrawser();
        viewBookingsClickHandler();
        createRequestClick();

    }

    private void init()
    {
        rl_view_bookings = (RelativeLayout) findViewById(R.id.rl_view_bookings);
        iv_open_drawer = (ImageView) findViewById(R.id.iv_open_drawer);
        rl_create_booking = (RelativeLayout) findViewById(R.id.rl_create_booking);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();



    }

    private void viewBookingsClickHandler()
    {
        rl_view_bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //callingImageSelectListener();
                startActivity(new Intent(MainScreen.this, ScheduledScreen.class));
            }
        });
    }

    private void openDrawser()
    {
        iv_open_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
        {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
        else
        {
            super.onBackPressed();
        }
    }

    private void createRequestClick() {
        rl_create_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreen.this, BookAMeetingScreen.class));
            }
        });
    }

}