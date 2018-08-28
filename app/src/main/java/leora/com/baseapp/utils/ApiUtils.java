package leora.com.baseapp.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import leora.com.baseapp.App;
import leora.com.baseapp.Constants;
import leora.com.baseapp.supportfiles.FileDownloader;

/**
 * All the basic conversion operations and other utils
 * required for API interactions are contained in this class
 */

public class ApiUtils {


    /**
     * generates json object to hit the API
     * adds the hospital permalink initially to it
     * @return
     */
    public static JSONObject getDefaultParams() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("key", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static HashMap<String, String> getApiRequestDefaultMap() {
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("tempp1", "temp11");
        params.put("tempp12", "temp112");

        String device_identifier = Settings.Secure.getString(App.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (DataUtils.isStringValueExist(device_identifier))
            params.put("device_identifier", device_identifier);

        return params;
    }




    /**
     * downloads the file from the URL
     * @param activity
     * @param uRl
     * @param file_name
     * @return
     */
    public static long fileDownloader(Context activity, String uRl, String file_name) {

        DownloadManager mgr = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE).setDestinationInExternalPublicDir("/" + ValueUtils.ROOT_FOLDER_PREFIX, file_name)
            //                .setVisibleInDownloadsUi(false)
            //                .setNotificationVisibility(DownloadManager.Request
            // .VISIBILITY_HIDDEN)
            .setAllowedOverRoaming(false).setTitle(ValueUtils.ROOT_FOLDER_PREFIX + " - " + "Uploads").setDescription("Consultation Uploads");
        //                .setDestinationInExternalPublicDir("/dhaval_files", "test.jpg");

        return mgr.enqueue(request);
    }


    /**
     * adding files to download list
     */
    public static void downloadingReportFiles(Activity activity, String file_url, String file_name){
        FileDownloader startFileDownloader = new FileDownloader(activity, file_name, file_url);
        startFileDownloader.downloadFile();
    }



}
