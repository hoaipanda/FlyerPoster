package com.design.flyerposter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.design.flyerposter.AppUtils;
import com.design.flyerposter.Contains;
import com.design.flyerposter.R;
import com.design.flyerposter.adapter.FeatureAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvFeature;
    private RelativeLayout lyCategories, lyMyDesign, lyFeature;
    private ImageView imFeature, imCategories, imMyDesign;
    private ArrayList<String> listFeature = new ArrayList<>();
    private FeatureAdapter featureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setUpRvFeature();
    }

    private void initView() {
        rvFeature = findViewById(R.id.rvFeature);
        lyCategories = findViewById(R.id.lyCategories);
        lyMyDesign = findViewById(R.id.lyMyDesign);
        lyFeature = findViewById(R.id.lyFeature);
        imFeature = findViewById(R.id.imFeature);
        imCategories = findViewById(R.id.imCategories);
        imMyDesign = findViewById(R.id.imMyDesign);

        lyCategories.setOnClickListener(lsClick);
        lyFeature.setOnClickListener(lsClick);
        lyMyDesign.setOnClickListener(lsClick);
    }


    private void setUpRvFeature() {
        listFeature = AppUtils.getListFeature(this);
        featureAdapter = new FeatureAdapter(this, listFeature);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        rvFeature.setLayoutManager(gridLayoutManager);
        rvFeature.setAdapter(featureAdapter);
        featureAdapter.setOnClickItemFeature(new FeatureAdapter.OnClickItemFeature() {
            @Override
            public void onClickFeature(String feature) {
                Intent intent = new Intent(MainActivity.this, EditPosterActivity.class);
                intent.putExtra(Contains.POSTER, feature);
                startActivity(intent);
            }
        });
    }

    private View.OnClickListener lsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lyCategories:
                    imCategories.setVisibility(View.VISIBLE);
                    imMyDesign.setVisibility(View.GONE);
                    imFeature.setVisibility(View.GONE);
                    rvFeature.setVisibility(View.VISIBLE);
                    break;
                case R.id.lyFeature:
                    imCategories.setVisibility(View.GONE);
                    imMyDesign.setVisibility(View.GONE);
                    imFeature.setVisibility(View.VISIBLE);
                    break;
                case R.id.lyMyDesign:
                    imCategories.setVisibility(View.GONE);
                    imMyDesign.setVisibility(View.VISIBLE);
                    imFeature.setVisibility(View.GONE);
                    break;
            }
        }
    };
}
