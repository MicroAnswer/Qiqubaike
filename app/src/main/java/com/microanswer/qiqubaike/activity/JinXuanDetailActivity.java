package com.microanswer.qiqubaike.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.microanswer.qiqubaike.R;
import com.microanswer.qiqubaike.api.QiquApi;
import com.microanswer.qiqubaike.bean.JinXuanItem;
import com.microanswer.qiqubaike.other.Fun;
import com.microanswer.qiqubaike.viewholder.ItemHolder;
import com.microanswer.qiqubaike.viewholder.ViewLoadingHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 此界面为了实现
 * Created by Micro on 2017-10-25.
 */

public class JinXuanDetailActivity extends BaseActivity implements ViewLoadingHolder.OnShouldLoad {
    private final String TYPE = "epyt";
    private final String DATA = "atad";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Adpt adapter;

    // 当前要显示的内容id
    private String jinXuanItemId;
    private ArrayList<Map<String, Object>> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jinxuan_detail);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        data = new ArrayList<>();

        // 初始化数据只有一个，这一个就是命令加载的数据
        HashMap<String, Object> loading = new HashMap<>();
        loading.put(TYPE, "loading");
        loading.put(DATA, "loading");
        data.add(loading);

        adapter = new Adpt();
    }

    private boolean isLoading = false; // 是否正在加载内容
    private boolean isLoadingPL = false; // 是否真正在加载评论
    @Override
    public void onLoad() {

        if(isLoading || isLoadingPL) {
            return;
        }

        // 获取传入到该页面的内容id
        Intent intent = getIntent();
        jinXuanItemId = intent.getStringExtra("id");

        // 内容和评论分开同时加载

        if (!isLoading) {
            isLoading = true;
            QiquApi.getJinXuanContent(jinXuanItemId).then(new Fun() {
                @Override
                public Object d0(Object obj) throws Throwable {

                    isLoading = false;
                    return null;
                }
            }).promise();
        }

        if (!isLoadingPL) {
            isLoadingPL = true;
            QiquApi.getJinXuanPinLun(jinXuanItemId).then(new Fun() {
                @Override
                public Object d0(Object obj) throws Throwable {

                    isLoadingPL = false;
                    return null;
                }
            }).promise();
        }

    }

    private class Adpt extends RecyclerView.Adapter<ItemHolder> {

        private LayoutInflater layoutInflater;

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (null == layoutInflater) {
                layoutInflater = LayoutInflater.from(parent.getContext());
            }

            View v = layoutInflater.inflate(viewType, parent, false);
            ItemHolder itemViewHolder = null;

            if (viewType == R.layout.view_loading) {
                ViewLoadingHolder vlh = new ViewLoadingHolder(v);
                vlh.setOnShouldLoad(JinXuanDetailActivity.this);
                itemViewHolder = vlh;
            }

            return itemViewHolder.init(parent.getContext());
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.bind(position, null);
        }

        @Override
        public int getItemViewType(int position) {
            Map<String, Object> stringObjectMap = data.get(position);
            Object o = stringObjectMap.get(TYPE);

            if ("loading".equals(o)) {
                return R.layout.view_loading;
            } else if ("datacontent".equals(o)) {

                // JinXuanItem item = items.get(position - 1);
                // if (item.getItem_type() == 0) {
                //     // 图片内容【可能是静态图，可能是gif】
                //     List<JinXuanItem.Images> images = item.getImages();
                //     if (images.size() >= 1) {
                //         JinXuanItem.Images images1 = images.get(0);
                //         if ("GIF".equals(images1.getType()) || "gif".equals(images1.getType())) {
                //             return R.layout.jinxuan_item_gif;
                //         } else {
                //             return R.layout.jinxuan_item_image;
                //         }
                //     } else {
                //         // 这条内容，是图片内容，可是却没有图片
                //         return R.layout.jinxuan_item_image;
                //     }
                // } else if (item.getItem_type() == 1) {
                //     return R.layout.jinxuan_item_text;
                // }
            }
            return 0;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}
