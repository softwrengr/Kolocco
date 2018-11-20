package com.kooloco.ui.navigation;

import android.view.WindowManager;

import com.kooloco.di.component.ActivityComponent;
import com.kooloco.imagevideo.ImageAndVideoPicker;
import com.kooloco.imagevideo.ImagePickerPath;
import com.kooloco.ui.MainActivity;
import com.kooloco.ui.authantication.fragment.ForgotPasswordFragment;
import com.kooloco.ui.authantication.view.ForgotPasswordView;
import com.kooloco.ui.expereince.fragment.DemoCalenderFragment;
import com.kooloco.ui.expereince.fragment.ExperienceDetailsFragment;
import com.kooloco.ui.expereince.fragment.ExperienceSelectDateTimeFragment;
import com.kooloco.ui.expereince.fragment.ExperienceSelectVisitorFragment;
import com.kooloco.ui.expereince.fragment.ExperinceAddParticipantsFragment;
import com.kooloco.ui.expereince.view.DemoCalenderView;
import com.kooloco.ui.expereince.view.ExperienceDetailsView;
import com.kooloco.ui.expereince.view.ExperienceSelectDateTimeView;
import com.kooloco.ui.expereince.view.ExperienceSelectVisitorView;
import com.kooloco.ui.expereince.view.ExperinceAddParticipantsView;
import com.kooloco.ui.myexperience.fragment.BlogDetailsFragment;
import com.kooloco.ui.myexperience.fragment.BlogListFragment;
import com.kooloco.ui.myexperience.fragment.CreateBlogFragment;
import com.kooloco.ui.myexperience.fragment.ImageFilterFragment;
import com.kooloco.ui.myexperience.view.BlogDetailsView;
import com.kooloco.ui.myexperience.view.BlogListView;
import com.kooloco.ui.myexperience.view.CreateBlogView;
import com.kooloco.ui.myexperience.view.ImageFilterView;
import com.kooloco.ui.notification.fragment.CheckSportEaquipmentsFragment;
import com.kooloco.ui.notification.fragment.NotificationFragment;
import com.kooloco.ui.notification.fragment.ReciveObjectionDetailsFragment;
import com.kooloco.ui.notification.view.CheckSportEaquipmentsView;
import com.kooloco.ui.notification.view.NotificationView;
import com.kooloco.ui.notification.view.ReciveObjectionDetailsView;
import com.kooloco.ui.alllocal.fragment.AllLocalMapFragment;
import com.kooloco.ui.alllocal.view.AllLocalMapView;
import com.kooloco.ui.authantication.AuthActivity;
import com.kooloco.ui.authantication.fragment.LoginFragment;
import com.kooloco.ui.authantication.fragment.SignUpFragment;
import com.kooloco.ui.authantication.view.LoginView;
import com.kooloco.ui.authantication.view.SignUpView;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.chat.fragment.ChatFragment;
import com.kooloco.ui.chat.fragment.RecentChatFragment;
import com.kooloco.ui.chat.view.ChatView;
import com.kooloco.ui.chat.view.RecentChatView;
import com.kooloco.ui.invite.fragment.InviteFragment;
import com.kooloco.ui.invite.fragment.InviteListFragment;
import com.kooloco.ui.invite.view.InviteListView;
import com.kooloco.ui.invite.view.InviteView;
import com.kooloco.ui.isolated.IsolatedFullActivity;
import com.kooloco.ui.onboarding.fragment.OnBoardingActivityFragment;
import com.kooloco.ui.onboarding.fragment.OnBoardingFragment;
import com.kooloco.ui.onboarding.view.OnBoardingActivityView;
import com.kooloco.ui.onboarding.view.OnBoardingView;
import com.kooloco.ui.payment.fragment.CardFragment;
import com.kooloco.ui.payment.view.CardView;
import com.kooloco.ui.profile.fragment.BecomeLocalWebFragment;
import com.kooloco.ui.profile.fragment.CurrencyVisitorFragment;
import com.kooloco.ui.profile.view.BecomeLocalWebView;
import com.kooloco.ui.profile.view.CurrencyVisitorView;
import com.kooloco.ui.visitor.dashboard.fragment.SelectActivityNewFragment;
import com.kooloco.ui.visitor.dashboard.fragment.SelectServiceNewFragment;
import com.kooloco.ui.visitor.dashboard.view.SelectActivityNewView;
import com.kooloco.ui.visitor.dashboard.view.SelectServiceNewView;
import com.kooloco.ui.visitor.home.fragment.HomeLocalAndExpFragment;
import com.kooloco.ui.visitor.home.fragment.HomeNewFragment;
import com.kooloco.ui.visitor.home.view.HomeLocalAndExpView;
import com.kooloco.ui.visitor.home.view.HomeNewView;
import com.kooloco.ui.visitor.organizationDetails.fragment.MettingLocationFragment;
import com.kooloco.ui.visitor.organizationDetails.fragment.OrganizationReportFragment;
import com.kooloco.ui.visitor.organizationDetails.view.MettingLocationView;
import com.kooloco.ui.visitor.organizationDetails.view.OrganizationReportView;
import com.kooloco.ui.webOpen.fragment.WebOpenFragment;
import com.kooloco.ui.webOpen.view.WebOpenView;
import com.kooloco.uilocal.MainLocalActivity;
import com.kooloco.uilocal.Notification.fragment.ModifyObjectionFragment;
import com.kooloco.uilocal.Notification.fragment.NotificationLocalFragment;
import com.kooloco.uilocal.Notification.fragment.ReceivedObjectionLocalFragment;
import com.kooloco.uilocal.Notification.view.ModifyObjectionView;
import com.kooloco.uilocal.Notification.view.NotificationLocalView;
import com.kooloco.uilocal.Notification.view.ReceivedObjectionLocalView;
import com.kooloco.uilocal.addservice.fragment.AddImageFragment;
import com.kooloco.uilocal.addservice.fragment.BecomeALocalFragment;
import com.kooloco.uilocal.addservice.fragment.CancellationPoliciesFragment;
import com.kooloco.uilocal.addservice.fragment.ChooseLanguagesFragment;
import com.kooloco.uilocal.addservice.fragment.ChooseSpeakLanguagesFragment;
import com.kooloco.uilocal.addservice.fragment.ChooseSportActivities1Fragment;
import com.kooloco.uilocal.addservice.fragment.ChooseSportActivities2Fragment;
import com.kooloco.uilocal.addservice.fragment.ChooseSportActivitiesFragment;
import com.kooloco.uilocal.addservice.fragment.ChooseSportActivitiesNewFragment;
import com.kooloco.uilocal.addservice.fragment.ChooseSportEquipmentsFragment;
import com.kooloco.uilocal.addservice.fragment.CreateYourExperienceFragment;
import com.kooloco.uilocal.addservice.fragment.SetAvailabilitiesFragment;
import com.kooloco.uilocal.addservice.fragment.SetLocationFragment;
import com.kooloco.uilocal.addservice.fragment.SportPriceRulesFragment;
import com.kooloco.uilocal.addservice.fragment.SportPriceRulesTabFragment;
import com.kooloco.uilocal.addservice.fragment.SuccessLocalFragment;
import com.kooloco.uilocal.addservice.fragment.UploadAchievementsFragment;
import com.kooloco.uilocal.addservice.fragment.UploadCertificationsFragment;
import com.kooloco.uilocal.addservice.view.AddImageView;
import com.kooloco.uilocal.addservice.view.BecomeALocalView;
import com.kooloco.ui.manager.ActivityBuilder;
import com.kooloco.ui.manager.FragmentActionPerformer;
import com.kooloco.ui.myexperience.fragment.MyExperienceDetailsFragment;
import com.kooloco.ui.myexperience.fragment.MyExperienceFragment;
import com.kooloco.ui.myexperience.view.MyExperienceDetailsView;
import com.kooloco.ui.myexperience.view.MyExperienceView;
import com.kooloco.ui.profile.fragment.CanecellationFragment;
import com.kooloco.ui.profile.fragment.ChangePasswordFragment;
import com.kooloco.ui.profile.fragment.FavoritesFragment;
import com.kooloco.ui.profile.fragment.HelpAndFaqFragment;
import com.kooloco.ui.profile.fragment.OrderDetailsFragment;
import com.kooloco.ui.profile.fragment.OrderHIstoryFragment;
import com.kooloco.ui.profile.fragment.ProfileFragment;
import com.kooloco.ui.profile.fragment.SettingsFragment;
import com.kooloco.ui.profile.fragment.ViewProfileFragment;
import com.kooloco.ui.profile.view.CanecellationView;
import com.kooloco.ui.profile.view.ChangePasswordView;
import com.kooloco.ui.profile.view.FavoritesView;
import com.kooloco.ui.profile.view.HelpAndFaqView;
import com.kooloco.ui.profile.view.OrderDetailsView;
import com.kooloco.ui.profile.view.OrderHIstoryView;
import com.kooloco.ui.profile.view.ProfileView;
import com.kooloco.ui.profile.view.SettingsView;
import com.kooloco.ui.profile.view.ViewProfileView;
import com.kooloco.ui.rating.fragment.RatingnFragment;
import com.kooloco.ui.rating.view.RatingnView;
import com.kooloco.ui.visitor.dashboard.fragment.AddParticipantsFragment;
import com.kooloco.ui.visitor.dashboard.fragment.AppointmentBookedFragment;
import com.kooloco.ui.visitor.dashboard.fragment.AppointmentSummaryFragment;
import com.kooloco.ui.visitor.dashboard.fragment.DashboardFragment;
import com.kooloco.ui.visitor.dashboard.fragment.DurationFragment;
import com.kooloco.ui.visitor.dashboard.fragment.MeetingLocationFragment;
import com.kooloco.ui.visitor.dashboard.fragment.PaymentFragment;
import com.kooloco.ui.visitor.dashboard.fragment.ReceiptFragment;
import com.kooloco.ui.visitor.dashboard.fragment.SelectActivitiesFragment;
import com.kooloco.ui.visitor.dashboard.fragment.SelectDateFragment;
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
import com.kooloco.ui.visitor.home.fragment.HomeFragment;
import com.kooloco.ui.visitor.home.view.HomeView;
import com.kooloco.ui.visitor.organizationDetails.fragment.LocalVisitorMapFragment;
import com.kooloco.ui.visitor.organizationDetails.fragment.OrganizationDetailsFragment;
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
import com.kooloco.uilocal.complateorder.fragment.CompleteOrderFragment;
import com.kooloco.uilocal.complateorder.view.CompleteOrderView;
import com.kooloco.uilocal.earnings.fragment.AllMonthFragment;
import com.kooloco.uilocal.earnings.fragment.EarningsFragment;
import com.kooloco.uilocal.earnings.fragment.MonthEarningFragment;
import com.kooloco.uilocal.earnings.view.AllMonthView;
import com.kooloco.uilocal.earnings.view.EarningsView;
import com.kooloco.uilocal.earnings.view.MonthEarningView;
import com.kooloco.uilocal.expereince.fragment.AddDetailsFragment;
import com.kooloco.uilocal.expereince.fragment.EditExperienceFragment;
import com.kooloco.uilocal.expereince.fragment.ExperienceCancellationPoliciesFragment;
import com.kooloco.uilocal.expereince.fragment.ExperienceDisableExperienceFragment;
import com.kooloco.uilocal.expereince.fragment.ExperienceFragment;
import com.kooloco.uilocal.expereince.fragment.ExperienceInCompleteStepFragment;
import com.kooloco.uilocal.expereince.fragment.ExperienceMeetingSpotFragment;
import com.kooloco.uilocal.expereince.fragment.ExperienceSelectFragment;
import com.kooloco.uilocal.expereince.fragment.ExperienceSetSpecialAvailabilitesFragment;
import com.kooloco.uilocal.expereince.fragment.ExperienceSportEquipmentsFragment;
import com.kooloco.uilocal.expereince.fragment.ExperienceSportFragment;
import com.kooloco.uilocal.expereince.fragment.OtherDetailsAnotherFieldsFragment;
import com.kooloco.uilocal.expereince.fragment.OtherDetailsFragment;
import com.kooloco.uilocal.expereince.fragment.ScheduleAndPriceFragment;
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
import com.kooloco.uilocal.home.fragment.AcceptOrderDetailsFragment;
import com.kooloco.uilocal.home.fragment.LocalCheckSportEaquipmentsFragment;
import com.kooloco.uilocal.home.fragment.OrderDetailsLocalFragment;
import com.kooloco.uilocal.home.fragment.RateAndReviewFragment;
import com.kooloco.uilocal.home.view.AcceptOrderDetailsView;
import com.kooloco.uilocal.home.view.LocalCheckSportEaquipmentsView;
import com.kooloco.uilocal.home.view.OrderDetailsLocalView;
import com.kooloco.uilocal.home.view.RateAndReviewView;
import com.kooloco.uilocal.modify.fragment.ModifyAddressFragment;
import com.kooloco.uilocal.modify.fragment.ModifyDurationFragment;
import com.kooloco.uilocal.modify.view.ModifyAddressView;
import com.kooloco.uilocal.modify.view.ModifyDurationView;
import com.kooloco.uilocal.organaization.fragment.AddBankFragment;
import com.kooloco.uilocal.organaization.fragment.AddLocalFragment;
import com.kooloco.uilocal.organaization.fragment.AddNewPaymentRulesFragment;
import com.kooloco.uilocal.organaization.fragment.AssignLocalFragment;
import com.kooloco.uilocal.organaization.fragment.CreateYourOrganizationFragment;
import com.kooloco.uilocal.organaization.fragment.EditOrganizationFragment;
import com.kooloco.uilocal.organaization.fragment.OrgAddBankFragment;
import com.kooloco.uilocal.organaization.fragment.OrganizationDashboardFragment;
import com.kooloco.uilocal.organaization.fragment.OrganizationIncompleteStepFragment;
import com.kooloco.uilocal.organaization.fragment.OrganizationStatusFragment;
import com.kooloco.uilocal.organaization.fragment.PerAddBankFragment;
import com.kooloco.uilocal.organaization.fragment.PreviewDetailsFragment;
import com.kooloco.uilocal.organaization.fragment.PreviewDetailsSingalLocalFragment;
import com.kooloco.uilocal.organaization.fragment.SetPaymentRulesFragment;
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
import com.kooloco.uilocal.profile.fragment.EditAchievementsFragment;
import com.kooloco.uilocal.profile.fragment.EditCertificationsFragment;
import com.kooloco.uilocal.profile.fragment.EditProfileFragment;
import com.kooloco.uilocal.profile.fragment.LocalIncompleteStepFragment;
import com.kooloco.uilocal.profile.fragment.ProfileLocalFragment;
import com.kooloco.uilocal.profile.fragment.ViewProfileLocalFragment;
import com.kooloco.uilocal.profile.view.EditAchievementsView;
import com.kooloco.uilocal.profile.view.EditCertificationsView;
import com.kooloco.uilocal.profile.view.EditProfileView;
import com.kooloco.uilocal.profile.view.LocalIncompleteStepView;
import com.kooloco.uilocal.profile.view.ProfileLocalView;
import com.kooloco.uilocal.profile.view.ViewProfileLocalView;
import com.kooloco.uilocal.setavability.fragment.DisableSportFragment;
import com.kooloco.uilocal.setavability.fragment.SetAvabilityFragment;
import com.kooloco.uilocal.setavability.fragment.SetSpecialAvabilitiesFragment;
import com.kooloco.uilocal.setavability.view.DisableSportView;
import com.kooloco.uilocal.setavability.view.SetAvabilityView;
import com.kooloco.uilocal.setavability.view.SetSpecialAvabilitiesView;
import com.kooloco.util.ImagePicker;

