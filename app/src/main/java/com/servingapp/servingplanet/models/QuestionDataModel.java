package com.servingapp.servingplanet.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionDataModel {

    @SerializedName("que1")
    @Expose
    public String que1;
    @SerializedName("que2")
    @Expose
    public String que2;
    @SerializedName("que3")
    @Expose
    public String que3;
    @SerializedName("que4")
    @Expose
    public String que4;
    @SerializedName("que5")
    @Expose
    public String que5;
    @SerializedName("que6")
    @Expose
    public String que6;

    public String getQue1() {
        return que1;
    }

    public void setQue1(String que1) {
        this.que1 = que1;
    }

    public String getQue2() {
        return que2;
    }

    public void setQue2(String que2) {
        this.que2 = que2;
    }

    public String getQue3() {
        return que3;
    }

    public void setQue3(String que3) {
        this.que3 = que3;
    }

    public String getQue4() {
        return que4;
    }

    public void setQue4(String que4) {
        this.que4 = que4;
    }

    public String getQue5() {
        return que5;
    }

    public void setQue5(String que5) {
        this.que5 = que5;
    }

    public String getQue6() {
        return que6;
    }

    public void setQue6(String que6) {
        this.que6 = que6;
    }
}
