package leora.com.baseapp.utils;

import android.Manifest;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * All the calendar conversion and display utils are contained in this class
 */

public class CalendarUtils {

    //    2016-08-31T17:46:04
    final public static String tfs_yeardatetime_server = "yyyy-MM-dd'T'HH:mm:ss";

    final public static String tfs_yeardatetime_1 = "dd MMM yyyy' at 'hh:mm a";
    final public static String tfs_yeardatetime_2 = "dd MMM',' hh:mm a";
    final public static String tfs_yeardatetime_3 = "dd MMM', 'yyyy";
    final public static String tfs_yeardate_1 = "yyyy-MM-dd";
    final public static String tfs_yeardate_2 = "dd-MM-yyyy";
    final public static String tfs_yeardate_3 = "dd MMM yyyy";
    final public static String date_format_display_1 = "EEEE, d MMMM";
    final public static String date_format_display_2 = "EEE, d MMMM";
    final public static String tfs_hours_24 = "HH:mm:ss";
    final public static String tfs_hours_12 = "hh:mm a";
    final public static String[] permission_location = {Manifest.permission.ACCESS_FINE_LOCATION};

    public static int current_date = Integer.parseInt(getcurrentInstance(1));
    public static int current_month = Integer.parseInt(getcurrentInstance(2));
    public static int current_year = Integer.parseInt(getcurrentInstance(3));

    /**
     * get calendar obj for input string of specified format
     *
     * @param val
     * @param format
     * @return
     */
    public static Calendar getCalender(String val, String format) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = getSimpleDateFormat(format);

        try {

            Date date = dateFormat.parse(val);
            if (date != null) {
                long millis = date.getTime();
                calendar.setTimeInMillis(millis);
            }
        } catch (Exception e) {

            Log.e("getCalender", "ex1"+"==="+val+"==="+format+"==="+ e);
            e.printStackTrace();
        }

        return calendar;
    }

    /**
     * get calendar obj for input date
     *
     * @param date
     * @return
     */
    public static Calendar getCalender(Date date) {

        Calendar calendar = Calendar.getInstance();

        if (date != null) {
            long millis = date.getTime();
            calendar.setTimeInMillis(millis);
        }

        return calendar;
    }

    /**
     * get calendar obj for input long
     *
     * @param val
     * @return
     */
    public static Calendar getCalender(Long val) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(val);

        return calendar;
    }

    /**
     * get string value of specified format for input calendar obj
     *
     * @param calendar
     * @param format
     * @return
     */
    public static String getTimeString(Calendar calendar, String format) {
        String val = "";

        SimpleDateFormat dateFormat = getSimpleDateFormat(format);
        Date date = calendar.getTime();

        try {
            val = dateFormat.format(date);
        } catch (Exception e) {
            Log.e("getTimeString", "ex1" + e+"=="+format);

            e.printStackTrace();
        }

        return val;
    }

    /**
     * get string value of specific attribute for current time
     *
     * @param type
     * @return
     */
    public static String getcurrentInstance(int type) {
        return getcurrentInstance(Calendar.getInstance(), type);
    }

    /**
     * get string value of specific attribute for calender instance
     *
     * @param calendar
     * @param type
     * @return
     */
    public static String getcurrentInstance(Calendar calendar, int type) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(calendar.getTimeInMillis());

        c.add(Calendar.DATE, -1);
        SimpleDateFormat df = CalendarUtils.getSimpleDateFormat("yyyy-MM-dd");
        switch (type) {
            case 1:
                df = CalendarUtils.getSimpleDateFormat("dd");
                break;
            case 2:
                df = CalendarUtils.getSimpleDateFormat("MM");
                break;
            case 3:
                df = CalendarUtils.getSimpleDateFormat("yyyy");
                break;

        }
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static SimpleDateFormat getSimpleDateFormat(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        return simpleDateFormat;
    }

    /**
     * returns the age of the input date
     *
     * @param dob
     * @return
     */
    public static String getAge(Calendar dob) {
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    /**
     * validates weather the DOB given in milliseconds format is of adult or not
     *
     * @param dob_millis
     * @return
     */
    public static Boolean isAdult(long dob_millis) {
        Calendar calendar_dob = Calendar.getInstance();
        calendar_dob.setTimeInMillis(dob_millis);

        Calendar calendar_min_adult = Calendar.getInstance();
        calendar_min_adult.set(Calendar.MINUTE, 0);
        calendar_min_adult.set(Calendar.SECOND, 0);
        calendar_min_adult.set(Calendar.HOUR, 0);
        calendar_min_adult.add(Calendar.YEAR, -18);

        // ////  //Log.e("recc_compcal", "=="+calendar_dob.getTimeInMillis()+"=="+calendar_min_adult.getTimeInMillis());

        return (calendar_dob.getTimeInMillis() < calendar_min_adult.getTimeInMillis());
    }

    /**
     * Convert time format
     *
     * @param millis
     * @return
     */
    public static String getDisplayDate(Long millis) {
        if (System.currentTimeMillis() - millis < 3600000) {
            //            long seconds=(millis/1000)/60;
            long minutes = ((System.currentTimeMillis() - millis) / 1000) / 60;
            if (minutes == 1) {
                return minutes + " minute ago";
            } else {
                return minutes + " minutes ago";
            }
        } else if (System.currentTimeMillis() - millis < 86400000) {
            long i = ((((System.currentTimeMillis() - millis) / (60 * 60 * 1000))) % 24);
            if (i == 1) {
                return "1 hour ago";
            } else {
                return i + " hours ago";
            }

        } else if (System.currentTimeMillis() - millis > 86400000 && System.currentTimeMillis() < 172800000) {
            return "1 day ago";
        } else if (System.currentTimeMillis() - millis > 172800000 && System.currentTimeMillis() - millis < 129600000) {
            return "2 days ago";
        } else {
            return getTimeString(getCalender(millis), CalendarUtils.date_format_display_1);
        }
    }

    /**
     * returns the difference between two dates
     *
     * @param val
     * @return
     */
    public static String getDifferenceDateShort(Long val) {

        Long date_current = Calendar.getInstance().getTimeInMillis();
        Long years_difference = date_current - val;

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(years_difference);
        int mYear = c.get(Calendar.YEAR) - 1970;
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        String display_age = "";
        if (mYear != 0) display_age = mYear + " year" + ((mYear > 1) ? "s" : "");
        else if (mMonth != 0 && !DataUtils.isStringValueExist(display_age))
            display_age = mMonth + " month" + ((mMonth > 1) ? "s" : "");
        else if (!DataUtils.isStringValueExist(display_age))
            display_age = mDay + " day" + ((mDay == 1) ? "" : "s");

        return display_age;
    }

    /**
     * returns the difference between given date and today in years
     *
     * @param val
     * @return
     */
    public static int getDifferenceDateOnlyYear(Long val) {

        Long date_current = Calendar.getInstance().getTimeInMillis();
        Long years_difference = date_current - val;

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(years_difference);
        return c.get(Calendar.YEAR) - 1970;

    }


}
