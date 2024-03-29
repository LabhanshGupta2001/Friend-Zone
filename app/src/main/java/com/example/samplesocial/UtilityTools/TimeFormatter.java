package com.enwdtech.sawit.UtilityTools;

import android.content.Context;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;

import com.google.android.gms.common.util.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeFormatter {
    @NonNull
    public static String timeAgo(String date, String Pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(Pattern, Locale.ENGLISH);
        Date time_ago = null;
        try {
            time_ago = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long currenttime = System.currentTimeMillis();
        long diff = currenttime - time_ago.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String time = null;
        if (diffDays > 0) {
            if (diffDays == 1) {
                time = diffDays + " d ago ";
            } else if (diffDays <= 7) {
                time = diffDays + " d ago ";
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd,yyyy");
                time = dateFormat.format(time_ago);
            }
        } else {
            if (diffHours > 0) {
                if (diffHours == 1) {
                    time = diffHours + " h ago";
                } else {
                    time = diffHours + " h ago";
                }
            } else {
                if (diffMinutes > 0) {
                    if (diffMinutes == 1) {
                        time = diffMinutes + " m ago";
                    } else {
                        time = diffMinutes + " m ago";
                    }
                } else {
                    if (diffSeconds <= 0) {
                        time = "Now";
                    } else {
                        time = diffSeconds + " s ago";
                    }
                }

            }

        }
        return time;
    }

    @NonNull
    public static String timeAgo(Date time_ago) {
        long currenttime = System.currentTimeMillis();
        long diff = currenttime - time_ago.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String time = null;
        if (diffDays > 0) {
            if (diffDays == 1) {
                time = diffDays + " d ago ";
            } else if (diffDays <= 7) {
                time = diffDays + " d ago ";
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd,yyyy");
                time = dateFormat.format(time_ago);
            }
        } else {
            if (diffHours > 0) {
                if (diffHours == 1) {
                    time = diffHours + " h ago";
                } else {
                    time = diffHours + " h ago";
                }
            } else {
                if (diffMinutes > 0) {
                    if (diffMinutes == 1) {
                        time = diffMinutes + " m ago";
                    } else {
                        time = diffMinutes + " m ago";
                    }
                } else {
                    if (diffSeconds <= 0) {
                        time = "Now";
                    } else {
                        time = diffSeconds + " s ago";
                    }
                }

            }

        }
        return time;
    }

    @NonNull
    public static String getFormattedDate(String date, Context context, String Pattern) throws ParseException {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat(Pattern, Locale.getDefault());
        dateFormat1.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = dateFormat1.parse(date);

        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(date1.getTime());

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EEE, MMM d | h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "Today" + " " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday" + " " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMM dd yyyy | h:mm aa", smsTime).toString();
        }
    }

    public static String getDateTime(String date, Context context, String Pattern, @NonNull String For) throws ParseException {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat(Pattern, Locale.getDefault());
        dateFormat1.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = dateFormat1.parse(date);

        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(date1.getTime());
        if (For.equals("Date")) {
            return DateFormat.format("dd MMM yyyy", smsTime).toString();
        } else {
            return DateFormat.format("h:mm:ss aa", smsTime).toString();
        }

    }

    public static String changeDateFormat(String currentFormat, String requiredFormat, String dateString) {
        String result = "";
        if (Strings.isEmptyOrWhitespace(dateString)) {
            return result;
        }
        SimpleDateFormat formatterOld = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat formatterNew = new SimpleDateFormat(requiredFormat, Locale.getDefault());
        Date date = null;
        try {
            date = formatterOld.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            result = formatterNew.format(date);
        }
        return result;
    }

}