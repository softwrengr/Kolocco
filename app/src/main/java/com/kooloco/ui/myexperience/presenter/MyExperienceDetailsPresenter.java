package com.kooloco.ui.myexperience.presenter;/**
 * Created by hlink44 on 28/9/17.
 */

import android.os.Bundle;

import com.google.gson.Gson;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.PerActivity;
import com.kooloco.model.BlogDetails;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Response;
import com.kooloco.ui.base.BasePresenter;

import javax.inject.Inject;


import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.myexperience.view.CreateBlogView;
import com.kooloco.ui.myexperience.view.MyExperienceDetailsView;
import com.kooloco.ui.navigation.AppNavigator;
import com.kooloco.ui.visitor.organizationDetails.view.LocalVisitorMapView;
import com.kooloco.ui.visitor.organizationDetails.view.MettingLocationView;
import com.kooloco.util.SubscribeWithView;

import java.util.HashMap;
import java.util.Map;

@PerActivity
public class MyExperienceDetailsPresenter extends BasePresenter<MyExperienceDetailsView> {
    @Inject
    Session session;

    @Inject
    AppPreferences appPreferences;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public MyExperienceDetailsPresenter() {

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


    public void getData(String orderId) {

        view.showLoader();

        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("order_id", orderId);
        appPreferences.putBoolean("isEditBlog", true);

        koolocoRepository.orderDetailsVisitor(map).subscribe(new SubscribeWithView<Response<OrderDetails>>(view) {
            @Override
            public void onSuccess(Response<OrderDetails> orderDetailsResponse) {
                view.hideLoader();
                view.setData(orderDetailsResponse.getData());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }


    public void opnChat(ReceiverData receiverData) {
        Bundle bundle = new Bundle();
        bundle.putString("receiverData", new Gson().toJson(receiverData));

        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.Chat).addBundle(bundle).start();
    }


    public void openCheckEquipments(String orderId, ReceiverData receiverData,String orderStatus,String chatStatus) {


        navigator.openCheckSportEaquipmentsView().hasData(checkSportEaquipmentsView -> {
            checkSportEaquipmentsView.setOrderId(orderId);
            checkSportEaquipmentsView.setRecevierData(receiverData);
            checkSportEaquipmentsView.setOrderStatus(orderStatus);
            checkSportEaquipmentsView.setChatStatus(chatStatus);

        }).replace(true);
    }

    public void openBlogList() {
        navigator.openBlogListView().replace(true);
    }

    public void openCreateBlog(ExperienceDetails experienceDetails) {

        if (experienceDetails.getIsPublished().equalsIgnoreCase("1")) {
            view.showLoader();
            Map<String, String> map = new HashMap<>();
            map.put("user_id", session.getUser().getId());
            map.put("blog_id", "" + experienceDetails.getBlogId());

            koolocoRepository.blogDetails(map).subscribe(new SubscribeWithView<Response<BlogDetails>>(view) {
                @Override
                public void onSuccess(Response<BlogDetails> listResponse) {
                    view.hideLoader();
                    navigator.openCreateBlogView().hasData(new Passable<CreateBlogView>() {
                        @Override
                        public void passData(CreateBlogView createBlogView) {
                            createBlogView.setExperienceDetails(experienceDetails);
                            createBlogView.setBlogDetails(listResponse.getData());
                        }
                    }).replace(true);

                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    view.hideLoader();
                }
            });

        } else {
            navigator.openCreateBlogView().hasData(new Passable<CreateBlogView>() {
                @Override
                public void passData(CreateBlogView createBlogView) {
                    createBlogView.setExperienceDetails(experienceDetails);
                }
            }).replace(true);

        }

    }

    public void openMapScreen(OrderDetails orderDetails) {

        DashboardDetails dashboardDetails = new DashboardDetails();


        dashboardDetails.setFirstname(orderDetails.getFirstname());
        dashboardDetails.setLastname(orderDetails.getLastname());

        dashboardDetails.setImageUrl(orderDetails.getProfileImage());

        dashboardDetails.setLocalLocation(orderDetails.getMeetingAddress());

        dashboardDetails.setLatitude(orderDetails.getMeetingLatitude());
        dashboardDetails.setLongitude(orderDetails.getMeetingLongitude());

        navigator.openMettingLocalVisitorAndLocalMap().hasData(new Passable<MettingLocationView>() {
            @Override
            public void passData(MettingLocationView localVisitorMapView) {
                localVisitorMapView.setData(dashboardDetails);
            }
        }).replace(true);
    }

    public void openExpDetails(ExpereinceNew expereinceNew) {
        Bundle bundle = new Bundle();
        bundle.putString("expId", expereinceNew.getId());
        navigator.openIsloatedFullActivity().setPage(AppNavigator.Pages.EXPDETAILSLOCAL).addBundle(bundle).start();
    }
}
