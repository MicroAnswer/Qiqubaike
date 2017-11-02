package com.microanswer.qiqubaike.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.microanswer.qiqubaike.R;
import com.microanswer.qiqubaike.bean.JinXuanItem;

import org.xutils.common.util.DensityUtil;

import java.util.List;

import answer.android.views.ExpandView;

public class ItemHolderImage extends ItemHolder {

        private ExpandView expandView;
        private LinearLayout btn_see_more_pic;
        private ImageView image;

        public ItemHolderImage(View itemView) {
            super(itemView);
            expandView = (ExpandView) itemView.findViewById(R.id.expandview_pic);
            btn_see_more_pic = (LinearLayout) itemView.findViewById(R.id.see_more_pic);
            image = (ImageView) findViewById(R.id.imageView);
        }

        public ItemHolderImage bind(int position, JinXuanItem jinXuanItem) {
            super.bind(position, jinXuanItem);
            if (!btn_see_more_pic.hasOnClickListeners()) {
                btn_see_more_pic.setOnClickListener(this);
            }
            btn_see_more_pic.setVisibility(View.GONE);

            image.setImageDrawable(null);

            if (jinXuanItem != null) {

                final List<JinXuanItem.Images> images = jinXuanItem.getImages();
                if (images != null && images.size() > 0) {
                    image.post(new Runnable() {
                        @Override
                        public void run() {
                            JinXuanItem.Images images1 = images.get(0);

                            int heigsht = images1.getHeight();
                            int widtsh = images1.getWidth();
                            int width = image.getWidth();
                            int he = Math.round(width * (heigsht / (float) widtsh));
                            if (he > DensityUtil.dip2px(350f)) {
                                btn_see_more_pic.setVisibility(View.VISIBLE);
                            }

                            ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
                            layoutParams.height = he;
                            image.setLayoutParams(layoutParams);

                            Glide.with(context).load(images1.getUrl()).asBitmap().into(image);
                        }
                    });
                    return this;
                }

            }
            return this;
        }

        @Override
        public void onSomethingClick(View view) {
            super.onSomethingClick(view);
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