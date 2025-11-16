package com.serious.emergencyapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static final String PREF_NAME = "EmergencyAppPrefs";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_BLOOD = "blood_type";
    private static final String KEY_CONTACT = "contact_number";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Save user info
    public void saveUserInfo(String name, String bloodType, String contactNumber) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_BLOOD, bloodType);
        editor.putString(KEY_CONTACT, contactNumber);
        editor.apply();
    }

    // Getters
    public String getUserName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public String getBloodType() {
        return sharedPreferences.getString(KEY_BLOOD, "");
    }

    public String getContactNumber() {
        return sharedPreferences.getString(KEY_CONTACT, "");
    }

    // Clear all saved data
    public void clearData() {
        editor.clear();
        editor.apply();
    }
}
