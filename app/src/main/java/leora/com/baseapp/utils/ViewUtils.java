package leora.com.baseapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

import leora.com.baseapp.App;
import leora.com.baseapp.customclass.CustomAppCompatActivity;
import leora.com.baseapp.R;

/**
 * All the utils related to view are contained in this class
 */

public class ViewUtils {

    public static final int LENGTH_DEFAULT_TEXT = 1530;
    public static final int LENGTH_TITLE_TEXT = 128;
    public static final int LENGTH_DESCRIPTION_TEXT = 512;

    final public static float alpha_active = (float) 1.0;
    final public static float alpha_inactive = (float) 0.5;
    final public static float alpha_inactive_2 = (float) 0.7;
    final public static ArrayList<String> month_list = new ArrayList<String>(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));

    final public static String edittext_type_integer = "integer";
    final public static String edittext_type_float = "float";
    final public static String edittext_type_height = "height";
    final public static String edittext_type_weight = "weight";

    final public static String edittext_type_dob = "edittext_type_dob";
    final public static String edittext_type_alphabetonly = "edittext_type_alphabetonly";
    final public static String edittext_type_alphanumeric = "edittext_type_alphanumeric";

    final public static float alpha_full = 1f;
    final public static int EDIT_TEXT_TYPE_NORMAL = 1;
    final public static int EDIT_TEXT_TYPE_CAPS_ALL = 2;
    final public static int EDIT_TEXT_TYPE_CAPS_WORDS = 3;
    final public static int EDIT_TEXT_TYPE_CAPS_SENTS = 4;
    final public static int EDIT_TEXT_TYPE_NUMERIC_INT = 5;
    final public static int EDIT_TEXT_TYPE_NUMERIC_DEC = 6;
    final public static int EDIT_TEXT_TYPE_EMAIL = 7;

    final public static int EDIT_TEXT_LIMIT_NAME = 40;
    final public static int EDIT_TEXT_LIMIT_TITLE = 120;
    final public static int EDIT_TEXT_LIMIT_PHONE = 12;
    final public static int EDIT_TEXT_LIMIT_EMAIL = 80;
    final public static int EDIT_TEXT_LIMIT_COUNT = 5;
    final public static int EDIT_TEXT_LIMIT_OTP = 6;
    final public static int EDIT_TEXT_LIMIT_SHORT_DESCRIPTION = 540;
    final public static int EDIT_TEXT_LIMIT_LONG_DESCRIPTION = 1100;

    //Lato Regular and bold typeface
    public static Typeface font_regular, font_bold;

    
    
    
    /**
     * getst the thumbnail bitmap of the file
     * @param f
     * @param WIDTH
     * @param HIGHT
     * @return
     */
    public static Bitmap decodeFile(File f, int WIDTH, int HIGHT) {
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //The new size we want to scale to
            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            //Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }


    /**
     * load the file from the input path in drawable format
     * @param context
     * @param path_relative
     * @return
     */
    public static Drawable getDrawable(Context context, String path_relative) {
        InputStream ims = null;
        try {
            ims = context.getAssets().open(path_relative);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Drawable.createFromStream(ims, null);
    }


    public static Object setViewToModel(Activity activity, View view, Object model) {
        Class<?> classx = model.getClass();
        //        Object model = null;
        try {
            //            model = classx.newInstance();

            for (Field field : classx.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    String field_name = field.getName();

                    try {
                        // ////  //Log.e("---", field.getType() + "==st4== " + field_name);
                        //                        String type = "" + field.getType();

                        {
                            View each_view = view.findViewById(activity.getResources().getIdentifier(field_name, "id", activity.getPackageName()));

                            Object val = field_name;
                            if (val != null) field.set(model, each_view);

                        }
                    } catch (Exception e) {
                        // ////  //Log.e("essset1", field.getType() + "e==" + field_name + e + "==");
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            // ////  //Log.e("instaexx", "e==" + e);
            e.printStackTrace();
        }

        return model;

    }

    /**
     * brings up the keyboard
     * @param view
     * @param context
     */
    public static void showKeyboard(final View view, final Context context) {
        view.post(new Runnable() {
            @Override
            public void run() {
                view.requestFocus();
                InputMethodManager imgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);

            }
        });


    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public static ProgressDialog showProgressBar(ProgressDialog progressBar, Activity activity) {
        return showProgressBar(progressBar, activity, "Loading...", true);
    }

    public static ProgressDialog showProgressBar(ProgressDialog progressBar, Activity activity, Boolean cancellable) {
        return showProgressBar(progressBar, activity, "Loading...", cancellable);
    }

    public static ProgressDialog showProgressBar(ProgressDialog progressBar, Activity activity, String txt) {
        return showProgressBar(progressBar, activity, txt, true);
    }

    public static ProgressDialog showProgressBar(ProgressDialog progressBar, Activity activity, String txt, Boolean cancellable) {
        if (progressBar == null) {
            progressBar = new ProgressDialog(activity);
        }
        progressBar.setMessage(txt);
        if (!progressBar.isShowing()) progressBar.show();
        progressBar.setCancelable(cancellable);
        return progressBar;
    }

    public static void hideProgressBar(ProgressDialog progressBar) {
        //        if (progressBar != null) {
        //            if (progressBar.isShowing()) {
        //
        //
        try {
            progressBar.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            progressBar.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            progressBar.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressBar = null;
        //        }
        //        else
        //        {
        //            // ////  //Log.e("null", "sss_ppp");
        //        }
    }


    /**
     * generat a dynamice edit text based on input value
     * @param et
     * @param hint
     * @param text
     * @param limit
     * @param max_lines
     * @param et_type
     * @return
     */
    public static EditText setEditText(EditText et, String hint, String text, int limit, int max_lines, int et_type) {

        switch (et_type) {

            case EDIT_TEXT_TYPE_NORMAL:
                et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case EDIT_TEXT_TYPE_CAPS_ALL:
                break;
            case EDIT_TEXT_TYPE_CAPS_WORDS:
                break;
            case EDIT_TEXT_TYPE_CAPS_SENTS:
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                break;
            case EDIT_TEXT_TYPE_NUMERIC_INT:
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case EDIT_TEXT_TYPE_EMAIL:
                et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case EDIT_TEXT_TYPE_NUMERIC_DEC:
                break;


        }

        et.setLines(max_lines);

        if (limit != ValueUtils.NO_VALUE_INT)
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(limit)});

        if (DataUtils.isStringValueExist(hint)) et.setHint(hint);
        if (DataUtils.isStringValueExist(text)) et.setText(text);

        String val = et.getText().toString();
        if (DataUtils.isStringValueExist(val)) et.setSelection(et.length());

        return et;
    }

    /**
     * sets max length for edittext
     * @param et
     * @param max_length
     * @return
     */
    public static EditText setEtMaxLength(EditText et, int max_length) {
        // ////  //Log.e("refdg", "avcvmaxl" + max_length + "===");
        if (max_length != ValueUtils.NO_VALUE_INT)
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max_length)});
        return et;
    }


    /**
     * gets the resourece ID using its name
     * if resource name doesn't exist return ID as  "-1"
     * @param context
     * @param resource_name
     * @return
     */
    public static int getResourceByName(Context context, String resource_name) {

        String final_name = resource_name.toLowerCase().equals("bangalore") ? "bengaluru" : resource_name.replace(" ", "_").toLowerCase();

        return context.getResources().getIdentifier(final_name, "drawable", context.getPackageName());
    }


    public static void setAnimForActivityOncreate(Activity activity) {
        activity.overridePendingTransition(R.anim.activityentering_oncreate, R.anim.activityleaveing_oncreate);
    }

    public static void setAnimForActivityOnfinish(Activity activity) {
        activity.overridePendingTransition(R.anim.activityentering_onfinish, R.anim.activityleaving_onfinish);
    }

    //    for setting list view height dynamically
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /**
     * converts dp to px
     * @param dps
     * @return
     */
    public static int convertdpToPx(int dps) {
        final float scale = App.getAppContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }

}
