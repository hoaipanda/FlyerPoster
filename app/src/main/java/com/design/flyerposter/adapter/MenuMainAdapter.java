package com.design.flyerposter.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.design.flyerposter.R;
import com.design.flyerposter.data.MenuMain;

import java.util.ArrayList;

public class MenuMainAdapter extends RecyclerView.Adapter<MenuMainAdapter.ViewHolder> {
    private ArrayList<MenuMain> list;
    private Context context;

    public MenuMainAdapter(Context context, ArrayList<MenuMain> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.item_menu_main, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MenuMain menuMain = list.get(i);
        Glide.with(context).load(Uri.parse("file:///android_asset/menumain/" + menuMain.getImage())).into(viewHolder.imMenuMain);
        viewHolder.tvMenu.setText(menuMain.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemMenuMain != null) {
                    onClickItemMenuMain.onClickMain(menuMain);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imMenuMain;
        TextView tvMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imMenuMain = itemView.findViewById(R.id.imMenuMain);
            tvMenu = itemView.findViewById(R.id.tvMenu);
        }
    }

    public OnClickItemMenuMain onClickItemMenuMain;

    public void setOnClickItemMenuMain(OnClickItemMenuMain onClickItemMenuMain) {
        this.onClickItemMenuMain = onClickItemMenuMain;
    }

    public interface OnClickItemMenuMain {
        void onClickMain(MenuMain menuMain);
    }
}
