package com.example.reserve;

import com.example.reserve.Notifications.MyResponse;
import com.example.reserve.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(


            {
                    "Content-Type:application/json",



            }
            )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
