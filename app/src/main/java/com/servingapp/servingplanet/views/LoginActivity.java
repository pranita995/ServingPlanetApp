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
import com.servingapp.servingplanet.models.LoginResponseModel;
import com.servingapp.servingplanet.retrofit.ApiHandler;
import com.servingapp.servingplanet.retrofit.ServingServices;
import com.servingapp.servingplanet.utils.SharedPreferenceUtils;
import com.servingapp.servingplanet.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditUserId, mEditPassword;
    private Button mButtonLogin;
    private TextView mTextForgotPassword, mTextSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        initializeViews();
        registerListeners();
    }

    private void registerListeners() {

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils utils = new Utils();
                if (utils.checkInternetConnection(LoginActivity.this)) {
                    if (validateId() && validatePassword()) {
                        login();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.no_internet_connection_message),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mTextForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

        mTextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }

    private void login() {
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setTitle("Loading...");
        pd.setMessage("Please wait..!");
        pd.show();
        pd.setCancelable(false);
        Map<String, String> loginMap = new HashMap<>();
        final String userName, password;
        userName = mEditUserId.getText().toString().trim();
        password = mEditPassword.getText().toString().trim();

        loginMap.put("user_id", userName);
        loginMap.put("password", password);

        ServingServices apiService = ApiHandler.getApiService();
        final Call<LoginResponseModel> loginCall =
                apiService.wsLogin(loginMap);
        loginCall.enqueue(new Callback<LoginResponseModel>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<LoginResponseModel> call,
                                   Response<LoginResponseModel> response) {
                pd.hide();
                if (response.isSuccessful()) {
                    LoginResponseModel loginResponseModel = response.body();
                    if (!loginResponseModel.getStatus().isEmpty() &&
                            loginResponseModel.getStatus().trim().equalsIgnoreCase("OK")) {
                        SharedPreferenceUtils.storeToken(LoginActivity.this, loginResponseModel.getData().getAccessToken());
                        Intent intent = null;
                        if (loginResponseModel.getData().getMaster_pwd() == null) {
                            intent = new Intent(LoginActivity.this, OTPVerificationActivity.class).putExtra("userID"
                                    , mEditUserId.getText().toString().trim());
                            startActivity(intent);
                        } else {
                            intent = new Intent(LoginActivity.this, PinActivity.class).putExtra("type"
                                    , loginResponseModel.getData().getGoogle2faauth());
                            startActivity(intent);
                            finish();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, loginResponseModel.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call,
                                  Throwable t) {
                pd.hide();
                Log.e("ERROR", t.toString());
                Toast.makeText(LoginActivity.this, "Something went wrong..Please try again..!",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean validatePassword() {
        String password = mEditPassword.getText().toString().trim();
        if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateId() {
        String userID = mEditUserId.getText().toString().trim();
        if (userID.isEmpty()) {
            Toast.makeText(LoginActivity.this, getString(R.string.invalid_user_id), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initializeViews() {
        mEditUserId = findViewById(R.id.edit_user_id);
        mEditPassword = findViewById(R.id.edit_password);
        mButtonLogin = findViewById(R.id.button_login);
        mTextForgotPassword = findViewById(R.id.text_forgot_password);
        mTextSignUp = findViewById(R.id.text_sign_up);
    }
}
