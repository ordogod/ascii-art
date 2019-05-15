package com.ordolabs.asciiArt.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ordogod on 010 10.05.19.
 **/


public abstract class BaseActivity extends AppCompatActivity implements BaseMvpView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public abstract Intent getStartIntent(@NonNull Context fromContext);
}
