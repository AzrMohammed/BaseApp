package leora.com.baseapp.supportfiles;

import android.app.Activity;

import java.util.Calendar;

import leora.com.baseapp.utils.CalendarUtils;
import leora.com.baseapp.utils.DataUtils;
import leora.com.baseapp.utils.DisplayUtils;
import leora.com.baseapp.utils.ValueUtils;

/**
 * Custom class to validate the values of that type
 */

public class FieldValidator {


    public static Boolean validateMobile(Activity activity, String val) {
        Boolean valid = true;
        String notify = "";

        if (!DataUtils.isStringValueExist(val)) {
            valid = false;
            notify = "Mobile number is not valid";
        } else if (val.length() < 10) {
            valid = false;
            notify = "Mobile number should minimum of 10 characters.";
        }

        if (DataUtils.isStringValueExist(notify) && !valid)
            DisplayUtils.showMessage(activity, notify);

        //  //Log.e("cvalimob", "=====" + val + "===" + valid + "==");

        return valid;
    }

    public static Boolean validateEmail(Activity activity, String val) {
        Boolean valid = true;
        String notify = "";

        String key = "Email address";

        if (!DataUtils.isStringValueExist(val)) {
            valid = false;
            notify = key + " is not valid";
        } else if (val.length() < 3) {
            valid = false;
            notify = key + " is not valid";
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            valid = false;
            notify = key + " is not valid";
        }

        if (DataUtils.isStringValueExist(notify) && !valid)
            DisplayUtils.showMessage(activity, notify);

        return valid;
    }


    public static Boolean validatePassword(Activity activity, String val) {
        Boolean valid = true;
        String notify = "";

        String key = "Password";

        if (!DataUtils.isStringValueExist(val)) {
            valid = false;
            notify = key + " is not valid";
        } else if (val.length() < 4) {
            valid = false;
            notify = key + " is not valid";
        }

        if (DataUtils.isStringValueExist(notify) && !valid)
            DisplayUtils.showMessage(activity, notify);

        return valid;
    }

    public static Boolean validatePinCode(Activity activity, String val, String key) {
        Boolean valid = true;
        String notify = "";


        if (!DataUtils.isStringValueExist(val)) {
            valid = false;
            notify = key + " is not valid";
        } else if (val.length() < 6) {
            valid = false;
            notify = key + " is not valid";
        }

        if (DataUtils.isStringValueExist(notify) && !valid)
            DisplayUtils.showMessage(activity, notify);

        return valid;
    }

    public static Boolean validateAadhaarNumber(Activity activity, String val, String key) {
        Boolean valid = true;
        String notify = "";

        //  //Log.e("validateAadhaarNumber: ", val.matches(Constants.numRegex) + "====");
        if (!DataUtils.isStringValueExist(val)) {
            valid = false;
            notify = key + " is not valid";
        } else if (val.length() < 12) {
            valid = false;
            notify = key + " is not valid";
        } else if (!val.matches("[0-9]+")) {
            valid = false;
            notify = key + " is not valid";
        }

        if (DataUtils.isStringValueExist(notify) && !valid)
            DisplayUtils.showMessage(activity, notify);

        return valid;
    }

    public static Boolean validateVoterID(Activity activity, String val, String key) {
        Boolean valid = true;
        String notify = "";


        if (!DataUtils.isStringValueExist(val)) {
            valid = false;
            notify = key + " is not valid";
        } else if (val.length() < 10) {
            valid = false;
            notify = key + " is not valid";
        }

        if (DataUtils.isStringValueExist(notify) && !valid)
            DisplayUtils.showMessage(activity, notify);

        return valid;
    }

    public static Boolean validateIDProof(Activity activity, String val, String key) {
        Boolean valid = true;

        switch (key) {
            case ValueUtils.AADHAR_CARD:
                valid = validateAadhaarNumber(activity, val, key);
                break;
            case ValueUtils.VOTER_ID:
                valid = validateVoterID(activity, val, key);
                break;
            case ValueUtils.DRIVING_LICENSE:
                valid = validateVoterID(activity, val, key);
                break;
            case ValueUtils.PASSPORT:
                valid = validateVoterID(activity, val, key);
                break;

        }


        return valid;
    }


