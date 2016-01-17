package com.lawgimenez.kontaks.pages;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerView;
import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;
import com.lawgimenez.kontaks.R;
import com.lawgimenez.kontaks.adapters.ContactsAdapter;
import com.lawgimenez.kontaks.listeners.OnContactsSelectedListener;
import com.lawgimenez.kontaks.models.Contact;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by lawrencegimenez on 1/17/16.
 */
public class FragmentSelectContacts extends Fragment implements DragSelectRecyclerViewAdapter.SelectionListener, OnContactsSelectedListener {

    private static final String TAG = "FragmentSelectContacts";

    private DragSelectRecyclerView mRecyclerViewList;
    private ContactsAdapter mAdapter;

    private ArrayList<Contact> mListContacts;

    public static FragmentSelectContacts newInstance() {
        return new FragmentSelectContacts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_select_contacts, container, false);

        // Initialize data
        mRecyclerViewList = (DragSelectRecyclerView) view.findViewById(R.id.recycler_contacts);
        mRecyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).color(Color.GRAY).build());
//        mRecyclerViewList.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        mAdapter = new ContactsAdapter(this, mListContacts);
        mAdapter.setSelectionListener(this);
        mRecyclerViewList.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onDragSelectionChanged(int count) {
        Log.i(TAG, "Selected count = " + count);
    }

    @Override
    public void onContactsSelected(int index) {
//        Log.i(TAG, "Contact selected at index = " + index);

        Log.i(TAG, "Selected: " + mAdapter.getSelectedIndices().toString());

        mAdapter.toggleSelected(index);
    }

    @Override
    public void onContactsSelectedLongPress(int index) {
        mRecyclerViewList.setDragSelectActive(true, index);
    }

    public void setContactsList(ArrayList<Contact> listContacts) {
        mListContacts = listContacts;
    }
}
