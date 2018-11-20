package com.kooloco.di.component;


import com.kooloco.di.PerFragment;
import com.kooloco.di.module.FragmentModule;
import com.kooloco.ui.alllocal.fragment.AllLocalMapNewFragment;
import com.kooloco.ui.authantication.fragment.ForgotPasswordFragment;
import com.kooloco.ui.expereince.fragment.DemoCalenderFragment;
import com.kooloco.ui.expereince.fragment.ExperienceDetailsFragment;
import com.kooloco.ui.expereince.fragment.ExperienceSelectDateTimeFragment;
import com.kooloco.ui.expereince.fragment.ExperienceSelectVisitorFragment;
import com.kooloco.ui.expereince.fragment.ExperinceAddParticipantsFragment;
import com.kooloco.ui.myexperience.fragment.BlogDetailsFragment;
import com.kooloco.ui.myexperience.fragment.BlogListFragment;
import com.kooloco.ui.myexperience.fragment.CreateBlogFragment;
import com.kooloco.ui.myexperience.fragment.ImageFilterFragment;
import com.kooloco.ui.notification.fragment.CheckSportEaquipmentsFragment;
import com.kooloco.ui.notification.fragment.NotificationFragment;
import com.kooloco.ui.notification.fragment.ReciveObjectionDetailsFragment;
import com.kooloco.ui.alllocal.fragment.AllLocalMapFragment;
import com.kooloco.ui.authantication.fragment.SignUpFragment;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.chat.fragment.ChatFragment;
import com.kooloco.ui.chat.fragment.RecentChatFragment;
import com.kooloco.ui.fragment.ChildFragment;
import com.kooloco.ui.fragment.LoginFragment;
import com.kooloco.ui.invite.fragment.InviteFragment;
import com.kooloco.ui.invite.fragment.InviteListFragment;
import com.kooloco.ui.onboarding.fragment.OnBoardingActivityFragment;
import com.kooloco.ui.onboarding.fragment.OnBoardingFragment;
import com.kooloco.ui.payment.fragment.CardFragment;
import com.kooloco.ui.profile.fragment.BecomeLocalWebFragment;
import com.kooloco.ui.profile.fragment.CurrencyVisitorFragment;
import com.kooloco.ui.visitor.dashboard.fragment.SelectActivityNewFragment;
import com.kooloco.ui.visitor.dashboard.fragment.SelectServiceNewFragment;
import com.kooloco.ui.visitor.home.fragment.HomeLocalAndExpFragment;
import com.kooloco.ui.visitor.home.fragment.HomeNewFragment;
import com.kooloco.ui.visitor.organizationDetails.fragment.MettingLocationFragment;
import com.kooloco.ui.visitor.organizationDetails.fragment.OrganizationReportFragment;
import com.kooloco.ui.webOpen.fragment.WebOpenFragment;
import com.kooloco.uilocal.Notification.fragment.ModifyObjectionFragment;
import com.kooloco.uilocal.Notification.fragment.NotificationLocalFragment;
import com.kooloco.uilocal.Notification.fragment.ReceivedObjectionLocalFragment;
import com.kooloco.uilocal.addservice.fragment.AddImageFragment;
import com.kooloco.uilocal.addservice.fragment.BecomeALocalFragment;
import com.kooloco.ui.myexperience.fragment.MyExperienceDetailsFragment;
import com.kooloco.ui.myexperience.fragment.MyExperienceFragment;
import com.kooloco.ui.profile.fragment.CanecellationFragment;
import com.kooloco.ui.profile.fragment.ChangePasswordFragment;
import com.kooloco.ui.profile.fragment.FavoritesFragment;
import com.kooloco.ui.profile.fragment.HelpAndFaqFragment;
import com.kooloco.ui.profile.fragment.OrderDetailsFragment;
import com.kooloco.ui.profile.fragment.OrderHIstoryFragment;
import com.kooloco.ui.profile.fragment.OrderHistoryComplateFragment;
import com.kooloco.ui.profile.fragment.OrderHistoryPendingFragment;
import com.kooloco.ui.profile.fragment.ProfileFragment;
import com.kooloco.ui.profile.fragment.SettingsFragment;
import com.kooloco.ui.profile.fragment.ViewProfileFragment;
import com.kooloco.ui.rating.fragment.RatingnFragment;
import com.kooloco.ui.visitor.dashboard.fragment.AddParticipantsFragment;
import com.kooloco.ui.visitor.dashboard.fragment.AppointmentBookedFragment;
import com.kooloco.ui.visitor.dashboard.fragment.AppointmentSummaryFragment;
import com.kooloco.ui.visitor.dashboard.fragment.CertificatesFragment;
import com.kooloco.ui.visitor.dashboard.fragment.DashboardFragment;
import com.kooloco.ui.visitor.dashboard.fragment.DurationFragment;
import com.kooloco.ui.visitor.dashboard.fragment.MeetingLocationFragment;
import com.kooloco.ui.visitor.dashboard.fragment.PaymentFragment;
import com.kooloco.ui.visitor.dashboard.fragment.ReceiptFragment;
import com.kooloco.ui.visitor.dashboard.fragment.SelectActivitiesFragment;
import com.kooloco.ui.visitor.dashboard.fragment.SelectDateFragment;
import com.kooloco.ui.visitor.home.fragment.HomeFragment;
import com.kooloco.ui.visitor.organizationDetails.fragment.LocalVisitorMapFragment;
import com.kooloco.ui.visitor.organizationDetails.fragment.OrganizationDetailsFragment;
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
import com.kooloco.uilocal.complateorder.fragment.CompleteOrderFragment;
import com.kooloco.uilocal.earnings.fragment.AllMonthFragment;
import com.kooloco.uilocal.earnings.fragment.EarningsFragment;
import com.kooloco.uilocal.earnings.fragment.MonthEarningFragment;
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
import com.kooloco.uilocal.home.fragment.AcceptOrderDetailsFragment;
import com.kooloco.uilocal.home.fragment.HomeAcceptFragment;
import com.kooloco.uilocal.home.fragment.HomePendingFragment;
import com.kooloco.uilocal.home.fragment.LocalCheckSportEaquipmentsFragment;
import com.kooloco.uilocal.home.fragment.OrderDetailsLocalFragment;
import com.kooloco.uilocal.home.fragment.RateAndReviewFragment;
import com.kooloco.uilocal.modify.fragment.ModifyAddressFragment;
import com.kooloco.uilocal.modify.fragment.ModifyDurationFragment;
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
import com.kooloco.uilocal.profile.fragment.EditAchievementsFragment;
import com.kooloco.uilocal.profile.fragment.EditCertificationsFragment;
import com.kooloco.uilocal.profile.fragment.EditProfileFragment;
import com.kooloco.uilocal.profile.fragment.LocalIncompleteStepFragment;
import com.kooloco.uilocal.profile.fragment.ProfileLocalFragment;
import com.kooloco.uilocal.profile.fragment.ViewProfileLocalFragment;
import com.kooloco.uilocal.setavability.fragment.DisableSportFragment;
import com.kooloco.uilocal.setavability.fragment.SetAvabilityFragment;
import com.kooloco.uilocal.setavability.fragment.SetSpecialAvabilitiesFragment;
import com.kooloco.util.MyCustomProgressDialog;

