package com.example.samplesocial.Retrofit;

import com.enwdtech.sawit.model.FacebookModelVideo;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiFacebookInterface {
    //Facebook post url
    @FormUrlEncoded
    @POST("/{user-id}/live_videos?status=LIVE_NOW")
    Call<FacebookModelVideo> facebook_post(@FieldMap HashMap<String, String> hm, @Path(Constant.userid) String userid);

}
