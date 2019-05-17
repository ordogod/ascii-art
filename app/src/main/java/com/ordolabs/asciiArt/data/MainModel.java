package com.ordolabs.asciiArt.data;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.ordolabs.asciiArt.base.BaseModel;
import com.ordolabs.asciiArt.ui.MainPresenter;
import com.ordolabs.asciiArt.utils.ArtGenerator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ordogod on 12.05.19.
 **/

public class MainModel<P extends MainPresenter> extends BaseModel<P> implements ModelContracts.MainModel<P> {

    private int fontSize;
    private String literalsUnparsed;
    private String[] literalsArr;

    private Uri uploadedImgData;
    private Bitmap generatedBitmap;

    private boolean qGenerated;
    private boolean qTimeoutHandleSet;

    private TimerTask timeoutHandler;

    private int RESULT_LOAD_IMAGE;

    public MainModel(P mvpPresenter) {
        attachPresenter(mvpPresenter);
        qGenerated = false;
        qTimeoutHandleSet = false;
        RESULT_LOAD_IMAGE = 1;
    }

    @Override
    public void attachPresenter(P mvpPresenter) {
        super.attachPresenter(mvpPresenter);
    }

    @Override
    public void detachPresenter() {
        super.detachPresenter();
    }

    @Override
    public boolean isPresenterAttached() {
        return super.isPresenterAttached();
    }

    @Override
    public P getPresenter() {
        return super.getPresenter();
    }

    public String[] getLiteralsArr() {
        return literalsArr;
    }

    public void setLiteralsUnparsed(String literalsUnparsed) {
        this.literalsUnparsed = literalsUnparsed;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public Uri getUploadedImgData() {
        return uploadedImgData;
    }

    public void setUploadedImgData(Uri uploadedImgData) {
        this.uploadedImgData = uploadedImgData;
    }

    public int getRESULT_LOAD_IMAGE() {
        return RESULT_LOAD_IMAGE;
    }

    public Bitmap getGeneratedBitmap() {
        return generatedBitmap;
    }

    public void setGeneratedBitmap(Bitmap generatedBitmap) {
        this.generatedBitmap = generatedBitmap;
    }

    public boolean getqGenerated() {
        return qGenerated;
    }

    public void setqGenerated(boolean qGenerated) {
        this.qGenerated = qGenerated;
    }


    @Override
    public boolean isInputInRangeIncludingBoth(int min, int max, int input) {
        if (min <= max) {
            if (min <= input && input <= max) return true;
        }
        else {
            throw new IllegalArgumentException("Invalid input range. First argument must be less\\equal to second one.");
        }
        return false;
    }

    @Override
    public void parseLiteralsString() {

        int literalsCount = getLiteralsCount(literalsUnparsed);
        String[] literals = new String[literalsCount];

        for (int i = 0; i < literalsUnparsed.length(); i++) {

            if (literalsUnparsed.charAt(i) == ']') continue;

            if (literalsUnparsed.charAt(i) == '[') {

                String currentLiteral = getLiteralInsideBrackets(i, literalsUnparsed);
                if (currentLiteral.length() != 0) {
                    literals[i] = currentLiteral;
                    i += currentLiteral.length();
                }
                else if (literalsUnparsed.charAt(i+1) != ']') literals[i] = "]";
            }
            else literals[i] = literalsUnparsed.charAt(i) + "";

        }

        this.literalsArr = literals;
    }

    private int getLiteralsCount(String literalsUnparsed) {

        int literalsCount = 0;
        String currentLiteral ;

        for (int i = 0; i < literalsUnparsed.length(); i++) {

            if (literalsUnparsed.charAt(i) == ']') continue;

            if (literalsUnparsed.charAt(i) == '[') {
                currentLiteral = getLiteralInsideBrackets(i, literalsUnparsed);
                literalsCount++;
                if (literalsUnparsed.charAt(i+1) != ']') i += currentLiteral.length();
            }
            else literalsCount++;

        }
        return literalsCount;
    }

    private String getLiteralInsideBrackets (int openBracketsIndex, String literalsUnparsed) {

        String literal = "";

        for (int i = openBracketsIndex + 1; i < literalsUnparsed.length(); i++) {

            if (i == literalsUnparsed.length() - 1 && literalsUnparsed.charAt(i) != ']') return "";

            if (literalsUnparsed.charAt(i) != ']') {
                literal += literalsUnparsed.charAt(i);
            }
            else return literal;

        }
        return "";
    }

    @Override
    public Intent getIntentForPictureUploading() {
        return new Intent(Intent.ACTION_PICK)
                .setType("image/*")
                .putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpg", "image/png", "image/jpeg"});
    }

    @Override
    public void startArtGenerationAsync() {
        new ArtGenerator(this).execute();
    }
}