/**
 * Created by hlink21 on 10/7/17.
 */

public abstract class AppNavigationProvider extends BaseActivity implements AppNavigator {
    PermissionGrantedListener permissionGrantedListener;

    @Override
    public void pickImage(final ImagePicker.ImagePickerResult imagePickerResult) {
      /*  ImagePicker imagePicker = new ImagePicker();
        imagePicker.setImagePickerResult(imagePickerResult);
        //imagePicker.setCropMode(mode);
        imagePicker.show(getCurrentFragment().getFragmentManager(), ImagePicker.class.getSimpleName());
*/
        ImageAndVideoPicker imageChooserDialog = ImageAndVideoPicker.newInstance(false);
        imageChooserDialog.setImageCallBack(new ImageAndVideoPicker.ImageCallBack() {
            @Override
            public void sendImage(ImagePickerPath imagePicker) {

                if (imagePicker.isPick()) {
                    if (imagePicker.isVideo()) {
                    } else {
                        imagePickerResult.onResult(imagePicker.getImagePath());
                    }
                }

            }
        });
        imageChooserDialog.show(getCurrentFragment().getFragmentManager(), ImageAndVideoPicker.class.getName());

    }

    @Override
    public void pickImageCustom(ImagePicker.ImagePickerResult imagePickerResult) {
        ImageAndVideoPicker imageChooserDialog = ImageAndVideoPicker.newInstance(false);
        imageChooserDialog.setCustomCrop(true);
        imageChooserDialog.setImageCallBack(new ImageAndVideoPicker.ImageCallBack() {
            @Override
            public void sendImage(ImagePickerPath imagePicker) {

                if (imagePicker.isPick()) {
                    if (imagePicker.isVideo()) {
                    } else {
                        imagePickerResult.onResult(imagePicker.getImagePath());
                    }
                }

            }
        });
        imageChooserDialog.show(getCurrentFragment().getFragmentManager(), ImageAndVideoPicker.class.getName());
    }

