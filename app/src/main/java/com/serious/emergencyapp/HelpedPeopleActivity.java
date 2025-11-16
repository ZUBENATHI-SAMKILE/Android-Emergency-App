package com.serious.emergencyapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class HelpedPeopleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helped_people);

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
