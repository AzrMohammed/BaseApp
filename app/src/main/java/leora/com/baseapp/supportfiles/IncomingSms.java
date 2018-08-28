package leora.com.baseapp.supportfiles;

/**
 * This class acts as an listener for incoming messages
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IncomingSms extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
          Log.e("camee", "smss");

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String address = currentMessage.getDisplayOriginatingAddress();
                    String body = currentMessage.getDisplayMessageBody();

                    Pattern mPattern = Pattern.compile(".\\d{6}.");

                    Matcher matcher = mPattern.matcher(body);
                      Log.e("came", "11");
//                    if (matcher.find()) {
//                        String otp = body.substring(matcher.start() + 1, matcher.start() + 7);   if ((address.contains("CLINIC") || address.contains("CLDNIN")) && body.contains("OTP") && body.contains("registration")) {
//
//                        RegisterOtp registerOtp = RegisterOtp.fragment;
//                        LoginOtp loginOtp = LoginOtp.fragment;
//
//                        if (registerOtp != null) {
//                            Log.e("called_from", "reccreg");
//                            registerOtp.receivedSms(otp);
//                            //                                receivedSms(context, otp);
//                        }
//
//                        if (loginOtp != null) {
//                            Log.e("called_from", "recclog");
//                            loginOtp.receivedSms(otp);
//                            //                                receivedSms(context, otp);
//                        }
//
//                    }
//                    }
                }
            }

        } catch (Exception e) {
            Log.e("exxx5", e + "==");
            //            Toast.makeText(context, "err1 " + e, Toast.LENGTH_LONG).show();
        }
    }

}

