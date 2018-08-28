package leora.com.baseapp.supportfiles;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import leora.com.baseapp.App;
import leora.com.baseapp.Constants;
import leora.com.baseapp.R;
import leora.com.baseapp.activity.ScreenHome;
import leora.com.baseapp.model.DbSQLiteHelper;
import leora.com.baseapp.utils.DataUtils;
import leora.com.baseapp.utils.ValueUtils;

/**
 * This class contains all the generic common methods
 */
public class CommonMethods {



    /**
     * sets the flow values for the activity's flow arraylist
     * @param activity
     * @param key
     * @param value
     */
    public static void setFlowValues(Activity activity, String key, String value) {

        if(activity != null){
        if(!(activity.isFinishing())){
            String activity_name = activity.getClass().getSimpleName();

            //        // ////  //Log.e("activity_name recc", "===" + activity_name + "==" + key + "==" + value);

            if (activity_name.equals(ScreenHome.class.getSimpleName())) {
                ((ScreenHome) activity).putFlowValues(key, value);
            }  else {
                   Log.e("val_avv", "put_folow_val_act_not_found");
            }
        }
        }

    }

    /**
     * gets the flow values from the activity's flow arraylist
     * @param activity
     * @param key
     * @return
     */
    public static String getFlowValues(Activity activity, String key) {
        String activity_name = activity.getClass().getSimpleName();

        String value = "";

        //        // ////  //Log.e("activity_name recc_get", "===" + activity_name + "==" + key + "==");

        if (activity_name.equals(ScreenHome.class.getSimpleName())) {
            value = ((ScreenHome) activity).getFlowValues(key);
        } else {
            // ////  //Log.e("val_avava_get", "get_folow_val_act_not_found");
        }

        return value;

    }

    /**
     * validates if user is logged in
     * @return
     */
    public static Boolean isUserLoggedIn(int user_status) {
        Boolean user_logged_in = false;

//        int user_status = PreferenceData.preferencegetDataInt(PreferenceData.user_status);

        switch (user_status) {
            case PreferenceData.PREFERENCE_INT_NOVALUE:
                user_logged_in = false;
                break;

            case ValueUtils.user_status_not_logged_in:
                user_logged_in = false;
                break;

            case ValueUtils.user_status_logged_in_not_verified:
                user_logged_in = true;
                break;

            case ValueUtils.user_status_logged_in_verified:
                user_logged_in = true;
                break;

        }
        return user_logged_in;
    }

