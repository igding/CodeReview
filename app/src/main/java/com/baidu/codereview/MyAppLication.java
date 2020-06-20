package com.baidu.codereview;

import android.app.Application;

import com.baidu.codereview.uitls.ContextUtils;

/**
 * <pre>
 *     author : handler
 *     e-mail : dingchao314@gmail.com
 *     time   : 2020/06/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MyAppLication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtils.init(this);
    }
}
