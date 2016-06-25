package com.cyy.imagebrowserdemo.mlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by cyy on 2016/6/24.
 * 只负责布局
 *
 */
public class AutoGridLayout extends FrameLayout {

    private final static int MAX_CHILDREN = 9; //最大9个
    private final static int MARGIN_IN_IMAGE = 10; // px

    private int showedNum = 0 ;

    private List<String> mUriStrList;
    private View itemViews[] = new View[9];

    private int imageWidth;

    private ItemClickListener itemClickListener;

    public AutoGridLayout(Context context) {
        super(context);
    }

    public AutoGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置显示几个
     * @param uriStr fff
     */
    public void setHowMuchImageView(List<String> uriStr , boolean loadData){
        this.mUriStrList = uriStr;
        int num = uriStr.size();
        if (num > 9){
            showedNum = 9;
        }else {
            showedNum = num;
        }
        requestLayout();
        if (loadData){
            int size = uriStr.size();
            for (int i = 0 ; i< size ; i++){
                this.setDataForEveryItemView(itemViews[i] , uriStr.get(i));
            }
        }
    }

    public void setDataForEveryItemView(View itemView , String uriStr){}

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0 ; i < MAX_CHILDREN ; i++){
            View itemView = LayoutInflater.from(this.getContext())
                    .inflate(getItemViewLayout() , null);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams
                    (100 , 100);
            this.addView(itemView , lp);
            this.itemViews[i] = itemView;
            final int position = i;
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v ) {
                    if (itemClickListener!=null)itemClickListener.onItemClick(v , position);
                }
            });
        }
    }

    public int getItemViewLayout(){
        return 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int WIDTH_MODE = MeasureSpec.getMode(widthMeasureSpec);
        int WIDTH_SIZE = MeasureSpec.getSize(widthMeasureSpec);

        int childStatue = 0; ///自view的状态 暂时不知道啥作用 看设置0就行，我也不知道为啥，看源码就是这样干的

        Log.e("---------", "size width = "+ WIDTH_SIZE );
        imageWidth = calculateImageWidthByShowedNum(WIDTH_SIZE , showedNum);
        int childWidthMeasureSpec  = getChildMeasureSpec(widthMeasureSpec , 0 , imageWidth);
        int childHeightMeasureSpec = getChildMeasureSpec
                (heightMeasureSpec , 0 , (int)(imageWidth * getHeightRatio(showedNum)));

        int count = this.getChildCount();
        for (int i = 0 ; i < count ; i++){
            if (i > showedNum - 1){
                //不显示的要隐藏
                getChildAt(i).setVisibility(GONE);
            }else {
                ///测量每一个宽高
                getChildAt(i).measure(childWidthMeasureSpec , childHeightMeasureSpec);
                getChildAt(i).setVisibility(VISIBLE);
            }
        }

        int maxHeight = ((showedNum-1)/3 +1) * imageWidth + (showedNum-1)/3 * MARGIN_IN_IMAGE;
        setMeasuredDimension(resolveSizeAndState(imageWidth * 3 ,widthMeasureSpec , childStatue) ,
                resolveSizeAndState(maxHeight ,heightMeasureSpec , childStatue));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int mLeft = imageWidth + MARGIN_IN_IMAGE;
        int mTop = imageWidth + MARGIN_IN_IMAGE;
        for (int i = 0 ; i < showedNum ; i++){
            int y = i / 3 ;
            int x = i % 3;
            getChildAt(i).layout(mLeft*x , mTop*y , mLeft*x + imageWidth , mTop*y + imageWidth);
        }
    }

    /**
     * 得到每一个item view的宽高 子类可重写这个方法自动以不同的宽高
     * @param maxWidth AutoGridLayout 的最大宽度
     * @param willShowItemViewNum 将要显示的item view的数量
     * @return item view的宽
     */
    public int calculateImageWidthByShowedNum(int maxWidth , int willShowItemViewNum ) {
        if (willShowItemViewNum == 2){
            return  (maxWidth - MARGIN_IN_IMAGE) /2;
        }
        if (willShowItemViewNum == 1){
            return  maxWidth *2/3;
        }
        return  (maxWidth - MARGIN_IN_IMAGE*2) /3;
    }

    /**
     * 宽高的百分比 子类可重写改变宽高比
     * @return  宽高的百分比
     */
    public float getHeightRatio(int willShowItemViewNum){
        return 1;
    }

    public void setItemClickListener(ItemClickListener l){
        this.itemClickListener = l;
    }
    public interface ItemClickListener{
        void onItemClick(View view ,int position);
    }
}
