package com.kooloco.data.datasource;


import com.google.gson.Gson;
import com.kooloco.core.AppExecutors;
import com.kooloco.core.Session;
import com.kooloco.data.URLFactory;
import com.kooloco.data.entity.mapper.SportActivityEntityMapper;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.data.service.Service;
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
import com.kooloco.model.LocalImage;
import com.kooloco.model.LocalNew;
import com.kooloco.model.Medium;
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
import com.kooloco.model.SetSpecialAvability;
import com.kooloco.model.Sport;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportPriceRules;
import com.kooloco.model.Tag;
import com.kooloco.model.Time;
import com.kooloco.model.UploadDocument;
import com.kooloco.model.UploadImage;
import com.kooloco.model.User;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.net.ConnectException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by hlink21 on 16/5/17. It is used to Live api call
 * This class used to call live api
 */
@Singleton
public class KoolocoDataSource extends BaseDataSource implements KoolocoRepository {
    private final Service service;
    @Inject
    Session session;

    @Inject
    DatabaseCacheDataSource databaseCacheDataSource;

    @Inject
    public KoolocoDataSource(AppExecutors appExecutors, Service service) {
        super(appExecutors);
        this.service = service;
    }

/*
    @Override
    public Single<Response> getBrandList() {
        return execute(service.getBrandList())
                .map(BaseMapper.responseMapFunction);
    }
*/


    @Override
    public Single<Response<UploadImage>> uploadImage(Map<String, RequestBody> partMap, MultipartBody.Part profileImage) {
        return execute(service.uploadImage(partMap, profileImage));
    }

    @Override
    public Single<Response<UploadDocument>> uploadDocument(MultipartBody.Part profileImage) {
        return execute(service.uploadDocument(profileImage));
    }

