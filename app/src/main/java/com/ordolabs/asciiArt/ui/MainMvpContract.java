package com.ordolabs.asciiArt.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.ordolabs.asciiArt.base.BaseActivity;
import com.ordolabs.asciiArt.base.BaseMvpPresenter;
import com.ordolabs.asciiArt.base.BaseMvpView;

/**
 * Created by ordogod on 10.05.19.
 **/

public interface MainMvpContract {

    interface MainMvpView extends BaseMvpView {
        @Override
        Intent getStartIntent(@NonNull Context fromContext);
    }

    interface MainMvpPresenter<V extends BaseActivity> extends BaseMvpPresenter<V> {

        void setImageViewData(@NonNull Uri data);

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
