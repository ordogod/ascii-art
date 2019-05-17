package com.ordolabs.asciiArt.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.ordolabs.asciiArt.data.MainModel;
import com.ordolabs.asciiArt.utils.RepeatListener;
import com.ordolabs.asciiArt.base.BaseActivity;
import com.ordolabs.asciiArt.base.BasePresenter;
import com.ordolabs.myapplication.R;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ordogod on 10.05.19.
 **/

public class MainPresenter<V extends BaseActivity> extends BasePresenter<V> implements MainMvpContract.MainMvpPresenter<V> {

    private MainModel<MainPresenter> mvpModel;

    private TextView fontSizeSpan;
    private ImageButton fontSizeButtonLess;
    private ImageButton fontSizeButtonMore;

    private EditText textListInput;
    private Button textListClearButton;

    private Button uploadImgButton;
    private TextView uploadImgInfo;

    private Button startButton;

    private RelativeLayout uploadedImgContainer;
    private PhotoView uploadedImgView;

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
        textListClearButton = mvpView.findViewById(R.id.mainTextListClearButton);

        uploadImgButton = mvpView.findViewById(R.id.mainUploadImgButton);
        uploadImgInfo = mvpView.findViewById(R.id.mainUploadImgInfo);

        startButton = mvpView.findViewById(R.id.mainStartButton);

        uploadedImgContainer = mvpView.findViewById(R.id.mainImageContainer);
        uploadedImgView = mvpView.findViewById(R.id.mainUploadedImg);

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

        textListClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textListInput.setText("");
            }
        });

        uploadImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpView.startActivityForResult(mvpModel.getIntentForPictureUploading(), mvpModel.getRESULT_LOAD_IMAGE());
            }
        });

        uploadedImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadedImgContainer.setBackgroundColor(
                        mvpView.getResources().getColor(R.color.imageViewWindowBackground)
                );
                RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT
                );

                TransitionManager.beginDelayedTransition(
                        uploadedImgContainer,
                        new TransitionSet().addTransition(
                                new ChangeBounds().setDuration(300L)
                                        .setStartDelay(0)
                                        .setInterpolator(new AccelerateInterpolator())
                        )
                );

                uploadedImgContainer.setLayoutParams(params);
            }
        });

        uploadedImgContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadedImgContainer.setBackgroundColor(
                        mvpView.getResources().getColor(R.color.transparent)
                );
                RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
                );

                TransitionManager.beginDelayedTransition(
                        uploadedImgContainer,
                        new TransitionSet().addTransition(
                                new ChangeBounds().setDuration(300L)
                                        .setStartDelay(0)
                                        .setInterpolator(new AccelerateInterpolator())
                        )
                );

                params.addRule(RelativeLayout.BELOW, R.id.mainSettingsContainer);
                uploadedImgContainer.setLayoutParams(params);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (String.valueOf(textListInput.getText()).length() == 0) {
                    Toast.makeText(
                            mvpView,
                            mvpView.getResources().getString(R.string.act_main_text_list_empty_toast),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                mvpModel.setFontSize(Integer.parseInt(fontSizeSpan.getText().toString()));
                mvpModel.setLiteralsUnparsed(String.valueOf(textListInput.getText()));
                mvpModel.parseLiteralsString();

                // makes new timer thread to set generated pic after it was made
                final Object lock = new Object();
                new Timer().scheduleAtFixedRate(new TimerTask() {

                    public void run() {
                        try {
                            mvpView.runOnUiThread(new  Runnable() {

                                public void run() {
                                    if (mvpModel.getqGenerated()) {

                                        setImageViewBitmap(mvpModel.getGeneratedBitmap());

                                        setStartButtonStateActive(true);
                                        setStartButtonText(mvpView.getResources().getString(R.string.act_main_start_button));

                                        mvpModel.setqGenerated(false);

                                    }
                                    synchronized (lock) { lock.notify(); }
                                }

                            });

                            try {
                                synchronized (lock) { lock.wait(); }
                            }
                            catch(InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }, 0, 1000);

                setStartButtonStateActive(false);
                setStartButtonText(mvpView.getResources().getString(R.string.act_main_start_button_generating));

                mvpModel.startArtGenerationAsync();
            }
        });
    }

    private void setImageViewBitmap(@NonNull Bitmap bitmap) {
        uploadedImgView.setImageBitmap(bitmap);
    }

    @Override
    public void setImageViewData(@NonNull Uri data) {
        uploadedImgView.setImageURI(data);
        updateUpoadImgInfo(new File(data.getPath()).getName());
        setStartButtonStateActive(true);
        mvpModel.setUploadedImgData(data);

        Toast.makeText(
                mvpView,
                mvpView.getResources().getString(R.string.act_main_fullscreen_info_toast),
                Toast.LENGTH_LONG
        ).show();
    }

    @SuppressLint("SetTextI18n")
    private void updateUpoadImgInfo(String uploadedImgName) {
        if (uploadedImgName.length() > 15) uploadedImgName = uploadedImgName.substring(0, 14) + "…";
        uploadImgInfo.setText("Image \'" + uploadedImgName + "\' successfully uploaded");
    }

    private void setStartButtonStateActive(boolean state) {
        startButton.setEnabled(state);
        if (state) startButton.setTextColor(mvpView.getResources().getColor(R.color.primaryText));
        else startButton.setTextColor(mvpView.getResources().getColor(R.color.secondaryText));
    }

    private void setStartButtonText(@NonNull String text) {
        startButton.setText(text);
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