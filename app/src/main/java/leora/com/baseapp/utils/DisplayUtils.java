package leora.com.baseapp.utils;

import android.app.Activity;
import android.widget.Toast;

import leora.com.baseapp.App;

/**
 * This class contains all display utils functions for error message or positive message
 *
 */

public class DisplayUtils {

    /**
     * displays the message passed in to the function in terms of toast
     * @param activity
     * @param message
     */
    public static void showMessage(Activity activity, String message)
    {
        showMessage(activity, message, false);
    }

    public static void showMessage(Activity activity, String message, Boolean time_long)
    {
        Toast.makeText(activity, message, time_long? Toast.LENGTH_LONG: Toast.LENGTH_SHORT).show();
    }

    public static void showMessageError(Activity activity, String message)
    {
        showMessage(activity, message, false);
    }

    public static void showMessageError(Activity activity, String message, Boolean time_long)
    {
        Toast.makeText(activity, message, time_long? Toast.LENGTH_LONG: Toast.LENGTH_SHORT).show();
    }





}
