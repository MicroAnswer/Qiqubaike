package com.microanswer.qiqubaike.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.microanswer.qiqubaike.R;
import com.microanswer.qiqubaike.bean.JinXuanItem;

public class ViewLoadingHolder extends ItemHolder {

    private ImageView load_view;
    private OnShouldLoad onShouldLoad;

    public ViewLoadingHolder(View itemView) {
        super(itemView);
    }

    @Override
    public ItemHolder init(Context context) {
        super.init(context);

        load_view = (ImageView) findViewById(R.id.load_view);

        return this;
    }

    @Override
    public ItemHolder bind(int position, JinXuanItem jinXuanItem) {
        super.bind(position, jinXuanItem);
        Glide.with(context).load(R.drawable.uc_loading)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(load_view);
        if (onShouldLoad != null) {
            onShouldLoad.onLoad();
        }
        return this;
    }

    public OnShouldLoad getOnShouldLoad() {
        return onShouldLoad;
    }

    public void setOnShouldLoad(OnShouldLoad onShouldLoad) {
        this.onShouldLoad = onShouldLoad;
    }

    public interface OnShouldLoad {
        void onLoad();
    }

}