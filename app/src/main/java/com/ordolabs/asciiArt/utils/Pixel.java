package com.ordolabs.asciiArt.utils;

/**
 * Created by ordogod on 13.05.19.
 **/

public class Pixel {

    private int r;
    private int g;
    private int b;

    public Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Pixel(int hexColor) {

    }

    public void increaseEachBy(int addR, int addG, int addB) {
        r += addR;
        g += addG;
        b += addB;
    }


    public void increaseEachBy(Pixel pix) {
        this.r += pix.r;
        this.g += pix.g;
        this.b += pix.b;
    }

    public void setEachAvg(int sumedPixelsQuantity) {
        if (sumedPixelsQuantity == 0) return;
        this.r = (int) (this.r / sumedPixelsQuantity);
        this.g = (int) (this.g / sumedPixelsQuantity);
        this.b = (int) (this.b / sumedPixelsQuantity);
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
