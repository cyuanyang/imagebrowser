package com.cyy.yimagebrowser.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cyy.yimagebrowser.YAttach;

/**
 * Created by cyy on 2016/6/23.
 *
 */
public abstract class BaseNavigationBar extends LinearLayout{
    public BaseNavigationBar(Context context) {
        this(context , null);
    }

    public BaseNavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public BaseNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMyView();
    }

    private void initMyView() {
        LayoutInflater.from(this.getContext()).inflate(getLayoutReceours() , this);
        setupView();
    }

    public void viewPagerChanged(int currentPosition,int all){
        if (YAttach.DEBUG)
            Log.d("BaseNavigationBar" , "viewPagerChanged currentPosition="+currentPosition);
    }
    abstract protected int getLayoutReceours();
    abstract protected void setupView();
}
