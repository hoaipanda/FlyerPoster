package com.design.flyerposter.adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.design.flyerposter.R;

import java.util.ArrayList;

public class FontAdapter extends RecyclerView.Adapter<FontAdapter.ViewHolder> {
    private ArrayList<String> listFont;

    public FontAdapter(ArrayList<String> listFont) {
        this.listFont = listFont;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.item_font, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String font = listFont.get(i);
        viewHolder.tvFont.setTypeface(Typeface.createFromAsset(viewHolder.itemView.getContext().getAssets(), "fonts/" + font));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemFont != null) {
                    onClickItemFont.onClickFont(font);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listFont.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFont;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFont = itemView.findViewById(R.id.tvFont);
        }
    }

    public OnClickItemFont onClickItemFont;

    public void setOnClickItemFont(OnClickItemFont onClickItemFont) {
        this.onClickItemFont = onClickItemFont;
    }

    public interface OnClickItemFont {
        void onClickFont(String font);
    }
}
