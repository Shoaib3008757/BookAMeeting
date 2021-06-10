package com.shoaib.bam.activities.RegisterOfficeAndRoom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.ChildEventListener;
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
import com.shoaib.bam.utilities.ConstantFunctions;
import com.shoaib.bam.utilities.ConstantValues;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisteringRoom extends AppCompatActivity {
    
    TextView tv_lat, tv_lng, sp_office, tv_selected_office_id;
    RelativeLayout rl_top_cat;
    EditText et_name, et_phone, et_room_no ;
    RelativeLayout rl_submit;
    boolean officeAdded = true;
    ImageView iv_back_arrow;
    TextView tv_title;
    String mcreatedBy;
    RelativeLayout rl_bg;
    SpinKitView spin_kit;
    ArrayList<HashMap<String, String>> dataLis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering_room);

        init();
        gettingIntentValues();
        onBackArrowClilckHandler();
        onSendInvitationClickHandler();
    }

    private void init()
    {
        
        tv_lat = (TextView) findViewById(R.id.tv_lat);
        tv_lng = (TextView) findViewById(R.id.tv_lng);
        rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_room_no = (EditText) findViewById(R.id.et_room_no);
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        rl_top_cat = (RelativeLayout) findViewById(R.id.rl_top_cat);
        tv_title = (TextView) findViewById(R.id.tv_title);
        sp_office = (TextView) findViewById(R.id.sp_office);
        tv_selected_office_id = (TextView) findViewById(R.id.tv_selected_office_id);
        rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
        spin_kit = (SpinKitView) findViewById(R.id.spin_kit);

        getAllOfficesFromDB();
    }

    private void gettingIntentValues()
    {
        Intent i = getIntent();
        mcreatedBy = "";

        String customerPhone = i.getStringExtra("userPhone");

        if (customerPhone!=null)
        {
            if (!customerPhone.isEmpty())
            {
                et_phone.setText(customerPhone);
            }
        }

    }

    private void onSendInvitationClickHandler()
    {
        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String roomName = et_name.getText().toString();
                String roomPhoneNumber = et_phone.getText().toString();
                String roomNo = et_room_no.getText().toString();
                String officeId = tv_selected_office_id.getText().toString();
                String officeName = sp_office.getText().toString();

                if (dataLis.size()==0)
                {
                    Toast.makeText(RegisteringRoom.this, "Please Refresh Screen for loading office", Toast.LENGTH_SHORT).show();
                }
                else if (officeId.isEmpty())
                {
                    Toast.makeText(RegisteringRoom.this, "Please Select Office", Toast.LENGTH_SHORT).show();

                }
                else if (roomName.length()<1)
                {
                    et_name.setError("Should not empty");
                }
                else if (roomPhoneNumber.length()<0)
                {
                    et_phone.setError("Should not empty");
                }
                else if (roomPhoneNumber.length()<10)
                {
                    et_phone.setError("Please Enter Valid Phone Number");
                }
                else if (!roomPhoneNumber.startsWith("04"))
                {
                    et_phone.setError("Please Enter Valid Phone Number 33");
                }
                else if (roomNo.isEmpty())
                {
                    et_room_no.setError("Should not empty");
                }


                else {


                    rl_submit.setVisibility(View.GONE);
                    insertNewDataToFirebase(roomName, roomPhoneNumber, roomNo,
                            officeName, officeId);

                }
            }
        });
    }

    public void insertNewDataToFirebase(final String roomName, final String roomPhoneNumber, final String roomNo,
                                        final String officeName, final String officeId)
    {

        long time= System.currentTimeMillis();
        String mTime = String.valueOf(time);

        final ProgressDialog progressDialog = new ProgressDialog(RegisteringRoom.this);
        progressDialog.setTitle("Uploading...");

        creatingUserTable(roomName, roomPhoneNumber, roomNo,
                officeName, officeId);
    }


    //creating office Table
    private void creatingUserTable(final String roomName, final String roomPhoneNumber, final String roomNo,
                                   final String officeName, final String officeId)
    {
        spin_kit.setVisibility(View.VISIBLE);
        rl_bg.setVisibility(View.VISIBLE);
        final String roomId = ConstantFunctions.getratingRandomId();

        //uploading to database
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ConstantValues.RoomPath);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    ModelsClasses.RoomModel data = new ModelsClasses.RoomModel(roomId,  "no", roomNo,
                            "", "", "",
                            "", "", "",
                            "", "", "",
                            roomName, roomPhoneNumber, officeName, officeId);

                    //Adding values
                    mDatabase.child(roomId).setValue(data);
                    mDatabase.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if (officeAdded) {
                                spin_kit.setVisibility(View.GONE);
                                rl_bg.setVisibility(View.GONE);

                                AlertDialog.Builder alert = new AlertDialog.Builder(RegisteringRoom.this);
                                alert.setTitle("Room Registered Successfully!");
                                alert.setMessage("Thank you!");
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                });
                                alert.setCancelable(false);
                                alert.show();
                                officeAdded = false;
                            }

                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            Log.e("TAG", "the values 2 ");
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            Log.e("TAG", "the values 3 ");
                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                            Log.e("TAG", "the values 4 ");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("TAG", "the values 5 ");
                        }
                    });
                }
                else {
                    rl_submit.setVisibility(View.VISIBLE);
                    spin_kit.setVisibility(View.GONE);
                    rl_bg.setVisibility(View.GONE);
                    Toast.makeText(RegisteringRoom.this, "Office Already Registered!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void onBackArrowClilckHandler()
    {
        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void getAllOfficesFromDB()
    {
        rl_bg.setVisibility(View.VISIBLE);
        spin_kit.setVisibility(View.VISIBLE);
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ConstantValues.OfficePath);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    for (DataSnapshot office : snapshot.getChildren())
                    {

                        ModelsClasses.OfficeModel beam = office.getValue(ModelsClasses.OfficeModel.class);
                        Log.e("TAg", "the reusl beam is " + beam);
                         String officeId = beam.officeId;
                         String name = beam.name;
                         String address = beam.address;
                         String lat = beam.lat;
                         String lng = beam.lng;
                         String bookingStatus = beam.bookingStatus;
                         String ownerName = beam.ownerName;
                         String companyName = beam.companyName;
                         String openingTiming = beam.openingTiming;
                         String closingTiming = beam.closingTiming;
                         String OfficeNumber = beam.OfficeNumber;

                        HashMap<String, String> map = new HashMap<>();
                        map.put(ConstantValues.name, name);
                        map.put(ConstantValues.officeId, officeId);
                        map.put(ConstantValues.address, address);
                        map.put(ConstantValues.lat, lat);
                        map.put(ConstantValues.lng, lng);
                        map.put(ConstantValues.bookingStatus, bookingStatus);
                        map.put(ConstantValues.ownerName, ownerName);
                        map.put(ConstantValues.companyName, companyName);
                        map.put(ConstantValues.openingTiming, openingTiming);
                        map.put(ConstantValues.closingTiming, closingTiming);
                        map.put(ConstantValues.OfficeNumber, OfficeNumber);

                        dataLis.add(map);


                    }//end of for loop


                    rl_bg.setVisibility(View.GONE);
                    spin_kit.setVisibility(View.GONE);
                    listClick(dataLis);
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
        rl_top_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customeListSelectionPop(list, 0);
            }
        });

    }

    private void customeListSelectionPop(ArrayList<HashMap<String, String>> list, final int type)// type 0 for top list, 1 for sub1 and 2 for sub2
    {
        final Dialog dialog = new Dialog(RegisteringRoom.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_category_selection_popup);
        RecyclerView rc_items = (RecyclerView) dialog.findViewById(R.id.rc_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RegisteringRoom.this, LinearLayoutManager.VERTICAL, false);
        rc_items.setLayoutManager(linearLayoutManager);

        CustomListAdapter adapter1 = new CustomListAdapter(RegisteringRoom.this,
                list, new CustomListItemClick() {
            @Override
            public void onSingleItemClick(View view, int position) {

                    String selectedItem = list.get(position).get(ConstantValues.name);
                    String selectedItemKey = list.get(position).get(ConstantValues.officeId);
                    sp_office.setText(selectedItem);
                    tv_selected_office_id.setText(selectedItemKey);
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

}
