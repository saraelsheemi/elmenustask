package com.vogella.android.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vogella.android.myapplication.adapters.ExpandableRecyclerView;
import com.vogella.android.myapplication.models.TagItem;
import com.vogella.android.myapplication.presenters.TagItemListContract;
import com.vogella.android.myapplication.presenters.TagItemListPresenter;
import com.vogella.android.myapplication.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagItemsListFragment extends Fragment implements TagItemListContract.TagsView {
    private static final String TAG = "TagItemsListFragment";
    ArrayList<TagItem> tagItems = new ArrayList<>();
    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    ExpandableRecyclerView menuItemsListAdapter;
    LinearLayoutManager linearLayoutManager;
    TagItemListPresenter presenter = new TagItemListPresenter(this);
    private int pageNumber = 1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tag_items_list, container, false);
        ButterKnife.bind(this, v);
        initAdapter();
        return v;
    }

    private void initAdapter() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        menuItemsListAdapter = new ExpandableRecyclerView(tagItems, getContext(), new OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
                presenter.getItems(((TagItem) item).getTagName());
            }
        });
        recyclerView.setAdapter(menuItemsListAdapter);
        presenter.getTagsList(pageNumber);

    }


    @Override
    public void showLoading(boolean showLoading) {
        if (showLoading) {

        } else {

        }
    }

    @Override
    public void updateList(ArrayList<TagItem> items) {
        tagItems.clear();
        tagItems.addAll(items);
        menuItemsListAdapter.notifyDataSetChanged();
    }
    @Override
    public RecyclerView getRecycler() {
        return recyclerView;
    }
}
