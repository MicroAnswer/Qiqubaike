package com.microanswer.qiqubaike.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.microanswer.qiqubaike.R;
import com.microanswer.qiqubaike.bean.JinXuanItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Micro on 2017-10-25.
 */

public class JinXuanDetailActivity extends BaseActivity {
    private final String TYPE = "epyt";
    private final String DATA = "atad";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Adpt adapter;
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

    private class Adpt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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
