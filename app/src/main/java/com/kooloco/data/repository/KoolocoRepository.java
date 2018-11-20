package com.kooloco.data.repository;


import com.kooloco.data.URLFactory;
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
import com.kooloco.model.OrderOrg;
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

import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.POST;

/**
 * Created by hlink21 on 11/5/17.
 */

public interface KoolocoRepository {


    Single<Response<UploadImage>> uploadImage(Map<String, RequestBody> partMap, MultipartBody.Part profileImage);

    /**
     * It is used to upload image
     * @param profileImage
     * @return
     */
    Single<Response<UploadDocument>> uploadDocument(MultipartBody.Part profileImage);

    /**
     * It is used to sign up
     * @return
     */
    Single<Response<User>> postSignup(Map<String, String> map);

    /**
     * It is used to login visitor and local
     * @param map pass user login information
     * @return return user
     */
    Single<Response<User>> postLogin(Map<String, String> map);

    /**
     * It is used to check social media login
     * @param map
     * @return
     */
    Single<Response<User>> postCheckSocial(Map<String, String> map);

    /**
     * It is used to become local signup step 1
     * @param map
     * @return
     */
    Single<Response<User>> localStepOne(Map<String, String> map);

    /**
     * It is used to  become local add images
     * @param partMap
     * @return
     */
    Single<Response<User>> addImageStepTwo(Map<String, String> partMap);

    /**
     * It is used to get sport list
     * It user id pass then getting selected fields
     * @param map
     * @return
     */
    Single<Response<List<SportActivity>>> sportType(Map<String, String> map);

    /**
     * It is used to become local upload certification
     * @param partMap
     * @return
     */
    Single<Response<User>> uploadCertificationStepThree(Map<String, String> partMap);

    /**
     * It is used to become local upload achievements
     * @param partMap
     * @return
     */
    Single<Response<User>> uploadAchivementsStepFour(Map<String, String> partMap);

    /**
     * It is used to get tag list (Upload achievement)
     * @param map
     * @return
     */
    Single<Response<List<Tag>>> tagList(Map<String, String> map);

    /**
     * It is used to get language
     * If pass user id then getting selected fields
     * @param map
     * @return
     */
    Single<Response<User>> localLanguage(Map<String, String> map);

    /**
     * It is used to get language
     * It pass user id then getting selected fields
     * @param map
     * @return
     */
    Single<Response<LanguageResponse>> language(Map<String, String> map);

    /**
     * It is used to get currency
     * If pass user id then getting selected fields
     * @param map
     * @return
     */
    Single<Response<List<Currency>>> getCurrency(Map<String, String> map);

    /**
     * It is used to set currency
     * @param map
     * @return
     */
    Single<Response<User>> setCurrency(Map<String, String> map);

    /**
     * It is used to get activity list
     * If pass user id then getting selected fields
     * @param map
     * @return
     */
    Single<Response<List<Activities>>> experience(Map<String, String> map);

    /**
     * it is used to set experience step six
     * @param map
     * @return
     */
    Single<Response<User>> setExperienceStepSix(Map<String, String> map);

    /**
     * it is used to set sport price rule
     * @param map
     * @return
     */
    Single<Response<User>> setSportPriceRules(Map<String, String> map);

    Single<Response<EquipmentResponse>> equipment(Map<String, String> map);

    /**
     * it is used to set sport equipment
     * @param map
     * @return
     */
    Single<Response<User>> setSportEquipments(Map<String, String> map);

    /**
     * it is used to set schedule
     * @param map
     * @return
     */
    Single<Response<User>> setSchedule(Map<String, String> map);

    Single<Response<List<CancellationPolicy>>> cancellation(Map<String, String> map);

    /**
     * it is used to set cancellation policy
     * @param map
     * @return
     */
    Single<Response<User>> setcancellation(Map<String, String> map);

    /**
     * it is used to set sport local
     * @param map
     * @return
     */
    Single<Response<User>> setSportLocal(Map<String, String> map);

    /**
     * it is used to select sport
     * @param map
     * @return
     */
    Single<Response<List<SportPriceRules>>> getSelectSport(Map<String, String> map);

    /**
     * it is used to add equipment
     * @param map
     * @return
     */
    Single<Response<Equipment>> addEquipment(Map<String, String> map);

