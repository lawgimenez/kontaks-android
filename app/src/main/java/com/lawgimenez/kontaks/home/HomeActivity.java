package com.lawgimenez.kontaks.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.lawgimenez.kontaks.R;
import com.lawgimenez.kontaks.pages.FragmentContactsSync;

/**
 * Created by lawrencegimenez on 1/10/16.
 */
public class HomeActivity extends AppCompatActivity {

    private FrameLayout mContainerHome;
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();

        setSupportActionBar(mToolbar);

        FragmentContactsSync fragmentContactsSync = FragmentContactsSync.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.container_home, fragmentContactsSync).commit();
    }

    private void initViews() {
        mContainerHome = (FrameLayout) findViewById(R.id.container_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
