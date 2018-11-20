package com.kooloco.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by hlink on 18/6/18.
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {


    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