    /**
     * it is used to skip sign up step
     * @param map
     * @return
     */
    Single<Response<User>> skipSignupStep(Map<String, String> map);

    /**
     * it is used to set location
     * @param map
     * @return
     */
    Single<Response<User>> setLocation(Map<String, String> map);

    /**
     * it is used to set become a local
     * @param map
     * @return
     */
    Single<Response<User>> setBecomeLocal(Map<String, String> map);

    Single<Response<User>> setVisitor(Map<String, String> map);

    Single<Response<List<DashboardDetails>>> getVisitorHome(Map<String, String> map, boolean isFirstTime);

    Single<Response<HomeNewResponse>> getVisitorHomeNew(Map<String, String> map);

    /**
     * it is used to get page one data on visitor side
     * @param map
     * @return
     */
    Single<Response<HomeLocalAndExpResponse>> getVisitorOnePage(@Body() Map<String, String> map);

    /**
     * it is used to get experience list
     * @param map
     * @return
     */
    Single<Response<List<ExpereinceNew>>> getVisitorHomeExp(Map<String, String> map);

    /**
     * it is used to get local
     * @param map
     * @return
     */
    Single<Response<List<LocalNew>>> getVisitorHomeLocal(Map<String, String> map);

    /**
     * it is used to get local detail
     * @param map
     * @return
     */
    Single<Response<DashboardDetails>> getVisitorHomeLocalDetails(@Body() Map<String, String> map);

    /**
     * it is used to get disable date
     * @param map
     * @return
     */
    Single<Response<DisableDateListResposne>> getDisableDateList(Map<String, String> map);

    /**
     * it is used to get visitor home filter data
     * @param map
     * @return
     */
    Single<Response<List<DashboardDetails>>> getVisitorHomeFilter(Map<String, String> map);

    /**
     * it is used to logout
     * @param map
     * @return
     */
    Single<Response> logout(Map<String, String> map);

    /**
     * it is used to change password
     * @param map
     * @return
     */
    Single<Response> changePassword(Map<String, String> map);

    /**
     * it is used to forgot password
     * @param map
     * @return
     */
    Single<Response> forgotPassword(Map<String, String> map);

    /**
     * it is used to delete equipment
     * @param map
     * @return
     */
    Single<Response> deleteEquipment(Map<String, String> map);

    /**
     * it is used to get local profile
     * @param map
     * @return
     */
    Single<Response<DashboardDetails>> getLocalProfile(Map<String, String> map);

    /**
     * it is used to get sport
     * @param map
     * @return
     */
    Single<Response<List<Certifications>>> getSport(Map<String, String> map);

    /**
     * it is used to get certificate
     * @param map
     * @return
     */
    Single<Response<List<Certifications>>> getCertification(Map<String, String> map);

    /**
     * it is used to get achievement
     * @param map
     * @return
     */
    Single<Response<List<Certifications>>> getAchivements(Map<String, String> map);

    /**
     * it is used to delete sport
     * @param map
     * @return
     */
    Single<Response> deleteSportImage(Map<String, String> map);

    /**
     * it is used to delete certificate
     * @param map
     * @return
     */
    Single<Response> deleteCertificate(Map<String, String> map);

    /**
     * it is used to delete achievement
     * @param map
     * @return
     */
    Single<Response> deleteAchivement(Map<String, String> map);

    /**
     * it is used to update profile
     * @param partMap
     * @return
     */
    Single<Response<User>> updateProfile(Map<String, String> partMap);

    /**
     * it is used to get activity and sport
     * @param map
     * @return
     */
    Single<Response<SelectActivites>> activityAndSport(Map<String, String> map);

    /**
     * it is not used currently
     * @param map
     * @return
     */
    Single<Response<List<Time>>> getStartTime(Map<String, String> map);

    /**
     * it is not used currently
     * @param map
     * @return
     */
    Single<Response<List<Time>>> getEndTime(Map<String, String> map);

    /**
     * it is used to book an appointment
     * @param map
     * @return
     */
    Single<Response<BookingData>> boookAppointment(Map<String, String> map);

    /**
     * it is ued to get
     * @param map
     * @return
     */
    Single<Response<List<Quation>>> getQuation(Map<String, String> map);

  /*  Single<Response<List<Order>>> getPendingOrderVisitor(Map<String, String> map);

    Single<Response<List<Order>>> getPendingOrderLocal(Map<String, String> map);
*/

