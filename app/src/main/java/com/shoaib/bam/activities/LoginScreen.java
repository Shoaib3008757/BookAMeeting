package com.shoaib.bam.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.shoaib.bam.Models.ModelsClasses;
import com.shoaib.bam.R;
import com.shoaib.bam.utilities.ConstantValues;
import com.shoaib.bam.utilities.SharedPrefs;

import org.jetbrains.annotations.NotNull;

public class LoginScreen extends AppCompatActivity {

    EditText et_login_email, et_login_password;
    TextView tv_forgot_password;
    RelativeLayout rl_login_button, rl_register_button;
    CheckBox ch_is_admin;
    SharedPreferences sharedPreferences;
    boolean isLoginWithPhone = false;
    public  static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    String ip = "";
    String androidID = "0";
    RelativeLayout rl_bg;
    SpinKitView spin_kit;

    private final int ACCESS_FINE_LOCATION = 11;
    private final int CAMERA_PER = 12;
    private final int STORAGE_PERMISSION = 14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        init();
        loginButtonClickHandler();
        forgotPassowrdClick();
        FirebaseApp.initializeApp(this);
        signupClick();


    }

    private void init()
    {
        et_login_email = (EditText) findViewById(R.id.et_login_email);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        tv_forgot_password = (TextView) findViewById(R.id.tv_forgot_password);
        rl_login_button = (RelativeLayout) findViewById(R.id.rl_login_button);
        rl_register_button = (RelativeLayout) findViewById(R.id.rl_register_button);
        //rl_login_button.setBackground(getResources().getDrawable(R.drawable.login_button));
        ch_is_admin = (CheckBox) findViewById(R.id.ch_is_admin);
        rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
        spin_kit = (SpinKitView) findViewById(R.id.spin_kit);
        sharedPreferences = getSharedPreferences(SharedPrefs.PREF_NAME, 0);
        androidID = SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.FID);


        String userID = SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.key);
        Log.e("TAg", "the staff role is userID " + userID);
        if (userID.length()>2)
        {
            String userType = SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.userType);

                Intent i = new Intent(LoginScreen.this, MainScreen.class);
                startActivity(i);
                finish();

        }else
        {
            Log.e("TAg", "login not found ");

        }


    }

    private void loginButtonClickHandler()
    {
        rl_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("TAG", "here in the fir ");
                String email = et_login_email.getText().toString();
                String password = et_login_password.getText().toString();
                if (email.length()==0)
                {
                    Toast.makeText(LoginScreen.this, "Email / Phone should not empty", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()==0)
                {
                    Toast.makeText(LoginScreen.this, "Password should not empty", Toast.LENGTH_SHORT).show();
                }

                else {

                    if (email.startsWith("+971") || email.startsWith("05")){isLoginWithPhone = true;}
                    else {isLoginWithPhone = false;}

                    loginUser(email, password);

                }

            }
        });
    }

    private void loginUser(final String email, final String pass)
    {
        spin_kit.setVisibility(View.VISIBLE);
        rl_bg.setVisibility(View.VISIBLE);
        Query query;
        if (isLoginWithPhone)
        {final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(ConstantValues.appRootPath);
            query = databaseRef.child(ConstantValues.USERS).orderByChild("userPhone").equalTo(email);
        }else {
            final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(ConstantValues.appRootPath);
            query = databaseRef.child(ConstantValues.USERS).orderByChild("userEmail").equalTo(email);
        }
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0

                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        ModelsClasses.UserModel usersBean = user.getValue(ModelsClasses.UserModel.class);

                        if (email.equalsIgnoreCase(usersBean.userEmail) || email.equalsIgnoreCase(usersBean.userPhone)) {
                            if (usersBean.password.equalsIgnoreCase(pass)) {
                                String key = usersBean.key;
                                String userCompanyId = usersBean.companyId;
                                String userAdminID = usersBean.createdById;
                                String userDesignation = usersBean.userDesignation;
                                String userName = usersBean.userFullName;
                                String userPhone = usersBean.userPhone;
                                String userEmail = usersBean.userEmail;
                                String userAddress = usersBean.userAddress;
                                ;
                                String userLatitude = usersBean.currentLat;
                                String userLongitude = usersBean.currentLng;
                                String userStatus = usersBean.userStatus;
                                String userPassword = usersBean.password;
                                String officeId = usersBean.officeId;
                                String roomId = usersBean.roomId;
                                String udid = usersBean.FID;
                                String imageUrl = usersBean.userProfileImageUrl;
                                String userType = usersBean.userType;

                                SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.PREF_NAME, 0);
                                SharedPrefs.StoreStringPref(sharedPreferences, SharedPrefs.key, key);
                                SharedPrefs.StoreStringPref(sharedPreferences, SharedPrefs.companyId, userCompanyId);
                                SharedPrefs.StoreStringPref(sharedPreferences, SharedPrefs.createdById, userAdminID);
                                SharedPrefs.StoreStringPref(sharedPreferences, SharedPrefs.userDesignation, userDesignation);
                                SharedPrefs.StoreStringPref(sharedPreferences, SharedPrefs.userFullName, userName);
                                SharedPrefs.StoreStringPref(sharedPreferences, SharedPrefs.userPhone, userPhone);
                                SharedPrefs.StoreStringPref(sharedPreferences, SharedPrefs.userEmail, userEmail);
                                SharedPrefs.StoreStringPref(sharedPreferences, SharedPrefs.FID, udid);
                                SharedPrefs.StoreStringPref(sharedPreferences, SharedPrefs.userType, userType);
                                SharedPrefs.StoreStringPref(sharedPreferences, SharedPrefs.roomId, roomId);
                                SharedPrefs.StoreStringPref(sharedPreferences, SharedPrefs.officeId, officeId);

                                spin_kit.setVisibility(View.GONE);
                                rl_bg.setVisibility(View.GONE);
                                Intent i = new Intent(LoginScreen.this, MainScreen.class);
                                startActivity(i);
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), "Email or Password is incorrect", Toast.LENGTH_LONG).show();
                                et_login_password.setError("Incorrect password");
                                spin_kit.setVisibility(View.GONE);
                                rl_bg.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "User Not Registered", Toast.LENGTH_LONG).show();
                            spin_kit.setVisibility(View.GONE);
                            rl_bg.setVisibility(View.GONE);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

            private void forgotPassowrdClick() {
                tv_forgot_password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //dialogForEmailOrPhoneCaturing();
                    }
                });
            }



    private void signupClick()
    {
        rl_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginScreen.this, RegistrationScreen.class);
                startActivity(i);
            }
        });
    }
}