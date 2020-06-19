package com.baidu.codereview.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * <pre>
 *     author : handler
 *     e-mail : dingchao314@gmail.com
 *     time   : 2020/06/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        initData();
    }

    protected abstract void initData();

    protected abstract int getView();

}
