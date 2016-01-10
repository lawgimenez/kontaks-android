package com.lawgimenez.kontaks;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by lawrencegimenez on 1/10/16.
 */
public class KontaksApplication extends Application {

    private static final String PREF_SETTINGS = "com.lawgimenez.pref_settings";

    private static final String KEY_GETTING_STARTED = "getting_started";

    private static KontaksApplication mApplication;

    private SharedPreferences mSharedSettings;

    /**
     * True if user has tapped on "Getting Started" in intro/tutorial page.
     */
    private boolean mGettingStarted = false;

    public static KontaksApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;

        mSharedSettings = getSharedPreferences(PREF_SETTINGS, MODE_PRIVATE);
    }

    public SharedPreferences getSettings() {
        return mSharedSettings;
    }

    public boolean isGettingStarted() {
        return mSharedSettings.getBoolean(KEY_GETTING_STARTED, false);
    }
}
