package com.serious.emergencyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.serious.emergencyapp.utils.SharedPrefManager;

public class SettingsActivity extends AppCompatActivity {

    private EditText etName, etBlood, etContact;
    private Button btnSave, btnTerms, btnAbout, btnHowTo, btnHelped;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

            //  Enable ActionBar Back Arrow
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }


        // Initialize Shared Preferences Manager
        prefManager = new SharedPrefManager(this);

        // Link XML views
        etName = findViewById(R.id.etName);
        etBlood = findViewById(R.id.etBlood);
        etContact = findViewById(R.id.etContact);
        btnSave = findViewById(R.id.btnSaveSettings);
        btnTerms = findViewById(R.id.btnTerms);
        btnAbout = findViewById(R.id.btnAbout);
        btnHowTo = findViewById(R.id.btnHowToUse);
        btnHelped = findViewById(R.id.btnHelped);

        // Load saved user info
        etName.setText(prefManager.getUserName());
        etBlood.setText(prefManager.getBloodType());
        etContact.setText(prefManager.getContactNumber());

        // Save updated info
        btnSave.setOnClickListener(v -> {
            prefManager.saveUserInfo(
                    etName.getText().toString(),
                    etBlood.getText().toString(),
                    etContact.getText().toString()
            );
            finish();
        });

        // Navigate to static info screens
        btnTerms.setOnClickListener(v -> startActivity(new Intent(this, TermsActivity.class)));
        btnAbout.setOnClickListener(v -> startActivity(new Intent(this, AboutActivity.class)));
        btnHowTo.setOnClickListener(v -> startActivity(new Intent(this, HowToUseActivity.class)));
        btnHelped.setOnClickListener(v -> startActivity(new Intent(this, HelpedPeopleActivity.class)));
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

