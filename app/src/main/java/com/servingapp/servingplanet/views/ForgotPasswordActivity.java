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

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText mEditUserID;
    private Button mButtonSubmit;
    private TextView mTextLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeViews();
        registerListeners();
    }

    private void registerListeners() {

        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUserID()) {
                    checkUserExist();
                }
            }
        });

        mTextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean validateUserID() {
        String userID = mEditUserID.getText().toString().trim();
        if (userID.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.invalid_user_id), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void checkUserExist() {
        final ProgressDialog pd = new ProgressDialog(ForgotPasswordActivity.this);
        pd.setTitle("Loading...");
        pd.setMessage("Please wait..!");
        pd.show();
        pd.setCancelable(false);
        Map<String, String> loginMap = new HashMap<>();

        loginMap.put("user_id", mEditUserID.getText().toString().trim());

        ServingServices apiService = ApiHandler.getApiService();
        final Call<ResponseModel> loginCall = apiService.wsCheckUserExist(loginMap);
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
                        startActivity(new Intent(ForgotPasswordActivity.this,
                                SecurityQuestionsActivity.class)
                                .putExtra("userId", mEditUserID.getText().toString().trim()));
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, ResponseModel.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,
                                  Throwable t) {
                pd.hide();
                Log.e("ERROR", t.toString());
                Toast.makeText(ForgotPasswordActivity.this, "Something went wrong..Please try again..!",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initializeViews() {
        mEditUserID = findViewById(R.id.edit_user_id);
        mButtonSubmit = findViewById(R.id.button_submit);
        mTextLogin = findViewById(R.id.text_back_to_login);
    }
}
