package com.servingapp.servingplanet.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.servingapp.servingplanet.R;
import com.servingapp.servingplanet.utils.SharedPreferenceUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(5 * 1000);
                    Intent intent = null;
                    if (SharedPreferenceUtils.getToken(SplashActivity.this) == null ||
                            SharedPreferenceUtils.getSecurityPin(SplashActivity.this) == null) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, PinActivity.class));
                    }
                    finish();
                } catch (Exception e) {
                }
            }
        };

        // start thread
        background.start();
    }
}
