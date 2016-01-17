package com.lawgimenez.kontaks.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    private Context mContext;

    public ContactsAdapter(Context context, ArrayList<Contact> listContacts) {
        mContext = context;
        mListContacts = listContacts;
    }

    public void setContactsSelectedListener(OnContactsSelectedListener listener) {
        mContactsListener = listener;
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

        if (isIndexSelected(position)) {
            contactsViewHolder.mViewContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        } else {
            contactsViewHolder.mViewContainer.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.white));
        }

        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int randomColor = colorGenerator.getRandomColor();
        String firstLetter = contact.getDisplayName().substring(0, 1);
        TextDrawable textDrawable = TextDrawable.builder().buildRound(firstLetter, randomColor);

        contactsViewHolder.mImageViewContact.setImageDrawable(textDrawable);

        contactsViewHolder.mTextViewContactName.setText(contact.getGivenName());
    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        public View mViewContainer;
        public ImageView mImageViewContact;
        public TextView mTextViewContactName;

        public ContactsViewHolder(View view) {
            super(view);

            mViewContainer = view;
            mImageViewContact = (ImageView) view.findViewById(R.id.imageview_contact);
            mTextViewContactName = (TextView) view.findViewById(R.id.textview_contact_display);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
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
