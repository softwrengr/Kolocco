package com.kooloco.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink21 on 7/7/16.
 */
public class ResponseEntity<TData> {


    @SerializedName("code")
    @Expose
    public int responseCode;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("data")
    @Expose
    public TData data;


}
