package com.vogella.android.myapplication.models;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TagItem implements Serializable {

    @SerializedName("tagName")
    private String tagName;
    @SerializedName("photoURL")
    private String photoURL;

    public TagItem() {
    }

    public TagItem(String tagName, String photoURL) {
        this.tagName = tagName;
        this.photoURL = photoURL;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
