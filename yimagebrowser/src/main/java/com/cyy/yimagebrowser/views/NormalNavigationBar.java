package com.cyy.yimagebrowser.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cyy.yimagebrowser.R;

/**
 * Created by cyy on 2016/6/23.
 * 普通的bar
 */
public class NormalNavigationBar extends BaseNavigationBar implements View.OnClickListener{

    private FrameLayout backBtn;
    private TextView titleView;

    public NormalNavigationBar(Context context) {
        super(context);
    }

    public NormalNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NormalNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutReceours() {
        return R.layout.normal_navigation_layout;
    }

    @Override
    protected void setupView() {
        backBtn = (FrameLayout) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);
        titleView = (TextView) findViewById(R.id.title_view);
    }

    @Override
    public void onClick(View v) {
        ((Activity)this.getContext()).finish();
        ((Activity)this.getContext()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void viewPagerChanged(int currentPosition, int all) {
        super.viewPagerChanged(currentPosition, all);

        titleView.setText((currentPosition+1) + "/" + all);
    }
}
