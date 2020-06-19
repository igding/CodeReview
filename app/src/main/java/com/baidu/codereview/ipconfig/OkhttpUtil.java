package com.baidu.codereview.ipconfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * <pre>
 *     author : handler
 *     e-mail : dingchao314@gmail.com
 *     time   : 2020/06/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class OkhttpUtil {

    private static OkHttpClient okHttpClient;

    public static OkHttpClient getInstance() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);
        okHttpClient = builder.build();

//        okHttpClient = builder.build();
        return okHttpClient;
    }

    private OkhttpUtil() {
    }
}
