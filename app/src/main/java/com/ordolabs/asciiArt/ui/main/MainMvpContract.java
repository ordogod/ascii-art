package com.ordolabs.asciiArt.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.ordolabs.asciiArt.ui.base.BaseActivity;
import com.ordolabs.asciiArt.ui.base.BaseMvpPresenter;
import com.ordolabs.asciiArt.ui.base.BaseMvpView;

/**
 * Created by ordogod on 10.05.19.
 **/

public interface MainMvpContract {

    interface MainMvpView extends BaseMvpView {
        @Override
        Intent getStartIntent(@NonNull Context fromContext);
    }

    interface MainMvpPresenter<V extends BaseActivity> extends BaseMvpPresenter<V> {
        @Override
        void initLayoutViews();

        @Override
        void attachView(V mvpView);

        @Override
        void detachView();

        @Override
        boolean isViewAttached();

        @Override
        V getMvpView();

        @Override
        void setToolbar(String title);
    }
}
