package com.lawgimenez.kontaks.models;

import java.util.ArrayList;

/**
 * Created by lawrencegimenez on 1/10/16.
 */
public class Contact {

    private static final int FACEBOOK = 1;
    private static final int TWITTER = 2;
    private static final int GOOGLE = 3;

    private long mDeviceId;

    private String mDisplayName;

    private int mHasPhoneNumber;

    private ArrayList<String> mListPhoneNumbers;

    private long mPhotoId;

    private long mPhotoUri;

    private long mPhotoThumbnailUri;

    private int mTimesContacted;

    private long mLastTimeContacted;

    private int mFavorited;

    private ArrayList<Integer> mListAccounts;

    public void setDeviceId(long deviceId) {
        mDeviceId = deviceId;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public void setHasPhoneNumber(int hasPhoneNumber) {
        mHasPhoneNumber = hasPhoneNumber;
    }

    public void setPhoneNumbers(ArrayList<String> listPhoneNumbers) {
        mListPhoneNumbers = listPhoneNumbers;
    }

    public void setPhotoId(long photoId) {
        mPhotoId = photoId;
    }

    public void setPhotoUri(long photoUri) {
        mPhotoUri = photoUri;
    }

    public void setPhotoThumbnailUri(long photoThumbnailUri) {
        mPhotoThumbnailUri = photoThumbnailUri;
    }

    public void setTimesContacted(int timesContacted) {
        mTimesContacted = timesContacted;
    }

    public void setLastTimeContacted(long lastTimeContacted) {
        mLastTimeContacted = lastTimeContacted;
    }

    public void setIsFavorited(int favorited) {
        mFavorited = favorited;
    }

    public void setAccounts(ArrayList<Integer> listAccounts) {
        mListAccounts = listAccounts;
    }

    /**
     * Getters
     */

    public long getDeviceId() {
        return mDeviceId;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public int getHasPhoneNumber() {
        return mHasPhoneNumber;
    }

    public ArrayList<String> getPhoneNumbers() {
        return mListPhoneNumbers;
    }

    public long getPhotoId() {
        return mPhotoId;
    }

    public long getPhotoUri() {
        return mPhotoUri;
    }

    public long getPhotoThumbnailUri() {
        return mPhotoThumbnailUri;
    }

    public int getTimesContacted() {
        return mTimesContacted;
    }

    public long getLastTimeContacted() {
        return mLastTimeContacted;
    }

    public int getIsFavorited() {
        return mFavorited;
    }

    public ArrayList<Integer> getAccounts() {
        return mListAccounts;
    }
}
