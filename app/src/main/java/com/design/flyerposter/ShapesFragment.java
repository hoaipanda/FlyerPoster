package com.design.flyerposter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.design.flyerposter.adapter.ShapeAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ShapesFragment extends Fragment {
    private String name;
    private String type;
    private RecyclerView rvShape;
    private ShapeAdapter shapeAdapter;
    private ArrayList<String> listShape = new ArrayList<>();
    private Activity context;

    public ShapesFragment(String name, String type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shapes, container, false);
        rvShape = view.findViewById(R.id.rvShape);
        context = getActivity();
        listShape = AppUtils.getListShapes(context, type, name);
        shapeAdapter = new ShapeAdapter(context, listShape, type, name);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        rvShape.setLayoutManager(gridLayoutManager);
        rvShape.setAdapter(shapeAdapter);
        shapeAdapter.setOnClickItemShape(new ShapeAdapter.OnClickItemShape() {
            @Override
            public void onClickShape(String shape) {
                Intent intent = new Intent();
                intent.putExtra(Contains.SHAPE, type + "/" + name + "/" + shape);
                context.setResult(Activity.RESULT_OK, intent);
                context.finish();
            }
        });
        return view;
    }

}