    public static Boolean validateOtp(Activity activity, String val) {
        Boolean valid = true;
        String notify = "";

        String key = "OTP";

        if (!DataUtils.isStringValueExist(val)) {
            valid = false;
            notify = key + " is not valid";
        } else if (val.length() < 5) {

            valid = false;
            notify = key + " is not valid";
        }

        if (DataUtils.isStringValueExist(notify) && !valid)
            DisplayUtils.showMessage(activity, notify);

        return valid;
    }


    public static Boolean validateVisaExpiryDate(Activity activity, String yy_val, String mm_val, String dd_val, String hint) {


        Boolean valid = true;
        String notify = "";


        if (!DataUtils.isStringValueExist(yy_val) || !DataUtils.isStringValueExist(mm_val) || !DataUtils.isStringValueExist(dd_val)) {
            valid = false;
            notify = hint + " is not valid";

        } else if (Integer.parseInt(mm_val) > 12) {
            notify = " Month is not valid";
            valid = false;

        } else if (Integer.parseInt(dd_val) > 31) {
            notify = " Date is not valid";
            valid = false;
        } else if (Integer.parseInt(yy_val) < Integer.parseInt(CalendarUtils.getTimeString(Calendar.getInstance(), "yyyy"))) {
            notify = hint + " year is not valid";
            valid = false;

        }

        if (DataUtils.isStringValueExist(notify) && !valid)
            DisplayUtils.showMessage(activity, notify);

        return valid;

    }


    public static Boolean validateDOB(Activity activity, String yy_val, String mm_val, String dd_val, String hint) {

        Boolean valid = true;
        String notify = "";


        if (!DataUtils.isStringValueExist(yy_val) || !DataUtils.isStringValueExist(mm_val) || !DataUtils.isStringValueExist(dd_val)) {
            valid = false;
            notify = hint + " is not valid";

        } else if (Integer.parseInt(mm_val) > 12) {
            notify = hint + " Month is not valid";
            valid = false;

        } else if (Integer.parseInt(dd_val) > 31) {
            notify = hint + " Date is not valid";
            valid = false;
        } else if (Integer.parseInt(yy_val) < 1930) {
            notify = hint + " Year is not valid";
            valid = false;

        } else if (Integer.parseInt(yy_val) > Integer.parseInt(CalendarUtils.getTimeString(Calendar.getInstance(), "yyyy"))) {
            notify = hint + " Year is not valid";
            valid = false;

        }

        if (DataUtils.isStringValueExist(notify) && !valid)
            DisplayUtils.showMessage(activity, notify);

        return valid;

    }

    public static Boolean validateName(Activity activity, String val, String key) {

        Boolean valid = true;
        String notify = "";

        if (!DataUtils.isStringValueExist(val)) {
            valid = false;
            notify = key + " is not valid";
        } else if (val.length() < 4) {
            valid = false;
            notify = key + " is not valid";
        }

        if (DataUtils.isStringValueExist(notify) && !valid)
            DisplayUtils.showMessage(activity, notify);

        //        String regx = "^[\\p{L} .'-]+$";
        //        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        //        Matcher matcher = pattern.matcher(txt);
        //        return matcher.find();

        return valid;
    }


//    Generic methods

    /**
     * validates if string value exist
     * @param activity
     * @param val
     * @param key
     * @return
     */
    public static boolean validateIsStringExist(Activity activity, String val, String key) {

        boolean valid = true;
        String notify = "";

        if (!DataUtils.isStringValueExist(val)) {
            valid = false;
            notify = key + " is not valid";
        }

        if (!valid)
            DisplayUtils.showMessage(activity, notify);
        return valid;


    }

}
