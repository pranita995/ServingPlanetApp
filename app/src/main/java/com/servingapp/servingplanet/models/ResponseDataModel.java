package com.servingapp.servingplanet.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseDataModel implements Serializable {
    @SerializedName("link")
    @Expose
    public String link;

    @SerializedName("usd_rate")
    @Expose
    public Float usdRate;

    public Float getUsdRate() {
        return usdRate;
    }

    public void setUsdRate(Float usdRate) {
        this.usdRate = usdRate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
