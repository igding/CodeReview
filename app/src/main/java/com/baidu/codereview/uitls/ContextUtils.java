package com.baidu.codereview.uitls;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @author dc on 2017/11/21.
 * @describe  在application的时候，调用contextUtils来
 */

public class ContextUtils {

    private static Context context;

    private ContextUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull Context context) {
        ContextUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("u should init first");
    }
}
