package com.lawgimenez.kontaks.pages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lawgimenez.kontaks.R;

/**
 * Created by lawrencegimenez on 1/17/16.
 */
public class FragmentEmptyGroup extends Fragment implements View.OnClickListener {

    private Button mButtonCreatGroup;

    public static FragmentEmptyGroup newInstance() {
        return new FragmentEmptyGroup();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_empty_group, container, false);

        mButtonCreatGroup = (Button) view.findViewById(R.id.button_create_group);
        mButtonCreatGroup.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