    /**
     * it is ued to get pending order visitor side
     * @param map
     * @return
     */
    Single<Response<List<Order>>> getPendingOrderVisitor(@FieldMap() Map<String, String> map);

    /**
     * IT is used to get complete order visitor
     * @param map
     * @return
     */
    Single<Response<List<Order>>> getComplateOrderVisitor(@FieldMap() Map<String, String> map);

    /**
     * It is used to get pending order visitor
     * @param map
     * @return
     */
    Single<Response<List<Order>>> getPendingOrderLocal(@FieldMap() Map<String, String> map);

    /**
     * it is used to get accept order local
     * @param map
     * @return
     */
    Single<Response<List<Order>>> getAcceptOrderLocal(@FieldMap() Map<String, String> map);

    /**
     * it is used to get complete order local
     * @param map
     * @return
     */
    Single<Response<List<Order>>> getComplateOrderLocal(@FieldMap() Map<String, String> map);

    /**
     * it is used get schedule
     * @param map
     * @return
     */
    Single<Response<List<SchduleDashboard>>> getSchdule(Map<String, String> map);

    /**
     * it is used to get  notification action setting screen
     * @param map
     * @return
     */
    Single<Response<User>> getNotificationAction(Map<String, String> map);

    /**
     * it is used to get email notification setting screen
     * @param map
     * @return
     */
    Single<Response<User>> getNotificationEmailAction(Map<String, String> map);

    /**
     * It is used to get notifications lists
     * @param map
     * @return
     */
    Single<Response<List<Notification>>> getNotifications(Map<String, String> map);

    /**
     * IT is used to accept order local
     * @param map
     * @return
     */
    Single<Response> acceptOrder(Map<String, String> map);

    /**
     * It is used to reject order local
     * @param map
     * @return
     */
    Single<Response> rejectOrder(Map<String, String> map);

    /**
     * IT is used to complete order local
     * @param map
     * @return
     */
    Single<Response> complateOrder(Map<String, String> map);

    /**
     * it is ued to cancel order locla
     * @param map
     * @return
     */
    Single<Response> cancelOrder(Map<String, String> map);

    /**
     * It is used to send payment request
     * @param map
     * @return
     */
    Single<Response> sendPaymentRequest(Map<String, String> map);

    /**
     * it is used to get order details local side
     * @param map
     * @return
     */
    Single<Response<OrderDetails>> orderDetailsLocal(Map<String, String> map);

    /**
     * it is used to get order details visitor side
     * @param map
     * @return
     */
    Single<Response<OrderDetails>> orderDetailsVisitor(Map<String, String> map);

    /**
     * it is used to get order list local side
     * @param map
     * @return
     */
    Single<Response<Order>> orderDataLocal(Map<String, String> map);

    /**
     * it is used to get order list visitor side
     * @param map
     * @return
     */
    Single<Response<Order>> orderDataVisitor(Map<String, String> map);

    /**
     * it is used to send objection visitor
     * @param map
     * @return
     */
    Single<Response> sendObjection(Map<String, String> map);

    /**
     * it is used to pay order
     * @param map
     * @return
     */
    Single<Response> payNow(Map<String, String> map);

    /**
     * It is used get objection details locla side
     * @param map
     * @return
     */
    Single<Response<ObjectionDetails>> objectionDetails(Map<String, String> map);

    /**
     * IT is used to accept objection local side
     * @param map
     * @return
     */
    Single<Response> acceptObjection(Map<String, String> map);

    /**
     * It is used to modify objection local
     * @param map
     * @return
     */
    Single<Response> modifyObjection(Map<String, String> map);

    /**
     * IT is used to get objection details visitor side
     * @param map
     * @return
     */
    Single<Response<ObjectionDetails>> objectionDetailsVisitor(Map<String, String> map);

    /**
     * It is used to accept objection visitor side
     * @param map
     * @return
     */
    Single<Response> acceptObjectionVisitor(Map<String, String> map);

    /**
     * it is used to send objection request admin
     * @param map
     * @return
     */
    Single<Response> adminObjectionRequest(Map<String, String> map);


    /**
     * It used to get location duration
     * Not used currently
     * @param map
     * @return
     */
    Single<Response<List<String>>> getDurationLocal(Map<String, String> map);


