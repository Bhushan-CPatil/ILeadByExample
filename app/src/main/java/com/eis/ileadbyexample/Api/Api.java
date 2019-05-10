package com.eis.ileadbyexample.Api;

import com.eis.ileadbyexample.Models.BtnEnDsResponce;
import com.eis.ileadbyexample.Models.DefaultResponse;
import com.eis.ileadbyexample.Models.ErrorBooleanResponce;
import com.eis.ileadbyexample.Models.LoginResponse;
import com.eis.ileadbyexample.Pojo.HistoryList;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userLogin(
            @Field("ecode") String email,
            @Field("password") String password,
            @Field("mobono") String mPhoneNumber,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("registerUser")
    Call<DefaultResponse> registerUser(
            @Field("id") String lid,
            @Field("password") String password,
            @Field("mobno") String mobileno,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("verifyuser")
    Call<DefaultResponse> checkOTP(
            @Field("otp") String OTPPASS,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("resendotp")
    Call<DefaultResponse> resendOTP(
            @Field("id") String id,
            @Field("contact") String contact,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("logout")
    Call<DefaultResponse> Logout(
            @Field("ecode") String ecode,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("fcinsertion")
    Call<DefaultResponse> FCALL(
            @Field("ecode") String ecode,
            @Field("latlang") String latlang,
            @Field("loc") String loc,
            @Field("pincode") String pincode,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("lcinsertion")
    Call<DefaultResponse> LCALL(
            @Field("ecode") String ecode,
            @Field("latlang") String latlang,
            @Field("loc") String loc,
            @Field("pincode") String pincode,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("checkstatus")
    Call<ErrorBooleanResponce> islogincontinue(
            @Field("ecode") String ecode,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("checkcall")
    Call<BtnEnDsResponce> btnenable(
            @Field("ecode") String ecode,
            @Field("DBPrefix") String DBPrefix
    );

    @FormUrlEncoded
    @POST("allcallhistory")
    Call<HistoryList> historyList(
            @Field("ecode") String ecode,
            @Field("DBPrefix") String DBPrefix
    );
}
