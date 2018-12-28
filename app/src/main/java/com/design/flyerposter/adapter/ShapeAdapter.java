package com.design.flyerposter.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.design.flyerposter.R;

import java.util.ArrayList;

public class ShapeAdapter extends RecyclerView.Adapter<ShapeAdapter.ViewHolder> {
    private ArrayList<String> listShape;
    private Context context;
    private String type;
    private String name;

    public ShapeAdapter(Context context, ArrayList<String> listShape, String type, String name) {
        this.context = context;
        this.listShape = listShape;
        this.type = type;
        this.name = name;
    }

    @NonNull
    @Override
    public ShapeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new ShapeAdapter.ViewHolder(layoutInflater.inflate(R.layout.item_shape, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShapeAdapter.ViewHolder viewHolder, int i) {
        final String shape = listShape.get(i);
        Glide.with(context).load(Uri.parse("file:///android_asset/" + type + "/" + name + "/" + shape)).into(viewHolder.imShape);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemShape != null) {
                    onClickItemShape.onClickShape(shape);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listShape.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imShape;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imShape = itemView.findViewById(R.id.imShape);
        }
    }

    public OnClickItemShape onClickItemShape;

    public void setOnClickItemShape(OnClickItemShape onClickItemShape) {
        this.onClickItemShape = onClickItemShape;
    }

    public interface OnClickItemShape {
        void onClickShape(String shap);
    }
}
