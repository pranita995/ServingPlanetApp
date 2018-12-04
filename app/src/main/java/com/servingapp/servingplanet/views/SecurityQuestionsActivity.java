package com.servingapp.servingplanet.views;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.servingapp.servingplanet.R;
import com.servingapp.servingplanet.models.QuestionDataModel;
import com.servingapp.servingplanet.models.QuestionModel;
import com.servingapp.servingplanet.models.ResponseModel;
import com.servingapp.servingplanet.retrofit.ApiHandler;
import com.servingapp.servingplanet.retrofit.ServingServices;
import com.servingapp.servingplanet.utils.Constants;
import com.servingapp.servingplanet.utils.Utils;
import com.servingapp.servingplanet.utils.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecurityQuestionsActivity extends AppCompatActivity {
    private EditText mEditUserAnswer;
    private Spinner mSpinnerQuestion;
    private Button mSubmitQuestion;
    private ArrayList<QuestionDataModel> mListQuestion;
    private String mQuestionId = "", mStringUserId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_questions);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mStringUserId = extras.getString("userId");
        }

        initializeViews();
        registerListeners();

        if (Utils.checkInternetConnection(SecurityQuestionsActivity.this)) {
            getQuestions();
        } else {
            Toast.makeText(SecurityQuestionsActivity.this, getString(R.string.no_internet_connection_message), Toast.LENGTH_SHORT).show();
        }
    }

    private void getQuestions() {


        final ProgressDialog pd = ViewUtils.getProgressBar(SecurityQuestionsActivity.this, "Loading...", "Please wait..!");

        ServingServices apiService = ApiHandler.getApiService();
        final Call<QuestionModel> loginCall = apiService.wsGetQuestions();
        loginCall.enqueue(new Callback<QuestionModel>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<QuestionModel> call,
                                   Response<QuestionModel> response) {
                pd.hide();

                if (response.isSuccessful()) {
                    QuestionModel questionModel = response.body();
                    if (questionModel.getCode() == Constants.RESPONSE_CODE_OK &&
                            questionModel.getStatus().equals("OK")) {
                        setStateSpinnerAdapter(questionModel.getQuestionDataModel());
                    } else {
                        Toast.makeText(SecurityQuestionsActivity.this, questionModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<QuestionModel> call, Throwable t) {
                pd.hide();
                Toast.makeText(SecurityQuestionsActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setStateSpinnerAdapter(QuestionDataModel data) {
        List<String> listQuestionName = new ArrayList<>();
        mListQuestion = new ArrayList<QuestionDataModel>();
        listQuestionName.add("Select Security Question");
        listQuestionName.add(data.getQue1());
        listQuestionName.add(data.getQue2());
        listQuestionName.add(data.getQue3());
        listQuestionName.add(data.getQue4());
        listQuestionName.add(data.getQue5());
        listQuestionName.add(data.getQue6());

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SecurityQuestionsActivity.this,
                R.layout.layout_spinner_item, listQuestionName);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerQuestion.setAdapter(spinnerArrayAdapter);
    }

    private void registerListeners() {

        mSubmitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateQuestion() && validateAnswer()) {
                    submitAnswer();
                }
            }
        });

        mSpinnerQuestion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    mQuestionId = "que" + position;
                } else {
                    mQuestionId = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void submitAnswer() {
        final ProgressDialog pd = new ProgressDialog(SecurityQuestionsActivity.this);
        pd.setTitle("Loading...");
        pd.setMessage("Please wait..!");
        pd.show();
        pd.setCancelable(false);
        Map<String, String> loginMap = new HashMap<>();

        loginMap.put("ans", mEditUserAnswer.getText().toString().trim());
        loginMap.put("que", mQuestionId);
        loginMap.put("user_id", mStringUserId);

        ServingServices apiService = ApiHandler.getApiService();
        final Call<ResponseModel> loginCall = apiService.wssubmitUserAnswer(loginMap);
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
                        Intent i = new Intent(SecurityQuestionsActivity.this, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(SecurityQuestionsActivity.this, ResponseModel.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,
                                  Throwable t) {
                pd.hide();
                Log.e("ERROR", t.toString());
                Toast.makeText(SecurityQuestionsActivity.this, "Something went wrong..Please try again..!",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean validateAnswer() {
        String answer = mEditUserAnswer.getText().toString().trim();
        if (answer.isEmpty()) {
            Toast.makeText(SecurityQuestionsActivity.this,
                    getString(R.string.invalid_answer), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateQuestion() {
        if (mQuestionId.equals("")) {
            Toast.makeText(SecurityQuestionsActivity.this,
                    getString(R.string.select_security_question), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initializeViews() {
        mEditUserAnswer = findViewById(R.id.edit_user_answer);
        mSpinnerQuestion = findViewById(R.id.spinner_questions);
        mSubmitQuestion = findViewById(R.id.button_submit_question);
    }
}
