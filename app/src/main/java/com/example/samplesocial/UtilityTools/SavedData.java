package com.example.samplesocial.UtilityTools;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.samplesocial.Retrofit.Constant;


/**
 * Created by Anil on 12/3/2021.
 */
public class SavedData {
    private static final String BroadcasterId = "BroadcasterId";
    private static final String FirebaseToken = "FirebaseToken";
    private static final String NotificationOnOff = "NotificationOnOff";
    private static final String Language = "Language";
    private static final String Welcome = "Welcome";
    private static final String gmailLogin = "gmailLogin";
    private static String savedInstagramName = "savedInstagramName";
    private static SharedPreferences prefs;
    private static String facebookId = "facebookId";
    private static String facebookToken = "facebookToken";
    private static String socialmediaUserName = "fullname";
    private static String socialMediaTitle = "socialMediaTitle";
    private static String socialMediaDesc = "socialMediaDesc";
    private static String savedOnceWelcome = "savedOnceWelcome";
    private static String savedInstagramToken = "savedInstagramToken";
    private static String saveTwitchName = "saveTwitchName";
    private static String twitchToken = "twitchToken";
    private static String twitterToken = "twitterToken";
    private static String twitterUserName = "twitterUserName";
    private static String twitterName = "twitterName";
    private static String twitterID="twitterID";
    private static String Latitude = "Latitude";
    private static String Longitude = "Longitude ";
    private static String Speed = "Speed";
    private static String Adderess = "Adderess";
    private static String tripId = "tripId";


    public static SharedPreferences getInstance() {
        if (prefs == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(AppController.getInstance());
        }
        return prefs;
    }

    public static String getLanguage() {
        return getInstance().getString(Language, Constant.english);
    }

