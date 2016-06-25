package com.cyy.yimagebrowser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;

import java.util.List;

/**
 * Created by cyy on 2016/6/25.
 * 入口函数
 */
public class YImageBrowser {

    private YImageBrowser(){}

    public static YImageBrowser newInstance(){
        return new YImageBrowser();
    }

    public void startBrowserImage(Activity activity , List<String> uriStrs , List<String>holderUriStrs , View selectedView , int position){

        int[] size = new int[2];
        Rect r = new Rect();
        selectedView.getLocalVisibleRect(r);
        size[0] = r.right;
        size[1] = r.bottom;
        int[] location = new int[2];
        selectedView.getLocationOnScreen(location);

        Intent i = new Intent();
        i.setClass(activity, YImageBrowserActivity.class);
        String[] strings = new String[uriStrs.size()];
        uriStrs.toArray(strings);
        String[] stringsHolder = new String[holderUriStrs.size()];
        holderUriStrs.toArray(stringsHolder);
        i.putExtra("placeHolderUri",stringsHolder); //传小图作为占位图
        i.putExtra("urls", strings);//大图地址
        i.putExtra("position", position);
        i.putExtra("currentImageUri", uriStrs.get(position));
        i.putExtra("location", location);
        i.putExtra("size", size);
        activity.startActivity(i);
        activity.overridePendingTransition(0, 0);
    }
}
