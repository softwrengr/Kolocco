package com.kooloco.data.datasource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kooloco.data.URLFactory;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.DatabaseStore;
import com.kooloco.model.Response;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import io.realm.Realm;

/**
 * Created by hlink on 19/1/18.
 */

@Singleton
public class DatabaseCacheUtil {

    public Response<List<DashboardDetails>> getVsitorHome() {
        Response<List<DashboardDetails>> listResponse = new Response<>();

        listResponse.setResponseCode(1);
        listResponse.setMessage("Local list");
        listResponse.setData(new ArrayList<>());

        DatabaseStore apiName = Realm.getDefaultInstance().where(DatabaseStore.class).equalTo("apiName", URLFactory.VISITORHOME).findFirst();
        if (apiName != null) {
            if (apiName.getResponse() != null) {
                String response = apiName.getResponse();
                listResponse = new Gson().fromJson(response, new TypeToken<Response<List<DashboardDetails>>>() {
                }.getType());


            }
        }

        return listResponse;
    }
}
