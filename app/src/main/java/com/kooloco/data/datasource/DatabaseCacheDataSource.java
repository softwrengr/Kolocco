package com.kooloco.data.datasource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kooloco.data.URLFactory;
import com.kooloco.model.BlogDetails;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.DatabaseStore;
import com.kooloco.model.ExpFavData;
import com.kooloco.model.ExpFavDataBase;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.HomeLocalAndExpResponse;
import com.kooloco.model.HomeNewResponse;
import com.kooloco.model.Response;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;

/**
 * Created by hlink on 19/1/18.
 */

@Singleton
public class DatabaseCacheDataSource {

    @Inject
    public DatabaseCacheDataSource() {
    }


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

    public Response<List<ExperienceDetails>> getExperienceList() {
        Response<List<ExperienceDetails>> listResponse = new Response<>();
        listResponse.setResponseCode(1);
        listResponse.setMessage("Experience list");
        listResponse.setData(new ArrayList<>());

        DatabaseStore apiName = Realm.getDefaultInstance().where(DatabaseStore.class).equalTo("apiName", URLFactory.EXPERINCELIST).findFirst();
        if (apiName != null) {
            if (apiName.getResponse() != null) {
                String response = apiName.getResponse();
                listResponse = new Gson().fromJson(response, new TypeToken<Response<List<ExperienceDetails>>>() {
                }.getType());


            }
        }

        return listResponse;
    }

    public Response<List<BlogDetails>> getBlogList() {
        Response<List<BlogDetails>> listResponse = new Response<>();
        listResponse.setResponseCode(1);
        listResponse.setMessage("Experience list");
        listResponse.setData(new ArrayList<>());

        DatabaseStore apiName = Realm.getDefaultInstance().where(DatabaseStore.class).equalTo("apiName", URLFactory.BLOGLIST).findFirst();
        if (apiName != null) {
            if (apiName.getResponse() != null) {
                String response = apiName.getResponse();
                listResponse = new Gson().fromJson(response, new TypeToken<Response<List<BlogDetails>>>() {
                }.getType());


            }
        }

        return listResponse;
    }


    public Response<HomeNewResponse> getVisitorHomeNew() {

        Response<HomeNewResponse> listResponse = new Response<>();
        listResponse.setResponseCode(1);
        listResponse.setMessage("Experience list");
        listResponse.setData(new HomeNewResponse());

        DatabaseStore apiName = Realm.getDefaultInstance().where(DatabaseStore.class).equalTo("apiName", URLFactory.VISITORHOMENEW).findFirst();

        if (apiName != null) {
            if (apiName.getResponse() != null) {
                String response = apiName.getResponse();
                listResponse = new Gson().fromJson(response, new TypeToken<Response<HomeNewResponse>>() {
                }.getType());
            }
        }

        return listResponse;
    }

    public Response<HomeLocalAndExpResponse> getVisitorHomeOnePage() {

        Response<HomeLocalAndExpResponse> listResponse = new Response<>();
        listResponse.setResponseCode(1);
        listResponse.setMessage("Experience list");
        listResponse.setData(new HomeLocalAndExpResponse());

        DatabaseStore apiName = Realm.getDefaultInstance().where(DatabaseStore.class).equalTo("apiName", URLFactory.VISITORHOMEGETONEPAGE).findFirst();

        if (apiName != null) {
            if (apiName.getResponse() != null) {
                String response = apiName.getResponse();
                listResponse = new Gson().fromJson(response, new TypeToken<Response<HomeLocalAndExpResponse>>() {
                }.getType());
            }
        }

        return listResponse;
    }

    /**
     * @param apiName  Api Url
     * @param response String Response
     */
    public void setDataCache(String apiName, String response) {

        Realm defaultInstance = Realm.getDefaultInstance();
        defaultInstance.executeTransaction(realm -> {
            DatabaseStore databaseStore = new DatabaseStore();
            databaseStore.setApiName(apiName);
            databaseStore.setResponse(response);
            realm.copyToRealmOrUpdate(databaseStore);
        });

    }

    public void setDataFavExp(String expId, String isFav) {
        Realm defaultInstance = Realm.getDefaultInstance();
        defaultInstance.executeTransaction(realm -> {
            ExpFavDataBase expFavDataBase = new ExpFavDataBase();
            expFavDataBase.setId(expId);
            expFavDataBase.setIsFav(isFav);
            realm.copyToRealmOrUpdate(expFavDataBase);
        });
    }


    public ExpFavDataBase getDataFavExp(String expId, String isFav) {

        ExpFavDataBase expFavDataBase = Realm.getDefaultInstance().where(ExpFavDataBase.class).equalTo("id", expId).findFirst();

        if (expFavDataBase == null) {
            expFavDataBase=new ExpFavDataBase();
            expFavDataBase.setId(expId);
            expFavDataBase.setIsFav(isFav);
            setDataFavExp(expId, isFav);
        }
        return expFavDataBase;

    }

}
