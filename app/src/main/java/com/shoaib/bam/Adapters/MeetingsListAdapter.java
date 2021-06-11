package com.shoaib.bam.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shoaib.bam.R;
import com.shoaib.bam.utilities.ConstantFunctions;
import com.shoaib.bam.utilities.ConstantValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MeetingsListAdapter extends RecyclerView.Adapter<MeetingsListAdapter.MyViewHolder>  {

    private ArrayList<HashMap<String, String>> dataArray;
    private Activity mContext;

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    public static final int REQUEST_PERMISSION_CODE = 300;
    private int lastPosition = -1;
    private CountDownTimer cdt;
    boolean isTimeCompleted = false;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_order_id, tv_customerID,  tv_customerLat, tv_customerLng, tv_orderCompletionDate ;
        private TextView tv_cutomer_name, tv_order_taken_date, tv_satus, tv_total_items;
        private RelativeLayout rl_single_item;
        TextView tv_m_assign_to, tv_assign_to;
        LinearLayout ll_deliverd_by;
        TextView tv_m_delivered_by;
        TextView tv_delivered_by;
        LinearLayout ll_delivery_date;
        TextView tv_delivery_date;
        LinearLayout ll_delivery_on;
        TextView tv_delivery_on;
        TextView tv_delivery_time;
        TextView tv_place_time;
        TextView tv_txt_request_number;
        TextView  tv_item_count,
                tv_request_number;
        LinearLayout ll_status, ll_items;

        LinearLayout ll_timmer;
        TextView tv_hrs, tv_minuts, tv_seconds, tv_txt_total_items;
        TextView tv_building_name, tv_customer_phone, tv_assigned_to, tv_end_time;
        LinearLayout  ll_customer_building, ll_customer_phone;
        SpinKitView spin_kit;
        RelativeLayout rl_bg;

        boolean isTimeCompleted = false;
        long mStartTime;


        public MyViewHolder(final View view) {
            super(view);

            tv_order_id = (TextView) view.findViewById(R.id.tv_order_id);
            tv_customerID = (TextView) view.findViewById(R.id.tv_customerID);
            tv_customerLat = (TextView) view.findViewById(R.id.tv_customerLat);
            tv_customerLng = (TextView) view.findViewById(R.id.tv_customerLng);
            tv_orderCompletionDate = (TextView) view.findViewById(R.id.tv_orderCompletionDate);
            tv_cutomer_name = (TextView) view.findViewById(R.id.tv_cutomer_name);
            tv_order_taken_date = (TextView) view.findViewById(R.id.tv_order_taken_date);
            tv_satus = (TextView) view.findViewById(R.id.tv_satus);
            tv_total_items = (TextView) view.findViewById(R.id.tv_total_items);
            rl_single_item = (RelativeLayout) view.findViewById(R.id.rl_single_item);
            tv_assign_to = (TextView) view.findViewById(R.id.tv_assign_to);
            tv_m_assign_to = (TextView) view.findViewById(R.id.tv_m_assign_to);
            ll_deliverd_by = (LinearLayout) view.findViewById(R.id.ll_deliverd_by);
            tv_m_delivered_by = (TextView) view.findViewById(R.id.tv_m_delivered_by);
            tv_delivered_by = (TextView) view.findViewById(R.id.tv_delivered_by);
            ll_delivery_date = (LinearLayout) view.findViewById(R.id.ll_delivery_date);
            tv_delivery_date = (TextView) view.findViewById(R.id.tv_delivery_date);
            ll_delivery_on = (LinearLayout) view.findViewById(R.id.ll_delivery_on);
            tv_delivery_on = (TextView) view.findViewById(R.id.tv_delivery_on);
            tv_delivery_time = (TextView) view.findViewById(R.id.tv_delivery_time);
            tv_place_time  = (TextView) view.findViewById(R.id.tv_place_time);
            tv_item_count  = (TextView) view.findViewById(R.id.tv_item_count);
            tv_txt_total_items  = (TextView) view.findViewById(R.id.tv_txt_total_items);
            tv_assigned_to  = (TextView) view.findViewById(R.id.tv_assigned_to);
            ll_status = (LinearLayout) view.findViewById(R.id.ll_status);
            ll_items = (LinearLayout) view.findViewById(R.id.ll_items);

            ll_timmer = (LinearLayout) view.findViewById(R.id.ll_timmer);
            tv_hrs = (TextView) view.findViewById(R.id.tv_hrs);
            tv_minuts = (TextView) view.findViewById(R.id.tv_minuts);
            tv_seconds = (TextView) view.findViewById(R.id.tv_seconds);
            tv_txt_request_number = (TextView) view.findViewById(R.id.tv_txt_request_number);
            tv_request_number = (TextView) view.findViewById(R.id.tv_request_number);
            ll_customer_building = (LinearLayout) view.findViewById(R.id.ll_customer_building);
            ll_customer_phone = (LinearLayout) view.findViewById(R.id.ll_customer_phone);
            tv_building_name = (TextView) view.findViewById(R.id.tv_building_name);
            tv_customer_phone = (TextView) view.findViewById(R.id.tv_customer_phone);
            tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
            rl_bg = (RelativeLayout) view.findViewById(R.id.rl_bg);
            spin_kit = (SpinKitView) view.findViewById(R.id.spin_kit);

        }
    }

    public MeetingsListAdapter(Activity context , ArrayList<HashMap<String, String>> appealList) {
        this.mContext = context;
        this.dataArray = appealList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        MyViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_meetings_list_layout, parent, false);
        viewHolder = new MyViewHolder(itemView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            int pp = position+1;


            final String meetingId = dataArray.get(position).get(ConstantValues.meetingId);
            final String roomId = dataArray.get(position).get(ConstantValues.roomId);
            final String roomName = dataArray.get(position).get(ConstantValues.roomName);
            final String bookedtimeStemp = dataArray.get(position).get(ConstantValues.bookedtimeStemp);
            final String bookStartDate = dataArray.get(position).get(ConstantValues.bookStartDate);
            final String bookStartTime = dataArray.get(position).get(ConstantValues.bookStartTime);
            final String bookendEndDate = dataArray.get(position).get(ConstantValues.bookendEndDate);
            final String bookEndTime = dataArray.get(position).get(ConstantValues.bookEndTime);
            final String bookingDurationTimeStemp = dataArray.get(position).get(ConstantValues.bookingDurationTimeStemp);
            final String bookingDuration = dataArray.get(position).get(ConstantValues.bookingDuration);
            final String bookByName = dataArray.get(position).get(ConstantValues.bookByName);
            final String bookById = dataArray.get(position).get(ConstantValues.bookById);
            final String officeId = dataArray.get(position).get(ConstantValues.officeId);
            final String officeName = dataArray.get(position).get(ConstantValues.officeName);
            final String meetingStatus = dataArray.get(position).get(ConstantValues.meetingStatus);


            holder.tv_request_number.setText(bookStartTime);
            holder.tv_end_time.setText(bookEndTime);
            holder.tv_assign_to.setText(officeName);
            holder.tv_order_id.setText(meetingId);
            holder.tv_customerID.setText(roomId);
            holder.tv_cutomer_name.setText(roomName);
            holder.tv_total_items.setText(bookByName);
            holder.tv_order_taken_date.setText(bookStartTime);
            holder.tv_orderCompletionDate.setText(bookEndTime);
            holder.tv_place_time.setText(bookingDuration);
            holder.tv_satus.setText(meetingStatus);

            //single item click
            holder.rl_single_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == dataArray.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

}