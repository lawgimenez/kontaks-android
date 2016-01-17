package com.lawgimenez.kontaks.models;

/**
 * Created by lawrencegimenez on 1/16/16.
 */
public class Group {

    private long mId;

    private long mGroupId;

    private String mGroupName;

    private String mGroupDescription;

    public void setId(int id) {
        mId = id;
    }

    public void setGroupId(long groupId) {
        mGroupId = groupId;
    }

    public void setGroupName(String groupName) {
        mGroupName = groupName;
    }

    public void setGroupDescription(String groupDescription) {
        mGroupDescription = groupDescription;
    }

    public long getId() {
        return mId;
    }

    public long getGroupId() {
        return mGroupId;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public String getGroupDescription() {
        return mGroupDescription;
    }
}
