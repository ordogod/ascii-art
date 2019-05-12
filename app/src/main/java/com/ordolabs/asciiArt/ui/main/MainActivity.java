package com.ordolabs.asciiArt.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;

import com.ordolabs.asciiArt.ui.base.BaseActivity;
import com.ordolabs.myapplication.R;

public class MainActivity extends BaseActivity implements MainMvpContract.MainMvpView {

    MainPresenter<MainActivity> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter<>(this);
    }

    @Override
    public Intent getStartIntent(@NonNull Context fromContext) {
        return new Intent(fromContext, MainActivity.class);
    }
}
