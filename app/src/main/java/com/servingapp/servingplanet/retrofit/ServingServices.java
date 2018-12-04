package com.servingapp.servingplanet.retrofit;

import com.servingapp.servingplanet.models.LoginResponseModel;
import com.servingapp.servingplanet.models.QuestionModel;
import com.servingapp.servingplanet.models.ResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Abhjit on 30/11/2017.
 */

public interface ServingServices {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponseModel> wsLogin(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("validatelogintoken")
    Call<ResponseModel> wsCheckOTP(@Header("Authorization") String authHeader, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("checkuserexist")
    Call<ResponseModel> wsCheckUserExist(@FieldMap Map<String, String> map);

    @GET("security-question")
    Call<QuestionModel> wsGetQuestions();

    @FormUrlEncoded
    @POST("account-recovery")
    Call<ResponseModel> wssubmitUserAnswer(@FieldMap Map<String, String> map);

//
//    @FormUrlEncoded
//    @POST("reset-passwordlink")
//    Call<ResponseModel> wsResetPassword(@FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST("register")
//    Call<RegistrationResponseModel> wsRegister(@FieldMap Map<String, String> map);
//
//    @GET("getuserdashboard")
//    Call<DashboardResponseModel> wsGetDashboardData(@Header("Authorization") String authHeader);
//
//    @GET("get-reference-id")
//    Call<ResponseModel> wsGetReferralLink(@Header("Authorization") String authHeader);
//
//    @GET("getprofileinfo")
//    Call<ProfileResponseModel> wsGetUserProfile(@Header("Authorization") String authHeader);
//
//    @FormUrlEncoded
//    @POST("withdrawReport")
//    Call<WithdrawReportResponseModel> wsGetWithdrawReport(@Header("Authorization") String authHeader, @FieldMap Map<String, String> map);
//
//    @GET("getlevel")
//    Call<LevelResponseModel> wsGetLevels(@Header("Authorization") String authHeader);
//
//    @GET("getCurrency/usd")
//    Call<ResponseModel> wsGetUSD(@Header("Authorization") String authHeader);
//
//    @FormUrlEncoded
//    @POST("usertasklist")
//    Call<UserTaskListResponseModel> wsGetUserTaskList(@Header("Authorization") String authHeader, @FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST("otpOnwithdrawIncome")
//    Call<ResponseModel> wsRedeemPoints(@Header("Authorization") String authHeader, @FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST("withdrawIncome")
//    Call<ResponseModel> wsWithdraw(@Header("Authorization") String authHeader, @FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST("level-view")
//    Call<LevelViewResponseModel> wsGetLevelView(@Header("Authorization") String authHeader, @FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST("level-income-datewise")
//    Call<PointsResponseModel> wsGetLevelIncomeDateWise(@Header("Authorization") String authHeader, @FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST("level-income")
//    Call<PointsResponseModel> wsGetLevelIncome(@Header("Authorization") String authHeader, @FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST("videoReport")
//    Call<VideoResponseModel> wsGetVideoReport(@Header("Authorization") String authHeader, @FieldMap Map<String, String> map);
//
//    @FormUrlEncoded
//    @POST("taskIncomeReport")
//    Call<PointsResponseModel> wsTaskIncome(@Header("Authorization") String authHeader, @FieldMap Map<String, String> map);
//
//    @Multipart
//    @POST("video-upload-resumable")
//    Call<ResponseModel> wsUploadVideo(@Header("Authorization") String authHeader, @PartMap() Map<String, RequestBody> partMap,
//                                      @Part MultipartBody.Part file);

}
