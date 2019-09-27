package com.vogella.android.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vogella.android.myapplication.R;
import com.vogella.android.myapplication.models.TagItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuItemsListAdapter extends RecyclerView.Adapter<MenuItemsListAdapter.ItemViewHolder> {

    private ArrayList<TagItem> tagItems;
    private Context mContext;

    public MenuItemsListAdapter(ArrayList<TagItem> tagItems, Context mContext) {
        this.tagItems = tagItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.tag_list_item, parent, false);

        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final TagItem tagItem = tagItems.get(position);
        holder.tagName.setText(tagItem.getTagName());

        Glide.with(mContext)
                .load(tagItem.getPhotoURL())
                .apply(new RequestOptions().dontAnimate())
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return tagItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_menu_item)
        ImageView itemImage;
        @BindView(R.id.txt_item_name)
        TextView tagName;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar1);
        }
    }
}
