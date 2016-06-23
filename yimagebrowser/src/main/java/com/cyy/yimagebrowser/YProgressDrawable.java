package com.cyy.yimagebrowser;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.facebook.drawee.drawable.DrawableUtils;

/**
 * Created by cyy on 2016/6/14.
 * 进度条啊
 */
public class YProgressDrawable extends Drawable {

    private Paint mRingBackgroundPaint;
    private int mRingBackgroundColor;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 圆环颜色
    private int mRingColor;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 总进度
    private int mTotalProgress = 10000;
    // 当前进度
    private int mProgress;

    public YProgressDrawable(){
        initAttrs();
    }

    @Override
    public void draw(Canvas canvas) {
        drawBar(canvas,mTotalProgress,mRingBackgroundPaint);
        drawBar(canvas,mProgress,mRingPaint);
    }

    private void drawBar(Canvas canvas, int level, Paint paint) {
        if (level > 0 ) {
            Rect bound= getBounds();
            int mXCenter = bound.centerX();
            int mYCenter = bound.centerY();
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius  + mXCenter ;
            oval.bottom = mRingRadius  + mYCenter ;
            canvas.drawArc(oval, -90, ((float) level / mTotalProgress) * 360, true, paint); //
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mRingPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mRingPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return DrawableUtils.getOpacityFromColor(this.mRingPaint.getColor());
    }

    @Override
    protected boolean onLevelChange(int level) {

        mProgress = level;
        if(level > 0 && level < 10000) {
            invalidateSelf();
            return true;
        }else {
            return false;
        }
//        return super.onLevelChange(level);
    }

    private void initAttrs() {
        float mRadius = 64;
        mStrokeWidth = 4;
        mRingBackgroundColor = 0xFFadadad;
        mRingColor = 0xFF0EB6D2;
        mRingRadius = mRadius + mStrokeWidth / 2;
        initVariable();
    }

    private void initVariable() {
        mRingBackgroundPaint = new Paint();
        mRingBackgroundPaint.setAntiAlias(true);
        mRingBackgroundPaint.setColor(mRingBackgroundColor);
        mRingBackgroundPaint.setStyle(Paint.Style.STROKE);
        mRingBackgroundPaint.setStrokeWidth(mStrokeWidth);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.FILL);
        mRingPaint.setStrokeWidth(mStrokeWidth);
    }
}
