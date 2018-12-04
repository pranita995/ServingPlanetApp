package com.servingapp.servingplanet.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amit Ghuge on 30/11/2017.
 */

public class ApiHandler {
    private static final String DEV_BASE_URL = "https://new.mlmplandemo.in/serving_test/serving-planet-backend/public/api/";

    private static final long HTTP_TIMEOUT = TimeUnit.SECONDS.toMillis(60);
    private static ServingServices apiService;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(DEV_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static ServingServices getApiService() {
        if (apiService == null) {
            httpClient.connectTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
            httpClient.writeTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
            httpClient.readTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
            httpClient.retryOnConnectionFailure(true);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
            Retrofit retrofit = builder.client(httpClient.build()).build();

            apiService = retrofit.create(ServingServices.class);
            return apiService;
        } else {
            return apiService;
        }
    }
}
