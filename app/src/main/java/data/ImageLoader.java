package data;//package com.example.testimageloader;

import android.content.Context;
import android.widget.ImageView;

import com.java.dac.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class ImageLoader {

    public static void init(Context context) {
        mImageLoader.init(ImageLoaderConfiguration.createDefault(context));
        // FIXME add showImage*()
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(null)
                .showImageForEmptyUri(R.drawable.logo_196)
                .showImageOnFail(R.drawable.logo_196)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .build();
    }

    public static void displayImage(String imageUrl, ImageView imageView) {
        mImageLoader.displayImage(imageUrl, imageView, mDisplayImageOptions);
    }

    public static void clearDiskCache() {
        mImageLoader.clearDiskCache();
    }
    public static void cancelDisplayTask(ImageView v) {
        mImageLoader.cancelDisplayTask(v);
    }
    private static com.nostra13.universalimageloader.core.ImageLoader
            mImageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
    private static DisplayImageOptions mDisplayImageOptions;
}
