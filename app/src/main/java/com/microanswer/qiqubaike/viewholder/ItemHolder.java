package com.microanswer.qiqubaike.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.microanswer.qiqubaike.R;
import com.microanswer.qiqubaike.bean.JinXuanItem;
import com.microanswer.qiqubaike.other.Tool;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //<editor-fold desc="一堆变量">
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

        /**
         * 分享按钮
         */
        protected LinearLayout getLinearLayoutshare;

        protected JinXuanItem jinXuanItem;
        //</editor-fold>

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
                getLinearLayoutshare = (LinearLayout) findViewById(R.id.linearLayoutshare);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        protected View findViewById(int id) {
            return itemView.findViewById(id);
        }

    public ItemHolder bind(int position, JinXuanItem jinXuanItem) {
            if (position >= 1) {
                if (jinXuanItem != null) {

                    this.jinXuanItem = jinXuanItem;

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

                            if (splContent != null) {
                                splContent.setText(Tool.formatUcText(context, hotCmt.getContent()));
                                // Log.i("JinXuanItem", hotCmt.getContent());
                            }
                            if (splName != null)
                                splName.setText(hotCmt.getNick_name());
                            if (splzancount != null)
                                splzancount.setText(String.valueOf(hotCmt.getUpcnt()));


                        } else {
                            linearLayoutSplView.setVisibility(View.GONE);
                        }
                    }

                    // 设置监听
                    try {
                        if (linearLayoutdiao != null && !linearLayoutdiao.hasOnClickListeners()) {
                            linearLayoutdiao.setOnClickListener(this);
                        }
                        if (linearLayoutkeng != null && !linearLayoutkeng.hasOnClickListeners()) {
                            linearLayoutkeng.setOnClickListener(this);
                        }
                        if (linearLayoutpl != null && !linearLayoutpl.hasOnClickListeners()) {
                            linearLayoutpl.setOnClickListener(this);
                        }
                        if (linearLayoutSplView != null && !linearLayoutSplView.hasOnClickListeners()) {
                            linearLayoutSplView.setOnClickListener(this);
                        }
                        if (getLinearLayoutshare != null && !getLinearLayoutshare.hasOnClickListeners()) {
                            getLinearLayoutshare.setOnClickListener(this);
                        }
                        if (btnJB != null && !btnJB.hasOnClickListeners()) {
                            btnJB.setOnClickListener(this);
                        }
                        if (headImg != null && headImg.hasOnClickListeners()) {
                            headImg.setOnClickListener(this);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            return this;
        }

        public ItemHolder recycled() {
            return this;
        }

        @Override
        public void onClick(View v) {
            Log.i("JinXuanItem", v.getId() + ", " + v.getClass().getName());
            if (linearLayoutSplView == v) {
                onSPLClick();
            } else if (linearLayoutpl == v) {
                onPLCkick();
            } else if (linearLayoutkeng == v) {
                onKengClick();
            } else if (linearLayoutdiao == v) {
                onDiaoClick();
            } else if (getLinearLayoutshare == v) {
                onShareClick();
            } else if (headImg == v) {
                onHeadImgClick();
            } else if (btnJB == v) {
                onJBClick();
            } else {
                onSomethingClick(v);
            }
        }

        /**
         * 神评论点击
         */
        protected void onSPLClick() {
        }

        /**
         * 坑 点击i
         */
        protected void onKengClick() {
        }

        /**
         * 屌 点击
         */
        protected void onDiaoClick() {
        }

        /**
         * 分享点击
         */
        protected void onShareClick() {
            if (jinXuanItem != null) {
            }
        }

        /**
         * 评论点击
         */
        protected void onPLCkick() {
        }

        /**
         * 头像点击
         */
        protected void onHeadImgClick() {
        }

        /**
         * 举报按钮点击
         */
        protected void onJBClick() {
        }

        /**
         * 当点击的东西都没在判断范围内,调用此方法
         *
         * @param v
         */
        protected void onSomethingClick(View v) {
        }
    }