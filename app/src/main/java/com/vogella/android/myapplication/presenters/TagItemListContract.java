package com.vogella.android.myapplication.presenters;

import android.content.Context;

import com.vogella.android.myapplication.models.TagItem;

import java.util.ArrayList;

public interface TagItemListContract {

    interface Presenter {
        void getTagsList(int pageNumber);
    }

    interface TagsView {
        void showLoading(boolean showLoading);
        void updateList(ArrayList<TagItem> items);
        Context getContext();
    }
}
