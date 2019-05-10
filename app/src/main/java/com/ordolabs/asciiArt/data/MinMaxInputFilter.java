package com.ordolabs.asciiArt.data;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by ordogod on 10.05.19.
 */
public class MinMaxInputFilter implements InputFilter {

    private int intMin, intMax;

    public MinMaxInputFilter(int minValue, int maxValue) {
        this.intMin = minValue;
        this.intMax = maxValue;
    }

    public MinMaxInputFilter(String minValue, String maxValue) {
        this.intMin = Integer.parseInt(minValue);
        this.intMax = Integer.parseInt(maxValue);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(intMin, intMax, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}