package com.kooloco.data.service;

import com.kooloco.data.URLFactory;
import com.kooloco.data.entity.ResponseEntity;
import com.kooloco.data.entity.SportActivityEntity;
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
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.DisableDateListResposne;
import com.kooloco.model.DisableExperience;
import com.kooloco.model.EarningData;
import com.kooloco.model.Equipment;
import com.kooloco.model.EquipmentResponse;
import com.kooloco.model.ExpDetails;
import com.kooloco.model.ExpFavData;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.ExperienceResponse;
import com.kooloco.model.FilterGetData;
import com.kooloco.model.HomeLocalAndExpResponse;
import com.kooloco.model.HomeNewResponse;
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
import com.kooloco.model.Response;
import com.kooloco.model.Review;
import com.kooloco.model.ReviewData;
import com.kooloco.model.SchduleDashboard;
import com.kooloco.model.ScheduleAndPriceResponse;
import com.kooloco.model.SchedulePrice;
import com.kooloco.model.SelectActivites;
import com.kooloco.model.SetSpecialAvability;
import com.kooloco.model.SportPriceRules;
import com.kooloco.model.Tag;
import com.kooloco.model.Time;
import com.kooloco.model.UploadDocument;
import com.kooloco.model.UploadImage;
import com.kooloco.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


/**
 * Created by hlink21 on 10/5/17.
 */

public interface Service {

    @Multipart
    @POST(URLFactory.UPLOADIMAGE)
    Single<Response<UploadImage>> uploadImage(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part profileImage);

    @Multipart
    @POST(URLFactory.UPLOADIMAGEMULTIPALE)
    Single<Response<List<MultiFile>>> uploadFile(@PartMap() Map<String, RequestBody> partMap, @Part List<MultipartBody.Part> partFile);

    @Multipart
    @POST(URLFactory.UPLOADDOCUMENT)
    Single<Response<UploadDocument>> uploadDocument(@Part MultipartBody.Part profileImage);


    @POST(URLFactory.SIGNUP)
    Single<Response<User>> signUp(@Body() Map<String, String> map);


    @POST(URLFactory.LOGIN)
    Single<Response<User>> login(@Body() Map<String, String> map);

    @POST(URLFactory.CHECKSOCIALID)
    Single<Response<User>> checkSocial(@Body() Map<String, String> map);


    @POST(URLFactory.LOCALSTEPONE)
    Single<Response<User>> localStepOne(@Body() Map<String, String> map);

    @POST(URLFactory.ADDIMAGESTEPTWO)
    Single<Response<User>> addImageStepTwo(@Body() Map<String, String> partMap);

    @POST(URLFactory.SPORTTYPE)
    Single<ResponseEntity<Collection<SportActivityEntity>>> sportType(@Body() Map<String, String> map);

    @POST(URLFactory.UPLOADCERTIFICATES)
    Single<Response<User>> UploadCertificationStepThree(@Body() Map<String, String> partMap);

    @POST(URLFactory.UPLOADACHIVEMENTS)
    Single<Response<User>> uploadAchivementsStepFour(@Body() Map<String, String> partMap);


    @POST(URLFactory.GETTAG)
    Single<Response<List<Tag>>> tagList(@Body() Map<String, String> map);


    @POST(URLFactory.LOCALLANGUAGE)
    Single<Response<User>> LocalLanguage(@Body() Map<String, String> map);


    @POST(URLFactory.GETLANGUAGE)
    Single<Response<LanguageResponse>> language(@Body() Map<String, String> map);


    @POST(URLFactory.GETEXPERIENCE)
    Single<Response<List<Activities>>> experience(@Body() Map<String, String> map);


    @POST(URLFactory.SETEXPERIENCESIX)
    Single<Response<User>> setExperienceStepSix(@Body() Map<String, String> map);


    @POST(URLFactory.SETSPORTPRICERULES)
    Single<Response<User>> setSportPriceRules(@Body() Map<String, String> map);


    @POST(URLFactory.GETEQUIPMENT)
    Single<Response<EquipmentResponse>> equipment(@Body() Map<String, String> map);