    /**
     * clears all the notification in the notification panel
     */
    public static void clearAllNotifications() {

        try {
            NotificationManager nMgr = (NotificationManager) App.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * clears all the data in
     * 1) Database
     * 2) SharedPreferences
     * 3) App Notification
     * and logs out
     *
     * @param context1
     */
    public static void clearAll(Context context1) {

        Context context = App.getAppContext();

        context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit().clear().commit();

        DbSQLiteHelper model = getDbModel(context);
        model.dropTable();
        clearAllNotifications();

    }

    public static String getPassword() {
        if (Constants.password == null) Constants.password = getKey();
        return Constants.password;
    }

    /**
     * encryts the data
     * data stored in the local storage are encrypted using this method
     *
     * @param plainText
     * @return
     */
    public static String encrypt(String plainText) {

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec key = new SecretKeySpec(getPassword().getBytes("UTF-8"), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec("AAAAAAAAAAAAAAAA".getBytes("UTF-8")));
            plainText = Base64.encodeToString(cipher.doFinal(plainText.getBytes("UTF-8")), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plainText;
    }

    /**
     * decrypts the encrypted string
     *
     * @param is_serverprefix_req
     * @param cipherText_str
     * @return
     */
    public static String decrypt(Boolean is_serverprefix_req, String cipherText_str) {
        String url = cipherText_str;
        try {
            byte[] cipherText = Base64.decode(cipherText_str, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec key = new SecretKeySpec(getPassword().getBytes("UTF-8"), "AES");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec("AAAAAAAAAAAAAAAA".getBytes("UTF-8")));
            url = new String(cipher.doFinal(cipherText), "UTF-8");
            if (is_serverprefix_req) url = Constants.SERVER + url;
        } catch (Exception e) {
            //  //Log.e("decry_fun", e + "");
            e.printStackTrace();
        }
        return url;
    }

    /**
     * encryption support key
     *
     * @return
     */
    public static String getKey() {
        String final_hash_value = "";
        int max_loop_val = 16;
        Boolean is_running = true;
        String hashKey = (App.getAppContext() != null) ? "OkQD5QlPEjGRlOoRK0Rqe6MHvwsoRg6StPFuQaEWgjHAMvXU1RXLxIl2EBebHnBnca9V1NiLESHkb4oy9xqHIn5vNZiALBNTF5ykQwCvw07EX1HXrquZOssPqiGA4Q4DIxia4NtbeRuF7ppv1tXwWM3waGehVRDMMD9Vcrylq8yViXYj0xbgIBP5Pu6z3knAPGsVCNZBxyw9JJqgBLx8wAAKSubvGiFp4bLlWVa2fqUVOl05FuUmXeZ9QywPjz3OuvNCzh2Ymyo4v9zkIi1AwTIrckb4axJOfwLYFoItfmCDx7zveo86kSkgtG79AiK3J6TjYZe1uvcDR8aOXiaqLgZZbliGRyNRVZvVZYRv2aiwlvm7zp9aivb23xQWZfMFnYZbxoyq2OWMcWMIfJm8fmGGfmgCnV0zu58WrMPgK8AI9hZ7cCff3H9izbT3k1BVSzck8QeS048RKAQmTDemfJW1X1pq8h5FDiOyLApwEovEsmACCgwp1tKy3jmSkepoSIvPhu9T8GS9YRszTXaFaR0WI6OAPo5CN1IcMRSNciQ7y2Ew4NUgFgi6vlex7nIDeJ7ebznEnNmUQ1ZZ2SFUTaQVXJA6UChqtYC9UTu4fh2NG4wBgGW3ZAx6JVQ4zMNS4ux00GjxKWlubIg2RL3WEhSUptCt3R47KGx5unjlqMEODmrVxnC4GoQS9zpb4xmVHa6fWPZw9Vij9BRSaqiYJgFMXmw2jRUmuaXIIC70tSwruWXCFjUxTn2hDiQJMfVwDZtjtAsmc6iCT4UEF28MEZXZTqtwjn1JJZkiEBEfzfRtNuYgLPxZlcaLik9g1oUhamQniSeclmsHtZMaYSWl9GNVC04q6MZ6V3FYqujH2mKQw76I9GSTglcOaO6bxGBtrNVVVtLJk9AjZ6PjF1ool9QAD3BWURuOG1izkuvGu3yqnENWyrs5M9DwgngDf3hARbrGI414Atoachh72grcqwq5ANh9J0HnKncBzFKJ" : "oRi9LZ3geTjgvbhsww6ssU2v1ZZRaozs8lfRaKLJ4xkJhi1nJbjYJ4xELqbTWC5XBxIpUafjCkrhqBZ1jrIupObKMG4aO8TO7eKjWJTUFWHWcz3KLYGyy4hy91tjmbCHfXC3ly1tjJcOE3FfEJSgGeaEm88mJctF7yjKIf4HzYZni00Bgl6LKBQVwIBlY9n55Y8IGPwtOEoxX7tXhFAbA4Wa8DCwz7p3oBiFr4wJjHEvahiwnI1qZamh2MAtnsQK70FkAvT5SyqDgKE8ciiLFhPD0AsLaWIp4BKUJ2mP566Cn6P1FzY1UHbw8PlfrguG6pzsnaNUYHZAsx0a1zRX9g1gGNI4uxONr1W8lINpSMxQ7A7is9qEQOQXGK7woKlribfIJqmDvXPA62GDM4pNkiYHyrxZbzNNObrUr34eKLEzt3IBwKIWq6wlCEzQ7Ju72CVkEMt0Pix9yBjqxyCQ7unyHBj4YqjfxF4mCcDNttr5OxRaep9z0Ig978EyBrc6ikGP00pTUsPcEqrlhKMTAfJXhGBLm7re7zKo38R2368rw06ASZQJzUcGT9NPKxEDwOSLmNeEyZEjgDn9WcsSy95U57QUQfZ4KRnjz5FsVpRmyk21QXVuwDSqfQH0DHizekC1AraopQ7UBTIPQWxEbJVySepsixHaVYpkAFqrwq3xqAPhsga8ofrpHu0WcBZ7t179ESohEMxU3j2DwQxqqNl3qVHwZmRtgFkamlQ6aqyoSaPvgBuxFxwAw6KaaDRy3B8L3eTUsUa8EYmzEUJFLtaOQC0htDcHenFKFNJpULhjJmnMa4s2AriwgPSibKWKA2ikVnnjW9MaZnFNIB34A1DabpHaZixwaU0N8PYyx5ntJpwWWaYg9sBRVDh2tcxGPDcOwP8LQHy1N5Gm3rX7EjYZNepmtlK3HV1q0Raw3lp2Re7JSNQUHNEAgZRFwXN5owVxoppRsvfWKnutRfIa8cHPTZYbGuuK89KP7fVs";
        for (int i = 0; i < max_loop_val; i++) {
            String temp_val = ((((((hashKey.length() - (hashKey.indexOf(hashKey.indexOf(((char) (65 + i)))))) > 10 && (hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))) != -1)) ? (hashKey.substring((hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))), (hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))) + 10)) : (((hashKey.length() - (hashKey.indexOf(((char) (65 + i))))) > 10 && (hashKey.indexOf(((char) (65 + i)))) != -1)) ? (hashKey.substring((hashKey.indexOf(((char) (65 + i)))), (hashKey.indexOf(((char) (65 + i)))) + 10)) : hashKey.substring(12, 23)) != null) ? ((is_running = (is_running) ? false : true) ? (((((hashKey.length() - (hashKey.indexOf(hashKey.indexOf(((char) (65 + i)))))) > 10 && (hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))) != -1)) ? (hashKey.substring((hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))), (hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))) + 10)) : (((hashKey.length() - (hashKey.indexOf(((char) (65 + i))))) > 10 && (hashKey.indexOf(((char) (65 + i)))) != -1)) ? (hashKey.substring((hashKey.indexOf(((char) (65 + i)))), (hashKey.indexOf(((char) (65 + i)))) + 10)) : hashKey.substring(12, 23)).charAt(i % 10) + "") : (((((hashKey.length() - (hashKey.indexOf(hashKey.indexOf(((char) (65 + i)))))) > 10 && (hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))) != -1)) ? (hashKey.substring((hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))), (hashKey.indexOf(hashKey.indexOf(((char) (65 + i))))) + 10)) : (((hashKey.length() - (hashKey.indexOf(((char) (65 + i))))) > 10 && (hashKey.indexOf(((char) (65 + i)))) != -1)) ? (hashKey.substring((hashKey.indexOf(((char) (65 + i)))), (hashKey.indexOf(((char) (65 + i)))) + 10)) : hashKey.substring(12, 23)).charAt((i + 5) % 10) + "")) : null);
            if (temp_val != null) final_hash_value += temp_val;
            else max_loop_val++;
        }
        //  //Log.e("final_hash_value", final_hash_value);

        return final_hash_value;
    }

    /**
     * returns db model
     *
     * @param context
     * @return
     */
    public static DbSQLiteHelper getDbModel(Context context) {
        if (Constants.dbModel == null) {
            //  //Log.e("cameenull", "yess");
            Constants.dbModel = new DbSQLiteHelper(context);
            Constants.dbModel.getWritableDatabase();
        }
        return Constants.dbModel;
    }

    /**
     * returns the APP's base path to store in app related files
     *
     * @param slash_req
     * @return
     */
    public static String getBasePath(Boolean slash_req) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/" + App.getAppContext().getResources().getString(R.string.app_name));
        if (!myDir.exists()) myDir.mkdir();

        return myDir.toString() + (slash_req ? "/" : "");
    }

    public static HashMap<String, String> getErrorReportMap() {
        HashMap<String, String> params_error = new HashMap<String, String>();


        String device_identifier = Settings.Secure.getString(App.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (DataUtils.isStringValueExist(device_identifier))
            params_error.put("device_identifier", device_identifier);

        return params_error;
    }




}
