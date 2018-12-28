package com.design.flyerposter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.design.flyerposter.R;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private ArrayList<String> listColor;

    public ColorAdapter(ArrayList<String> listColor) {
        this.listColor = listColor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.item_color, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String color = listColor.get(i);
        viewHolder.imColor.setColorFilter(Color.parseColor(color));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemColor != null) {
                    onClickItemColor.onClickColor(color);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listColor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imColor = itemView.findViewById(R.id.imColor);
        }
    }

    public interface OnClickItemColor {
        void onClickColor(String color);
    }

    public OnClickItemColor onClickItemColor;

    public void setOnClickItemColor(OnClickItemColor onClickItemColor) {
        this.onClickItemColor = onClickItemColor;
    }
}
