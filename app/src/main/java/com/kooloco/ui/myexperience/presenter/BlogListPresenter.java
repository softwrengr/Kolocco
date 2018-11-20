package com.kooloco.ui.myexperience.presenter;/**
 * Created by hlink44 on 31/10/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.datasource.DatabaseCacheDataSource;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.BlogDetails;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.myexperience.view.BlogDetailsView;
import com.kooloco.ui.myexperience.view.BlogListView;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class BlogListPresenter extends BasePresenter<BlogListView> {

    @Inject
    KoolocoRepository kooocoRepository;

    @Inject
    Session session;

    @Inject
    DatabaseCacheDataSource databaseCacheDataSource;

    @Inject
    public BlogListPresenter() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void openBlogDetails(BlogDetails blogDetails) {

        Bundle bundle=new Bundle();

        bundle.putString("blogDetails",new Gson().toJson(blogDetails));

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.BlogDetails).addBundle(bundle).start();
    }

    public void getData(int page, boolean isShowLoader) {
        if (isShowLoader) {
            view.showLoader();
        }

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("page", "" + page);

        kooocoRepository.getBlogList(map, page == 1).subscribe(new SubscribeWithView<Response<List<BlogDetails>>>(view) {
            @Override
            public void onSuccess(Response<List<BlogDetails>> listResponse) {
                if (isShowLoader) {
                    view.hideLoader();
                }
                view.setData(listResponse.getData(), page);
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() != 0) {
                        super.onError(e);
                    }
                } else {
                    super.onError(e);
                }
                if (isShowLoader) {
                    view.hideLoader();
                }
                view.setData(new ArrayList<BlogDetails>(), page);
            }
        });
    }

    public void getDataLocal() {
        view.setData(databaseCacheDataSource.getBlogList().getData(), 1);
    }

    public void setBlogLike(String blogId, int position) {

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("blog_id", blogId);
        map.put("status", "like");

        kooocoRepository.bloglike(map).subscribe(new SubscribeWithView<Response<BlogDetails>>(view) {
            @Override
            public void onSuccess(Response<BlogDetails> listResponse) {
                view.hideLoader();
                view.updateDataBlog(listResponse.getData(), position);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }
}
