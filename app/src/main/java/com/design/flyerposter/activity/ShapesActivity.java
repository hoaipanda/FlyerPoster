package com.design.flyerposter.activity;

import android.content.res.AssetManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.design.flyerposter.Contains;
import com.design.flyerposter.R;
import com.design.flyerposter.ShapesFragment;
import com.design.flyerposter.adapter.ViewPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ShapesActivity extends AppCompatActivity {
    private ImageView imBack;
    private TabLayout tab;
    private ViewPager pager;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<String> listShape = new ArrayList<>();
    private String type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes);
        type = getIntent().getStringExtra(Contains.TYPE_SHAPE);
        initView();
        updateTab();
        setupTabTitle();
    }

    private void initView() {
        imBack = findViewById(R.id.imBack);
        tab = findViewById(R.id.tab);
        pager = findViewById(R.id.pager);
    }

    private void updateTab() {
        listShape = getListShape();
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < listShape.size(); i++) {
            ShapesFragment shapesFragment = new ShapesFragment(listShape.get(i), type);
            viewPagerAdapter.addFragment(shapesFragment);
        }
        pager.setAdapter(viewPagerAdapter);
        tab.setupWithViewPager(pager);
    }

    private ArrayList<String> getListShape() {
        AssetManager assetManager = getAssets();
        String[] files = new String[0];
        try {
            files = assetManager.list("shapes");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<String>(Arrays.asList(files));
    }

    private void setupTabTitle() {
        for (int i = 0; i < listShape.size(); i++)
            tab.getTabAt(0).setText(listShape.get(i));
    }

}
