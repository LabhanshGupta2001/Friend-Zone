package com.example.samplesocial.UtilityTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.samplesocial.R;
import com.example.samplesocial.Retrofit.Constant;
import com.example.samplesocial.activity.LoginActivity;
import com.example.samplesocial.database.UserDataHelper;
import com.example.samplesocial.database.UserModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Anil on 12/3/2021.
 */

public class Utils {

    public static UserModel GetSession() {
        return UserDataHelper.getInstance().getList().get(0);
    }

    public static boolean IS_LOGIN() {
        return UserDataHelper.getInstance().getList().size() > 0;
    }

    public static Intent getFileChooser(String[] mimeTypes) {
        //

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";

            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }

            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }

        return intent;
    }

    public static void I(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
    }

    public static <T> List<T> removeDuplicates(List<T> list) {

        // Create a new ArrayList
        List<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

    public static String formatToYesterdayOrToday(String date) throws ParseException {
        Date dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        java.text.DateFormat timeFormatter = new SimpleDateFormat("hh:mma");

        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return "Today | " + timeFormatter.format(dateTime);
        } else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            return "Yesterday | " + timeFormatter.format(dateTime);
        } else {
            return date;
        }
    }

  /*  public static void UnAuthorizationToken(Context cx) {
        UserDataHelper.getInstance().deleteAll();
        I_clear(cx, GetStartedActivity.class, null);
    }*/

    /**
     * Change the status bar Color of the Activity to the Desired Color.
     *
     * @param activity - Activity
     * @param color    - Desired Color
     */
    public static void changeStatusBarColor(Activity activity, int color) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(activity, color));
    }

    public static void UnAuthorizationToken(Context cx) {
        UserDataHelper.getInstance().deleteAll();
        I_clear(cx, LoginActivity.class, null);
    }


    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
//getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

//Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Utils.E("Package Name=" + context.getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

// String key = new String(Base64.encodeBytes(md.digest()));
                Utils.E("Key Hash=" + key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Utils.E("Name not found" + e1);
        } catch (NoSuchAlgorithmException e) {
            Utils.E("No such an algorithm" + e);
        } catch (Exception e) {
            Utils.E("Exception" + e);
        }

        return key;
    }


    public static void I_finish(Activity cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        //  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
        cx.finish();
    }

    public static void I_clear(Context cx, Class<?> startActivity, Bundle data) {
        Intent i = new Intent(cx, startActivity);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (data != null)
            i.putExtras(data);
        cx.startActivity(i);
    }

    public static void E(String msg) {
        if (Const.Development.equals(Constant.Debug))
            Log.e("Log.E By Anil", msg);
    }

    public static String getFormattedDate(long smsTimeInMilis, Context context) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EEE, MMM d | h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return context.getString(R.string.today) + " " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return context.getString(R.string.yesterday) + " " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy | h:mm aa", smsTime).toString();
        }
    }

    public static void logOutTwitch(Context c) {

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(aBoolean -> Utils.E("check ClearCookies::" + aBoolean));


    }

    public static Dialog initProgressDialog(Context c) {
        Dialog dialog = new Dialog(c);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public static void T(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
    }

    public static void share(Context c, String subject, String shareBody) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        c.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void T_Long(Context c, String msg) {
        Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
    }

    public static void alert(Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.app_name));
        builder.setMessage(message)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog alertdialog = builder.create();
        alertdialog.show();
    }

    public static void setBottomSheetFullHeight(Activity activity, BottomSheetDialog bottomSheetDialog) {
        setupFullHeight(bottomSheetDialog, activity);

    }

    private static void setupFullHeight(BottomSheetDialog bottomSheetDialog, Activity activity) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight(activity);
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private static int getWindowHeight(Activity activity) {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        (activity).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static void setLanguage(String language, Context context) {
        SavedData.saveLanguage(language);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
        LocaleHelper.setLocale(context, language);
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;
        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }
        return (extension != null) ? extension : Constant.blank;
    }

}