    /**
     * It used to modify location duration
     * Not used currently
     * @param map
     * @return
     */
    Single<Response> setModifyLocationDuration(Map<String, String> map);

    /**
     * It used to accept location duration
     * Not used currently
     * @param map
     * @return
     */
    Single<Response> acceptLocationDuration(Map<String, String> map);

    /**
     * It used to decline location duration
     * Not used currently
     * @param map
     * @return
     */
    Single<Response> declineLocationDuration(Map<String, String> map);

    /**
     * It is used to notify action
     * @param map
     * @return
     */
    Single<Response> notifyAction(Map<String, String> map);

    /**
     * IT is used to get experience list visitor side
     * @param map
     * @param isFirstTime
     * @return
     */
    Single<Response<List<ExperienceDetails>>> getExperience(Map<String, String> map, boolean isFirstTime);

    /**
     * It is used to get blog list
     * @param map
     * @param isFirstTime
     * @return
     */
    Single<Response<List<BlogDetails>>> getBlogList(Map<String, String> map, boolean isFirstTime);

    /**
     * It is used to blog likes
     * @param map
     * @return
     */
    Single<Response<BlogDetails>> bloglike(Map<String, String> map);

    /**
     * IT is used to get blog comment
     * @param map
     * @return
     */
    Single<Response<List<Review>>> blogComment(Map<String, String> map);

    /**
     * IT is used to add comment blog
     * @param map
     * @return
     */
    Single<Response<Review>> addComment(Map<String, String> map);

    /**
     * it is used to publish experience
     * @param map
     * @return
     */
    Single<Response> publishExperience(Map<String, String> map);

    /**
     * It is used to get filter data
     * @param map
     * @return
     */
    Single<Response<FilterGetData>> filterData(Map<String, String> map);

    /**
     * It is used to create organization
     * @param map
     * @return
     */
    Single<Response<OrganizationDetails>> createOrg(Map<String, String> map);

    /**
     * It is used to get organization details local and visitor both side
     * @param map
     * @return
     */
    Single<Response<OrganizationDetails>> orgDetails(Map<String, String> map);

    /**
     * It is used to edit oeganization
     * @param map
     * @return
     */
    Single<Response> editOrg(Map<String, String> map);

    /**
     * It is used to delete organization media
     * @param map
     * @return
     */
    Single<Response> deleteOrgMediea(Map<String, String> map);

    /**
     * It is used to get local list organization
     * @param map
     * @return
     */
    Single<Response<List<OrgLocal>>> orgLocalList(Map<String, String> map);

    /**
     * it is used to add local organization
     * @param map
     * @return
     */
    Single<Response> addToLocalOrg(Map<String, String> map);

    /**
     * It is used to set payment rule organization
     * @param map
     * @return
     */
    Single<Response<List<PaymentRuleList>>> getSetPaymentRules(Map<String, String> map);

    /**
     * It is used to get payment rule option organization
     * @param map
     * @return
     */
    Single<Response<List<PaymentRulesOption>>> getPaymentRulesOption(Map<String, String> map);

    /**
     * it i sued to get unassign local
     * @param map
     * @return
     */
    Single<Response<List<OrgLocal>>> getUnAssignLocal(Map<String, String> map);


    /**
     * It is used to set payment rules
     * @param map
     * @return
     */
    Single<Response> setPaymentRule(Map<String, String> map);

    /**
     * It is used to edit payment rules
     * @param map
     * @return
     */
    Single<Response> editPaymentRule(Map<String, String> map);

    /**
     * It is used to delete payment rules
     * @param map
     * @return
     */
    Single<Response> deletePaymentRule(Map<String, String> map);

    /**
     * It is used to accept organization
     * @param map
     * @return
     */
    Single<Response> acceptOrg(Map<String, String> map);

    /**
     * It is used to decline organixation local
     * @param map
     * @return
     */
    Single<Response> declineOrg(Map<String, String> map);

    /**
     * It is used to exit organization
     * @param map
     * @return
     */
    Single<Response> exitOrg(Map<String, String> map);

    /**
     * It is used get visitor activities
     * @param map
     * @return
     */
    Single<Response<List<Activities>>> getVisitorActivities(Map<String, String> map);

    Single<Response<BookingFeeAndComision>> getBookingFees(Map<String, String> map);

