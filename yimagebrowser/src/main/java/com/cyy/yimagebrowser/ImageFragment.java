package com.cyy.yimagebrowser;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyy.yimagebrowser.photodraweeview.OnPhotoTapListener;
import com.cyy.yimagebrowser.photodraweeview.OnViewTapListener;
import com.cyy.yimagebrowser.photodraweeview.PhotoDraweeView;
import com.cyy.yimagebrowser.utils.CacheExistUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by cyy on 2016/6/12.
 *
 *
 */
public class ImageFragment extends Fragment {

    public static final String ARG_URL = "ImageFragment.ARG_URL";
    public static final String ARG_HOLDERURL = "ImageFragment.ARG_HOLDERURL";
    private PhotoDraweeView photoDraweeView;

    /**
     * 要显示的图片的地址
     */
    private String urlStr;
    private String holderUri;

    private OnPhotoTapListener photoTapListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //---拿到参数
        urlStr = getArguments()!=null?getArguments().getString(ARG_URL):"";
        holderUri = getArguments()!=null ? getArguments().getString(ARG_HOLDERURL):"";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_item, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photoDraweeView = (PhotoDraweeView) view.findViewById(R.id.image_item);
        GenericDraweeHierarchy draweeHierarchy =  photoDraweeView.getHierarchy();
        draweeHierarchy.setProgressBarImage(new YProgressDrawable());
        ///拿到本地缓存的小图的缓存图片做为展位图
        setThumbImageAsPlaceHolder();
        //---设置图片缩放
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(urlStr))
                .setResizeOptions(new ResizeOptions(dm.widthPixels,dm.heightPixels))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco
                .newDraweeControllerBuilder()
                .setOldController(photoDraweeView.getController())
                .setImageRequest(request)
                .setControllerListener(new BaseControllerListener<ImageInfo>(){
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (imageInfo == null) {
                            return;
                        }
                        photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        super.onFailure(id, throwable);
                    }
                })
                .build();
        photoDraweeView.setController(controller);
        if (photoTapListener!=null)photoDraweeView.setOnPhotoTapListener(photoTapListener);
    }
    ///图片点击事件
    public void setOnPhotoTapListener(OnPhotoTapListener listener){
        this.photoTapListener= listener;
    }

    private void setThumbImageAsPlaceHolder(){
        CacheExistUtils cacheExistUtils = new CacheExistUtils(){
            @Override
            public void onDisk(final Drawable d) {
                photoDraweeView.post(new Runnable() {
                    @Override
                    public void run() {
                        GenericDraweeHierarchy draweeHierarchy =  photoDraweeView.getHierarchy();
                        draweeHierarchy.setPlaceholderImage( d , ScalingUtils.ScaleType.FIT_CENTER);
                    }
                }) ;
            }
            @Override
            public void notOnDisk() {}
        };
        cacheExistUtils.isCacheInDisk(holderUri);
    }

    /**
     * 初始化fragment
     * @param url 该fragment 要显示的图片
     * @return 新的实例
     */
    public static ImageFragment newInstance(String url , String holderUri) {
        Bundle args = new Bundle();
        args.putString(ARG_URL , url);
        args.putString(ARG_HOLDERURL , holderUri);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
