package com.ordolabs.asciiArt.base;

/**
 * Created by ordogod on 010 10.05.19.
 **/

import android.support.v7.widget.Toolbar;

import com.ordolabs.myapplication.R;

/**
 * Base class that implements the View(Activity) interface.
 */
public abstract class BasePresenter<V extends BaseActivity> implements BaseMvpPresenter<V> {

    protected V mvpView;

    public BasePresenter(V mvpView) {
        attachView(mvpView);
    }

    @Override
    public abstract void initLayoutViews(); // no implementation (abstract class can not deal with attached mvpView's views)

    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    @Override
    public boolean isViewAttached() {
        return this.mvpView != null;
    }

    @Override
    public V getMvpView() {
        if (isViewAttached() == false) throw new MvpViewNotAttachedException();
        else return this.mvpView;
    }

    @Override
    public void setToolbar(String title) {
        mvpView.setSupportActionBar( (Toolbar) mvpView.findViewById(R.id.toolbar));
        mvpView.setTitle(title);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        MvpViewNotAttachedException() {
            super("Please call \'Presenter.attachView(MvpView)\' before requestion data from the Presenter.");
        }
    }
}
