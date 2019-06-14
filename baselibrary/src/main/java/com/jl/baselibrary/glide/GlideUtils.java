package com.jl.baselibrary.glide;

import android.content.Context;
import android.widget.ImageView;

import com.jl.baselibrary.R;
import com.jl.baselibrary.glide.transform.GlideCircleTransform;
import com.jl.baselibrary.glide.transform.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class GlideUtils {

    /**
     * 加载图片(默认)
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.common_white)
                .error(R.color.common_white)
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }


    /**
     * 加载圆形图片
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(R.color.common_white)
                .error(R.color.common_white)
                //.priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform());
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆角图片
     */
    public static void loadRoundCircleImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(R.color.common_white)
                .error(R.color.common_white)
                //.priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideRoundTransform(20));
        Glide.with(context).load(url).apply(options).into(imageView);

    }

    /**
     * 加载圆角图片-指定任意部分圆角（图片上、下、左、右四个角度任意定义）
     */
    public static void loadCustRoundCircleImage(Context context, String url, ImageView imageView, RoundedCornersTransformation.CornerType type) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.common_white)
                .error(R.color.common_white)
                //.priority(Priority.HIGH)
                .transform(new RoundedCornersTransformation(45, 0, type))
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载模糊图片（自定义透明度）
     */
    public static void loadBlurImage(Context context, String url, ImageView imageView, int blur) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.common_white)
                .error(R.color.common_white)
                //.priority(Priority.HIGH)
                .transform(new BlurTransformation(blur))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载灰度(黑白)图片（自定义透明度）
     */
    public static void loadBlackImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.common_white)
                .error(R.color.common_white)

                //.priority(Priority.HIGH)
                .transform(new GrayscaleTransformation())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

}