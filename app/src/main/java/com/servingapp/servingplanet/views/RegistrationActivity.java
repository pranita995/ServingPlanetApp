package com.servingapp.servingplanet.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.servingapp.servingplanet.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
}
