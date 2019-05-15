package com.ordolabs.asciiArt.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ordolabs.asciiArt.base.BaseActivity;
import com.ordolabs.myapplication.R;

public class MainActivity extends BaseActivity implements MainMvpContract.MainMvpView {

    private MainPresenter<MainActivity> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter<>(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImg = data.getData();
            presenter.setImageViewData(selectedImg);
        }
    }

    @Override
    public Intent getStartIntent(@NonNull Context fromContext) {
        return new Intent(fromContext, MainActivity.class);
    }
}
