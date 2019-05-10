package com.ordolabs.asciiArt.ui.main;

import com.ordolabs.asciiArt.ui.base.BaseActivity;
import com.ordolabs.asciiArt.ui.base.BasePresenter;
import com.ordolabs.myapplication.R;

/**
 * Created by ordogod on 10.05.19.
 **/

class MainPresenter<V extends BaseActivity> extends BasePresenter<V> implements MainMvpContract.MainMvpPresenter<V> {

    MainPresenter(V mvpView) {
        super(mvpView);
        setToolbar(mvpView.getString(R.string.act_main_label));
        initLayoutViews();
    }

    @Override
    public void initLayoutViews() {

    }

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }

    @Override
    public V getMvpView() {
        return super.getMvpView();
    }

    @Override
    public void setToolbar(String title) {
        super.setToolbar(title);
    }
}
