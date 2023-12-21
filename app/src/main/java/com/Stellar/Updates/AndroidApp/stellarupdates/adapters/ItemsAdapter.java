package com.Stellar.Updates.AndroidApp.stellarupdates.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.Stellar.Updates.AndroidApp.stellarupdates.R;
import com.Stellar.Updates.AndroidApp.stellarupdates.models.Item;

import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {
    private Context context;
    private List<Item> items;

    public ItemsAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    public void setData(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.steller_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemView.setTag(items.get(position));

        Item model = items.get(position);
        holder.tvNotification.setText(model.getDescription() + "");
        holder.tvTitle.setText(model.getTitle() + "");

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNotification, tvTitle;
        ImageFilterView imgInfo;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNotification = (TextView) itemView.findViewById(R.id.tvItemDesp);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            imgInfo = (ImageFilterView) itemView.findViewById(R.id.ic_info);


        }
    }
}