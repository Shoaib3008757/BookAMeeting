package com.shoaib.bam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shoaib.bam.Adapters.CustomListAdapter;
import com.shoaib.bam.Interfaces.CustomListItemClick;
import com.shoaib.bam.Models.ModelsClasses;
import com.shoaib.bam.R;
import com.shoaib.bam.activities.RegisterOfficeAndRoom.RegisteringRoom;
import com.shoaib.bam.utilities.ConstantFunctions;
import com.shoaib.bam.utilities.ConstantValues;
import com.shoaib.bam.utilities.SharedPrefs;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ScheduledScreen extends AppCompatActivity {

    TextView tv_selected_room, tv_date, tv_time, tv_date_end, tv_time_end, tv_selected_room_id;
    RelativeLayout rl_select_room, rl_submit, rl_bg;
    SpinKitView spin_kit;
    EditText et_description;
    ImageView iv_back_arrow;
    ArrayList<HashMap<String, String>> roomsList;
    public static TextView tv_temp_date_tv;
    public static TextView tv_temp_time_tv;
    public static int pickStarDateOrEnd = 0; //1 for start time 2 for end time
    String selectedOfficeName = "";
    String selectedOfficeId = "";
    boolean isAdded = true;
    boolean isSlotBooked = false;

    DatabaseReference meetingDBref;
    ValueEventListener meetingsListener=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secheduled_screen);

        init();
        onBackArrowClick();
        startDateClick();
        startTimeClick();
        endDateClick();
        endTimeClick();
        submitClick();
        getAllRoomsFromDB();

    }

    private void init()
    {
        tv_selected_room = (TextView) findViewById(R.id.tv_selected_room);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time_end = (TextView) findViewById(R.id.tv_time_end);
        tv_date_end = (TextView) findViewById(R.id.tv_date_end);
        tv_selected_room_id = (TextView) findViewById(R.id.tv_selected_room_id);
        rl_select_room = (RelativeLayout) findViewById(R.id.rl_select_room);
        rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);
        rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
        et_description = (EditText) findViewById(R.id.et_description);
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        spin_kit = (SpinKitView) findViewById(R.id.spin_kit); 
        roomsList = new ArrayList<>();

    }

    private void startDateClick()
    {
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_temp_date_tv = tv_date;
                pickStarDateOrEnd = 1;
                DialogFragment dFragment = new
                        DatePickerFragment();
                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");

            }
        });
    }
    private void startTimeClick()
    {
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment = new TimePickerFragment();
                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Time Picker");
                tv_temp_time_tv = tv_time;
            }
        });
    }
    private void endDateClick()
    {
        tv_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_temp_date_tv = tv_date_end;
                pickStarDateOrEnd = 2;
                DialogFragment dFragment = new
                        DatePickerFragment();
                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });
    }
    private void endTimeClick()
    {
        tv_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment = new TimePickerFragment();
                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Time Picker");
                tv_temp_time_tv = tv_time_end;
            }
        });
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
    private void submitClick()
    {
        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = tv_selected_room.getText().toString();
                String roomId = tv_selected_room_id.getText().toString();
                String startDate = tv_date.getText().toString();
                String startTime = tv_time.getText().toString();
                String endDate = tv_date_end.getText().toString();
                String endTime = tv_time_end.getText().toString();
                String description = et_description.getText().toString();
                Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);


                if(startDate.equalsIgnoreCase(getResources().getString(R.string.selectDate)))
                {
                    Toast.makeText(ScheduledScreen.this, "Please Select Start Date", Toast.LENGTH_SHORT).show();
                    tv_date.setAnimation(animShake);
                }
                else if(startTime.equalsIgnoreCase(getResources().getString(R.string.selectTime)))
                {
                    Toast.makeText(ScheduledScreen.this, "Please Select Start Date", Toast.LENGTH_SHORT).show();
                    tv_time.setAnimation(animShake);
                }
                else if(endDate.equalsIgnoreCase(getResources().getString(R.string.selectDate)))
                {
                    Toast.makeText(ScheduledScreen.this, "Please Select End Date", Toast.LENGTH_SHORT).show();
                    tv_date_end.setAnimation(animShake);
                }
                else if(endTime.equalsIgnoreCase(getResources().getString(R.string.selectTime)))
                {
                    Toast.makeText(ScheduledScreen.this, "Please Select End Time", Toast.LENGTH_SHORT).show();
                    tv_time_end.setAnimation(animShake);
                }else if (description.isEmpty())
                {
                    et_description.setError("Should not be empty");
                }else
                    {
                        String startTimestemp = ConstantFunctions.dateTimeToTimeStemp(startDate, startTime);
                        String endTimestemp = ConstantFunctions.dateTimeToTimeStemp(endDate, endTime);
                        long startTimestempToLong = Long.parseLong(startTimestemp);
                        long endTimstempToLong = Long.parseLong(endTimestemp);
                        long timeDiff = endTimstempToLong - startTimestempToLong;

                        if (startTimestempToLong>endTimstempToLong)
                        {
                            Toast.makeText(ScheduledScreen.this, "Ent Date/Time should not les then start Date/Time", Toast.LENGTH_SHORT).show();

                        }else if (timeDiff<1800000) //1800000 = 30 mints
                        {
                            Toast.makeText(ScheduledScreen.this, "Meeting Time Should not less then 30 Mints", Toast.LENGTH_SHORT).show();

                        }else
                            {

                                String meetingDuration = ConstantFunctions.timeUnitToFullTime(timeDiff, TimeUnit.MILLISECONDS);

                                long bookedTimeStempStart = Long.parseLong(ConstantFunctions.dateTimeToTimeStemp(startDate, startTime));
                                long currentTimeStemp = ConstantFunctions.gettingCurrentTimeStepm();
                                if(currentTimeStemp>bookedTimeStempStart)
                                {
                                    Toast.makeText(ScheduledScreen.this, "Start Time not Valid", Toast.LENGTH_SHORT).show();
                                }else {

                                    insertIntoMeeting(roomName, roomId, selectedOfficeId,
                                            selectedOfficeName, startDate,
                                            startTime, endDate, endTime,
                                            meetingDuration, String.valueOf(timeDiff), description);
                                }
                            }
                    }
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // DatePickerDialog THEME_DEVICE_DEFAULT_LIGHT
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    R.style.datepicker,this,year,month,day);
            return  dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            Calendar cal = Calendar.getInstance(); cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();
            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
            String selectdDate = sdf.format(chosenDate);
            tv_temp_date_tv.setText(selectdDate);

            /*if (pickStarDateOrEnd == 1) {
                tv_date_start.setText(dateis);
            }
            if (pickStarDateOrEnd==2)
            {
                tv_date_end.setText(dateis);
            }*/

        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            // Get a Calendar instance
            final Calendar calendar = Calendar.getInstance();
            // Get the current hour and minute
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            // TimePickerDialog Theme : THEME_DEVICE_DEFAULT_LIGHT
            TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                    R.style.datepicker,this,hour,minute,false);


            // Return the TimePickerDialog
            return tpd;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            // Do something with the returned time
            String am_pm = "";

            Calendar datetime = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            datetime.set(Calendar.MINUTE, minute);

            if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                am_pm = "AM";
            else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                am_pm = "PM";


            String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ?"12":datetime.get(Calendar.HOUR)+"";
            String hours = strHrsToShow;
            int inthurs = Integer.parseInt(hours);
            if (inthurs<10){
                String stringhours = String.valueOf(inthurs);
                hours = "0"+hours;
            }
            int minuts = datetime.get(Calendar.MINUTE);
            String stringMinuts = String.valueOf(minuts);
            if (minute<10){
                stringMinuts = "0"+stringMinuts;

            }
            tv_temp_time_tv.setText( hours+":"+stringMinuts+" "+am_pm );

            //tv.setText("HHH:MMM\n" + hourOfDay + ":" + minute);
        }
    }

    private void getAllRoomsFromDB()
    {
        rl_bg.setVisibility(View.VISIBLE);
        spin_kit.setVisibility(View.VISIBLE);
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ConstantValues.RoomPath);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot office : snapshot.getChildren())
                    {
                        ModelsClasses.RoomModel beam = office.getValue(ModelsClasses.RoomModel.class);

                         String roomId = beam.roomId;
                         String roomName = beam.roomName;
                         String officeName = beam.officeName;
                         String officeId = beam.officeId;
                         String PhoneNumber = beam.PhoneNumber;
                         String bookingStatus = beam.bookingStatus;
                         String roomNumber = beam.roomNumber;
                         String bookedTime = beam.bookedTime;
                         String openingTiming = beam.openingTiming;
                         String clossingTiming = beam.clossingTiming;
                         String bookedById = beam.bookedById;
                         String BookedByName = beam.BookedByName;
                         String BookFromTime = beam.BookFromTime;
                         String BookToTime = beam.BookToTime;
                         String BookFromDate = beam.BookFromDate;
                         String BookToDate = beam.BookToDate;

                        HashMap<String, String> map = new HashMap<>();
                        map.put(ConstantValues.officeId, roomId);
                        map.put(ConstantValues.name, roomName);
                        map.put(ConstantValues.bookedById, bookedById);
                        map.put(ConstantValues.BookedByName, BookedByName);
                        map.put(ConstantValues.bookingStatus, bookingStatus);
                        map.put(ConstantValues.BookFromTime, BookFromTime);
                        map.put(ConstantValues.BookToTime, BookToTime);
                        map.put(ConstantValues.BookFromDate, BookFromDate);
                        map.put(ConstantValues.BookToDate, BookToDate);
                        map.put(ConstantValues.officeIdInRoom, officeId);
                        map.put(ConstantValues.officeName, officeName);
                        
                        roomsList.add(map);


                    }//end of for loop


                    rl_bg.setVisibility(View.GONE);
                    spin_kit.setVisibility(View.GONE);
                    
                    listClick(roomsList);
                    if(roomsList.size()>0)
                    {
                        tv_selected_room.setText(roomsList.get(0).get(ConstantValues.name));
                        tv_selected_room_id.setText(roomsList.get(0).get(ConstantValues.officeId));
                        selectedOfficeId = roomsList.get(0).get(ConstantValues.officeIdInRoom);
                        selectedOfficeName = roomsList.get(0).get(ConstantValues.officeName);


                    }
                }
                else {
                    rl_bg.setVisibility(View.GONE);
                    spin_kit.setVisibility(View.GONE);
                    Log.e("TAg", "Data not exist ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void listClick(final ArrayList<HashMap<String, String>> list)
    {
        rl_select_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customeListSelectionPop(list, 0);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (meetingsListener!=null)
        {
            meetingDBref.removeEventListener(meetingsListener);
        }
    }

    private void customeListSelectionPop(ArrayList<HashMap<String, String>> list, final int type)// type 0 for top list, 1 for sub1 and 2 for sub2
    {
        final Dialog dialog = new Dialog(ScheduledScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_category_selection_popup);
        RecyclerView rc_items = (RecyclerView) dialog.findViewById(R.id.rc_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ScheduledScreen.this, LinearLayoutManager.VERTICAL, false);
        rc_items.setLayoutManager(linearLayoutManager);

        CustomListAdapter adapter1 = new CustomListAdapter(ScheduledScreen.this,
                list, new CustomListItemClick() {
            @Override
            public void onSingleItemClick(View view, int position) {

                String selectedItem = list.get(position).get(ConstantValues.name);
                String selectedItemKey = list.get(position).get(ConstantValues.officeId);
                tv_selected_room.setText(selectedItem);
                tv_selected_room_id.setText(selectedItemKey);
                selectedOfficeId = list.get(position).get(ConstantValues.officeIdInRoom);
                selectedOfficeName = list.get(position).get(ConstantValues.officeName);
                dialog.dismiss();
            }

            @Override
            public void onLongSingleItemClick(View view, int position) {

            }
        });

        rc_items.setAdapter(adapter1);

        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }

    private void insertIntoMeeting(final String roomName, final String roomId, final String selectedOfficeId,
                                   final String selectedOfficeName, final String startDate,
                                   final String startTime, final String endDate, final String endTime,
                                   final String meetingDuration, final String bookingDurationTimeStemp, final String meetingDescription)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.PREF_NAME, 0);
        String myKey = SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.key);
        String userFullName = SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.userFullName);
        String meetingId = ConstantFunctions.getratingRandomId();

        spin_kit.setVisibility(View.VISIBLE);
        rl_bg.setVisibility(View.VISIBLE);

        meetingDBref = FirebaseDatabase.getInstance().getReference(ConstantValues.MeetingPath);
        meetingsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    isSlotBooked = false;
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        ModelsClasses.ScheduledMeetings model = postSnapshot.getValue(ModelsClasses.ScheduledMeetings.class);

                        String meetingId = model.meetingId;
                        String bookedroomId = model.roomId;
                        String roomName = model.roomName;
                        String bookedtimeStemp = model.bookedtimeStemp;
                        String bookStartDate = model.bookStartDate;
                        String bookStartTime = model.bookStartTime;
                        String bookendEndDate = model.bookendEndDate;
                        String bookEndTime = model.bookEndTime;
                        String bookingDurationTimeStempServer = model.bookingDurationTimeStemp;
                        String bookingDuration = model.bookingDuration;
                        String bookByName = model.bookByName;
                        String bookById = model.bookById;
                        String officeId = model.officeId;
                        String officeName = model.officeName;
                        String meetingStatus = model.meetingStatus;

                        if (roomId.equalsIgnoreCase(bookedroomId)) {

                            long bookedTimeStempStart = Long.parseLong(ConstantFunctions.dateTimeToTimeStemp(bookStartDate, bookStartTime));
                            long bookedTimeStempEnd = Long.parseLong(ConstantFunctions.dateTimeToTimeStemp(bookendEndDate, bookEndTime));
                            long selectedTimeStempStart = Long.parseLong(ConstantFunctions.dateTimeToTimeStemp(startDate, startTime));
                            long selectedTimeStempEnd = Long.parseLong(ConstantFunctions.dateTimeToTimeStemp(endDate, endTime));

                            if(bookedTimeStempEnd>selectedTimeStempStart)
                            {
                                if(selectedTimeStempStart>bookedTimeStempStart)
                                {
                                    isSlotBooked = true;
                                }
                                if(selectedTimeStempEnd>bookedTimeStempStart)
                                {
                                    isSlotBooked = true;
                                }
                            }

                        }
                    }//end of loop

                    if (isSlotBooked)
                    {
                        slotNotAvailableDialog();
                        spin_kit.setVisibility(View.GONE);
                        rl_bg.setVisibility(View.GONE);

                    }else
                        {
                            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ConstantValues.MeetingPath);
                            ModelsClasses.ScheduledMeetings data = new ModelsClasses.ScheduledMeetings(
                                    meetingId,  roomId,  roomName,
                                    String.valueOf(ConstantFunctions.gettingCurrentTimeStepm()),  startDate,  startTime,
                                    endDate,  endTime,  bookingDurationTimeStemp,
                                    meetingDuration,  userFullName,  myKey,
                                    selectedOfficeId, selectedOfficeName, "Pending",
                                    meetingDescription);

                            mDatabase.child(meetingId).setValue(data);
                            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    if (isAdded) {
                                        spin_kit.setVisibility(View.GONE);
                                        rl_bg.setVisibility(View.GONE);

                                        successDialog();
                                        isAdded = false;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                        }
                    Log.e("TAg", "the here are values current selected slot booked " + isSlotBooked);
                }else
                    {
                        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ConstantValues.MeetingPath);
                        ModelsClasses.ScheduledMeetings data = new ModelsClasses.ScheduledMeetings(
                                meetingId,  roomId,  roomName,
                                String.valueOf(ConstantFunctions.gettingCurrentTimeStepm()),  startDate,  startTime,
                                endDate,  endTime,  bookingDurationTimeStemp,
                                meetingDuration,  userFullName,  myKey,
                                selectedOfficeId, selectedOfficeName, "Pending",
                                meetingDescription);

                        mDatabase.child(meetingId).setValue(data);
                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                if (isAdded) {
                                    spin_kit.setVisibility(View.GONE);
                                    rl_bg.setVisibility(View.GONE);

                                    successDialog();
                                    isAdded = false;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        Query query = meetingDBref.orderByChild(ConstantValues.bookStartDate).equalTo(tv_date.getText().toString());
        query.addListenerForSingleValueEvent(meetingsListener);

    }

    private void slotNotAvailableDialog()
    {

        final Dialog dialog = new Dialog(ScheduledScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.general_dialog_layout);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tv_line_separator = (TextView) dialog.findViewById(R.id.tv_line_separator);
        TextView tv_description = (TextView) dialog.findViewById(R.id.tv_description);
        TextView tv_left_text = (TextView) dialog.findViewById(R.id.tv_left_text);
        TextView tv_right_text = (TextView) dialog.findViewById(R.id.tv_right_text);
        RelativeLayout rl_left = (RelativeLayout) dialog.findViewById(R.id.rl_left);
        RelativeLayout rl_right = (RelativeLayout) dialog.findViewById(R.id.rl_right);

        tv_title.setText("Slot Not Available!");
        tv_description.setText("Room is already booked on selected time slot. Please Book on different Time and Date Range");
        rl_left.setVisibility(View.GONE);
        tv_line_separator.setVisibility(View.GONE);
        tv_left_text.setText("Ok");
        tv_right_text.setText("Ok");

        rl_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        rl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

    }

    private void successDialog()
    {

        final Dialog dialog = new Dialog(ScheduledScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.general_dialog_layout);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tv_line_separator = (TextView) dialog.findViewById(R.id.tv_line_separator);
        TextView tv_description = (TextView) dialog.findViewById(R.id.tv_description);
        TextView tv_left_text = (TextView) dialog.findViewById(R.id.tv_left_text);
        TextView tv_right_text = (TextView) dialog.findViewById(R.id.tv_right_text);
        RelativeLayout rl_left = (RelativeLayout) dialog.findViewById(R.id.rl_left);
        RelativeLayout rl_right = (RelativeLayout) dialog.findViewById(R.id.rl_right);

        tv_title.setText("Meeting Room Booked Successfully!");
        tv_description.setText("Thank You! your meeting room has been booked");
        rl_left.setVisibility(View.GONE);
        tv_line_separator.setVisibility(View.GONE);
        tv_left_text.setText("Ok");
        tv_right_text.setText("Ok");

        rl_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();

            }
        });

        rl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

    }
}