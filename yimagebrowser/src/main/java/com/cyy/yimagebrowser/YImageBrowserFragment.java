package com.cyy.yimagebrowser;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import com.cyy.yimagebrowser.photodraweeview.MultiTouchViewPager;
import com.cyy.yimagebrowser.photodraweeview.OnPhotoTapListener;
import com.cyy.yimagebrowser.views.BaseNavigationBar;
import com.cyy.yimagebrowser.views.NormalNavigationBar;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyy on 2016/6/12.
 *
 */
public class YImageBrowserFragment extends Fragment implements
        ViewPager.OnPageChangeListener , OnPhotoTapListener{

    protected SimpleDraweeView scaleDraweeView;
    protected FrameLayout scaleDraweeViewLayout;
    protected FrameLayout navigationBarContainer;
    protected FrameLayout bottomInfoLayout;
    protected View backgroundView;
    private MultiTouchViewPager viewPager;
    private BaseNavigationBar navigationBar; //navi

    private List<Fragment> fragments = new ArrayList<>();

    private String[] holderUri;
    String urlStr[];
    private int pos; //图片所在数组中的位置
    private int currentPosition;//当前位置

    private int[] location;
    private int[] size;
    private String currentImageUri;

    private YImageBrowserDelegate delegate; /// 动画代理
    private int isShowNaviBar; //1>show 2>hiden 3>doing

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlStr = getArguments() != null ? (String[]) getArguments().getCharSequenceArray("urls") : null;
        holderUri = getArguments() != null ? (String[]) getArguments().getCharSequenceArray("holderUri") : null;
        pos = getArguments() != null ? getArguments().getInt("pos") : 0;
        location = getArguments() != null ? getArguments().getIntArray("location") : null;
        size = getArguments() != null ? getArguments().getIntArray("size") : null;
        currentImageUri = getArguments() != null ? getArguments().getString("currentImageUri") : "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_view, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        for (int i = 0; i < urlStr.length; i++) {
            ImageFragment fragment = ImageFragment.newInstance(urlStr[i], holderUri[i]);
            fragment.setOnPhotoTapListener(this);
            fragments.add(fragment);
        }
        viewPager = (MultiTouchViewPager) view.findViewById(R.id.view_pager);
        MyAdapter adapter = new MyAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
        viewPager.addOnPageChangeListener(this);
        currentPosition = pos;

        //缩放动画
        scaleDraweeView.setImageURI(Uri.parse(currentImageUri));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) scaleDraweeView.getLayoutParams();
        lp.leftMargin = location[0];
        lp.topMargin = location[1];
        lp.width = size[0];
        lp.height = size[1];

        scaleDraweeView.post(new Runnable() {
            @Override
            public void run() {
                proformAnim();
            }
        });
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    /**
     * 执行动画
     */
    private void proformAnim() {
        Rect layoutRect = new Rect();
        scaleDraweeViewLayout.getLocalVisibleRect(layoutRect);

        PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofInt("width", size[0], layoutRect.right);
        PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofInt("height", size[1], layoutRect.bottom);
        PropertyValuesHolder leftHolder = PropertyValuesHolder.ofInt("left", location[0], 0);
        PropertyValuesHolder topHolder = PropertyValuesHolder.ofInt("top", location[1], 0);
        PropertyValuesHolder middleHolder = PropertyValuesHolder.ofInt("middle", 1, 100);

        //背景变化
        PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofInt("alpha" , 0 ,100);

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setValues(scaleXHolder, scaleYHolder, leftHolder, topHolder, middleHolder , alphaHolder);
        valueAnimator.setDuration(YAttach.DURRING);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) scaleDraweeView.getLayoutParams();
                lp.width = (int) animation.getAnimatedValue("width");
                lp.height = (int) animation.getAnimatedValue("height");
                lp.leftMargin = (int) animation.getAnimatedValue("left");
                lp.topMargin = (int) animation.getAnimatedValue("top");
                scaleDraweeView.requestLayout();

                float alpha = (int)animation.getAnimatedValue("alpha")/100.0f;
                backgroundView.setAlpha(alpha);
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                if (delegate != null) delegate.animationBegin();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationCompleted();

                if (delegate != null) delegate.animationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        valueAnimator.start();
    }

    private void animationCompleted() {
        viewPager.setVisibility(View.VISIBLE);
        scaleDraweeViewLayout.setVisibility(View.GONE);
        setNavigationBar(new NormalNavigationBar(this.getContext()));
        preformShowAnim(R.anim.normal_navi_anim);
    }

    /**
     * 显示标题和信息的动画
     */
    private void preformShowAnim(final int animId) {
        Animation animation = AnimationUtils.loadAnimation(this.getContext(), animId);
        navigationBarContainer.startAnimation(animation);
        isShowNaviBar = 3;
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animId == R.anim.hide_navi_anim){
                    navigationBarContainer.setVisibility(View.GONE);
                    isShowNaviBar = 2;
                }else {
                    navigationBarContainer.setVisibility(View.VISIBLE);
                    isShowNaviBar = 1;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public static YImageBrowserFragment newInstance(String[] urls, String[] holderUrls, String currentImageUri, int pos, int[] loction, int[] size) {
        Bundle args = new Bundle();
        args.putCharSequenceArray("urls", urls);
        if (holderUrls != null) args.putCharSequenceArray("holderUri", holderUrls);
        args.putInt("pos", pos);
        args.putIntArray("location", loction);
        args.putIntArray("size", size);
        args.putString("currentImageUri", currentImageUri);
        YImageBrowserFragment fragment = new YImageBrowserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static YImageBrowserFragment newInstance(String[] urls, String currentImageUri, int pos, int[] loction, int[] size) {
        return YImageBrowserFragment.newInstance(urls, null, currentImageUri, pos, loction, size);
    }

    private void initView(View rootView) {
        scaleDraweeView = (SimpleDraweeView) rootView.findViewById(R.id.scale_draweeView);
        scaleDraweeViewLayout = (FrameLayout) rootView.findViewById(R.id.scale_draweeView_layout);
        viewPager = (MultiTouchViewPager) rootView.findViewById(R.id.view_pager);
        navigationBarContainer = (FrameLayout) rootView.findViewById(R.id.navigation_bar_container);
        bottomInfoLayout = (FrameLayout) rootView.findViewById(R.id.bottom_info_layout);
        backgroundView = (View) rootView.findViewById(R.id.background_view);
    }

    /**
     * you should specify navi bar's height
     *
     * @param bar view
     */
    public <T extends BaseNavigationBar> void setNavigationBar(T bar) {
        if (navigationBarContainer.getChildCount() > 0) {
            navigationBarContainer.removeAllViews();
        }
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        navigationBarContainer.addView(bar, lp);

        this.navigationBar = bar;
        this.navigationBar.viewPagerChanged(currentPosition , fragments.size());
    }

    /**
     * you should specify  layout height
     *
     * @param layoutId id
     */
    public void setBottomInfoLayout(int layoutId) {
        if (bottomInfoLayout.getChildCount() > 0) {
            bottomInfoLayout.removeAllViews();
        }
        View bottomView = LayoutInflater.from(this.getContext()).inflate(layoutId, null);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomInfoLayout.addView(bottomView, lp);
    }

    public void setDelegate(YImageBrowserDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        if (navigationBar!=null)
            navigationBar.viewPagerChanged(currentPosition , fragments.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPhotoTap(View view, float x, float y) {
        if (isShowNaviBar == 1 ){
            preformShowAnim(R.anim.hide_navi_anim);
        }else if (isShowNaviBar == 2){
            preformShowAnim(R.anim.normal_navi_anim);
        }
    }

    /**
     * adapter
     */
    class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
}
