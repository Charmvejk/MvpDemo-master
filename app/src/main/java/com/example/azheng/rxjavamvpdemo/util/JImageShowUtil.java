package com.example.azheng.rxjavamvpdemo.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
 import com.example.azheng.rxjavamvpdemo.R;
import com.example.azheng.rxjavamvpdemo.jApp;

import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * Created by jincancan on 16/9/11.
 * 图片加载封装类 默认CENTER_CROP
 */
public class JImageShowUtil {

    private static final String TAG = "TLImageShowUtil";


    public static void displayImage(final String imgUrl,
                                    final ImageView imageView,
                                    final long uin) {
        if (!TextUtils.isEmpty(imgUrl)) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
//                    .placeholder(R.mipmap.ic_launcher)
//                    .error(R.mipmap.ic_launcher)
                    .priority(Priority.NORMAL);
            Glide.with(jApp.getIns())
                    .load(imgUrl)
                    .apply(options)
                    .into(imageView);

        }
    }

    public static void displayImage(final String imgUrl,
                                    final ImageView imageView,
                                    @DrawableRes int placeId,
                                    @DrawableRes int errorId,
                                    RequestListener<Drawable> callback) {
        if (TextUtils.isEmpty(imgUrl)) {
            imageView.setImageResource(placeId);
        } else {
            RequestOptions options = new RequestOptions()
//                    .centerCrop()
                    .placeholder(placeId)
                    .error(errorId)
                    .priority(Priority.NORMAL);
            Glide.with(jApp.getIns())
                    .load(imgUrl)
                    .apply(options)
                    .listener(callback)
                    .into(imageView);
        }
    }

    public static void displayImage(final String imgUrl, final ImageView imageView) {
        if (!TextUtils.isEmpty(imgUrl)) {

            RequestOptions options = new RequestOptions()
                    .centerCrop()
//                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.no_avatar).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .priority(Priority.NORMAL);
            Glide.with(jApp.getIns())
                    .load(imgUrl)
                    .apply(options)
//                    .listener(callback)
                    .into(imageView);

        }
    }

    /***
     *
     * @param imgUrl 图片链接
     * @param imageView view
     * @param userImageScale 是否使用imageview的scaleType
     */
    public static void displayImage(final String imgUrl, final ImageView imageView, boolean userImageScale) {
        if (!TextUtils.isEmpty(imgUrl)) {
            RequestOptions options = new RequestOptions()
                    .priority(Priority.NORMAL);

            if (!userImageScale) {
                options.centerCrop();
            }
            RequestOptions options1 = new RequestOptions()
                    .centerCrop()
//                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.no_avatar).skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .priority(Priority.NORMAL);

            Glide.with(jApp.getIns())

                    .load(imgUrl)
                    .apply(options1)
                    .into(imageView);
        }
    }

    public static void displayImage(final Object imgUrl, final ImageView imageView) {
        if (imgUrl == null) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .priority(Priority.NORMAL);
        Glide.with(jApp.getIns())
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }

    public static void displayImage(final String imgUrl, final ImageView imageView, int size) {
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(jApp.getIns())
                    .load(imgUrl)
                    .into(imageView);
        }
    }

    public static void displayImage(final String imgUrl, final ImageView imageView, final RequestListener<Drawable> callback) {
        if (!TextUtils.isEmpty(imgUrl)) {
            RequestOptions options = new RequestOptions()
                    .priority(Priority.NORMAL);
            Glide.with(jApp.getIns())
                    .load(imgUrl)
                    .apply(options)
                    .listener(callback)
                    .into(imageView);
        }
    }


    /**
     * 预加载网络图片.
     *
     * @param imgUrl
     */
    public static void preLoadImage(final String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(jApp.getIns())
                    .load(imgUrl)
                    .preload();
        }
    }

    public static void displayImageBlur(String imgUrl, ImageView view) {
        if (!TextUtils.isEmpty(imgUrl)) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .bitmapTransform(new BlurTransformation(25, 4))
                    .priority(Priority.NORMAL);
//            options.bitmapTransform()
            // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
//            options.bitmapTransform(new BlurTransformation(25, 4));

            Glide.with(jApp.getIns())
                    .load(imgUrl)
                    .apply(options)
                    .into(view);
        }
    }

    public static void displayImageBlur(String imgUrl, Target target) {
        if (!TextUtils.isEmpty(imgUrl)) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .priority(Priority.NORMAL);
//            options.bitmapTransform()
            // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
            options.bitmapTransform(new BlurTransformation(23, 4));

            Glide.with(jApp.getIns())
                    .load(imgUrl)
                    .apply(options)
                    .into(target);
        }
    }

    public static void saveImage(String url, SimpleTarget<Drawable> simpleTarget) {
        Glide.with(jApp.getIns()).load(url).into(simpleTarget);
    }

    public static void displayImageScale(final String imgUrl, final String smallImgUrl, final ImageView imageView, final RequestListener<Drawable> callback, Context context, final LoadingDailog dialog) {
        if (!TextUtils.isEmpty(imgUrl)) {
            RequestOptions options = new RequestOptions()
                    .priority(Priority.NORMAL)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);

            RequestBuilder<Drawable> thumbnailRequest = Glide
                    .with(jApp.getIns())
                    .load(smallImgUrl);
//
//            Glide.with(JApp.getIns())
//                    .load(imgUrl)
//                    .apply(options)
//                    .thumbnail(thumbnailRequest)
//                    .listener(callback)
//                    .into(imageView);


            Glide.with(jApp.getIns()).load(imgUrl).
                    thumbnail(thumbnailRequest)
                    .listener(callback)
                    .apply(options).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(final Drawable resource, Transition<? super Drawable> transition) {
                    //加载完成后的处理

                    dialog.dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageDrawable(resource);
                        }
                    }, 300);

                }
            });


        }


    }


    public static void displayImageScale(final int imgResource, final String smallImgUrl, final ImageView imageView, final RequestListener<Drawable> callback) {
        if (imgResource != 0) {
            RequestOptions options = new RequestOptions()
                    .priority(Priority.NORMAL)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);

            RequestBuilder<Drawable> thumbnailRequest = Glide
                    .with(jApp.getIns())
                    .load(smallImgUrl);

            Glide.with(jApp.getIns())
                    .load(imgResource)
                    .apply(options)
                    .thumbnail(thumbnailRequest)
                    .listener(callback)
                    .into(imageView);
        }
    }
}
