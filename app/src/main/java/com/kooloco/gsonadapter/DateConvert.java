package com.kooloco.gsonadapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.kooloco.util.TimeConvertUtils;

import java.lang.reflect.Type;

/**
 * Created by hlink on 16/1/18.
 */

public class DateConvert implements JsonDeserializer<String> {

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if (json.getAsString().isEmpty()) {
            return "";

        } else {
            return TimeConvertUtils.datTimeConvertServerToLocalMain(json.getAsString(), "MM d, yyyy hh:mm:ss a", "MMM d, yyyy hh:mm:ss a");
        }

    }
}
