package com.ordolabs.asciiArt.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.ordolabs.asciiArt.data.MainModel;
import com.ordolabs.asciiArt.ui.MainPresenter;

import java.io.IOException;
import java.util.Random;

/**
 * Created by ordogod on 16.05.19.
 **/

public class ArtGenerator extends AsyncTask<Void, Void, String> {

    private MainModel<MainPresenter> mvpModel;

    public ArtGenerator(@NonNull MainModel mvpModel) {
        super();
        this.mvpModel = mvpModel;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(Void... voids) {
        generateArt();
        return null;
    }

    private void generateArt() {

        Bitmap initImgBitmap;
        Canvas generatedCanvas;
        Paint textPaint = null;
        CustomColor[][] avgColorsGrid;

        Random rnd = new Random();

        int imgW;
        int imgH;

        initImgBitmap = getInitialImageBitmap();

        if (initImgBitmap != null) {
            imgW = initImgBitmap.getWidth();
            imgH = initImgBitmap.getHeight();
        }
        else return;

        Bitmap generatedBitmap = Bitmap.createBitmap(imgW, imgH, Bitmap.Config.ARGB_8888);
        generatedCanvas = new Canvas(generatedBitmap);

        generatedCanvas.drawARGB(255, 0, 0, 0);
        textPaint = getCustomizedPaint();

        int symbH = (int) (mvpModel.getFontSize() - Math.floor(mvpModel.getFontSize() * 0.2d));
        int symbW = (int) textPaint.measureText("#") + 1;

        avgColorsGrid = new CustomColor[imgH / symbH][];

        for (int i = 0; i < avgColorsGrid.length * symbH; i++) {
            avgColorsGrid[i / symbH] = new CustomColor[imgW / symbW];
            for (int j = 0; j < avgColorsGrid[0].length * symbW; j++) {
                avgColorsGrid[i / symbH][j / symbW] = getAvgPixelsColor(i, j, symbW, symbH, imgW, imgH, initImgBitmap);
                j = j + symbW - 1;
            }
            i = i + symbH - 1;
        }

        String currentLiteral;

        for (int i = 0; i < avgColorsGrid.length; i++) {
            for (int j = 0; j < avgColorsGrid[0].length; j++) {

                currentLiteral = getRandomLiteralFromList(rnd);

                if (currentLiteral.length() == 1) {
                    textPaint.setARGB(255,
                            avgColorsGrid[i][j].getR(),
                            avgColorsGrid[i][j].getG(),
                            avgColorsGrid[i][j].getB()
                    );
                    generatedCanvas.drawText(currentLiteral, j * symbW, i * symbH, textPaint);
                }

                else {
                    int availableCellsInRow;

                    if (j + currentLiteral.length() <= avgColorsGrid[0].length)
                        availableCellsInRow = currentLiteral.length();
                    else
                        availableCellsInRow = avgColorsGrid[0].length - j - 1;

                    for (int k = 0; k < availableCellsInRow; k++) {
                        textPaint.setARGB(255,
                                avgColorsGrid[i][j + k].getR(),
                                avgColorsGrid[i][j + k].getG(),
                                avgColorsGrid[i][j + k].getB()
                        );
                        generatedCanvas.drawText(String.valueOf(currentLiteral.charAt(k)), (j + k) * symbW, i * symbH, textPaint);
                    }

                    j = j + currentLiteral.length() - 1;
                    if (j >= avgColorsGrid[0].length) break;
                }
            }
        }

        mvpModel.setGeneratedBitmap(generatedBitmap);
        mvpModel.setqGenerated(true);
    }

    private Bitmap getInitialImageBitmap() {

        Bitmap initImgBitmap = null;
        try {
            initImgBitmap = MediaStore.Images.Media.getBitmap(
                    mvpModel.getPresenter().getMvpView().getContentResolver(),
                    mvpModel.getUploadedImgData()
            );
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return initImgBitmap;
    }

    private Paint getCustomizedPaint() {

        Paint paint = new Paint();
        paint.setTextSize(mvpModel.getFontSize());
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.create("Consolas", Typeface.NORMAL));

        return paint;
    }


    private CustomColor getAvgPixelsColor(int startI, int startJ, int symbW, int symbH, int imgW, int imgH, Bitmap initBitmap) {

        CustomColor avgPixel = new CustomColor(0, 0, 0);
        int currentPixel;

        for (int i = startI; i < startI + symbH && i < imgH; i++) {
            for (int j = startJ; j < startJ + symbW && j < imgW; j++) {
                currentPixel = initBitmap.getPixel(j, i);
                avgPixel.increaseEachBy(
                        Color.red(currentPixel),
                        Color.green(currentPixel),
                        Color.blue(currentPixel)
                );
            }
        }

        avgPixel.setEachAvg(symbW * symbH);
        return avgPixel;
    }

    private String getRandomLiteralFromList(Random rnd) {
        return mvpModel.getLiteralsArr()[rnd.nextInt(mvpModel.getLiteralsArr().length)];
    }
}
