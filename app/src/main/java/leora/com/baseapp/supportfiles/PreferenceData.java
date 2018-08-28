package leora.com.baseapp.supportfiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;

import leora.com.baseapp.App;
import leora.com.baseapp.Constants;
import leora.com.baseapp.utils.DataUtils;
import leora.com.baseapp.utils.ValueUtils;

/**
 * Custom Sharedpreference handler class
 * all the methods found are
 * getter and setter for the targetted shared preference variables
 * All the key names used in the shared preference are stored in this class itself as static variables
 */
public class PreferenceData {

    public static final int PREFERENCE_INT_NOVALUE = -5;


    public static final String USER_LOCATION_ADDRESS = "USER_LOCATION_ADDRESS";
    public static final String USER_LOCATION_ADDRESSLINE1 = "USER_LOCATION_ADDRESSLINE1";
    public static final String USER_LOCATION_ADDRESSLINE2 = "USER_LOCATION_ADDRESSLINE2";
    public static final String USER_LOCATION_CITY = "USER_LOCATION_CITY";
    public static final String USER_LOCATION_STATE = "USER_LOCATION_STATE";
    public static final String USER_LOCATION_PINCODE = "USER_LOCATION_PINCODE";
    public static final String USER_LOCATION_LAT = "USER_LOCATION_LAT";
    public static final String USER_LOCATION_LONG = "USER_LOCATION_LONG";

    public static void preferencePutData(String key, Object value) {

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = App.getAppContext().getSharedPreferences(App.getAppContext().getPackageName(), Context.MODE_PRIVATE);
        editor = settings.edit();

        switch ((value != null) ? value.getClass().getSimpleName() : "String") {

            case "String":
                editor.putString(key, DataUtils.isStringValueExist((String) value) ?((String) value) : null );
                break;

            case "Integer":
                editor.putInt(key, (int) value);
                break;

            case "Boolean":
                editor.putBoolean(key, (Boolean) value);
                break;
        }

        editor.apply();
    }

    public static String preferencegetDataString(String key) {
        String string_out = App.getAppContext().getSharedPreferences(App.getAppContext().getPackageName(), Context.MODE_PRIVATE).getString(key, null);
        return DataUtils.isStringValueExist(string_out) ? string_out : null ;
    }

    public static int preferencegetDataInt(String key) {
        return App.getAppContext().getSharedPreferences(App.getAppContext().getPackageName(), Context.MODE_PRIVATE).getInt(key, PREFERENCE_INT_NOVALUE);
    }

    public static Boolean preferencegetDataBoolean(String key) {
        return App.getAppContext().getSharedPreferences(App.getAppContext().getPackageName(), Context.MODE_PRIVATE).getBoolean(key, false);
    }


}
