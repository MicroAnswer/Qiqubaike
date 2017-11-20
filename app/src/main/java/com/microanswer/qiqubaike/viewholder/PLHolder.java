package com.microanswer.qiqubaike.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.microanswer.qiqubaike.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 评论的Holder
 * Created by Microanswer on 2017/11/15.
 */

public class PLHolder extends RecyclerView.ViewHolder {
    public CircleImageView head;
    public TextView name;
    public TextView zhancount;
    public TextView plcontent;

    public PLHolder(View itemView) {
        super(itemView);

        head = (CircleImageView) itemView.findViewById(R.id.head);
        name = (TextView) itemView.findViewById(R.id.name);
        zhancount = (TextView) itemView.findViewById(R.id.zhancount);
        plcontent = (TextView) itemView.findViewById(R.id.plcontent);
    }
}
