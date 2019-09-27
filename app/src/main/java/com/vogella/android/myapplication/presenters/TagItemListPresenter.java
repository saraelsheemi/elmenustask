package com.vogella.android.myapplication.presenters;

import android.util.Log;

import com.vogella.android.myapplication.models.TagListResponse;
import com.vogella.android.myapplication.network.ApiService;
import com.vogella.android.myapplication.network.ServiceGenerator;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TagItemListPresenter implements TagItemListContract.Presenter {
    private static final String TAG = "TagItemListPresenter";

    private TagItemListContract.TagsView tagsView;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ApiService apiService;

    public TagItemListPresenter(TagItemListContract.TagsView tagsView) {
        this.tagsView = tagsView;
        apiService = ServiceGenerator.getClient(tagsView.getContext()).create(ApiService.class);
    }

    @Override
    public void getTagsList(int pageNumber) {
        tagsView.showLoading(true);
        disposable.add(apiService.loadTags(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TagListResponse>() {
                    @Override
                    public void onSuccess(TagListResponse tagListResponse) {
                        tagsView.updateList(tagListResponse.getTagItems());

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "OnError");
                        tagsView.showLoading(false);
                    }
                }));
    }
}