    @POST(URLFactory.SETSPORTEQUPMENTS)
    Single<Response<User>> setSportEquipments(@Body() Map<String, String> map);


    @POST(URLFactory.SETAVAIBILITY)
    Single<Response<User>> setSchedule(@Body() Map<String, String> map);


    @POST(URLFactory.GETCANCELLATION)
    Single<Response<List<CancellationPolicy>>> cancellation(@Body() Map<String, String> map);


    @POST(URLFactory.SETCANCELLATION)
    Single<Response<User>> setcancellation(@Body() Map<String, String> map);


    @POST(URLFactory.SETSPORTLOCAL)
    Single<Response<User>> setSportLocal(@Body() Map<String, String> map);


    @POST(URLFactory.GETSELECTSPORT)
    Single<Response<List<SportPriceRules>>> getSelectSport(@Body() Map<String, String> map);


    @POST(URLFactory.ADDEQUIPMENT)
    Single<Response<Equipment>> addEquipment(@Body() Map<String, String> map);


    @POST(URLFactory.SKIPSIGNUPSTEP)
    Single<Response<User>> skipSignupStep(@Body() Map<String, String> map);


    @POST(URLFactory.SETLOCATION)
    Single<Response<User>> setLocation(@Body() Map<String, String> map);


    @POST(URLFactory.BECOMELOCAL)
    Single<Response<User>> setBecomeLocal(@Body() Map<String, String> map);


    @POST(URLFactory.BECOMEVISITOR)
    Single<Response<User>> setVisitor(@Body() Map<String, String> map);


    @POST(URLFactory.VISITORHOME)
    Single<Response<List<DashboardDetails>>> getVisitorHome(@Body() Map<String, String> map);

    @POST(URLFactory.VISITORHOMENEW)
    Single<Response<HomeNewResponse>> getVisitorHomeNew(@Body() Map<String, String> map);

    @POST(URLFactory.VISITORDISABLEDATELIST)
    Single<Response<DisableDateListResposne>> getDisableDateList(@Body() Map<String, String> map);

    @POST(URLFactory.VISITORSELECTDATEAVABILITY)
    Single<Response<List<SchedulePrice>>> getSelectDateSchedule(@Body() Map<String, String> map);


    @POST(URLFactory.VISITORHOMEGETONEPAGE)
    Single<Response<HomeLocalAndExpResponse>> getVisitorOnePage(@Body() Map<String, String> map);

    @POST(URLFactory.VISITORHOMELOCAL)
    Single<Response<List<LocalNew>>> getVisitorHomeLocal(@Body() Map<String, String> map);

    @POST(URLFactory.VISITORHOMEEXP)
    Single<Response<List<ExpereinceNew>>> getVisitorHomeExp(@Body() Map<String, String> map);


    @POST(URLFactory.VISITORHOMEFILTER)
    Single<Response<List<DashboardDetails>>> getVisitorHomeFilter(@Body() Map<String, String> map);

    @POST(URLFactory.VISITORHOMLOCALDETAILS)
    Single<Response<DashboardDetails>> getVisitorHomeLocalDetails(@Body() Map<String, String> map);

    @POST(URLFactory.LOGOUT)
    Single<Response> logout(@Body() Map<String, String> map);


    @POST(URLFactory.CHANGEPASSWORD)
    Single<Response> changePassword(@Body() Map<String, String> map);

    @POST(URLFactory.FORGOTPASSWORD)
    Single<Response> forgotPassword(@Body() Map<String, String> map);


    @POST(URLFactory.DELETEEQUIPMENT)
    Single<Response> deleteEquipment(@Body() Map<String, String> map);


    @POST(URLFactory.GETPROFILE)
    Single<Response<DashboardDetails>> getLocalProfile(@Body() Map<String, String> map);


    @POST(URLFactory.GETSPORT)
    Single<Response<List<Certifications>>> getSport(@Body() Map<String, String> map);


    @POST(URLFactory.GETCERTIFICATES)
    Single<Response<List<Certifications>>> getCertification(@Body() Map<String, String> map);


    @POST(URLFactory.GETACHIVEMENTS)
    Single<Response<List<Certifications>>> getAchivements(@Body() Map<String, String> map);


