package leora.com.baseapp;

import android.app.Application;
import android.content.Context;

/**
 * This class acts as an application class instance to
 * preserve the app instance and to initialize library
 */
public class App extends Application {
    private static App mInstance;
    private static Context mAppContext;




    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        this.setAppContext(getApplicationContext());
    }

    public static App getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }


    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;
}
