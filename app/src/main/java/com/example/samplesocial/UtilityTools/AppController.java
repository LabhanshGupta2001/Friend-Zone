package com.example.samplesocial.UtilityTools;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.samplesocial.database.UserDataHelper;


/**
 * Created by Anil on 12/3/2021.
 */


public class AppController extends Application {
    @SuppressLint("StaticFieldLeak")
    static Context context;
    @SuppressLint("StaticFieldLeak")
    private static AppController mInstance;
    ConnectivityManager connectivityManager;
    boolean connected = false;

    public AppController() {
        mInstance = this;
    }

    /**
     * @return
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**
     * @param ctx
     * @return
     */
    public static AppController getInstance(Context ctx) {
        context = ctx.getApplicationContext();
        return mInstance;
    }

    public static Context getContext() {
        return mInstance;
    }

    /**
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, LocaleHelper.getLanguage(base)));
    }

    /**
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        /*  LocaleHelper.setLocale(context, LocaleHelper.getLanguage(context));*/
    }

    /**
     * @return
     */
    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;
        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
        }
        return connected;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        SavedData.getInstance();
        new UserDataHelper(this);
    }


}

