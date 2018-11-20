package com.kooloco.ui.isolated;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kooloco.R;
import com.kooloco.Startup;
import com.kooloco.constant.EventBusAction;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Common;
import com.kooloco.core.Session;
import com.kooloco.data.URLFactory;
import com.kooloco.di.component.ActivityComponent;
import com.kooloco.model.AllMonth;
import com.kooloco.model.BlogDetails;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.MonthEarning;
import com.kooloco.model.Order;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.OrganizationVisitor;
import com.kooloco.model.OtherDetailsFieldsSelect;
import com.kooloco.model.ReceiverData;
import com.kooloco.model.Service;
import com.kooloco.model.User;
import com.kooloco.ui.base.RootView;
import com.kooloco.ui.manager.FragmentActionPerformer;
import com.kooloco.ui.manager.Passable;
import com.kooloco.ui.myexperience.view.BlogDetailsView;
import com.kooloco.ui.myexperience.view.MyExperienceDetailsView;
import com.kooloco.ui.navigation.AppNavigationProvider;
import com.kooloco.ui.notification.view.ReciveObjectionDetailsView;
import com.kooloco.ui.profile.view.CurrencyVisitorView;
import com.kooloco.ui.profile.view.OrderDetailsView;
import com.kooloco.ui.visitor.dashboard.view.DashboardView;
import com.kooloco.ui.visitor.dashboard.view.ReceiptView;
import com.kooloco.ui.visitor.organizationDetails.view.MettingLocationView;
import com.kooloco.uilocal.MainLocalActivity;
import com.kooloco.uilocal.Notification.view.ReceivedObjectionLocalView;
import com.kooloco.uilocal.addservice.view.ChooseLanguagesView;
import com.kooloco.uilocal.earnings.view.MonthEarningView;
import com.kooloco.uilocal.home.view.OrderDetailsLocalView;
import com.kooloco.uilocal.modify.view.ModifyAddressView;
import com.kooloco.uilocal.modify.view.ModifyDurationView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

import static com.kooloco.ui.navigation.AppNavigator.Pages.LOCALINCOMPLETE;

/**
 * Created by h-link9 on 21/07/2017.
 */

public class IsolatedFullActivity extends AppNavigationProvider {

    private String orderStatus = "";

    String monthName = "";

    String expDetails = "";

    String blogDetails = "", orgDetails = "";

    String orderId = "";
    String expId = "";

    boolean isPreview = false, isOrgScreen = false;

    @Inject
    Session session;

    String homeDetails = "";

    String notificationId = "";

    String receiverData = "";

    String sportsData = "";
    String onBoardData = "{}";

    String monthEarning = "", monthEarningTotal = "0.00";

    String monthAll = "";

    String expSelectDate = "";

    String webTitle = "", webUrl = "";

    @Inject
    AppPreferences appPreferences;

    @Override
    public int findFragmentPlaceHolder() {
        return R.id.placeHolder;
    }

    @Override
    public int findContentView() {
        return R.layout.activity_isolated_full;
    }

