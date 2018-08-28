package leora.com.baseapp.network;

import org.json.JSONObject;

/**
 * Created by AZR on 12-03-2018.
 */

public interface CustomResponseListener {

    public void responseSuccess(JSONObject response);

    public void responseFailure(JSONObject response);

    public void responseError(String message);

}