    @POST(URLFactory.DELETESPORT)
    Single<Response> deleteSportImage(@Body() Map<String, String> map);


    @POST(URLFactory.DELETECERTIFICATE)
    Single<Response> deleteCertificate(@Body() Map<String, String> map);


    @POST(URLFactory.DELETEACHIVEMENT)
    Single<Response> deleteAchivement(@Body() Map<String, String> map);


    @POST(URLFactory.UPDATEPROFILE)
    Single<Response<User>> updateProfile(@Body() Map<String, String> partMap);


    @POST(URLFactory.ACTIVITYANDSPORT)
    Single<Response<SelectActivites>> activityAndSport(@Body() Map<String, String> map);


    @POST(URLFactory.GETSTARTTIME)
    Single<Response<List<Time>>> getStartTime(@Body() Map<String, String> map);


    @POST(URLFactory.GETENDTIME)
    Single<Response<List<Time>>> getEndTime(@Body() Map<String, String> map);


    @POST(URLFactory.BOOKAPPOINTMENT)
    Single<Response<BookingData>> boookAppointment(@Body() Map<String, String> map);

/*

    @POST(URLFactory.VISITORPENDINGORDER)
    Single<Response<List<Order>>> getPendingOrderVisitor(@Body() Map<String, String> map);


    @POST(URLFactory.LOCALPENDINGORDER)
    Single<Response<List<Order>>> getPendingOrderLocal(@Body() Map<String, String> map);

*/


    @POST(URLFactory.VISITORPENDINGORDER)
    Single<Response<List<Order>>> getPendingOrderVisitor(@Body() Map<String, String> map);

    @POST(URLFactory.VISITORCOMPLATEORDER)
    Single<Response<List<Order>>> getComplateOrderVisitor(@Body() Map<String, String> map);


    @POST(URLFactory.LOCALPENDINGORDER)
    Single<Response<List<Order>>> getPendingOrderLocal(@Body() Map<String, String> map);

    @POST(URLFactory.LOCALACCEPTORDER)
    Single<Response<List<Order>>> getAcceptOrderLocal(@Body() Map<String, String> map);

    @POST(URLFactory.LOCALCOMPLATEORDER)
    Single<Response<List<Order>>> getComplateOrderLocal(@Body() Map<String, String> map);


    @POST(URLFactory.GETSCHDULE)
    Single<Response<List<SchduleDashboard>>> getSchdule(@Body() Map<String, String> map);

    @POST(URLFactory.NOTIFICATIONACTION)
    Single<Response<User>> notificationAction(@Body() Map<String, String> map);

    @POST(URLFactory.NOTIFICATIONEMAILACTION)
    Single<Response<User>> notificationEmailAction(@Body() Map<String, String> map);

    @POST(URLFactory.NOTIFICATIONLIST)
    Single<Response<List<Notification>>> getNotifications(@Body() Map<String, String> map);


    //Order Accept reject and complete
    @POST(URLFactory.ACCEPTORDER)
    Single<Response> acceptOrder(@Body() Map<String, String> map);

    @POST(URLFactory.REJECTORDER)
    Single<Response> rejectOrder(@Body() Map<String, String> map);

    @POST(URLFactory.COMPLATEORDER)
    Single<Response> complateOrder(@Body() Map<String, String> map);

    @POST(URLFactory.CANCELORDER)
    Single<Response> cancelOrder(@Body() Map<String, String> map);

    @POST(URLFactory.SENDPAYMENTREQUESTORDER)
    Single<Response> sendPaymentREquestOrder(@Body() Map<String, String> map);

    @POST(URLFactory.ORDERDETAILSLOCAL)
    Single<Response<OrderDetails>> orderDetailsLocal(@Body() Map<String, String> map);

    @POST(URLFactory.ORDERDETAILSVISITOR)
    Single<Response<OrderDetails>> orderDetailsVisitor(@Body() Map<String, String> map);

    @POST(URLFactory.ORDERDETAILSLOCAL)
    Single<Response<Order>> orderDataLocal(@Body() Map<String, String> map);

    @POST(URLFactory.ORDERDETAILSVISITOR)
    Single<Response<Order>> orderDataVisitor(@Body() Map<String, String> map);


