package com.kooloco.gsonadapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.kooloco.constant.Common;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.navigation.AppNavigator;

import java.lang.reflect.Type;

/**
 * Created by hlink on 16/1/18.
 */

public class NotificationTag implements JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int defValue = -1;

        if (json.getAsString().equalsIgnoreCase("modify_duration")) {
            defValue = 0;
        } else if (json.getAsString().equalsIgnoreCase("modify_objection") || json.getAsString().equalsIgnoreCase("receipt_objection")) {
            defValue = 1;
        } else if (json.getAsString().equalsIgnoreCase(Common.Visitor.ACCEPT_ORDER) || json.getAsString().equalsIgnoreCase(Common.Visitor.COMPLETE_ORDER) || json.getAsString().equalsIgnoreCase(Common.Local.ORDER_REQUEST)) {
            defValue = 2;
        } else if (json.getAsString().equalsIgnoreCase(Common.Visitor.PAYMENT_REQUEST)) {
            defValue = 3;
        } else if (json.getAsString().equalsIgnoreCase(Common.Visitor.ORGANIZATIONREQUEST)) {
            defValue = 4;
        } else if (json.getAsString().equalsIgnoreCase("modify_location")) {
            defValue = 5;
        } else if (json.getAsString().equalsIgnoreCase(Common.Visitor.RATE_TO_LOCAL)) {
            defValue = 6;
        } else if (json.getAsString().equalsIgnoreCase(Common.Visitor.NEW_LOCAL_ADMIN_APPROVE)) {
            defValue = 7;
        }
        return defValue;
    }
}
