package com.microanswer.qiqubaike.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.microanswer.qiqubaike.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 此界面为了实现
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

    private class Adpt extends RecyclerView.Adapter<ItemViewHolder> {

        private LayoutInflater layoutInflater;

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (null == layoutInflater) {
                layoutInflater = LayoutInflater.from(parent.getContext());
            }

            View v = layoutInflater.inflate(viewType, parent, false);
            ItemViewHolder itemViewHolder = null;

            if (viewType == R.layout.view_loading) {
                itemViewHolder = new LoadingHolder(v);
            }

            return itemViewHolder.init(parent.getContext());
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.bind(position);
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


    private abstract class ItemViewHolder extends RecyclerView.ViewHolder {
        protected Context context;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        protected View findViewById(int id) {
            return itemView.findViewById(id);
        }

        ItemViewHolder init(Context context) {
            this.context = context;
            return this;
        }

        abstract ItemViewHolder bind(int position);

        abstract ItemViewHolder recycled();
    }

    /**
     * 小贱军疯狂加载中...
     */
    private class LoadingHolder extends ItemViewHolder {
        private ImageView load_view;

        public LoadingHolder(View itemView) {
            super(itemView);
        }

        @Override
        LoadingHolder init(Context context) {
            super.init(context);
            load_view = (ImageView) findViewById(R.id.load_view);
            return this;
        }

        @Override
        LoadingHolder bind(int position) {
            Glide.with(context).load(R.drawable.uc_loading)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .into(load_view);
            return this;
        }

        @Override
        LoadingHolder recycled() {
            return this;
        }
    }

}