    /**
     * It is used to get booking fees commission
     * @param map
     * @return
     */
    Single<Response<BookingFeeAndComision>> getBookingFeesNew(Map<String, String> map);

    /**
     * It is used to get rate option
     * Visitor and locla both
     * @param map
     * @return
     */
    Single<Response<List<RatingOption>>> getRatOption(Map<String, String> map);

    /**
     * It is used to set rate visitor
     * @param map
     * @return
     */
    Single<Response> setRateVisitor(Map<String, String> map);

    /**
     * It is used to set rate local
     * @param map
     * @return
     */
    Single<Response> setRateLocal(Map<String, String> map);

    /**
     * It is used to get profile visitor
     * @param map
     * @return
     */
    Single<Response<User>> getProfileVisitor(Map<String, String> map);

    /**
     * It is used to get visitor rate
     * @param map
     * @return
     */
    Single<Response<ReviewData>> getRateVisitor(Map<String, String> map);

    /**
     * It is used to get local rate
     * @param map
     * @return
     */
    Single<Response<ReviewData>> getRateLocal(Map<String, String> map);

    /**
     * It is used to get experience rate
     * @param map
     * @return
     */
    Single<Response<ReviewData>> getRateExp(Map<String, String> map);

    /**
     * It is used to get local earning
     * @param map
     * @return
     */
    Single<Response<EarningData>> getEarningLocal(Map<String, String> map);

    /**
     * It is used to get not completed step
     * @param map
     * @return
     */
    Single<Response> getdontWantComplete(Map<String, String> map);

    /**
     * It is user get organization status
     * @param map
     * @return
     */
    Single<Response<OrganizationStaus>> getOrgStatus(Map<String, String> map);

    /**
     * It is user to delete local organization
     * @param map
     * @return
     */
    Single<Response> getdeleteLocalOrg(Map<String, String> map);

    /**
     * It is used to get organization dashboard
     * @param map
     * @return
     */
    Single<Response<OrganizationDashboard>> getOrgDashboard(Map<String, String> map);

    /**
     * It is used to delete organization
     * @param map
     * @return
     */
    Single<Response> deleteOrgnization(Map<String, String> map);

    /**
     * It is used to check local affiliated
     * @param map
     * @return
     */
    Single<Response<CheckPaymentRules>> getIsCheckLocalAffilted(Map<String, String> map);

    /**
     * It is used to get exp. list
     * @param map
     * @return
     */
    Single<Response<List<ExpereinceNew>>> getExpListLocal(Map<String, String> map);

    /**
     * It is used to set bank details
     * @param map
     * @return
     */
    Single<Response<DashboardDetails>> setBankDetails(Map<String, String> map);

    /**
     * It is used to set experience add details
     * @param map
     * @return
     */
    Single<Response<ExperienceResponse>> setExpAddDetails(Map<String, String> map);

    /**
     * It is used to get experience add details
     * @param map
     * @return
     */
    Single<Response<ExperienceResponse>> getExpAddDetails(Map<String, String> map);

    /**
     * It is used to set experience activity
     * @param map
     * @return
     */
    Single<Response<ExperienceResponse>> setExpActivity(Map<String, String> map);

    Single<Response<ExperienceResponse>> setExpSport(Map<String, String> map);

    /**
     * It is used to set expedience meeting spot
     * @param map
     * @return
     */
    Single<Response<ExperienceResponse>> setExpMeetingSpot(Map<String, String> map);

    /**
     * It is used to set cancellation policy
     * @param map
     * @return
     */
    Single<Response<ExperienceResponse>> setExpCancelationPolicy(@Body() Map<String, String> map);

    /**
     * It is used to get expedience details
     * @param map
     * @return
     */
    Single<Response<OtheDetailsResponse>> getExpOtherDetails(@Body() Map<String, String> map);

    /**
     * It is used to set experience details
     * @param map
     * @return
     */
    Single<Response<ExperienceResponse>> setExpOtherDetails(@Body() Map<String, String> map);

    /**
     * It is used to set experience other fields details
     * @param map
     * @return
     */
    Single<Response<OtherDetailsAnotherFields>> setExpOtherDetailsFields(@Body() Map<String, String> map);

    /**
     * It is used to get other details fields experience
     * @param map
     * @return
     */
    Single<Response<List<OtherDetailsAnotherFields>>> getExpOtherDetailsFields(@Body() Map<String, String> map);

