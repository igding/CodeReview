package com.baidu.codereview.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.codereview.R;
import com.baidu.codereview.activity.H5Activity;
import com.baidu.codereview.bean.IntentBean;
import com.baidu.codereview.bean.MainBean;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author : handler
 *     e-mail : dingchao314@gmail.com
 *     time   : 2020/06/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MainAdadpter extends RecyclerView.Adapter<MainAdadpter.RecyclerHolder> {

    Context mContext;
    List<MainBean> datas;

    public MainAdadpter(Context context, List<MainBean> data) {
        mContext = context;
        datas = data;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        final MainBean mainBean = datas.get(position);
        holder.mTvContent.setText(mainBean.getTitle());
        holder.mTvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, H5Activity.class);
                intent.putExtra(IntentBean.LINK,mainBean.getLink());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        private final TextView mTvContent;

        public RecyclerHolder(View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
