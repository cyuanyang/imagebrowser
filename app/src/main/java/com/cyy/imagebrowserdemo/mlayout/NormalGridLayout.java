package com.cyy.imagebrowserdemo.mlayout;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;

import com.cyy.imagebrowserdemo.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by cyy on 2016/6/24.
 *
 */
public class NormalGridLayout extends AutoGridLayout {
    public NormalGridLayout(Context context) {
        super(context);
    }

    public NormalGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NormalGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getItemViewLayout() {
        return R.layout.item_view;
    }

    @Override
    public void setDataForEveryItemView(View itemView , String uriStr) {
        super.setDataForEveryItemView(itemView , uriStr);
        SimpleDraweeView simpleDraweeView =
                (SimpleDraweeView) itemView.findViewById(R.id.simpleDraweeView);
        simpleDraweeView.setImageURI(Uri.parse(uriStr));
    }
}
