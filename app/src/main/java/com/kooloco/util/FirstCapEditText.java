package com.kooloco.util;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by hlink44 on 16/8/17.
 */

public class FirstCapEditText extends AppCompatEditText {
    public FirstCapEditText(Context context) {
        super(context);
        init();
    }


    public FirstCapEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FirstCapEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if ((!s.equals(s.toUpperCase()) && s.length() == 1)) {
                    s = s.toUpperCase();
                    FirstCapEditText.this.setText(s);
                    FirstCapEditText.this.setSelection(s.length());
                }
            }
        });
    }

}
