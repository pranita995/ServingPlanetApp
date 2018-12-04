package com.servingapp.servingplanet.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponseDataModel implements Serializable {

    @SerializedName("mailverification")
    @Expose
    public String mailverification;
    @SerializedName("google2faauth")
    @Expose
    public String google2faauth;
    @SerializedName("mailotp")
    @Expose
    public String mailotp;
    @SerializedName("mobileverification")
    @Expose
    public String mobileverification;
    @SerializedName("otpmode")
    @Expose
    public String otpmode;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("sms")
    @Expose
    public String sms;
    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("master_pwd")
    @Expose
    public String master_pwd;

    public String getMaster_pwd() {
        return master_pwd;
    }

    public void setMaster_pwd(String master_pwd) {
        this.master_pwd = master_pwd;
    }

    public String getMailverification() {
        return mailverification;
    }

    public void setMailverification(String mailverification) {
        this.mailverification = mailverification;
    }

    public String getGoogle2faauth() {
        return google2faauth;
    }

    public void setGoogle2faauth(String google2faauth) {
        this.google2faauth = google2faauth;
    }

    public String getMailotp() {
        return mailotp;
    }

    public void setMailotp(String mailotp) {
        this.mailotp = mailotp;
    }

    public String getMobileverification() {
        return mobileverification;
    }

    public void setMobileverification(String mobileverification) {
        this.mobileverification = mobileverification;
    }

    public String getOtpmode() {
        return otpmode;
    }

    public void setOtpmode(String otpmode) {
        this.otpmode = otpmode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
