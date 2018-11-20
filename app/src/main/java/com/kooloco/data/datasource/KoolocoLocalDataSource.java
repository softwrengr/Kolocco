package com.kooloco.data.datasource;


import com.kooloco.core.AppExecutors;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.temp.Temp;
import com.kooloco.model.Activities;
import com.kooloco.model.Bank;
import com.kooloco.model.BlogDetails;
import com.kooloco.model.BookingData;
import com.kooloco.model.BookingFeeAndComision;
import com.kooloco.model.CancellationData;
import com.kooloco.model.CancellationPolicy;
import com.kooloco.model.Card;
import com.kooloco.model.Certifications;
import com.kooloco.model.CheckBank;
import com.kooloco.model.CheckEquipment;
import com.kooloco.model.CheckEquipmentResponse;
import com.kooloco.model.CheckPaymentRules;
import com.kooloco.model.CountryList;
import com.kooloco.model.Currency;
import com.kooloco.model.DisableDateListResposne;
import com.kooloco.model.DisableExperience;
import com.kooloco.model.Duration;
import com.kooloco.model.EarningData;
import com.kooloco.model.Equipment;
import com.kooloco.model.EquipmentResponse;
import com.kooloco.model.ExpDetails;
import com.kooloco.model.ExpFavData;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.ExperienceResponse;
import com.kooloco.model.Favorites;
import com.kooloco.model.FilterGetData;
import com.kooloco.model.HelpAndFaq;
import com.kooloco.model.Home;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.HomeLocalAndExpResponse;
import com.kooloco.model.HomeNewResponse;
import com.kooloco.model.Invite;
import com.kooloco.model.Language;
import com.kooloco.model.LanguageResponse;
import com.kooloco.model.LocalNew;
import com.kooloco.model.MultiFile;
import com.kooloco.model.Notification;
import com.kooloco.model.NotificationReadCheck;
import com.kooloco.model.ObjectionDetails;
import com.kooloco.model.Order;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.OrgLocal;
import com.kooloco.model.OrganizationDashboard;
import com.kooloco.model.OrganizationDetails;
import com.kooloco.model.OrganizationStaus;
import com.kooloco.model.OtheDetailsResponse;
import com.kooloco.model.OtherDetailsAnotherFields;
import com.kooloco.model.PaymentRuleList;
import com.kooloco.model.PaymentRulesOption;
import com.kooloco.model.Quation;
import com.kooloco.model.RatingOption;
import com.kooloco.model.RecentChat;
import com.kooloco.model.Response;
import com.kooloco.model.Review;
import com.kooloco.model.ReviewData;
import com.kooloco.model.SchduleDashboard;
import com.kooloco.model.ScheduleAndPriceResponse;
import com.kooloco.model.SchedulePrice;
import com.kooloco.model.SelectActivites;
import com.kooloco.model.Service;
import com.kooloco.model.SetSpecialAvability;
import com.kooloco.model.Sport;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportPriceRules;
import com.kooloco.model.Tag;
import com.kooloco.model.Time;
import com.kooloco.model.UploadDocument;
import com.kooloco.model.UploadImage;
import com.kooloco.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by hlink21 on 16/5/17.
 */
@Singleton
public class KoolocoLocalDataSource extends BaseDataSource implements KoolocoRepository {
    @Inject
    Session session;

    @Inject
    public KoolocoLocalDataSource(AppExecutors appExecutors) {
        super(appExecutors);
    }

/*
    @Override
    public Single<Response> getBrandList() {
        return null;
    }
*/

    @Override
    public Single<Response<UploadImage>> uploadImage(Map<String, RequestBody> partMap, MultipartBody.Part profileImage) {
        return null;
    }

    @Override
    public Single<Response<UploadDocument>> uploadDocument(MultipartBody.Part profileImage) {
        return null;
    }

