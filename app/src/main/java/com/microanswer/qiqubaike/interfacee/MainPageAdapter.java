package com.microanswer.qiqubaike.interfacee;

import android.support.v4.app.FragmentManager;

import com.microanswer.qiqubaike.fragment.BaseFragment;

/**
 * Created by Micro on 2017-10-3.
 */

public interface MainPageAdapter {

    /**
     * 请返回主界面一共有多少个分tab页面
     *
     * @return
     */
    int getPageCount();

    /**
     * 请返回对应界面的Fragment
     *
     * @param position
     * @return
     */
    BaseFragment getFragment(int position);

    /**
     * 请返回对应界面的标题
     *
     * @param position
     * @return
     */
    String getFragmentTitle(int position);

    FragmentManager getSupportFragmentManager();
}
