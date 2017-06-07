package com.jerry.zhoupro.fragment;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jerry.zhoupro.R;
import com.jerry.zhoupro.adapter.CommonAdapter;
import com.jerry.zhoupro.adapter.LostFoundInfoListAdapter;
import com.jerry.zhoupro.bean.ThingInfoBean;
import com.jerry.zhoupro.command.Constants;
import com.jerry.zhoupro.command.Key;
import com.jerry.zhoupro.util.Mlog;

import android.view.View;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by wzl-pc on 2017/5/20.
 */

public abstract class InfoListBaseFragment extends BaseFragment {

    @BindView(R.id.pull_refresh_list)
    PullToRefreshListView mPullRefreshList;
    protected CommonAdapter<ThingInfoBean> mAdapter;
    protected List<ThingInfoBean> mLostFoundInfos = new ArrayList<>();
    private int type;// Lost 0；Found 1；

    public static InfoListBaseFragment newInstance(int type) {
        InfoListBaseFragment fragment;
        if (type == Constants.LOST) {
            fragment = new InfoListLostFragment();
        } else {
            fragment = new InfoListFoundFragment();
        }
        fragment.type = type;
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_info_list;
    }

    @Override
    public void initView(final View view) {
        super.initView(view);
        mAdapter = new LostFoundInfoListAdapter(getContext(), mLostFoundInfos);
        mPullRefreshList.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        BmobQuery<ThingInfoBean> query = new BmobQuery<>(ThingInfoBean.class.getSimpleName());
        query.addWhereEqualTo(Key.TAG_RELEASE_TYPE, type);
        query.setLimit(10);
        query.order(Key.CREATEDAT);
        query.findObjects(new FindListener<ThingInfoBean>() {
            @Override
            public void done(final List<ThingInfoBean> list, final BmobException e) {
                if (e != null) {
                    toast(R.string.error);
                    return;
                }
                for (ThingInfoBean thingInfoBean : list) {
                    Mlog.d(thingInfoBean.toString());
                    mLostFoundInfos.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
