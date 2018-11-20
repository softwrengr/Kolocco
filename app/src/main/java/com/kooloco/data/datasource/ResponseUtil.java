package com.kooloco.data.datasource;

import com.kooloco.model.Response;

/**
 * Created by hlink44 on 16/11/17.
 */

public class ResponseUtil<T> {
    public Response<T> getResponse(T t, String message) {
        Response<T> tResponse = new Response<>();
        tResponse.setResponseCode(1);
        tResponse.setMessage("");
        tResponse.setData(t);
        return tResponse;
    }

    public Response<T> getResponse(T t) {
        return getResponse(t, "");
    }
}
