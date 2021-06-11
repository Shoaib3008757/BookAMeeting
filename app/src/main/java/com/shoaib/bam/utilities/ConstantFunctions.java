package com.shoaib.bam.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ConstantFunctions {

    public static String getratingRandomId()
    {
        String assignmentId = "";
        int max = 999999999;
        int min = 1;
        int random = (int )(Math.random() * max + min);
        String timeStemp = String.valueOf(System.currentTimeMillis());
        return assignmentId = timeStemp+"_"+String.valueOf(random);
        //return assignmentId = String.valueOf(random);
    }

    public static long gettingCurrentTimeStepm() {
        return System.currentTimeMillis();
    }

    public static String currentDate() {
        Date currentDate = Calendar.getInstance().getTime();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        String mCurrentDate = sdf.format(currentDate);
        return mCurrentDate;
    }

    public static String currentTime()
    {
        long timeStemp = System.currentTimeMillis();
        String currentTime = android.text.format.DateFormat.format("hh:mm a",
                new Date(timeStemp)).toString();
        return currentTime;

    }
    public static String dateTimeToTimeStemp(String sDate, String sTime)
    {
        String resultTimeStemp = "";
        Date date = null;
        try {
            String dateAndTime = sDate+" "+sTime;
            java.text.DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
            date = (Date)formatter.parse(dateAndTime);
            Log.e("TAg", "the time Stemp is here  " + date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.valueOf(date.getTime());
    }

    public static String dateTimeToTimeStempWithoutAMPM(String sDate, String sTime)
    {
        String resultTimeStemp = "";
        Date date = null;
        try {
            String dateAndTime = sDate+" "+sTime;
            java.text.DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
            date = (Date)formatter.parse(dateAndTime);
            Log.e("TAg", "the time Stemp is here  " + date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return String.valueOf(date.getTime());
    }

    public static String timeUnitToFullTime(long time, TimeUnit timeUnit) {
        long day = timeUnit.toDays(time);
        long hour = timeUnit.toHours(time) % 24;
        long minute = timeUnit.toMinutes(time) % 60;
        long second = timeUnit.toSeconds(time) % 60;
        if (day > 0) {
            return String.format("%d day %02d hours : %02d minutes", day, hour, minute);
        } else if (hour > 0) {
            return String.format("%d hours : %02d minutes", hour, minute);
        } else if (minute > 0) {
            return String.format("%d minutes", minute);
        } else {
            return String.format("%02d seconds", second);
        }
    }

    public static void checkingInterConnection(Activity activity) {


        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo activeWIFIInfo = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        if (activeWIFIInfo.isConnected() || activeNetInfo.isConnected())

        {
        } else

        {
            showInternetDialog(activity);
        }
    }

    public static void showInternetDialog(final Activity activity) {
        AlertDialog internetDialog;
        AlertDialog.Builder internetBuilder = new AlertDialog.Builder(activity);
        internetBuilder.setCancelable(false);
        internetBuilder
                .setTitle("No Internet")
                .setMessage("Please turn Internet services ON")
                .setPositiveButton("Enable 3G/4G",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                activity.startActivity(intent);
                            }
                        })
                .setNegativeButton("Enable Wifi",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // User pressed Cancel button. Write
                                // Logic Here
                                activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            }
                        })
                .setNeutralButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                                activity.finish();
                            }
                        });
        internetDialog = internetBuilder.create();
        internetDialog.show();
    }

    public static String parseDateToddMMyyyy(String time) {
        String inputPattern = "MMM dd, yyyy";
        String outputPattern = "dd-MMM-yyyy";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        if (!time.contains(","))
        {
            inputPattern = "dd MMM yyyy";
            inputFormat = new SimpleDateFormat(inputPattern);

        }

        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);

            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();


        }
        return str;
    }
}
