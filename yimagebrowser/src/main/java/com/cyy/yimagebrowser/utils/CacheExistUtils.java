package com.cyy.yimagebrowser.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferInputStream;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by cyy on 2016/6/15.
 * 主要用于判断fresco 是否对此url在本地有缓存
 */
public abstract class CacheExistUtils {

    public void isCacheInDisk(String uriStr){
        ImageRequest request1 = ImageRequest.fromUri(Uri.parse(uriStr));
        final ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<PooledByteBuffer>>
                dataSource = imagePipeline.fetchEncodedImage(request1 , null);
        DataSubscriber dataSubscriber = new BaseDataSubscriber<CloseableReference<PooledByteBuffer>>() {
            @Override
            protected void onNewResultImpl(DataSource<CloseableReference<PooledByteBuffer>> closeableReferenceDataSource) {

                CloseableReference<PooledByteBuffer> imageReference = closeableReferenceDataSource.getResult();
                if (imageReference != null) {
                    try {
                        PooledByteBuffer image = imageReference.clone().get();
                        InputStream inputStream = new PooledByteBufferInputStream(image);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Drawable d = new BitmapDrawable(bitmap);
                        onDisk(d);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    notOnDisk();
                }
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {

            }
        };
        dataSource.subscribe(dataSubscriber , new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
                new ThreadPoolExecutor.DiscardOldestPolicy()));
    }

    abstract public void onDisk(Drawable d);
    abstract public void notOnDisk();
}
