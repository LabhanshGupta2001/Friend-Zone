package com.example.samplesocial.database;

import android.database.sqlite.SQLiteDatabase;


import com.example.samplesocial.UtilityTools.Utils;

import java.io.Serializable;


/**
 * Created by Rakhi on 9/Dec/2021.
 */


public class UserModel implements Serializable {
    public static final String TABLE_NAME = "UserModelSawIT";
    public static final String KEY_ID = "_id";
    public static final String KEY_USER_ID = "user_id";
    public static final String KeyName = "name";
    public static final String KeyPhone = "phone";
    public static final String KeyprofilePic = "profilePic";
    public static final String Key_otp = "otp";
    public static final String KEY_deviceType = "deviceType";
    public static final String KEY_fcm_id = "fcm_id";
    public static final String KEY_Token = "token";



    public static void creteTable(SQLiteDatabase db) {
        String CREATE_CLIENTTABLE = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_USER_ID + " text," +
                KeyName + " text," +
                KeyprofilePic + " text," +
                Key_otp + " text," +
                KEY_deviceType + " text, " +
                KEY_fcm_id + " text, " +
                KEY_Token + " text, " +
                KeyPhone + " text " +
                ")";
        db.execSQL(CREATE_CLIENTTABLE);
        Utils.E("check Create table::" + CREATE_CLIENTTABLE);
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }



    String Id;
    String userId;
    String userName;
    String userPhone;
    String otp;
    String profilepic;
    String token;
    String fcmId;
    String email;
    String pin;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    String deviceType;

    public String getFcmId() {
        return fcmId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    String device_type;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getotp() {
        return otp;
    }

    public void setotp(String otp) {
        this.otp = otp;
    }




    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }





    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
