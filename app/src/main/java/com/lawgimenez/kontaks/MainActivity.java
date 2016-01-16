package com.lawgimenez.kontaks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lawgimenez.kontaks.home.HomeActivity;
import com.lawgimenez.kontaks.intro.KontaksIntro;

public class MainActivity extends AppCompatActivity {

    private KontaksApplication mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApplication = KontaksApplication.getInstance();

        Intent intent;
        if (!mApplication.isGettingStarted()) {
            // It means user has just installed the app, so we will still display
            // the tutorial/intro page.
            intent = new Intent(this, KontaksIntro.class);
        } else {
            intent = new Intent(this, HomeActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
