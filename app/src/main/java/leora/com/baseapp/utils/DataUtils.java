package leora.com.baseapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import leora.com.baseapp.App;
import leora.com.baseapp.Constants;

/**
 * All the data processing and conversion utils are contained in this class
 */

public class DataUtils {

    /**
     * validates if the string value truly exist
     * @param string
     * @return
     */
    public static Boolean isStringValueExist(String string) {
        Boolean exist = true;

        if (string == null) exist = false;
        else if (string.trim().equals("")) exist = false;
        else if (string.trim().equalsIgnoreCase("null")) exist = false;
        else if (string.trim().equalsIgnoreCase(ValueUtils.NOT_DEFINED)) exist = false;

        return exist;
    }

    /**
     * checks if the string value is "true"
     * @param string
     * @return
     */
    public static Boolean isStringTrue(String string) {
        Boolean is_true = false;

        if (string == null) is_true = false;
        else if (string.trim().equalsIgnoreCase("true")) is_true = true;

        return is_true;
    }


    /**
     * remove duplicate in the arraylist
     * @param values
     * @return
     */
    public static ArrayList<String> removeDuplicate(ArrayList<String> values) {
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(values);
        values.clear();
        values.addAll(hashSet);
        return values;
    }


    /**
     * convert json array to arraylist
     * @param val
     * @return
     */
    public static ArrayList<String> convertJsonArrayToArraylist(String val)
    {
        ArrayList<String> val_arr = new ArrayList<String>();

        if(isStringValueExist(val))
        {
            try {


                JSONArray jsonArray = new JSONArray(val);

                for(int j=0;j<jsonArray.length();j++)
                {
                    val_arr.add(jsonArray.getString(j));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return  val_arr;
    }



    /**
     * gets the distance between two latlong
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }



    public static boolean isNumeric(String str) {
        str = str.trim();
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    public static String getDistanceDisplay(String nearest_hospital_branch_distance)
    {
        String display = "";

        if(DataUtils.isStringValueExist(nearest_hospital_branch_distance))
        {
            int distance_int = Integer.parseInt(nearest_hospital_branch_distance);

            if(distance_int  < 1000)
            {
                display = "Less than a km away";
            }
            else
            {
                display = (distance_int/1000) +" km away";
            }
        }
        return  display;
    }


    /**
     * convers string array to Arraylist
     * @param arr
     * @return
     */
    public static ArrayList<String> convertArrayToArrayList(String[] arr) {
        ArrayList<String> arrayListCustom = new ArrayList<String>();
        ArrayList<String> arr_2 = new ArrayList<String>(Arrays.asList(arr));
        arrayListCustom.addAll(arr_2);
        return arrayListCustom;
    }


    public static HashMap<String, String> convertJsonObjToMap(JSONObject jsonObject) {
        HashMap<String, String> val_set = new HashMap<String, String>();

        if (jsonObject != null) {
            Iterator<String> iter = jsonObject.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                String value = jsonObject.optString(key);
                val_set.put(key, value);
            }
        }
        return val_set;

    }


    public static JSONObject convertMapToJsonObj(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();


        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = entry.getValue();
            String key = entry.getKey();

            try {
                jsonObject.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }


    /**
     * generates random string of specified length
     * @param len
     * @return
     */
    public static String randomString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }




}