    @POST(URLFactory.SENDOBJECTION)
    Single<Response> sendObjection(@Body() Map<String, String> map);

    @POST(URLFactory.PAYNOW)
    Single<Response> payNow(@Body() Map<String, String> map);

    @POST(URLFactory.OBJECTIONDETAILS)
    Single<Response<ObjectionDetails>> objectionDetails(@Body() Map<String, String> map);

    @POST(URLFactory.ACCEPTOBJECTION)
    Single<Response> acceptObjection(@Body() Map<String, String> map);

    @POST(URLFactory.MODIFYOBJECTION)
    Single<Response> modifyObjection(@Body() Map<String, String> map);


    @POST(URLFactory.OBJECTIONDETAILSVISITOR)
    Single<Response<ObjectionDetails>> objectionDetailsVisitor(@Body() Map<String, String> map);

    @POST(URLFactory.ACCEPTOBJECTIONVISITOR)
    Single<Response> acceptObjectionVisitor(@Body() Map<String, String> map);

    @POST(URLFactory.ADMINOBJECTION)
    Single<Response> adminObjectionRequest(@Body() Map<String, String> map);

    @POST(URLFactory.GETDURATION)
    Single<Response<List<String>>> getDurationLocal(@Body() Map<String, String> map);

    @POST(URLFactory.SETMODIFYLD)
    Single<Response> setModifyLocationDuration(@Body() Map<String, String> map);

    @POST(URLFactory.ACCEPTLOCATION)
    Single<Response> acceptLocation(@Body() Map<String, String> map);

    @POST(URLFactory.ACCEPTDURATION)
    Single<Response> declineDuration(@Body() Map<String, String> map);

    @POST(URLFactory.NOTIFYACTION)
    Single<Response> notifyAction(@Body() Map<String, String> map);

    @POST(URLFactory.EXPERINCELIST)
    Single<Response<List<ExperienceDetails>>> getExperience(@Body() Map<String, String> map);

    @POST(URLFactory.BLOGLIST)
    Single<Response<List<BlogDetails>>> getBlogList(@Body() Map<String, String> map);

    @POST(URLFactory.BLOGLIKEDISLIKE)
    Single<Response<BlogDetails>> bloglike(@Body() Map<String, String> map);

    @POST(URLFactory.BLOGGETCOMEEENTS)
    Single<Response<List<Review>>> blogComment(@Body() Map<String, String> map);

    @POST(URLFactory.ADDCOMMENTS)
    Single<Response<Review>> addComment(@Body() Map<String, String> map);

    @POST(URLFactory.QUATION)
    Single<Response<List<Quation>>> getQuation(@Body() Map<String, String> map);

    @POST(URLFactory.PUBLISHEXPERIENCE)
    Single<Response> publishExperience(@Body() Map<String, String> map);

    @POST(URLFactory.BLOGLDETAILS)
    Single<Response<BlogDetails>> blogDetails(@Body() Map<String, String> map);

    @POST(URLFactory.DELETEBLOGMEDIA)
    Single<Response> deleteBlogMediea(@Body() Map<String, String> map);

    @POST(URLFactory.EDITEXPERENCE)
    Single<Response> editExperience(@Body() Map<String, String> map);

    @POST(URLFactory.SETSPECIALAVABILITY)
    Single<Response> setSpecialAvavility(@Body() Map<String, String> map);

    @POST(URLFactory.GETSPECIALAVABILITY)
    Single<Response<SetSpecialAvability>> getSpecialAvavility(@Body() Map<String, String> map);

    @POST(URLFactory.GETFILTERDATA)
    Single<Response<FilterGetData>> filterData(@Body() Map<String, String> map);

    @POST(URLFactory.CREATORG)
    Single<Response<OrganizationDetails>> createOrg(@Body() Map<String, String> map);

    @POST(URLFactory.ORGDET)
    Single<Response<OrganizationDetails>> orgDetails(@Body() Map<String, String> map);

    @POST(URLFactory.EDITORG)
    Single<Response> editOrg(@Body() Map<String, String> map);

