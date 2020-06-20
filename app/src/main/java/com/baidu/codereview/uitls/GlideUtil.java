package com.baidu.codereview.uitls;

import android.content.Context;
import android.widget.ImageView;

import com.baidu.codereview.R;
import com.bumptech.glide.Glide;

import java.io.File;

/**
 * <pre>
 *     author : handler
 *     e-mail : dingchao314@gmail.com
 *     time   : 2020/06/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GlideUtil {

    public static void loadBitmap(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.mipmap.ic_launcher_round).into(imageView);
    }

    public static void loadFile(Context context, File file, ImageView imageView) {
        Glide.with(context).load(file).into(imageView);
    }

}
