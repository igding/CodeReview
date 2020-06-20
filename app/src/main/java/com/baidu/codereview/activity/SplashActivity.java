package com.baidu.codereview.activity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.baidu.codereview.R;
import com.baidu.codereview.bean.SPBean;
import com.baidu.codereview.ipconfig.Api;
import com.baidu.codereview.ipconfig.OkhttpUtil;
import com.baidu.codereview.uitls.GlideUtil;
import com.baidu.codereview.uitls.SPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     author : handler
 *     e-mail : dingchao314@gmail.com
 *     time   : 2020/06/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void initData() {
        final ImageView mImgSplash = findViewById(R.id.img_splash);
        //网络请求，获取图片地址
        initImage(mImgSplash);
        postDelayed(mImgSplash);
    }

    private void postDelayed(final ImageView mImgSplash) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                downLoad(mImgSplash);

            }
        }, 3000);
    }

    private void downLoad(final ImageView mImgSplash) {
        Request request = new Request.Builder()
                .url(Api.banner)
                .get()//默认就是GET请求，可以不写
                .build();
        OkhttpUtil.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                success(response, mImgSplash);
            }
        });
    }

    private void success(Response response, ImageView mImgSplash) throws IOException {
        String string = response.body().string();
        Log.i(TAG, "onResponse:" + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            final String imagePath = (String) jsonObject1.get("imagePath");
            doSomeThings(imagePath, mImgSplash);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doSomeThings(final String imagePath, final ImageView mImgSplash) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadBitMap(imagePath, mImgSplash);
                goToActivity();
            }
        });
    }

    private void goToActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void loadBitMap(String imagePath, ImageView mImgSplash) {
        SPUtils.getInstance().put(SPBean.IMAGE_PATH, imagePath);
        GlideUtil.loadBitmap(SplashActivity.this, imagePath, mImgSplash);
    }

    private void initImage(ImageView mImgSplash) {
        String imagePath = SPUtils.getInstance().getString(SPBean.IMAGE_PATH);
        if (imagePath != null) {
            GlideUtil.loadBitmap(SplashActivity.this, imagePath, mImgSplash);
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_splash;
    }

}
