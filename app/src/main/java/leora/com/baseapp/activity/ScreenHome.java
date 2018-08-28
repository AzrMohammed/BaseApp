package leora.com.baseapp.activity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Map;

import leora.com.baseapp.customclass.CustomAppCompatActivity;
import leora.com.baseapp.R;
import leora.com.baseapp.model.apimodel.ApiResponseSampleModel;
import leora.com.baseapp.network.CustomJsonObjectRequest;
import leora.com.baseapp.network.CustomResponseListener;
import leora.com.baseapp.utils.ApiUtils;
import leora.com.baseapp.utils.DataUtils;

public class ScreenHome extends CustomAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_home);
    }

    /**
     * sample api request
     */
    public void proceedSampleRequest()
    {
        final String url = "SampleUrl";

        Map<String, String> params = ApiUtils.getApiRequestDefaultMap();
        params.put("param_key1", "param_val1");

        JSONObject request_obj = DataUtils.convertMapToJsonObj(params);


        new CustomJsonObjectRequest(ScreenHome.this, true, Request.Method.POST, url, request_obj, new CustomResponseListener() {
            @Override
            public void responseSuccess(JSONObject response) {
                Log.e("cameonss", "===" + response);
                try {

                    /**
                     * sample processing of response data
                     * assigning values to the locally created model for API response
                     * once assigned we can consume it
                     */
//                    JSONObject responseObj = new JSONObject(response);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    ApiResponseSampleModel exampleApiModel = gson.fromJson(response.toString(), ApiResponseSampleModel.class);

                    Log.e("rec name from api", "=="+exampleApiModel.name+"");

                } catch (Exception e) {
                    Log.e("parse_recclogg", "===" + e);
                    e.printStackTrace();
                }
            }

            @Override
            public void responseFailure(JSONObject response) {

            }

            @Override
            public void responseError(String message) {
                Log.e("cameonerr", "===" + message);
            }
        });
    }


}
