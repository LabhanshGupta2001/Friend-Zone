package com.example.samplesocial.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.samplesocial.UtilityTools.Utils;

import java.util.ArrayList;

/**
 * Created by Rakhi on 9/12/2021.
 */
public class UserDataHelper {
    private static UserDataHelper instance;
    private SQLiteDatabase db;
    private DataManager dm;
    Context cx;

    public UserDataHelper(Context cx) {
        instance = this;
        this.cx = cx;
        dm = new DataManager(cx, DataManager.DATABASE_NAME, null, DataManager.DATABASE_VERSION);
    }

    public static UserDataHelper getInstance() {
        return instance;
    }

    public void open() {
        db = dm.getWritableDatabase();
    }

    public void close() {
        //  db.close();
    }

    public void read() {
        db = dm.getReadableDatabase();
    }

    public void delete(int companyId) {
        open();
        db.delete(UserModel.TABLE_NAME, UserModel.KEY_ID + " = '" + companyId + "'", null);
        close();
    }

    public void deleteAll() {
        open();
        db.delete(UserModel.TABLE_NAME, null, null);
        close();
    }

    private boolean isExist(UserModel userModel) {
        read();
        Cursor cur = db.rawQuery("select * from " + UserModel.TABLE_NAME + " where " +
                UserModel.KEY_USER_ID + "='"
                + userModel.getUserId() + "'", null);
        if (cur.moveToFirst()) {
            return true;
        }
        return false;
    }

    public void insertData(UserModel userModel) {
        open();
        ContentValues values = new ContentValues();
        values.put(UserModel.KEY_USER_ID, userModel.getUserId());
        values.put(UserModel.KEY_ID, userModel.getId());
        values.put(UserModel.KeyName, userModel.getUserName());
        values.put(UserModel.KeyPhone, userModel.getUserPhone());
        values.put(UserModel.KeyprofilePic, userModel.getProfilepic());
        values.put(UserModel.Key_otp, userModel.getotp());
        values.put(UserModel.KEY_deviceType, userModel.getDevice_type());
        values.put(UserModel.KEY_fcm_id, userModel.getFcmId());
        values.put(UserModel.KEY_Token, userModel.getToken());


        if (!isExist(userModel)) {
            Utils.E("insert successfully");
            db.insert(UserModel.TABLE_NAME, null, values);
        } else {
            Utils.E("update successfully" + userModel.getUserId());
            db.update(UserModel.TABLE_NAME, values, UserModel.KEY_ID + "=" + userModel.getId(), null);
        }
        close();
    }

    @SuppressLint("Range")
    public ArrayList<UserModel> getList() {
        ArrayList<UserModel> userItem = new ArrayList<UserModel>();
        read();
        Cursor cursor = db.rawQuery("select * from " + UserModel.TABLE_NAME, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            do {
                UserModel sawIt = new UserModel();
                sawIt.setId(cursor.getString(cursor.getColumnIndex(UserModel.KEY_ID)));
                sawIt.setUserId(cursor.getString(cursor.getColumnIndex(UserModel.KEY_USER_ID)));
                sawIt.setUserName  (cursor.getString(cursor.getColumnIndex(UserModel.KeyName)));
                sawIt.setUserPhone (cursor.getString(cursor.getColumnIndex(UserModel.KeyPhone)));
                sawIt.setProfilepic(cursor.getString(cursor.getColumnIndex(UserModel.KeyprofilePic)));
                sawIt.setotp (cursor.getString(cursor.getColumnIndex(UserModel.Key_otp)));
                sawIt.setDevice_type (cursor.getString(cursor.getColumnIndex(UserModel.KEY_deviceType)));
                sawIt.setFcmId (cursor.getString(cursor.getColumnIndex(UserModel.KEY_fcm_id)));
                sawIt.setToken (cursor.getString(cursor.getColumnIndex(UserModel.KEY_Token)));
                userItem.add(sawIt);
            } while ((cursor.moveToPrevious()));
            cursor.close();
        }
        close();
        return userItem;
    }
}