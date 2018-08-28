package leora.com.baseapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * All the network utils are contained in this class
 */
public class NetworkUtils {

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Method checks if there is an internet connection for the device
     * @param context
     * @return
     */
    public static boolean isConnected(final Context context) {
        try {
            final ConnectivityManager connMngr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            final NetworkInfo wifiNetwork = connMngr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetwork != null && wifiNetwork.isConnectedOrConnecting()) { return true; }

            final NetworkInfo mobileNetwork = connMngr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNetwork != null && mobileNetwork.isConnectedOrConnecting()) { return true; }

            final NetworkInfo activeNetwork = connMngr.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) { return true; }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
