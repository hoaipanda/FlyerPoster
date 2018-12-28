package com.design.flyerposter.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.design.flyerposter.R;

import java.util.ArrayList;

public class MenuTextAdapter extends RecyclerView.Adapter<MenuTextAdapter.ViewHolder> {
    private ArrayList<String> listMenu;

    public MenuTextAdapter(ArrayList<String> listMenu) {
        this.listMenu = listMenu;
    }

    int pos = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.item_menu_text, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final String menu = listMenu.get(i);
        viewHolder.tvMenu.setText(menu);
        if (pos == i) {
            viewHolder.lyBg.setVisibility(View.VISIBLE);
            viewHolder.tvMenu.setTextColor(Color.WHITE);
        } else {
            viewHolder.lyBg.setVisibility(View.GONE);
            viewHolder.tvMenu.setTextColor(Color.GRAY);
        }

        viewHolder.itemPos = i;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = viewHolder.itemPos;
                notifyDataSetChanged();
                if (onClickItemMenu != null) {
                    onClickItemMenu.onClickMenu(menu);
                }
            }
        });

    }

    public void reSetAdapter() {
        pos = 0;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMenu;
        RelativeLayout lyBg;
        int itemPos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMenu = itemView.findViewById(R.id.tvMenu);
            lyBg = itemView.findViewById(R.id.bg);
        }
    }

    public interface OnClickItemMenu {
        void onClickMenu(String menu);
    }

    public OnClickItemMenu onClickItemMenu;

    public void setOnClickItemMenu(OnClickItemMenu onClickItemMenu) {
        this.onClickItemMenu = onClickItemMenu;
    }
}