    @Override
    public Single<Response<User>> postSignup(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<User>> postLogin(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<User>> postCheckSocial(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<User>> localStepOne(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<User>> addImageStepTwo(Map<String, String> partMap) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<List<SportActivity>>> sportType(Map<String, String> map) {
        return Single.just(new ResponseUtil<List<SportActivity>>().getResponse(Temp.getSportActivities()));
    }

    @Override
    public Single<Response<User>> uploadCertificationStepThree(Map<String, String> partMap) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<User>> uploadAchivementsStepFour(Map<String, String> partMap) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<List<Tag>>> tagList(Map<String, String> map) {
        return Single.just(new ResponseUtil<List<Tag>>().getResponse(Temp.getTags()));
    }

    @Override
    public Single<Response<User>> localLanguage(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<LanguageResponse>> language(Map<String, String> map) {
        return Single.just(new ResponseUtil<LanguageResponse>().getResponse(Temp.getLanguages()));
    }

    @Override
    public Single<Response<List<Currency>>> getCurrency(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<User>> setCurrency(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Activities>>> experience(Map<String, String> map) {
        return Single.just(new ResponseUtil<List<Activities>>().getResponse(Temp.getActivites()));
    }

    @Override
    public Single<Response<User>> setExperienceStepSix(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<User>> setSportPriceRules(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<EquipmentResponse>> equipment(Map<String, String> map) {
        return Single.just(new ResponseUtil<EquipmentResponse>().getResponse(Temp.getEquipmentsResponse().get(0)));
    }

    @Override
    public Single<Response<User>> setSportEquipments(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<User>> setSchedule(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<List<CancellationPolicy>>> cancellation(Map<String, String> map) {
        return Single.just(new ResponseUtil<List<CancellationPolicy>>().getResponse(Temp.getCancellationPolicy()));
    }

    @Override
    public Single<Response<User>> setcancellation(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<User>> setSportLocal(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<List<SportPriceRules>>> getSelectSport(Map<String, String> map) {
        return Single.just(new ResponseUtil<List<SportPriceRules>>().getResponse(Temp.getSelectSport()));
    }

    @Override
    public Single<Response<Equipment>> addEquipment(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<User>> skipSignupStep(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<User>> setLocation(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<User>> setBecomeLocal(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<User>> setVisitor(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<List<DashboardDetails>>> getVisitorHome(Map<String, String> map, boolean isFirstTime) {
        return Single.just(new ResponseUtil<List<DashboardDetails>>().getResponse(new ArrayList<DashboardDetails>()));
    }

    @Override
    public Single<Response<HomeNewResponse>> getVisitorHomeNew(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<HomeLocalAndExpResponse>> getVisitorOnePage(Map<String, String> map) {
        return null;
    }


    @Override
    public Single<Response<List<ExpereinceNew>>> getVisitorHomeExp(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<LocalNew>>> getVisitorHomeLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<DashboardDetails>> getVisitorHomeLocalDetails(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<DisableDateListResposne>> getDisableDateList(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<DashboardDetails>>> getVisitorHomeFilter(Map<String, String> map) {
        return Single.just(new ResponseUtil<List<DashboardDetails>>().getResponse(new ArrayList<DashboardDetails>()));
    }

    @Override
    public Single<Response> logout(Map<String, String> map) {
        return Single.just(new Response());
    }

    @Override
    public Single<Response> changePassword(Map<String, String> map) {
        return Single.just(new Response());
    }

    @Override
    public Single<Response> forgotPassword(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> deleteEquipment(Map<String, String> map) {
        return Single.just(new Response());
    }

    @Override
    public Single<Response<DashboardDetails>> getLocalProfile(Map<String, String> map) {
        DashboardDetails dashboardDetails = new DashboardDetails();


        dashboardDetails.setLocalImages(Temp.getLocalImageSlider());
        dashboardDetails.setImageUrl("android.resource://com.kooloco/drawable/user_round");
        dashboardDetails.setLocalRating("4.5");
        dashboardDetails.setLocalName("Doris Cadiz");
        dashboardDetails.setLocalNTMY("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas non lacinia velit. Suspendisse potenti. Donec at arcu venenatis, commodo tortor. ");

        dashboardDetails.setTimeMon("7:00 am 8:00 am");
        dashboardDetails.setTimeTue("7:00 am 8:00 am");
        dashboardDetails.setTimeWed("7:00 am 8:00 am");
        dashboardDetails.setTimeThu("7:00 am 8:00 am");
        dashboardDetails.setTimeFri("7:00 am 8:00 am");
        dashboardDetails.setTimeSat("7:00 am 8:00 am");
        dashboardDetails.setTimeSun("7:00 am 8:00 am");

        dashboardDetails.setServicesTypes(Temp.getServiceType());
        dashboardDetails.setServices(Temp.getLocalServices());
        dashboardDetails.setCertificateInfos(Temp.getCertificates());
        dashboardDetails.setAchivmentsInfo(Temp.getAchivments());
        dashboardDetails.setReviews(Temp.getReviews());

        dashboardDetails.setLocalLanguages("English, German");
        dashboardDetails.setLocalDistance("8 km");
        dashboardDetails.setLocalLocation("3668 Hood Avenue San Diego, CA 92103");
        dashboardDetails.setOrgName("Biking in the BORO");
        dashboardDetails.setOrgDes("Lorem Ipsum is simply dummy text of the printing \nand typesetting industry.\n");
        dashboardDetails.setOrgAddress("3345 Scenicview Drive Toyah,TX 79785");
        dashboardDetails.setOrgImage("android.resource://com.kooloco/drawable/user_round");
        dashboardDetails.setCancelPolicy("Kool");
        dashboardDetails.setCancelDes("Cancel up to 24 hours before your activity and get a full refund,\nincluding all fees. Cancel within 24 hours of activity and the first night is non-refundable except for fees.");

        Response<DashboardDetails> homeResponse = new Response<>();
        homeResponse.setData(dashboardDetails);

        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<List<Certifications>>> getSport(Map<String, String> map) {
        return Single.just(new ResponseUtil<List<Certifications>>().getResponse(new ArrayList<Certifications>()));

    }

    @Override
    public Single<Response<List<Certifications>>> getCertification(Map<String, String> map) {
        return Single.just(new ResponseUtil<List<Certifications>>().getResponse(new ArrayList<Certifications>()));
    }

    @Override
    public Single<Response<List<Certifications>>> getAchivements(Map<String, String> map) {
        return Single.just(new ResponseUtil<List<Certifications>>().getResponse(new ArrayList<Certifications>()));
    }

    @Override
    public Single<Response> deleteSportImage(Map<String, String> map) {
        return Single.just(new Response());
    }

    @Override
    public Single<Response> deleteCertificate(Map<String, String> map) {
        return Single.just(new Response());
    }

    @Override
    public Single<Response> deleteAchivement(Map<String, String> map) {
        return Single.just(new Response());
    }

    @Override
    public Single<Response<User>> updateProfile(Map<String, String> partMap) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<SelectActivites>> activityAndSport(Map<String, String> map) {
        return getActivites();
    }

    @Override
    public Single<Response<List<Time>>> getStartTime(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Time>>> getEndTime(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<BookingData>> boookAppointment(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Quation>>> getQuation(Map<String, String> map) {
        return null;
    }

    /*  @Override
    public Single<Response<List<Order>>> getPendingOrderVisitor(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Order>>> getPendingOrderLocal(Map<String, String> map) {
        return null;
    }*/

    @Override
    public Single<Response<List<Order>>> getPendingOrderVisitor(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Order>>> getComplateOrderVisitor(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Order>>> getAcceptOrderLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Order>>> getComplateOrderLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Order>>> getPendingOrderLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<SchduleDashboard>>> getSchdule(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<User>> getNotificationAction(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<User>> getNotificationEmailAction(Map<String, String> map) {
        return Single.just(new ResponseUtil<User>().getResponse(Temp.getUser()));
    }

    @Override
    public Single<Response<List<Home>>> getLocalList() {
        List<Home> homes = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Home home = new Home();
            home.setLocalName("Doris Cadiz");
            home.setLocalRating("4.5");
            home.setLocalDistance("8 km");
            home.setImageProfile("android.resource://com.kooloco/drawable/user_round");

            if (i % 2 == 0) {
                home.setFav(true);
            }

            home.setLocalImages(Temp.getLocalImageSlider());
            home.setServices(Temp.getLocalServices());
            home.setServiceTypes(Temp.getServiceType());

            homes.add(home);
        }

        Response<List<Home>> homeResponse = new Response<>();

        homeResponse.setData(homes);
        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<DashboardDetails>> getDashboardDetails() {
        DashboardDetails dashboardDetails = new DashboardDetails();


        dashboardDetails.setLocalImages(Temp.getLocalImageSlider());
        dashboardDetails.setImageUrl("android.resource://com.kooloco/drawable/user_round");
        dashboardDetails.setLocalRating("4.5");
        dashboardDetails.setLocalName("Doris Cadiz");
        dashboardDetails.setLocalNTMY("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas non lacinia velit. Suspendisse potenti. Donec at arcu venenatis, commodo tortor. ");

        dashboardDetails.setTimeMon("7:00 am 8:00 am");
        dashboardDetails.setTimeTue("7:00 am 8:00 am");
        dashboardDetails.setTimeWed("7:00 am 8:00 am");
        dashboardDetails.setTimeThu("7:00 am 8:00 am");
        dashboardDetails.setTimeFri("7:00 am 8:00 am");
        dashboardDetails.setTimeSat("7:00 am 8:00 am");
        dashboardDetails.setTimeSun("7:00 am 8:00 am");

        dashboardDetails.setServicesTypes(Temp.getServiceType());
        dashboardDetails.setServices(Temp.getLocalServices());
        dashboardDetails.setCertificateInfos(Temp.getCertificates());
        dashboardDetails.setAchivmentsInfo(Temp.getAchivments());
        dashboardDetails.setReviews(Temp.getReviews());

        dashboardDetails.setLocalLanguages("English, German");
        dashboardDetails.setLocalDistance("8 km");
        dashboardDetails.setLocalLocation("3668 Hood Avenue San Diego, CA 92103");
        dashboardDetails.setOrgName("Biking in the BORO");
        dashboardDetails.setOrgDes("Lorem Ipsum is simply dummy text of the printing \nand typesetting industry.\n");
        dashboardDetails.setOrgAddress("3345 Scenicview Drive Toyah,TX 79785");
        dashboardDetails.setOrgImage("android.resource://com.kooloco/drawable/user_round");
        dashboardDetails.setCancelPolicy("Kool");
        dashboardDetails.setCancelDes("Cancel up to 24 hours before your activity and get a full refund,\nincluding all fees. Cancel within 24 hours of activity and the first night is non-refundable except for fees.");

        Response<DashboardDetails> homeResponse = new Response<>();
        homeResponse.setData(dashboardDetails);

        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<OrganizationDetails>> getOrganizationDetails() {
        OrganizationDetails organizationDetails = new OrganizationDetails();

        organizationDetails.setImageUrl("android.resource://com.kooloco/drawable/user_round");
        organizationDetails.setOrgName("Biking in the BORO");
        organizationDetails.setOrgActivityTypes("Discoveries");
        organizationDetails.setOrgSportTypes("Skiing : Freeride, Freestyle");
        organizationDetails.setOrgLocation("3668 Hood Avenue Sna Diego, CA 92103");

        organizationDetails.setOrgLocals(Temp.getOrgLocals());
        organizationDetails.setOrgImage(Temp.getOrgImage());


        Response<OrganizationDetails> homeResponse = new Response<>();
        homeResponse.setData(organizationDetails);

        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<SelectActivites>> getActivites() {
        SelectActivites selectActivites = new SelectActivites();
        selectActivites.setActivities(Temp.getActivites());
        //  selectActivites.setServices(Temp.getLocalServices());

        Response<SelectActivites> homeResponse = new Response<>();
        homeResponse.setData(selectActivites);

        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<List<Time>>> getTimes() {
        Response<List<Time>> homeResponse = new Response<>();
        homeResponse.setData(Temp.getTimes());

        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<List<Duration>>> getDuration() {
        Response<List<Duration>> homeResponse = new Response<>();
        homeResponse.setData(Temp.getDurations());

        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<List<Card>>> getPayment() {
        Response<List<Card>> homeResponse = new Response<>();
        homeResponse.setData(Temp.getCards());

        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<List<Order>>> getOrderPending() {
        Response<List<Order>> homeResponse = new Response<>();
        homeResponse.setData(Temp.getOrderPending());

        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<List<Order>>> getOrderComplate() {
        Response<List<Order>> homeResponse = new Response<>();
        homeResponse.setData(Temp.getOrdersComplate());
        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<List<Invite>>> getInvites() {
        Response<List<Invite>> homeResponse = new Response<>();
        homeResponse.setData(Temp.getInvites());
        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<List<Notification>>> getNotifications(Map<String, String> map) {
        Response<List<Notification>> homeResponse = new Response<>();
        homeResponse.setData(Temp.getNotifications());
        return Single.just(homeResponse);
    }

    @Override
    public Single<Response> acceptOrder(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> rejectOrder(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> complateOrder(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> cancelOrder(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> sendPaymentRequest(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<OrderDetails>> orderDetailsLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<OrderDetails>> orderDetailsVisitor(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<Order>> orderDataLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<Order>> orderDataVisitor(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> sendObjection(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> payNow(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ObjectionDetails>> objectionDetails(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> acceptObjection(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> modifyObjection(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ObjectionDetails>> objectionDetailsVisitor(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> acceptObjectionVisitor(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> adminObjectionRequest(Map<String, String> map) {
        return null;
    }


    @Override
    public Single<Response<List<String>>> getDurationLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> setModifyLocationDuration(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> declineLocationDuration(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> notifyAction(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<ExperienceDetails>>> getExperience(Map<String, String> map, boolean isFirstTime) {
        return null;
    }

    @Override
    public Single<Response<List<BlogDetails>>> getBlogList(Map<String, String> map, boolean isFirstTime) {
        return null;
    }

    @Override
    public Single<Response<BlogDetails>> bloglike(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Review>>> blogComment(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<Review>> addComment(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> publishExperience(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<FilterGetData>> filterData(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<OrganizationDetails>> createOrg(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<OrganizationDetails>> orgDetails(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> editOrg(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> deleteOrgMediea(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<OrgLocal>>> orgLocalList(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> addToLocalOrg(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<PaymentRuleList>>> getSetPaymentRules(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<PaymentRulesOption>>> getPaymentRulesOption(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<OrgLocal>>> getUnAssignLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> setPaymentRule(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> editPaymentRule(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> deletePaymentRule(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> acceptOrg(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> declineOrg(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> exitOrg(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Activities>>> getVisitorActivities(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<BookingFeeAndComision>> getBookingFees(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<BookingFeeAndComision>> getBookingFeesNew(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<RatingOption>>> getRatOption(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> setRateVisitor(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> setRateLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<User>> getProfileVisitor(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ReviewData>> getRateVisitor(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ReviewData>> getRateLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ReviewData>> getRateExp(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<EarningData>> getEarningLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> getdontWantComplete(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<OrganizationStaus>> getOrgStatus(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> getdeleteLocalOrg(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<OrganizationDashboard>> getOrgDashboard(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<CheckPaymentRules>> getIsCheckLocalAffilted(Map<String, String> map) {
        return null;
    }


    @Override
    public Single<Response<DashboardDetails>> setBankDetails(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ExperienceResponse>> setExpAddDetails(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ExperienceResponse>> getExpAddDetails(Map<String, String> map) {
        return null;
    }


    @Override
    public Single<Response<ExperienceResponse>> setExpActivity(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ExperienceResponse>> setExpSport(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ExperienceResponse>> setExpMeetingSpot(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ExperienceResponse>> setExpCancelationPolicy(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<OtheDetailsResponse>> getExpOtherDetails(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ExperienceResponse>> setExpOtherDetails(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<OtherDetailsAnotherFields>> setExpOtherDetailsFields(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<OtherDetailsAnotherFields>>> getExpOtherDetailsFields(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> deleteExpOtherDetailsFields(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<DisableExperience>> getExpDisable(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> setExpDisable(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ScheduleAndPriceResponse>> getExpAvability(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ScheduleAndPriceResponse>> setExpAvability(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ScheduleAndPriceResponse>> editExpAvability(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ScheduleAndPriceResponse>> deleteExpAvability(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> setExpMaxParticipants(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> deleteExp(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ExpereinceNew>> getExpEdit(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ExpDetails>> getExpDetails(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<SchedulePrice>>> getSelectDateSchedule(Map<String, String> map) {
        return null;
    }


    @Override
    public Single<Response<HomeLocalAndExpResponse>> getFilterOnePage(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<ExpereinceNew>>> getFilterExp(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<LocalNew>>> getFilterLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> setOnBordingData(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> setSPeakLanguage(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<NotificationReadCheck>> getNotificationReadCheck(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> setNotificationRead(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> setExpEquipment(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<CheckEquipmentResponse>> getEquipmentOrder(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> setEquipmentOrder(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<CheckEquipment>> addEquipmentOrder(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<ExpereinceNew>>> getExpListLocal(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Card>>> getCreditCardList(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> deleteCreditCardList(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Card>>> addCreditCardList(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<CountryList>>> getCountryList(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> changeDefaultBank(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> deleteBank(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Bank>>> getBankList(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<CheckBank>> checkOrgBank(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<CancellationData>> getCancellationAmount(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> visitorCancel(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> localCancel(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Order>>> getLocalMonthEarning(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<ExpereinceNew>>> getFavExp(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<ExpFavData>> setFavExp(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> localOrderEquipmentDelete(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> deleteOrgnization(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> acceptLocationDuration(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Sport>>> getSports() {
        Response<List<Sport>> homeResponse = new Response<>();
        homeResponse.setData(Temp.getSports());
        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<List<RecentChat>>> getRecntChat() {
        Response<List<RecentChat>> homeResponse = new Response<>();
        homeResponse.setData(Temp.getRecentChats());
        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<List<Favorites>>> getFav() {
        Response<List<Favorites>> homeResponse = new Response<>();
        homeResponse.setData(Temp.getFavorites());
        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<List<HelpAndFaq>>> getHelpAndFaq() {
        Response<List<HelpAndFaq>> homeResponse = new Response<>();
        homeResponse.setData(Temp.getHelpAndFaqs());
        return Single.just(homeResponse);
    }

    @Override
    public Single<Response<List<MultiFile>>> uploadFile(Map<String, RequestBody> partMap, List<MultipartBody.Part> partFile) {
        return null;
    }

    @Override
    public Single<Response<BlogDetails>> blogDetails(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> deleteBlogMediea(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> editExperience(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response> setSpecialAvavility(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<SetSpecialAvability>> getSpecialAvavility(Map<String, String> map) {
        return null;
    }

    @Override
    public Single<Response<List<Service>>> sportServiceType(Map<String, String> map) {
        return null;
    }

    private Response getResponse() {
        Response response = new Response();
        response.setResponseCode(1);
        response.setMessage("Successfully");
        return response;
    }
}
