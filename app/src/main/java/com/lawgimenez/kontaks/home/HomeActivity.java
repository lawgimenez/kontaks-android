package com.lawgimenez.kontaks.home;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.lawgimenez.kontaks.KontaksApplication;
import com.lawgimenez.kontaks.R;
import com.lawgimenez.kontaks.models.Contact;
import com.lawgimenez.kontaks.models.Group;
import com.lawgimenez.kontaks.pages.FragmentAddGroup;
import com.lawgimenez.kontaks.pages.FragmentContactsSync;
import com.lawgimenez.kontaks.pages.FragmentEmptyGroup;
import com.lawgimenez.kontaks.pages.FragmentSelectContacts;
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

    private FragmentAddGroup mFragmentAddGroup;
    private FragmentSelectContacts mFragmentSelectContacts;

    private boolean mIsInAddGroupPage = false;

    private boolean mContactsEmpty = false;
    private boolean mGroupEmpty = false;

    private MenuItem mMenuItemSave;
    private MenuItem mMenuItemCancel;

    private String mGroupName;
    private String mGroupDesc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mApplication = KontaksApplication.getInstance();
        mDatabase = mApplication.getDatabase();

        initViews();

        setSupportActionBar(mToolbar);

        Log.i(TAG, "Group count: " + mDatabase.getGroupsCount());
        if (mDatabase.getContactsCount() == 0) {
            mContactsEmpty = true;

            RetrieveContactsTask retrieveContactsTask = new RetrieveContactsTask();
            retrieveContactsTask.execute();

            FragmentContactsSync fragmentContactsSync = FragmentContactsSync.newInstance();
            getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.container_home, fragmentContactsSync).commit();
        } else {
            if (mDatabase.getGroupsCount() == 0) {
                FragmentEmptyGroup fragmentEmptyGroup = FragmentEmptyGroup.newInstance();
                getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.container_home, fragmentEmptyGroup).commit();

                mGroupEmpty = true;
            } else {

            }
        }

//        Log.i(TAG, "Contact count: " + mDatabase.getContactsCount());
//        if (mDatabase.getContactsCount() > 0) {
//            mIsInAddGroupPage = true;
//
//            mFragmentAddGroup = FragmentAddGroup.newInstance();
//
//            getSupportFragmentManager().beginTransaction().add(R.id.container_home, mFragmentAddGroup).commit();
//        } else {
//
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mMenuItemSave = menu.findItem(R.id.action_save);
        mMenuItemCancel = menu.findItem(R.id.action_cancel);

        if (mContactsEmpty || mGroupEmpty) {
            mMenuItemSave.setVisible(false);
            mMenuItemCancel.setVisible(false);
        } else {
            mMenuItemSave.setVisible(true);
            mMenuItemCancel.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            Log.i(TAG, "Save Group");

            if (mIsInAddGroupPage) {
                // It means we are in the add group page and after we should proceed to select
                // contacts page
                mGroupName = mFragmentAddGroup.getGroupName();
                mGroupDesc = mFragmentAddGroup.getGroupDescription();

                mFragmentSelectContacts = FragmentSelectContacts.newInstance();
                mFragmentSelectContacts.setContactsList(mDatabase.getAllContacts());
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
                        .replace(R.id.container_home, mFragmentSelectContacts).commit();
            } else {
                // If you are here, you came from the select contacts page.
                //
                Group group = new Group();
                group.setGroupName(mGroupName);
                group.setGroupDescription(mGroupDesc);

                mDatabase.addGroup(group);
                Log.i(TAG, "Add Group page is not currently displayed.");
            }

            return true;
        } else if (id == R.id.action_cancel) {
            Log.i(TAG, "Action cancel = " + mIsInAddGroupPage);

            if (mIsInAddGroupPage) {
                if (mDatabase.getGroupsCount() == 0) {
                    Log.i(TAG, "Pop stack");
                    getSupportFragmentManager().popBackStack();
                } else {
                    Log.i(TAG, "Whut");
                }
            }
        }

        return super.onOptionsItemSelected(item);
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
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    long photoId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
                    long photoThumbnail = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                    int isFavorited = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.STARRED));

                    String givenName = "";
                    String familyName = "";

                    // Fetch given and family name
                    String whereName = ContactsContract.Data.MIMETYPE + " = ? AND " +
                            ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = " + contactId;
                    String[] whereNameParams = new String[] {
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                    };
                    Cursor cursorName = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null,
                            whereName, whereNameParams, ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
                    if (cursorName != null) {
                        while (cursorName.moveToNext()) {
                            givenName = cursorName.getString(cursorName.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                            familyName = cursorName.getString(cursorName.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));

                            Log.i(TAG, "First name = " + givenName);
                            Log.i(TAG, "Last name = " + familyName);
                        }

                        cursorName.close();
                    }

                    // Retrieve contact's phone numbers if there is one
                    String hasPhoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    if (Integer.parseInt(hasPhoneNum) > 0) {
                        // Cursor for getting phone number
                        Cursor cursorPhoneNum = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[] { contactId }, null);
                        if (cursorPhoneNum != null) {
                            while (cursorPhoneNum.moveToNext()) {
                                String phoneNumber = cursorPhoneNum.getString(cursorPhoneNum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                //Log.i(TAG, "Phone No: " + phoneNumber);
                            }
                            cursorPhoneNum.close();
                        }
                    }

                    Contact contacts = new Contact();
                    contacts.setDeviceId(Long.parseLong(contactId));
                    contacts.setDisplayName(name);
                    contacts.setGivenName(givenName);
                    contacts.setFamilyName(familyName);
                    contacts.setPhotoId(photoId);
                    contacts.setPhotoThumbnailUri(photoThumbnail);
                    contacts.setIsFavorited(isFavorited);

                    mDatabase.addContacts(contacts);

                    //Log.i(TAG, "Name: " + name);

                } // end of while loop
            }
            cursor.close();
        }
    }

    public void createGroup() {
        mIsInAddGroupPage = true;

        mFragmentAddGroup = FragmentAddGroup.newInstance();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_right)
                .addToBackStack(null)
                .replace(R.id.container_home, mFragmentAddGroup).commit();

        mToolbar.setTitle(getString(R.string.add_group));

        mMenuItemCancel.setVisible(true);
        mMenuItemSave.setVisible(true);
        mMenuItemSave.setTitle(getString(R.string.next));
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
            mIsInAddGroupPage = true;

            createGroup();
        }
    }
}
