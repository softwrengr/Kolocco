package com.kooloco.GplusHelper;

import android.support.annotation.Nullable;

/**
 * Created by Chirag on 02/03/2016.
 */
public abstract class IGooglePlusTaskComplete<T>{
    public abstract void onTaskComplete(boolean success,@Nullable T data);
}
