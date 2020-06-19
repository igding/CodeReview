package com.baidu.codereview.activity;

import android.webkit.WebView;

import com.baidu.codereview.R;
import com.baidu.codereview.bean.IntentBean;

/**
 * <pre>
 *     author : handler
 *     e-mail : dingchao314@gmail.com
 *     time   : 2020/06/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class H5Activity extends BaseActivity {

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra(IntentBean.LINK);
        WebView mWebView = findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
    }

    @Override
    protected int getView() {
        return R.layout.activity_h5;
    }
}
