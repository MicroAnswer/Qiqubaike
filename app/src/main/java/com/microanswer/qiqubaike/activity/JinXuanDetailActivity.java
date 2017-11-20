package com.microanswer.qiqubaike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.microanswer.qiqubaike.R;
import com.microanswer.qiqubaike.api.QiquApi;
import com.microanswer.qiqubaike.bean.JinXuanComment;
import com.microanswer.qiqubaike.bean.JinXuanItem;
import com.microanswer.qiqubaike.other.Fun;
import com.microanswer.qiqubaike.other.Tool;
import com.microanswer.qiqubaike.viewholder.ItemHolder;
import com.microanswer.qiqubaike.viewholder.ItemHolderGif;
import com.microanswer.qiqubaike.viewholder.ItemHolderImage;
import com.microanswer.qiqubaike.viewholder.PLHolder;
import com.microanswer.qiqubaike.viewholder.PLTypeHolder;
import com.microanswer.qiqubaike.viewholder.ViewLoadingHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 此界面为了实现
 * Created by Micro on 2017-10-25.
 */

public class JinXuanDetailActivity extends BaseActivity implements ViewLoadingHolder.OnShouldLoad {
    private LayoutInflater inflater;
    private LinearLayout detailContent;
    private Adpt adapter;

    // 当前要显示的内容
    private JinXuanItem jinXuanItem;
    // 评论数据集合
    private ArrayList<Object> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jinxuan_detail);
        inflater = LayoutInflater.from(this);

        detailContent = (LinearLayout) findViewById(R.id.detailContent);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new Adpt();
        recyclerView.setAdapter(adapter);
    }

    private boolean isLoaded = false; // 标记内容是否已经加载
    private boolean isPLoaded = false; // 标记评论是否已经加载
    private boolean isLoading = false; // 标记是否正在加载类容
    private boolean isPLoading = false; // 标记是否正在加载评论类容

    @Override
    public void onLoad() {

        if (isLoaded && isPLoaded) { // 内容和评论都加载了，不用再加载。
            return;
        }

        // 获取传入到该页面的内容id
        Intent intent = getIntent();
        String jinXuanItemId = intent.getStringExtra("id");

        // 内容和评论分开同时加载
        if (!isLoaded && !isLoading) { // 如果内容没有加载，则进行加载
            isLoading = true;
            QiquApi.getJinXuanDetail(jinXuanItemId).then(new Fun() {
                @Override
                public Object d0(Object obj) throws Throwable {
                    jinXuanItem = (JinXuanItem) obj;
                    isLoaded = true; // 标记已经加载了。
                    isLoading = false; // 标记加载完成了

                    // 判断Item类型， 架子啊不同的View显示
                    JinXuanItem item = jinXuanItem;
                    int viewType = R.layout.jinxuan_item_text;
                    if (item.getItem_type() == 0) {
                        // 图片内容【可能是静态图，可能是gif】
                        List<JinXuanItem.Images> images = item.getImages();
                        if (images.size() >= 1) {
                            JinXuanItem.Images images1 = images.get(0);
                            if ("GIF".equals(images1.getType()) || "gif".equals(images1.getType())) {
                                viewType = R.layout.jinxuan_item_gif;
                            } else {
                                viewType = R.layout.jinxuan_item_image;
                            }
                        } else {
                            // 这条内容，是图片内容，可是却没有图片
                            viewType = R.layout.jinxuan_item_image;
                        }
                    } else if (item.getItem_type() == 1) {
                        viewType = R.layout.jinxuan_item_text;
                    }

                    ItemHolder itemHolder = null;
                    View v = inflater.inflate(viewType, detailContent, false);
                    if (viewType == R.layout.jinxuan_item_text) {
                        // 加载文本item
                        itemHolder = new ItemHolder(v);
                    } else if (viewType == R.layout.jinxuan_item_gif) {
                        itemHolder = new ItemHolderGif(v);
                    } else if (viewType == R.layout.jinxuan_item_image) {
                        itemHolder = new ItemHolderImage(v);
                    }
                    itemHolder.init(JinXuanDetailActivity.this);
                    itemHolder.bind(1, jinXuanItem);
                    itemHolder.hideSPL();
                    detailContent.addView(v);
                    return null;
                }
            }).promise();
        }

        if (!isPLoaded && !isPLoading) {
            isPLoaded = true;
            QiquApi.getJinXuanPinLun(jinXuanItemId).then(new Fun() {
                @Override
                public Object d0(Object obj) throws Throwable {
                    Map<String, ArrayList<JinXuanComment>> d =
                            (Map<String, ArrayList<JinXuanComment>>) obj;
                    ArrayList<JinXuanComment> hotcomments = d.get("hotcomments");
                    ArrayList<JinXuanComment> comments = d.get("comments");
                    isPLoaded = true;
                    isPLoading = false;
                    data = new ArrayList<>();
                    if (hotcomments.size() > 0) {
                        data.add("spl_" + hotcomments.size());
                        data.addAll(hotcomments);
                    }
                    if (comments.size() > 0) {
                        data.add("pl_" + comments.size());
                        data.addAll(comments);
                    }
                    adapter.notifyDataSetChanged();
                    return null;
                }
            }).promise();
        }

    }

    private class Adpt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private LayoutInflater layoutInflater;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (null == layoutInflater) {
                layoutInflater = LayoutInflater.from(parent.getContext());
            }

            View v = layoutInflater.inflate(viewType, parent, false);
            RecyclerView.ViewHolder itemViewHolder = null;

            if (viewType == R.layout.view_loading) {
                ViewLoadingHolder vlh = new ViewLoadingHolder(v);
                vlh.setOnShouldLoad(JinXuanDetailActivity.this);
                vlh.init(parent.getContext());
                itemViewHolder = vlh;
            } else if (viewType == R.layout.view_pl) {
                itemViewHolder = new PLHolder(v);
            } else if (viewType == R.layout.view_pl_type) {
                itemViewHolder = new PLTypeHolder(v);
            }

            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewLoadingHolder) {
                ViewLoadingHolder loadingHolder = (ViewLoadingHolder) holder;
                loadingHolder.bind(position, null);
            } else {
                Object value = data.get(position);
                if (holder instanceof PLTypeHolder) {
                    PLTypeHolder plTypeHolder = (PLTypeHolder) holder;
                    if (value instanceof String) {
                        String[] vs = value.toString().split("_");
                        plTypeHolder.pltypeimg.setImageResource("spl".equals(vs[0]) ? R.drawable.ic_spl : R.drawable.ic_pll);
                        plTypeHolder.count.setText("(" + vs[1] + ")");
                    }
                } else if (holder instanceof PLHolder) {
                    PLHolder plHolder = (PLHolder) holder;

                    if (value instanceof JinXuanComment) {
                        JinXuanComment jinXuanComment = (JinXuanComment) value;

                        Glide.with(JinXuanDetailActivity.this)
                                .load(jinXuanComment.getAuthor_avatar())
                                .into(plHolder.head);

                        plHolder.name.setText(jinXuanComment.getAuthor());
                        plHolder.plcontent.setText(Tool.formatUcText(JinXuanDetailActivity.this, jinXuanComment.getContent()));
                        plHolder.zhancount.setText(String.valueOf(jinXuanComment.getLike()));
                    }

                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (getItemCount() == 1) {
                // 只有一个view， 显示加载
                return R.layout.view_loading;
            } else {
                Object item = data.get(position);
                if (item instanceof String) {
                    return R.layout.view_pl_type;
                } else {
                    return R.layout.view_pl;
                }
            }
        }

        @Override
        public int getItemCount() {
            if (null == data || data.size() < 1) {
                return 1;
            } else {
                return data.size();
            }
        }
    }

}
