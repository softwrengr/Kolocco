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

public class StringToBoolean implements JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsString().equalsIgnoreCase("1");
    }
}
