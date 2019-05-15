package com.ordolabs.asciiArt.data;

import android.content.Intent;

import com.ordolabs.asciiArt.base.BaseMvpModel;
import com.ordolabs.asciiArt.ui.MainPresenter;

/**
 * Created by ordogod on 13.05.19.
 **/

public interface ModelContracts {

    interface MainModel<P extends MainPresenter> extends BaseMvpModel<P> {

        boolean isInputInRangeIncludingBoth(int min, int max, int input);

        void parseLiteralsString();

        Intent getIntentForGalleryPictureUploading();

        void generateArt();

        @Override
        void attachPresenter(P mvpPresenter);

        @Override
        void detachPresenter();

        @Override
        boolean isPresenterAttached();

        @Override
        P getPresenterView();
    }
}
