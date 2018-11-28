package com.kooloco.data;

import okhttp3.HttpUrl;

/**
 * Created by hlink21 on 11/5/17.
 */

public class URLFactory {


    // server details
    private static boolean isLocal = false;

    private static final String SCHEME = isLocal ? "http" : "https";
    private static final String HOST =  isLocal ? "aventure-privee.com" : "aventure-privee.com";
   // private static final String HOST = isLocal ? "192.168.1.131" : "132.148.17.145"; //za sta khbra poha shuma kho ta mata postman yo exmpale ukhay
//    private static final String HOST = isLocal ? "52.47.196.211" : "52.47.196.211";
//    private static final String API_PATH = isLocal ? "project/kooloco/v1/" : "~hyperlinkserver/koolocoNEW/v1/";
    private static final String API_PATH = isLocal ? "v1/" : "v1/";
    private static final String EXTRA_URL = "http://aventure-privee.com/~hyperlinkserver/koolocoNEW/assets/upload/icon";
    private static final String EXTRA_URL_NEW = "http://aventure-privee.com/~hyperlinkserver/koolocoNEW/";

    public static final String IOS_APPSTORE_ID = "1434510291";

    public static final String FIREBASE_ID_DYNAMICLINK = "kooloco.page.link";


    public static HttpUrl provideHttpUrl() {
        return new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST)
                .addPathSegments(API_PATH)
                .build();
    }
    //crashed postmanopen ka
    // API Methods
    public static final String SIGNUP = "combine/signup";
    public static final String LOGIN = "combine/login";
    public static final String CHECKSOCIALID = "combine/checkSocialId";
    public static final String SPORTTYPE = "combine/getSportsType/";
    public static final String GETTAG = "combine/getTags";
    public static final String GETLANGUAGE = "combine/getLanguages";
    public static final String GETCURRENCY = "combine/getCurrency";
    public static final String SETCURRENCY = "combine/setCurrency";

    public static final String GETEXPERIENCE = "combine/getActivity";
    public static final String GETEQUIPMENT = "combine/getEquipment";
    public static final String GETCANCELLATION = "combine/getCancellationPolicy";
    public static final String LOGOUT = "combine/logout/";
    public static final String CHANGEPASSWORD = "combine/changePassword/";
    public static final String FORGOTPASSWORD = "combine/forgot_password/";

    public static final String UPLOADIMAGE = "combine/uploadImage/";
    public static final String NOTIFICATIONACTION = "combine/notificationOnOff/";
    public static final String NOTIFICATIONEMAILACTION = "combine/mailAlertOnOff/";
    public static final String NOTIFICATIONLIST = "combine/getNotificationList";
    public static final String QUATION = "combine/getQuestion";
    public static final String UPLOADIMAGEMULTIPALE = "combine/uploadMultipleMedia";
    public static final String BLOGLDETAILS = "visitor/getBlogDetail";
    public static final String DELETEBLOGMEDIA = "visitor/deleteBlogMedia";

    public static final String EDITEXPERENCE = "visitor/editBlog";
    public static final String NOTIFYACTION = "visitor/notifyForLocation";
    public static final String VISITORGETACTIVITES = "visitor/getBookingActivity";

    public static final String GETRATOPTION = "combine/getRatingOption";
    public static final String GETVISIPROFILE = "visitor/getProfile";
    public static final String VISITORDISABLEDATELIST = "visitor/getCalenderYearlyDisabledDate";

    //Visitor
    public static final String BECOMEVISITOR = "visitor/becomeVisitor";
    public static final String VISITORHOME = "visitor/getNearByLocal/";
    public static final String VISITORHOMENEW = "visitor/homeScreen";
    public static final String VISITORHOMLOCALDETAILS = "visitor/getLocalProfile";

    public static final String UPDATEPROFILE = "visitor/update_profile/";
    public static final String ACTIVITYANDSPORT = "visitor/getActivityAndExperience";
    public static final String GETSTARTTIME = "visitor/getStartTimeSlot";
    public static final String GETENDTIME = "visitor/getEndTimeSlot";
    public static final String VISITORCOMPLATEORDER = "visitor/getCompletedOrder";
    public static final String ORDERDETAILSVISITOR = "visitor/orderDetail";
    public static final String SENDOBJECTION = "visitor/sendObjection";
    public static final String PAYNOW = "visitor/payNow";
    public static final String ACCEPTOBJECTIONVISITOR = "visitor/acceptObjection";

    public static final String ADMINOBJECTION = "visitor/requestToAdmin";
    public static final String OBJECTIONDETAILSVISITOR = "visitor/getObjectionDetail";

    public static final String ACCEPTLOCATION = "visitor/acceptBookingModification";
    public static final String ACCEPTDURATION = "visitor/declineBookingModification";

    public static final String EXPERINCELIST = "visitor/getExperiences";

    public static final String BLOGLIST = "visitor/exploreKoolocoList";
    public static final String BLOGGETCOMEEENTS = "visitor/getComment";
    public static final String ADDCOMMENTS = "visitor/giveComment";
    public static final String PUBLISHEXPERIENCE = "visitor/publishExperience";

    public static final String VISITORHOMEFILTER = "visitor/filter";
    public static final String VISITORHOMEEXP = "visitor/getNearByExperienceList";

    public static final String VISITORHOMELOCAL = "visitor/getNearByLocalList";


    public static final String GETEXPEDITILS = "experience/getExperienceDetail";

    public static final String VISITORSELECTDATEAVABILITY = "visitor/getSelectedDateSchedule";

    public static final String VISITONBOARDING = "visitor/setOnBoarding";

    //Local
    public static final String LOCALSTEPONE = "Local/localStepOne";
    public static final String ADDIMAGESTEPTWO = "Local/addImages";
    public static final String UPLOADCERTIFICATES = "Local/addCertificate";
    public static final String UPLOADACHIVEMENTS = "Local/uploadAchievement";
    public static final String LOCALLANGUAGE = "Local/setLocalLanguage";
    public static final String SETEXPERIENCESIX = "Local/setExperience";
    public static final String SETSPORTPRICERULES = "Local/setPriceRule";
    public static final String SETSPORTEQUPMENTS = "local/setSportEquipment";
    public static final String SETAVAIBILITY = "local/setSchedule";
    public static final String SETCANCELLATION = "Local/setCancellationPolicy";
    public static final String SETSPORTLOCAL = "Local/setAvailableSportLocal";
    public static final String GETSELECTSPORT = "Local/getSelectedSport/";
    public static final String ADDEQUIPMENT = "Local/addEquipment/";
    public static final String SKIPSIGNUPSTEP = "Local/skipSignupStep/";
    public static final String SETLOCATION = "local/setLocation/";
    public static final String BECOMELOCAL = "Local/becomeLocal/";
    public static final String DELETEEQUIPMENT = "Local/deleteEquipment/";

    public static final String GETPROFILE = "Local/getLocalProfile/";
    public static final String GETSPORT = "Local/getSportsImages/";
    public static final String GETCERTIFICATES = "Local/getCertificateImages/";
    public static final String GETACHIVEMENTS = "Local/getAchievementImages/";

    public static final String DELETESPORT = "Local/deleteSportImage/";
    public static final String DELETECERTIFICATE = "local/deleteCertificate/";
    public static final String DELETEACHIVEMENT = "local/deleteAchievement/";
    public static final String BOOKAPPOINTMENT = "visitor/bookLocal";
    public static final String LOCALPENDINGORDER = "Local/getPendingOrder";
    public static final String VISITORPENDINGORDER = "Visitor/getPendingOrder";
    public static final String GETSCHDULE = "Local/getSchedule";
    public static final String BLOGLIKEDISLIKE = "visitor/blogLikeDislike";
    public static final String VISITORHOMEGETONEPAGE = "visitor/getPageOneList";


    public static final String VISITORFILTEREXP = "visitor/filterExperience";
    public static final String VISITORFILTERLOCAL = "visitor/filterLocal";
    public static final String VISITORFILTERGETONEPAGE = "visitor/initialFilter";


    public static final String LOCALACCEPTORDER = "local/getAcceptedOrder";
    public static final String LOCALCOMPLATEORDER = "local/getCompletedOrder";

    public static final String ACCEPTORDER = "local/acceptOrder";
    public static final String REJECTORDER = "local/declineOrder";
    public static final String COMPLATEORDER = "local/completeOrder";
    public static final String CANCELORDER = "local/cancelOrder";
    public static final String SENDPAYMENTREQUESTORDER = "local/sendPaymentRequest";

    public static final String OBJECTIONDETAILS = "local/getObjectionDetail";
    public static final String ACCEPTOBJECTION = "local/acceptObjection";
    public static final String MODIFYOBJECTION = "local/modifyObjection";
    public static final String GETDURATION = "Local/getDuration";
    public static final String SETMODIFYLD = "Local/modifyBooking";
    public static final String SETSPECIALAVABILITY = "local/setSpecialSchedule";
    public static final String GETSPECIALAVABILITY = "local/getSpecialSchedule";
    public static final String GETFILTERDATA = "combine/getFilterFieldDetail";
    public static final String CREATORG = "Local/createOrganisation";
    public static final String ORGDET = "Local/getOrganisationDetail";
    public static final String EDITORG = "Local/editOrganisation";
    public static final String DELETEORGMEDIA = "local/deleteOrganisationMedia";
    public static final String ORGLIOCALLIST = "local/localList";
    public static final String ADDLOCALORG = "local/addLocalToOrganisation";
    public static final String GETSETPAYMENTRULES = "Local/getPaymentRule";

    public static final String ORDERDETAILSLOCAL = "local/orderDetail";
    public static final String GETPAYMENTRULEOPTION = "Local/organisationPaymentRuleOption";
    public static final String GETUNASSIGNLOCAL = "local/unassignedLocal";
    public static final String SETPAYMENTRULES = "local/setPaymentRule";
    public static final String EDITPAYMENTRULES = "Local/updatePaymentRule";
    public static final String DELETEPAYMENTRULES = "Local/deletePaymentRule";
    public static final String ACCEPTORG = "Local/acceptOrganisationRequest";
    public static final String DECLINEORG = "Local/rejectOrganisationRequest";
    public static final String EXITORG = "local/exitOrganisation";
    public static final String GETBOOKINGFEES = "combine/getSetting";

    public static final String SETRATEVISITOR = "visitor/giveRate";
    public static final String SETRATELOCAL = "local/giveRate";

    public static final String GETRATELOCAL = "local/getReview";
    public static final String GETRATEVISITOR = "visitor/getReview";
    public static final String GETLOCALEARNING = "local/earnings";
    public static final String GETORGSTATUS = "Local/organizationStep";
    public static final String DELETELOCALORG = "local/deleteLocalFromOrganisation";
    public static final String GETORGDASHBOARD = "local/organisationDashboard";

    public static final String SETBANKDETAILS = "local/setBankAccount";
    public static final String SETEXPADDDETAILS = "experience/add_details";
    public static final String GETEXPADDDETAILS = "experience/getExperienceAddDetails";

    public static final String SETEXPACTIVITY = "experience/setActivity";
    public static final String SETEXPSPORT = "experience/setSport";
    public static final String SETEXPMEETINGSPOT = "experience/setMeetingLocation";
    public static final String SETEXPCANCELLATIONPOLICY = "experience/setExperienceCancellationPolicy";
    public static final String GETEXPOTHERDETAILS = "experience/getOtherDetails";
    public static final String SETEXPOTHERDETAILS = "experience/setExperienceOtherDetailes";
    public static final String GETEXPOTHERDETAILSFEILDS = "experience/getExperienceExtraFields";
    public static final String SETEXPOTHERDETAILSFEILDS = "experience/setExperienceExtraFields";
    public static final String DELETEEXPOTHERDETAILSFEILDS = "experience/deleteExperienceExtraField";
    public static final String GETEXPLISTLOCAL = "experience/getExperienceList";
    public static final String GETEXPDISABELE = "experience/getExperienceDisable";
    public static final String SETEXPDISABELE = "experience/setExperienceDisable";
    public static final String GETEXPAVABILITY = "experience/getSchedule";
    public static final String SETEXPAVABILITY = "experience/setSchedule";
    public static final String EDITEXPAVABILITY = "experience/editSchedule";
    public static final String DELETEEXPAVABILITY = "experience/deleteSchedule";
    public static final String SETEXPMAXPARTICIPNATS = "experience/setExperienceMaximumParticipant";
    public static final String EXPDELETE = "experience/deleteExperience";
    public static final String GETEXPEDIT = "experience/editExperienceScreen";
    public static final String GETRATEEXP = "experience/getReview";
    public static final String SETSPEAKLANGUAGES = "combine/setSpeakingLanguage";
    public static final String SETEXPEQUIPMENT = "experience/addEquipmentToExperience";

    public static final String GETNOTIFICATIONNOTREAD = "combine/isNotificationRead";
    public static final String SETNOTIFICATIONNOTREAD = "combine/makeAsRead";

    public static final String GETDONTWANTCOMPLETE = "combine/dontWantToComplete";

    public static final String DELETEORG = "local/deleteOrganisation";

    public static final String GETEXPEQUIPMENTORDER = "combine/getOrderEquipmentCheckList";
    public static final String SETEXPEQUIPMENTORDER = "visitor/toggleCheckedEquipment";
    public static final String ADDEXPEQUIPMENTORDER = "local/addEquipmentToBooking";
    public static final String GETCREDITCARD = "visitor/getCard";
    public static final String DELETECREDITCARD = "visitor/deleteCard";
    public static final String ADDCREDITCARD = "visitor/saveCard";
    public static final String GETCOUNTRYLIST = "combine/getCountry";

    public static final String UPLOADDOCUMENT = "local/uploadDocument";
    public static final String GETBANKLIST = "local/getBankList";
    public static final String DELETEBANK = "local/deleteBank";
    public static final String CHANGEDEFAULTEBANK = "local/changeDefaultBank";
    public static final String CHECKORGBANK = "local/isOrganisationHasBank";
    public static final String GETCANCELLATIONAMOUNT = "visitor/getCancellationAmount";
    public static final String VISITORCANCEL = "visitor/cancelOrder";
    public static final String LOCALCANCEL = "local/cancelOrder";
    public static final String MONTHORDEREARNING = "local/monthWiseOrder";
    public static final String SETFAVEXP = "visitor/toggleFavorite";
    public static final String GETBOOKINGFEESNEW = "combine/getFees";
    public static final String LOCALORDERDELETEEQUIPMENT = "local/deleteBookingEquipement";

    public static final String ISCHECKLOCALAFFILATED = "combine/checkLocalIsAffilated";
    public static final String GETFAVEXP = "visitor/getMyFavoriteExperience";

    public static final String GETWATHER = "http://132.148.17.145/~hyperlinkserver/kooloco/listing/weather/";

    public static final String LOCAL = EXTRA_URL + "/map_local_location_icon_eoU_icon.ico";
    public static final String MEETING = EXTRA_URL + "/map_current_location_icon_ymb_icon.ico";
    public static final String CURRENT = EXTRA_URL + "/icon/my_pin_g3h_icon.ico";

    public static final String BECOME_LOCAL_WEB_URL = EXTRA_URL_NEW + "home/become_local_app";

    public static final String EXP_SHARE = EXTRA_URL_NEW + "experience/view/";
    public static final String LOCAL_SHARE = EXTRA_URL_NEW + "visitor/local_profile/";


    public static final String TERMS_CONDITIONS = EXTRA_URL_NEW + "home/terms_of_service_app";
    public static final String FEEDBACKURL = "https://support.kooloco.com/";


}
