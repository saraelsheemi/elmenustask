package com.vogella.android.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vogella.android.myapplication.adapters.ExpandableRecyclerView;
import com.vogella.android.myapplication.models.TagItem;
import com.vogella.android.myapplication.models.TagItemDetails;
import com.vogella.android.myapplication.presenters.TagItemListContract;
import com.vogella.android.myapplication.presenters.TagItemListPresenter;
import com.vogella.android.myapplication.utils.OnItemClickListener;
import com.vogella.android.myapplication.views.activities.MainActivity;
import com.vogella.android.myapplication.views.fragments.ItemDetailsFragment;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagItemsListFragment extends Fragment implements TagItemListContract.TagsView {
    private static final String TAG = "TagItemsListFragment";
    private ArrayList<TagItem> tagItems = new ArrayList<>();
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    private ExpandableRecyclerView menuItemsListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private TagItemListPresenter presenter = new TagItemListPresenter(this);
    private int pageNumber = 1;
    private boolean isScrolling = false;
    private int currentItems, totalItems, scrollOutItems;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    private final String PARENT_LISTENER = "Parent";
    private final String CHILD_LISTENER = "CHILD";
    View v;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tag_items_list, container, false);
        ButterKnife.bind(this, v);
        initAdapter();
        return v;
    }

    private void initAdapter() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        menuItemsListAdapter = new ExpandableRecyclerView(tagItems, getContext(),
                getListener(PARENT_LISTENER), getListener(CHILD_LISTENER));

        recyclerView.setAdapter(menuItemsListAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;

                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    pageNumber++;
                    presenter.getTagsList(pageNumber);
                }
            }
        });
        presenter.getTagsList(pageNumber);

    }


    @Override
    public void showLoading(boolean showLoading) {
        if (showLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateList(ArrayList<TagItem> items) {
        tagItems.addAll(items);
        menuItemsListAdapter.notifyDataSetChanged();
    }

    @Override
    public RecyclerView getRecycler() {
        return recyclerView;
    }

    public OnItemClickListener getListener(String type) {
        switch (type) {
            case PARENT_LISTENER:
                return new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object item) {
                        presenter.getItems(((TagItem) item).getTagName());
                    }
                };
            case CHILD_LISTENER:
                return new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object item) {
                        ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("details", ((TagItemDetails) item));
                        itemDetailsFragment.setArguments(bundle);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.addNewTransition(itemDetailsFragment);

                    }
                };
            default:
                return null;
        }

    }
}
