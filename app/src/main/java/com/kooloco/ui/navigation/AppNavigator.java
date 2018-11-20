package com.kooloco.ui.navigation;

import android.support.annotation.StyleRes;

import com.kooloco.imagevideo.ImageAndVideoPicker;
import com.kooloco.model.ExpDetails;
import com.kooloco.ui.authantication.view.ForgotPasswordView;
import com.kooloco.ui.expereince.view.DemoCalenderView;
import com.kooloco.ui.expereince.view.ExperienceDetailsView;
import com.kooloco.ui.expereince.view.ExperienceSelectDateTimeView;
import com.kooloco.ui.expereince.view.ExperienceSelectVisitorView;
import com.kooloco.ui.expereince.view.ExperinceAddParticipantsView;
import com.kooloco.ui.myexperience.view.BlogDetailsView;
import com.kooloco.ui.myexperience.view.BlogListView;
import com.kooloco.ui.myexperience.view.CreateBlogView;
import com.kooloco.ui.myexperience.view.ImageFilterView;
import com.kooloco.ui.notification.view.CheckSportEaquipmentsView;
import com.kooloco.ui.notification.view.NotificationView;
import com.kooloco.ui.notification.view.ReciveObjectionDetailsView;
import com.kooloco.ui.alllocal.view.AllLocalMapView;
import com.kooloco.ui.authantication.view.LoginView;
import com.kooloco.ui.authantication.view.SignUpView;
import com.kooloco.ui.chat.view.ChatView;
import com.kooloco.ui.chat.view.RecentChatView;
import com.kooloco.ui.invite.view.InviteListView;
import com.kooloco.ui.invite.view.InviteView;
import com.kooloco.ui.onboarding.view.OnBoardingActivityView;
import com.kooloco.ui.onboarding.view.OnBoardingView;
import com.kooloco.ui.payment.fragment.CardFragment;
import com.kooloco.ui.payment.view.CardView;
import com.kooloco.ui.profile.view.BecomeLocalWebView;
import com.kooloco.ui.profile.view.CurrencyVisitorView;
import com.kooloco.ui.visitor.dashboard.view.SelectActivityNewView;
import com.kooloco.ui.visitor.dashboard.view.SelectServiceNewView;
import com.kooloco.ui.visitor.home.view.HomeLocalAndExpView;
import com.kooloco.ui.visitor.home.view.HomeNewView;
import com.kooloco.ui.visitor.organizationDetails.view.MettingLocationView;
import com.kooloco.ui.visitor.organizationDetails.view.OrganizationReportView;
import com.kooloco.ui.webOpen.view.WebOpenView;
import com.kooloco.uilocal.Notification.view.ModifyObjectionView;
import com.kooloco.uilocal.Notification.view.NotificationLocalView;
import com.kooloco.uilocal.Notification.view.ReceivedObjectionLocalView;
import com.kooloco.uilocal.addservice.view.AddImageView;
import com.kooloco.uilocal.addservice.view.BecomeALocalView;
import com.kooloco.ui.manager.ActivityBuilder;
import com.kooloco.ui.manager.AppNavigationProvider;
import com.kooloco.ui.manager.FragmentActionPerformer;
import com.kooloco.ui.myexperience.view.MyExperienceDetailsView;
import com.kooloco.ui.myexperience.view.MyExperienceView;
import com.kooloco.ui.profile.view.CanecellationView;
import com.kooloco.ui.profile.view.ChangePasswordView;
import com.kooloco.ui.profile.view.FavoritesView;
import com.kooloco.ui.profile.view.HelpAndFaqView;
import com.kooloco.ui.profile.view.OrderDetailsView;
import com.kooloco.ui.profile.view.OrderHIstoryView;
import com.kooloco.ui.profile.view.ProfileView;
import com.kooloco.ui.profile.view.SettingsView;
import com.kooloco.ui.profile.view.ViewProfileView;
import com.kooloco.ui.rating.view.RatingnView;
import com.kooloco.ui.visitor.dashboard.view.AddParticipantsView;
import com.kooloco.ui.visitor.dashboard.view.AppointmentBookedView;
import com.kooloco.ui.visitor.dashboard.view.AppointmentSummaryView;
import com.kooloco.ui.visitor.dashboard.view.DashboardView;
import com.kooloco.ui.visitor.dashboard.view.DurationView;
import com.kooloco.ui.visitor.dashboard.view.MeetingLocationView;
import com.kooloco.ui.visitor.dashboard.view.PaymentView;
import com.kooloco.ui.visitor.dashboard.view.ReceiptView;
import com.kooloco.ui.visitor.dashboard.view.SelectActivitiesView;
import com.kooloco.ui.visitor.dashboard.view.SelectDateView;
import com.kooloco.ui.visitor.home.view.HomeView;
import com.kooloco.ui.visitor.organizationDetails.view.LocalVisitorMapView;
import com.kooloco.ui.visitor.organizationDetails.view.OrganizationDetailsView;
import com.kooloco.uilocal.addservice.view.CancellationPoliciesView;
import com.kooloco.uilocal.addservice.view.ChooseLanguagesView;
import com.kooloco.uilocal.addservice.view.ChooseSpeakLanguagesView;
import com.kooloco.uilocal.addservice.view.ChooseSportActivities1View;
import com.kooloco.uilocal.addservice.view.ChooseSportActivities2View;
import com.kooloco.uilocal.addservice.view.ChooseSportActivitiesNewView;
import com.kooloco.uilocal.addservice.view.ChooseSportActivitiesView;
import com.kooloco.uilocal.addservice.view.ChooseSportEquipmentsView;
import com.kooloco.uilocal.addservice.view.CreateYourExperienceView;
import com.kooloco.uilocal.addservice.view.SetAvailabilitiesView;
import com.kooloco.uilocal.addservice.view.SetLocationView;
import com.kooloco.uilocal.addservice.view.SportPriceRulesTabView;
import com.kooloco.uilocal.addservice.view.SportPriceRulesView;
import com.kooloco.uilocal.addservice.view.SuccessLocalView;
import com.kooloco.uilocal.addservice.view.UploadAchievementsView;
import com.kooloco.uilocal.addservice.view.UploadCertificationsView;
import com.kooloco.uilocal.complateorder.view.CompleteOrderView;
import com.kooloco.uilocal.earnings.view.AllMonthView;
import com.kooloco.uilocal.earnings.view.EarningsView;
import com.kooloco.uilocal.earnings.view.MonthEarningView;
import com.kooloco.uilocal.expereince.view.AddDetailsView;
import com.kooloco.uilocal.expereince.view.EditExperienceView;
import com.kooloco.uilocal.expereince.view.ExperienceCancellationPoliciesView;
import com.kooloco.uilocal.expereince.view.ExperienceDisableExperienceView;
import com.kooloco.uilocal.expereince.view.ExperienceInCompleteStepView;
import com.kooloco.uilocal.expereince.view.ExperienceMeetingSpotView;
import com.kooloco.uilocal.expereince.view.ExperienceSelectView;
import com.kooloco.uilocal.expereince.view.ExperienceSetSpecialAvailabilitesView;
import com.kooloco.uilocal.expereince.view.ExperienceSportEquipmentsView;
import com.kooloco.uilocal.expereince.view.ExperienceSportView;
import com.kooloco.uilocal.expereince.view.ExperienceView;
import com.kooloco.uilocal.expereince.view.OtherDetailsAnotherFieldsView;
import com.kooloco.uilocal.expereince.view.OtherDetailsView;
import com.kooloco.uilocal.expereince.view.ScheduleAndPriceView;
import com.kooloco.uilocal.home.view.AcceptOrderDetailsView;
import com.kooloco.uilocal.home.view.LocalCheckSportEaquipmentsView;
import com.kooloco.uilocal.home.view.OrderDetailsLocalView;
import com.kooloco.uilocal.home.view.RateAndReviewView;
import com.kooloco.uilocal.modify.view.ModifyAddressView;
import com.kooloco.uilocal.modify.view.ModifyDurationView;
import com.kooloco.uilocal.organaization.view.AddBankView;
import com.kooloco.uilocal.organaization.view.AddLocalView;
import com.kooloco.uilocal.organaization.view.AddNewPaymentRulesView;
import com.kooloco.uilocal.organaization.view.AssignLocalView;
import com.kooloco.uilocal.organaization.view.CreateYourOrganizationView;
import com.kooloco.uilocal.organaization.view.EditOrganizationView;
import com.kooloco.uilocal.organaization.view.OrgAddBankView;
import com.kooloco.uilocal.organaization.view.OrganizationDashboardView;
import com.kooloco.uilocal.organaization.view.OrganizationIncompleteStepView;
import com.kooloco.uilocal.organaization.view.OrganizationStatusView;
import com.kooloco.uilocal.organaization.view.PerAddBankView;
import com.kooloco.uilocal.organaization.view.PreviewDetailsSingalLocalView;
import com.kooloco.uilocal.organaization.view.PreviewDetailsView;
import com.kooloco.uilocal.organaization.view.SetPaymentRulesView;
import com.kooloco.uilocal.profile.view.EditAchievementsView;
import com.kooloco.uilocal.profile.view.EditCertificationsView;
import com.kooloco.uilocal.profile.view.EditProfileView;
import com.kooloco.uilocal.profile.view.LocalIncompleteStepView;
import com.kooloco.uilocal.profile.view.ProfileLocalView;
import com.kooloco.uilocal.profile.view.ViewProfileLocalView;
import com.kooloco.uilocal.setavability.view.DisableSportView;
import com.kooloco.uilocal.setavability.view.SetAvabilityView;
import com.kooloco.uilocal.setavability.view.SetSpecialAvabilitiesView;
import com.kooloco.util.ImagePicker;

