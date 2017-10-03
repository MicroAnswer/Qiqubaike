package com.microanswer.qiqubaike.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.microanswer.qiqubaike.R;

/**
 * Created by Micro on 2017-10-3.
 */

public abstract class BaseFragment extends Fragment {

    protected String name;

    /**
     * 根据不同的位置创建不同的Fragment
     *
     * @param position
     * @return
     */
    public static BaseFragment buildFragment(Context context, int position) {
        if (0 == position) {

        } else if (1 == position) {

        } else if (2 == position) {

        } else if (3 == position) {

        } else if (4 == position) {

        }
        return new JinXuanFragment();
    }

    public String getName(Context context, int position) {
        if (TextUtils.isEmpty(name)) {
            name = context.getResources().getStringArray(R.array.page_title)[position];
        }
        return name;
    }

    public String getName() {
        return name;
    }
}
