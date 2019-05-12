package com.ordolabs.asciiArt.ui.main;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ordolabs.asciiArt.data.MainModel;
import com.ordolabs.asciiArt.utils.RepeatListener;
import com.ordolabs.asciiArt.ui.base.BaseActivity;
import com.ordolabs.asciiArt.ui.base.BasePresenter;
import com.ordolabs.myapplication.R;

/**
 * Created by ordogod on 10.05.19.
 **/

public class MainPresenter<V extends BaseActivity> extends BasePresenter<V> implements MainMvpContract.MainMvpPresenter<V> {

    private MainModel<MainPresenter> mvpModel;

    private TextView fontSizeSpan;
    private ImageButton fontSizeButtonLess;
    private ImageButton fontSizeButtonMore;

    private EditText textListInput;

    MainPresenter(V mvpView) {
        super(mvpView);

        mvpModel = new MainModel<MainPresenter>(this);

        setToolbar(mvpView.getString(R.string.act_main_label));
        initLayoutViews();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initLayoutViews() {

        fontSizeSpan = mvpView.findViewById(R.id.mainFontSizeSpan);
        fontSizeButtonLess = mvpView.findViewById(R.id.mainFontSizeButtonLess);
        fontSizeButtonMore = mvpView.findViewById(R.id.mainFontSizeButtonMore);

        textListInput = mvpView.findViewById(R.id.mainTextListInput);

        fontSizeButtonLess.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = fontSizeSpan.getText().toString();
                if (mvpModel.isInputInRangeIncludingBoth(3, 25, Integer.parseInt(input) - 1)) {
                    fontSizeSpan.setText(String.valueOf(Integer.parseInt(input) - 1));
                }
            }
        }));

        fontSizeButtonMore.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = fontSizeSpan.getText().toString();
                if (mvpModel.isInputInRangeIncludingBoth(3, 25, Integer.parseInt(input) + 1)) {
                    fontSizeSpan.setText(String.valueOf(Integer.parseInt(input) + 1));
                }
            }
        }));

//        mvpView.startActivityForResult(mvpModel.getIntentForGalleryPictureUploading(), mvpModel.getRESULT_LOAD_IMAGE());

//        mvpModel.parseLiteralsString( String.valueOf(textListInput.getText()) ); TODO: set into start art generation handler
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