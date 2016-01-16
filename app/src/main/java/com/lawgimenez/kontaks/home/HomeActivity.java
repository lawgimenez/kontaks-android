package com.lawgimenez.kontaks.home;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import com.lawgimenez.kontaks.KontaksApplication;
import com.lawgimenez.kontaks.R;
import com.lawgimenez.kontaks.models.Contact;
import com.lawgimenez.kontaks.pages.FragmentAddGroup;
import com.lawgimenez.kontaks.pages.FragmentContactsSync;
import com.lawgimenez.kontaks.utils.KontaksDatabaseHelper;

/**
 * Created by lawrencegimenez on 1/10/16.
 */
public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private FrameLayout mContainerHome;
    private Toolbar mToolbar;

    private KontaksApplication mApplication;
    private KontaksDatabaseHelper mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mApplication = KontaksApplication.getInstance();
        mDatabase = mApplication.getDatabase();

        RetrieveContactsTask retrieveContactsTask = new RetrieveContactsTask();
        retrieveContactsTask.execute();

        initViews();

        setSupportActionBar(mToolbar);

        Log.i(TAG, "Contact count: " + mDatabase.getContactsCount());
        if(mDatabase.getContactsCount() > 0) {
            FragmentAddGroup fragmentAddGroup = FragmentAddGroup.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.container_home, fragmentAddGroup).commit();
        } else {
            FragmentContactsSync fragmentContactsSync = FragmentContactsSync.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.container_home, fragmentContactsSync).commit();
        }
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

                    Contact contacts = new Contact();
                    contacts.setDeviceId(Long.parseLong(id));
                    contacts.setDisplayName(name);

                    mDatabase.addContacts(contacts);

                    Log.i(TAG, "Name: " + name);

                } // end of while loop
            }
            cursor.close();
        }
    }

    private class RetrieveContactsTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {

            retrievePhoneContacts();

            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            Log.i(TAG, "Done retrieving contacts list");

            FragmentAddGroup fragmentAddGroup = FragmentAddGroup.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
                    .replace(R.id.container_home, fragmentAddGroup).commit();

            mToolbar.setTitle(getString(R.string.add_group));
        }
    }
}
