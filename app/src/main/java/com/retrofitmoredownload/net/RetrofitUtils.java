package com.retrofitmoredownload.net;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by yfeng on 2017/11/16.
 */

public class RetrofitUtils {
//    public static RequestApi doHttpDeal(){
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(5, TimeUnit.SECONDS)
//                .readTimeout(5, TimeUnit.SECONDS)
////                .addNetworkInterceptor(new MyInterceptro())
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .client(client)
//                .baseUrl(RequestApi.BASE_URL)
//                .build();
//
//        RequestApi api = retrofit.create(RequestApi.class);
//
//        return api;
//    }

    public static RequestApi download(String baseurl){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseurl)
                .build();

        RequestApi api = retrofit.create(RequestApi.class);

        return api;
    }

}