/**
 * Created by hlink on 27/12/16.
 */

public interface AppNavigator extends AppNavigationProvider {


    enum Pages implements AppNavigationProvider.HasPage {
        Dashboard, ViewProfile, Chat, MyExpeDet, OrderHistory, InviteFriends, HelpAndFaq, Setting, Fav, BecomeLocal, BecomeLocalWebView, OrdeDetailsLocal, AcceptOrderDetails, AllMonthAppEarning, ViewProfileLocal, NotificationLocal, CreateYourOrganization, SetAvabilities, EditProfile, RateAndReview, ChooseLanguage, MONTHEARNING, BlogList, BlogDetails, MODIFYDURATIONLOCATION, MODIFYLOCATION, MODIFYDURATION, ReceipScreen, EditOrganization, LOCALRECIVEOBJECTION, Notification, OrdeDetailsVisitor, RATING, ORGSTATUS, LOCALINCOMPLETE, ORGADDLOCAL, ORGSETPAYMENTRULES, ORGADDPAYMENTRULES, ORGPREVIEW, METTINGLOCATIONDISP, RECEVIOBJECTIONVISITOR, LOCALEXP, CREATORG, EXPADDDETAILS, EXPDETAILSLOCAL, EXPDETAILSLOCALSIDE, ONBORDING, ONBORDINGACTIVITY, ADDBANKLOCAL, VISITORCURRENCY, LOCALCURRENCY, EXPSELECTDATE, OPENWEBVIEW;


    }

