package com.kooloco.gsonadapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.kooloco.constant.Common;

import java.lang.reflect.Type;

/**
 * Created by hlink on 16/1/18.
 */

public class OrderStatusTag implements JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        int defValue = -1;


        if (json.getAsString().equalsIgnoreCase("pending")) {
            defValue = 0;
        } else if (json.getAsString().equalsIgnoreCase("accepted")) {
            defValue = 1;
        } else if (json.getAsString().equalsIgnoreCase("completed")) {
            defValue = 2;
        } else if (json.getAsString().equalsIgnoreCase("rejected")) {
            defValue = 3;
        } else if (json.getAsString().equalsIgnoreCase(Common.OrderDetails.DECLINE)) {
            defValue = 4;
        } else if (json.getAsString().equalsIgnoreCase(Common.OrderDetails.CANCELED)) {
            defValue = 5;
        }


        return defValue;
    }
}
