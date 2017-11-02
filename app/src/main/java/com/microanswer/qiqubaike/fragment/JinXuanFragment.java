package com.microanswer.qiqubaike.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.microanswer.qiqubaike.R;
import com.microanswer.qiqubaike.api.QiquApi;
import com.microanswer.qiqubaike.bean.JinXuanItem;
import com.microanswer.qiqubaike.other.Fun;
import com.microanswer.qiqubaike.viewholder.BannerHolder;
import com.microanswer.qiqubaike.viewholder.ItemHolder;
import com.microanswer.qiqubaike.viewholder.ItemHolderGif;
import com.microanswer.qiqubaike.viewholder.ItemHolderImage;
import com.microanswer.qiqubaike.viewholder.ViewLoadingHolder;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Micro on 2017-10-3.
 */

public class JinXuanFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
        , ViewLoadingHolder.OnShouldLoad {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecAdapter adapter;
    private SwipeRefreshLayout swiperefreshlayout;

    private String recoid = "";
    private List<JinXuanItem> items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_jin_xuan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setOnRefreshListener(this);

        linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        adapter = new RecAdapter();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private boolean isLoading = false;

    private void loadData(final String recoid) {
        if (!isLoading) {
            isLoading = true;
            QiquApi.getJinXuanContent(recoid).then(new Fun() {
                @Override
                public Object d0(Object obj) throws Throwable {
                    HashMap<String, Object> data = (HashMap<String, Object>) obj;
                    // String result = data.get("orign").toString();
                    if (items == null || TextUtils.isEmpty(recoid)) {
                        items = (List<JinXuanItem>) data.get("data");
                        adapter.notifyDataSetChanged();
                    } else {
                        List<JinXuanItem> data1 = (List<JinXuanItem>) data.get("data");
                        int size = items.size();
                        items.addAll(data1);
                        adapter.notifyItemRangeChanged(size, data1.size());
                    }
                    JinXuanFragment.this.recoid = data.get("recoid").toString();
                    swiperefreshlayout.setRefreshing(false);
                    isLoading = false;
                    // AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    //         .setMessage(result).setPositiveButton("确定", null).create();
                    // alertDialog.show();
                    // Log.i("JinXuanItem", result);
                    return null;
                }
            }).promise();
        }
    }

    @Override
    public void onRefresh() {
        recoid = "";
        swiperefreshlayout.setRefreshing(true);
        loadData(recoid);
    }

    @Override
    public void onLoad() {
        loadData(recoid);
    }

    class RecAdapter extends RecyclerView.Adapter<ItemHolder> {

        private LayoutInflater inflater;

        @Override
        public void onViewRecycled(ItemHolder holder) {
            super.onViewRecycled(holder);
            holder.recycled();
        }

        @Override
        public int getItemViewType(int position) {
            if (0 == position) {
                // banner 视图始终显示
                return R.layout.jinxuan_item_banner;
            }

            if (items == null || position == items.size() + 1) {
                // 数据还没有初始化，显示加载的ProgressBar
                return R.layout.view_loading;
            }
            if (position - 1 < items.size()) {
                JinXuanItem item = items.get(position - 1);
                if (item.getItem_type() == 0) {
                    // 图片内容【可能是静态图，可能是gif】
                    List<JinXuanItem.Images> images = item.getImages();
                    if (images.size() >= 1) {
                        JinXuanItem.Images images1 = images.get(0);
                        if ("GIF".equals(images1.getType()) || "gif".equals(images1.getType())) {
                            return R.layout.jinxuan_item_gif;
                        } else {
                            return R.layout.jinxuan_item_image;
                        }
                    } else {
                        // 这条内容，是图片内容，可是却没有图片
                        return R.layout.jinxuan_item_image;
                    }
                } else if (item.getItem_type() == 1) {
                    return R.layout.jinxuan_item_text;
                }
            }
            return 0; // 不应该运行到这儿的
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (null == inflater) {
                inflater = LayoutInflater.from(parent.getContext());
            }

            ItemHolder ih = null;
            View v = inflater.inflate(viewType, parent, false);

            if (viewType == R.layout.jinxuan_item_image) {
                ih = new ItemHolderImage(v);
            } else if (viewType == R.layout.jinxuan_item_gif) {
                ih = new ItemHolderGif(v);
            } else if (viewType == R.layout.jinxuan_item_text) {
                ih = new ItemHolder(v);
            } else if (viewType == R.layout.jinxuan_item_banner) {
                ih = new BannerHolder(v);
            } else if (viewType == R.layout.view_loading) {
                ViewLoadingHolder vlh = new ViewLoadingHolder(v);
                vlh.setOnShouldLoad(JinXuanFragment.this);
                ih = vlh;
            }

            return ih.init(parent.getContext());
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {

            if (items != null && items.size() > 0 && position > 0 && position - 1 < items.size()) {
                int dataPosition = position - 1;
                JinXuanItem jinXuanItem = items.get(dataPosition);
                holder.bind(position, jinXuanItem);
            } else {
                holder.bind(position, null);
            }
        }

        @Override
        public int getItemCount() {
            if (null == items || items.size() <= 0) {
                return 2;
            }
            if (items.size() > 0) {
                return items.size() + 2;
            }
            return 1; // 不应该运行到这
        }
    }

}
