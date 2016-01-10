package com.lawgimenez.kontaks.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawgimenez.kontaks.R;

/**
 * Created by lawrencegimenez on 1/10/16.
 */
public class IntroPageFragment extends Fragment {

    private static final String KEY_PAGE_DESC = "page_desc";

    public static IntroPageFragment getInstance(String pageDescription) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PAGE_DESC, pageDescription);

        IntroPageFragment fragment = new IntroPageFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);

        String pageDesc = getArguments().getString(KEY_PAGE_DESC);

        View view = inflater.inflate(R.layout.fragment_intro_page, parent, false);

        TextView textViewPageDesc = (TextView) view.findViewById(R.id.textview_page_desc);
        textViewPageDesc.setText(pageDesc);

        return view;
    }
}
