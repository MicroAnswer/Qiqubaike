package com.microanswer.qiqubaike.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import answer.android.views.ExpandView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Micro on 2017-10-3.
 */

public class JinXuanFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_jin_xuan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new RecAdapter();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
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
            if (position % 2 == 0) {
                return R.layout.jinxuan_item_image;
            } else if (position % 5 == 0) {
                return R.layout.jinxuan_item_gif;
            }
            return R.layout.jinxuan_item_text;
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
            }

            return ih;
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
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

        public ItemHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
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
        }

        protected View findViewById(int id) {
            return itemView.findViewById(id);
        }

        ItemHolder bind(int position) {
            return this;
        }

        ItemHolder recycled() {
            return this;
        }

    }

    class ItemHolderImage extends ItemHolder implements View.OnClickListener {

        private ExpandView expandView;
        private LinearLayout btn_see_more_pic;

        public ItemHolderImage(View itemView) {
            super(itemView);
            expandView = (ExpandView) itemView.findViewById(R.id.expandview_pic);
            btn_see_more_pic = (LinearLayout) itemView.findViewById(R.id.see_more_pic);
        }

        public ItemHolderImage bind(int position) {
            super.bind(position);
            if (!btn_see_more_pic.hasOnClickListeners()) {
                btn_see_more_pic.setOnClickListener(this);
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
    }

    class ItemHolderGif extends ItemHolder implements View.OnClickListener, RequestListener<Integer, GlideDrawable> {

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
            ((ViewGroup)linearLayoutplaygif.getParent()).setVisibility(View.VISIBLE);
            linearLayoutplaygif.setVisibility(View.VISIBLE);
            gifloadprogress.setVisibility(View.GONE);


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

                Glide.with(context).load(R.mipmap.ma)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .listener(this)
                        .into(imageviewGif);
            }
        }

        @Override
        public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

            gifloadprogress.setVisibility(View.GONE);
            linearLayoutplaygif.setVisibility(View.VISIBLE);
            ((ViewGroup) linearLayoutplaygif.getParent()).setVisibility(View.GONE);

            return false;
        }
    }
}