    @POST(URLFactory.DELETEORGMEDIA)
    Single<Response> deleteOrgMediea(@Body() Map<String, String> map);

    @POST(URLFactory.ORGLIOCALLIST)
    Single<Response<List<OrgLocal>>> orgLocalList(@Body() Map<String, String> map);

    @POST(URLFactory.ADDLOCALORG)
    Single<Response> addToLocalOrg(@Body() Map<String, String> map);

    @POST(URLFactory.GETSETPAYMENTRULES)
    Single<Response<List<PaymentRuleList>>> getSetPaymentRules(@Body() Map<String, String> map);

    @POST(URLFactory.GETPAYMENTRULEOPTION)
    Single<Response<List<PaymentRulesOption>>> getPaymentRulesOption(@Body() Map<String, String> map);

    @POST(URLFactory.GETUNASSIGNLOCAL)
    Single<Response<List<OrgLocal>>> getUnAssignLocal(@Body() Map<String, String> map);

    @POST(URLFactory.SETPAYMENTRULES)
    Single<Response> setPaymentRule(@Body() Map<String, String> map);

    @POST(URLFactory.EDITPAYMENTRULES)
    Single<Response> editPaymentRule(@Body() Map<String, String> map);

    @POST(URLFactory.DELETEPAYMENTRULES)
    Single<Response> deletePaymentRule(@Body() Map<String, String> map);

    @POST(URLFactory.ACCEPTORG)
    Single<Response> acceptOrg(@Body() Map<String, String> map);

    @POST(URLFactory.DECLINEORG)
    Single<Response> declineOrg(@Body() Map<String, String> map);

    @POST(URLFactory.EXITORG)
    Single<Response> exitOrg(@Body() Map<String, String> map);

    @POST(URLFactory.VISITORGETACTIVITES)
    Single<Response<List<Activities>>> getVisitorActivities(@Body() Map<String, String> map);

    @POST(URLFactory.GETBOOKINGFEES)
    Single<Response<BookingFeeAndComision>> getBookingFees(@Body() Map<String, String> map);

    @POST(URLFactory.GETBOOKINGFEESNEW)
    Single<Response<BookingFeeAndComision>> getBookingFeesNew(@Body() Map<String, String> map);

    @POST(URLFactory.GETRATOPTION)
    Single<Response<List<RatingOption>>> getRatOption(@Body() Map<String, String> map);

    @POST(URLFactory.SETRATEVISITOR)
    Single<Response> setRateVisitor(@Body() Map<String, String> map);

    @POST(URLFactory.SETRATELOCAL)
    Single<Response> setRateLocal(@Body() Map<String, String> map);

    @POST(URLFactory.GETVISIPROFILE)
    Single<Response<User>> getProfileVisitor(@Body() Map<String, String> map);

    @POST(URLFactory.GETRATEVISITOR)
    Single<Response<ReviewData>> getRateVisitor(@Body() Map<String, String> map);

    @POST(URLFactory.GETRATELOCAL)
    Single<Response<ReviewData>> getRateLocal(@Body() Map<String, String> map);

    @POST(URLFactory.GETRATEEXP)
    Single<Response<ReviewData>> getRateExp(@Body() Map<String, String> map);

    @POST(URLFactory.GETLOCALEARNING)
    Single<Response<EarningData>> getEarningLocal(@Body() Map<String, String> map);

    @POST(URLFactory.GETDONTWANTCOMPLETE)
    Single<Response> getdontWantComplete(@Body() Map<String, String> map);

    @POST(URLFactory.GETORGSTATUS)
    Single<Response<OrganizationStaus>> getOrgStatus(@Body() Map<String, String> map);

    @POST(URLFactory.DELETELOCALORG)
    Single<Response> getdeleteLocalOrg(@Body() Map<String, String> map);

    @POST(URLFactory.GETORGDASHBOARD)
    Single<Response<OrganizationDashboard>> getOrgDashboard(@Body() Map<String, String> map);

    @POST(URLFactory.DELETEORG)
    Single<Response> deleteOrgnization(@Body() Map<String, String> map);

    @POST(URLFactory.ISCHECKLOCALAFFILATED)
    Single<Response<CheckPaymentRules>> getIsCheckLocalAffilted(@Body() Map<String, String> map);

