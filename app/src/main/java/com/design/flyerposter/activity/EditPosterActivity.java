package com.design.flyerposter.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.design.flyerposter.AppUtils;
import com.design.flyerposter.Contains;
import com.design.flyerposter.ImageUtils;
import com.design.flyerposter.R;
import com.design.flyerposter.adapter.ColorAdapter;
import com.design.flyerposter.adapter.FontAdapter;
import com.design.flyerposter.adapter.MenuMainAdapter;
import com.design.flyerposter.adapter.MenuTextAdapter;
import com.design.flyerposter.data.ImageJson;
import com.design.flyerposter.data.MenuMain;
import com.design.flyerposter.data.TextJson;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class EditPosterActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private StickerView stickerView;
    private ArrayList<TextJson> listText = new ArrayList<>();
    private ArrayList<ImageJson> listImage = new ArrayList<>();
    private RelativeLayout main;
    private RelativeLayout lyMenu;
    private ImageView imClose;
    private RecyclerView rvMenu;
    private MenuTextAdapter menuTextAdapter;
    private ArrayList<String> listMenu = new ArrayList<>();
    private RelativeLayout lyFont, lyShadow, lyColor, lyEdit, lyZoom, lyLetter, lyOpacity, lySpacing;
    private SeekBar sbRotation, sbShadow, sbOpacity, sbZoom, sbLetter;
    private TextSticker mTextSticker;

    private RecyclerView rvFont;
    private FontAdapter fontAdapter;
    private ArrayList<String> listFont;

    private RecyclerView rvColor;
    private ArrayList<String> listColor;
    private ColorAdapter colorAdapter;
    private RecyclerView rvMenuMain;
    private MenuMainAdapter menuMainAdapter;
    private ArrayList<MenuMain> listMenuMain = new ArrayList<>();
    static final int REWQUEST_CHOSE_PHOTO = 111, REQUEST_STICKER = 123;
    private RelativeLayout lyText;
    private ImageView imCloseText, imDelText, imDoneText;
    private EditText edText;

    private ImageView imEdit, imRight, imLeft, imUp, imDown;
    private boolean isEdit;
    private boolean isText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_poster);
        initView();
        updateRvMenu();
        updateRvFont();
        setUpLayout();
        updatervColor();
        setUpMenuMain();

    }

    private void setUpMenuMain() {
        listMenuMain = AppUtils.getListMenuMain();
        menuMainAdapter = new MenuMainAdapter(this, listMenuMain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvMenuMain.setLayoutManager(linearLayoutManager);
        rvMenuMain.setAdapter(menuMainAdapter);
        menuMainAdapter.setOnClickItemMenuMain(new MenuMainAdapter.OnClickItemMenuMain() {
            @Override
            public void onClickMain(MenuMain menuMain) {
                switch (menuMain.getName()) {
                    case "Add Text":
                        animationUp(lyText, 1700f);
                        lyText.setVisibility(View.VISIBLE);
                        isEdit = false;
                        break;
                    case "Add Image":
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, REWQUEST_CHOSE_PHOTO);
                        break;
                    case "Background":
                        Intent intent1 = new Intent(EditPosterActivity.this, ShapesActivity.class);
                        intent1.putExtra(Contains.TYPE_SHAPE, "background");
                        startActivityForResult(intent1, REQUEST_STICKER);
                        break;
                    case "Graphics":
                        Intent intent2 = new Intent(EditPosterActivity.this, ShapesActivity.class);
                        intent2.putExtra(Contains.TYPE_SHAPE, "graphics");
                        startActivityForResult(intent2, REQUEST_STICKER);
                        break;
                    case "Shapes":
                        Intent intent3 = new Intent(EditPosterActivity.this, ShapesActivity.class);
                        intent3.putExtra(Contains.TYPE_SHAPE, "shapes");
                        startActivityForResult(intent3, REQUEST_STICKER);
                        break;
                    case "Text Arts":
                        Intent intent4 = new Intent(EditPosterActivity.this, ShapesActivity.class);
                        intent4.putExtra(Contains.TYPE_SHAPE, "shapes");
                        startActivityForResult(intent4, REQUEST_STICKER);
                        break;


                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REWQUEST_CHOSE_PHOTO:
                    if (data != null) {
                        final Uri imageUri = data.getData();
                        Bitmap bm = null;
                        try {
                            bm = ImageUtils.handleSamplingAndRotationBitmap(EditPosterActivity.this, imageUri);
                            addImage(bm);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    break;
                case REQUEST_STICKER:
                    if (data != null) {
                        String icon = data.getStringExtra(Contains.SHAPE);
                        Bitmap bm = AppUtils.getBitmapFromAsset(EditPosterActivity.this, icon);
                        addImage(bm);
                    }
                    break;

            }
        }

    }

    private void addSticker(Bitmap bitmap, int x, int y, int w, int h) {
//        float ratio = bitmap.getWidth() / (float) bitmap.getHeight();
//        int with = w;
//        int height = Math.round(with / ratio);
        bitmap = Bitmap.createScaledBitmap(
                bitmap, w, h, false);
        Drawable drawable = new BitmapDrawable(bitmap);
        stickerView.addSticker(new DrawableSticker(drawable), x, y);
    }

    private void addImage(Bitmap bitmap) {
        float ratio = bitmap.getWidth() / (float) bitmap.getHeight();
        int with = 720;
        int height = Math.round(with / ratio);
        bitmap = Bitmap.createScaledBitmap(
                bitmap, with, height, false);
        Drawable drawable = new BitmapDrawable(bitmap);
        stickerView.addSticker(new DrawableSticker(drawable));
    }

    private void setUpLayout() {
        ViewGroup.LayoutParams params = main.getLayoutParams();
        params.height = 850 + 300;
        params.width = 600 + 300;
        main.setLayoutParams(params);
        String json = null;
        try {
            InputStream is = getAssets().open("mycategory.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");


            JSONObject object = new JSONObject(json);
            JSONObject jsonData = object.getJSONObject("data");
            JSONArray categoryArr = jsonData.getJSONArray("category_list");
//            for (int i = 0; i < categoryArr.length(); i++) {
            JSONObject cateObject = categoryArr.getJSONObject(0);
            JSONArray listArr = cateObject.getJSONArray("json_list");
            JSONObject listObject = listArr.getJSONObject(0);
            JSONArray textArr = listObject.getJSONArray("text_json");
//            for (int j = 0; j < textArr.length(); j++) {
//                JSONObject textObject = textArr.getJSONObject(j);
//                int xPos = textObject.getInt("xPos");
//                int yPos = textObject.getInt("yPos");
//                String color = textObject.getString("color");
//                String text = textObject.getString("text");
//                int size = textObject.getInt("size");
//                String fontPath = textObject.getString("fontPath");
//                String bg_image = textObject.getString("bg_image");
//                int opacity = textObject.getInt("opacity");
//                String shadowColor = textObject.getString("shadowColor");
//                listText.add(new TextJson(xPos, yPos, color, text, size, fontPath, bg_image, opacity, shadowColor));
//
//
//            }

            JSONArray imageArr = listObject.getJSONArray("image_sticker_json");
            for (int i = 0; i < imageArr.length(); i++) {
                JSONObject object1 = imageArr.getJSONObject(i);
                int top = object1.getInt("yPos");
                int left = object1.getInt("xPos");
                int width = object1.getInt("width");
                int height = object1.getInt("height");
                String image = object1.getString("image_sticker_image");
                listImage.add(new ImageJson(image, height, width, top, left));
            }

            for (int i = 0; i < listImage.size(); i++) {
                ImageJson imageJson = listImage.get(i);
                Bitmap bm = AppUtils.getBitmapFromAsset(EditPosterActivity.this, imageJson.getImage());
                addSticker(bm, imageJson.getLeft(), imageJson.getTop(), imageJson.getWidth(), imageJson.getHeigh());

            }
//            }


            for (int i = 0; i < listText.size(); i++) {
                TextJson textJson = listText.get(i);
                TextSticker tv = new TextSticker(this);
                tv.setText(textJson.getText());
                tv.setTextColor(Color.parseColor(textJson.getColor()));
                tv.setMaxTextSize(textJson.getSize());
                tv.setAlpha(textJson.getOpacity());
//                tv.setLetterSpacing(textJson.get)

                tv.setTypeface(Typeface.createFromAsset(getAssets(), textJson.getFontPath()));
                tv.resizeText();
                stickerView.addSticker(tv, textJson.getxPos() + 150, textJson.getyPos() + 150);
                stickerView.invalidate();
                stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
                    @Override
                    public void onStickerAdded(@NonNull Sticker sticker) {

                    }

                    @Override
                    public void onStickerClicked(@NonNull Sticker sticker) {
                        if (sticker instanceof TextSticker) {

                            if (isText) {
                                menuTextAdapter.reSetAdapter();
                            } else {
                                animationUp(lyMenu, 500f);
                                lyMenu.setVisibility(View.VISIBLE);
                            }

                            isText = true;

                            mTextSticker = (TextSticker) sticker;

                            lyEdit.setVisibility(View.VISIBLE);
                            lyShadow.setVisibility(View.GONE);
                            lyFont.setVisibility(View.GONE);
                            lyColor.setVisibility(View.GONE);
                            lyZoom.setVisibility(View.GONE);
                            lyLetter.setVisibility(View.GONE);
                            lyOpacity.setVisibility(View.GONE);
                            menuTextAdapter.reSetAdapter();
                        }
                    }

                    @Override
                    public void onStickerDeleted(@NonNull Sticker sticker) {

                    }

                    @Override
                    public void onStickerDragFinished(@NonNull Sticker sticker) {

                    }

                    @Override
                    public void onStickerTouchedDown(@NonNull Sticker sticker) {

                    }

                    @Override
                    public void onStickerZoomFinished(@NonNull Sticker sticker) {

                    }

                    @Override
                    public void onStickerFlipped(@NonNull Sticker sticker) {

                    }

                    @Override
                    public void onStickerDoubleTapped(@NonNull Sticker sticker) {

                    }
                });
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void updatervColor() {
        listColor = AppUtils.getListColor(this);
        colorAdapter = new ColorAdapter(listColor);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvColor.setLayoutManager(linearLayoutManager);
        rvColor.setAdapter(colorAdapter);
        colorAdapter.setOnClickItemColor(new ColorAdapter.OnClickItemColor() {
            @Override
            public void onClickColor(String color) {
                mTextSticker.setTextColor(Color.parseColor(color));
                stickerView.invalidate();
            }
        });
    }

    private void updateRvFont() {
        listFont = AppUtils.getListFonts(this);
        fontAdapter = new FontAdapter(listFont);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvFont.setLayoutManager(linearLayoutManager);
        rvFont.setAdapter(fontAdapter);
        fontAdapter.setOnClickItemFont(new FontAdapter.OnClickItemFont() {
            @Override
            public void onClickFont(String font) {
                mTextSticker.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + font));
                stickerView.invalidate();
            }
        });
    }


    private void updateRvMenu() {
        listMenu = AppUtils.addListMenu();
        menuTextAdapter = new MenuTextAdapter(listMenu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvMenu.setLayoutManager(linearLayoutManager);
        rvMenu.setAdapter(menuTextAdapter);
        menuTextAdapter.setOnClickItemMenu(new MenuTextAdapter.OnClickItemMenu() {
            @Override
            public void onClickMenu(String menu) {
                switch (menu) {
                    case "Edit":
                        lyEdit.setVisibility(View.VISIBLE);
                        lyShadow.setVisibility(View.GONE);
                        lyFont.setVisibility(View.GONE);
                        lyColor.setVisibility(View.GONE);
                        lyZoom.setVisibility(View.GONE);
                        lyLetter.setVisibility(View.GONE);
                        lyOpacity.setVisibility(View.GONE);
                        break;

                    case "Zoom":
                        lyShadow.setVisibility(View.GONE);
                        lyFont.setVisibility(View.GONE);
                        lyColor.setVisibility(View.GONE);
                        lyEdit.setVisibility(View.GONE);
                        lyZoom.setVisibility(View.VISIBLE);
                        lyLetter.setVisibility(View.GONE);
                        lyOpacity.setVisibility(View.GONE);
                        sbZoom.setProgress((int) (mTextSticker.getScale() * sbZoom.getMax() / 2));
                        break;
                    case "Color":
                        lyShadow.setVisibility(View.GONE);
                        lyFont.setVisibility(View.GONE);
                        lyColor.setVisibility(View.VISIBLE);
                        lyEdit.setVisibility(View.GONE);
                        lyZoom.setVisibility(View.GONE);
                        lyLetter.setVisibility(View.GONE);
                        lyOpacity.setVisibility(View.GONE);
                        break;
                    case "Font":
                        lyShadow.setVisibility(View.GONE);
                        lyFont.setVisibility(View.VISIBLE);
                        lyColor.setVisibility(View.GONE);
                        lyEdit.setVisibility(View.GONE);
                        lyZoom.setVisibility(View.GONE);
                        lyLetter.setVisibility(View.GONE);
                        lyOpacity.setVisibility(View.GONE);
                        break;
                    case "Shadow":
                        lyShadow.setVisibility(View.VISIBLE);
                        lyFont.setVisibility(View.GONE);
                        lyColor.setVisibility(View.GONE);
                        lyEdit.setVisibility(View.GONE);
                        lyZoom.setVisibility(View.GONE);
                        lyLetter.setVisibility(View.GONE);
                        lyOpacity.setVisibility(View.GONE);
                        sbShadow.setProgress((int) mTextSticker.getShadow());

                        break;
                    case "Opacity":
                        lyShadow.setVisibility(View.GONE);
                        lyFont.setVisibility(View.GONE);
                        lyColor.setVisibility(View.GONE);
                        lyEdit.setVisibility(View.GONE);
                        lyZoom.setVisibility(View.GONE);
                        lyLetter.setVisibility(View.GONE);
                        lyOpacity.setVisibility(View.VISIBLE);

                        sbOpacity.setProgress((int) mTextSticker.getOpacity());

                        break;
                    case "Spacing":
                        lyShadow.setVisibility(View.GONE);
                        lyFont.setVisibility(View.GONE);
                        lyColor.setVisibility(View.GONE);
                        lyEdit.setVisibility(View.GONE);
                        lyZoom.setVisibility(View.GONE);
                        lyLetter.setVisibility(View.VISIBLE);
                        lyOpacity.setVisibility(View.GONE);

                        sbLetter.setProgress((int) mTextSticker.getLetterSpacing() * 10);
                        break;
                }
            }
        });
    }


    private void initView() {
        edText = findViewById(R.id.edText);
        lyText = findViewById(R.id.lyText);
        imCloseText = findViewById(R.id.imCloseText);
        imDelText = findViewById(R.id.imDelText);
        imDoneText = findViewById(R.id.imDoneText);

        rvMenuMain = findViewById(R.id.rvMenuMain);
        sbOpacity = findViewById(R.id.sbOpacity);
        sbZoom = findViewById(R.id.sbZoom);
        sbLetter = findViewById(R.id.sbLetter);
        main = findViewById(R.id.main);
        lyMenu = findViewById(R.id.lyMenu);
        imClose = findViewById(R.id.imClose);
        rvMenu = findViewById(R.id.rvMenuText);
        stickerView = findViewById(R.id.sticker);
        sbRotation = findViewById(R.id.seekRotation);
        lyFont = findViewById(R.id.lyFont);
        rvFont = findViewById(R.id.rvFont);
        lyShadow = findViewById(R.id.lyShadow);
        sbShadow = findViewById(R.id.sbShadow);
        lyColor = findViewById(R.id.lyColor);
        rvColor = findViewById(R.id.rvColor);
        lyLetter = findViewById(R.id.lyLetter);
        lyEdit = findViewById(R.id.lyEdit);
        lyZoom = findViewById(R.id.lyZoom);
        lyLetter = findViewById(R.id.lyLetter);
        lyOpacity = findViewById(R.id.lyOpacity);

        imEdit = findViewById(R.id.imEdit);
        imUp = findViewById(R.id.imUp);
        imDown = findViewById(R.id.imDown);
        imRight = findViewById(R.id.imRight);
        imLeft = findViewById(R.id.imLeft);


        imClose = findViewById(R.id.imClose);
        imClose.setOnClickListener(lsClick);

        imCloseText.setOnClickListener(lsClick);
        imDoneText.setOnClickListener(lsClick);
        imDelText.setOnClickListener(lsClick);
        imEdit.setOnClickListener(lsClick);
        imUp.setOnClickListener(lsClick);
        imDown.setOnClickListener(lsClick);
        imRight.setOnClickListener(lsClick);
        imLeft.setOnClickListener(lsClick);

        sbShadow.setOnSeekBarChangeListener(this);
        sbRotation.setOnSeekBarChangeListener(this);
        sbLetter.setOnSeekBarChangeListener(this);
        sbZoom.setOnSeekBarChangeListener(this);
        sbOpacity.setOnSeekBarChangeListener(this);

    }

    private View.OnClickListener lsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imClose:
                    animationDown(lyMenu, 500f);
                    lyMenu.setVisibility(View.GONE);
                    isText = false;
                    break;
                case R.id.imCloseText:
                    hideKeyboard();
                    animationDown(lyText, 1700f);
                    lyText.setVisibility(View.GONE);
                    edText.setText("");

                    break;
                case R.id.imDoneText:
                    hideKeyboard();
                    animationDown(lyText, 1700f);
                    lyText.setVisibility(View.GONE);
                    if (isEdit) {
                        if (edText.getText().length() > 0)
                            mTextSticker.setText(edText.getText().toString());
                        else {
                            Toast.makeText(EditPosterActivity.this, "Type something", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        TextSticker tv = new TextSticker(EditPosterActivity.this);
                        if (edText.getText().length() > 0) {
                            tv.setText(edText.getText().toString());
                            tv.setTextColor(Color.WHITE);
                            tv.setMaxTextSize(18);

                            tv.resizeText();
                            stickerView.addStickerNone(tv);
                            stickerView.invalidate();
                            edText.setText("");
                        } else {
                            Toast.makeText(EditPosterActivity.this, "Type something", Toast.LENGTH_SHORT).show();
                        }

                    }


                    break;
                case R.id.imDelText:
                    edText.setText("");
                    edText.refreshDrawableState();
                    edText.setHint("type something");
                    break;
                case R.id.imEdit:
                    animationUp(lyText, 1700f);
                    lyText.setVisibility(View.VISIBLE);
                    edText.setText(mTextSticker.getText());
                    isEdit = true;
                    break;
                case R.id.imUp:
                    break;
                case R.id.imDown:
                    break;
                case R.id.imRight:
                    break;
                case R.id.imLeft:
                    break;
            }
        }
    };

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(EditPosterActivity.this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }


    private void animationUp(View view, float x) {
        TranslateAnimation translateYAnimation = new TranslateAnimation(0f, 0f, x, 0f);
        translateYAnimation.setDuration(400l);
        translateYAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(translateYAnimation);

    }

    private void animationDown(View view, float x) {
        TranslateAnimation translateYAnimation = new TranslateAnimation(0f, 0f, 0f, x);
        translateYAnimation.setDuration(400l);
        translateYAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(translateYAnimation);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sbShadow:
                mTextSticker.setShadow((float) progress, "#000000");
                stickerView.invalidate();
                break;
            case R.id.seekRotation:

                break;
            case R.id.sbOpacity:
                mTextSticker.setAlpha(progress);
                stickerView.invalidate();
                break;
            case R.id.sbLetter:
                mTextSticker.setLetterSpacing((float) progress / 10);
                stickerView.invalidate();
                break;
            case R.id.sbZoom:
//                mTextSticker.setMaxTextSize(progress);
                mTextSticker.setScale(2 * (float) progress / seekBar.getMax());
                stickerView.setScale(2 * (float) progress / seekBar.getMax());
                stickerView.invalidate();
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
