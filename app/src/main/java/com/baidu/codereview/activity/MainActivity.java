package com.baidu.codereview.activity;

import android.util.Log;

import com.baidu.codereview.R;
import com.baidu.codereview.adapter.MainAdadpter;
import com.baidu.codereview.bean.MainBean;
import com.baidu.codereview.ipconfig.Api;
import com.baidu.codereview.ipconfig.OkhttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void initData() {
        final RecyclerView mRvMain = findViewById(R.id.rv_main);

        mRvMain.setLayoutManager(new LinearLayoutManager(this));

        Request request = new Request.Builder()
                .url(Api.main)
                .get()//默认就是GET请求，可以不写
                .build();
        OkhttpUtil.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject data = (JSONObject) jsonObject.get("data");
                    JSONArray datas = (JSONArray) data.get("datas");
                    final List<MainBean> mainBeans = new ArrayList<>();
                    for (int i = 0; i < datas.length(); i++) {
                        JSONObject jsonObject1 = datas.getJSONObject(i);
                        String link = (String) jsonObject1.get("link");
                        String title = (String) jsonObject1.get("title");
                        MainBean mainBean = new MainBean();
                        mainBean.setLink(link);
                        mainBean.setTitle(title);
                        mainBeans.add(mainBean);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRvMain.setAdapter(new MainAdadpter(MainActivity.this, mainBeans));
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "onResponse:" + string);
            }
        });
    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }
}
