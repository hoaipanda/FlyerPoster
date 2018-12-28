package com.design.flyerposter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;

import com.design.flyerposter.data.MenuMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class AppUtils {
    public static ArrayList<String> addListMenu() {
        ArrayList<String> listMenu;
        listMenu = new ArrayList<>();
        listMenu.add("Edit");
        listMenu.add("Zoom");
        listMenu.add("Color");
        listMenu.add("Font");
        listMenu.add("Shadow");
        listMenu.add("Opacity");
        listMenu.add("Spacing");
        return listMenu;
    }

    public static ArrayList<MenuMain> getListMenuMain() {
        ArrayList<MenuMain> listMenu = new ArrayList<>();
        listMenu.add(new MenuMain("image.png", "Add Image"));
        listMenu.add(new MenuMain("text.png", "Add Text"));
        listMenu.add(new MenuMain("background.png", "Background"));
        listMenu.add(new MenuMain("graphic.png", "Graphics"));
        listMenu.add(new MenuMain("shape.png", "Shapes"));
        listMenu.add(new MenuMain("art.png", "Text Arts"));

        return listMenu;
    }

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
        }

        return bitmap;
    }

    public static ArrayList<String> getListFeature(Context context) {
        AssetManager assetManager = context.getAssets();
        String[] files = new String[0];
        try {
            files = assetManager.list("feature");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<String>(Arrays.asList(files));
    }

    public static ArrayList<String> getListShapes(Context context, String type, String name) {
        AssetManager assetManager = context.getAssets();
        String[] files = new String[0];
        try {
            files = assetManager.list(type + "/" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<String>(Arrays.asList(files));
    }

    public static ArrayList<String> getListFonts(Context context) {
        String[] files = new String[0];
        try {
            AssetManager assetManager = context.getAssets();
            files = assetManager.list("fonts");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>(Arrays.asList(files));
    }

    public static ArrayList<String> getListColor(Context context) {
        ArrayList<String> list = new ArrayList<>();
        InputStream is = null;
        String json = null;
        try {
            is = context.getAssets().open("colors.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray colorArr = jsonObject.getJSONArray("colors");
            for (int i = 0; i < colorArr.length(); i++) {
                JSONObject jsonObject1 = colorArr.getJSONObject(i);
                list.add(jsonObject1.getString("rgb"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;

    }


}
