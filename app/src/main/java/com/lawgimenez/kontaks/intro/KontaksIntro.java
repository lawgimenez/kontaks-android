package com.lawgimenez.kontaks.intro;

import android.os.Bundle;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by lawrencegimenez on 1/10/16.
 */
public class KontaksIntro extends AppIntro {

    private static final String TAG = "KontaksIntro";

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(IntroPageFragment.getInstance("Page 1"));
        addSlide(IntroPageFragment.getInstance("Page 2"));
        addSlide(SyncContactsFragment.getInstance());
    }

    @Override
    public void onSkipPressed() {
        Log.d(TAG, "onSkipPressed()");
    }

    @Override
    public void onNextPressed() {
        Log.d(TAG, "onNextPressed()");
    }

    @Override
    public void onDonePressed() {
        Log.d(TAG, "onDonePressed()");
    }

    @Override
    public void onSlideChanged() {
        Log.d(TAG, "onSlideChanged()");
    }
}
