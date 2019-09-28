package com.vogella.android.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vogella.android.myapplication.R;
import com.vogella.android.myapplication.models.TagItemDetails;
import com.vogella.android.myapplication.utils.OnItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.ViewHolder> {

    private ArrayList<TagItemDetails> itemDetails;
    private Context mContext;
    private final OnItemClickListener listener;

    public InnerRecyclerViewAdapter(ArrayList<TagItemDetails> itemDetails, Context mContext, OnItemClickListener listener) {
        this.itemDetails = itemDetails;
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InnerRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_details, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TagItemDetails item = itemDetails.get(position);
        holder.bind(item, listener);
        holder.name.setText(item.getName());
        Glide.with(mContext)
                .load(item.getPhotoUrl())
                .apply(new RequestOptions().dontAnimate())
                .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return itemDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_detail_item)
        ImageView itemImage;
        @BindView(R.id.txt_detail_name)
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final TagItemDetails item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
