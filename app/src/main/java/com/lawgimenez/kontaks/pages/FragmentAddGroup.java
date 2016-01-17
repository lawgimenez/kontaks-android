package com.lawgimenez.kontaks.pages;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerView;
import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;
import com.lawgimenez.kontaks.R;
import com.lawgimenez.kontaks.adapters.ContactsAdapter;
import com.lawgimenez.kontaks.listeners.OnContactsSelectedListener;
import com.lawgimenez.kontaks.models.Contact;

import java.util.ArrayList;

/**
 * Created by lawrencegimenez on 1/16/16.
 */
public class FragmentAddGroup extends Fragment implements DragSelectRecyclerViewAdapter.SelectionListener, OnContactsSelectedListener {

    private static final String TAG = "FragmentAddGroup";

    private EditText mEditTextAddGroup;
    private EditText mEditTextAddGroupDesc;

    private DragSelectRecyclerView mRecyclerViewList;
    private ContactsAdapter mAdapter;

    private ArrayList<Contact> mListContacts;

    public static FragmentAddGroup newInstance() {
        return new FragmentAddGroup();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_add_group, container, false);

        initViews(view);

        // Initialize data
        mAdapter = new ContactsAdapter(this, mListContacts);
        mAdapter.setSelectionListener(this);
        mRecyclerViewList.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onDragSelectionChanged(int count) {
        Log.i(TAG, "Selected = " + count);
    }

    @Override
    public void onContactsSelected(int index) {
        Log.i(TAG, "Contact selected at index = " + index);

        mAdapter.toggleSelected(index);
    }

    @Override
    public void onContactsSelectedLongPress(int index) {
        mRecyclerViewList.setDragSelectActive(true, index);
    }

    private void initViews(View view) {
        mEditTextAddGroup = (EditText) view.findViewById(R.id.edittext_add_group);
        mEditTextAddGroupDesc = (EditText) view.findViewById(R.id.edittext_add_desc);
        mRecyclerViewList = (DragSelectRecyclerView) view.findViewById(R.id.recycler_contacts);
        mRecyclerViewList.setLayoutManager(new GridLayoutManager(getActivity(), 4));
    }

    public String getGroupName() {
        return mEditTextAddGroup.getText().toString();
    }

    public String getGroupDescription() {
        return mEditTextAddGroupDesc.getText().toString();
    }

    public void setContactsList(ArrayList<Contact> listContacts) {
        mListContacts = listContacts;
    }
}