    @POST(URLFactory.SPORTTYPE)
    Single<Response<List<com.kooloco.model.Service>>> serviceType(@Body() Map<String, String> map);

    @POST(URLFactory.SETBANKDETAILS)
    Single<Response<DashboardDetails>> setBankDetails(@Body() Map<String, String> map);

    @POST(URLFactory.SETEXPADDDETAILS)
    Single<Response<ExperienceResponse>> setExpAddDetails(@Body() Map<String, String> map);

    @POST(URLFactory.GETEXPADDDETAILS)
    Single<Response<ExperienceResponse>> getExpAddDetails(@Body() Map<String, String> map);

    @POST(URLFactory.SETEXPACTIVITY)
    Single<Response<ExperienceResponse>> setExpActivity(@Body() Map<String, String> map);

    @POST(URLFactory.SETEXPSPORT)
    Single<Response<ExperienceResponse>> setExpSport(@Body() Map<String, String> map);

    @POST(URLFactory.SETEXPMEETINGSPOT)
    Single<Response<ExperienceResponse>> setExpMeetingSpot(@Body() Map<String, String> map);

    @POST(URLFactory.SETEXPCANCELLATIONPOLICY)
    Single<Response<ExperienceResponse>> setExpCancelationPolicy(@Body() Map<String, String> map);

    @POST(URLFactory.GETEXPOTHERDETAILS)
    Single<Response<OtheDetailsResponse>> getExpOtherDetails(@Body() Map<String, String> map);

    @POST(URLFactory.SETEXPOTHERDETAILS)
    Single<Response<ExperienceResponse>> setExpOtherDetails(@Body() Map<String, String> map);

    @POST(URLFactory.GETEXPOTHERDETAILSFEILDS)
    Single<Response<List<OtherDetailsAnotherFields>>> getExpOtherDetailsFields(@Body() Map<String, String> map);

    @POST(URLFactory.SETEXPOTHERDETAILSFEILDS)
    Single<Response<OtherDetailsAnotherFields>> setExpOtherDetailsFields(@Body() Map<String, String> map);

    @POST(URLFactory.DELETEEXPOTHERDETAILSFEILDS)
    Single<Response> deleteExpOtherDetailsFields(@Body() Map<String, String> map);

    @POST(URLFactory.GETEXPLISTLOCAL)
    Single<Response<List<ExpereinceNew>>> getExpListLocal(@Body() Map<String, String> map);

    @POST(URLFactory.SETEXPDISABELE)
    Single<Response> setExpDisable(@Body() Map<String, String> map);

    @POST(URLFactory.GETEXPDISABELE)
    Single<Response<DisableExperience>> getExpDisable(@Body() Map<String, String> map);

    @POST(URLFactory.GETEXPAVABILITY)
    Single<Response<ScheduleAndPriceResponse>> getExpAvability(@Body() Map<String, String> map);

    @POST(URLFactory.SETEXPAVABILITY)
    Single<Response<ScheduleAndPriceResponse>> setExpAvability(@Body() Map<String, String> map);

    @POST(URLFactory.EDITEXPAVABILITY)
    Single<Response<ScheduleAndPriceResponse>> editExpAvability(@Body() Map<String, String> map);

    @POST(URLFactory.DELETEEXPAVABILITY)
    Single<Response<ScheduleAndPriceResponse>> deleteExpAvability(@Body() Map<String, String> map);

    @POST(URLFactory.SETEXPMAXPARTICIPNATS)
    Single<Response> setExpMaxParticipants(@Body() Map<String, String> map);

    @POST(URLFactory.EXPDELETE)
    Single<Response> deleteExp(@Body() Map<String, String> map);

    @POST(URLFactory.GETEXPEDIT)
    Single<Response<ExpereinceNew>> getExpEdit(@Body() Map<String, String> map);

    @POST(URLFactory.GETEXPEDITILS)
    Single<Response<ExpDetails>> getExpDetails(@Body() Map<String, String> map);


    @POST(URLFactory.VISITORFILTERGETONEPAGE)
    Single<Response<HomeLocalAndExpResponse>> getFilterOnePage(@Body() Map<String, String> map);

