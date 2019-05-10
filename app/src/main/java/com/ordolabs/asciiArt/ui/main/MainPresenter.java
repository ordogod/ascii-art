package com.ordolabs.asciiArt.ui.main;

import android.text.InputFilter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ordolabs.asciiArt.data.MinMaxInputFilter;
import com.ordolabs.asciiArt.ui.base.BaseActivity;
import com.ordolabs.asciiArt.ui.base.BasePresenter;
import com.ordolabs.myapplication.R;

/**
 * Created by ordogod on 10.05.19.
 **/

class MainPresenter<V extends BaseActivity> extends BasePresenter<V> implements MainMvpContract.MainMvpPresenter<V> {

    private EditText fontSizeInput;

    private ImageButton fontSizeButtonLess;
    private ImageButton fontSizeButtonMore;

    MainPresenter(V mvpView) {
        super(mvpView);
        setToolbar(mvpView.getString(R.string.act_main_label));
        initLayoutViews();
    }

    @Override
    public void initLayoutViews() {

        fontSizeInput = mvpView.findViewById(R.id.mainFontSizeInput);
        fontSizeInput.setFilters(new InputFilter[]{ new MinMaxInputFilter("3", "30")});

        fontSizeButtonLess = mvpView.findViewById(R.id.mainFontSizeButtonLess);
        fontSizeButtonMore = mvpView.findViewById(R.id.mainFontSizeButtonMore);
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
