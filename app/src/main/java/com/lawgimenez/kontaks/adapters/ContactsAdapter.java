package com.lawgimenez.kontaks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;
import com.lawgimenez.kontaks.R;
import com.lawgimenez.kontaks.models.Contact;

import java.util.ArrayList;

/**
 * Created by lawrencegimenez on 1/17/16.
 */
public class ContactsAdapter extends DragSelectRecyclerViewAdapter<ContactsAdapter.ContactsViewHolder> {

    private ArrayList<Contact> mListContacts;

    public ContactsAdapter(ArrayList<Contact> listContacts) {
        mListContacts = listContacts;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup container, int viewType) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_contact, container, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder contactsViewHolder, int position) {
        super.onBindViewHolder(contactsViewHolder, position);

        Contact contact = mListContacts.get(position);

        contactsViewHolder.mTextViewContact.setText(contact.getDisplayName());
    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewContact;

        public ContactsViewHolder(View view) {
            super(view);

            mTextViewContact = (TextView) view.findViewById(R.id.textview_contact);
        }
    }
}
