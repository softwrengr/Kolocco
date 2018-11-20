package com.kooloco.util;

import android.text.format.DateUtils;

import com.kooloco.core.Common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by hlink44 on 2/7/16.
 */
public class TimeConvertUtils {

    public static String dateTimeConvertServertToLocal(String timeConvert, String input, String output) {

        //COnvert local to local
        DateFormat timeFormater = new SimpleDateFormat(input);

        try {

            Date time;

            timeFormater.setTimeZone(TimeZone.getDefault());

            time = timeFormater.parse(timeConvert);

            DateFormat timeFormaterSecond = new SimpleDateFormat(output);


            return timeFormaterSecond.format(time);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";


/*
        //  DebugLog.e("Convert Time" + timeConvert);

        DateFormat timeFormater = new SimpleDateFormat(input);

        try {

            Date time;


            timeFormater.setTimeZone(TimeZone.getTimeZone(ExtraConstrant.SERVER_TIMEZONE));


            time = timeFormater.parse(timeConvert);

            DateFormat timeFormaterSecond = new SimpleDateFormat(output); //HH for hour of the day (0 - 23)

            timeFormaterSecond.setTimeZone(TimeZone.getDefault());


            return timeFormaterSecond.format(time);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
*/
    }

   /* public static String newDateTimeConvertServertToLocal(String timeConvert, String input, String output) {


        //  DebugLog.e("Convert Time" + timeConvert);

        DateFormat timeFormater = new SimpleDateFormat(input);

        try {

            Date time;


            timeFormater.setTimeZone(TimeZone.getTimeZone(ExtraConstrant.SERVER_TIMEZONE));


            time = timeFormater.parse(timeConvert);

            DateFormat timeFormaterSecond = new SimpleDateFormat(output); //HH for hour of the day (0 - 23)

            timeFormaterSecond.setTimeZone(TimeZone.getDefault());


            return timeFormaterSecond.format(time);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }*/

    public static String dateTimeConvertLocalToServer(String timeConvert, String input, String output) {

        //COnvert local to local
        DateFormat timeFormater = new SimpleDateFormat(input);

        try {

            Date time;

            timeFormater.setTimeZone(TimeZone.getDefault());

            time = timeFormater.parse(timeConvert);

            DateFormat timeFormaterSecond = new SimpleDateFormat(output);


            return timeFormaterSecond.format(time);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

        ///

       /* DateFormat timeFormater = new SimpleDateFormat(input);

        try {

            Date time;

            timeFormater.setTimeZone(TimeZone.getDefault());

            time = timeFormater.parse(timeConvert);

            DateFormat timeFormaterSecond = new SimpleDateFormat(output);

            timeFormaterSecond.setTimeZone(TimeZone.getTimeZone(ExtraConstrant.SERVER_TIMEZONE));


            return timeFormaterSecond.format(time);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";*/
    }

    public static String datTimeConvertServerToLocalMain(String timeConvert, String input, String output) {

        DateFormat timeFormater = new SimpleDateFormat(input, Locale.US);

        try {

            Date time;


            timeFormater.setTimeZone(TimeZone.getTimeZone(Common.SERVER_TIMEZONE));

            time = timeFormater.parse(timeConvert);

            DateFormat timeFormaterSecond = new SimpleDateFormat(output, Locale.getDefault()); //HH for hour of the day (0 - 23)

            timeFormaterSecond.setTimeZone(TimeZone.getDefault());


            return timeFormaterSecond.format(time);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String datTimeConvertLocalToServerMain(String timeConvert, String input, String output) {

        DateFormat timeFormater = new SimpleDateFormat(input, Locale.US);

        try {

            Date time;

            timeFormater.setTimeZone(TimeZone.getDefault());

            time = timeFormater.parse(timeConvert);

            DateFormat timeFormaterSecond = new SimpleDateFormat(output, Locale.US);

            timeFormaterSecond.setTimeZone(TimeZone.getTimeZone(Common.SERVER_TIMEZONE));


            return timeFormaterSecond.format(time);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static String dateTimeConvertLocalToLocal(String timeConvert, String input, String output) {


        DateFormat timeFormater = new SimpleDateFormat(input);

        try {

            Date time;

            timeFormater.setTimeZone(TimeZone.getDefault());

            time = timeFormater.parse(timeConvert);

            DateFormat timeFormaterSecond = new SimpleDateFormat(output);


            return timeFormaterSecond.format(time);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static String dateTimeConvertLocalToLocalNotification(String timeConvert, String input, String output) {


        DateFormat timeFormater = new SimpleDateFormat(input, Locale.getDefault());

        try {

            Date time;

            timeFormater.setTimeZone(TimeZone.getDefault());

            time = timeFormater.parse(timeConvert);

            DateFormat timeFormaterSecond = new SimpleDateFormat(output, Locale.getDefault());

            return timeFormaterSecond.format(time);


        } catch (ParseException e) {
            e.printStackTrace();
            return timeConvert;
        }

    }

    public static String dateAndTimeGet(String convertDateTime, String convertFormate) {
        String time = "";
        try {
            long now = System.currentTimeMillis();
            String datetime1 = convertDateTime;
            SimpleDateFormat dateFormat = new SimpleDateFormat(convertFormate);
            Date convertedDate = dateFormat.parse(datetime1);

            CharSequence relavetime1 = DateUtils.getRelativeTimeSpanString(
                    convertedDate.getTime(),
                    now,
                    DateUtils.SECOND_IN_MILLIS);
            time = "" + relavetime1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getTodayAndYestrday(String timeConvert, String input, String today, String yestarday) {


        DateFormat timeFormater = new SimpleDateFormat(input);

        try {

            Date time;

            time = timeFormater.parse(timeConvert);
            String message = "";

            if (checkIfToday(time)) {
                message = today;
            } else if (checkIfYesterday(time)) {
                message = yestarday;
            } else {
                message = "";
            }
            return message;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static boolean checkIfYesterday(Date date) {
        Calendar c1 = Calendar.getInstance(); // today
        c1.add(Calendar.DAY_OF_YEAR, -1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date); // your date

        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkIfToday(Date date) {
        Calendar c1 = Calendar.getInstance(); // today

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date); // your date

        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
            return true;
        } else {
            return false;
        }
    }

    public static Date getDate(String timeConvert, String input) {

        DateFormat timeFormater = new SimpleDateFormat(input);
        Date time = null;

        timeFormater.setTimeZone(TimeZone.getDefault());

        try {
            time = timeFormater.parse(timeConvert);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }


    private GregorianCalendar getCalander(String dateS, String timeS) {
        GregorianCalendar calendar = new GregorianCalendar();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormater = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)

        SimpleDateFormat simpleDateFormatY = new SimpleDateFormat("yyyy");

        SimpleDateFormat simpleDateFormatM = new SimpleDateFormat("MM");

        SimpleDateFormat simpleDateFormatD = new SimpleDateFormat("dd");

        SimpleDateFormat timeFormaterH = new SimpleDateFormat("HH"); //HH for hour of the day (0 - 23)

        SimpleDateFormat timeFormaterM = new SimpleDateFormat("mm"); //HH for hour of the day (0 - 23)


        try {
            Date date = simpleDateFormat.parse(dateS);

            Date time = timeFormater.parse(timeS);

            calendar = new GregorianCalendar(Integer.parseInt(simpleDateFormatY.format(date)), (Integer.parseInt(simpleDateFormatM.format(date)) - 1), Integer.parseInt(simpleDateFormatD.format(date)), Integer.parseInt(timeFormaterH.format(time)), Integer.parseInt(timeFormaterM.format(time)));

            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            calendar = null;
            return calendar;
        }

    }


    public static String dateTimeConvertLocalToLocalDay(String timeConvert, String input, String output) {


        DateFormat timeFormater = new SimpleDateFormat(input);

        try {

            Date time;

            timeFormater.setTimeZone(TimeZone.getDefault());

            time = timeFormater.parse(timeConvert);

            DateFormat timeFormaterSecond = new SimpleDateFormat(output, Locale.ENGLISH);


            return timeFormaterSecond.format(time);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

}
