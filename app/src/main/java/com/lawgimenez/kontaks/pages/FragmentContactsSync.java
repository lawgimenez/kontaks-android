package com.lawgimenez.kontaks.pages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lawgimenez.kontaks.R;

/**
 * Created by lawrencegimenez on 1/16/16.
 */
public class FragmentContactsSync extends Fragment {

    public static FragmentContactsSync newInstance() {
        return new FragmentContactsSync();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_contact_sync, container, false);
    }
}