    /**
     * It is used to delete other fields experience
     * @param map
     * @return
     */
    Single<Response> deleteExpOtherDetailsFields(Map<String, String> map);

    /**
     * It is used to get experience disable
     * @param map
     * @return
     */
    Single<Response<DisableExperience>> getExpDisable(Map<String, String> map);

    /**
     * It is used to disable experience
     * @param map
     * @return
     */
    Single<Response> setExpDisable(Map<String, String> map);

    /**
     * It is used to get experience schedule
     * @param map
     * @return
     */
    Single<Response<ScheduleAndPriceResponse>> getExpAvability(Map<String, String> map);

    /**
     * It is used to set schedule experience
     * @param map
     * @return
     */
    Single<Response<ScheduleAndPriceResponse>> setExpAvability(Map<String, String> map);

    /**
     * It is used to edit schedule
     * @param map
     * @return
     */
    Single<Response<ScheduleAndPriceResponse>> editExpAvability(Map<String, String> map);

    /**
     * It is used to delete experience schedule
     * @param map
     * @return
     */
    Single<Response<ScheduleAndPriceResponse>> deleteExpAvability(Map<String, String> map);

    /**
     * It is used to set maximum participants
     * @param map
     * @return
     */
    Single<Response> setExpMaxParticipants(Map<String, String> map);

    /**
     * It is used to delete experience
     * @param map
     * @return
     */
    Single<Response> deleteExp(Map<String, String> map);

    /**
     * It is used to get edit experience list local side
     * @param map
     * @return
     */
    Single<Response<ExpereinceNew>> getExpEdit(Map<String, String> map);

    /**
     * It is used to get experience details
     * It is used both side local and visitor
     * @param map
     * @return
     */
    Single<Response<ExpDetails>> getExpDetails(Map<String, String> map);

    /**
     * It is used get data schedule
     * @param map
     * @return
     */
    Single<Response<List<SchedulePrice>>> getSelectDateSchedule(Map<String, String> map);

    /**
     * It is used to get filler open page data
     * Get local and expedience list
     * @param map
     * @return
     */
    Single<Response<HomeLocalAndExpResponse>> getFilterOnePage(Map<String, String> map);

    /**
     * It is used to get experience filter list
     * @param map
     * @return
     */
    Single<Response<List<ExpereinceNew>>> getFilterExp(Map<String, String> map);

    /**
     * It is used to get local filter
     * @param map
     * @return
     */
    Single<Response<List<LocalNew>>> getFilterLocal(Map<String, String> map);

    /**
     * It is used to set on boarding data
     * @param map
     * @return
     */
    Single<Response> setOnBordingData(Map<String, String> map);

    /**
     * It is used to set speak language
     * @param map
     * @return
     */
    Single<Response> setSPeakLanguage(Map<String, String> map);

    /**
     * It is used to check any notification is not read (Used local and visitor)
     * @param map
     * @return
     */
    Single<Response<NotificationReadCheck>> getNotificationReadCheck(Map<String, String> map);

    Single<Response> setNotificationRead(Map<String, String> map);

    /**
     * It is used to set experience equipment
     * @param map
     * @return
     */
    Single<Response> setExpEquipment(Map<String, String> map);

    /**
     * It is used to get equipment list order details
     * @param map
     * @return
     */
    Single<Response<CheckEquipmentResponse>> getEquipmentOrder(Map<String, String> map);

    /**
     * It is used ro set equipment
     * @param map
     * @return
     */
    Single<Response> setEquipmentOrder(Map<String, String> map);

    /**
     * It is user add equipment
     * @param map
     * @return
     */
    Single<Response<CheckEquipment>> addEquipmentOrder(Map<String, String> map);

    /**
     * It is used get card list
     * @param map
     * @return
     */
    Single<Response<List<Card>>> getCreditCardList(Map<String, String> map);

    /**
     * It is used to delete card
     * @param map
     * @return
     */
    Single<Response> deleteCreditCardList(Map<String, String> map);

    /**
     * It is used to add card
     * @param map
     * @return
     */
    Single<Response<List<Card>>> addCreditCardList(Map<String, String> map);

    /**
     * It is used to get country support bank
     * @param map
     * @return
     */
    Single<Response<List<CountryList>>> getCountryList(Map<String, String> map);

