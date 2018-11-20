package com.kooloco.ui.myexperience.presenter;/**
 * Created by hlink44 on 1/11/17.
 */

import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.model.BlogDetails;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.Response;
import com.kooloco.model.Review;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.myexperience.view.BlogDetailsView;
import com.kooloco.ui.visitor.organizationDetails.view.LocalVisitorMapView;
import com.kooloco.util.SubscribeWithView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PerActivity
public class BlogDetailsPresenter extends BasePresenter<BlogDetailsView> {

    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;


    @Inject
    public BlogDetailsPresenter() {

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

    public void getComments(String id) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("blog_id", id);

        koolocoRepository.blogComment(map).subscribe(new SubscribeWithView<Response<List<Review>>>(view) {
            @Override
            public void onSuccess(Response<List<Review>> listResponse) {
                view.hideLoader();
                view.setDataReview(listResponse.getData());
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
                view.setDataReview(new ArrayList<>());
            }
        });
    }

    public void addComent(String id, String comment) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("blog_id", id);
        map.put("user_id", session.getUser().getId());
        map.put("comment", comment);

        koolocoRepository.addComment(map).subscribe(new SubscribeWithView<Response<Review>>(view) {
            @Override
            public void onSuccess(Response<Review> listResponse) {
                view.hideLoader();
                view.setDataComments(listResponse.getData());
            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void openMapScreen(BlogDetails blogDetails) {
        DashboardDetails dashboardDetails = new DashboardDetails();


        dashboardDetails.setFirstname(blogDetails.getFirstname());
        dashboardDetails.setLastname(blogDetails.getLastname());

        dashboardDetails.setImageUrl(blogDetails.getProfileImage());

        dashboardDetails.setLocalLocation(blogDetails.getMeetingAddress());

        dashboardDetails.setLatitude(blogDetails.getMeetingLatitude());
        dashboardDetails.setLongitude(blogDetails.getMeetingLongitude());

        navigator.openLocalAndVistorMap().hasData(new Passable<LocalVisitorMapView>() {
            @Override
            public void passData(LocalVisitorMapView localVisitorMapView) {
                localVisitorMapView.setData(dashboardDetails);
            }
        }).replace(true);
    }
}