    enum ThemeColor implements HasThemeColor {
        WHITE(0);
        @StyleRes
        private int theme;

        ThemeColor(@StyleRes int theme) {
            this.theme = theme;
        }

        @StyleRes
        public int getTheme() {
            return theme;
        }
    }

    void showErrorMessage(String message);

    void goBack();

    void finish();


    void pickImage(ImagePicker.ImagePickerResult imagePickerResult);

    void pickImageCustom(ImagePicker.ImagePickerResult imagePickerResult);

    void openImageOrVideoPickDefault(ImageAndVideoPicker.ImageCallBack imageCallBack, boolean isVideo);

    ActivityBuilder openIsloatedFullActivity();

    ActivityBuilder startAuthenticationActivity();

    ActivityBuilder startHomeActivity();

    ActivityBuilder startHomeLocalActivity();

    FragmentActionPerformer<LoginView> openLogin();

    FragmentActionPerformer<SignUpView> openSignup();

    FragmentActionPerformer<HomeView> openHome();

    FragmentActionPerformer<DashboardView> openDashboard();

    FragmentActionPerformer<OrganizationDetailsView> openOrganisationDetails();

    FragmentActionPerformer<LocalVisitorMapView> openLocalAndVistorMap();

    FragmentActionPerformer<MettingLocationView> openMettingLocalVisitorAndLocalMap();

    FragmentActionPerformer<SelectActivitiesView> openSelectActivitesView();

    FragmentActionPerformer<SelectDateView> openSelectDateView();

    FragmentActionPerformer<AddParticipantsView> openAddParticipantsView();

    FragmentActionPerformer<DurationView> openDurationView();

    FragmentActionPerformer<MeetingLocationView> openMeetingLocationView();

    FragmentActionPerformer<AppointmentSummaryView> openAppointmentSummeryView();

    FragmentActionPerformer<PaymentView> openPaymentView();

    FragmentActionPerformer<AppointmentBookedView> openAppointmentBookedView();

    FragmentActionPerformer<ReceiptView> openReceiptView();

    FragmentActionPerformer<RatingnView> openRatingView();

    FragmentActionPerformer<ProfileView> openProfileView();

    FragmentActionPerformer<ViewProfileView> openViewProfileView();

    FragmentActionPerformer<OrderHIstoryView> openOrderHistoryView();

