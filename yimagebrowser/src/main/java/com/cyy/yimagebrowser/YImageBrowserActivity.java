package com.cyy.yimagebrowser;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;


/**
 * Created by cyy on 2016/6/12.
 */
public class YImageBrowserActivity extends FragmentActivity {

    protected FrameLayout contentLayout;

    private String[] placeholderUri; //用于大图还没加载出来时先放一个缩略图
    private String[] urlStrs;
    private int pos;

    private int[] size;
    private int[] location;
    private String currentImageUri; //缩略图用于缩放动画的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        placeholderUri = (String[]) getIntent().getCharSequenceArrayExtra("placeHolderUri");
        urlStrs = (String[]) getIntent().getCharSequenceArrayExtra("urls");
        pos = getIntent().getIntExtra("position", 0);
        location = getIntent().getIntArrayExtra("location");
        size = getIntent().getIntArrayExtra("size");
        currentImageUri = getIntent().getStringExtra("currentImageUri");

        super.setContentView(R.layout.activity_image_browser);
        initView();
    }

    private void initView() {
        contentLayout = (FrameLayout) findViewById(R.id.contentLayout);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction t = manager.beginTransaction();
        t.add(R.id.contentLayout, YImageBrowserFragment.newInstance(urlStrs, placeholderUri, currentImageUri, pos, location, size));
        t.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
