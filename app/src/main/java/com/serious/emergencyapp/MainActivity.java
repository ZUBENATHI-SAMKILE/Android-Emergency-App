package com.serious.emergencyapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.serious.emergencyapp.utils.LocationHelper;
import com.serious.emergencyapp.utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private Button btnSendSms, btnCall;
    private ImageButton btnSettings, btnRefresh;
    private SharedPrefManager prefManager;
    private LocationHelper locationHelper;
    private Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefManager = new SharedPrefManager(this);
        locationHelper = new LocationHelper(this, location -> lastLocation = location);

        btnSendSms = findViewById(R.id.btnSendSms);
        btnCall = findViewById(R.id.btnCall);
        btnSettings = findViewById(R.id.btnSettings);
        btnRefresh = findViewById(R.id.btnRefresh);

        btnSettings.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        btnRefresh.setOnClickListener(v ->
                locationHelper.requestSingleUpdate());

        btnSendSms.setOnClickListener(v -> sendEmergencySms());
        btnCall.setOnClickListener(v -> makeEmergencyCall());

        // Get location at startup
        locationHelper.getLastLocation();
    }

    private void sendEmergencySms() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
            return;
        }

        String phone = prefManager.getContactNumber();
        String name = prefManager.getUserName();
        String blood = prefManager.getBloodType();

        if (phone.isEmpty()) {
            Toast.makeText(this, "Please set emergency contact in Settings.", Toast.LENGTH_SHORT).show();
            return;
        }

        String message = "🚨 Emergency Alert from " + name +
                "\nBlood Type: " + blood +
                "\nLocation: " + (lastLocation != null
                ? "https://maps.google.com/?q=" + lastLocation.getLatitude() + "," + lastLocation.getLongitude() + "Please help immediately!"
                : "Location not available");

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, message, null, null);

        Toast.makeText(this, "Emergency SMS sent!", Toast.LENGTH_SHORT).show();
    }

    private void makeEmergencyCall() {
        String phone = prefManager.getContactNumber();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
            return;
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "Set emergency contact first.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        startActivity(callIntent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted. Try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
