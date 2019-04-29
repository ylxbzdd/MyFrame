package com.example.administrator.myframe.utils;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Created by admin on 2017/11/3.
 */

public class PicassoImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        AppUtils.displayImage(context,imageView,(String) path);
//        Picasso.with(context).load(path + "").into(imageView);
    }
}
