package uz.mobiler.wallpaperappg2122.retrofit;


import android.content.Context;

import com.mocklets.pluto.PlutoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    final String BASE_URL = "https://api.unsplash.com/";

    public Retrofit getRetrofit(Context context){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new PlutoInterceptor()).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

   public ApiService apiService(Context context){
        return getRetrofit(context).create(ApiService.class);
    }
//    fun apiService(context: Context) = getRetrofit(context).create(ApiService::class.java)

}