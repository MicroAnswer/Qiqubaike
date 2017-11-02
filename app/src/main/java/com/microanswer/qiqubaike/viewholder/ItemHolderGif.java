package com.microanswer.qiqubaike.viewholder;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.microanswer.qiqubaike.R;
import com.microanswer.qiqubaike.bean.JinXuanItem;

import java.util.List;

public class ItemHolderGif extends ItemHolder implements View.OnClickListener,
        RequestListener<String, GlideDrawable> {

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
    public ItemHolderGif bind(int position, JinXuanItem jinXuanItem) {
        super.bind(position, jinXuanItem);

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
    public ItemHolderGif recycled() {
        super.recycled();
        return this;
    }

    @Override
    public void onSomethingClick(View v) {
        super.onSomethingClick(v);
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
    public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                               boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, String model,
                                   Target<GlideDrawable> target, boolean isFromMemoryCache,
                                   boolean isFirstResource) {
        gifloadprogress.setVisibility(View.GONE);
        linearLayoutplaygif.setVisibility(View.VISIBLE);
        ((ViewGroup) linearLayoutplaygif.getParent()).setVisibility(View.GONE);

        return false;
    }
}