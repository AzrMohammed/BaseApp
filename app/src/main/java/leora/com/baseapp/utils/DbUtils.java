package leora.com.baseapp.utils;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * All the db interaction utils are contained in this class
 */

public class DbUtils {



    /**
     * generates 24 digit random string to use as an local slug
     * @return
     */
    public static String createSlug() {


        String slug = DataUtils.randomString(9)+ System.currentTimeMillis();

        if(slug.length()>24)
            slug = slug.substring(0,24);

        return slug;

    }


    /**
     * get all the key names present in the content provider
     * @param vals
     * @param arrayList
     * @return
     */
    public static ArrayList<String> getContentValuesKeys(ContentValues vals, ArrayList<String> arrayList) {
        Set<Map.Entry<String, Object>> s = vals.valueSet();
        Iterator itr = s.iterator();

        while (itr.hasNext()) {
            Map.Entry me = (Map.Entry) itr.next();
            String key = me.getKey().toString();
            if (!key.equals("sync_complete") && !key.equals("last_sync_time")) arrayList.add(key);
        }

        ArrayList<String> arrayList1 = DataUtils.removeDuplicate(arrayList);
        // ////  //Log.e("final_filer_siz", arrayList1.size() + "===" + arrayList.size() + "===" + vals.size());
        return arrayList1;
    }


}
