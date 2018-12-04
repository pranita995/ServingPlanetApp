package com.servingapp.servingplanet.views;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.servingapp.servingplanet.R;
import com.servingapp.servingplanet.models.ResponseModel;
import com.servingapp.servingplanet.retrofit.ApiHandler;
import com.servingapp.servingplanet.retrofit.ServingServices;
import com.servingapp.servingplanet.utils.SharedPreferenceUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerificationActivity extends AppCompatActivity {
    private TextView mTextBackToLogin;
    private EditText mEditVerificationCode;
    private Button mButtonSubmit;
    private String mUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeViews();
        registerListeners();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mUserId = extras.getString("userID");
        }
    }

    private void registerListeners() {

        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateCode()) {
                    validateOTP();
                }
            }
        });

        mTextBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void validateOTP() {
        final ProgressDialog pd = new ProgressDialog(OTPVerificationActivity.this);
        pd.setTitle("Loading...");
        pd.setMessage("Please wait..!");
        pd.show();
        pd.setCancelable(false);
        Map<String, String> loginMap = new HashMap<>();

        loginMap.put("googleotp", mEditVerificationCode.getText().toString().trim());
        loginMap.put("user_id", mUserId);

        ServingServices apiService = ApiHandler.getApiService();
        final Call<ResponseModel> loginCall = apiService.wsCheckOTP("Bearer " + SharedPreferenceUtils.getToken(
                OTPVerificationActivity.this), loginMap);
        loginCall.enqueue(new Callback<ResponseModel>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<ResponseModel> call,
                                   Response<ResponseModel> response) {
                pd.hide();
                if (response.isSuccessful()) {
                    ResponseModel ResponseModel = response.body();
                    if (!ResponseModel.getStatus().isEmpty() &&
                            ResponseModel.getStatus().trim().equalsIgnoreCase("OK")) {
                        startActivity(new Intent(OTPVerificationActivity.this, PinActivity.class));
                        finish();
                    } else {
                        Toast.makeText(OTPVerificationActivity.this, ResponseModel.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,
                                  Throwable t) {
                pd.hide();
                Log.e("ERROR", t.toString());
                Toast.makeText(OTPVerificationActivity.this, "Something went wrong..Please try again..!",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean validateCode() {
        String code = mEditVerificationCode.getText().toString().trim();
        if (code.isEmpty()) {
            Toast.makeText(OTPVerificationActivity.this,
                    getString(R.string.invalid_verification_code), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initializeViews() {
        mTextBackToLogin = findViewById(R.id.text_back_to_login);
        mEditVerificationCode = findViewById(R.id.edit_verification_code);
        mButtonSubmit = findViewById(R.id.button_submit);
    }
}
