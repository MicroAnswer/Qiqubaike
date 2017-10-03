package com.microanswer.qiqubaike.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.microanswer.qiqubaike.R;
import com.microanswer.qiqubaike.adapter.MainActivityAdapter;
import com.microanswer.qiqubaike.fragment.BaseFragment;
import com.microanswer.qiqubaike.interfacee.MainPageAdapter;

public class MainActivity extends BaseActivity implements MainPageAdapter {

    private static final String KEY_PAGEINDEX = "key_pageindex";


    // 主界面的viewpager
    private ViewPager viewpager;

    // 适配器
    private MainActivityAdapter adapter;

    // 所有的分页Fragment
    private SparseArrayCompat<BaseFragment> fragments;

    // 分页tab
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        fragments = new SparseArrayCompat<>(getPageCount());
        adapter = new MainActivityAdapter(this);

        // 设置适配器
        viewpager.setAdapter(adapter);

        // 关联tablayout
        tabLayout.setupWithViewPager(viewpager);

        // 设置初始化加载页面的位置为上次退出时保存的位置
        viewpager.setCurrentItem(getPageIndex());
    }

    /**
     * 获取主界面上共有几个tab分页面
     *
     * @return
     */
    @Override
    public int getPageCount() {
        return getResources().getStringArray(R.array.page_title).length;
    }

    @Override
    public BaseFragment getFragment(int position) {

        BaseFragment fragment = fragments.get(position);

        if (fragment == null) {
            fragment = BaseFragment.buildFragment(this, position);
            fragments.put(position, fragment);
        }

        return fragment;
    }

    @Override
    public String getFragmentTitle(int position) {
        return getFragment(position).getName(this, position);
    }

    /**
     * 保存当前页面的位置
     */
    private void savePageIndex() {
        getDefaultSharedPreferences()
                .edit()
                .putInt(KEY_PAGEINDEX, viewpager.getCurrentItem())
                .apply();
    }

    /**
     * 获取保存的当前页面位置
     *
     * @return
     */
    private int getPageIndex() {
        return getDefaultSharedPreferences()
                .getInt(KEY_PAGEINDEX, 0);
    }

    @Override
    protected void onDestroy() {

        // 保存当前页面的位置
        savePageIndex();

        super.onDestroy();
    }
}
