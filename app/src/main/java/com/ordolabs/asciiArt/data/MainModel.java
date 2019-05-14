package com.ordolabs.asciiArt.data;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;

import com.ordolabs.asciiArt.ui.base.BaseModel;
import com.ordolabs.asciiArt.ui.main.MainPresenter;
import com.ordolabs.asciiArt.utils.Pixel;

import java.io.IOException;
import java.util.Random;

/**
 * Created by ordogod on 12.05.19.
 **/

public class MainModel<P extends MainPresenter> extends BaseModel<P> implements ModelContracts.MainModel<P> {

    private String literalsUnparsed;
    private String[] literalsArr;
    private int fontSize;
    private Uri uploadedImgData;

    private int RESULT_LOAD_IMAGE = 1;

    public MainModel(P mvpPresenter) {
        attachPresenter(mvpPresenter);
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
    public P getPresenterView() {
        return super.getPresenterView();
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
    public Intent getIntentForGalleryPictureUploading() {
        return new Intent(Intent.ACTION_PICK)
                .setType("image/*")
                .putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpg", "image/png", "image/jpeg"});
    }

    @Override
    public void generateArt() {

        Bitmap bitmap = null;
        Pixel[][] pixels;
        Canvas c;
        Paint p;
        Pixel[][] cellSymbGrid;

        Random rnd = new Random();

        int imgW;
        int imgH;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(mvpPresenter.getMvpView().getContentResolver(), uploadedImgData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert bitmap != null;

        imgW = bitmap.getWidth();
        imgH = bitmap.getHeight();

        Bitmap bitmapCanvas = Bitmap.createBitmap(imgW, imgH, Bitmap.Config.ARGB_8888);
        c = new Canvas(bitmapCanvas);
        p = new Paint();

        Paint fillAll = new Paint();
        fillAll.setColor(Color.BLACK);
        fillAll.setStyle(Paint.Style.FILL);

        c.drawPaint(fillAll);
        fillAll = null;

        p = new Paint();
        p.setTextSize(fontSize);
        p.setStyle(Paint.Style.FILL);
        p.setAntiAlias(true);
        p.setTypeface(Typeface.create("Consolas", Typeface.NORMAL));

        pixels = new Pixel[imgH][];
        int pixelColor;

        for (int i = 0; i < imgH; i++) {
            pixels[i] = new Pixel[imgW];
            for (int j = 0; j < imgW; j++) {
                pixelColor = bitmap.getPixel(j, i);
                pixels[i][j] = new Pixel(
                        Color.red(pixelColor),
                        Color.green(pixelColor),
                        Color.blue(pixelColor)
                );
            }
        }

        int symbH = (int) (fontSize - Math.floor(fontSize * 0.2d));
        int symbW = (int) p.measureText("#") + 1;

        cellSymbGrid = new Pixel[imgH / symbH][];

        for (int i = 0; i < cellSymbGrid.length * symbH; i++) {
            cellSymbGrid[i / symbH] = new Pixel[imgW / symbW];
            for (int j = 0; j < cellSymbGrid[0].length * symbW; j++) {
                cellSymbGrid[i / symbH][j / symbW] = getAvgPixelsColor(i, j, symbW, symbH, imgW, imgH, pixels);
                j = j + symbW - 1;
            }
            i = i + symbH - 1;
        }

        pixels = null;
        String currentLiteral;

        for (int i = 0; i < cellSymbGrid.length; i++) {
            for (int j = 0; j < cellSymbGrid[0].length; j++) {

                currentLiteral = getRandomLiteralFromList(rnd);
                if (currentLiteral.length() == 1) {
                    p.setARGB(255,
                            cellSymbGrid[i][j].getR(),
                            cellSymbGrid[i][j].getG(),
                            cellSymbGrid[i][j].getB()
                    );
                }
                else {
                    Pixel avgForNonPrimeLiteral = new Pixel(0, 0, 0);
                    int freeCellsInRow;
                    if (j + currentLiteral.length() <= cellSymbGrid[0].length - 1) freeCellsInRow = currentLiteral.length();
                    else freeCellsInRow = cellSymbGrid[0].length - j - 1;
                    for (int k = 0; k < freeCellsInRow; k++) {
                        avgForNonPrimeLiteral.increaseEachBy(cellSymbGrid[i][j + k]);
                    }
                    avgForNonPrimeLiteral.setEachAvg(freeCellsInRow);
                    p.setARGB(255,
                            avgForNonPrimeLiteral.getR(),
                            avgForNonPrimeLiteral.getG(),
                            avgForNonPrimeLiteral.getB()
                    );
                }

                c.drawText(currentLiteral, j * symbW, i * symbH, p);
                if (currentLiteral.length() != 1) {
                    j = j + currentLiteral.length() - 1;
                    if (j >= cellSymbGrid[0].length) break;
                }
                mvpPresenter.setImageViewCanvas(bitmapCanvas);
            }
        }

        mvpPresenter.setImageViewCanvas(bitmapCanvas);
    }

    private Pixel getAvgPixelsColor(int startI, int startJ, int symbW, int symbH, int imgW, int imgH, Pixel[][] pixels) {

        Pixel avgPixel = new Pixel(0, 0, 0);

        for (int i = startI; i < startI + symbH && i < imgH; i++) {
            for (int j = startJ; j < startJ + symbW && j < imgW; j++) {
                avgPixel.increaseEachBy(pixels[i][j]);
            }
        }

        avgPixel.setEachAvg(symbW * symbH);
        return avgPixel;
    }

    private String getRandomLiteralFromList(Random rnd) {
        return literalsArr[rnd.nextInt(literalsArr.length)];
    }
}
