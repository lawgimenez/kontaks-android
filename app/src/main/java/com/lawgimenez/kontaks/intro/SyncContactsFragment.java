package com.lawgimenez.kontaks.intro;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by lawrencegimenez on 1/10/16.
 */
public class SyncContactsFragment extends Fragment {

    private static final String TAG = "SyncContactsFragment";

    public static SyncContactsFragment getInstance() {
        return new SyncContactsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //retrievePhoneContacts();
    }

    private void retrievePhoneContacts() {
        ContentResolver contentResolver = getActivity().getContentResolver();
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