    @Override
    public void openImageOrVideoPickDefault(ImageAndVideoPicker.ImageCallBack imageCallBack, boolean isVideo) {
        ImageAndVideoPicker imageChooserDialog = ImageAndVideoPicker.newInstance(isVideo);
        imageChooserDialog.setImageCallBack(imageCallBack);
        imageChooserDialog.show(getCurrentFragment().getFragmentManager(), ImageAndVideoPicker.class.getName());
    }

    @Override
    public boolean requestAllPermission(PermissionGrantedListener permissionGrantedListener) {
        this.permissionGrantedListener = permissionGrantedListener;
        return super.requestAllPermission();
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public ActivityBuilder startAuthenticationActivity() {
        return activityStarter.make(AuthActivity.class);
    }

    @Override
    public ActivityBuilder openIsloatedFullActivity() {
        return activityStarter.make(IsolatedFullActivity.class);
    }


    public FragmentActionPerformer<LoginView> openLogin() {
        return navigationFactory.make(LoginFragment.class);
    }

    @Override
    public FragmentActionPerformer<SignUpView> openSignup() {
        return navigationFactory.make(SignUpFragment.class);
    }

    @Override
    public ActivityBuilder startHomeActivity() {
        return activityStarter.make(MainActivity.class);
    }

    @Override
    public ActivityBuilder startHomeLocalActivity() {
        return activityStarter.make(MainLocalActivity.class);
    }

    @Override
    public FragmentActionPerformer<HomeView> openHome() {
        return navigationFactory.make(HomeFragment.class);
    }

    @Override
    public FragmentActionPerformer<DashboardView> openDashboard() {
        return navigationFactory.make(DashboardFragment.class);
    }

    @Override
    public FragmentActionPerformer<OrganizationDetailsView> openOrganisationDetails() {
        return navigationFactory.make(OrganizationDetailsFragment.class);
    }

    @Override
    public FragmentActionPerformer<LocalVisitorMapView> openLocalAndVistorMap() {
        return navigationFactory.make(LocalVisitorMapFragment.class);
    }

    @Override
    public FragmentActionPerformer<MettingLocationView> openMettingLocalVisitorAndLocalMap() {
        return navigationFactory.make(MettingLocationFragment.class);
    }

    @Override
    public FragmentActionPerformer<SelectActivitiesView> openSelectActivitesView() {
        return navigationFactory.make(SelectActivitiesFragment.class);
    }

    @Override
    public FragmentActionPerformer<SelectDateView> openSelectDateView() {
        return navigationFactory.make(SelectDateFragment.class);
    }

    @Override
    public FragmentActionPerformer<AddParticipantsView> openAddParticipantsView() {
        return navigationFactory.make(AddParticipantsFragment.class);
    }

    @Override
    public FragmentActionPerformer<DurationView> openDurationView() {
        return navigationFactory.make(DurationFragment.class);
    }

    @Override
    public FragmentActionPerformer<MeetingLocationView> openMeetingLocationView() {
        return navigationFactory.make(MeetingLocationFragment.class);
    }

    @Override
    public FragmentActionPerformer<AppointmentSummaryView> openAppointmentSummeryView() {
        return navigationFactory.make(AppointmentSummaryFragment.class);
    }

    @Override
    public FragmentActionPerformer<PaymentView> openPaymentView() {
        return navigationFactory.make(PaymentFragment.class);
    }

    @Override
    public FragmentActionPerformer<AppointmentBookedView> openAppointmentBookedView() {
        return navigationFactory.make(AppointmentBookedFragment.class);
    }

    @Override
    public FragmentActionPerformer<ReceiptView> openReceiptView() {
        return navigationFactory.make(ReceiptFragment.class);
    }

    @Override
    public FragmentActionPerformer<RatingnView> openRatingView() {
        return navigationFactory.make(RatingnFragment.class);
    }

    @Override
    public FragmentActionPerformer<ProfileView> openProfileView() {
        return navigationFactory.make(ProfileFragment.class);
    }

    @Override
    public FragmentActionPerformer<ViewProfileView> openViewProfileView() {
        return navigationFactory.make(ViewProfileFragment.class);
    }

    @Override
    public FragmentActionPerformer<OrderHIstoryView> openOrderHistoryView() {
        return navigationFactory.make(OrderHIstoryFragment.class);
    }

    @Override
    public FragmentActionPerformer<OrderDetailsView> openOrderDetailsView() {
        return navigationFactory.make(OrderDetailsFragment.class);
    }

    @Override
    public FragmentActionPerformer<CanecellationView> openCancellationView() {
        return navigationFactory.make(CanecellationFragment.class);
    }

    @Override
    public FragmentActionPerformer<InviteView> openInviteView() {
        return navigationFactory.make(InviteFragment.class);
    }

    @Override
    public FragmentActionPerformer<InviteListView> openInviteListView() {
        return navigationFactory.make(InviteListFragment.class);
    }

    @Override
    public FragmentActionPerformer<NotificationView> openNotificationView() {
        return navigationFactory.make(NotificationFragment.class);
    }

    @Override
    public FragmentActionPerformer<ReciveObjectionDetailsView> openReciveObjectionDetailsView() {
        return navigationFactory.make(ReciveObjectionDetailsFragment.class);
    }

    @Override
    public FragmentActionPerformer<CheckSportEaquipmentsView> openCheckSportEaquipmentsView() {
        return navigationFactory.make(CheckSportEaquipmentsFragment.class);
    }

    @Override
    public FragmentActionPerformer<LocalCheckSportEaquipmentsView> openLocalCheckSportEaquipmentsView() {
        return navigationFactory.make(LocalCheckSportEaquipmentsFragment.class);
    }

    @Override
    public FragmentActionPerformer<RecentChatView> openRecentChatView() {
        return navigationFactory.make(RecentChatFragment.class);
    }

    @Override
    public FragmentActionPerformer<FavoritesView> openFavoritesView() {
        return navigationFactory.make(FavoritesFragment.class);
    }

    @Override
    public FragmentActionPerformer<SettingsView> openSettingView() {
        return navigationFactory.make(SettingsFragment.class);
    }

    @Override
    public FragmentActionPerformer<ChangePasswordView> openChangePasswordView() {
        return navigationFactory.make(ChangePasswordFragment.class);
    }

    @Override
    public FragmentActionPerformer<ChatView> openChatView() {
        return navigationFactory.make(ChatFragment.class);
    }

    @Override
    public FragmentActionPerformer<HelpAndFaqView> openHelpAndFaqView() {
        return navigationFactory.make(HelpAndFaqFragment.class);
    }

    @Override
    public FragmentActionPerformer<MyExperienceView> openMyExpiranceView() {
        return navigationFactory.make(MyExperienceFragment.class);
    }

    @Override
    public FragmentActionPerformer<MyExperienceDetailsView> openMyExpiranceDetailsView() {
        return navigationFactory.make(MyExperienceDetailsFragment.class);
    }

    @Override
    public FragmentActionPerformer<AllLocalMapView> openAllLocalView() {
        return navigationFactory.make(AllLocalMapFragment.class);
    }

    @Override
    public FragmentActionPerformer<OrganizationReportView> openOragizationReportView() {
        return navigationFactory.make(OrganizationReportFragment.class);
    }

    @Override
    public FragmentActionPerformer<BecomeALocalView> openBecomeALocalView() {
        return navigationFactory.make(BecomeALocalFragment.class);
    }

    @Override
    public FragmentActionPerformer<BecomeLocalWebView> openBecomeLocalWebView() {
        return navigationFactory.make(BecomeLocalWebFragment.class);
    }

    @Override
    public FragmentActionPerformer<AddImageView> openAddImageView() {
        return navigationFactory.make(AddImageFragment.class);
    }

    @Override
    public FragmentActionPerformer<UploadCertificationsView> openUploadCertificateView() {
        return navigationFactory.make(UploadCertificationsFragment.class);
    }

    @Override
    public FragmentActionPerformer<UploadAchievementsView> openUploadAchievementsView() {
        return navigationFactory.make(UploadAchievementsFragment.class);
    }

    @Override
    public FragmentActionPerformer<ChooseLanguagesView> openChooseLangaugeView() {
        return navigationFactory.make(ChooseLanguagesFragment.class);
    }

    @Override
    public FragmentActionPerformer<CreateYourExperienceView> openCreatYourExpView() {
        return navigationFactory.make(CreateYourExperienceFragment.class);
    }

    @Override
    public FragmentActionPerformer<ChooseSportActivitiesView> openChooseSportActivityView() {
        return navigationFactory.make(ChooseSportActivitiesFragment.class);
    }

    @Override
    public FragmentActionPerformer<SportPriceRulesTabView> openSportPriceRulesView() {
        return navigationFactory.make(SportPriceRulesTabFragment.class);
    }

    @Override
    public FragmentActionPerformer<ChooseSportActivitiesNewView> openChooseSportActivityNewView() {
        return navigationFactory.make(ChooseSportActivitiesNewFragment.class);
    }

    @Override
    public FragmentActionPerformer<ChooseSportActivities1View> openChooseSportActivity1View() {
        return navigationFactory.make(ChooseSportActivities1Fragment.class);
    }

    @Override
    public FragmentActionPerformer<ChooseSportActivities2View> openChooseSportActivity2View() {
        return navigationFactory.make(ChooseSportActivities2Fragment.class);
    }

    @Override
    public FragmentActionPerformer<ChooseSportEquipmentsView> openChooseSportEquipmentsView() {
        return navigationFactory.make(ChooseSportEquipmentsFragment.class);
    }

    @Override
    public FragmentActionPerformer<CancellationPoliciesView> openCancellationPoliciesView() {
        return navigationFactory.make(CancellationPoliciesFragment.class);
    }

    @Override
    public FragmentActionPerformer<SetLocationView> openSetLocationView() {
        return navigationFactory.make(SetLocationFragment.class);
    }

    @Override
    public FragmentActionPerformer<SetAvailabilitiesView> openSetAvilabilitesView() {
        return navigationFactory.make(SetAvailabilitiesFragment.class);
    }

    @Override
    public FragmentActionPerformer<SuccessLocalView> openSuccessLocalView() {
        return navigationFactory.make(SuccessLocalFragment.class);
    }

    @Override
    public FragmentActionPerformer<com.kooloco.uilocal.home.view.HomeView> openHomeLocalView() {
        return navigationFactory.make(com.kooloco.uilocal.home.fragment.HomeFragment.class);
    }

    @Override
    public FragmentActionPerformer<OrderDetailsLocalView> openOrderDetailsLocalView() {
        return navigationFactory.make(OrderDetailsLocalFragment.class);
    }

    @Override
    public FragmentActionPerformer<AcceptOrderDetailsView> openAcceptOrderDetailsLocalView() {
        return navigationFactory.make(AcceptOrderDetailsFragment.class);
    }

    @Override
    public FragmentActionPerformer<ProfileLocalView> openProfileLocalView() {
        return navigationFactory.make(ProfileLocalFragment.class);
    }

    @Override
    public FragmentActionPerformer<EarningsView> openEarningsView() {
        return navigationFactory.make(EarningsFragment.class);
    }

    @Override
    public FragmentActionPerformer<AllMonthView> openAllMonthEarningView() {
        return navigationFactory.make(AllMonthFragment.class);
    }

    @Override
    public FragmentActionPerformer<MonthEarningView> openMonthEarningView() {
        return navigationFactory.make(MonthEarningFragment.class);
    }

    @Override
    public FragmentActionPerformer<ViewProfileLocalView> openViewProfileLocalView() {
        return navigationFactory.make(ViewProfileLocalFragment.class);
    }

    @Override
    public FragmentActionPerformer<CompleteOrderView> openCompleteOrderView() {
        return navigationFactory.make(CompleteOrderFragment.class);
    }

    @Override
    public FragmentActionPerformer<NotificationLocalView> openNotificationLocalView() {
        return navigationFactory.make(NotificationLocalFragment.class);
    }

    @Override
    public FragmentActionPerformer<ReceivedObjectionLocalView> openReceivedObjectionLocalView() {
        return navigationFactory.make(ReceivedObjectionLocalFragment.class);
    }

    @Override
    public FragmentActionPerformer<ModifyObjectionView> openModifyObjectionLocalView() {
        return navigationFactory.make(ModifyObjectionFragment.class);
    }

    @Override
    public FragmentActionPerformer<CreateYourOrganizationView> openCreateOrganization() {
        return navigationFactory.make(CreateYourOrganizationFragment.class);
    }

    @Override
    public FragmentActionPerformer<PreviewDetailsView> openPreviewDetailsView() {
        return navigationFactory.make(PreviewDetailsFragment.class);
    }

    @Override
    public FragmentActionPerformer<AddBankView> openAddBankView() {
        return navigationFactory.make(AddBankFragment.class);
    }

    @Override
    public FragmentActionPerformer<PerAddBankView> openAddBankPerView() {
        return navigationFactory.make(PerAddBankFragment.class);
    }

    @Override
    public FragmentActionPerformer<OrganizationDashboardView> openOrgniztionDashboardView() {
        return navigationFactory.make(OrganizationDashboardFragment.class);
    }

    @Override
    public FragmentActionPerformer<SetAvabilityView> openSetAvabilitiesProfileView() {
        return navigationFactory.make(SetAvabilityFragment.class);
    }

    @Override
    public FragmentActionPerformer<SetSpecialAvabilitiesView> openSetSpecialAvabilitiesView() {
        return navigationFactory.make(SetSpecialAvabilitiesFragment.class);
    }

    @Override
    public FragmentActionPerformer<DisableSportView> openDisableSportView() {
        return navigationFactory.make(DisableSportFragment.class);
    }

    @Override
    public FragmentActionPerformer<EditProfileView> openEditProfileView() {
        return navigationFactory.make(EditProfileFragment.class);
    }

    @Override
    public FragmentActionPerformer<RateAndReviewView> openLocalRateAndReviewView() {
        return navigationFactory.make(RateAndReviewFragment.class);
    }

    @Override
    public FragmentActionPerformer<EditOrganizationView> openEditOrganizationView() {
        return navigationFactory.make(EditOrganizationFragment.class);
    }

    @Override
    public FragmentActionPerformer<EditAchievementsView> openEditAchievementsView() {
        return navigationFactory.make(EditAchievementsFragment.class);
    }

    @Override
    public FragmentActionPerformer<EditCertificationsView> openEditCertificationView() {
        return navigationFactory.make(EditCertificationsFragment.class);
    }

    @Override
    public FragmentActionPerformer<PreviewDetailsSingalLocalView> openLocalPreviewDetailsView() {
        return navigationFactory.make(PreviewDetailsSingalLocalFragment.class);
    }

    @Override
    public FragmentActionPerformer<BlogListView> openBlogListView() {
        return navigationFactory.make(BlogListFragment.class);
    }

    @Override
    public FragmentActionPerformer<BlogDetailsView> openBlogDetailsView() {
        return navigationFactory.make(BlogDetailsFragment.class);
    }

    @Override
    public FragmentActionPerformer<CreateBlogView> openCreateBlogView() {
        return navigationFactory.make(CreateBlogFragment.class);
    }

    @Override
    public FragmentActionPerformer<ModifyDurationView> openModifyDuration() {
        return navigationFactory.make(ModifyDurationFragment.class);
    }

    @Override
    public FragmentActionPerformer<ModifyAddressView> openModifyLocation() {
        return navigationFactory.make(ModifyAddressFragment.class);
    }

    @Override
    public FragmentActionPerformer<ImageFilterView> openImageFilterView() {
        return navigationFactory.make(ImageFilterFragment.class);
    }

    @Override
    public FragmentActionPerformer<AssignLocalView> openAssignLocal() {
        return navigationFactory.make(AssignLocalFragment.class);
    }

    @Override
    public FragmentActionPerformer<AddLocalView> openAddLocal() {
        return navigationFactory.make(AddLocalFragment.class);
    }

    @Override
    public FragmentActionPerformer<SetPaymentRulesView> openSetPaymentRules() {
        return navigationFactory.make(SetPaymentRulesFragment.class);
    }

    @Override
    public FragmentActionPerformer<AddNewPaymentRulesView> openAddNewPaymentRules() {
        return navigationFactory.make(AddNewPaymentRulesFragment.class);
    }

    @Override
    public FragmentActionPerformer<SelectServiceNewView> openSelectServiceNew() {
        return navigationFactory.make(SelectServiceNewFragment.class);
    }

    @Override
    public FragmentActionPerformer<SelectActivityNewView> openSelectActivityNew() {
        return navigationFactory.make(SelectActivityNewFragment.class);
    }

    @Override
    public FragmentActionPerformer<LocalIncompleteStepView> openLocalIncompleteStepView() {
        return navigationFactory.make(LocalIncompleteStepFragment.class);
    }

    @Override
    public FragmentActionPerformer<OrganizationStatusView> openOrgStatus() {
        return navigationFactory.make(OrganizationStatusFragment.class);
    }

    @Override
    public FragmentActionPerformer<OrganizationIncompleteStepView> openOrgInComplete() {
        return navigationFactory.make(OrganizationIncompleteStepFragment.class);
    }

    @Override
    public FragmentActionPerformer<ForgotPasswordView> openForgotPasswordView() {
        return navigationFactory.make(ForgotPasswordFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperienceView> openExperienceView() {
        return navigationFactory.make(ExperienceFragment.class);
    }

    @Override
    public FragmentActionPerformer<EditExperienceView> openExperienceEditView() {
        return navigationFactory.make(EditExperienceFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperienceSelectView> openExperienceSelectView() {
        return navigationFactory.make(ExperienceSelectFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperienceSportView> openExperienceSportView() {
        return navigationFactory.make(ExperienceSportFragment.class);
    }

    @Override
    public FragmentActionPerformer<AddDetailsView> openExperienceAddDetailsView() {
        return navigationFactory.make(AddDetailsFragment.class);
    }

    @Override
    public FragmentActionPerformer<OtherDetailsView> openExperienceOtherDetailsView() {
        return navigationFactory.make(OtherDetailsFragment.class);
    }

    @Override
    public FragmentActionPerformer<OtherDetailsAnotherFieldsView> openExperienceOtherDetailsAnotherFieldsView() {
        return navigationFactory.make(OtherDetailsAnotherFieldsFragment.class);
    }

    @Override
    public FragmentActionPerformer<ScheduleAndPriceView> openExperienceSchedulePriceView() {
        return navigationFactory.make(ScheduleAndPriceFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperienceMeetingSpotView> openExperienceMeetingSpotView() {
        return navigationFactory.make(ExperienceMeetingSpotFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperienceCancellationPoliciesView> openExperienceCancellationPolicesView() {
        return navigationFactory.make(ExperienceCancellationPoliciesFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperienceSetSpecialAvailabilitesView> openExperienceSetSpecialAvailabilitesView() {
        return navigationFactory.make(ExperienceSetSpecialAvailabilitesFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperienceDisableExperienceView> openExperienceDisableExperienceView() {
        return navigationFactory.make(ExperienceDisableExperienceFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperienceSportEquipmentsView> openExperienceSportEquipmentsView() {
        return navigationFactory.make(ExperienceSportEquipmentsFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperienceInCompleteStepView> openExperienceInCompleteStepView() {
        return navigationFactory.make(ExperienceInCompleteStepFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperienceDetailsView> openExperienceDetailsView() {
        return navigationFactory.make(ExperienceDetailsFragment.class);
    }

    @Override
    public FragmentActionPerformer<DemoCalenderView> openDemoCalenderView() {
        return navigationFactory.make(DemoCalenderFragment.class);
    }

    @Override
    public FragmentActionPerformer<HomeNewView> openHomeNewView() {
        return navigationFactory.make(HomeNewFragment.class);
    }

    @Override
    public FragmentActionPerformer<HomeLocalAndExpView> openHomeLocalAndExpView() {
        return navigationFactory.make(HomeLocalAndExpFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperienceSelectVisitorView> openExperienceSelectVisitorView() {
        return navigationFactory.make(ExperienceSelectVisitorFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperienceSelectDateTimeView> openExperienceSelectDateTimeView() {
        return navigationFactory.make(ExperienceSelectDateTimeFragment.class);
    }

    @Override
    public FragmentActionPerformer<ExperinceAddParticipantsView> openExperinceAddParticipantsView() {
        return navigationFactory.make(ExperinceAddParticipantsFragment.class);
    }

    @Override
    public FragmentActionPerformer<OnBoardingView> openOnBoardingView() {
        return navigationFactory.make(OnBoardingFragment.class);
    }

    @Override
    public FragmentActionPerformer<OnBoardingActivityView> openOnBoardingActivityView() {
        return navigationFactory.make(OnBoardingActivityFragment.class);
    }

    @Override
    public FragmentActionPerformer<ChooseSpeakLanguagesView> openSpeekLanguageView() {
        return navigationFactory.make(ChooseSpeakLanguagesFragment.class);
    }

    @Override
    public FragmentActionPerformer<CardView> openCreditCardView() {
        return navigationFactory.make(CardFragment.class);
    }

    @Override
    public FragmentActionPerformer<OrgAddBankView> openOrgAddBankView() {
        return navigationFactory.make(OrgAddBankFragment.class);
    }

    @Override
    public FragmentActionPerformer<CurrencyVisitorView> openCurrencyVisitorView() {
        return navigationFactory.make(CurrencyVisitorFragment.class);
    }

    @Override
    public FragmentActionPerformer<WebOpenView> openWebOpenView() {
        return navigationFactory.make(WebOpenFragment.class);
    }

    @Override
    public int findFragmentPlaceHolder() {
        return 0;
    }

    @Override
    public int findContentView() {
        return 0;
    }

    @Override
    public void inject(ActivityComponent activityComponent) {

    }
}
