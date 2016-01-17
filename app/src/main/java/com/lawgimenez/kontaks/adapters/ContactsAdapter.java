package com.lawgimenez.kontaks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.dragselectrecyclerview.DragSelectRecyclerViewAdapter;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.lawgimenez.kontaks.R;
import com.lawgimenez.kontaks.listeners.OnContactsSelectedListener;
import com.lawgimenez.kontaks.models.Contact;

import java.util.ArrayList;

/**
 * Created by lawrencegimenez on 1/17/16.
 */
public class ContactsAdapter extends DragSelectRecyclerViewAdapter<ContactsAdapter.ContactsViewHolder> {

    private ArrayList<Contact> mListContacts;

    private OnContactsSelectedListener mContactsListener;

    public ContactsAdapter(OnContactsSelectedListener contactsListener, ArrayList<Contact> listContacts) {
        mContactsListener = contactsListener;
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

        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int randomColor = colorGenerator.getRandomColor();
        String firstLetter = contact.getDisplayName().substring(0, 1);
        TextDrawable textDrawable = TextDrawable.builder().buildRound(firstLetter, randomColor);

        contactsViewHolder.mImageViewContact.setImageDrawable(textDrawable);
    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        public ImageView mImageViewContact;

        public ContactsViewHolder(View view) {
            super(view);

            mImageViewContact = (ImageView) view.findViewById(R.id.imageview_contact);
            mImageViewContact.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mContactsListener.onContactsSelected(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            mContactsListener.onContactsSelectedLongPress(getAdapterPosition());

            return true;
        }
    }
}
