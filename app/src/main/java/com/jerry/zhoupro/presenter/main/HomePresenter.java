package com.jerry.zhoupro.presenter.main;

import java.util.ArrayList;
import java.util.List;

import com.jerry.zhoupro.R;
import com.jerry.zhoupro.base.BaseFragment;
import com.jerry.zhoupro.base.FragmentViewPagerAdapter;
import com.jerry.zhoupro.app.Constants;
import com.jerry.zhoupro.presenter.listener.MyViewPageChangeListener;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;

/**
 * Created by wzl-pc on 2017/5/9.
 */

public class HomePresenter extends BaseFragment {

    @BindView(R.id.rb_lost)
    RadioButton mRbLost;
    @BindView(R.id.rb_found)
    RadioButton mRbFound;
    @BindView(R.id.rg_main)
    RadioGroup mRgMain;
    @BindView(R.id.vp_lost_found)
    ViewPager mVpLostFound;
    private int type;
    private List<Fragment> mFragmentList;
    private FragmentViewPagerAdapter mAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(final View view) {
        super.initView(view);
        mRgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                type = checkedId == R.id.rb_lost ? Constants.LOST : Constants.FOUND;
                mVpLostFound.setCurrentItem(checkedId == R.id.rb_lost ? 0 : 1);
            }
        });

        mFragmentList = new ArrayList<>(2);
        String[] tabs = {getString(R.string.lost), getString(R.string.found)};
        mAdapter = new FragmentViewPagerAdapter(getChildFragmentManager(), tabs, mFragmentList);
        mVpLostFound.setAdapter(mAdapter);
        mVpLostFound.setOffscreenPageLimit(mFragmentList.size());
        mVpLostFound.addOnPageChangeListener(new MyViewPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                mRgMain.check(position == 0 ? R.id.rb_lost : R.id.rb_found);
            }
        });
        initFragment();
    }

    private void initFragment() {
        if (mAdapter != null) {
            mAdapter.clearFragmentCache();
            mFragmentList.clear();
        }
        mFragmentList.add(InfoListBasePresenter.newInstance(Constants.LOST));
        mFragmentList.add(InfoListBasePresenter.newInstance(Constants.FOUND));
        mAdapter.notifyDataSetChanged();
    }
}
