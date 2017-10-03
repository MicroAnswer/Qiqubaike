package com.microanswer.qiqubaike.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.microanswer.qiqubaike.R;

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
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (null == inflater) {
                inflater = LayoutInflater.from(parent.getContext());
            }

            return new ItemHolder(inflater.inflate(R.layout.jinxuan_item, parent, false));
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

        public ItemHolder(View itemView) {
            super(itemView);
        }

        public void bind(int position) {
        }
    }
}
