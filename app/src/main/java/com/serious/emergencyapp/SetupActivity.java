package com.serious.emergencyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.serious.emergencyapp.utils.SharedPrefManager;

public class SetupActivity extends AppCompatActivity {

    private EditText etName, etBlood, etContact;
    private Button btnSave;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        prefManager = new SharedPrefManager(this);

        etName = findViewById(R.id.etName);
        etBlood = findViewById(R.id.etBlood);
        etContact = findViewById(R.id.etContact);
        btnSave = findViewById(R.id.btnSaveSetup);

        btnSave.setOnClickListener(v -> {
            prefManager.saveUserInfo(
                    etName.getText().toString(),
                    etBlood.getText().toString(),
                    etContact.getText().toString()
            );
            startActivity(new Intent(SetupActivity.this, MainActivity.class));
            finish();
        });

        //  Enable ActionBar Back Arrow
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
