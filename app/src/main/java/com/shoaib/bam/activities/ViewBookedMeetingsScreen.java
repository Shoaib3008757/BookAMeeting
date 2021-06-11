package com.shoaib.bam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shoaib.bam.Adapters.MeetingsListAdapter;
import com.shoaib.bam.Models.ModelsClasses;
import com.shoaib.bam.R;
import com.shoaib.bam.utilities.ConstantFunctions;
import com.shoaib.bam.utilities.ConstantValues;
import com.shoaib.bam.utilities.SharedPrefs;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class ViewBookedMeetingsScreen extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView rc_list;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progress_bar;
    public MeetingsListAdapter mAdapter;
    ImageView iv_back_arrow;

    ArrayList<HashMap<String, String>> bookingsList = new ArrayList<>();

    DatabaseReference ordersDatabaseRef;
    ValueEventListener orderListener=null;
    SharedPreferences sharedPreferences;
    private HorizontalCalendar horizontalCalendar;
    RelativeLayout rl_bg, rl_calendarView;
    SpinKitView spin_kit;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView tv_request_count;
    String resultDate;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_screen);

        init();
        onBackArrowClick();
        viewCalendar();
        swipListLoader();
    }

    private void init(){
        bookingsList = new ArrayList<>();
        rc_list = (RecyclerView) findViewById(R.id.rc_list);
        iv_back_arrow = (ImageView)  findViewById(R.id.iv_back_arrow);
        linearLayoutManager = new LinearLayoutManager(ViewBookedMeetingsScreen.this, LinearLayoutManager.VERTICAL, false);
        rc_list.setLayoutManager(linearLayoutManager);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        sharedPreferences = getSharedPreferences(SharedPrefs.PREF_NAME, 0);

        rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
        rl_calendarView = (RelativeLayout) findViewById(R.id.rl_calendarView);
        spin_kit = (SpinKitView) findViewById(R.id.spin_kit);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        tv_request_count = (TextView) findViewById(R.id.tv_request_count);
        resultDate = ConstantFunctions.currentDate();
        
    }

    private void onBackArrowClick()
    {
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        ConstantFunctions.checkingInterConnection(ViewBookedMeetingsScreen.this);
        if (mAdapter!=null)
        {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void swipListLoader()
    {
        // SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);
                
            }
        });
    }

    @Override
    public void onRefresh() {

        // Fetching data from server
        gettingAllCurrentUserMeetings(resultDate);


    }

    private void gettingAllCurrentUserMeetings(final String selectedDate)
    {
        rl_bg.setVisibility(View.VISIBLE);
        spin_kit.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setRefreshing(true);

        ordersDatabaseRef = FirebaseDatabase.getInstance().getReference(ConstantValues.MeetingPath);
        orderListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (bookingsList.size()>0){bookingsList.clear();}
                mSwipeRefreshLayout.setRefreshing(false);
                if (dataSnapshot.exists())
                {
                    if (bookingsList.size()>0){bookingsList.clear();}

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        ModelsClasses.ScheduledMeetings model = postSnapshot.getValue(ModelsClasses.ScheduledMeetings.class);

                        String meetingId = model.meetingId;
                        String roomId = model.roomId;
                        String roomName = model.roomName;
                        String bookedtimeStemp = model.bookedtimeStemp;
                        String bookStartDate = model.bookStartDate;
                        String bookStartTime = model.bookStartTime;
                        String bookendEndDate = model.bookendEndDate;
                        String bookEndTime = model.bookEndTime;
                        String bookingDurationTimeStemp = model.bookingDurationTimeStemp;
                        String bookingDuration = model.bookingDuration;
                        String bookByName = model.bookByName;
                        String bookById = model.bookById;
                        String officeId = model.officeId;
                        String officeName = model.officeName;
                        String meetingStatus = model.meetingStatus;
                        
                        HashMap<String, String> map = new HashMap<>();
                        map.put(ConstantValues.meetingId, meetingId);
                        map.put(ConstantValues.roomId, roomId);
                        map.put(ConstantValues.roomName, roomName);
                        map.put(ConstantValues.bookedtimeStemp, bookedtimeStemp);
                        map.put(ConstantValues.bookStartDate, bookStartDate);
                        map.put(ConstantValues.bookStartTime, bookStartTime);
                        map.put(ConstantValues.bookendEndDate, bookendEndDate);
                        map.put(ConstantValues.bookEndTime, bookEndTime);
                        map.put(ConstantValues.bookingDurationTimeStemp, bookingDurationTimeStemp);
                        map.put(ConstantValues.bookingDuration, bookingDuration);
                        map.put(ConstantValues.bookByName, bookByName);
                        map.put(ConstantValues.bookById, bookById);
                        map.put(ConstantValues.officeId, officeId);
                        map.put(ConstantValues.officeName, officeName);
                        map.put(ConstantValues.meetingStatus, meetingStatus);

                        String myId = SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.key);
                        if (bookById.equalsIgnoreCase(myId)) {
                            bookingsList.add(map);

                        }
                    }

                    rl_bg.setVisibility(View.GONE);
                    spin_kit.setVisibility(View.GONE);
                    if (bookingsList.size()>0) {
                        Collections.reverse(bookingsList);
                        mAdapter = new MeetingsListAdapter(ViewBookedMeetingsScreen.this, bookingsList);
                        rc_list.setAdapter(mAdapter);
                    }else {
                        if (mAdapter!=null) {
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    if (bookingsList.size()<10)
                    {
                        tv_request_count.setText("0"+bookingsList.size());
                    }else {
                        tv_request_count.setText(String.valueOf(bookingsList.size()));
                    }
                }
                else
                {
                    tv_request_count.setText("00");
                    rl_bg.setVisibility(View.GONE);
                    spin_kit.setVisibility(View.GONE);
                    bookingsList.clear();
                    if (mAdapter!=null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };

        Query query = ordersDatabaseRef.orderByChild(ConstantValues.bookStartDate).equalTo(selectedDate);
        query.addListenerForSingleValueEvent(orderListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (orderListener!=null)
        {
            ordersDatabaseRef.removeEventListener(orderListener);
        }
    }

    private void viewCalendar()
    {
        final Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .textSize(14f, 24f, 14f)
                .showDayName(true)
                .showMonthName(true)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {

                ArrayList<HashMap<String, Object>> filterDataList = new ArrayList<>();
                ArrayList<HashMap<String, Object>> tempList = new ArrayList<>();
                String curretFormatedDate  = DateFormat.getDateInstance().format(date).toString();
                resultDate = ConstantFunctions.parseDateToddMMyyyy(curretFormatedDate);
                if (resultDate==null)
                {
                    resultDate = DateFormat.getDateInstance().format(date).toString();
                }

                gettingAllCurrentUserMeetings(resultDate);

            }

        });
    }
}