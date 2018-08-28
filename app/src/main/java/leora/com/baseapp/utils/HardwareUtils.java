package leora.com.baseapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.hardware.Camera;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.lang.reflect.Field;

import leora.com.baseapp.App;
import leora.com.baseapp.supportfiles.DeviceValidator;
import leora.com.baseapp.supportfiles.GetFilePath;

import static android.os.Build.VERSION.SDK_INT;

/**
 * All the hardware utils are contained in this class
 */

public class HardwareUtils {


    public static int device_height = 0, device_width = 0;


    /**
     * Get the path of the file form URI
     * @param contentUri
     * @param activity
     * @return
     */
    public static String getRealPathFromUri(Uri contentUri, Activity activity) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Files.FileColumns.DATA};
            cursor = activity.getContentResolver().query(contentUri, proj, null, null, null);
            int coloumn_index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            cursor.moveToFirst();
            String realpath = cursor.getString(coloumn_index);
            // ////  //Log.e("repath1", "" + realpath);
            String extension = realpath.substring(realpath.lastIndexOf(".") + 1, realpath.length());
            if ((extension.equalsIgnoreCase("dcm")) || (extension.equalsIgnoreCase("txt")) || (extension.equals("rtf")) || (extension.equalsIgnoreCase("docx")) || (extension.equalsIgnoreCase("doc")) || (extension.equalsIgnoreCase("bmp")) || (extension.equalsIgnoreCase("pdf")) || (extension.equalsIgnoreCase("png")) || (extension.equalsIgnoreCase("jpeg")) || (extension.equalsIgnoreCase("jpg")))
                return realpath;
            else return "File not supported";
        } catch (Exception e) {
            try {
                String[] proj = {MediaStore.Images.Media.DATA};
                cursor = activity.getContentResolver().query(contentUri, proj, null, null, null);
                int coloumn_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String realpath = cursor.getString(coloumn_index);
                // ////  //Log.e("repath2", "" + realpath);
                String extension = realpath.substring(realpath.lastIndexOf(".") + 1, realpath.length());
                if ((extension.equalsIgnoreCase("dcm")) || (extension.equalsIgnoreCase("txt")) || (extension.equals("rtf")) || (extension.equalsIgnoreCase("docx")) || (extension.equalsIgnoreCase("doc")) || (extension.equalsIgnoreCase("bmp")) || (extension.equalsIgnoreCase("pdf")) || (extension.equalsIgnoreCase("png")) || (extension.equalsIgnoreCase("jpeg")) || (extension.equalsIgnoreCase("jpg")))
                    return realpath;
                else return "File not supported";
            } catch (Exception e1) {
                try {
                    String realpath = GetFilePath.getPath(App.getAppContext(), contentUri);
                    // ////  //Log.e("repath3", "" + realpath);
                    String extension = realpath.substring(realpath.lastIndexOf(".") + 1, realpath.length());
                    if ((extension.equalsIgnoreCase("dcm")) || (extension.equalsIgnoreCase("txt")) || (extension.equals("rtf")) || (extension.equalsIgnoreCase("docx")) || (extension.equalsIgnoreCase("doc")) || (extension.equalsIgnoreCase("bmp")) || (extension.equalsIgnoreCase("pdf")) || (extension.equalsIgnoreCase("png")) || (extension.equalsIgnoreCase("jpeg")) || (extension.equalsIgnoreCase("jpg")))
                        return realpath;
                    else return "File not supported";
                } catch (Exception e2) {
                    try {
                        String realpath = contentUri.getPath();
                        // ////  //Log.e("repath4", "" + realpath);
                        String extension = realpath.substring(realpath.lastIndexOf(".") + 1, realpath.length());
                        if ((extension.equalsIgnoreCase("dcm")) || (extension.equalsIgnoreCase("txt")) || (extension.equals("rtf")) || (extension.equalsIgnoreCase("docx")) || (extension.equalsIgnoreCase("doc")) || (extension.equalsIgnoreCase("bmp")) || (extension.equalsIgnoreCase("pdf")) || (extension.equalsIgnoreCase("png")) || (extension.equalsIgnoreCase("jpeg")) || (extension.equalsIgnoreCase("jpg")))
                            return realpath;
                        else return "File not supported";
                    } catch (Exception e3) {
                        // ////  //Log.e("nores", "getfilepath");
                        return "File not supported";
                    }
                }


            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    /**
     * checks if the GPS is enabled
     * @param context
     * @return
     */
    public static boolean isGPSTurnOn(final Context context) {
        final LocationManager manager = (LocationManager) App.getAppContext().getSystemService(Context.LOCATION_SERVICE);
        Boolean is_gps_enabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //  //Log.e("isGPSTurnOn", "=="+is_gps_enabled);
        return is_gps_enabled;
    }

    /**
     * checks if wifi permission exist
     * @param activity
     * @return
     */
    public static Boolean isWifiPermisssionExist(Activity activity) {

        Boolean permission_exist = false;
        int access_wifi = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_WIFI_STATE);

        if (android.os.Build.VERSION.SDK_INT < 23) {
            permission_exist = true;
        } else if (access_wifi == PackageManager.PERMISSION_GRANTED) {
            permission_exist = true;
        }

        return permission_exist;
    }

    /**
     * checks if GPS permission exist
     * @param activity
     * @return
     */
    public static Boolean isGPSPermisssionExist(Activity activity) {

        Boolean permission_exist = false;
        int access_wifi = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        //        ActivityCompat.requestPermissions(context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (android.os.Build.VERSION.SDK_INT < 23) {
            permission_exist = true;
        } else if (access_wifi == PackageManager.PERMISSION_GRANTED) {
            permission_exist = true;
        }

        return permission_exist;
    }




    /**
     * checks if wifi is enables
     * @param activity
     * @return
     */
    public static boolean isWifiEnabled(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        return false;
    }



    /**
     * initiates phone call to the care team
     * @param context
     */
    public static void proceedCall(Context context, String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + number));
        context.startActivity(callIntent);
    }




    /**
     * returns the device height
     * @return
     */
    public static int getDeviceHeight() {
        if (device_height == 0) {
            WindowManager wm = (WindowManager) App.getAppContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            device_width = size.x;
            device_height = size.y;
        }
        return device_height;
    }

    /**
     * returns device width
     * @return
     */
    public static int getDeviceWidth() {
        if (device_width == 0) {
            WindowManager wm = (WindowManager) App.getAppContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            device_width = size.x;
            device_height = size.y;
        }
        return device_width;
    }



    /**
     * checks if the devide is rooted
     * @return
     */
    public static Boolean isValidDevice() {

        Boolean is_valid_device = !(Build.FINGERPRINT.startsWith("generic") || Build.FINGERPRINT.startsWith("unknown") || "goldfish".equals(Build.HARDWARE) || Build.MODEL.contains("google_sdk") || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk".equals(Build.PRODUCT));

        if (DeviceValidator.isDeviceRooted()) is_valid_device = false;

        // ////  //Log.e("got_pie", is_valid_device + "===");

        return is_valid_device;
    }


    /**
     * get size of the file
     * @param filenew
     * @return
     */
    public static String getFilesizefromFile(File filenew) {
        int filesize_kb = Integer.parseInt(String.valueOf(filenew.length() / 1024));
        String file_size_str = "Not Defined";
        if (filesize_kb > 1024) file_size_str = filesize_kb / 1024 + "MB";
        else file_size_str = filesize_kb + "KB";

        return file_size_str;
    }


    /**
     * checks if front camera exist in device
     * @return
     */
    public static int getFrontCameraId() {
        Camera.CameraInfo ci = new Camera.CameraInfo();
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, ci);
            if (ci.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) return i;
        }
        return -1; // No front-facing camera found
    }

    public static String getAndroidVersionName() {

        StringBuilder builder = new StringBuilder();
        builder.append("android : ").append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == SDK_INT) {
                builder.append(" : ").append(fieldName).append(" : ");
                builder.append("sdk=").append(fieldValue);
            }
        }

        //// ////  //Log.e("fieldName",fieldName);
        String out = builder.toString();
        int endIndex = out.lastIndexOf(":");
        if (endIndex != -1) {
            out = out.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
        }
        endIndex = out.lastIndexOf(":");
        out = out.substring(endIndex, out.length() - 1);
        try {
            out = out.replace(" ", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            out = out.replace(":", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    //for checking internet connection
    // true  -  if connected
    // false - if issue with connection
    public static boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) for (int i = 0; i < info.length; i++)
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }

        }
        return false;
    }

}
