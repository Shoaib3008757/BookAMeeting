package com.shoaib.bam.activities.RegisterOfficeAndRoom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;
import com.shoaib.bam.Models.ModelsClasses;
import com.shoaib.bam.R;
import com.shoaib.bam.utilities.ConstantFunctions;
import com.shoaib.bam.utilities.ConstantValues;

public class RegisteringOffice extends AppCompatActivity {

    AutoCompleteTextView et_address;
    TextView tv_lat, tv_lng;
    EditText et_name, et_phone, et_office_no, et_designation ;
    RelativeLayout rl_submit;
    boolean officeAdded = true;
    ImageView iv_back_arrow;
    TextView tv_title;
    String mcreatedBy;
    RelativeLayout rl_bg;
    SpinKitView spin_kit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_registration_screen);

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
        rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_office_no = (EditText) findViewById(R.id.et_office_no);
        et_designation  = (EditText) findViewById(R.id.et_designation);
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);

        tv_title = (TextView) findViewById(R.id.tv_title);
        rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
        spin_kit = (SpinKitView) findViewById(R.id.spin_kit);

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

                String officeName = et_name.getText().toString();
                String officePhoneNumber = et_phone.getText().toString();
                String officeNo = et_office_no.getText().toString();
                String officeOwnerName = et_designation.getText().toString();
                String officeAddrress = et_address.getText().toString();
                String officeLat = tv_lat.getText().toString();
                String officeLng = tv_lng.getText().toString();


                if (officeName.length()<1)
                {
                    et_name.setError("Should not empty");
                }
                else if (officePhoneNumber.length()<0)
                {
                    et_phone.setError("Should not empty");
                }
                else if (officePhoneNumber.length()<10)
                {
                    et_phone.setError("Please Enter Valid Phone Number");
                }
                else if (!officePhoneNumber.startsWith("04"))
                {
                    et_phone.setError("Please Enter Valid Phone Number 33");
                }
                else if (officeNo.isEmpty())
                {
                    et_office_no.setError("Should not empty");
                }
                else if (officeOwnerName.isEmpty())
                {
                    et_designation.setError("Should not empty");
                }
                else if (officeAddrress.isEmpty())
                {
                    et_address.setError("Should not empty");
                }

                else {

                    officeLat = "";
                    officeLng = "";

                    rl_submit.setVisibility(View.GONE);
                    insertNewDataToFirebase(officeName, officePhoneNumber, officeNo,
                            officeOwnerName, officeAddrress);

                }
            }
        });
    }

    public void insertNewDataToFirebase(final String officeName, final String officePhoneNumber, final String officeNo,
                                        final String officeOwnerName, final String officeAddrress)
    {

        long time= System.currentTimeMillis();
        String mTime = String.valueOf(time);

        final ProgressDialog progressDialog = new ProgressDialog(RegisteringOffice.this);
        progressDialog.setTitle("Uploading...");

        creatingUserTable(officeName, officePhoneNumber, officeNo,
                officeOwnerName, officeAddrress);
    }


    //creating office Table
    private void creatingUserTable(final String officeName, final String officePhoneNumber, final String officeNo,
                                   final String officeOwnerName, final String officeAddrress)
    {
        spin_kit.setVisibility(View.VISIBLE);
        rl_bg.setVisibility(View.VISIBLE);
        final String officeId = ConstantFunctions.getratingRandomId();

        //uploading to database
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(ConstantValues.OfficePath);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    ModelsClasses.OfficeModel data = new ModelsClasses.OfficeModel(officeId, officeName, officeAddrress,
                            "", "", "no",
                            officeOwnerName, "BAM", "",
                            "", officePhoneNumber);

                    //Adding values
                    mDatabase.child(officeId).setValue(data);
                    mDatabase.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if (officeAdded) {
                                spin_kit.setVisibility(View.GONE);
                                rl_bg.setVisibility(View.GONE);

                                AlertDialog.Builder alert = new AlertDialog.Builder(RegisteringOffice.this);
                                alert.setTitle("Office Registered Successfully!");
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
                    Toast.makeText(RegisteringOffice.this, "Office Already Registered!", Toast.LENGTH_SHORT).show();
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
