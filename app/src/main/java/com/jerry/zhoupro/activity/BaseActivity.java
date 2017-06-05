package com.jerry.zhoupro.activity;

import com.jerry.zhoupro.pop.RefreshDialog;
import com.jerry.zhoupro.util.ToastTools;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;
    private RefreshDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeViews();
        setContentView(getContentLayout());
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    protected void beforeViews() {}

    protected abstract int getContentLayout() ;

    protected void initView() {}

    protected abstract void initData();

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public void loadingDialog() {
        if (null == progressDialog) {
            progressDialog = new RefreshDialog(this);
        }
        if (progressDialog.isShowing()) {
            return;
        }
        progressDialog.show();
    }

    public void closeLoadingDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected void setGone(View view) {
        view.setVisibility(View.GONE);
    }

    protected void setVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    protected void setInvisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    protected void toast(int resId) {
        ToastTools.showShort(this,resId);
    }
}
