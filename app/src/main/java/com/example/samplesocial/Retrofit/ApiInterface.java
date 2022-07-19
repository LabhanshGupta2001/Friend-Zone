package com.example.samplesocial.Retrofit;


import com.enwdtech.sawit.model.ChildDetail;
import com.enwdtech.sawit.model.ChildStatusModel;
import com.enwdtech.sawit.model.CommonResponseModel;
import com.enwdtech.sawit.model.CountryModel;
import com.enwdtech.sawit.model.CreateNewPassModel;
import com.enwdtech.sawit.model.CurrentBalanceModel;
import com.enwdtech.sawit.model.GetTripStatus;
import com.enwdtech.sawit.model.GetUploadFileModel;
import com.enwdtech.sawit.model.GetUserProfileModel;
import com.enwdtech.sawit.model.LoginModel;
import com.enwdtech.sawit.model.ManageChildModel;
import com.enwdtech.sawit.model.MatchOTPModel;
import com.enwdtech.sawit.model.NotificationModel;
import com.enwdtech.sawit.model.OldRecordingResponseModel;
import com.enwdtech.sawit.model.PlanDetail;
import com.enwdtech.sawit.model.ResendOtpModel;
import com.enwdtech.sawit.model.ResetPasswordModel;
import com.enwdtech.sawit.model.Signup;
import com.enwdtech.sawit.model.StartTripModel;
import com.enwdtech.sawit.model.TransactionData;
import com.enwdtech.sawit.model.UpdateFCMModel;
import com.enwdtech.sawit.model.UpdateTripdModel;
import com.enwdtech.sawit.model.UpdateUserProfileModel;
import com.enwdtech.sawit.model.VerfiyMobileModel;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kanchan 21/08/2020.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST(Constant.signup)
    Call<Signup> signup(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Constant.login)
    Call<LoginModel> login(@FieldMap HashMap<String, String> hm);


    @FormUrlEncoded
    @POST(Constant.generate_user_pin)
    Call<CommonResponseModel> generatePin(@FieldMap HashMap<String, String> hm);


    @FormUrlEncoded
    @POST(Constant.get_user_by_id)
    Call<LoginModel> get_user_by_id(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Constant.forgot_password_url_request)
    Call<ResetPasswordModel> forgot_password(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Constant.change_pin_request)
    Call<ResetPasswordModel> change_pin_request(@Header(Constant.Authorization) String Token, @FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @PUT(Constant.verfiy_mobile)
    Call<VerfiyMobileModel> verfiy_mobile(@Header(Constant.Authorization) String Token, @FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @PUT(Constant.update_fcmId)
    Call<UpdateFCMModel> update_fcmId(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Constant.change_user_password)
    Call<CreateNewPassModel> change_user_password(@Header(Constant.Authorization) String Token, @FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Constant.match_otp)
    Call<MatchOTPModel> match_otp(@FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Constant.match_user_pin)
    Call<CommonResponseModel> match_user_pin(@Header(Constant.Authorization) String Token, @FieldMap HashMap<String, String> hm);

    @GET(Constant.resend_otp)
    Call<ResendOtpModel> resend_otp(@Query(Constant.mobile) String mobile, @Query(Constant.type) String type);

    @GET(Constant.get_upload_file)
    Call<GetUploadFileModel> get_upload_file(@Header(Constant.Authorization) String token, @Query(Constant.platform) String platform);


    @DELETE(Constant.remove_upload_file + "/{" + Constant.remove_id + "}")
    Call<CommonResponseModel> remove_upload_file(@Header(Constant.Authorization) String Token, @Path(Constant.remove_id) String remove_id);


    @GET(Constant.get_user_profile)
    Call<GetUserProfileModel> get_user_profile(@Header(Constant.Authorization) String Token);

    @GET(Constant.get_upload_file)
    Call<OldRecordingResponseModel> getOldUploadFile(@Header(Constant.Authorization) String Token);

    @GET(Constant.get_notification)
    Call<NotificationModel> get_notification(@Header(Constant.Authorization) String Token);

    @Multipart
    @POST(Constant.update_user_profile)
    Call<UpdateUserProfileModel> update_user_profile(@Header(Constant.Authorization) String Token,
                                                     @PartMap HashMap<String, RequestBody> hm,
                                                     @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST(Constant.child_request)
    Call<CommonResponseModel> child_request(@Header(Constant.Authorization) String Token, @FieldMap HashMap<String, String> hm);

    @Multipart
    @POST(Constant.upload_file)
    Call<CommonResponseModel> upload_file(@Header(Constant.Authorization) String Token, @Part MultipartBody.Part file, @Part MultipartBody.Part fileThumbnail);


    @FormUrlEncoded
    @POST(Constant.start_trip)
    Call<StartTripModel> start_trip(@Header(Constant.Authorization) String Token, @FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Constant.update_trip)
    Call<UpdateTripdModel> update_trip(@Header(Constant.Authorization) String Token, @FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @POST(Constant.purchase_plan)
    Call<CommonResponseModel> addPlanPurchase(@Header(Constant.Authorization) String Token, @FieldMap HashMap<String, String> hm);

    @FormUrlEncoded
    @PUT(Constant.sub_user_stream_duration)
    Call<CurrentBalanceModel> sub_user_stream_duration(@Header(Constant.Authorization) String Token, @FieldMap HashMap<String, String> hm);


    @FormUrlEncoded
    @PUT(Constant.change_pinConst)
    Call<CreateNewPassModel> change_pin(@Header(Constant.Authorization) String Token, @FieldMap HashMap<String, String> hm);

    @GET(Constant.get_plan_list)
    Call<PlanDetail> getAvailablePlan(@Header(Constant.Authorization) String Token, @Query(Constant.device_type) String device_type);

    // http://localhost/Projects/GitLabProjects/sawitweb/get_user_stream_duration
    @GET(Constant.get_user_stream_duration)
    Call<CurrentBalanceModel> get_user_stream_duration(@Header(Constant.Authorization) String Token);

    @GET(Constant.get_child_detail)
    Call<ManageChildModel> get_child_detail(@Header(Constant.Authorization) String Token);

    @GET(Constant.get_child_detail)
    Call<ChildStatusModel> get_child_detail_status(@Header(Constant.Authorization) String Token);

    @GET(Constant.delete_child + "/{" + Constant.child_user_id + "}")
    Call<ChildDetail> delete_child(@Header(Constant.Authorization) String Token,
                                   @Path(Constant.child_user_id) String child_user_id);


    @GET(Constant.getMyVehicleStatusApi)
    Call<GetTripStatus> getTripstatus(@Header(Constant.Authorization) String Token);



    @GET(Constant.get_transaction_history)
    Call<TransactionData> get_Transaction(@Header(Constant.Authorization) String Token);

    @GET(Constant.country_code)
    Call<ArrayList<CountryModel>> country_code();

    @FormUrlEncoded
    @POST(Constant.userSupportApi)
    Call<CommonResponseModel> contactUs(@Header(Constant.Authorization)String token, @FieldMap HashMap<String, String> hm);
}
