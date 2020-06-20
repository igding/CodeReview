package com.baidu.codereview.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.codereview.Constants;
import com.baidu.codereview.R;
import com.baidu.codereview.bean.SpBean;
import com.baidu.codereview.ipconfig.Api;
import com.baidu.codereview.ipconfig.OkhttpUtil;
import com.baidu.codereview.uitls.DownloadUtil;
import com.baidu.codereview.uitls.GlideUtil;
import com.baidu.codereview.uitls.SPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private ImageView mImgSplash;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            file = (File) msg.obj;
            SPUtils.getInstance().put(SpBean.File, file.getAbsolutePath());
            GlideUtil.loadFile(SplashActivity.this, file, mImgSplash);
        }
    };

    private File file;

    @Override
    protected void initData() {
        mImgSplash = findViewById(R.id.img_splash);
        initPremission();
        postDelayed();
    }

    private void initPremission() {
        int i = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[0]);
        int j = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[1]);
        if (i != PackageManager.PERMISSION_GRANTED || j != PackageManager.PERMISSION_GRANTED) {
            //        // 如果没有授予该权限，就去提示用户请求
            startRequestPermission();
        } else {
            File downloadFile = new File(getExternalFilesDir(null) + File.separator + getPackageName());
            if (!downloadFile.exists()) {
                Log.i(TAG, "initPremission:" + downloadFile.getName());
                boolean mkdirs = downloadFile.mkdirs();
                Log.i(TAG, "onRequestPermissionsResult:" + mkdirs);
            }
        }

    }

    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, Constants.resultCode);
    }

    private void postDelayed() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String fileName = SPUtils.getInstance().getString(SpBean.File);
                if (!TextUtils.isEmpty(fileName)) {
                    File file = new File(fileName);
                    GlideUtil.loadFile(SplashActivity.this, file, mImgSplash);
                    goToActivity();
                    return;
                }
                downLoad();
            }
        }, 3000);
    }

    private void downLoad() {
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
                success(response);
            }
        });
    }

    private void success(Response response) throws IOException {
        String string = response.body().string();
        Log.i(TAG, "onResponse:" + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            final String imagePath = (String) jsonObject1.get("imagePath");
            doSomeThings(imagePath);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doSomeThings(final String imagePath) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadBitMap(imagePath);
                goToActivity();
            }
        });
    }

    private void goToActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void loadBitMap(final String imagePath) {
        //下载url
        DownloadUtil.get(this).download(imagePath, getApplication().getPackageName(), new DownloadUtil.OnDownloadListener() {

            @Override
            public void onDownloadSuccess(File nameFromUrl) {
                //子线程
                Message message = new Message();
                message.obj = nameFromUrl;
                message.what = 0;
                mHandler.sendMessage(message);
            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed() {

            }
        });
    }

    @Override
    protected int getView() {
        return R.layout.activity_splash;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.resultCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //如果没有获取权限，那么可以提示用户去设置界面--->应用权限开启权限
                } else {
                    //获取权限成功提示
                    Toast toast = Toast.makeText(this, "获取权限成功", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        }
    }
}
