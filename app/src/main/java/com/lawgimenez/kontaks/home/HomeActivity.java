package com.lawgimenez.kontaks.home;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import com.lawgimenez.kontaks.R;
import com.lawgimenez.kontaks.pages.FragmentContactsSync;

/**
 * Created by lawrencegimenez on 1/10/16.
 */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private FrameLayout mContainerHome;
    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        retrievePhoneContacts();

        initViews();

        setSupportActionBar(mToolbar);

        FragmentContactsSync fragmentContactsSync = FragmentContactsSync.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.container_home, fragmentContactsSync).commit();
    }

    private void initViews() {
        mContainerHome = (FrameLayout) findViewById(R.id.container_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void retrievePhoneContacts() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    String hasPhoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    if (Integer.parseInt(hasPhoneNum) > 0) {
                        // Cursor for getting phone number
                        Cursor cursorPhoneNum = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[] { id }, null);
                        if (cursorPhoneNum != null) {
                            while (cursorPhoneNum.moveToNext()) {
                                String phoneNumber = cursorPhoneNum.getString(cursorPhoneNum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                Log.i(TAG, "Phone No: " + phoneNumber);
                            }
                            cursorPhoneNum.close();
                        }
                    }

                    Log.i(TAG, "Name: " + name);

                } // end of while loop
            }
            cursor.close();
        }
    }
}
