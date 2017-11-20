package com.microanswer.qiqubaike.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.microanswer.qiqubaike.R;

/**
 * 评论的Holder
 * Created by Microanswer on 2017/11/15.
 */

public class PLTypeHolder extends RecyclerView.ViewHolder {
    public ImageView pltypeimg;
    public TextView count;
    public PLTypeHolder(View itemView) {
        super(itemView);
        pltypeimg = (ImageView) itemView.findViewById(R.id.pltypeimg);
        count = (TextView) itemView.findViewById(R.id.count);
    }
}
