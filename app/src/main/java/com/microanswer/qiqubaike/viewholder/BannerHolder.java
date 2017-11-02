package com.microanswer.qiqubaike.viewholder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.microanswer.qiqubaike.api.QiquApi;
import com.microanswer.qiqubaike.bean.BannerItem;
import com.microanswer.qiqubaike.bean.JinXuanItem;
import com.microanswer.qiqubaike.other.Fun;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class BannerHolder extends ItemHolder {

    private Banner mBanner;

    private List<String> imageUrls;
    private List<String> titles;

    public BannerHolder(View itemView) {
        super(itemView);
        imageUrls = new ArrayList<>();
        titles = new ArrayList<>();
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
    public ItemHolder recycled() {
        return super.recycled();
    }

    @Override
    public ItemHolder bind(int position, JinXuanItem j) {
        return super.bind(position, j);
    }
}