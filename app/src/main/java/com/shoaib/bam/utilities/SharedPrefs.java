package com.shoaib.bam.utilities;

import android.content.SharedPreferences;

public class SharedPrefs {

    public static final String PREF_NAME = "PREF_NAME";
    public static final String key = "key";
    public static final String companyId = "companyId";
    public static final String userId = "userId";
    public static final String createdById = "createdById";
    public static final String userRegionId = "userRegionId";
    public static final String userFullName = "userFullName";
    public static final String username = "username";
    public static final String userPhone = "userPhone";
    public static final String userLocationTrackingId = "userLocationTrackingId";
    public static final String userAddress = "userAddress";
    public static final String createdAt = "createdAt";
    public static final String userEmail = "userEmail";
    public static final String userDesignation = "userDesignation";
    public static final String userProfileImageUrl = "userProfileImageUrl";
    public static final String assigneStatus = "assigneStatus";
    public static final String lastDeliveryDate = "lastDeliveryDate";
    public static final String chain = "chain";
    public static final String isFirsTime = "isFirsTime";
    public static final String userRegionName = "userRegionName";
    public static final String creditLimit = "creditLimit";
    public static final String lastOrderDate = "lastOrderDate";
    public static final String lastReturnDate = "lastReturnDate";
    public static final String lastReturnedDate = "lastReturnedDate";
    public static final String lastStockDate = "lastStockDate";
    public static final String lastPaymentCollectionDate = "lastPaymentCollectionDate";
    public static final String lastOrderId = "lastOrderId";
    public static final String lastOrderPaymentStatus = "lastOrderPaymentStatus";
    public static final String creditDaysLimit = "creditDaysLimit";
    public static final String b2bDaysLimit = "b2bDaysLimit";
    public static final String lastCreditTimeStemp = "lastCreditTimeStemp";
    public static final String lastReplceBookDate = "lastReplceBookDate";
    public static final String lastReplacedDate = "lastReplacedDate";
    public static final String password = "password";
    public static final String userType = "userType";
    public static final String roomId = "roomId";
    public static final String officeId = "officeId";

    public static final String foremanId = "foremanId";
    public static final String fromTAB = "fromTAB";
    public static final String lastLogiinTimeStemp = "lastLogiinTimeStemp";
    public static final String onlineStatus = "onlineStatus";
    public static final String currentLat = "currentLat";
    public static final String currentLng = "currentLng";
    public static final String FID = "FID";
    public static final String batteryStatus = "batteryStatus";
    public static final String IP = "IP";
    public static final String DeviceName = "DeviceName";
    public static final String onlinStatus = "onlinStatus";
    public static final String appVersion = "appVersion";
    public static final String workingStatus = "workingStatus";
    public static final String lastWorkingId = "lastWorkingId";
    public static final String lastOnlineTimeStemp = "lastOnlineTimeStemp";
    public static final String lastActivScreenCloseTimStemp = "lastActivScreenCloseTimStemp";
    public static final String joiningDate = "joiningDate";
    public static final String lastLogin = "lastLogin";
    public static final String referenceNumber = "referenceNumber";
    public static final String lastReplacedId = "lastReplacedId";
    public static final String lastReturnedId = "lastReturnedId";
    public static final String lastStockId = "lastStockId";
    public static final String userEmiratsId = "userEmiratsId";
    public static final String userPasportId = "userPasportId";
    public static final String userNationality = "userNationality";
    public static final String userDOF = "userDOF";
    public static final String userEmiratesExpiryDate = "userEmiratesExpiryDate";
    public static final String userStatus = "userStatus";




//    public static SharedPrefs getInstance(Context context)
//    {
//
//    }


    public static int getIntPref(SharedPreferences sharedPreferences, String key) {
        return sharedPreferences.getInt(key, 0);
    }
    public static void StoreIntPref(SharedPreferences sharedPreferences,String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static String getStringPref(SharedPreferences sharedPreferences,String key) {
        return sharedPreferences.getString(key, "");
    }

    public static void StoreBooleanPref(SharedPreferences sharedPreferences, String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBooleanPref(SharedPreferences sharedPreferences,String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void StoreStringPref(SharedPreferences sharedPreferences,String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();


    }

    public static float getFloatPref(SharedPreferences sharedPreferences,String key) {
        return sharedPreferences.getFloat(key, 0);
    }
    public static void StoreFloatPref(SharedPreferences sharedPreferences,String key, float value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

}