import dagger.Subcomponent;

/**
 * Created by hlink21 on 31/5/16.
 */

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    BaseFragment baseFragment();

    void inject(LoginFragment loginFragment);

    void inject(ChildFragment childFragment);

    void inject(com.kooloco.ui.authantication.fragment.LoginFragment loginFragment);

    void inject(SignUpFragment signUpFragment);

    void inject(BecomeALocalFragment becomeALocalFragment);

    void inject(CertificatesFragment certificatesFragment);


    void inject(AddParticipantsFragment addParticipantsFragment);

    void inject(SelectActivitiesFragment selectActivitiesFragment);

    void inject(HomeFragment homeFragment);

    void inject(DashboardFragment dashboardFragment);

    void inject(OrganizationDetailsFragment organizationDetailsFragment);

    void inject(LocalVisitorMapFragment localVisitorMapFragment);

    void inject(SelectDateFragment selectDateFragment);

    void inject(DurationFragment durationFragment);

    void inject(MeetingLocationFragment meetingLocationFragment);

    void inject(AppointmentSummaryFragment appointmentSummaryFragment);

    void inject(PaymentFragment paymentFragment);

    void inject(AppointmentBookedFragment appointmentBookedFragment);

    void inject(ReceiptFragment receiptFragment);

    void inject(RatingnFragment ratingnFragment);

    void inject(ProfileFragment profileFragment);

    void inject(ViewProfileFragment viewProfileFragment);

    void inject(OrderHIstoryFragment orderHIstoryFragment);

    void inject(OrderHistoryComplateFragment orderHistoryComplateFragment);

    void inject(OrderHistoryPendingFragment orderHistoryPendingFragment);

    void inject(OrderDetailsFragment orderDetailsFragment);

    void inject(CanecellationFragment canecellationFragment);

    void inject(InviteFragment inviteFragment);

    void inject(InviteListFragment inviteListFragment);

    void inject(NotificationFragment notificationFragment);

    void inject(ReciveObjectionDetailsFragment reciveObjectionDetailsFragment);

    void inject(CheckSportEaquipmentsFragment checkSportEaquipmentsFragment);

    void inject(RecentChatFragment recentChatFragment);

    void inject(FavoritesFragment favoritesFragment);

    void inject(SettingsFragment settingsFragment);

    void inject(ChangePasswordFragment changePasswordFragment);

    void inject(ChatFragment chatFragment);

    void inject(HelpAndFaqFragment helpAndFaqFragment);

    void inject(MyExperienceFragment myExperienceFragment);

    void inject(MyExperienceDetailsFragment myExperienceDetailsFragment);

    void inject(AllLocalMapFragment allLocalMapFragment);

    void inject(AddImageFragment addImageFragment);

    void inject(UploadCertificationsFragment uploadCertificationsFragment);

    void inject(UploadAchievementsFragment uploadAchievementsFragment);

    void inject(ChooseLanguagesFragment chooseLanguagesFragment);

    void inject(CreateYourExperienceFragment createYourExperienceFragment);

    void inject(ChooseSportActivitiesFragment chooseSportActivitiesFragment);

    void inject(ChooseSportActivities1Fragment chooseSportActivities1Fragment);

    void inject(ChooseSportActivities2Fragment chooseSportActivities2Fragment);

    void inject(ChooseSportEquipmentsFragment chooseSportEquipmentsFragment);

    void inject(CancellationPoliciesFragment cancellationPoliciesFragment);

    void inject(SetLocationFragment setLocationFragment);

    void inject(SetAvailabilitiesFragment setAvailabilitiesFragment);

    void inject(SuccessLocalFragment successLocalFragment);

    void inject(com.kooloco.uilocal.home.fragment.HomeFragment homeFragment);

    void inject(HomePendingFragment homePendingFragment);

    void inject(HomeAcceptFragment homeAcceptFragment);


    void inject(OrderDetailsLocalFragment orderDetailsLocalFragment);

    void inject(AcceptOrderDetailsFragment acceptOrderDetailsFragment);

    void inject(OrganizationReportFragment organizationReportFragment);

    void inject(ProfileLocalFragment profileLocalFragment);

    void inject(EarningsFragment earningsFragment);

    void inject(MonthEarningFragment monthEarningFragment);

    void inject(AllMonthFragment allMonthFragment);

    void inject(ViewProfileLocalFragment viewProfileLocalFragment);

    void inject(CompleteOrderFragment complateOrderFragment);

    void inject(NotificationLocalFragment notificationLocalFragment);

    void inject(ReceivedObjectionLocalFragment receivedObjectionLocalFragment);

    void inject(ModifyObjectionFragment modifyObjectionFragment);

    void inject(CreateYourOrganizationFragment createYourOrganizationFragment);

    void inject(PreviewDetailsFragment previewDetailsFragment);

    void inject(AddBankFragment addBankFragment);

    void inject(PerAddBankFragment perAddBankViewFragment);

    void inject(OrganizationDashboardFragment organizationDetailsFragment);

    void inject(SetAvabilityFragment setAvabilityFragment);

    void inject(SetSpecialAvabilitiesFragment setSpecialAvabilitiesFragment);

    void inject(DisableSportFragment disableSportFragment);

    void inject(EditProfileFragment editProfileFragment);

    void inject(RateAndReviewFragment rateAndReviewFragment);

    void inject(EditOrganizationFragment editOrganizationFragment);

    void inject(EditCertificationsFragment editCertificationsFragment);

    void inject(EditAchievementsFragment editAchievementsFragment);

    void inject(PreviewDetailsSingalLocalFragment previewDetailsSingalLocalFragment);

    void inject(BlogListFragment blogListFragment);

    void inject(BlogDetailsFragment blogDetailsFragment);

    void inject(CreateBlogFragment createBlogFragment);

    void inject(ChooseSportActivitiesNewFragment chooseSportActivitiesNewFragment);

    void inject(SportPriceRulesFragment sportPriceRulesFragment);

    void inject(SportPriceRulesTabFragment sportPriceRulesTabFragment);

    void inject(ModifyAddressFragment modifyAddressFragment);

    void inject(ModifyDurationFragment modifyDurationFragment);

    void inject(ImageFilterFragment imageFilterFragment);

    void inject(AssignLocalFragment assignLocalFragment);

    void inject(SetPaymentRulesFragment setPaymentRulesFragment);

    void inject(AddNewPaymentRulesFragment addNewPaymentRulesFragment);

    void inject(SelectServiceNewFragment selectServiceNewFragment);

    void inject(SelectActivityNewFragment selectActivityNewFragment);

    void inject(MyCustomProgressDialog myCustomProgressDialog);

    void inject(MettingLocationFragment mettingLocationFragment);

    void inject(BecomeLocalWebFragment becomeLocalWebFragment);

    void inject(LocalIncompleteStepFragment localIncompleteStepFragment);

    void inject(OrganizationStatusFragment organizationStatusFragment);

    void inject(AddLocalFragment addLocalFragment);

    void inject(OrganizationIncompleteStepFragment organizationIncompleteStepFragment);

    void inject(AllLocalMapNewFragment allLocalMapNewFragment);

    void inject(ForgotPasswordFragment forgotPasswordFragment);

    void inject(ExperienceFragment experienceFragment);

    void inject(EditExperienceFragment editExperienceFragment);

    void inject(ExperienceSelectFragment experienceSelectFragment);

    void inject(ExperienceSportFragment experienceSportFragment);

    void inject(AddDetailsFragment addDetailsFragment);

    void inject(OtherDetailsFragment otherDetailsFragment);

    void inject(OtherDetailsAnotherFieldsFragment otherDetailsAnotherFieldsFragment);

    void inject(ScheduleAndPriceFragment scheduleAndPriceFragment);

    void inject(ExperienceMeetingSpotFragment experienceMeetingSpotFragment);

    void inject(ExperienceCancellationPoliciesFragment experienceCancellationPoliciesFragment);

    void inject(ExperienceSetSpecialAvailabilitesFragment experienceSetSpecialAvailabilitesFragment);

    void inject(ExperienceDisableExperienceFragment experienceDisableExperienceFragment);

    void inject(ExperienceSportEquipmentsFragment experienceSportEquipmentsFragment);

    void inject(ExperienceInCompleteStepFragment experienceInCompleteStepFragment);

    void inject(ExperienceDetailsFragment experienceDetailsFragment);

    void inject(DemoCalenderFragment demoCalenderFragment);

    void inject(HomeNewFragment homeNewFragment);

    void inject(HomeLocalAndExpFragment homeLocalAndExpFragment);

    void inject(ExperienceSelectVisitorFragment experienceSelectVisitorFragment);

    void inject(ExperienceSelectDateTimeFragment experienceSelectDateTimeFragment);

    void inject(ExperinceAddParticipantsFragment experinceAddParticipantsFragment);

    void inject(OnBoardingFragment onBoardingFragment);

    void inject(OnBoardingActivityFragment onBoardingActivityFragment);

    void inject(ChooseSpeakLanguagesFragment chooseSpeakLanguagesFragment);

    void inject(LocalCheckSportEaquipmentsFragment localCheckSportEaquipmentsFragment);

    void inject(CardFragment cardFragment);

    void inject(OrgAddBankFragment orgAddBankFragment);

    void inject(CurrencyVisitorFragment currencyVisitorFragment);

    void inject(WebOpenFragment webOpenFragment);
}
