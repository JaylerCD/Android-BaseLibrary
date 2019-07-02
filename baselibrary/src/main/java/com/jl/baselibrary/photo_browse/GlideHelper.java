package com.jl.baselibrary.photo_browse;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.jl.baselibrary.glide.GlideUtils;

/**
 * Created by Kevin on 2017/8/22.
 */

public final class GlideHelper {
    public static void load(Context context, Object url, ImageView imageView){
        GlideUtils.loadImage(context, url, imageView);
    }
    public static void load(Activity activity,Object url,ImageView imageView){
        load(activity,url,imageView,false);
    }
    public static void load(final Activity activity, Object url, ImageView imageView, boolean isClickFinish){
        GlideUtils.loadImage(activity, url, imageView);
        if(isClickFinish){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }
    }
}