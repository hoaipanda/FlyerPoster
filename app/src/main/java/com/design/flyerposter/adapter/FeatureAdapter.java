package com.design.flyerposter.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.design.flyerposter.R;

import java.util.ArrayList;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.ViewHolder> {
    private ArrayList<String> listFeature;
    private Context context;

    public FeatureAdapter(Context context, ArrayList<String> listFeature) {
        this.context = context;
        this.listFeature = listFeature;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new ViewHolder(layoutInflater.inflate(R.layout.item_feature, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String feature = listFeature.get(i);
        Glide.with(context).load(Uri.parse("file:///android_asset/feature/" + feature)).into(viewHolder.imFeature);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemFeature != null) {
                    onClickItemFeature.onClickFeature(feature);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFeature.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imFeature;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imFeature = itemView.findViewById(R.id.imFeature);
        }
    }

    public OnClickItemFeature onClickItemFeature;

    public void setOnClickItemFeature(OnClickItemFeature onClickItemFeature) {
        this.onClickItemFeature = onClickItemFeature;
    }

    public interface OnClickItemFeature {
        void onClickFeature(String feature);
    }
}
