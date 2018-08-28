package leora.com.baseapp.supportfiles;

import android.app.Activity;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;

import leora.com.baseapp.utils.DisplayUtils;


/**
 * Not used
 */
public class FileDownloader {

    Activity act;
    String fileActualName;
    String fileUrl;
    public FileDownloader(Activity _act, String _fileActualName, String _fileUrl) {
        act = _act;
        fileActualName = _fileActualName;
        fileUrl = _fileUrl;
    }

    public void downloadFile(){
        String serviceString = act.DOWNLOAD_SERVICE;
        DownloadManager downloadmanager;
        downloadmanager = (DownloadManager) act.getSystemService(serviceString);
        // //  //Log.e("fileUrl", fileUrl);
        Uri uri = Uri.parse(fileUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);
        //request.setTitle("Download Sample");
        request.setTitle(fileActualName);
        //request.setDestinationInExternalFilesDir(getActivity(), null, fileUrl);
        request.setDestinationInExternalFilesDir(act, Environment.DIRECTORY_DOWNLOADS, fileUrl);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);
        Long reference = downloadmanager.enqueue(request);

        DisplayUtils.showMessage(act, fileActualName + " is downloading. Please check your notification.");
    }
}
