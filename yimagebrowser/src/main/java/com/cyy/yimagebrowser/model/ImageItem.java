package com.cyy.yimagebrowser.model;

import android.support.annotation.Size;

/**
 * Created by cyy on 2016/6/13.
 *
 * image model 用于放大用的
 *
 */
public class ImageItem {

    private String urlStr;
    private int position;

    private int orginWidth;
    private int orginHeight;

    private int locationX;
    private int locationY;

    public String getUrlStr() {
        return urlStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getOrginWidth() {
        return orginWidth;
    }

    public void setOrginWidth(int orginWidth) {
        this.orginWidth = orginWidth;
    }

    public int getOrginHeight() {
        return orginHeight;
    }

    public void setOrginHeight(int orginHeight) {
        this.orginHeight = orginHeight;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }
}
