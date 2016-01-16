package com.lawgimenez.kontaks.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.lawgimenez.kontaks.R;
import com.lawgimenez.kontaks.pages.FragmentContactsSync;

/**
 * Created by lawrencegimenez on 1/10/16.
 */
public class HomeActivity extends AppCompatActivity {

    private FrameLayout mContainerHome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();

        FragmentContactsSync fragmentContactsSync = FragmentContactsSync.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.container_home, fragmentContactsSync).commit();
    }

    private void initViews() {
        mContainerHome = (FrameLayout) findViewById(R.id.container_home);
    }
}
