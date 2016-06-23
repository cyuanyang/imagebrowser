package com.cyy.yimagebrowser.photodraweeview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 * 针对两点触摸事件闪退 处理的ViewPager
 * Created by cyy on 15/11/27.
 */
public class MultiTouchViewPager extends ViewPager {

    public MultiTouchViewPager(Context context) {
        super(context);
    }

    public MultiTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isDisallowIntercept = false; //是否允许父类拦截

    @Override public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        isDisallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        if (ev.getPointerCount() > 1 && isDisallowIntercept) {
            requestDisallowInterceptTouchEvent(false);
            boolean handled = super.dispatchTouchEvent(ev);
            requestDisallowInterceptTouchEvent(true);
            return handled;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }
}