    FragmentActionPerformer<OrderDetailsView> openOrderDetailsView();

    FragmentActionPerformer<CanecellationView> openCancellationView();

    FragmentActionPerformer<InviteView> openInviteView();

    FragmentActionPerformer<InviteListView> openInviteListView();

    FragmentActionPerformer<NotificationView> openNotificationView();

    FragmentActionPerformer<ReciveObjectionDetailsView> openReciveObjectionDetailsView();

    FragmentActionPerformer<CheckSportEaquipmentsView> openCheckSportEaquipmentsView();

    FragmentActionPerformer<LocalCheckSportEaquipmentsView> openLocalCheckSportEaquipmentsView();

    FragmentActionPerformer<RecentChatView> openRecentChatView();

    FragmentActionPerformer<FavoritesView> openFavoritesView();

    FragmentActionPerformer<SettingsView> openSettingView();

    FragmentActionPerformer<ChangePasswordView> openChangePasswordView();

    FragmentActionPerformer<ChatView> openChatView();

    FragmentActionPerformer<HelpAndFaqView> openHelpAndFaqView();

    FragmentActionPerformer<MyExperienceView> openMyExpiranceView();

    FragmentActionPerformer<MyExperienceDetailsView> openMyExpiranceDetailsView();

    FragmentActionPerformer<AllLocalMapView> openAllLocalView();

    FragmentActionPerformer<OrganizationReportView> openOragizationReportView();

    FragmentActionPerformer<UploadCertificationsView> openUploadCertificateView();

    FragmentActionPerformer<UploadAchievementsView> openUploadAchievementsView();

    FragmentActionPerformer<ChooseLanguagesView> openChooseLangaugeView();

    FragmentActionPerformer<CreateYourExperienceView> openCreatYourExpView();

    FragmentActionPerformer<ChooseSportActivitiesView> openChooseSportActivityView();

    FragmentActionPerformer<SportPriceRulesTabView> openSportPriceRulesView();

    FragmentActionPerformer<ChooseSportActivitiesNewView> openChooseSportActivityNewView();

    FragmentActionPerformer<ChooseSportActivities1View> openChooseSportActivity1View();

    FragmentActionPerformer<ChooseSportActivities2View> openChooseSportActivity2View();

    FragmentActionPerformer<ChooseSportEquipmentsView> openChooseSportEquipmentsView();

    FragmentActionPerformer<CancellationPoliciesView> openCancellationPoliciesView();

    FragmentActionPerformer<SetLocationView> openSetLocationView();

    FragmentActionPerformer<SetAvailabilitiesView> openSetAvilabilitesView();

    FragmentActionPerformer<SuccessLocalView> openSuccessLocalView();

    FragmentActionPerformer<com.kooloco.uilocal.home.view.HomeView> openHomeLocalView();

    FragmentActionPerformer<OrderDetailsLocalView> openOrderDetailsLocalView();

    FragmentActionPerformer<AcceptOrderDetailsView> openAcceptOrderDetailsLocalView();

    FragmentActionPerformer<ProfileLocalView> openProfileLocalView();

    FragmentActionPerformer<EarningsView> openEarningsView();

    FragmentActionPerformer<AllMonthView> openAllMonthEarningView();

    FragmentActionPerformer<MonthEarningView> openMonthEarningView();

    FragmentActionPerformer<ViewProfileLocalView> openViewProfileLocalView();

    FragmentActionPerformer<CompleteOrderView> openCompleteOrderView();

    FragmentActionPerformer<NotificationLocalView> openNotificationLocalView();

    FragmentActionPerformer<ReceivedObjectionLocalView> openReceivedObjectionLocalView();

    FragmentActionPerformer<ModifyObjectionView> openModifyObjectionLocalView();

    FragmentActionPerformer<CreateYourOrganizationView> openCreateOrganization();

    FragmentActionPerformer<PreviewDetailsView> openPreviewDetailsView();

    FragmentActionPerformer<AddBankView> openAddBankView();

    FragmentActionPerformer<PerAddBankView> openAddBankPerView();

    FragmentActionPerformer<OrganizationDashboardView> openOrgniztionDashboardView();

    FragmentActionPerformer<SetAvabilityView> openSetAvabilitiesProfileView();

    FragmentActionPerformer<SetSpecialAvabilitiesView> openSetSpecialAvabilitiesView();

    FragmentActionPerformer<DisableSportView> openDisableSportView();

    FragmentActionPerformer<EditProfileView> openEditProfileView();

