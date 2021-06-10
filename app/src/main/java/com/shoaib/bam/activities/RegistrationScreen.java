package com.shoaib.bam.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shoaib.bam.Models.ModelsClasses;
import com.shoaib.bam.R;
import com.shoaib.bam.utilities.ConstantFunctions;
import com.shoaib.bam.utilities.ConstantValues;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationScreen extends AppCompatActivity {

    AutoCompleteTextView et_address;
    TextView tv_lat, tv_lng;

    RelativeLayout rl_person_image;
    ImageView iv_person;
    EditText et_name, et_phone, et_email, et_designation ;
    RelativeLayout rl_send_invitation;


    boolean customerAdded = true;
    ImageView iv_back_arrow;
    LinearLayout ll_shop_name, ll_designation;
    TextView tv_title;
    String mcreatedBy;
    RelativeLayout rl_bg;
    SpinKitView spin_kit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

        init();
        gettingIntentValues();
        onBackArrowClilckHandler();
        onSendInvitationClickHandler();



    }

    private void init()
    {
        et_address = (AutoCompleteTextView) findViewById(R.id.et_address);
        tv_lat = (TextView) findViewById(R.id.tv_lat);
        tv_lng = (TextView) findViewById(R.id.tv_lng);
        rl_person_image = (RelativeLayout) findViewById(R.id.rl_person_image);
        rl_send_invitation = (RelativeLayout) findViewById(R.id.rl_send_invitation);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_email = (EditText) findViewById(R.id.et_email);
        et_designation  = (EditText) findViewById(R.id.et_designation);
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        ll_shop_name = (LinearLayout) findViewById(R.id.ll_shop_name);
        ll_designation = (LinearLayout) findViewById(R.id.ll_designation);
        tv_title = (TextView) findViewById(R.id.tv_title);
        rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
        spin_kit = (SpinKitView) findViewById(R.id.spin_kit);

        iv_person = (ImageView) findViewById(R.id.iv_person);
        iv_person.bringToFront();

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
        rl_send_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String customerName = et_name.getText().toString();
                String customerNumber = et_phone.getText().toString();
                String customerEmail = et_email.getText().toString();
                String customerDesignation = et_designation.getText().toString();
                String customerAddrress = et_address.getText().toString();
                String customerLat = tv_lat.getText().toString();
                String customerLng = tv_lng.getText().toString();


                if (customerName.length()<1)
                {
                    et_name.setError("Should not empty");
                }
                else if (customerNumber.length()<0)
                {
                    et_phone.setError("Should not empty");
                }
                else if (customerNumber.length()<10)
                {
                    et_phone.setError("Please Enter Valid Phone Number");
                }
                else if (!customerNumber.startsWith("05"))
                {
                    et_phone.setError("Please Enter Valid Phone Number 33");
                }
                else if (customerEmail.length()<1){
                    et_email.setError("Should not be empty");
                }
                else if (!emailValidator(customerEmail)){
                    et_email.setError("Invalid Email Address");
                }

                else {

                    if (customerEmail.length() == 0) {
                        customerEmail = "";
                    }
                    if (customerName.length() == 0) {
                        customerName = "";
                    }
                    if (customerNumber.length() == 0) {
                        customerNumber = "";
                    }
                    if (customerDesignation.length() == 0) {
                        customerDesignation = "";
                    }
                    if (customerAddrress.length() == 0) {
                        customerAddrress = "";
                    }
                    if (customerLat.length() == 0) {
                        customerLat = "";
                    }
                    if (customerLng.length() == 0) {
                        customerLng = "";
                    }
                    String contactType = "";

                    rl_send_invitation.setVisibility(View.GONE);
                    insertNewDataToFirebase(customerName, customerNumber, customerEmail,
                            customerDesignation, customerAddrress);


                }
            }
        });
    }

    public static boolean emailValidator(final String mailAddress) {

        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mailAddress);
        return matcher.matches();
    }

    public void insertNewDataToFirebase(final String customerName, final String customerPhone, final String customerEmail,
                                        final String designation, final String userAddress)
    {

        long time= System.currentTimeMillis();
        String mTime = String.valueOf(time);

        final ProgressDialog progressDialog = new ProgressDialog(RegistrationScreen.this);
        progressDialog.setTitle("Uploading...");

        creatingUserTable(customerName, customerPhone, customerEmail,
                designation, userAddress,"");
    }

    //creating customers Table
    private void creatingUserTable(final String customerName, final String customerPhone, final String customerEmail,
                                   final String designation, final String userAddress,final String imageUrl)
    {
        spin_kit.setVisibility(View.VISIBLE);
        rl_bg.setVisibility(View.VISIBLE);
        final String userId = customerPhone;

        //uploading to database
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ConstantValues.userPath);
        final DatabaseReference checkingExisitance = FirebaseDatabase.getInstance().getReference(ConstantValues.userPath+"/"+userId);
        Query query = checkingExisitance.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    ModelsClasses.UserModel data = new ModelsClasses.UserModel(userId,"",userId, "",
                            customerName,customerEmail, "12345", "user",
                            customerPhone, userId,"",
                            customerName, "",userAddress,
                            String.valueOf(ConstantFunctions.gettingCurrentTimeStepm()),designation,imageUrl,
                            "","","",
                            "","");

                    //Adding values
                    mDatabase.child(userId).setValue(data);
                    mDatabase.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if (customerAdded) {
                                spin_kit.setVisibility(View.GONE);
                                rl_bg.setVisibility(View.GONE);

                                AlertDialog.Builder alert = new AlertDialog.Builder(RegistrationScreen.this);
                                alert.setTitle("User Registered Successfully! Please Login");
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
                                customerAdded = false;
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
                    rl_send_invitation.setVisibility(View.VISIBLE);
                    spin_kit.setVisibility(View.GONE);
                    rl_bg.setVisibility(View.GONE);
                    Toast.makeText(RegistrationScreen.this, "Member Already Registered! Please Login Instead", Toast.LENGTH_SHORT).show();
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


}
