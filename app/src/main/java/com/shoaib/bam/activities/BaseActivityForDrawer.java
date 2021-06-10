package com.shoaib.bam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.shoaib.bam.R;
import com.shoaib.bam.activities.RegisterOfficeAndRoom.RegisteringRoom;
import com.shoaib.bam.activities.RegisterOfficeAndRoom.RegisteringOffice;
import com.shoaib.bam.utilities.SharedPrefs;

public class BaseActivityForDrawer extends AppCompatActivity {

    public DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    ImageView iv_logo;
    TextView tv_staf_name, tv_staf_role, tv_company_name;


    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_for_drawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;
        mNavigationView.setItemIconTintList(null);

        // get menu from navigationView
        Menu menu = mNavigationView.getMenu();
        View hView =  mNavigationView.getHeaderView(0);
        iv_logo = (ImageView) hView.findViewById(R.id.iv_logo);
        tv_staf_name = (TextView) hView.findViewById(R.id.tv_staf_name);
        tv_staf_role = (TextView) hView.findViewById(R.id.tv_staf_role);
        tv_company_name = (TextView) hView.findViewById(R.id.tv_company_name);


        sharedPreferences = getSharedPreferences(SharedPrefs.PREF_NAME, 0);


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.menu_log_out){

                    String userKey = SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.key);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    Intent i = new Intent(BaseActivityForDrawer.this, LoginScreen.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                }




                if (menuItem.getItemId()== R.id.menu_add_office)
                {
                    Intent i = new Intent(BaseActivityForDrawer.this, RegisteringOffice.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);


                }
                if (menuItem.getItemId()== R.id.menu_add_room)
                {
                    Intent i = new Intent(BaseActivityForDrawer.this, RegisteringRoom.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                if (menuItem.getItemId()== R.id.menu_settings)
                {

                }




                return false;
            }

        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences sharedPreferences = getSharedPreferences(SharedPrefs.PREF_NAME, 0);
        final String userKey  = SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.key);
        String fullname  = SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.userFullName);
        String imageUrl = SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.userProfileImageUrl);
        String stafRole = SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.userType);
        String stafCompany = SharedPrefs.getStringPref(sharedPreferences, SharedPrefs.companyId);
        tv_staf_name.setText(fullname);
        tv_company_name.setText("BAM");
        tv_staf_role.setText("(User)");
    }
}