    @Override
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);


        final Pages page = (Pages) getIntent().getSerializableExtra(Common.ACTIVITY_FIRST_PAGE);

        orderStatus = getIntent().getStringExtra("orderStatus");

        monthName = getIntent().getStringExtra("monthName");

        homeDetails = getIntent().getStringExtra("localDetails");

        expDetails = getIntent().getStringExtra("isExpStatus");

        blogDetails = getIntent().getStringExtra("blogDetails");

        orgDetails = getIntent().getStringExtra("orgDetails");

        orderId = getIntent().getStringExtra("orderId");

        expId = getIntent().getStringExtra("expId");

        notificationId = getIntent().getStringExtra("notificationId");

        isPreview = getIntent().getBooleanExtra("isPreviewScreen", false);
        isOrgScreen = getIntent().getBooleanExtra("isOrgScreen", false);

        receiverData = getIntent().getStringExtra("receiverData");

        sportsData = getIntent().getStringExtra("sports");

        onBoardData = getIntent().getStringExtra("onBordData");

        monthEarning = getIntent().getStringExtra("monthEarning");

        monthEarningTotal = getIntent().getStringExtra("monthEarningTotal");

        monthAll = getIntent().getStringExtra("monthAll");

        expSelectDate = getIntent().getStringExtra(Common.EXPSELECTDATE);

        webTitle = getIntent().getStringExtra(com.kooloco.constant.Common.WEBTITLE);
        webUrl = getIntent().getStringExtra(com.kooloco.constant.Common.WEBURL);


        if (page != null) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentActionPerformer fragmentActionPerformer = navigatePage(page);
                    if (fragmentActionPerformer != null) {
                        fragmentActionPerformer
                                .setBundle(getIntent().getExtras())
                                .add(false);
                    }

                }
            }, 100);

        }
    }

    private FragmentActionPerformer<? extends RootView> navigatePage(final Pages page) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 50);
        switch (page) {
            case Dashboard:
                return openDashboard().hasData(new Passable<DashboardView>() {
                    @Override
                    public void passData(DashboardView dashboardView) {
                        dashboardView.setDataNew(new Gson().fromJson(homeDetails, DashboardDetails.class));
                        dashboardView.setIsPreview(isPreview);
                        dashboardView.setIsOrg(isOrgScreen);
                    }
                });
            case Notification:
                return openNotificationView();
            case Chat:
                return openChatView().hasData(chatView -> {
                    chatView.setReceiverData(new Gson().fromJson(receiverData, ReceiverData.class));
                });
            case ViewProfile:
                return openViewProfileView();
            case MyExpeDet:
                return openMyExpiranceDetailsView().hasData(new Passable<MyExperienceDetailsView>() {
                    @Override
                    public void passData(MyExperienceDetailsView myExperienceDetailsView) {
                        myExperienceDetailsView.setExpDetails(new Gson().fromJson(expDetails, ExperienceDetails.class));
                    }
                });
            case OrderHistory:
                return openOrderHistoryView();
            case InviteFriends:
                return openInviteView();
            case Fav:
                return openFavoritesView();
            case Setting:
                return openSettingView();
            case HelpAndFaq:
                return openHelpAndFaqView();
            case BecomeLocalWebView:
                return openBecomeLocalWebView();
            case BecomeLocal:
                User user = session.getUser();
                if (user != null) {
                    if (user.getSignupStep().equalsIgnoreCase("0")) {
                        return openBecomeALocalView();
                    }/* else if (user.getSignupStep().equalsIgnoreCase("1")) {
                        return openAddImageView();
                    } else if (user.getSignupStep().equalsIgnoreCase("2")) {
                        return openUploadCertificateView();
                    } else if (user.getSignupStep().equalsIgnoreCase("3")) {
                        return openUploadAchievementsView();
                    } else if (user.getSignupStep().equalsIgnoreCase("4")) {
                        return openChooseLangaugeView();
                    } else if (user.getSignupStep().equalsIgnoreCase("5")) {
                        return openChooseSportActivityNewView();
                    } else if (user.getSignupStep().equalsIgnoreCase("6")) {
                        return openCreatYourExpView();
                    } else if (user.getSignupStep().equalsIgnoreCase("7")) {
                        return openSportPriceRulesView();
                    } else if (user.getSignupStep().equalsIgnoreCase("8")) {
                        return openChooseSportEquipmentsView();
                    } else if (user.getSignupStep().equalsIgnoreCase("9")) {
                        return openSetAvilabilitesView();
                    } else if (user.getSignupStep().equalsIgnoreCase("10")) {
                        return openCancellationPoliciesView();
                    } else if (user.getSignupStep().equalsIgnoreCase("11")) {
                        return openSetLocationView();
                    }*/ else {
                        finishAffinity();
                        startActivity(new Intent(this, MainLocalActivity.class));
                        return null;

                    }
                }
            case OrdeDetailsLocal:
                return openOrderDetailsLocalView().hasData(new Passable<OrderDetailsLocalView>() {
                    @Override
                    public void passData(OrderDetailsLocalView orderDetailsLocalView) {
                        orderDetailsLocalView.setOrder(new Gson().fromJson(orderStatus, Order.class));
                    }
                });
            case AcceptOrderDetails:
                return openAcceptOrderDetailsLocalView().hasData(acceptOrderDetailsView -> acceptOrderDetailsView.setOrder(new Gson().fromJson(orderStatus, Order.class)));
            case AllMonthAppEarning:
                return openAllMonthEarningView().hasData(allMonthView -> {
                    allMonthView.setEarningData(new Gson().fromJson(monthEarning, new TypeToken<List<MonthEarning>>() {
                    }.getType()));
                    allMonthView.setTotalAmount(monthEarningTotal);
                });
            case ViewProfileLocal:
                return openViewProfileLocalView();
            case NotificationLocal:
                return openNotificationLocalView();
            case CreateYourOrganization:
                return openCreateOrganization();
            case SetAvabilities:
                return openSetAvabilitiesProfileView();
            case EditProfile:
                return openEditProfileView();
            case RateAndReview:
                return openLocalRateAndReviewView().hasData(rateAndReviewView -> rateAndReviewView.setData(new Gson().fromJson(orderStatus, Order.class)));
            case ChooseLanguage:
                return openChooseLangaugeView().hasData(new Passable<ChooseLanguagesView>() {
                    @Override
                    public void passData(ChooseLanguagesView chooseLanguagesView) {
                        chooseLanguagesView.setIsEdit(true);
                    }
                });
            case MONTHEARNING:
                return openMonthEarningView().hasData(new Passable<MonthEarningView>() {
                    @Override
                    public void passData(MonthEarningView monthEarningView) {
                        monthEarningView.setAllMonth(new Gson().fromJson(monthAll, AllMonth.class));
                    }
                });
            case BlogList:
                return openBlogListView();
            case BlogDetails:
                return openBlogDetailsView().hasData(new Passable<BlogDetailsView>() {
                    @Override
                    public void passData(BlogDetailsView blogDetailsView) {
                        blogDetailsView.setData(new Gson().fromJson(blogDetails, BlogDetails.class));
                    }
                });
            case MODIFYDURATIONLOCATION:
                return openModifyDuration().hasData(new Passable<ModifyDurationView>() {
                    @Override
                    public void passData(ModifyDurationView modifyDurationView) {
                        modifyDurationView.setIsLocation(true);
                        modifyDurationView.setOrder(new Gson().fromJson(orderStatus, Order.class));
                    }
                });
            case MODIFYDURATION:
                return openModifyDuration().hasData(new Passable<ModifyDurationView>() {
                    @Override
                    public void passData(ModifyDurationView modifyDurationView) {
                        modifyDurationView.setIsLocation(false);
                        modifyDurationView.setOrder(new Gson().fromJson(orderStatus, Order.class));
                    }
                });
            case MODIFYLOCATION:
                return openModifyLocation().hasData(new Passable<ModifyAddressView>() {
                    @Override
                    public void passData(ModifyAddressView modifyAddressView) {
                        modifyAddressView.setIsLocation(false);
                        modifyAddressView.setOrder(new Gson().fromJson(orderStatus, Order.class));
                    }
                });
            case ReceipScreen:
                return openReceiptView().hasData(new Passable<ReceiptView>() {
                    @Override
                    public void passData(ReceiptView receiptView) {
                        receiptView.setOrderId(orderId);
                    }
                });
            case EditOrganization:
                return openEditOrganizationView();
            case LOCALRECIVEOBJECTION:
                return openReceivedObjectionLocalView().hasData(new Passable<ReceivedObjectionLocalView>() {
                    @Override
                    public void passData(ReceivedObjectionLocalView receivedObjectionLocalView) {
                        receivedObjectionLocalView.setNotificationId(notificationId);
                    }
                });
            case RATING:
                return openRatingView().hasData(ratingnView -> ratingnView.setOrderId(orderId));

            case OrdeDetailsVisitor:
                return openOrderDetailsView().hasData(new Passable<OrderDetailsView>() {
                    @Override
                    public void passData(OrderDetailsView orderDetailsView) {
                        orderDetailsView.setOrder(new Gson().fromJson(orderStatus, Order.class));
                    }
                });

            case LOCALINCOMPLETE:
                return openLocalIncompleteStepView();

            case ORGSTATUS:
                return openOrgStatus();

            case ORGADDLOCAL:
                return openAddLocal().hasData(setPaymentRulesView -> {
                    setPaymentRulesView.setOrderDetails(new Gson().fromJson(orgDetails, OrganizationDetails.class));
                    setPaymentRulesView.setIsCreateOrg(true);
                });

            case ORGSETPAYMENTRULES:
                return openSetPaymentRules().hasData(setPaymentRulesView -> {
                    setPaymentRulesView.setOrderDetails(new Gson().fromJson(orgDetails, OrganizationDetails.class));
                    setPaymentRulesView.setIsCreatOrganization(true);
                });

            case ORGADDPAYMENTRULES:
                return openAddNewPaymentRules().hasData(setPaymentRulesView -> {
                    setPaymentRulesView.setDataOrganization(new Gson().fromJson(orgDetails, OrganizationDetails.class));
                });

            case CREATORG:
                return openCreateOrganization();

            case ORGPREVIEW:
                OrganizationVisitor organizationVisitor = new OrganizationVisitor();
                organizationVisitor.setOwnerId(session.getUser().getId());

                return openOrganisationDetails().hasData(organizationDetailsView -> {
                    organizationDetailsView.setDataOrgVisitor(organizationVisitor);
                    organizationDetailsView.setIsPreview(true);
                });
            case METTINGLOCATIONDISP:
                return openMettingLocalVisitorAndLocalMap().hasData(new Passable<MettingLocationView>() {
                    @Override
                    public void passData(MettingLocationView localVisitorMapView) {
                        localVisitorMapView.setData(new Gson().fromJson(homeDetails, DashboardDetails.class));
                    }
                });
            case RECEVIOBJECTIONVISITOR:
                return openReciveObjectionDetailsView().hasData(new Passable<ReciveObjectionDetailsView>() {
                    @Override
                    public void passData(ReciveObjectionDetailsView reciveObjectionDetailsView) {
                        reciveObjectionDetailsView.setId(notificationId);
                    }
                });
            case LOCALEXP:
                return openExperienceView();
            case EXPADDDETAILS:
                return openExperienceAddDetailsView();
            case EXPDETAILSLOCAL:
                return openExperienceDetailsView().hasData(experienceDetailsView -> experienceDetailsView.setExpId(expId));
            case EXPDETAILSLOCALSIDE:
                return openExperienceDetailsView().hasData(experienceDetailsView -> {
                    experienceDetailsView.setIsLocalApp(true);
                    experienceDetailsView.setExpId(expId);
                });
            case ONBORDING:
                return openOnBoardingView().hasData(onBoardingView -> onBoardingView.data(new Gson().fromJson(sportsData, new TypeToken<List<Service>>() {
                }.getType())));
            case ONBORDINGACTIVITY:
                return openOnBoardingActivityView().hasData(onBoardingActivityView -> onBoardingActivityView.setOnBoardingData(onBoardData));

            case ADDBANKLOCAL:
                return openAddBankView();
            case VISITORCURRENCY:
                return openCurrencyVisitorView().hasData(new Passable<CurrencyVisitorView>() {
                    @Override
                    public void passData(CurrencyVisitorView currencyVisitorView) {
                        currencyVisitorView.setIsVisitor(true);
                    }
                });
            case LOCALCURRENCY:
                return openCurrencyVisitorView().hasData(new Passable<CurrencyVisitorView>() {
                    @Override
                    public void passData(CurrencyVisitorView currencyVisitorView) {
                        currencyVisitorView.setIsVisitor(false);
                    }
                });
            case EXPSELECTDATE:
                return openExperienceSelectDateTimeView().hasData(experienceSelectDateTimeView -> experienceSelectDateTimeView.setExperienceBooking(new Gson().fromJson(expSelectDate, ExperienceBookingFlow.class)));
            case OPENWEBVIEW:
                return openWebOpenView().hasData(webOpenView -> webOpenView.setData(webTitle, webUrl));
            default:
                return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(EventBusAction.LOCALEXPADD);

    }


}
