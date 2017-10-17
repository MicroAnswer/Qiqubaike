package com.microanswer.qiqubaike.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.microanswer.qiqubaike.R;
import com.microanswer.qiqubaike.api.QiquApi;
import com.microanswer.qiqubaike.bean.BannerItem;
import com.microanswer.qiqubaike.bean.JinXuanItem;
import com.microanswer.qiqubaike.other.Fun;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import answer.android.views.ExpandView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Micro on 2017-10-3.
 */

public class JinXuanFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecAdapter adapter;
    private SwipeRefreshLayout swiperefreshlayout;

    private String recoid = "";
    private List<JinXuanItem> items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_jin_xuan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        swiperefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setOnRefreshListener(this);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
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
                        items.addAll(data1);
                        adapter.notifyItemRangeChanged(items.size(), data1.size());
                    }
                    JinXuanFragment.this.recoid = data.get("recoid").toString();
                    swiperefreshlayout.setRefreshing(false);
                    isLoading = false;
                    // AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    //         .setMessage(result).setPositiveButton("确定",null).create();
                    // alertDialog.show();
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

            if (items == null || position == items.size()) {
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
                ih = new ViewLoadingHolder(v);
            }

            return ih.init(parent.getContext());
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            if (null == items) {
                return 2;
            }
            if (items.size() > 3) {
                return items.size() + 1;
            }
            return items.size();
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        protected Context context;

        /**
         * 头像
         */
        protected CircleImageView headImg;

        /**
         * 昵称
         */
        protected TextView nickName;

        /**
         * 举报
         */
        protected TextView btnJB;

        /**
         * 文案内容
         */
        protected TextView textContent;

        /**
         * 神评论View
         */
        protected LinearLayout linearLayoutSplView;

        /**
         * 神评论用户名
         */
        protected TextView splName;

        /**
         * 神评论点赞按钮
         */
        protected LinearLayout splzhan;

        /**
         * 神评论赞图标
         */
        protected ImageView splzanicon;

        /**
         * 神评论赞个数
         */
        protected TextView splzancount;

        /**
         * 神评论内容
         */
        protected TextView splContent;

        /**
         * 叼 按钮
         */
        protected LinearLayout linearLayoutdiao;

        /**
         * 叼 按钮图标
         */
        protected ImageView diaoicon;

        /**
         * 叼 个数
         */
        protected TextView diaoCount;

        /**
         * 坑 按钮
         */
        protected LinearLayout linearLayoutkeng;

        /**
         * 坑 图标
         */
        protected ImageView kengicon;

        /**
         * 坑 个数
         */
        protected TextView kengCount;

        /**
         * 评论按钮
         */
        protected LinearLayout linearLayoutpl;

        /**
         * 评论条数
         */
        protected TextView plCount;

        protected JinXuanItem jinXuanItem;

        public ItemHolder(View itemView) {
            super(itemView);
        }

        public ItemHolder init(Context context) {
            this.context = context;
            try {
                headImg = (CircleImageView) findViewById(R.id.circleImageView);
                nickName = (TextView) findViewById(R.id.nickName);
                btnJB = (TextView) findViewById(R.id.btn_jb);
                textContent = (TextView) findViewById(R.id.textContext);
                linearLayoutdiao = (LinearLayout) findViewById(R.id.linearLayoutdiao);
                linearLayoutSplView = (LinearLayout) findViewById(R.id.linearLayoutSplView);
                splName = (TextView) findViewById(R.id.splname);
                splContent = (TextView) findViewById(R.id.sqlcontent);
                splzhan = (LinearLayout) findViewById(R.id.splzhan);
                splzanicon = (ImageView) findViewById(R.id.splzhanicon);
                splzancount = (TextView) findViewById(R.id.splzancount);
                diaoicon = (ImageView) findViewById(R.id.diaoicon);
                diaoCount = (TextView) findViewById(R.id.diaoCount);
                linearLayoutkeng = (LinearLayout) findViewById(R.id.linearLayoutkeng);
                kengicon = (ImageView) findViewById(R.id.kengicon);
                kengCount = (TextView) findViewById(R.id.kengCount);
                linearLayoutpl = (LinearLayout) findViewById(R.id.linearLayoutpl);
                plCount = (TextView) findViewById(R.id.plcount);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        protected View findViewById(int id) {
            return itemView.findViewById(id);
        }

        ItemHolder bind(int position) {
            if (position >= 1) {
                int posi = position - 1;
                if (null != items && posi < items.size()) {
                    jinXuanItem = items.get(posi);
                }

                if (jinXuanItem != null) {
                    if (headImg != null)
                        Glide.with(context).load(jinXuanItem.getEditor_icon()).into(headImg);

                    if (nickName != null)
                        nickName.setText(jinXuanItem.getEditor_nickname());

                    String summary = jinXuanItem.getSummary();
                    String title = jinXuanItem.getTitle();

                    if (textContent != null) {
                        if (TextUtils.isEmpty(summary) && !TextUtils.isEmpty(title)) {
                            textContent.setText(title);
                        } else if (!TextUtils.isEmpty(summary) && TextUtils.isEmpty(title)) {
                            textContent.setText(summary);
                        } else {
                            textContent.setText("这个用户没有找到文案内容。");
                            Log.i("JinXuanItem", jinXuanItem.toString());
                        }
                    }

                    if (diaoCount != null) {
                        diaoCount.setText(String.valueOf(jinXuanItem.getLike_cnt()));
                    }

                    if (kengCount != null) {
                        kengCount.setText(String.valueOf(jinXuanItem.getDislike_cnt()));
                    }

                    if (plCount != null) {
                        plCount.setText(String.valueOf(jinXuanItem.getCmt_cnt()));
                    }

                    if (linearLayoutSplView != null) {
                        List<JinXuanItem.HotCmts> hot_cmts = jinXuanItem.getHot_cmts();
                        if (hot_cmts != null && hot_cmts.size() > 0) {
                            // 有神评论
                            linearLayoutSplView.setVisibility(View.VISIBLE);
                            JinXuanItem.HotCmts hotCmt = hot_cmts.get(0);

                            if (splContent != null)
                                splContent.setText(hotCmt.getContent());
                            if (splName != null)
                                splName.setText(hotCmt.getNick_name());
                            if (splzancount != null)
                                splzancount.setText(String.valueOf(hotCmt.getUpcnt()));


                        } else {
                            linearLayoutSplView.setVisibility(View.GONE);
                        }
                    }
                }
            }
            return this;
        }

        ItemHolder recycled() {
            return this;
        }

    }

    class ItemHolderImage extends ItemHolder implements View.OnClickListener, RequestListener<String, GlideDrawable> {

        private ExpandView expandView;
        private LinearLayout btn_see_more_pic;
        private ImageView image;

        public ItemHolderImage(View itemView) {
            super(itemView);
            expandView = (ExpandView) itemView.findViewById(R.id.expandview_pic);
            btn_see_more_pic = (LinearLayout) itemView.findViewById(R.id.see_more_pic);
            image = (ImageView) findViewById(R.id.imageView);
        }

        public ItemHolderImage bind(int position) {
            super.bind(position);
            if (!btn_see_more_pic.hasOnClickListeners()) {
                btn_see_more_pic.setOnClickListener(this);
            }
            btn_see_more_pic.setVisibility(View.GONE);

            ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            image.setLayoutParams(layoutParams);

            if (jinXuanItem != null) {

                List<JinXuanItem.Images> images = jinXuanItem.getImages();

                if (images != null && images.size() > 0) {
                    JinXuanItem.Images images1 = images.get(0);
                    Glide.with(context).load(images1.getUrl()).listener(this).into(image);
                    return this;
                }

            }
            return this;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.see_more_pic:
                    expandView.toggle();
                    btn_see_more_pic.setVisibility(View.GONE);
                    break;
            }
        }

        public ItemHolderImage recycled() {
            super.recycled();
            if (expandView.isExpan()) {
                expandView.close();
                btn_see_more_pic.setVisibility(View.VISIBLE);
            }
            return this;
        }

        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, final Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            // ImageView imageView = image;
            // ViewGroup.LayoutParams params = imageView.getLayoutParams();
            // int vw = imageView.getWidth();
            // float scale = resource.getHeight() / (float) resource.getWidth();
            // int vh = Math.round(vw * scale);
            // params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
            // imageView.setLayoutParams(params);
            // int intrinsicHeight = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();

            ViewGroup.LayoutParams params = image.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            image.setLayoutParams(params);


            int width = image.getWidth();
            Rect rect = resource.copyBounds();
            int rWidth = rect.width();
            int rHeight = rect.height();

            int he = Math.round(width * (rHeight / (float) rWidth));

             Log.i("JinXuanItem", "");
            if (he > DensityUtil.dip2px(280f)) {
                btn_see_more_pic.setVisibility(View.VISIBLE);
            }
            return false;
        }
    }

    class ItemHolderGif extends ItemHolder implements View.OnClickListener, RequestListener<String, GlideDrawable> {

        /**
         * 显示GIF的View
         */
        private ImageView imageviewGif;

        /**
         * 播放Gif按钮
         */
        private LinearLayout linearLayoutplaygif;

        /**
         * gif加载提示
         */
        private ProgressBar gifloadprogress;

        private String url;
        private Bitmap coverBitmap;

        public ItemHolderGif(View itemView) {
            super(itemView);
            imageviewGif = (ImageView) findViewById(R.id.imageViewGif);
            linearLayoutplaygif = (LinearLayout) findViewById(R.id.linearLayoutplaygif);
            gifloadprogress = (ProgressBar) findViewById(R.id.gifloadprogress);
        }

        @Override
        ItemHolderGif bind(int position) {
            super.bind(position);

            if (!linearLayoutplaygif.hasOnClickListeners()) {
                linearLayoutplaygif.setOnClickListener(this);
            }

            // 把界面重置为有播放按钮遮罩的
            ((ViewGroup) linearLayoutplaygif.getParent()).setVisibility(View.VISIBLE);
            linearLayoutplaygif.setVisibility(View.VISIBLE);
            gifloadprogress.setVisibility(View.GONE);

            if (jinXuanItem != null) {

                List<JinXuanItem.Images> images = jinXuanItem.getImages();

                if (images != null && images.size() > 0) {
                    JinXuanItem.Images images1 = images.get(0);
                    url = images1.getUrl();
                    Glide.with(context).load(url).asBitmap().into(imageviewGif);
                    // Log.i("JinXuanFragment", into.getClass().getName());
                    return this;
                }

            }
            Glide.with(context).load(R.mipmap.ma).asBitmap().into(imageviewGif);
            return this;
        }

        @Override
        ItemHolderGif recycled() {
            super.recycled();
            return this;
        }

        @Override
        public void onClick(View v) {
            if (v == linearLayoutplaygif) {
                linearLayoutplaygif.setVisibility(View.GONE);
                gifloadprogress.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(url)) {
                    Glide.with(context).load(url)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .placeholder(imageviewGif.getDrawable())
                            .listener(this)
                            .dontAnimate()
                            .into(imageviewGif);
                }
            }
        }

        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            gifloadprogress.setVisibility(View.GONE);
            linearLayoutplaygif.setVisibility(View.VISIBLE);
            ((ViewGroup) linearLayoutplaygif.getParent()).setVisibility(View.GONE);

            return false;
        }
    }

    class BannerHolder extends ItemHolder {

        private Banner mBanner;

        private List<String> imageUrls;
        private List<String> titles;

        public BannerHolder(View itemView) {
            super(itemView);
            imageUrls = new ArrayList<>();
            // imageUrls.add("http://bq-img.peco.uodoo.com/qiqu/img/buz/banner/1d30b80e0bbd92a5f4cd9d5df7aef358.jpg");
            titles = new ArrayList<>();
            // titles.add("酒精探测仪闪红光 司机吓得大喊：刚吃了蛋黄派！");
        }

        @Override
        public BannerHolder init(Context context) {
            super.init(context);
            mBanner = (Banner) itemView;
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            mBanner.setBannerAnimation(Transformer.Default);
            mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            });

            QiquApi.getJinXuanBanner().then(new Fun() {
                @Override
                public Object d0(Object obj) throws Throwable {
                    List<BannerItem> bannerItems = (List<BannerItem>) obj;
                    // Log.i("MBanner", bannerItems.size() + "");
                    if (bannerItems != null) {
                        for (BannerItem bannerItem : bannerItems) {
                            imageUrls.add(bannerItem.getImgUrl());
                            titles.add(bannerItem.getTitle());
                        }
                        mBanner.setImages(imageUrls);
                        mBanner.setBannerTitles(titles);
                        mBanner.start();
                    }
                    return null;
                }
            }).exception(new Fun() {
                @Override
                public Object d0(Object obj) throws Throwable {
                    Log.i("MBanner", obj.toString());
                    return null;
                }
            }).promise();

            return this;
        }

        @Override
        ItemHolder recycled() {
            return super.recycled();
        }

        @Override
        ItemHolder bind(int position) {
            return super.bind(position);
        }
    }

    class ViewLoadingHolder extends ItemHolder {

        public ViewLoadingHolder(View itemView) {
            super(itemView);
        }

        @Override
        ItemHolder bind(int position) {
            super.bind(position);
            loadData(recoid);
            return this;
        }
    }
}
