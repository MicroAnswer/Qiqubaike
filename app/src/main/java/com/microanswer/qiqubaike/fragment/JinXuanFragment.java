package com.microanswer.qiqubaike.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        public ItemHolder(View itemView) {
            super(itemView);
            headImg = (CircleImageView) findViewById(R.id.circleImageView);
            nickName = (TextView) findViewById(R.id.nickName);
            btnJB = (TextView) findViewById(R.id.btn_jb);
            textContent = (TextView) findViewById(R.id.textContext);
        }

        private View findViewById(int id) {
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

    class ItemHolderGif extends ItemHolder {

        public ItemHolderGif(View itemView) {
            super(itemView);
        }

        @Override
        ItemHolderGif bind(int position) {
            super.bind(position);
            return this;
        }

        @Override
        ItemHolderGif recycled() {
            super.recycled();
            return this;
        }
    }
}