    FragmentActionPerformer<RateAndReviewView> openLocalRateAndReviewView();

    FragmentActionPerformer<EditOrganizationView> openEditOrganizationView();

    FragmentActionPerformer<EditAchievementsView> openEditAchievementsView();

    FragmentActionPerformer<EditCertificationsView> openEditCertificationView();

    FragmentActionPerformer<PreviewDetailsSingalLocalView> openLocalPreviewDetailsView();

    FragmentActionPerformer<BlogListView> openBlogListView();

    FragmentActionPerformer<BlogDetailsView> openBlogDetailsView();

    FragmentActionPerformer<CreateBlogView> openCreateBlogView();

    FragmentActionPerformer<ModifyDurationView> openModifyDuration();

    FragmentActionPerformer<ModifyAddressView> openModifyLocation();

    FragmentActionPerformer<ImageFilterView> openImageFilterView();

    FragmentActionPerformer<AssignLocalView> openAssignLocal();

    FragmentActionPerformer<AddLocalView> openAddLocal();

    FragmentActionPerformer<SetPaymentRulesView> openSetPaymentRules();

    FragmentActionPerformer<AddNewPaymentRulesView> openAddNewPaymentRules();

    FragmentActionPerformer<SelectServiceNewView> openSelectServiceNew();

    FragmentActionPerformer<SelectActivityNewView> openSelectActivityNew();

    FragmentActionPerformer<LocalIncompleteStepView> openLocalIncompleteStepView();

    FragmentActionPerformer<OrganizationStatusView> openOrgStatus();

    FragmentActionPerformer<OrganizationIncompleteStepView> openOrgInComplete();

    FragmentActionPerformer<ForgotPasswordView> openForgotPasswordView();

    FragmentActionPerformer<ExperienceView> openExperienceView();

    FragmentActionPerformer<EditExperienceView> openExperienceEditView();

    FragmentActionPerformer<ExperienceSelectView> openExperienceSelectView();

    FragmentActionPerformer<ExperienceSportView> openExperienceSportView();

    FragmentActionPerformer<AddDetailsView> openExperienceAddDetailsView();

    FragmentActionPerformer<OtherDetailsView> openExperienceOtherDetailsView();

    FragmentActionPerformer<OtherDetailsAnotherFieldsView> openExperienceOtherDetailsAnotherFieldsView();

    FragmentActionPerformer<ScheduleAndPriceView> openExperienceSchedulePriceView();

    FragmentActionPerformer<ExperienceMeetingSpotView> openExperienceMeetingSpotView();

    FragmentActionPerformer<ExperienceCancellationPoliciesView> openExperienceCancellationPolicesView();

    FragmentActionPerformer<ExperienceSetSpecialAvailabilitesView> openExperienceSetSpecialAvailabilitesView();

    FragmentActionPerformer<ExperienceDisableExperienceView> openExperienceDisableExperienceView();

    FragmentActionPerformer<ExperienceSportEquipmentsView> openExperienceSportEquipmentsView();

    FragmentActionPerformer<ExperienceInCompleteStepView> openExperienceInCompleteStepView();

    FragmentActionPerformer<ExperienceDetailsView> openExperienceDetailsView();

    FragmentActionPerformer<DemoCalenderView> openDemoCalenderView();

    FragmentActionPerformer<HomeNewView> openHomeNewView();

    FragmentActionPerformer<HomeLocalAndExpView> openHomeLocalAndExpView();

    FragmentActionPerformer<ExperienceSelectVisitorView> openExperienceSelectVisitorView();

    FragmentActionPerformer<ExperienceSelectDateTimeView> openExperienceSelectDateTimeView();

    FragmentActionPerformer<ExperinceAddParticipantsView> openExperinceAddParticipantsView();

    FragmentActionPerformer<OnBoardingView> openOnBoardingView();

    FragmentActionPerformer<OnBoardingActivityView> openOnBoardingActivityView();

    FragmentActionPerformer<ChooseSpeakLanguagesView> openSpeekLanguageView();

    FragmentActionPerformer<CardView> openCreditCardView();

    FragmentActionPerformer<OrgAddBankView> openOrgAddBankView();

    FragmentActionPerformer<CurrencyVisitorView> openCurrencyVisitorView();

    FragmentActionPerformer<WebOpenView> openWebOpenView();


    /*
    Visitor Side app
*/

    FragmentActionPerformer<BecomeALocalView> openBecomeALocalView();

    FragmentActionPerformer<BecomeLocalWebView> openBecomeLocalWebView();

    FragmentActionPerformer<AddImageView> openAddImageView();

}
