package com.lawgimenez.kontaks.pages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lawgimenez.kontaks.R;

/**
 * Created by lawrencegimenez on 1/16/16.
 */
public class FragmentAddGroup extends Fragment {

    private EditText mEditTextAddGroup;
    private EditText mEditTextAddGroupDesc;

    public static FragmentAddGroup newInstance() {
        return new FragmentAddGroup();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_add_group, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        mEditTextAddGroup = (EditText) view.findViewById(R.id.edittext_add_group);
        mEditTextAddGroupDesc = (EditText) view.findViewById(R.id.edittext_add_desc);
    }
}