    public static void saveLanguage(String role) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Language, role);
        editor.apply();
    }

    public static boolean getNotificationOnOff() {
        return getInstance().getBoolean(NotificationOnOff, true);
    }

    public static void saveNotificationOnOff(boolean role) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(NotificationOnOff, role);
        editor.apply();
    }

    public static String getFirebaseToken() {
        return getInstance().getString(FirebaseToken, Constant.blank);
    }

    public static void saveFirebaseToken(String token) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(FirebaseToken, token);
        editor.apply();
    }

    public static String getFacebookId() {
        return getInstance().getString(facebookId, Constant.blank);
    }

    public static void saveFacebookId(String fbid) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(facebookId, fbid);
        editor.apply();
    }

    public static String getFacebookToken() {
        return getInstance().getString(facebookToken, Constant.blank);
    }

    public static void saveFacebookToken(String fbtoken) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(facebookToken, fbtoken);
        editor.apply();
    }

    public static String getSocialmediaUserName() {
        return getInstance().getString(socialmediaUserName, Constant.blank);
    }

    public static void saveSocialMediaUserName(String fbtoken) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(socialmediaUserName, fbtoken);
        editor.apply();
    }

    public static String getSocialMediaTitle() {
        return getInstance().getString(socialMediaTitle, Constant.blank);
    }

    public static void saveSocialMediaTitle(String title) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(socialMediaTitle, title);
        editor.apply();
    }

    public static String getSocialMediaDesc() {
        return getInstance().getString(socialMediaDesc, Constant.blank);
    }

    public static void saveSocialMediaDesc(String desc) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(socialMediaDesc, desc);
        editor.apply();
    }

    public static String getGmailAccount() {
        return getInstance().getString(gmailLogin, null);
    }

    public static void saveGmailAccount(String account) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(gmailLogin, account);
        editor.apply();
    }

    public static void deleteGmailAccount(String account) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(gmailLogin, account);
        editor.apply();
    }

    public static void savedOnceWelcome(boolean account) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(savedOnceWelcome, account);
        editor.apply();
    }

    public static boolean getOnceWelcome() {
        return getInstance().getBoolean(savedOnceWelcome, false);
    }

    public static void savedInstagramName(String account) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(savedInstagramName, account);
        editor.apply();
    }

    public static String getInstagramName() {
        return getInstance().getString(savedInstagramName, "");
    }

    public static void saveTwitchUserName(String saveTwitchUserName) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(saveTwitchName, saveTwitchUserName);
        editor.apply();
    }

    public static String getTwitchUserName() {
        return getInstance().getString(saveTwitchName, "");
    }

    public static String getSavedInstagramToken() {
        return getInstance().getString(savedInstagramToken, null);
    }

    public static void setSavedInstagramToken(String account) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(savedInstagramToken, account);
        editor.apply();
    }

    public static String getSavedTwitchToken() {
        return getInstance().getString(twitchToken, null);
    }

    public static void setSavedTwitchToken(String tToken) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(twitchToken, tToken);
        editor.apply();
    }

    public static void setSaveBroadcasterId(String broadcastid) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(BroadcasterId, broadcastid);
        editor.apply();
    }

    public static String getSavedBroadcasterId() {
        return getInstance().getString(BroadcasterId, null);
    }

    public static String getSavedTwitterToken() {
        return getInstance().getString(twitterToken, null);
    }

    public static void setSavedTwitterToken(String tToken) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(twitterToken, tToken);
        editor.apply();
    }

    public static void saveTwitterUserName(String saveTwitchUserName) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(twitterUserName, saveTwitchUserName);
        editor.apply();
    }

    public static String getTwitterUserName() {
        return getInstance().getString(twitterUserName, "");
    }

    public static void saveTwitterName(String twitterNameStr) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(twitterName, twitterNameStr);
        editor.apply();
    }

    public static String getTwitterName() {
        return getInstance().getString(twitterName, "");
    }

    public static void saveTwitterId(String twitterIDStr) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(twitterID, twitterIDStr);
        editor.apply();
    }

    public static String getTwitterId() {
        return getInstance().getString(twitterID, "");
    }

    private static String instagramGoliveEnable = "instagramGoliveEnable";
    public static void savedinstagramGoliveEnable(boolean enableUnable) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(instagramGoliveEnable, enableUnable);
        editor.apply();
    }

    public static boolean getinstagramGoliveEnable() {
        return getInstance().getBoolean(instagramGoliveEnable, false);
    }

    private static String facebookGoliveEnable = "facebookGoliveEnable";
    public static void savedfacebookGoliveEnable(boolean enableUnable) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(facebookGoliveEnable, enableUnable);
        editor.apply();
    }

    public static boolean getfacebookGoliveEnable() {
        return getInstance().getBoolean(facebookGoliveEnable, false);
    }

    private static String twitchGoliveEnable = "twitchGoliveEnable";
    public static void savedtwitchGoliveEnable(boolean enableUnable) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(twitchGoliveEnable, enableUnable);
        editor.apply();
    }

    public static boolean gettwitchGoliveEnable() {
        return getInstance().getBoolean(twitchGoliveEnable, false);
    }

    private static String youtubeGoliveEnable = "youtubeGoliveEnable";
    public static void savedyoutubeGoliveEnable(boolean enableUnable) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(youtubeGoliveEnable, enableUnable);
        editor.apply();
    }

    public static boolean getyoutubeGoliveEnable() {
        return getInstance().getBoolean(youtubeGoliveEnable, false);
    }


    public static String getTripId() {
        return getInstance().getString(tripId, Constant.blank);
    }

    public static void saveTripId(String tripIdStr) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(tripId, tripIdStr);
        editor.apply();
    }

    public static String getLatitude() {
        return getInstance().getString(Longitude, Constant.blank);

    }

    public static void saveLatitude(String latitude) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Latitude, latitude);
        editor.apply();
    }

    public static String getLongitude() {
        return getInstance().getString(Longitude, Constant.blank);
    }

    public static void saveLongitude(String longitude) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Longitude, longitude);
        editor.apply();
    }

    public static String getSpeed() {
        return getInstance().getString(Speed, Constant.blank);

    }

    public static void saveSpeed(String speed) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Speed, speed);
        editor.apply();
    }

    public static String getAdderess() {
        return getInstance().getString(Adderess, Constant.blank);

    }

    public static void saveAdderess(String adderess) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Adderess, adderess);
        editor.apply();
    }

    public static boolean getWelcome() {
        return getInstance().getBoolean(Welcome, false);
    }

    public static void saveWelcome(boolean role) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(Welcome, role);
        editor.apply();
    }
}