    @Override
    public Single<Response<User>> postSignup(Map<String, String> map) {
        return execute(service.signUp(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUserSession(userResponse.getData().getToken());
                    session.setUser(userResponse.getData());
                    session.setUserId(userResponse.getData().getId());
                }
            }
        });
    }

    @Override
    public Single<Response<User>> postLogin(Map<String, String> map) {
        return execute(service.login(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUserSession(userResponse.getData().getToken());
                    session.setUser(userResponse.getData());
                    session.setUserId(userResponse.getData().getId());
                }
            }
        });
    }

    @Override
    public Single<Response<User>> postCheckSocial(Map<String, String> map) {
        return execute(service.checkSocial(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUserSession(userResponse.getData().getToken());
                    session.setUser(userResponse.getData());
                    session.setUserId(userResponse.getData().getId());
                }
            }
        });
    }


    @Override
    public Single<Response<User>> localStepOne(Map<String, String> map) {
        return execute(service.localStepOne(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<User>> addImageStepTwo(Map<String, String> partMap) {
        return execute(service.addImageStepTwo(partMap)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<List<SportActivity>>> sportType(Map<String, String> param) {
        SportActivityEntityMapper sportActivityEntityMapper = new SportActivityEntityMapper();
        return executeMapper(service.sportType(param)).map(sportActivityEntityMapper.collectionMapFunction);
    }

    @Override
    public Single<Response<User>> uploadCertificationStepThree(Map<String, String> partMap) {
        return execute(service.UploadCertificationStepThree(partMap)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<User>> uploadAchivementsStepFour(Map<String, String> partMap) {
        return execute(service.uploadAchivementsStepFour(partMap)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<List<Tag>>> tagList(Map<String, String> map) {
        return execute(service.tagList(map));
    }

    @Override
    public Single<Response<User>> localLanguage(Map<String, String> map) {
        return execute(service.LocalLanguage(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<LanguageResponse>> language(Map<String, String> map) {
        return execute(service.language(map).doOnSuccess(new Consumer<Response<LanguageResponse>>() {
            @Override
            public void accept(Response<LanguageResponse> languageResponseResponse) throws Exception {

                for (int i = 0; i < languageResponseResponse.getData().getAppLanguage().size(); i++) {

                    Language language = languageResponseResponse.getData().getAppLanguage().get(i);

                    language.setSelect(language.getIsSelected().equalsIgnoreCase("1"));

                    languageResponseResponse.getData().getAppLanguage().set(i, language);

                }

                for (int i = 0; i < languageResponseResponse.getData().getSpeakingLanguages().size(); i++) {

                    Language language = languageResponseResponse.getData().getSpeakingLanguages().get(i);

                    language.setSelect(language.getIsSelected().equalsIgnoreCase("1"));

                    languageResponseResponse.getData().getSpeakingLanguages().set(i, language);

                }
            }
        }));
    }

    @Override
    public Single<Response<List<Currency>>> getCurrency(Map<String, String> map) {
        return execute(service.getCurrency(map));
    }

    @Override
    public Single<Response<User>> setCurrency(Map<String, String> map) {
        return execute(service.setCurrency(map)).doOnSuccess(userResponse -> {
            if (userResponse.getResponseCode() == 1) {
                session.setUser(userResponse.getData());
            }
        });
    }

    @Override
    public Single<Response<List<Activities>>> experience(Map<String, String> map) {
        return execute(service.experience(map));
    }

    @Override
    public Single<Response<User>> setExperienceStepSix(Map<String, String> map) {
        return execute(service.setExperienceStepSix(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<User>> setSportPriceRules(Map<String, String> map) {
        return execute(service.setSportPriceRules(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<EquipmentResponse>> equipment(Map<String, String> map) {
        return execute(service.equipment(map));
    }

    @Override
    public Single<Response<User>> setSportEquipments(Map<String, String> map) {
        return execute(service.setSportEquipments(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<User>> setSchedule(Map<String, String> map) {
        return execute(service.setSchedule(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<List<CancellationPolicy>>> cancellation(Map<String, String> map) {
        return execute(service.cancellation(map));
    }

    @Override
    public Single<Response<User>> setcancellation(Map<String, String> map) {
        return execute(service.setcancellation(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<User>> setSportLocal(Map<String, String> map) {
        return execute(service.setSportLocal(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<List<SportPriceRules>>> getSelectSport(Map<String, String> map) {
        return execute(service.getSelectSport(map));
    }

    @Override
    public Single<Response<Equipment>> addEquipment(Map<String, String> map) {
        return execute(service.addEquipment(map));
    }

    @Override
    public Single<Response<User>> skipSignupStep(Map<String, String> map) {
        return execute(service.skipSignupStep(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<User>> setLocation(Map<String, String> map) {
        return execute(service.setLocation(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<User>> setBecomeLocal(Map<String, String> map) {
        return execute(service.setBecomeLocal(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1 || userResponse.getResponseCode() == 12) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<User>> setVisitor(Map<String, String> map) {
        return execute(service.setVisitor(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                }
            }
        });
    }

    @Override
    public Single<Response<List<DashboardDetails>>> getVisitorHome(Map<String, String> map, boolean isFirstTime) {
        return execute(service.getVisitorHome(map)).onErrorResumeNext(new Function<Throwable, SingleSource<? extends Response<List<DashboardDetails>>>>() {
            @Override
            public SingleSource<? extends Response<List<DashboardDetails>>> apply(Throwable throwable) throws Exception {
                if (throwable instanceof ConnectException) {
                    if (isFirstTime) {
                        Response<List<DashboardDetails>> listResponse = databaseCacheDataSource.getVsitorHome();
                        if (listResponse != null && listResponse.getData() != null) {
                            if (listResponse.getData().size() == 0) {
                                return Single.error(throwable);
                            } else {
                                return Single.just(listResponse);
                            }
                        } else {
                            return Single.error(throwable);
                        }
                    } else {
                        return Single.error(throwable);
                    }
                }
                return Single.error(throwable);
            }
        }).doOnSuccess(new Consumer<Response<List<DashboardDetails>>>() {
            @Override
            public void accept(Response<List<DashboardDetails>> listResponse) throws Exception {
                if (isFirstTime) {
                    databaseCacheDataSource.setDataCache(URLFactory.VISITORHOME, new Gson().toJson(listResponse));
                }
            }
        });
    }

    @Override
    public Single<Response<HomeNewResponse>> getVisitorHomeNew(Map<String, String> map) {
        return execute(service.getVisitorHomeNew(map)).onErrorResumeNext(new Function<Throwable, SingleSource<? extends Response<HomeNewResponse>>>() {
            @Override
            public SingleSource<? extends Response<HomeNewResponse>> apply(Throwable throwable) throws Exception {
                if (throwable instanceof ConnectException) {
                    Response<HomeNewResponse> listResponse = databaseCacheDataSource.getVisitorHomeNew();
                    return Single.just(listResponse);
                }
                return Single.error(throwable);
            }
        }).doOnSuccess(new Consumer<Response<HomeNewResponse>>() {
            @Override
            public void accept(Response<HomeNewResponse> homeNewResponseResponse) throws Exception {
                databaseCacheDataSource.setDataCache(URLFactory.VISITORHOMENEW, new Gson().toJson(homeNewResponseResponse));
            }
        });
    }


    @Override
    public Single<Response<HomeLocalAndExpResponse>> getVisitorOnePage(Map<String, String> map) {
        return execute(service.getVisitorOnePage(map).onErrorResumeNext(new Function<Throwable, SingleSource<? extends Response<HomeLocalAndExpResponse>>>() {
            @Override
            public SingleSource<? extends Response<HomeLocalAndExpResponse>> apply(Throwable throwable) throws Exception {
                if (throwable instanceof ConnectException) {
                    Response<HomeLocalAndExpResponse> listResponse = databaseCacheDataSource.getVisitorHomeOnePage();
                    return Single.just(listResponse);
                }
                return Single.error(throwable);
            }
        }).doOnSuccess(new Consumer<Response<HomeLocalAndExpResponse>>() {
            @Override
            public void accept(Response<HomeLocalAndExpResponse> homeLocalAndExpResponseResponse) throws Exception {
                databaseCacheDataSource.setDataCache(URLFactory.VISITORHOMEGETONEPAGE, new Gson().toJson(homeLocalAndExpResponseResponse));
            }
        }));
    }


    @Override
    public Single<Response<List<ExpereinceNew>>> getVisitorHomeExp(Map<String, String> map) {
        return execute(service.getVisitorHomeExp(map));
    }

    @Override
    public Single<Response<List<LocalNew>>> getVisitorHomeLocal(Map<String, String> map) {
        return execute(service.getVisitorHomeLocal(map));
    }

    @Override
    public Single<Response<DashboardDetails>> getVisitorHomeLocalDetails(Map<String, String> map) {
        return execute(service.getVisitorHomeLocalDetails(map));
    }

    @Override
    public Single<Response<DisableDateListResposne>> getDisableDateList(Map<String, String> map) {
        return execute(service.getDisableDateList(map)).doOnSuccess(new Consumer<Response<DisableDateListResposne>>() {
            @Override
            public void accept(Response<DisableDateListResposne> disableDateListResposneResponse) throws Exception {

                ArrayList<CalendarDay> enabledDates = new ArrayList<>();

                for (String date : disableDateListResposneResponse.getData().getSelectDate()) {

                    DateFormat timeFormater = new SimpleDateFormat("yyyy-MM-dd");

                    try {

                        Date time;

                        timeFormater.setTimeZone(TimeZone.getDefault());

                        time = timeFormater.parse(date);

                        enabledDates.add(new CalendarDay(time));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
                disableDateListResposneResponse.getData().setCalendarDays(enabledDates);
            }
        });
    }

    @Override
    public Single<Response<List<DashboardDetails>>> getVisitorHomeFilter(Map<String, String> map) {
        return execute(service.getVisitorHomeFilter(map));
    }

    @Override
    public Single<Response> logout(Map<String, String> map) {
        return execute(service.logout(map));
    }

    @Override
    public Single<Response> changePassword(Map<String, String> map) {
        return execute(service.changePassword(map));
    }

    @Override
    public Single<Response> forgotPassword(Map<String, String> map) {
        return execute(service.forgotPassword(map));
    }

    @Override
    public Single<Response> deleteEquipment(Map<String, String> map) {
        return execute(service.deleteEquipment(map));
    }

    @Override
    public Single<Response<DashboardDetails>> getLocalProfile(Map<String, String> map) {
        return execute(service.getLocalProfile(map));
    }

    @Override
    public Single<Response<List<Certifications>>> getSport(Map<String, String> map) {
        return execute(service.getSport(map));
    }

    @Override
    public Single<Response<List<Certifications>>> getCertification(Map<String, String> map) {
        return execute(service.getCertification(map));
    }

    @Override
    public Single<Response<List<Certifications>>> getAchivements(Map<String, String> map) {
        return execute(service.getAchivements(map));
    }

    @Override
    public Single<Response> deleteSportImage(Map<String, String> map) {
        return execute(service.deleteSportImage(map));
    }

    @Override
    public Single<Response> deleteCertificate(Map<String, String> map) {
        return execute(service.deleteCertificate(map));
    }

    @Override
    public Single<Response> deleteAchivement(Map<String, String> map) {
        return execute(service.deleteAchivement(map));
    }

    @Override
    public Single<Response<User>> updateProfile(Map<String, String> partMap) {
        return execute(service.updateProfile(partMap)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                    session.setUserId(userResponse.getData().getId());
                }
            }
        });
    }

    @Override
    public Single<Response<SelectActivites>> activityAndSport(Map<String, String> map) {
        return execute(service.activityAndSport(map));
    }

    @Override
    public Single<Response<List<Time>>> getStartTime(Map<String, String> map) {
        return execute(service.getStartTime(map));
    }

    @Override
    public Single<Response<List<Time>>> getEndTime(Map<String, String> map) {
        return execute(service.getEndTime(map));
    }

    @Override
    public Single<Response<BookingData>> boookAppointment(Map<String, String> map) {
        return execute(service.boookAppointment(map));
    }

    @Override
    public Single<Response<List<Quation>>> getQuation(Map<String, String> map) {
        return execute(service.getQuation(map));
    }

    /* @Override
    public Single<Response<List<Order>>> getPendingOrderVisitor(Map<String, String> map) {
        return execute(service.getPendingOrderVisitor(map));
    }

    @Override
    public Single<Response<List<Order>>> getPendingOrderLocal(Map<String, String> map) {
        return execute(service.getPendingOrderLocal(map));
    }*/

  /*  @Override
    public Single<Response> getPendingOrderVisitor(Map<String, String> map) {
        return execute(service.getPendingOrderVisitor(map));
    }

    @Override
    public Single<Response> getPendingOrderLocal(Map<String, String> map) {
        return execute(service.getPendingOrderLocal(map));

    }*/

    @Override
    public Single<Response<List<Order>>> getPendingOrderVisitor(Map<String, String> map) {
        return execute(service.getPendingOrderVisitor(map));
    }

    @Override
    public Single<Response<List<Order>>> getPendingOrderLocal(Map<String, String> map) {
        return execute(service.getPendingOrderLocal(map));
    }

    @Override
    public Single<Response<List<Order>>> getComplateOrderVisitor(Map<String, String> map) {
        return execute(service.getComplateOrderVisitor(map));
    }

    @Override
    public Single<Response<List<Order>>> getAcceptOrderLocal(Map<String, String> map) {
        return execute(service.getAcceptOrderLocal(map));
    }

    @Override
    public Single<Response<List<Order>>> getComplateOrderLocal(Map<String, String> map) {
        return execute(service.getComplateOrderLocal(map));
    }

    @Override
    public Single<Response<List<SchduleDashboard>>> getSchdule(Map<String, String> map) {
        return execute(service.getSchdule(map));
    }


    @Override
    public Single<Response<User>> getNotificationAction(Map<String, String> map) {
        return execute(service.notificationAction(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                    session.setUserId(userResponse.getData().getId());
                }
            }
        });
    }

    @Override
    public Single<Response<User>> getNotificationEmailAction(Map<String, String> map) {
        return execute(service.notificationEmailAction(map)).doOnSuccess(new Consumer<Response<User>>() {
            @Override
            public void accept(Response<User> userResponse) throws Exception {
                if (userResponse.getResponseCode() == 1) {
                    session.setUser(userResponse.getData());
                    session.setUserId(userResponse.getData().getId());
                }
            }
        });
    }

    @Override
    public Single<Response<List<Notification>>> getNotifications(Map<String, String> map) {
        return execute(service.getNotifications(map));
    }

    @Override
    public Single<Response> acceptOrder(Map<String, String> map) {
        return execute(service.acceptOrder(map));
    }

    @Override
    public Single<Response> rejectOrder(Map<String, String> map) {
        return execute(service.rejectOrder(map));
    }

    @Override
    public Single<Response> complateOrder(Map<String, String> map) {
        return execute(service.complateOrder(map));
    }

    @Override
    public Single<Response> cancelOrder(Map<String, String> map) {
        return execute(service.cancelOrder(map));
    }

    @Override
    public Single<Response> sendPaymentRequest(Map<String, String> map) {
        return execute(service.sendPaymentREquestOrder(map));
    }

    @Override
    public Single<Response<OrderDetails>> orderDetailsLocal(Map<String, String> map) {
        return execute(service.orderDetailsLocal(map));
    }

    @Override
    public Single<Response<OrderDetails>> orderDetailsVisitor(Map<String, String> map) {
        return execute(service.orderDetailsVisitor(map));
    }

    @Override
    public Single<Response<Order>> orderDataLocal(Map<String, String> map) {
        return execute(service.orderDataLocal(map));
    }

    @Override
    public Single<Response<Order>> orderDataVisitor(Map<String, String> map) {
        return execute(service.orderDataVisitor(map));
    }

    @Override
    public Single<Response> sendObjection(Map<String, String> map) {
        return execute(service.sendObjection(map));
    }

    @Override
    public Single<Response> payNow(Map<String, String> map) {
        return execute(service.payNow(map));
    }

    @Override
    public Single<Response<ObjectionDetails>> objectionDetails(Map<String, String> map) {
        return execute(service.objectionDetails(map));
    }

    @Override
    public Single<Response> acceptObjection(Map<String, String> map) {
        return execute(service.acceptObjection(map));
    }

    @Override
    public Single<Response> modifyObjection(Map<String, String> map) {
        return execute(service.modifyObjection(map));
    }

    @Override
    public Single<Response<ObjectionDetails>> objectionDetailsVisitor(Map<String, String> map) {
        return execute(service.objectionDetailsVisitor(map));
    }

    @Override
    public Single<Response> acceptObjectionVisitor(Map<String, String> map) {
        return execute(service.acceptObjectionVisitor(map));
    }

    @Override
    public Single<Response> adminObjectionRequest(Map<String, String> map) {
        return execute(service.adminObjectionRequest(map));
    }

    @Override
    public Single<Response<List<String>>> getDurationLocal(Map<String, String> map) {
        return execute(service.getDurationLocal(map));
    }

    @Override
    public Single<Response> setModifyLocationDuration(Map<String, String> map) {
        return execute(service.setModifyLocationDuration(map));
    }

    @Override
    public Single<Response> acceptLocationDuration(Map<String, String> map) {
        return execute(service.acceptLocation(map));
    }

    @Override
    public Single<Response> declineLocationDuration(Map<String, String> map) {
        return execute(service.declineDuration(map));
    }

    @Override
    public Single<Response> notifyAction(Map<String, String> map) {
        return execute(service.notifyAction(map));
    }

    @Override
    public Single<Response<List<ExperienceDetails>>> getExperience(Map<String, String> map, boolean isFirstTime) {
        return execute(service.getExperience(map)).onErrorResumeNext(new Function<Throwable, SingleSource<? extends Response<List<ExperienceDetails>>>>() {
            @Override
            public SingleSource<? extends Response<List<ExperienceDetails>>> apply(Throwable throwable) throws Exception {
                if (throwable instanceof ConnectException) {
                    if (isFirstTime) {
                        Response<List<ExperienceDetails>> listResponse = databaseCacheDataSource.getExperienceList();
                        if (listResponse != null && listResponse.getData() != null) {
                            if (listResponse.getData().size() == 0) {
                                return Single.error(throwable);
                            } else {
                                return Single.just(listResponse);
                            }
                        } else {
                            return Single.error(throwable);
                        }
                    } else {
                        return Single.error(throwable);
                    }
                }
                return Single.error(throwable);
            }
        }).doOnSuccess(new Consumer<Response<List<ExperienceDetails>>>() {
            @Override
            public void accept(Response<List<ExperienceDetails>> listResponse) throws Exception {
                if (isFirstTime) {
                    databaseCacheDataSource.setDataCache(URLFactory.EXPERINCELIST, new Gson().toJson(listResponse));
                }
            }
        });
    }

    @Override
    public Single<Response<List<BlogDetails>>> getBlogList(Map<String, String> map, boolean isFirstTime) {
        return execute(service.getBlogList(map)).onErrorResumeNext(new Function<Throwable, SingleSource<? extends Response<List<BlogDetails>>>>() {
            @Override
            public SingleSource<? extends Response<List<BlogDetails>>> apply(Throwable throwable) throws Exception {
                if (throwable instanceof ConnectException) {
                    if (isFirstTime) {
                        Response<List<BlogDetails>> listResponse = databaseCacheDataSource.getBlogList();
                        if (listResponse != null && listResponse.getData() != null) {
                            if (listResponse.getData().size() == 0) {
                                return Single.error(throwable);
                            } else {
                                return Single.just(listResponse);
                            }
                        } else {
                            return Single.error(throwable);
                        }
                    } else {
                        return Single.error(throwable);
                    }
                }
                return Single.error(throwable);
            }
        }).doOnSuccess(new Consumer<Response<List<BlogDetails>>>() {
            @Override
            public void accept(Response<List<BlogDetails>> listResponse) throws Exception {
                if (isFirstTime) {
                    databaseCacheDataSource.setDataCache(URLFactory.EXPERINCELIST, new Gson().toJson(listResponse));
                }
            }
        });
    }


    @Override
    public Single<Response<BlogDetails>> bloglike(Map<String, String> map) {
        return execute(service.bloglike(map));
    }

    @Override
    public Single<Response<List<Review>>> blogComment(Map<String, String> map) {
        return execute(service.blogComment(map));
    }

    @Override
    public Single<Response<Review>> addComment(Map<String, String> map) {
        return execute(service.addComment(map));
    }

    @Override
    public Single<Response> publishExperience(Map<String, String> map) {
        return execute(service.publishExperience(map));
    }

    @Override
    public Single<Response<FilterGetData>> filterData(Map<String, String> map) {
        return execute(service.filterData(map));
    }

    @Override
    public Single<Response<OrganizationDetails>> createOrg(Map<String, String> map) {
        return execute(service.createOrg(map));
    }

    @Override
    public Single<Response<OrganizationDetails>> orgDetails(Map<String, String> map) {
        return execute(service.orgDetails(map));
    }

    @Override
    public Single<Response> editOrg(Map<String, String> map) {
        return execute(service.editOrg(map));
    }

    @Override
    public Single<Response<List<OrgLocal>>> orgLocalList(Map<String, String> map) {
        return execute(service.orgLocalList(map));
    }

    @Override
    public Single<Response> addToLocalOrg(Map<String, String> map) {
        return execute(service.addToLocalOrg(map));
    }

    @Override
    public Single<Response<List<PaymentRuleList>>> getSetPaymentRules(Map<String, String> map) {
        return execute(service.getSetPaymentRules(map));
    }

    @Override
    public Single<Response<List<PaymentRulesOption>>> getPaymentRulesOption(Map<String, String> map) {
        return execute(service.getPaymentRulesOption(map));
    }

    @Override
    public Single<Response<List<OrgLocal>>> getUnAssignLocal(Map<String, String> map) {
        return execute(service.getUnAssignLocal(map));
    }

    @Override
    public Single<Response> setPaymentRule(Map<String, String> map) {
        return execute(service.setPaymentRule(map));
    }

    @Override
    public Single<Response> editPaymentRule(Map<String, String> map) {
        return execute(service.editPaymentRule(map));
    }

    @Override
    public Single<Response> deletePaymentRule(Map<String, String> map) {
        return execute(service.deletePaymentRule(map));
    }

    @Override
    public Single<Response> acceptOrg(Map<String, String> map) {
        return execute(service.acceptOrg(map));
    }

    @Override
    public Single<Response> declineOrg(Map<String, String> map) {
        return execute(service.declineOrg(map));
    }

    @Override
    public Single<Response> exitOrg(Map<String, String> map) {
        return execute(service.exitOrg(map));
    }

    @Override
    public Single<Response<List<Activities>>> getVisitorActivities(Map<String, String> map) {
        return execute(service.getVisitorActivities(map));
    }

    @Override
    public Single<Response<BookingFeeAndComision>> getBookingFees(Map<String, String> map) {
        return execute(service.getBookingFees(map));
    }

    @Override
    public Single<Response<BookingFeeAndComision>> getBookingFeesNew(Map<String, String> map) {
        return execute(service.getBookingFeesNew(map));
    }

    @Override
    public Single<Response<List<RatingOption>>> getRatOption(Map<String, String> map) {
        return execute(service.getRatOption(map));
    }

    @Override
    public Single<Response> setRateVisitor(Map<String, String> map) {
        return execute(service.setRateVisitor(map));
    }

    @Override
    public Single<Response> setRateLocal(Map<String, String> map) {
        return execute(service.setRateLocal(map));
    }

    @Override
    public Single<Response<User>> getProfileVisitor(Map<String, String> map) {
        return execute(service.getProfileVisitor(map));
    }

    @Override
    public Single<Response<ReviewData>> getRateVisitor(Map<String, String> map) {
        return execute(service.getRateVisitor(map));
    }

    @Override
    public Single<Response<ReviewData>> getRateLocal(Map<String, String> map) {
        return execute(service.getRateLocal(map));
    }

    @Override
    public Single<Response<ReviewData>> getRateExp(Map<String, String> map) {
        return execute(service.getRateExp(map));
    }

    @Override
    public Single<Response<EarningData>> getEarningLocal(Map<String, String> map) {
        return execute(service.getEarningLocal(map));
    }

    @Override
    public Single<Response> getdontWantComplete(Map<String, String> map) {
        return execute(service.getdontWantComplete(map));
    }

    @Override
    public Single<Response<OrganizationStaus>> getOrgStatus(Map<String, String> map) {
        return execute(service.getOrgStatus(map));
    }

    @Override
    public Single<Response> getdeleteLocalOrg(Map<String, String> map) {
        return execute(service.getdeleteLocalOrg(map));
    }

    @Override
    public Single<Response<OrganizationDashboard>> getOrgDashboard(Map<String, String> map) {
        return execute(service.getOrgDashboard(map));
    }

    @Override
    public Single<Response> deleteOrgnization(Map<String, String> map) {
        return execute(service.deleteOrgnization(map));
    }

    @Override
    public Single<Response<CheckPaymentRules>> getIsCheckLocalAffilted(Map<String, String> map) {
        return execute(service.getIsCheckLocalAffilted(map));
    }


    @Override
    public Single<Response<DashboardDetails>> setBankDetails(Map<String, String> map) {
        return execute(service.setBankDetails(map));
    }

    @Override
    public Single<Response<ExperienceResponse>> setExpAddDetails(Map<String, String> map) {
        return execute(service.setExpAddDetails(map));
    }

    @Override
    public Single<Response<ExperienceResponse>> getExpAddDetails(Map<String, String> map) {
        return execute(service.getExpAddDetails(map));
    }

    @Override
    public Single<Response> deleteOrgMediea(Map<String, String> map) {
        return execute(service.deleteOrgMediea(map));
    }


    @Override
    public Single<Response<ExperienceResponse>> setExpActivity(Map<String, String> map) {
        return execute(service.setExpActivity(map));
    }

    @Override
    public Single<Response<ExperienceResponse>> setExpSport(Map<String, String> map) {
        return execute(service.setExpSport(map));
    }

    @Override
    public Single<Response<ExperienceResponse>> setExpMeetingSpot(Map<String, String> map) {
        return execute(service.setExpMeetingSpot(map));
    }

    @Override
    public Single<Response<ExperienceResponse>> setExpCancelationPolicy(Map<String, String> map) {
        return execute(service.setExpCancelationPolicy(map));
    }

    @Override
    public Single<Response<OtheDetailsResponse>> getExpOtherDetails(Map<String, String> map) {
        return execute(service.getExpOtherDetails(map));
    }

    @Override
    public Single<Response<ExperienceResponse>> setExpOtherDetails(Map<String, String> map) {
        return execute(service.setExpOtherDetails(map));
    }


    @Override
    public Single<Response<OtherDetailsAnotherFields>> setExpOtherDetailsFields(Map<String, String> map) {
        return execute(service.setExpOtherDetailsFields(map));
    }

    @Override
    public Single<Response<List<OtherDetailsAnotherFields>>> getExpOtherDetailsFields(Map<String, String> map) {
        return execute(service.getExpOtherDetailsFields(map));
    }


    @Override
    public Single<Response> deleteExpOtherDetailsFields(Map<String, String> map) {
        return execute(service.deleteExpOtherDetailsFields(map));
    }

    @Override
    public Single<Response<DisableExperience>> getExpDisable(Map<String, String> map) {
        return execute(service.getExpDisable(map));
    }

    @Override
    public Single<Response> setExpDisable(Map<String, String> map) {
        return execute(service.setExpDisable(map));
    }

    @Override
    public Single<Response<ScheduleAndPriceResponse>> getExpAvability(Map<String, String> map) {
        return execute(service.getExpAvability(map));
    }

    @Override
    public Single<Response<ScheduleAndPriceResponse>> setExpAvability(Map<String, String> map) {
        return execute(service.setExpAvability(map));
    }

    @Override
    public Single<Response<ScheduleAndPriceResponse>> editExpAvability(Map<String, String> map) {
        return execute(service.editExpAvability(map));
    }

    @Override
    public Single<Response<ScheduleAndPriceResponse>> deleteExpAvability(Map<String, String> map) {
        return execute(service.deleteExpAvability(map));
    }

    @Override
    public Single<Response> setExpMaxParticipants(Map<String, String> map) {
        return execute(service.setExpMaxParticipants(map));
    }

    @Override
    public Single<Response> deleteExp(Map<String, String> map) {
        return execute(service.deleteExp(map));
    }

    @Override
    public Single<Response<ExpereinceNew>> getExpEdit(Map<String, String> map) {
        return execute(service.getExpEdit(map));
    }

    @Override
    public Single<Response<ExpDetails>> getExpDetails(Map<String, String> map) {
        return execute(service.getExpDetails(map)).doOnSuccess(new Consumer<Response<ExpDetails>>() {
            @Override
            public void accept(Response<ExpDetails> expDetailsResponse) throws Exception {

                List<LocalImage> localImages = new ArrayList<>();
                for (Medium medium : expDetailsResponse.getData().getMedia()) {
                    LocalImage localImage = new LocalImage();

                    localImage.setLocalImage(medium.getImage());
                    localImage.setLocalImageUrl(medium.getImage());

                    localImages.add(localImage);
                }
                expDetailsResponse.getData().setImages(localImages);
                expDetailsResponse.getData().setLocalName(expDetailsResponse.getData().getLocalInfoNew().getFirstname() + " " + expDetailsResponse.getData().getLocalInfoNew().getLastname());
                expDetailsResponse.getData().setLocalImage(expDetailsResponse.getData().getLocalInfoNew().getProfileImage());
                expDetailsResponse.getData().setLocalLanguage(expDetailsResponse.getData().getLocalInfoNew().getLanguages());
                expDetailsResponse.getData().setLocalDesc(expDetailsResponse.getData().getLocalInfoNew().getIntroYourSelf());
                expDetailsResponse.getData().setLocalRate(expDetailsResponse.getData().getLocalInfoNew().getRate());

                ArrayList<CalendarDay> enabledDates = new ArrayList<>();

                for (String date : expDetailsResponse.getData().getCalenderDates()) {

                    DateFormat timeFormater = new SimpleDateFormat("yyyy-MM-dd");

                    try {

                        Date time;

                        timeFormater.setTimeZone(TimeZone.getDefault());

                        time = timeFormater.parse(date);

                        enabledDates.add(new CalendarDay(time));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
                expDetailsResponse.getData().setCalendarDays(enabledDates);
            }
        });
    }

    @Override
    public Single<Response<List<SchedulePrice>>> getSelectDateSchedule(Map<String, String> map) {
        return execute(service.getSelectDateSchedule(map));
    }

    @Override
    public Single<Response<HomeLocalAndExpResponse>> getFilterOnePage(Map<String, String> map) {
        return execute(service.getFilterOnePage(map));
    }

    @Override
    public Single<Response<List<ExpereinceNew>>> getFilterExp(Map<String, String> map) {
        return execute(service.getFilterExp(map));
    }

    @Override
    public Single<Response<List<LocalNew>>> getFilterLocal(Map<String, String> map) {
        return execute(service.getFilterLocal(map));
    }

    @Override
    public Single<Response> setOnBordingData(Map<String, String> map) {
        return execute(service.setOnBordingData(map));
    }

    @Override
    public Single<Response> setSPeakLanguage(Map<String, String> map) {
        return execute(service.setSPeakLanguage(map));
    }

    @Override
    public Single<Response<List<ExpereinceNew>>> getExpListLocal(Map<String, String> map) {
        return execute(service.getExpListLocal(map));
    }

    @Override
    public Single<Response<NotificationReadCheck>> getNotificationReadCheck(Map<String, String> map) {
        return execute(service.getNotificationReadCheck(map));
    }

    @Override
    public Single<Response> setNotificationRead(Map<String, String> map) {
        return execute(service.setNotificationRead(map));
    }

    @Override
    public Single<Response> setExpEquipment(Map<String, String> map) {
        return execute(service.setExpEquipment(map));
    }

    @Override
    public Single<Response<CheckEquipmentResponse>> getEquipmentOrder(Map<String, String> map) {
        return execute(service.getEquipmentOrder(map));
    }

    @Override
    public Single<Response> setEquipmentOrder(Map<String, String> map) {
        return execute(service.setEquipmentOrder(map));
    }

    @Override
    public Single<Response<CheckEquipment>> addEquipmentOrder(Map<String, String> map) {
        return execute(service.addEquipmentOrder(map));
    }

    @Override
    public Single<Response<List<MultiFile>>> uploadFile(Map<String, RequestBody> partMap, List<MultipartBody.Part> partFile) {
        return execute(service.uploadFile(partMap, partFile));
    }

    @Override
    public Single<Response<BlogDetails>> blogDetails(Map<String, String> map) {
        return execute(service.blogDetails(map));
    }

    @Override
    public Single<Response> deleteBlogMediea(Map<String, String> map) {
        return execute(service.deleteBlogMediea(map));
    }

    @Override
    public Single<Response<List<Card>>> getCreditCardList(Map<String, String> map) {
        return execute(service.getCreditCardList(map));
    }

    @Override
    public Single<Response> deleteCreditCardList(Map<String, String> map) {
        return execute(service.deleteCreditCardList(map));
    }

    @Override
    public Single<Response<List<Card>>> addCreditCardList(Map<String, String> map) {
        return execute(service.addCreditCardList(map));
    }

    @Override
    public Single<Response<List<CountryList>>> getCountryList(Map<String, String> map) {
        return execute(service.getCountryList(map));
    }

    @Override
    public Single<Response> changeDefaultBank(Map<String, String> map) {
        return execute(service.changeDefaultBank(map));
    }

    @Override
    public Single<Response> deleteBank(Map<String, String> map) {
        return execute(service.deleteBank(map));
    }

    @Override
    public Single<Response<List<Bank>>> getBankList(Map<String, String> map) {
        return execute(service.getBankList(map));
    }

    @Override
    public Single<Response<CheckBank>> checkOrgBank(Map<String, String> map) {
        return execute(service.checkOrgBank(map));
    }

    @Override
    public Single<Response<CancellationData>> getCancellationAmount(Map<String, String> map) {
        return execute(service.getCancellationAmount(map));
    }

    @Override
    public Single<Response> visitorCancel(Map<String, String> map) {
        return execute(service.visitorCancel(map));
    }

    @Override
    public Single<Response> localCancel(Map<String, String> map) {
        return execute(service.localCancel(map));
    }

    @Override
    public Single<Response<List<Order>>> getLocalMonthEarning(Map<String, String> map) {
        return execute(service.getLocalMonthEarning(map));
    }

    @Override
    public Single<Response<List<ExpereinceNew>>> getFavExp(Map<String, String> map) {
        return execute(service.getFavExp(map));
    }

    @Override
    public Single<Response<ExpFavData>> setFavExp(Map<String, String> map) {
        return execute(service.setFavExp(map));
    }

    @Override
    public Single<Response> localOrderEquipmentDelete(Map<String, String> map) {
        return execute(service.localOrderEquipmentDelete(map));
    }

    @Override
    public Single<Response> editExperience(Map<String, String> map) {
        return execute(service.editExperience(map));
    }

    @Override
    public Single<Response> setSpecialAvavility(Map<String, String> map) {
        return execute(service.setSpecialAvavility(map));
    }


    @Override
    public Single<Response<SetSpecialAvability>> getSpecialAvavility(Map<String, String> map) {
        return execute(service.getSpecialAvavility(map));
    }

    @Override
    public Single<Response<List<com.kooloco.model.Service>>> sportServiceType(Map<String, String> map) {
        return execute(service.serviceType(map));
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

}
