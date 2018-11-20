package com.kooloco.model;

import android.util.Log;

/**
 * Created by hlink on 25/7/18.
 */
public class TempDemo1 implements TempDemo, TestDemo2 {

    public void print1() {
        Log.e(":::", "B Print");
    }

    @Override
    public void disp() {
        Log.e(":::", "B Disp");

    }

}