    @POST(URLFactory.VISITORFILTERLOCAL)
    Single<Response<List<LocalNew>>> getFilterLocal(@Body() Map<String, String> map);

    @POST(URLFactory.VISITORFILTEREXP)
    Single<Response<List<ExpereinceNew>>> getFilterExp(@Body() Map<String, String> map);

    @POST(URLFactory.VISITONBOARDING)
    Single<Response> setOnBordingData(@Body() Map<String, String> map);

    @POST(URLFactory.SETSPEAKLANGUAGES)
    Single<Response> setSPeakLanguage(@Body() Map<String, String> map);


    @POST(URLFactory.GETNOTIFICATIONNOTREAD)
    Single<Response<NotificationReadCheck>> getNotificationReadCheck(@Body() Map<String, String> map);

    @POST(URLFactory.SETNOTIFICATIONNOTREAD)
    Single<Response> setNotificationRead(@Body() Map<String, String> map);


    @POST(URLFactory.SETEXPEQUIPMENT)
    Single<Response> setExpEquipment(@Body() Map<String, String> map);

    @POST(URLFactory.GETEXPEQUIPMENTORDER)
    Single<Response<CheckEquipmentResponse>> getEquipmentOrder(@Body() Map<String, String> map);

    @POST(URLFactory.SETEXPEQUIPMENTORDER)
    Single<Response> setEquipmentOrder(@Body() Map<String, String> map);

    @POST(URLFactory.ADDEXPEQUIPMENTORDER)
    Single<Response<CheckEquipment>> addEquipmentOrder(@Body() Map<String, String> map);

    //Payment Related api
    @POST(URLFactory.GETCREDITCARD)
    Single<Response<List<Card>>> getCreditCardList(@Body() Map<String, String> map);

    @POST(URLFactory.DELETECREDITCARD)
    Single<Response> deleteCreditCardList(@Body() Map<String, String> map);

    @POST(URLFactory.ADDCREDITCARD)
    Single<Response<List<Card>>> addCreditCardList(@Body() Map<String, String> map);

    @POST(URLFactory.GETCOUNTRYLIST)
    Single<Response<List<CountryList>>> getCountryList(@Body() Map<String, String> map);

    @POST(URLFactory.GETBANKLIST)
    Single<Response<List<Bank>>> getBankList(@Body() Map<String, String> map);

    @POST(URLFactory.DELETEBANK)
    Single<Response> deleteBank(@Body() Map<String, String> map);

    @POST(URLFactory.CHANGEDEFAULTEBANK)
    Single<Response> changeDefaultBank(@Body() Map<String, String> map);

    @POST(URLFactory.CHECKORGBANK)
    Single<Response<CheckBank>> checkOrgBank(@Body() Map<String, String> map);

    @POST(URLFactory.GETCANCELLATIONAMOUNT)
    Single<Response<CancellationData>> getCancellationAmount(@Body() Map<String, String> map);

    @POST(URLFactory.VISITORCANCEL)
    Single<Response> visitorCancel(@Body() Map<String, String> map);

    @POST(URLFactory.LOCALCANCEL)
    Single<Response> localCancel(@Body() Map<String, String> map);

    @POST(URLFactory.MONTHORDEREARNING)
    Single<Response<List<Order>>> getLocalMonthEarning(@Body() Map<String, String> map);

    @POST(URLFactory.GETFAVEXP)
    Single<Response<List<ExpereinceNew>>> getFavExp(@Body() Map<String, String> map);

    @POST(URLFactory.SETFAVEXP)
    Single<Response<ExpFavData>> setFavExp(@Body() Map<String, String> map);

    @POST(URLFactory.GETCURRENCY)
    Single<Response<List<Currency>>> getCurrency(@Body() Map<String, String> map);

    @POST(URLFactory.SETCURRENCY)
    Single<Response<User>> setCurrency(@Body() Map<String, String> map);

    @POST(URLFactory.LOCALORDERDELETEEQUIPMENT)
    Single<Response> localOrderEquipmentDelete(@Body() Map<String, String> map);

}
