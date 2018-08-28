package leora.com.baseapp.utils;

import android.Manifest;

import java.util.ArrayList;
import java.util.regex.Pattern;

import leora.com.baseapp.App;
import leora.com.baseapp.Constants;
import leora.com.baseapp.R;

/**
 * This class contains all the constant values that can be used across the app
 */

public class ValueUtils {

    final public static String NO_CHANGE = "NO_CHANGE", NONE = "NONE", DEFAULT = "DEFAULT";


    final public static String RESPONSE_NO_DISPLAY = "NO_DISPLAY";



    final public static String ROOT_FOLDER_PREFIX = App.getAppContext().getResources().getString(R.string.app_name);

    final public static int VALUE_NO_CHANGE = -5;

    final public static int color_selected = App.getAppContext().getResources().getColor(R.color.tab_color_selected);
    final public static int color_deselected = App.getAppContext().getResources().getColor(R.color.tab_color_deselected);

    final public static int color_selected_bg = App.getAppContext().getResources().getColor(R.color.white);
    final public static int color_deselected_bg = App.getAppContext().getResources().getColor(R.color.border_circle_imageview_color);

    public static final String APP_ID = App.getAppContext().getPackageName();
    public static final String APP_USER_TYPE = "Patient";

    final public static String ELEMENT_TAG_KEY = "ELEMENT_TAG_KEY";
    final public static String NOT_DEFINED = "NOT_DEFINED";
    final public static String NO_VALUE_STR = "NOT_VALUE";
    final public static int NO_VALUE_INT = -5;

    final public static String SERVER_ERROR = "OOPS! Something went wrong.";
    final public static int readTimeOut = 20000;
    final public static int connectionTimeOut = 20000;
    final public static String UNKNOWN_ERROR = "500 found. Oops Something went wrong. Please try " + "" + "again later.";
    final public static String NETWORK_ERROR_MSG = "Sorry, no internet connectivity detected. " + "Please reconnect and try again later.";
    final public static String COOKIE = "Cookie";

    final public static String ACTION_TYPE = "ACTION_TYPE";

    final public static int user_status_not_logged_in = 0;
    final public static int user_status_logged_in_not_verified = 1;
    final public static int user_status_logged_in_verified = 2;


    public static final int NOCHANGE_INT = -5;
    public static final String NOCHANGE_STRING = String.valueOf(NOCHANGE_INT);
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String[] permission_req_camera = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    public static final String[] permission_req_gallery = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    final public static String DRIVING_LICENSE = "Driving License";
    final public static String PASSPORT = "Passport";
    final public static String VOTER_ID = "Voters ID";
    final public static String AADHAR_CARD = "Aadhaar Card";
    final public static String VISA = "Visa";
    final public static String CHECK_alphanumeric = "^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$";
    final public static String numRegex = ".*[0-9].*";
    final public static String alphaRegex = ".*[A-Z].*";
    final public static String country_list[] = {"Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Costa Rica", "Côte d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic People's Republic of Korea (North Korea)", "Democratic Republic of the Cong", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Lao People's Democratic Republic (Laos)", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia (Federated States of)", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Republic of Korea (South Korea)", "Republic of Moldova", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Tajikistan", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom of Great Britain and Northern Ireland", "United Republic of Tanzania", "United States of America", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"};
    final public static ArrayList<String> gender_list = new ArrayList<String>() {{
        add("Male");
        add("Female");
        add("Other");

    }};
    final public static ArrayList<String> id_proof_list = new ArrayList<String>() {{
        add("Driving Licence");
        add("Voter ID");
        add("PAN Card");
        add("Aadhar Card");
        add("Passport");

    }};

}
