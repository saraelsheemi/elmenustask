package com.vogella.android.myapplication.presenters;

import android.util.Log;

import com.vogella.android.myapplication.models.TagItem;
import com.vogella.android.myapplication.models.TagItemDetailsResponse;
import com.vogella.android.myapplication.models.TagListResponse;
import com.vogella.android.myapplication.network.ApiService;
import com.vogella.android.myapplication.network.ServiceGenerator;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TagItemListPresenter implements TagItemListContract.Presenter {
    private static final String TAG = "TagItemListPresenter";

    private TagItemListContract.TagsView tagsView;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiService apiService;
    private ArrayList<TagItem> tagItems;

    public TagItemListPresenter(TagItemListContract.TagsView tagsView) {
        this.tagsView = tagsView;
        apiService = ServiceGenerator.getClient(tagsView.getContext()).create(ApiService.class);
    }

    @Override
    public void getTagsList(int pageNumber) {
        disposable.add(apiService.loadTags(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TagListResponse>() {
                    @Override
                    public void onSuccess(TagListResponse tagListResponse) {
                        tagItems = tagListResponse.getTagItems();
//
//                                ArrayList<TagItemDetails> arrr = new ArrayList<TagItemDetails>();
//                                        arrr.add(new TagItemDetails(1,"sara",null,"ee"));
//                        tagListResponse.getTagItems().get(0).setTagItemDetails(arrr) ;
                        tagsView.updateList(tagListResponse.getTagItems());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "OnError");
                    }
                }));
    }

    @Override
    public void getItems(final String tagName) {
        disposable.add(apiService.loadItems(tagName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TagItemDetailsResponse>() {
                    @Override
                    public void onSuccess(TagItemDetailsResponse tagListResponse) {
                        Log.d(TAG, "success loaded items");
                        //expand list
                        int tagIndex = findTagNameIndex(tagName);
                        if (tagIndex != -1) {
                            tagItems.get(tagIndex).setTagItemDetails(tagListResponse.getTagItemDetails());
                            tagsView.updateList(tagItems);
                        } else {
                            Log.d(TAG, "onSuccess: tag name not found");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "OnError");
                    }
                }));
    }

    private int findTagNameIndex(String tagName) {
        for (int i = 0; i < tagItems.size(); i++) {
            if (tagItems.get(i).getTagName().contains(tagName))
                return i;

        }
        return -1;
    }
}
