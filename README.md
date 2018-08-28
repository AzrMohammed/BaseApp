# BaseApp

Hi this app acts as an framework to ease your work of development at all the basic level operations like API interations, DB interations, view/data conversion utils, validator utils, calendar utils, hardware utils, view utils 

CalendarUtils:
All the convesion and processing operations required for calendar/date instances can be found here 
eg : getSimpleDateFor(), getAge(), getDisplayDate(), etc ..,


DataUtils:
All the data processing operations can be found here
eg : isStringValueExist(), isStringTrue(), removeDuplicate(), convertJsonArrayToArraylist(), distance(), etc..,

DbUtils:
Basic db utils can be found here 
eg : createSlug(), getContentValuesKeys()

DisplayUtils:
Toast and user alert operations can be found here
eg : showMessage(), showMessageError()

HardwareUtils:
All the hardware utils can be found here
eg :  getDeviceHeight(), isValidDevice(), getRealPathFromUri(), isWifiPermisssionExist(), proceedCall(), etc.., 

NetworkUtils: 
Network validator utils can be found here
eg : isNetworkAvailable(), isConnected()

ValueUtils:
All the basic global constant values can be defined here

ViewUtils:
All the utils related to the screen view can be found here
eg : getDrawable(), setViewToModel(), showKeyboard(), setEtMaxLength(), setListViewHeightBasedOnChildren(), eg..,

FieldValidator:
All the basic validation criterias can be found here with the appropriate response messages.
eg : validateMobile(), validateEmail(), validatePassword(), validatePinCode(), etc..,


CommonMethods:
All the generic common methods can be found here

PreferenceData:
All the shared preferences put and get methods and the key variables can be found here and compressed as two methods
1) preferencePutData()
2) preferencegetDataString() and other relevant data types



API interation :

No more sync task, no more handling loders in multiple place.
Initiate the request wait for the callback.
This module is made adaptable in a way that it can be integrated with any network library you prefer like Volley, retrofit etc ... by default it is built with volley.

//define request url, request param, set weather to show progress or not, initiate the request, wait for the call back.

        final String url = "SampleUrl";
        Map<String, String> params = ApiUtils.getApiRequestDefaultMap();
        params.put("param_key1", "param_val1");

        JSONObject request_obj = DataUtils.convertMapToJsonObj(params);


        new CustomJsonObjectRequest(ScreenHome.this, true, Request.Method.POST, url, request_obj, new CustomResponseListener() {
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

Parsing response data :

// create the class that replicates the response the params and assign to it


                    /**
                     * sample processing of response data
                     * assigning values to the locally created model for API response
                     */
                    JSONObject responseObj = new JSONObject(response);
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    ApiResponseSampleModel exampleApiModel = gson.fromJson(response.toString(), ApiResponseSampleModel.class);




