package leora.com.baseapp.supportfiles;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import leora.com.baseapp.Constants;
import leora.com.baseapp.network.CustomJsonObjectRequest;
import leora.com.baseapp.network.CustomResponseListener;
import leora.com.baseapp.utils.ApiUtils;
import leora.com.baseapp.utils.DataUtils;
import leora.com.baseapp.utils.ValueUtils;

/**
 * Not used
 */

public class CommonNetwork {

    public static void reportError(final HashMap<String, String> report_error_map) {

        String url = Constants.URL_REPORT_ERROR;
        JSONObject request_obj = DataUtils.convertMapToJsonObj(report_error_map);

        new CustomJsonObjectRequest(null, Request.Method.POST, url, request_obj, new CustomResponseListener() {
            @Override
            public void responseSuccess(JSONObject response) {
            }

            @Override
            public void responseFailure(JSONObject response) {
            }

            @Override
            public void responseError(String message) {
            }
        });

    }




}
