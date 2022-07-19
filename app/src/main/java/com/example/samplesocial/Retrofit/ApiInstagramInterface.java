package com.example.samplesocial.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInstagramInterface {
    //Facebook post url

    @GET(Constant.token+"={"+Constant.token+"}&"+Constant.cmd+"={"+Constant.cmd+"}")
    Call<ResponseBody> getInstagramProfile(@Path(Constant.token) String token,@Path(Constant.cmd) String cmd);

}
