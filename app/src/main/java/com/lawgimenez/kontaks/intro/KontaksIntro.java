package com.lawgimenez.kontaks.intro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;
import com.lawgimenez.kontaks.KontaksApplication;
import com.lawgimenez.kontaks.R;
import com.lawgimenez.kontaks.home.HomeActivity;

/**
 * Created by lawrencegimenez on 1/10/16.
 */
public class KontaksIntro extends AppIntro {

    private static final String TAG = "KontaksIntro";

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(IntroPageFragment.getInstance("Page 1"));
        addSlide(IntroPageFragment.getInstance("Page 2"));
        addSlide(IntroPageFragment.getInstance("Page 3"));
//        addSlide(SyncContactsFragment.getInstance());

        setTheme(R.style.AppTheme_NoActionBar);

        showSkipButton(false);
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
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();

        KontaksApplication application = KontaksApplication.getInstance();
        application.setGettingStarted(true);
    }

    @Override
    public void onSlideChanged() {
        Log.d(TAG, "onSlideChanged()");
    }
}