    /**
     * It is used to delete bank
     * @param map
     * @return
     */
    Single<Response> changeDefaultBank(Map<String, String> map);

    /**
     * It is used to delete bank
     * @param map
     * @return
     */
    Single<Response> deleteBank(Map<String, String> map);

    /**
     * It is used to get bank list local and organization both
     * @param map
     * @return
     */
    Single<Response<List<Bank>>> getBankList(Map<String, String> map);

    /**
     * It is used to check Org bank
     * @param map
     * @return
     */
    Single<Response<CheckBank>> checkOrgBank(Map<String, String> map);

    /**
     * It is used to ge cancel amount visitor cancel details screen
     * @param map
     * @return
     */
    Single<Response<CancellationData>> getCancellationAmount(Map<String, String> map);

    /**
     * It is used to cancel order visitor
     * @param map
     * @return
     */
    Single<Response> visitorCancel(Map<String, String> map);

    /**
     * It is used to cancel order local
     * @param map
     * @return
     */
    Single<Response> localCancel(Map<String, String> map);

    /**
     * It is used to get local month wise earning
     * @param map
     * @return
     */
    Single<Response<List<Order>>> getLocalMonthEarning(Map<String, String> map);

    /**
     * It is used get fav experience list
     * @param map
     * @return
     */
    Single<Response<List<ExpereinceNew>>> getFavExp(Map<String, String> map);

    /**
     * It is used to set fav experience
     * @param map
     * @return
     */
    Single<Response<ExpFavData>> setFavExp(Map<String, String> map);

    /**
     * It is used to delete equipment local order details screen
     * @param map
     * @return
     */
    Single<Response> localOrderEquipmentDelete(Map<String, String> map);


    //Temp data get api

    /**
     * All method are not used live
     * All method are proto type used
     * @return
     */

    /**
     * Get local list
     * @return
     */
    Single<Response<List<Home>>> getLocalList();

    /**
     * Get dashboard details
     * @return
     */
    Single<Response<DashboardDetails>> getDashboardDetails();

    /**
     * Get organization details
     * @return
     */
    Single<Response<OrganizationDetails>> getOrganizationDetails();

    /**
     * Get activates list
     * @return
     */
    Single<Response<SelectActivites>> getActivites();

    Single<Response<List<Time>>> getTimes();

    /**
     * GEt duration
     * @return
     */
    Single<Response<List<Duration>>> getDuration();

    Single<Response<List<Card>>> getPayment();

    Single<Response<List<Order>>> getOrderPending();

    Single<Response<List<Order>>> getOrderComplate();

    /**
     * It is used to get invites
     * @return
     */
    Single<Response<List<Invite>>> getInvites();

    /**
     * It is used to get sport
     * @return
     */
    Single<Response<List<Sport>>> getSports();

    /***
     * It is used to get recent chat
     * @return
     */
    Single<Response<List<RecentChat>>> getRecntChat();

    /**
     * It is used to get fav experience
     * @return
     */
    Single<Response<List<Favorites>>> getFav();

    /**
     * It is used to get fav exp not used
     * @return
     */
    Single<Response<List<HelpAndFaq>>> getHelpAndFaq();

    /**
     * It is used to upload file
     * @param partMap
     * @param partFile
     * @return
     */
    Single<Response<List<MultiFile>>> uploadFile(Map<String, RequestBody> partMap, List<MultipartBody.Part> partFile);

    /**
     * It is used to get blog details
     * @param map
     * @return
     */
    Single<Response<BlogDetails>> blogDetails(Map<String, String> map);

    /**
     * It is used to delete blog media
     * @param map
     * @return
     */
    Single<Response> deleteBlogMediea(Map<String, String> map);

    /**
     * It is used to upload expedience
     * @param map
     * @return
     */
    Single<Response> editExperience(Map<String, String> map);

    /**
     * It is used to set special arability
     * @param map
     * @return
     */
    Single<Response> setSpecialAvavility(Map<String, String> map);

    /**
     * It is used to get special avavbility
     * @param map
     * @return
     */
    Single<Response<SetSpecialAvability>> getSpecialAvavility(Map<String, String> map);

    /**
     * It is used to set service
     * @param map
     * @return
     */
    Single<Response<List<Service>>> sportServiceType(Map<String, String> map);
}
