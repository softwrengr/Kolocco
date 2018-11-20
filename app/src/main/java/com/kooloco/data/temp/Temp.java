package com.kooloco.data.temp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kooloco.R;
import com.kooloco.model.Activities;
import com.kooloco.model.AddImages;
import com.kooloco.model.AllMonth;
import com.kooloco.model.Bank;
import com.kooloco.model.BookingFeeAndComision;
import com.kooloco.model.CancellationNewPolicy;
import com.kooloco.model.CancellationPolicy;
import com.kooloco.model.Card;
import com.kooloco.model.CertificateInfo;
import com.kooloco.model.CheckSportEquipment;
import com.kooloco.model.Country;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.DisableSport;
import com.kooloco.model.Duration;
import com.kooloco.model.EarningMonth;
import com.kooloco.model.EarningRate;
import com.kooloco.model.Equipment;
import com.kooloco.model.EquipmentResponse;
import com.kooloco.model.ExpDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.ExperienceResponse;
import com.kooloco.model.Favorites;
import com.kooloco.model.FilterGetData;
import com.kooloco.model.HelpAndFaq;
import com.kooloco.model.HomeTopLocation;
import com.kooloco.model.Invite;
import com.kooloco.model.Language;
import com.kooloco.model.LanguageResponse;
import com.kooloco.model.LocalImage;
import com.kooloco.model.LocalNew;
import com.kooloco.model.Month;
import com.kooloco.model.Notification;
import com.kooloco.model.Order;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.OrgImage;
import com.kooloco.model.OrgLocal;
import com.kooloco.model.OtherDetailsAnotherFields;
import com.kooloco.model.OtherDetailsFieldsSelect;
import com.kooloco.model.ProfileStatus;
import com.kooloco.model.RecentChat;
import com.kooloco.model.Review;
import com.kooloco.model.SchduleDashboard;
import com.kooloco.model.SchedulePrice;
import com.kooloco.model.Service;
import com.kooloco.model.ServiceType;
import com.kooloco.model.Sport;
import com.kooloco.model.SportActivity;
import com.kooloco.model.SportPriceRules;
import com.kooloco.model.SportPriceRulesSport;
import com.kooloco.model.SportSubActivity;
import com.kooloco.model.SubService;
import com.kooloco.model.Tag;
import com.kooloco.model.Time;
import com.kooloco.model.User;
import com.kooloco.model.VisitorBookingNewFlow;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by hlink44 on 19/9/17.
 */

public class Temp {

    static List<LocalImage> localImageSlider;

    static List<Service> serviceDrwable;

    static List<ServiceType> serviceTypes;

    static List<CertificateInfo> certificateInfos;

    static List<CertificateInfo> achivmentInfo;

    static List<Review> reviews;

    static List<OrgLocal> orgLocals = new ArrayList<>();

    static List<OrgImage> orgImages;

    static List<SubService> subServices;

    static List<Activities> activities;

    static List<Time> times;

    static List<Duration> durations;

    static List<Card> cards;

    static List<Order> ordersPending;

    static List<Order> ordersComplate;

    static List<Invite> invites;

    static List<Notification> notifications;

    static List<Sport> sports;

    static List<RecentChat> recentChats;

    static List<Favorites> favorites;

    static List<HelpAndFaq> helpAndFaqs;

    static List<AddImages> addImages;

    static List<SportActivity> sportActivities;

    static List<CheckSportEquipment> checkSportEquipments;


    static List<Month> months;

    static List<EarningRate> earningRates;

    static List<EarningMonth> earningMonths;

    static List<AllMonth> allMonths;


    static List<Notification> notificationsLocal;

    static List<DisableSport> disableSports;

    public static List<LocalImage> getLocalImageSlider() {
        if (localImageSlider == null) {
            localImageSlider = new ArrayList<>();

            LocalImage localImage = new LocalImage();
            localImage.setLocalImage("android.resource://com.kooloco/drawable/temp_1");

            LocalImage localImage1 = new LocalImage();
            localImage1.setLocalImage("android.resource://com.kooloco/drawable/temp_2");

            LocalImage localImage2 = new LocalImage();
            localImage2.setLocalImage("android.resource://com.kooloco/drawable/temp_3");

            LocalImage localImage3 = new LocalImage();
            localImage3.setLocalImage("android.resource://com.kooloco/drawable/temp_4");

            localImageSlider.add(localImage);
            localImageSlider.add(localImage1);
            localImageSlider.add(localImage2);
            localImageSlider.add(localImage3);

        }
        return localImageSlider;
    }

    public static List<Service> getLocalServices() {
        if (serviceDrwable == null) {
            serviceDrwable = new ArrayList<>();

            Service service = new Service();
            service.setServiceImage("android.resource://com.kooloco/drawable/home_climbiln");
            service.setName("Climbing");
            service.setSubServices(getSubService());

            Service service1 = new Service();
            service1.setServiceImage("android.resource://com.kooloco/drawable/home_hiking");
            service1.setName("Hiking");
            service1.setSubServices(getSubService());

            Service service2 = new Service();
            service2.setServiceImage("android.resource://com.kooloco/drawable/home_skate");
            service2.setName("Biking");
            service2.setSubServices(getSubService());

            Service service3 = new Service();
            service3.setServiceImage("android.resource://com.kooloco/drawable/home_skiing");
            service3.setName("Skiing");
            service3.setSubServices(getSubService());

            Service service4 = new Service();
            service4.setServiceImage("android.resource://com.kooloco/drawable/home_surfing");
            service4.setName("Surfing");
            service4.setSubServices(getSubService());

            Service service5 = new Service();
            service5.setServiceImage("android.resource://com.kooloco/drawable/home_map_snow");
            service5.setName("Snow");
            service5.setSubServices(getSubService());

            Service service6 = new Service();
            service6.setServiceImage("android.resource://com.kooloco/drawable/home_mountainering");
            service6.setName("Mountaineering");
            service6.setSubServices(getSubService());

            serviceDrwable.add(service);
            serviceDrwable.add(service1);
            serviceDrwable.add(service2);
            serviceDrwable.add(service3);
            serviceDrwable.add(service4);
            serviceDrwable.add(service5);
            serviceDrwable.add(service6);

        }
        return serviceDrwable;
    }

    public static List<SubService> getSubService() {
        if (subServices == null) {
            subServices = new ArrayList<>();

            SubService subService = new SubService();
            subService.setName("Slop");

            SubService subService1 = new SubService();
            subService1.setName("Freeride");

            SubService subService2 = new SubService();
            subService2.setName("Backcountry");

            SubService subService3 = new SubService();
            subService3.setName("Freestyle");

            subServices.add(subService);
            subServices.add(subService1);
            subServices.add(subService2);
            subServices.add(subService3);

        }
        return subServices;
    }


    public static List<Service> getLocalServices4() {
        List<Service> services = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            services.add(serviceDrwable.get(i));
        }
        return services;
    }


    public static List<ServiceType> getServiceType() {
        if (serviceTypes == null) {
            serviceTypes = new ArrayList<>();

            ServiceType serviceType = new ServiceType();
            serviceType.setServicePrice("$30");
            serviceType.setServiceName("Session");
            serviceType.setServicePriceAs("/hr");
            serviceType.setDesc("Just share a session with a local in his favorite spot");

            ServiceType serviceType1 = new ServiceType();
            serviceType1.setServicePrice("$45");
            serviceType1.setServiceName("Lesson");
            serviceType1.setServicePriceAs("/hr");
            serviceType1.setDesc("Learn and improve your skills with the local");

            ServiceType serviceType2 = new ServiceType();
            serviceType2.setServicePrice("$74");
            serviceType2.setServiceName("Discoveries");
            serviceType2.setServicePriceAs("/hr");
            serviceType2.setDesc("Discover the best secret spots with the local");


            serviceTypes.add(serviceType);
            serviceTypes.add(serviceType1);
            serviceTypes.add(serviceType2);

        }
        return serviceTypes;
    }

    public static List<CertificateInfo> getCertificates() {
        if (certificateInfos == null) {
            certificateInfos = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                CertificateInfo certificateInfo = new CertificateInfo();
                certificateInfo.setImageUrl("android.resource://com.kooloco/drawable/temp_certificat");

                if (i == 0) {
                    certificateInfo.setName("Biking Certificate");

                } else if (i == 1) {

                    certificateInfo.setName("Skiing Certificate");
                } else {

                    certificateInfo.setName("Bike Riding Certificate");
                }

                certificateInfo.setDesc("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n\nLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n\nLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n\n");
                certificateInfos.add(certificateInfo);
            }
        }
        return certificateInfos;
    }

    public static List<CertificateInfo> getAchivments() {
        if (achivmentInfo == null) {
            achivmentInfo = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                CertificateInfo certificateInfo = new CertificateInfo();
                certificateInfo.setImageUrl("android.resource://com.kooloco/drawable/temp_certificat");
                if (i == 0) {
                    certificateInfo.setName("Biking Achievement");

                } else if (i == 1) {

                    certificateInfo.setName("Skiing Achievement");
                } else {

                    certificateInfo.setName("Biking Achievement");
                }
                certificateInfo.setDesc("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n\nLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n\nLorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.\n\n");
                achivmentInfo.add(certificateInfo);
            }
        }
        return achivmentInfo;
    }

    public static List<Review> getReviews() {
        if (reviews == null) {
            reviews = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Review review = new Review();
                review.setImageUrl("android.resource://com.kooloco/drawable/user_round");
                review.setReview("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");
                review.setDate("2018-02-12 10:30:00");
                review.setName("Kathleen Anderson");

                review.setFirstname("Kathleen");
                review.setLastname("Anderson");
                review.setRate("4.5");
                review.setDesc("3");
                review.setInsertDate("2018-02-12 10:30:00");
                reviews.add(review);
            }
        }
        return reviews;
    }

    public static List<OrgImage> getOrgImage() {
        if (orgImages == null) {
            orgImages = new ArrayList<>();

            OrgImage orgImage = new OrgImage();
            orgImage.setImageUrl("android.resource://com.kooloco/drawable/temp_1");

            OrgImage orgImage1 = new OrgImage();
            orgImage1.setImageUrl("android.resource://com.kooloco/drawable/temp_2");

            OrgImage orgImage2 = new OrgImage();
            orgImage2.setImageUrl("android.resource://com.kooloco/drawable/temp_3");

            OrgImage orgImage3 = new OrgImage();
            orgImage3.setImageUrl("android.resource://com.kooloco/drawable/temp_4");

            orgImages.add(orgImage);
            orgImages.add(orgImage1);
            orgImages.add(orgImage2);
            orgImages.add(orgImage3);

        }
        return orgImages;
    }

    public static List<OrgLocal> getOrgLocals() {
        orgLocals = new ArrayList<>();

        OrgLocal orgLocal = new OrgLocal();
        orgLocal.setName("Angela Clore");
        orgLocal.setImageUrl("android.resource://com.kooloco/drawable/user_round");

        OrgLocal orgLocal1 = new OrgLocal();
        orgLocal1.setName("Jogn michel");
        orgLocal1.setImageUrl("android.resource://com.kooloco/drawable/user_round");


        orgLocals.add(orgLocal);
        orgLocals.add(orgLocal1);

        return orgLocals;
    }

    public static List<Activities> getActivites() {
        if (activities == null) {
            activities = new ArrayList<>();

            Activities activitie = new Activities();
            activitie.setName("Session");
            activitie.setImageUrl("android.resource://com.kooloco/drawable/sport_activity_session");
            activitie.setDesc("Just share a session with a local in his favorite spot");

            Activities activitie1 = new Activities();
            activitie1.setName("Lesson");
            activitie1.setImageUrl("android.resource://com.kooloco/drawable/sport_activity_lesson");
            activitie1.setDesc("Learn and improve your skills with the local");

            Activities activitie2 = new Activities();
            activitie2.setName("Discovery");
            activitie2.setImageUrl("android.resource://com.kooloco/drawable/sport_activity_discovery");
            activitie2.setDesc("Discover the best secret spots with the local");

            activities.add(activitie);
            activities.add(activitie1);
            activities.add(activitie2);

        }
        return activities;
    }

    public static List<Time> getTimes() {
        if (times == null) {
            times = new ArrayList<>();

            Time time = new Time();
            time.setTime("08:00 am");
            time.setIsDisable("0");
            time.setIsVisible("0");

            Time time1 = new Time();
            time1.setTime("08:30 am");
            time1.setIsDisable("0");
            time1.setIsVisible("0");

            Time time2 = new Time();
            time2.setTime("09:00 am");
            time2.setIsDisable("0");
            time2.setIsVisible("0");

            Time time3 = new Time();
            time3.setTime("09:30 am");
            time3.setIsDisable("0");
            time3.setIsVisible("0");

            Time time4 = new Time();
            time4.setTime("10:00 am");
            time4.setIsDisable("0");
            time4.setIsVisible("1");

            Time time5 = new Time();
            time5.setTime("10:30 am");
            time5.setIsDisable("0");
            time5.setIsVisible("1");

            Time time6 = new Time();
            time6.setTime("11:00 am");
            time6.setIsDisable("1");
            time6.setIsVisible("0");

            Time time7 = new Time();
            time7.setTime("11:30 am");
            time7.setIsDisable("1");
            time7.setIsVisible("0");


            Time time8 = new Time();
            time8.setTime("12:00 pm");
            time8.setIsDisable("0");
            time8.setIsVisible("0");

            Time time9 = new Time();
            time9.setTime("12:30 pm");
            time9.setIsDisable("0");
            time9.setIsVisible("0");

            Time time10 = new Time();
            time10.setTime("01:00 pm");
            time10.setIsDisable("0");
            time10.setIsVisible("1");


            times.add(time);
            times.add(time1);
            times.add(time2);
            times.add(time3);

            times.add(time4);
            times.add(time5);
            times.add(time6);
            times.add(time7);

            times.add(time8);
            times.add(time9);
            times.add(time10);

        }
        return times;
    }

    public static List<Duration> getDurations() {
        if (durations == null) {
            durations = new ArrayList<>();

            Duration duration = new Duration();
            duration.setDuration("1 Hour");

            Duration duration1 = new Duration();
            duration1.setDuration("2 Hour");

            Duration duration2 = new Duration();
            duration2.setDuration("3 Hour");

            Duration duration3 = new Duration();
            duration3.setDuration("4 Hour");


            durations.add(duration);
            durations.add(duration1);
            durations.add(duration2);
            durations.add(duration3);

        }
        return durations;
    }


    public static List<Card> getCards() {
        if (cards == null) {
            cards = new ArrayList<>();

            Card card = new Card();
            card.setImage("android.resource://com.kooloco/drawable/payment_visa");
            card.setCardNumber("XXXX XXXX XXXX 1234");

            Card card1 = new Card();
            card1.setImage("android.resource://com.kooloco/drawable/payment_mestro");
            card1.setCardNumber("XXXX XXXX XXXX 5678");

            Card card2 = new Card();
            card2.setImage("android.resource://com.kooloco/drawable/payment_mestro");
            card2.setCardNumber("XXXX XXXX XXXX 1234");

            cards.add(card);
            cards.add(card1);
            cards.add(card2);

        }

        return cards;
    }

    public static List<Order> getOrderPending() {
        if (ordersPending == null) {
            ordersPending = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Order order = new Order();
              /*  order.setImageUrl("android.resource://com.kooloco/drawable/user_round");
                order.setName("Doris Cadiz");
                order.setRating(4.5f);
                order.setMonth("Jun");

                order.setDay("10");
                order.setServiceType("Discoveries");
                order.setStatus(0);

                order.setServiceName("Skiing : Freeride");
                order.setServicePrice("$50");
                order.setLocationAddress("Hood Avenue San Diego, CA 9210");
*/
                ordersPending.add(order);

            }

        }

        return ordersPending;
    }

    public static List<Order> getOrdersComplate() {
        if (ordersComplate == null) {
            ordersComplate = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Order order = new Order();
     /*           order.setImageUrl("android.resource://com.kooloco/drawable/user_round");
                order.setName("Doris Cadiz");
                order.setRating(4.5f);
                order.setMonth("Jun");

                order.setDay("10");
                order.setServiceType("Discoveries");
                order.setStatus(1);

                order.setServiceName("Skiing : Freeride");
                order.setServicePrice("$50");
                order.setLocationAddress("Hood Avenue San Diego, CA 9210");*/

                ordersComplate.add(order);

            }

        }

        return ordersComplate;
    }

    public static List<Invite> getInvites() {
        if (invites == null) {
            invites = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Invite invite = new Invite();

                invite.setImageUrl("android.resource://com.kooloco/drawable/user_round");

                if (i == 0) {
                    invite.setName("Jogn Micheal");

                } else if (i % 2 == 0) {
                    invite.setName("Jogn Micheal");

                } else if (i % 3 == 0) {
                    invite.setName("Lynda Fraser");
                } else if (i % 5 == 0) {
                    invite.setName("Manuel Duff");
                } else {
                    invite.setName("Grady McNeil");
                }

                invites.add(invite);
            }

        }

        return invites;
    }

    public static List<Notification> getNotifications() {
        if (notifications == null) {
            notifications = new ArrayList<>();

            Notification notification = new Notification();
            notification.setTitle("Local name has notify on meet location for your appointment");
            notification.setSubTitle("Lorem ipsum dolor sit amet, consectetur..");
            notification.setTime("1 hr ago");
            notification.setStatus(0);

            Notification notification1 = new Notification();
            notification1.setTitle("Duration should be 4 hours.");
            notification1.setSubTitle("Lorem ipsum dolor sit amet, consectetur..");
            notification1.setTime("1 hr ago");
            notification1.setStatus(0);

            Notification notification2 = new Notification();
            notification2.setTitle("Local name has accept your appointment");
            notification2.setSubTitle("");
            notification2.setTime("1 hr ago");
            notification2.setStatus(2);


            Notification notification3 = new Notification();
            notification3.setTitle("Angela clore sent you your objection replay");
            notification3.setSubTitle("You will receive 50% discount and $50 flat rate.");
            notification3.setTime("1 hr ago");
            notification3.setStatus(1);

            notifications.add(notification);
            notifications.add(notification1);
            notifications.add(notification2);
            notifications.add(notification3);

        }

        return notifications;
    }


    public static List<Sport> getSports() {
        if (sports == null) {
            sports = new ArrayList<>();

            Sport sport = new Sport();
            sport.setName("Skis");

            Sport sport1 = new Sport();
            sport1.setName("Sticks");

            Sport sport2 = new Sport();
            sport2.setName("Goggles");

            Sport sport3 = new Sport();
            sport3.setName("Helmets & Beanies");

            sports.add(sport);
            sports.add(sport1);
            sports.add(sport2);
            sports.add(sport3);

        }

        return sports;
    }


    public static List<Sport> getSportsNew() {

        sports = new ArrayList<>();


        Sport sport = new Sport();
        sport.setName("Snowboard");

        Sport sport1 = new Sport();
        sport1.setName("Wax");

        Sport sport2 = new Sport();
        sport2.setName("Sunscreen");

        Sport sport3 = new Sport();
        sport3.setName("Custom 1");

        Sport sport4 = new Sport();
        sport4.setName("Custom 2");


        sports.add(sport);
        sports.add(sport1);
        sports.add(sport2);
        sports.add(sport3);
        sports.add(sport4);


        return sports;
    }

    public static List<RecentChat> getRecentChats() {
        if (recentChats == null) {
            recentChats = new ArrayList<>();

            RecentChat recentChat = new RecentChat();
            recentChat.setImageUrl("android.resource://com.kooloco/drawable/user_round");
            recentChat.setName("Doris Cadiz");
            recentChat.setMessage("Hello, how are you?");
            recentChat.setTime("Now");
            recentChat.setLive(true);

            RecentChat recentChat1 = new RecentChat();
            recentChat1.setImageUrl("android.resource://com.kooloco/drawable/user_round");
            recentChat1.setName("Keith Grimes");
            recentChat1.setMessage("Assimilant aliquando ducunt ad clemens devatio.");
            recentChat1.setTime("Now");
            recentChat1.setLive(true);

            RecentChat recentChat2 = new RecentChat();
            recentChat2.setImageUrl("android.resource://com.kooloco/drawable/user_round");
            recentChat2.setName("William Ellis");
            recentChat2.setMessage("Hello");
            recentChat2.setTime("1 min ago");
            recentChat2.setLive(false);

            RecentChat recentChat3 = new RecentChat();
            recentChat3.setImageUrl("android.resource://com.kooloco/drawable/user_round");
            recentChat3.setName("William Ward");
            recentChat3.setMessage("Hello, how are you?");
            recentChat3.setTime("2 min ago");
            recentChat3.setLive(false);

            RecentChat recentChat4 = new RecentChat();
            recentChat4.setImageUrl("android.resource://com.kooloco/drawable/user_round");
            recentChat4.setName("Shirley Ceja");
            recentChat4.setMessage("Hello");
            recentChat4.setTime("2 hr ago");
            recentChat4.setLive(false);

            RecentChat recentChat5 = new RecentChat();
            recentChat5.setImageUrl("android.resource://com.kooloco/drawable/user_round");
            recentChat5.setName("Kathy Ward");
            recentChat5.setMessage("Hello, how are you?");
            recentChat5.setTime("4 hr ago");
            recentChat5.setLive(false);

            RecentChat recentChat6 = new RecentChat();
            recentChat6.setImageUrl("android.resource://com.kooloco/drawable/user_round");
            recentChat6.setName("Chris Simpson");
            recentChat6.setMessage("Hello");
            recentChat6.setTime("6 hr ago");
            recentChat6.setLive(false);

            RecentChat recentChat7 = new RecentChat();
            recentChat7.setImageUrl("android.resource://com.kooloco/drawable/user_round");
            recentChat7.setName("Chris Ward");
            recentChat7.setMessage("Hilotae ");
            recentChat7.setTime("6 hr ago");
            recentChat7.setLive(false);

            RecentChat recentChat8 = new RecentChat();
            recentChat8.setImageUrl("android.resource://com.kooloco/drawable/user_round");
            recentChat8.setName("Chris Grimes");
            recentChat8.setMessage("Hercle, guttus nobilis!.");
            recentChat8.setTime("8 hr ago");
            recentChat8.setLive(false);


            recentChats.add(recentChat);
            recentChats.add(recentChat1);
            recentChats.add(recentChat2);
            recentChats.add(recentChat3);
            recentChats.add(recentChat4);
            recentChats.add(recentChat5);
            recentChats.add(recentChat6);
            recentChats.add(recentChat7);
            recentChats.add(recentChat8);

        }

        return recentChats;
    }


    public static List<Favorites> getFavorites() {
        if (favorites == null) {
            favorites = new ArrayList<>();
            for (int i = 0; i < 10; i++) {

                Favorites favorites1 = new Favorites();

                favorites1.setImageUrl("android.resource://com.kooloco/drawable/user_round");

                if (i == 0) {
                    favorites1.setName("Jogn Micheal");
                } else if (i % 2 == 0) {
                    favorites1.setName("Jogn Micheal");
                } else if (i % 3 == 0) {
                    favorites1.setName("Lynda Fraser");
                } else if (i % 5 == 0) {
                    favorites1.setName("Manuel Duff");
                } else {
                    favorites1.setName("Grady McNeil");
                }

                favorites1.setDay("10");
                favorites1.setMonth("Jun");

                favorites1.setServiceType("Discoveries");

                favorites1.setService("Skiing : Freeride");
                favorites1.setPrice("$50");
                favorites1.setLocation("Hood Avenue San Diego, CA 9210");
                favorites1.setDistance("8 km");


                favorites1.setFav(true);

                favorites.add(favorites1);
            }

        }

        return favorites;
    }

    public static List<HelpAndFaq> getHelpAndFaqs() {
        if (helpAndFaqs == null) {
            helpAndFaqs = new ArrayList<>();
            HelpAndFaq helpAndFaq = new HelpAndFaq();
            helpAndFaq.setQ("How can I track my orders & payment?");
            helpAndFaq.setAns("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.");

            HelpAndFaq helpAndFaq1 = new HelpAndFaq();
            helpAndFaq1.setQ("Why do we use it?");
            helpAndFaq1.setAns("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.");

            HelpAndFaq helpAndFaq2 = new HelpAndFaq();
            helpAndFaq2.setQ("Where can I get some?");
            helpAndFaq2.setAns("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.");

            HelpAndFaq helpAndFaq3 = new HelpAndFaq();
            helpAndFaq3.setQ("In placerat quam vel malesuada loborties?");
            helpAndFaq3.setAns("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.");

            HelpAndFaq helpAndFaq4 = new HelpAndFaq();
            helpAndFaq4.setQ("Cras loborties nisi elementum?");
            helpAndFaq4.setAns("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.");

            HelpAndFaq helpAndFaq5 = new HelpAndFaq();
            helpAndFaq5.setQ("Reservation Requests");
            helpAndFaq5.setAns("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.");

            helpAndFaqs.add(helpAndFaq);
            helpAndFaqs.add(helpAndFaq1);
            helpAndFaqs.add(helpAndFaq2);
            helpAndFaqs.add(helpAndFaq3);
            helpAndFaqs.add(helpAndFaq4);
            helpAndFaqs.add(helpAndFaq5);

        }

        return helpAndFaqs;
    }

    public static List<String> getFilSports() {
        List<String> strings = new ArrayList<>();

        // strings.add("Filter by sport");
        strings.add("Snowboarding");
        strings.add("Skiing");
        strings.add("Climbing");
        strings.add("Mountaining");

        return strings;
    }

    public static List<String> getFilTime() {
        List<String> strings = new ArrayList<>();

        //strings.add("Filter by Start Time");
        strings.add("9:00 am");
        strings.add("10:00 am");
        strings.add("11:00 am");
        strings.add("12:00 am");

        return strings;
    }

    public static List<String> getFilTimeNew() {
        List<String> strings = new ArrayList<>();

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        strings.add("00:00:00");
        strings.add("00:30:00");

        strings.add("01:00:00");
        strings.add("01:30:00");

        strings.add("02:00:00");
        strings.add("02:30:00");

        strings.add("03:00:00");
        strings.add("03:30:00");

        strings.add("04:00:00");
        strings.add("04:30:00");

        strings.add("05:00:00");
        strings.add("05:30:00");

        strings.add("06:00:00");
        strings.add("06:30:00");

        strings.add("07:00:00");
        strings.add("07:30:00");

        strings.add("08:00:00");
        strings.add("08:30:00");

        strings.add("09:00:00");
        strings.add("09:30:00");

        strings.add("10:00:00");
        strings.add("10:30:00");

        strings.add("11:00:00");
        strings.add("11:30:00");

        strings.add("12:00:00");
        strings.add("12:30:00");

        strings.add("13:00:00");
        strings.add("13:30:00");

        strings.add("14:00:00");
        strings.add("14:30:00");

        strings.add("15:00:00");
        strings.add("15:30:00");

        strings.add("16:00:00");
        strings.add("16:30:00");

        strings.add("17:00:00");
        strings.add("17:30:00");

        strings.add("18:00:00");
        strings.add("18:30:00");

        strings.add("19:00:00");
        strings.add("19:30:00");

        strings.add("20:00:00");
        strings.add("20:30:00");

        strings.add("21:00:00");
        strings.add("21:30:00");

        strings.add("22:00:00");
        strings.add("22:30:00");

        strings.add("23:00:00");
        strings.add("23:30:00");

        return strings;
    }

    public static List<String> getFilActivitys() {
        List<String> strings = new ArrayList<>();

        // strings.add("Filter by activity's type");
        strings.add("Discoveries");
        strings.add("Session");
        strings.add("Lesson");

        return strings;
    }

    public static List<String> getFilLanguage() {
        List<String> strings = new ArrayList<>();

        // strings.add("Filter by local's language");
        strings.add("Spanish");
        strings.add("German");
        strings.add("French");
        strings.add("English");

        return strings;
    }


    public static List<String> getLanguage() {
        List<String> strings = new ArrayList<>();

        strings.add("Spanish");
        strings.add("German");
        strings.add("French");
        strings.add("English");

        return strings;
    }

    public static List<AddImages> getAddImages() {
        if (addImages == null) {
            addImages = new ArrayList<>();

            AddImages localImage = new AddImages();
            localImage.setImageUrl("android.resource://com.kooloco/drawable/temp_1");


            AddImages localImage1 = new AddImages();
            localImage1.setImageUrl("android.resource://com.kooloco/drawable/temp_2");

            addImages.add(localImage);
            addImages.add(localImage1);

        }
        return addImages;
    }

    public static List<String> getAddTags() {
        List<String> strings = new ArrayList<>();
        strings.add("Contest");
        strings.add("Movie");
        strings.add("Photo Shooting");
        strings.add("Memorable Activity");
        strings.add("Sport Record");
        strings.add("Other");
        return strings;
    }

    public static List<SportActivity> getSportActivities() {
        sportActivities = new ArrayList<>();

        SportActivity sportActivity = new SportActivity();
        sportActivity.setName("Surfing");

        SportActivity sportActivity1 = new SportActivity();
        sportActivity1.setName("Skiing");
        sportActivity1.setExpand(true);
        sportActivity1.setSportSubActivities(sportSubActivities());

        SportActivity sportActivity2 = new SportActivity();
        sportActivity2.setName("Snowboarding");
        sportActivity2.setExpand(true);
        sportActivity2.setSportSubActivities(sportSubActivities());

        SportActivity sportActivity3 = new SportActivity();
        sportActivity3.setName("Biking");
        sportActivity3.setExpand(true);
        sportActivity3.setSportSubActivities(sportSubActivities());

        SportActivity sportActivity4 = new SportActivity();
        sportActivity4.setName("Skateboarding");

        SportActivity sportActivity5 = new SportActivity();
        sportActivity5.setName("Mountaineering");

        SportActivity sportActivity6 = new SportActivity();
        sportActivity6.setName("Hiking/Trekking");

        SportActivity sportActivity7 = new SportActivity();
        sportActivity7.setName("Climbing");

        sportActivities.add(sportActivity);
        sportActivities.add(sportActivity1);
        sportActivities.add(sportActivity2);
        sportActivities.add(sportActivity3);
        sportActivities.add(sportActivity4);
        sportActivities.add(sportActivity5);
        sportActivities.add(sportActivity6);
        sportActivities.add(sportActivity7);

        return sportActivities;
    }

    public static List<SportSubActivity> sportSubActivities() {
        List<SportSubActivity> SportSubActivitys = new ArrayList<>();

        SportSubActivity sportSubActivity = new SportSubActivity();
        sportSubActivity.setName("Slop");

        SportSubActivity sportSubActivity1 = new SportSubActivity();
        sportSubActivity1.setName("Freeride");

        SportSubActivity sportSubActivity2 = new SportSubActivity();
        sportSubActivity2.setName("Backcountry");

        SportSubActivity sportSubActivity3 = new SportSubActivity();
        sportSubActivity3.setName("Freestyle");

        SportSubActivitys.add(sportSubActivity);
        SportSubActivitys.add(sportSubActivity1);
        SportSubActivitys.add(sportSubActivity2);
        SportSubActivitys.add(sportSubActivity3);

        return SportSubActivitys;
    }

    public static List<CheckSportEquipment> getCheckSportEquipments() {
        if (checkSportEquipments == null) {
            checkSportEquipments = new ArrayList<>();

            CheckSportEquipment checkSportEquipment = new CheckSportEquipment();
            checkSportEquipment.setName("Skiing");
            checkSportEquipment.setSports(Temp.getSports());

            CheckSportEquipment checkSportEquipment1 = new CheckSportEquipment();
            checkSportEquipment1.setName("Biking");
            checkSportEquipment1.setSports(Temp.getSports());


            checkSportEquipments.add(checkSportEquipment);
            checkSportEquipments.add(checkSportEquipment1);

        }
        return checkSportEquipments;
    }

    public static List<Month> getMonths() {
        months = new ArrayList<>();

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.MONTH, 0);

        for (int i = 0; i < 12; i++) {




            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            SimpleDateFormat month_date_short = new SimpleDateFormat("MMM");

            SimpleDateFormat month_date_number = new SimpleDateFormat("MM");

            SimpleDateFormat month_year = new SimpleDateFormat("yyyy");

            Log.e(":::", "" + i);


            Log.e(":::", cal.getTime().toString());
            Log.e(":::", month_date_short.format(cal.getTime()));
            Log.e(":::", month_date.format(cal.getTime()));

            Month month = new Month();

          /*  if (i == 1) {

                Calendar calnew = Calendar.getInstance();

                calnew.set(Calendar.MONTH, Calendar.FEBRUARY);

                month.setName(month_date_short.format(calnew.getTime()));
                month.setFullName(month_date.format(calnew.getTime()));
                month.setMonthNum(month_date_number.format(calnew.getTime()));

            } else {


            }*/


            month.setName(month_date_short.format(cal.getTime()));
            month.setFullName(month_date.format(cal.getTime()));
            month.setMonthNum(month_date_number.format(cal.getTime()));

            month.setYear(month_year.format(cal.getTime()));

            months.add(month);

            cal.add(Calendar.MONTH, 1);

        }
        return months;
    }


    public static List<EarningRate> getEarningRatings() {
        if (earningRates == null) {
            earningRates = new ArrayList<>();

            EarningRate earningRate = new EarningRate();
            earningRate.setName("Quality - Price");
            earningRate.setImproved("25");
            earningRate.setWrong("3");
            earningRates.add(earningRate);

            earningRate = new EarningRate();
            earningRate.setName("Arrival Time");
            earningRate.setImproved("32");
            earningRate.setWrong("10");
            earningRates.add(earningRate);

            earningRate = new EarningRate();
            earningRate.setName("Professionalism");
            earningRate.setImproved("44");
            earningRate.setWrong("2");
            earningRates.add(earningRate);

            earningRate = new EarningRate();
            earningRate.setName("Guidance");
            earningRate.setImproved("20");
            earningRate.setWrong("0");
            earningRates.add(earningRate);

            earningRate = new EarningRate();
            earningRate.setName("Meeting Spot");
            earningRate.setImproved("5");
            earningRate.setWrong("3");
            earningRates.add(earningRate);

            earningRate = new EarningRate();
            earningRate.setName("Activity Duration");
            earningRate.setImproved("54");
            earningRate.setWrong("6");
            earningRates.add(earningRate);

            earningRate = new EarningRate();
            earningRate.setName("Other");
            earningRate.setImproved("0");
            earningRate.setWrong("3");
            earningRates.add(earningRate);

        }
        return earningRates;
    }


    public static List<EarningMonth> getearningMonths() {
        if (earningMonths == null) {
            earningMonths = new ArrayList<>();
            for (int i = 0; i < 10; i++) {

                EarningMonth favorites1 = new EarningMonth();

                favorites1.setImageUrl("android.resource://com.kooloco/drawable/user_round");

                if (i == 0) {
                    favorites1.setName("Jogn Micheal");
                } else if (i % 2 == 0) {
                    favorites1.setName("Jogn Micheal");
                } else if (i % 3 == 0) {
                    favorites1.setName("Lynda Fraser");
                } else if (i % 5 == 0) {
                    favorites1.setName("Manuel Duff");
                } else {
                    favorites1.setName("Grady McNeil");
                }

                favorites1.setDay("10");
                favorites1.setMonth("Jun");

                favorites1.setServiceType("Discoveries");

                favorites1.setService("Skiing : Freeride");
                favorites1.setPrice("USD 50");
                favorites1.setLocation("Hood Avenue San Diego, CA 9210");
                favorites1.setDistance("8 km");


                favorites1.setFav(true);

                earningMonths.add(favorites1);
            }

        }

        return earningMonths;
    }

    public static List<AllMonth> getAllMonths() {
        allMonths = new ArrayList<>();
        for (int i = 0; i < getMonths().size(); i++) {
            AllMonth allMonth = new AllMonth();

            allMonth.setPos(i);
            allMonth.setMonthName(Temp.getMonths().get(i).getFullName());
            allMonth.setShortName(Temp.getMonths().get(i).getName());
            allMonth.setMonthNum(Temp.getMonths().get(i).getMonthNum());
            allMonth.setYear(Temp.getMonths().get(i).getYear());

            allMonth.setPrice("0.00");
            allMonths.add(allMonth);
        }
        return allMonths;
    }

    public static List<Notification> getNotificationsLocal() {
        if (notificationsLocal == null) {
            notificationsLocal = new ArrayList<>();

            Notification notification = new Notification();
            notification.setTitle("Angela Clore Sent you an objection");
            notification.setSubTitle("Lorem ipsum dolor sit amet, consectetur..");
            notification.setTime("Now");
            notification.setStatus(1);

            Notification notification1 = new Notification();
            notification1.setTitle("Duration should be 4 hours.");
            notification1.setSubTitle("Lorem ipsum dolor sit amet, consectetur..");
            notification1.setTime("1 hr ago");
            notification1.setStatus(0);

            Notification notification2 = new Notification();
            notification2.setTitle("Devid Martin has notify on meet location for your appointment");
            notification2.setSubTitle("Lorem ipsum dolor sit amet, consectetur..");
            notification2.setTime("2 hr ago");
            notification2.setStatus(0);


            Notification notification3 = new Notification();
            notification3.setTitle("Angela Clore has accept your appointment");
            notification3.setSubTitle("Lorem ipsum dolor sit amet, consectetur..");
            notification3.setTime("3 hr ago");
            notification3.setStatus(0);


            notificationsLocal.add(notification);
            notificationsLocal.add(notification1);
            notificationsLocal.add(notification2);
            notificationsLocal.add(notification3);


            notification3 = new Notification();
            notification3.setTitle("Local name has notify on meet location for your appointment");
            notification3.setSubTitle("Lorem ipsum dolor sit amet, consectetur..");
            notification3.setTime("4 hr ago");
            notification3.setStatus(0);
            notificationsLocal.add(notification3);

            notification3 = new Notification();
            notification3.setTitle("giving information on its origins, as well as a random Lipsum");
            notification3.setSubTitle("Lorem ipsum dolor sit amet, consectetur..");
            notification3.setTime("4 hr ago");
            notification3.setStatus(0);
            notificationsLocal.add(notification3);

            notification3 = new Notification();
            notification3.setTitle("Angela Clore has accept your appointment");
            notification3.setSubTitle("Lorem ipsum dolor sit amet, consectetur..");
            notification3.setTime("3 hr ago");
            notification3.setStatus(0);
            notificationsLocal.add(notification3);

        }

        return notificationsLocal;
    }


    public static List<String> getCYOSports() {
        List<String> strings = new ArrayList<>();

        strings.add("Snowboarding");
        strings.add("Skiing");
        strings.add("Climbing");
        strings.add("Mountaining");

        return strings;
    }

    public static List<String> getCYOActivitys() {
        List<String> strings = new ArrayList<>();

        strings.add("Discoveries");
        strings.add("Session");
        strings.add("Lesson");

        return strings;
    }

    public static List<DisableSport> getDisableSport() {
        if (disableSports == null) {
            disableSports = new ArrayList<>();

            DisableSport disableSport = new DisableSport();
            disableSport.setName("Surfing");
            disableSports.add(disableSport);

            disableSport = new DisableSport();
            disableSport.setName("Skiing");
            disableSports.add(disableSport);

            disableSport = new DisableSport();
            disableSport.setName("Snowboarding");
            disableSports.add(disableSport);

            disableSport = new DisableSport();
            disableSport.setName("Biking");
            disableSports.add(disableSport);

            disableSport = new DisableSport();
            disableSport.setName("Skateboarding");
            disableSports.add(disableSport);

            disableSport = new DisableSport();
            disableSport.setName("Mountaineering");
            disableSports.add(disableSport);

            disableSport = new DisableSport();
            disableSport.setName("Hiking/Trekking");
            disableSports.add(disableSport);

            disableSport = new DisableSport();
            disableSport.setName("Climbing");
            disableSports.add(disableSport);

        }
        return disableSports;
    }


    public static List<OrgLocal> getEditOrgLocals() {
        List<OrgLocal> orgLocalsD = new ArrayList<>();

        OrgLocal orgLocal = new OrgLocal();
        orgLocal.setName("Angela Clore");
        orgLocal.setImageUrl("android.resource://com.kooloco/drawable/user_round");
        orgLocal.setPrevSelect(true);
        orgLocalsD.add(orgLocal);

        OrgLocal orgLocal1 = new OrgLocal();
        orgLocal1.setName("Jogn michel");
        orgLocal1.setImageUrl("android.resource://com.kooloco/drawable/user_round");
        orgLocalsD.add(orgLocal1);


        orgLocal1 = new OrgLocal();
        orgLocal1.setName("Petey Cruiser");
        orgLocal1.setImageUrl("android.resource://com.kooloco/drawable/user_round");
        orgLocal1.setPrevSelect(true);
        orgLocalsD.add(orgLocal1);


        orgLocal1 = new OrgLocal();
        orgLocal1.setName("Anna Mull");
        orgLocal1.setImageUrl("android.resource://com.kooloco/drawable/user_round");
        orgLocalsD.add(orgLocal1);


        return orgLocalsD;
    }

    public static List<String> getSubServiceN() {

        List<String> strings = new ArrayList<>();

        strings.add("Slop");
        strings.add("Freeride");
        strings.add("Backcountry");
        strings.add("Freestyle");

        return strings;
    }

    public static List<Tag> getTags() {
        List<Tag> tags = new ArrayList<>();

        Tag tag = new Tag();

        for (int i = 0; i < Temp.getAddTags().size(); i++) {
            tag.setId("" + i);
            tag.setName(Temp.getAddTags().get(i));
            tag.setTag("#" + Temp.getAddTags().get(i));
            tag.setSelect(false);
            tag.setIsActive("1");
            tag.setInsertdate("2017-11-15 17:11:37");
            tags.add(tag);
        }
        return tags;
    }

    public static User getUser() {
        User user = new User();
        user.setId("4");
        user.setToken("ohhOquLE2d9orurN6FuRF8QqXGCX7qswHJk8e5MhjWFL");
        user.setSocialId("");
        user.setFirstname("Doris");
        user.setLastname("Candiz");
        user.setEmail("doriscandiz@gmail.com");
        user.setSignupStep("0");
        user.setIntroYourSelf("");
        user.setAddress("");
        user.setPhone("");
        user.setPhoneDoc("");
        user.setLatitude("23.123554");
        user.setLongitude("72.1235465");
        user.setSignupType("S");
        user.setDeviceId("123456");
        user.setDeviceType("A");
        user.setRole("V");
        user.setAppLangId("0");
        user.setProfileImage("android.resource://com.kooloco/drawable/user_round");
        user.setProfileImageThumb("android.resource://com.kooloco/drawable/user_round");
        user.setRate("4");
        user.setAppNotification("1");
        user.setMailNotification("1");
        user.setReferralCode("1");
        return user;
    }

    public static LanguageResponse getLanguages() {
        LanguageResponse languageResponse = new LanguageResponse();

        List<Language> appLanguages = new ArrayList<>();

        List<Language> speakLanguages = new ArrayList<>();

        for (int i = 0; i < Temp.getLanguage().size(); i++) {
            Language language = new Language();
            language.setId("" + i);
            language.setName(Temp.getLanguage().get(i));
            language.setIsActive("1");
            language.setInsertdate("00-00-0000 00:00:00");
            appLanguages.add(language);
            speakLanguages.add(language);
        }


        languageResponse.setAppLanguage(appLanguages);
        languageResponse.setSpeakingLanguages(speakLanguages);
        return languageResponse;
    }

    public static List<EquipmentResponse> getEquipmentsResponse() {
        List<EquipmentResponse> equipmentResponses = new ArrayList<>();

        EquipmentResponse equipmentResponse = new EquipmentResponse();

        equipmentResponse.setId("1");
        equipmentResponse.setName("Surfing");
        equipmentResponse.setUserId("1");
        equipmentResponse.setParentId("1");
        equipmentResponse.setIsAdminApprove("1");
        equipmentResponse.setIsActive("1");
        equipmentResponse.setEquipments(Temp.getEquipment());

        equipmentResponses.add(equipmentResponse);

        equipmentResponse.setId("2");
        equipmentResponse.setName("Skiing");
        equipmentResponse.setUserId("1");
        equipmentResponse.setParentId("1");
        equipmentResponse.setIsAdminApprove("1");
        equipmentResponse.setIsActive("1");
        equipmentResponse.setEquipments(Temp.getEquipment());

        equipmentResponses.add(equipmentResponse);


        return equipmentResponses;
    }


    public static List<Equipment> getEquipment() {
        List<Equipment> equipments = new ArrayList<>();

        Equipment equipment = new Equipment();

        equipment.setId("1");
        equipment.setName("Boots");
        equipment.setSportId("1");
        //equipment.setUserId("1");
        equipment.setIsAdminApprove("1");
        equipment.setIsActive("1");
        equipment.setInsertdate("0000-00-00 00:00:00");
        equipment.setIsSelected("1");

        equipments.add(equipment);


        equipment = new Equipment();
        equipment.setId("2");
        equipment.setName("Snowboard");
        equipment.setSportId("1");
        //equipment.setUserId("1");
        equipment.setIsAdminApprove("1");
        equipment.setIsActive("1");
        equipment.setInsertdate("0000-00-00 00:00:00");
        equipment.setIsSelected("1");

        equipments.add(equipment);

        equipment = new Equipment();
        equipment.setId("3");
        equipment.setName("Wax");
        equipment.setSportId("1");
        // equipment.setUserId("1");
        equipment.setIsAdminApprove("1");
        equipment.setIsActive("1");
        equipment.setInsertdate("0000-00-00 00:00:00");
        equipment.setIsSelected("1");

        equipments.add(equipment);

        equipment = new Equipment();
        equipment.setId("4");
        equipment.setName("Goggles");
        equipment.setSportId("1");
        //equipment.setUserId("1");
        equipment.setIsAdminApprove("1");
        equipment.setIsActive("1");
        equipment.setInsertdate("0000-00-00 00:00:00");
        equipment.setIsSelected("1");

        equipments.add(equipment);

        equipment = new Equipment();

        equipment.setId("5");
        equipment.setName("Helmet");
        equipment.setSportId("1");
        // equipment.setUserId("1");
        equipment.setIsAdminApprove("1");
        equipment.setIsActive("1");
        equipment.setInsertdate("0000-00-00 00:00:00");
        equipment.setIsSelected("1");

        equipments.add(equipment);


        equipment = new Equipment();
        equipment.setId("6");
        equipment.setName("Sunscreen");
        equipment.setSportId("1");
        // equipment.setUserId("1");
        equipment.setIsAdminApprove("1");
        equipment.setIsActive("1");
        equipment.setInsertdate("0000-00-00 00:00:00");
        equipment.setIsSelected("1");

        equipments.add(equipment);

        return equipments;
    }

    public static List<CancellationPolicy> getCancellationPolicy() {
        List<CancellationPolicy> cancellationPolicys = new ArrayList<>();

        CancellationPolicy cancellationPolicy = new CancellationPolicy();

        cancellationPolicy.setId("1");
        cancellationPolicy.setTitle("Strict Policy");
        cancellationPolicy.setDescription("Cancel up to 30 days before you activity and get a full refund,including all fees, Cancel within 30 days of the activity and get a 50% refund of the total nightly rate, as well as  a full  refund of fees");
        cancellationPolicy.setDuration("24");
        cancellationPolicy.setDurationType("hours");
        cancellationPolicy.setRefundRatio("50.00");
        cancellationPolicy.setIsActive("1");
        cancellationPolicy.setInsertdate("0000-00-00 00:00:00");
        cancellationPolicy.setIsSelected("1");

        cancellationPolicys.add(cancellationPolicy);

        cancellationPolicy.setId("2");
        cancellationPolicy.setTitle("Moderate");
        cancellationPolicy.setDescription("Cancel up to 7 days before you activity and get a full refund,including all fees, Cancel within 7 days of the activity and get a 50% refund of the total nightly rate, as well as  a full  refund of fees");
        cancellationPolicy.setDuration("30");
        cancellationPolicy.setDurationType("days");
        cancellationPolicy.setRefundRatio("50.00");
        cancellationPolicy.setIsActive("1");
        cancellationPolicy.setInsertdate("0000-00-00 00:00:00");
        cancellationPolicy.setIsSelected("0");

        cancellationPolicys.add(cancellationPolicy);

        cancellationPolicy.setId("3");
        cancellationPolicy.setTitle("Kool");
        cancellationPolicy.setDescription("Cancel up to 24 hours before you activity and get a full refund,including all fees, Cancel within 24 hours of the activity and the first night is non-refundable except for fees.");
        cancellationPolicy.setDuration("7");
        cancellationPolicy.setDurationType("days");
        cancellationPolicy.setRefundRatio("10.00");
        cancellationPolicy.setIsActive("1");
        cancellationPolicy.setInsertdate("0000-00-00 00:00:00");
        cancellationPolicy.setIsSelected("0");

        cancellationPolicys.add(cancellationPolicy);

        return cancellationPolicys;
    }

    public static List<SportPriceRules> getSelectSport() {
        List<SportPriceRules> sportPriceRules = new ArrayList<>();

        SportPriceRules sportPriceRule = new SportPriceRules();
        sportPriceRule.setId("1");
        sportPriceRule.setName("Session");
        sportPriceRule.setSports(Temp.getSportRules());
        sportPriceRules.add(sportPriceRule);

        SportPriceRules sportPriceRule1 = new SportPriceRules();
        sportPriceRule1.setId("2");
        sportPriceRule1.setName("Lesson");
        sportPriceRule1.setSports(Temp.getSportRules());

        sportPriceRules.add(sportPriceRule1);

        SportPriceRules sportPriceRule2 = new SportPriceRules();
        sportPriceRule2.setId("3");
        sportPriceRule2.setName("Discoveries");
        sportPriceRule2.setSports(Temp.getSportRules());
        sportPriceRules.add(sportPriceRule2);


        return sportPriceRules;
    }

    public static List<SportPriceRulesSport> getSportRules() {
        List<SportPriceRulesSport> sportPriceRulesSports = new ArrayList<>();

        SportPriceRulesSport sportPriceRulesSport = new SportPriceRulesSport();
        sportPriceRulesSport.setId("1");
        sportPriceRulesSport.setUserId("1");
        sportPriceRulesSport.setName("Surfing");
        sportPriceRulesSport.setParentId("1");
        sportPriceRulesSport.setIsAdminApprove("1");
        sportPriceRulesSport.setIsActive("1");
        sportPriceRulesSport.setIsPrice("0");
        return sportPriceRulesSports;
    }


    public static List<ExpereinceNew> getExpereinceNew() {
        List<ExpereinceNew> expereinceNews = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ExpereinceNew expereinceNew = new ExpereinceNew();
            expereinceNew.setId("0");
            expereinceNew.setTitle("Surf Lesson in the Boro with lunch & all other services");
            expereinceNew.setImage_url("android.resource://com.kooloco/drawable/temp_1");
            expereinceNew.setRate("3");
            expereinceNew.setRateCount("10");
            expereinceNew.setExperience_url("android.resource://com.kooloco/drawable/lesson_new");
            expereinceNew.setLocation("Hossegor, France");
            expereinceNew.setPrice("20.20");

            expereinceNews.add(expereinceNew);

        }
        return expereinceNews;
    }

    public static OtherDetailsAnotherFields otherDetailsAnotherFields() {
        OtherDetailsAnotherFields otherDetailsAnotherField = new OtherDetailsAnotherFields();
        otherDetailsAnotherField.setId("1");
        otherDetailsAnotherField.setTitle("Lorem Ipsum is simply dummy text.");
        otherDetailsAnotherField.setDesc("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        otherDetailsAnotherField.setAddImages(Temp.getAddImages());
        return otherDetailsAnotherField;
    }

    public static SchedulePrice schedulePrice() {
        SchedulePrice schedulePrice = new SchedulePrice();
        schedulePrice.setId("1");
        schedulePrice.setStartTime("11:00:00");
        schedulePrice.setEndTime("13:00:00");
        schedulePrice.setPrice("20.35");
        return schedulePrice;
    }


    public static List<SchedulePrice> schedulePriceDay() {
        List<SchedulePrice> schedulePrices = new ArrayList<>();
        DateFormatSymbols dfs = new DateFormatSymbols();

        Calendar calendar = Calendar.getInstance();

        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        for (int i = 0; i < 7; i++) {

            SimpleDateFormat sdf = new SimpleDateFormat("EEE");
            calendar.set(Calendar.DAY_OF_WEEK, i + 1);

            String dayName = sdf.format(calendar.getTime());

            if (!dayName.isEmpty()) {
                SchedulePrice schedulePrice = new SchedulePrice();
                schedulePrice.setDay(dayName);
                schedulePrices.add(schedulePrice);
            }

        }
        return schedulePrices;
    }

    public static List<SchedulePrice> schedulePriceDayFull() {
        List<SchedulePrice> schedulePrices = new ArrayList<>();
        DateFormatSymbols dfs = new DateFormatSymbols();

        Calendar calendar = Calendar.getInstance();

        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        for (int i = 0; i < 7; i++) {

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            calendar.set(Calendar.DAY_OF_WEEK, i + 1);

            String dayName = sdf.format(calendar.getTime());

            if (!dayName.isEmpty()) {
                SchedulePrice schedulePrice = new SchedulePrice();
                schedulePrice.setDay(dayName);
                schedulePrices.add(schedulePrice);
            }

        }
        return schedulePrices;
    }

    public static EquipmentResponse getEquipmentResponse() {
        EquipmentResponse equipmentResponse = new EquipmentResponse();

        equipmentResponse.setId("13");
        equipmentResponse.setUserId("13");
        equipmentResponse.setParentId("13");
        equipmentResponse.setName("Snowboarding");
        equipmentResponse.setEquipments(Temp.getEquipment());

        return equipmentResponse;
    }


    public static List<ProfileStatus> profileStatuses() {

        List<ProfileStatus> profileStatuses = new ArrayList<>();

        ProfileStatus profileStatus;

        profileStatus = new ProfileStatus();
        profileStatus.setId("1");
        profileStatus.setStep("1");
        profileStatus.setTitle("Details");
        profileStatus.setIsComplete("1");
        profileStatuses.add(profileStatus);

        profileStatus = new ProfileStatus();
        profileStatus.setId("2");
        profileStatus.setStep("2");
        profileStatus.setTitle("Category");
        profileStatus.setIsComplete("1");
        profileStatuses.add(profileStatus);

        profileStatus = new ProfileStatus();
        profileStatus.setId("3");
        profileStatus.setStep("3");
        profileStatus.setTitle("Sport");
        profileStatus.setIsComplete("0");
        profileStatuses.add(profileStatus);

        profileStatus = new ProfileStatus();
        profileStatus.setId("4");
        profileStatus.setStep("4");
        profileStatus.setTitle("Schedule & Price");
        profileStatus.setIsComplete("0");
        profileStatuses.add(profileStatus);

        profileStatus = new ProfileStatus();
        profileStatus.setId("5");
        profileStatus.setStep("5");
        profileStatus.setTitle("Other Details");
        profileStatus.setIsComplete("0");
        profileStatuses.add(profileStatus);

        profileStatus = new ProfileStatus();
        profileStatus.setId("6");
        profileStatus.setStep("6");
        profileStatus.setTitle("Meeting Spot");
        profileStatus.setIsComplete("0");
        profileStatuses.add(profileStatus);

        profileStatus = new ProfileStatus();
        profileStatus.setId("7");
        profileStatus.setStep("7");
        profileStatus.setTitle("Cancellation Policy");
        profileStatus.setIsComplete("0");
        profileStatuses.add(profileStatus);


        return profileStatuses;
    }

    public static ExpDetails getExpDetails() {
        ExpDetails expDetails = new ExpDetails();

        expDetails.setImages(Temp.getLocalImageSlider());
        expDetails.setTitle("Surf Lesson in the Boro with lunch & all other\nservices included");
        expDetails.setRating("3");
        expDetails.setRatingCount("5");
        expDetails.setDuration("2:00 hours");
        expDetails.setCity("Hossegor");
        expDetails.setCountry("France");
        expDetails.setCapacity("10");

        expDetails.setPrice("25.20");

        expDetails.setExpLevel("Level: Beginner, Intermediate");
        expDetails.setExpPerfect("Perfecta for families, Individuals, Group of friends");

        expDetails.setDesc("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum is simply dummy text of the printing and typesetting industry.");

        expDetails.setSportActivity(Temp.getLocalServices().get(0));
        expDetails.setActivities(Temp.getActivites().get(0));

        expDetails.setMettingAddress("Chemin des Anciens-Moulins 8b, 1009 Pully, Suisse");

        expDetails.setMeetingLet("23.00");
        expDetails.setMeetingLng("72.00");


        expDetails.setHeighlight("-Great teacher\n" +
                "-Amazing support\n" +
                "-Cool friends\n" +
                "-All fetures\n" +
                "-Amazing adventure");
        expDetails.setInclude("-Full lesson\n" +
                "-Lunch\n" +
                "-Supports & Other");
        expDetails.setNotInclude("-Equipment\n" +
                "-Snacks");

        ArrayList<CalendarDay> enabledDates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);

        enabledDates.add(new CalendarDay(calendar));

        calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, 3);
        enabledDates.add(new CalendarDay(calendar.getTime()));

        calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, 5);

        enabledDates.add(new CalendarDay(calendar.getTime()));

        expDetails.setCalendarDays(enabledDates);

        expDetails.setLocalName("Hibrahim Mercuri");
        expDetails.setLocalDesc("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");
        expDetails.setLocalImage("android.resource://com.kooloco/drawable/user_round");
        expDetails.setLocalLanguage("Languages: French, Italian, English");

        List<OtherDetailsAnotherFields> otherDetailsAnotherFields = new ArrayList<>();
        otherDetailsAnotherFields.add(Temp.otherDetailsAnotherFields());
        otherDetailsAnotherFields.add(Temp.otherDetailsAnotherFields());

        expDetails.setOtherDetailsAnotherFields(otherDetailsAnotherFields);

        expDetails.setCancelletionPolicyName("Kool");

        expDetails.setCancelletionPolicyDesc("Cancel up to 24 hours before you activity and get a full refund, include all fees, Cancel within 24 hours of the activity and the first night is non-refundable except for fees.");

        List<String> strings = new ArrayList<>();
        strings.add("Surfing");
        strings.add("Lesson");
        strings.add("Bali");
        strings.add("Trendy");
        strings.add("Guiding");
        strings.add("Park rails");
        expDetails.setTags(strings);

        expDetails.setReviews(Temp.getReviews());

        return expDetails;

    }

    public static List<ExpereinceNew> getListExpNew() {

        List<ExpereinceNew> expereinceNews = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ExpereinceNew expereinceNew = new ExpereinceNew();
            expereinceNew.setId("0");
            expereinceNew.setTitle("Surf Lesson in the Boro with lunch & all other services");
            expereinceNew.setImage_url("android.resource://com.kooloco/drawable/temp_1");
            expereinceNew.setRate("3");
            expereinceNew.setRateCount("10");
            expereinceNew.setExperience_url("android.resource://com.kooloco/drawable/lesson_new");
            expereinceNew.setLocation("Hossegor, France");
            expereinceNew.setCity("Hossegor");
            expereinceNew.setCountry("France");
            expereinceNew.setPrice("20.20");
            expereinceNews.add(expereinceNew);
        }

        return expereinceNews;
    }

    public static List<LocalNew> getListLocalNew() {

        List<LocalNew> localNews = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            LocalNew localNew = new LocalNew();
            localNew.setId("0");
            localNew.setFirstName("Hibrahim");
            localNew.setLastName("Mercuri");
            localNew.setImage_url("android.resource://com.kooloco/drawable/temp_1");
            localNew.setRate("4");
            localNew.setLocation("Hossegor, France");
            localNew.setPrice("20.20");
            localNew.setServices(Temp.getLocalServices());
            localNew.setLocalImagesNew(Temp.getLocalImageSlider());
            localNews.add(localNew);
        }

        return localNews;
    }

    public static List<HomeTopLocation> getListLocationNew() {

        List<HomeTopLocation> homeTopLocations = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            HomeTopLocation localNew = new HomeTopLocation();
            localNew.setTopLocationImageUrl("android.resource://com.kooloco/drawable/temp_loc");
            homeTopLocations.add(localNew);
        }

        return homeTopLocations;
    }

    public static List<SchedulePrice> getListSchedulePrice() {
        List<SchedulePrice> schedulePrices = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            SchedulePrice schedulePrice = new SchedulePrice();
            schedulePrice.setId("1");
            if (i == 0) {
                schedulePrice.setStartTime("08:00:00");
                schedulePrice.setEndTime("09:00:00");
                schedulePrice.setPrice("20.35");
            } else if (i == 1) {
                schedulePrice.setStartTime("09:30:00");
                schedulePrice.setEndTime("10:30:00");
                schedulePrice.setPrice("23.35");

            } else if (i == 2) {
                schedulePrice.setStartTime("11:00:00");
                schedulePrice.setEndTime("12:00:00");
                schedulePrice.setPrice("21.35");

            } else if (i == 3) {
                schedulePrice.setStartTime("12:30:00");
                schedulePrice.setEndTime("13:30:00");
                schedulePrice.setPrice("18.12");

            } else if (i == 4) {
                schedulePrice.setStartTime("14:00:00");
                schedulePrice.setEndTime("15:00:00");
                schedulePrice.setPrice("45.35");
            }

            schedulePrices.add(schedulePrice);
        }

        return schedulePrices;
    }

    public static VisitorBookingNewFlow getVisitorBookingNewFlow() {

        //Set local data

        VisitorBookingNewFlow visitorBookingNewFlow = new VisitorBookingNewFlow();
        visitorBookingNewFlow.setLocalId("1");
        visitorBookingNewFlow.setLocalName("Hibrahim Mercuri");
        visitorBookingNewFlow.setLocalImage("android.resource://com.kooloco/drawable/temp_1");
        visitorBookingNewFlow.setLocalRating("3.5");

        //Set exp data

        visitorBookingNewFlow.setExperienceId("1");
        visitorBookingNewFlow.setExperienceTitle("Surf Lesson in the Boro with lunch & all other services");
        visitorBookingNewFlow.setExperienceImage("android.resource://com.kooloco/drawable/temp_2");

        visitorBookingNewFlow.setExperienceMeetingAddress("Chemin des Anciens-Moulins 8b, 1009 Pully, Suisse");
        visitorBookingNewFlow.setExperienceLet("23.00");
        visitorBookingNewFlow.setExperienceLng("72.00");

        //Set select date and time for booking
        visitorBookingNewFlow.setExpTimeSlotsId("1");
        visitorBookingNewFlow.setExpDate("29 Apr 2018");
        visitorBookingNewFlow.setExpSelectTime("from 10:00 AM to 11:00 AM");
        visitorBookingNewFlow.setExpDuration("01:00");
        visitorBookingNewFlow.setExpPrice("20.52");
        visitorBookingNewFlow.setExpIsGroupBookingExperience("1");
        visitorBookingNewFlow.setExpParticipants("10");
        visitorBookingNewFlow.setExpPriceAddPart("15.00");
        visitorBookingNewFlow.setExpStartTime("10:00");
        visitorBookingNewFlow.setExpEndTime("11:00");


        return visitorBookingNewFlow;

    }

    public static BookingFeeAndComision getBookingFeeAndComision() {

        //Set local data

        BookingFeeAndComision bookingAndCommision = new BookingFeeAndComision();
        bookingAndCommision.setFeesForBooking("10");
        bookingAndCommision.setCommision("10");
        bookingAndCommision.setPrice("20.20");

        return bookingAndCommision;

    }

    public static DashboardDetails dashboardDetails() {
        String json = "{\n" +
                "  \"distance\": \"6.06\",\n" +
                "  \"id\": \"74\",\n" +
                "  \"token\": \"\",\n" +
                "  \"social_id\": \"\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Smithhh\",\n" +
                "  \"timezone\": \"Asia\\/Kolkata\",\n" +
                "  \"email\": \"smith@gmail.com\",\n" +
                "  \"signup_step\": \"12\",\n" +
                "  \"intro_your_self\": \"Testtttt\",\n" +
                "  \"address\": \"Test\",\n" +
                "  \"phone\": \"12345678999\",\n" +
                "  \"phone_doc\": \"\",\n" +
                "  \"latitude\": \"\",\n" +
                "  \"longitude\": \"\",\n" +
                "  \"signup_type\": \"S\",\n" +
                "  \"device_id\": \"\",\n" +
                "  \"device_type\": \"\",\n" +
                "  \"role\": \"V\",\n" +
                "  \"app_lang_id\": \"2\",\n" +
                "  \"currency_id\": \"0\",\n" +
                "  \"profile_image\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/profile_image\\/default.png\",\n" +
                "  \"password\": \"e10adc3949ba59abbe56e057f20f883e\",\n" +
                "  \"rate\": \"4.50\",\n" +
                "  \"visitor_rate\": \"0.00\",\n" +
                "  \"app_notification\": \"1\",\n" +
                "  \"mail_notification\": \"1\",\n" +
                "  \"referral_code\": \"LLFTUXZ7\",\n" +
                "  \"is_want_to_complete\": \"1\",\n" +
                "  \"is_notification\": \"1\",\n" +
                "  \"is_admin_approve\": \"1\",\n" +
                "  \"is_login\": \"0\",\n" +
                "  \"is_verifed\": \"0\",\n" +
                "  \"otp\": \"\",\n" +
                "  \"is_active\": \"1\",\n" +
                "  \"insertdate\": \"2018-04-07 14:45:55\",\n" +
                "  \"cancellation_policy_id\": \"1\",\n" +
                "  \"location_address\": \"4 shantanu banlows rajpath club pachhad sindhu bhavan same bodkdev, Ellisbridge, Ahmedabad, Gujarat 380006, India\",\n" +
                "  \"location_area\": \"30\",\n" +
                "  \"location_latitude\": \"23.0225050809748\",\n" +
                "  \"location_city\": \"Ahmedabad\",\n" +
                "  \"location_state\": \"Gujarat\",\n" +
                "  \"location_country\": \"India\",\n" +
                "  \"location_longitude\": \"72.5713622197509\",\n" +
                "  \"title\": \"Strict Policy\",\n" +
                "  \"description\": \"Cancel up to 30 days before you activity and get a full refund,including all fees, Cancel within 30 days of the activity and get a 50% refund of the total nightly rate, as well as a full refund of fees\",\n" +
                "  \"is_affilated\": \"0\",\n" +
                "  \"organisation_detail\": {\n" +
                "    \n" +
                "  },\n" +
                "  \"languages\": \"German, French, English\",\n" +
                "  \"profile_image_thumb\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/profile_image\\/default.png\",\n" +
                "  \"is_favorite\": \"0\",\n" +
                "  \"sports_images\": [\n" +
                "    {\n" +
                "      \"id\": \"75\",\n" +
                "      \"user_id\": \"74\",\n" +
                "      \"sport_type_id\": \"1\",\n" +
                "      \"sport_sub_type_id\": \"0\",\n" +
                "      \"title\": \"Surfing\",\n" +
                "      \"description\": \"\",\n" +
                "      \"image\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/local\\/sports\\/eP6pzMXMFS1523112449.jpeg\",\n" +
                "      \"is_active\": \"1\",\n" +
                "      \"insertdate\": \"2018-04-07 14:47:33\",\n" +
                "      \"sport_name\": \"Surfing\",\n" +
                "      \"sub_sport_name\": \"\",\n" +
                "      \"image_url\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/local\\/sports\\/eP6pzMXMFS1523112449.jpeg\"\n" +
                "    },\n" +
                "{      \"id\": \"75\",\n" +
                "      \"user_id\": \"74\",\n" +
                "      \"sport_type_id\": \"1\",\n" +
                "      \"sport_sub_type_id\": \"0\",\n" +
                "      \"title\": \"Surfing\",\n" +
                "      \"description\": \"\",\n" +
                "      \"image\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/local\\/sports\\/eP6pzMXMFS1523112449.jpeg\",\n" +
                "      \"is_active\": \"1\",\n" +
                "      \"insertdate\": \"2018-04-07 14:47:33\",\n" +
                "      \"sport_name\": \"Surfing\",\n" +
                "      \"sub_sport_name\": \"\",\n" +
                "      \"image_url\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/local\\/sports\\/eP6pzMXMFS1523112449.jpeg\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"certificates\": [\n" +
                "    {\n" +
                "      \"id\": \"16\",\n" +
                "      \"user_id\": \"74\",\n" +
                "      \"title\": \"Surfing\",\n" +
                "      \"sport_type_id\": \"1\",\n" +
                "      \"sport_sub_type_id\": \"0\",\n" +
                "      \"certificate\": \"hFaj5RchWl1523112467.jpeg\",\n" +
                "      \"description\": \"Fdsfds\",\n" +
                "      \"insertdate\": \"2018-04-07 14:47:51\",\n" +
                "      \"sport_name\": \"Surfing\",\n" +
                "      \"sub_sport_name\": \"\",\n" +
                "      \"image_url\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/local\\/certificate\\/hFaj5RchWl1523112467.jpeg\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"schedule\": [\n" +
                "    {\n" +
                "      \"id\": \"81\",\n" +
                "      \"user_id\": \"74\",\n" +
                "      \"day\": \"Sun\",\n" +
                "      \"start_time\": \"00:00:00\",\n" +
                "      \"end_time\": \"02:00:00\",\n" +
                "      \"is_day_off\": \"1\",\n" +
                "      \"is_active\": \"1\",\n" +
                "      \"insertdate\": \"2018-04-07 14:48:41\"\n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"80\",\n" +
                "      \"user_id\": \"74\",\n" +
                "      \"day\": \"Sat\",\n" +
                "      \"start_time\": \"00:00:00\",\n" +
                "      \"end_time\": \"23:00:00\",\n" +
                "      \"is_day_off\": \"1\",\n" +
                "      \"is_active\": \"1\",\n" +
                "      \"insertdate\": \"2018-04-07 14:48:41\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"achievements\": [\n" +
                "    {\n" +
                "      \"id\": \"17\",\n" +
                "      \"user_id\": \"74\",\n" +
                "      \"title\": \"Surfing\",\n" +
                "      \"sport_type_id\": \"1\",\n" +
                "      \"sport_sub_type_id\": \"0\",\n" +
                "      \"achievement_doc\": \"wh5HPGtGFc1523112484.jpeg\",\n" +
                "      \"description\": \"\",\n" +
                "      \"insertdate\": \"2018-04-07 14:48:07\",\n" +
                "      \"sport_name\": \"Surfing\",\n" +
                "      \"sub_sport_name\": \"\",\n" +
                "      \"tags\": [\n" +
                "        {\n" +
                "          \"id\": \"1\",\n" +
                "          \"name\": \"Photo Shooting\",\n" +
                "          \"tag\": \"#photo_shooting\",\n" +
                "          \"is_active\": \"1\",\n" +
                "          \"insertdate\": \"2017-11-15 17:11:37\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"3\",\n" +
                "          \"name\": \"Movie\",\n" +
                "          \"tag\": \"#movie\",\n" +
                "          \"is_active\": \"1\",\n" +
                "          \"insertdate\": \"2017-11-21 17:29:12\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"image_url\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/local\\/achievement\\/wh5HPGtGFc1523112484.jpeg\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"experience\": [\n" +
                "    {\n" +
                "      \"id\": \"2\",\n" +
                "      \"name\": \"Lesson\",\n" +
                "      \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/experience_icon\\/lesson@3x.png\",\n" +
                "      \"icon_2\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/experience_icon\\/Dashboard_lesson3x.png\",\n" +
                "      \"description\": \"Learn and Improve your skills with the local\",\n" +
                "      \"is_active\": \"1\",\n" +
                "      \"insertdate\": \"2017-11-16 04:32:28\",\n" +
                "      \"minimum_duration\": \"3\",\n" +
                "      \"is_multiple_booking\": \"0\",\n" +
                "      \"new_participant_perc\": \"0.00\",\n" +
                "      \"sport_id\": \"9,1\",\n" +
                "      \"number_of_participant\": \"0\",\n" +
                "      \"price_per_hour\": \"30.00\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"low_price\": \"30.00\",\n" +
                "  \"sports\": [\n" +
                "    {\n" +
                "      \"id\": \"1\",\n" +
                "      \"user_id\": \"0\",\n" +
                "      \"name\": \"Surfing\",\n" +
                "      \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/surfing_3x.png\",\n" +
                "      \"site_icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/062f89f57e581d0ffac3e4a5f9439a46.jpg\",\n" +
                "      \"parent_id\": \"0\",\n" +
                "      \"is_admin_approve\": \"1\",\n" +
                "      \"is_active\": \"1\",\n" +
                "      \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "      \"is_selected\": \"1\",\n" +
                "      \"is_expandable\": \"0\",\n" +
                "      \"sub_sport_type\": [\n" +
                "        \n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"2\",\n" +
                "      \"user_id\": \"0\",\n" +
                "      \"name\": \"Skiing\",\n" +
                "      \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/skiing_3x.png\",\n" +
                "      \"site_icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/6c6287f3aef3f435cff606a7360b8547.jpg\",\n" +
                "      \"parent_id\": \"0\",\n" +
                "      \"is_admin_approve\": \"1\",\n" +
                "      \"is_active\": \"1\",\n" +
                "      \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "      \"is_selected\": \"1\",\n" +
                "      \"is_expandable\": \"1\",\n" +
                "      \"sub_sport_type\": [\n" +
                "        {\n" +
                "          \"id\": \"9\",\n" +
                "          \"user_id\": \"0\",\n" +
                "          \"name\": \"Slope\",\n" +
                "          \"icon\": \"\",\n" +
                "          \"site_icon\": \"default.png\",\n" +
                "          \"parent_id\": \"2\",\n" +
                "          \"is_admin_approve\": \"1\",\n" +
                "          \"is_active\": \"1\",\n" +
                "          \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "          \"is_selected\": \"1\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        return new Gson().fromJson(json, DashboardDetails.class);
    }


    public static List<Order> getOrdersPending() {
        String json = "[{\"id\":\"15\",\"user_id\":\"23\",\"local_id\":\"1\",\"sport_id\":\"1\",\"firstname\":\"keyon\",\"lastname\":\"Span\",\"total_price\":\"99.00\",\"booking_date\":\"2018-03-26\",\"profile_image\":\"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/profile_image\\/default.png\",\"meeting_address\":\"4, Local Road, Vishwas City 1, Sola, Ahmedabad, Gujarat 380061, India\",\"meeting_latitude\":\"23.075239865480828\",\"meeting_longitude\":\"72.5263299420476\",\"is_local_set_meeting_loc\":\"0\",\"is_local_set_duration\":\"0\",\"payment_status\":\"Escrow\",\"activity_total\":\"90.00\",\"commision_perc\":\"10\",\"commision_value\":\"9.00\",\"experience_name\":\"Session\",\"status\":\"pending\",\"insertdate\":\"2018-03-23 09:16:00\",\"rate\":\"0.00\",\"sport\":\"Surfing\",\"is_favorite\":\"0\"}]";

        return new Gson().fromJson(json, new TypeToken<List<Order>>() {
        }.getType());
    }

    public static OrderDetails getorderDetailsNew() {
        String json = "{\"id\":\"15\",\"user_id\":\"23\",\"local_id\":\"1\",\"experience_id\":\"1\",\"organisation_id\":\"0\",\"sport_id\":\"1\",\"booking_date\":\"2018-03-26\",\"start_time\":\"09:00:00\",\"end_time\":\"12:00:00\",\"extra_participant\":\"1\",\"user_duration\":\"03:00\",\"duration\":\"03:00\",\"additional_participant_charged\":\"0.00\",\"price\":\"90.00\",\"fees_for_booking\":\"9.00\",\"total_price\":\"99.00\",\"commision_perc\":\"10\",\"commision_value\":\"9.00\",\"fees_for_booking_perc\":\"10\",\"city\":\"\",\"meeting_address\":\"4, Local Road, Vishwas City 1, Sola, Ahmedabad, Gujarat 380061, India\",\"meeting_latitude\":\"23.075239865480828\",\"meeting_longitude\":\"72.5263299420476\",\"more_about\":\"\",\"card_id\":\"1\",\"is_objected\":\"0\",\"objection_reason\":\"\",\"status\":\"pending\",\"payment_status\":\"Escrow\",\"payment_rule_option\":\" \",\"payment_rule_type\":\" \",\"payment_rule_value\":\" \",\"canceled_by\":\"0\",\"insertdate\":\"2018-03-23 09:16:00\",\"is_local_set_meeting_loc\":\"0\",\"is_local_set_duration\":\"0\",\"activity_total\":\"90.00\",\"price_individual\":\"30.00\",\"additional_flat_amount\":\"100.00\",\"activities\":\"Session\",\"firstname\":\"keyon\",\"lastname\":\"Span\",\"rate\":\"0.00\",\"profile_image\":\"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/profile_image\\/default.png\",\"participant_count\":\"1\",\"participant\":[{\"id\":\"18\",\"order_id\":\"15\",\"user_id\":\"23\",\"is_register\":\"1\",\"email\":\"firstnathshah@gmail.com\",\"insertdate\":\"2018-03-23 09:16:00\"}],\"is_published\":\"0\",\"blog_id\":\"0\",\"price_rule\":{\"id\":\"31\",\"user_id\":\"1\",\"experience_id\":\"1\",\"sport_id\":\"1,9,10,6\",\"price_per_hour\":\"30.00\",\"new_participant_perc\":\"100.00\",\"number_of_participant\":\"2\",\"minimum_duration\":\"3\",\"is_multiple_booking\":\"1\",\"is_active\":\"1\",\"insertdate\":\"2018-03-22 13:17:59\"},\"price_per_hour\":90,\"addition_participant\":90,\"sports\":[{\"id\":\"1\",\"user_id\":\"0\",\"name\":\"Surfing\",\"icon\":\"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/surfing_3x.png\",\"site_icon\":\"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/062f89f57e581d0ffac3e4a5f9439a46.jpg\",\"parent_id\":\"0\",\"is_admin_approve\":\"1\",\"is_active\":\"1\",\"insertdate\":\"2017-11-16 00:00:00\",\"is_selected\":\"0\",\"is_expandable\":\"0\",\"sub_sport_type\":[]}],\"is_objection_detail\":\"0\",\"objection_detail\":{},\"is_review\":\"0\",\"review\":{}}";

        return new Gson().fromJson(json, OrderDetails.class);
    }

    public static List<ExperienceDetails> getListMyExp() {
        String json = "[{\n" +
                "      \"order_id\": \"8\",\n" +
                "      \"id\": \"8\",\n" +
                "      \"user_id\": \"21\",\n" +
                "      \"local_id\": \"22\",\n" +
                "      \"experience_id\": \"1\",\n" +
                "      \"organisation_id\": \"0\",\n" +
                "      \"sport_id\": \"10\",\n" +
                "      \"booking_date\": \"2018-06-26\",\n" +
                "      \"start_time\": \"09:00:00\",\n" +
                "      \"end_time\": \"12:00:00\",\n" +
                "      \"extra_participant\": \"2\",\n" +
                "      \"user_duration\": \"\",\n" +
                "      \"duration\": \"3:00\",\n" +
                "      \"exp_title\": \"Surf Lesson in the Boro with lunch & all other services\",\n" +
                "      \"exp_image\": \"android.resource://com.kooloco/drawable/temp_2\",\n" +
                "      \"additional_participant_charged\": \"150.00\",\n" +
                "      \"price\": \"60.00\",\n" +
                "      \"fees_for_booking\": \"21.00\",\n" +
                "      \"total_price\": \"231.00\",\n" +
                "      \"commision_perc\": \"10\",\n" +
                "      \"commision_value\": \"21.00\",\n" +
                "      \"fees_for_booking_perc\": \"10\",\n" +
                "      \"city\": \"\",\n" +
                "      \"meeting_address\": \"Ahmedabad, Gujarat 380060, India\",\n" +
                "      \"meeting_latitude\": \"23.06920889621642\",\n" +
                "      \"meeting_longitude\": \"72.51121234148741\",\n" +
                "      \"more_about\": \"\",\n" +
                "      \"card_id\": \"1\",\n" +
                "      \"is_objected\": \"1\",\n" +
                "      \"objection_reason\": \"Total\",\n" +
                "      \"status\": \"pending\",\n" +
                "      \"payment_status\": \"escrow\",\n" +
                "      \"payment_rule_option\": \" \",\n" +
                "      \"payment_rule_type\": \" \",\n" +
                "      \"payment_rule_value\": \" \",\n" +
                "      \"canceled_by\": \"0\",\n" +
                "      \"insertdate\": \"2018-03-23 07:41:31\",\n" +
                "      \"is_local_set_meeting_loc\": \"0\",\n" +
                "      \"is_local_set_duration\": \"0\",\n" +
                "      \"activity_total\": \"210.00\",\n" +
                "      \"price_individual\": \"20.00\",\n" +
                "      \"additional_flat_amount\": \"50.00\",\n" +
                "      \"experience\": \"Session\",\n" +
                "      \"profile_image\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/profile_image\\/default.png\",\n" +
                "      \"firstname\": \"Mery\",\n" +
                "      \"lastname\": \"Test\",\n" +
                "      \"intro_your_self\": \"\",\n" +
                "      \"rate\": \"4.33\",\n" +
                "      \"is_published\": \"0\",\n" +
                "      \"blog_id\": \"0\",\n" +
                "      \"sport\": \"Freeride Skiing\",\n" +
                "      \"tagline_one\": \"Session Freeride with Mery Test\",\n" +
                "      \"tagline_two\": \"By Kery Test, on the 26th of June, 2018\",\n" +
                "      \"sports_images\": [\n" +
                "        {\n" +
                "          \"id\": \"12\",\n" +
                "          \"user_id\": \"22\",\n" +
                "          \"sport_type_id\": \"4\",\n" +
                "          \"sport_sub_type_id\": \"17\",\n" +
                "          \"title\": \"BMX (Skate parks or street) Biking\",\n" +
                "          \"description\": \"Ttt\",\n" +
                "          \"image\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/local\\/sports\\/18f22870120bf9668a3c0b8175c86685.jpg\",\n" +
                "          \"is_active\": \"1\",\n" +
                "          \"insertdate\": \"2018-03-23 05:12:57\",\n" +
                "          \"sport_name\": \"Biking\",\n" +
                "          \"sub_sport_name\": \"BMX (Skate parks or street)\",\n" +
                "          \"image_url\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/local\\/sports\\/18f22870120bf9668a3c0b8175c86685.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"14\",\n" +
                "          \"user_id\": \"22\",\n" +
                "          \"sport_type_id\": \"1\",\n" +
                "          \"sport_sub_type_id\": \"0\",\n" +
                "          \"title\": \"Surfing\",\n" +
                "          \"description\": \"Tt\",\n" +
                "          \"image\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/local\\/sports\\/13912dc59407c1136f1eaf7330d12903.jpg\",\n" +
                "          \"is_active\": \"1\",\n" +
                "          \"insertdate\": \"2018-03-23 05:28:31\",\n" +
                "          \"sport_name\": \"Surfing\",\n" +
                "          \"sub_sport_name\": \"\",\n" +
                "          \"image_url\": \"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/local\\/sports\\/13912dc59407c1136f1eaf7330d12903.jpg\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }]";

        return new Gson().fromJson(json, new TypeToken<List<ExperienceDetails>>() {
        }.getType());
    }


    public static FilterGetData getStaticFilter() {
        String json = "{\n" +
                "  \"code\": \"1\",\n" +
                "  \"message\": \"Filter data\",\n" +
                "  \"data\": {\n" +
                "    \"experience\": [\n" +
                "      {\n" +
                "        \"id\": \"1\",\n" +
                "        \"name\": \"Session\",\n" +
                "        \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/experience_icon\\/session@3x.png\",\n" +
                "        \"icon_2\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/experience_icon\\/Dashboard_session3x.png\",\n" +
                "        \"description\": \"Just share session with a local in his favorite spot\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 03:14:15\",\n" +
                "        \"is_selected\": \"0\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"2\",\n" +
                "        \"name\": \"Lesson\",\n" +
                "        \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/experience_icon\\/lesson@3x.png\",\n" +
                "        \"icon_2\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/experience_icon\\/Dashboard_lesson3x.png\",\n" +
                "        \"description\": \"Learn and Improve your skills with the local\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 04:32:28\",\n" +
                "        \"is_selected\": \"0\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"3\",\n" +
                "        \"name\": \"Discovery\",\n" +
                "        \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/experience_icon\\/discovery@3x.png\",\n" +
                "        \"icon_2\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/experience_icon\\/Dashboard_discovery3x.png\",\n" +
                "        \"description\": \"Discovery the best secret spot with the local\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 06:33:45\",\n" +
                "        \"is_selected\": \"0\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"sports\": [\n" +
                "      {\n" +
                "        \"id\": \"1\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Surfing\",\n" +
                "        \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/surfing_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/062f89f57e581d0ffac3e4a5f9439a46.jpg\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"0\",\n" +
                "        \"sub_sport_type\": []\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"2\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Skiing\",\n" +
                "        \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/skiing_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/6c6287f3aef3f435cff606a7360b8547.jpg\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"1\",\n" +
                "        \"sub_sport_type\": [\n" +
                "          {\n" +
                "            \"id\": \"9\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Slope\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"parent_id\": \"2\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"10\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Freeride\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"parent_id\": \"2\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"11\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Ski touring\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"parent_id\": \"2\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"12\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Freestyle\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"parent_id\": \"2\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"3\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Snowboarding\",\n" +
                "        \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/snow_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/de344638576917341c2f25340a2cfc3a.jpg\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"1\",\n" +
                "        \"sub_sport_type\": [\n" +
                "          {\n" +
                "            \"id\": \"13\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Slope\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"parent_id\": \"3\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"14\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Freeride\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"parent_id\": \"3\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"15\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Backcountry\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"parent_id\": \"3\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"16\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Freestyle\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"parent_id\": \"3\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"4\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Biking\",\n" +
                "        \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/Bike.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/0a87c421824c679ac15e20eb221166bf.jpg\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"1\",\n" +
                "        \"sub_sport_type\": [\n" +
                "          {\n" +
                "            \"id\": \"17\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"BMX (Skate parks or street)\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"parent_id\": \"4\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"18\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"MTB\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"parent_id\": \"4\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"19\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Downhill\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"parent_id\": \"4\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"5\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Skateboarding\",\n" +
                "        \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/skate_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/c89292ad2d17892aa4f2aea3260249ee.jpg\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"0\",\n" +
                "        \"sub_sport_type\": []\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"6\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Mountaineering\",\n" +
                "        \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/mountaineering_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/f5d30b347be4420571e0931cd83aec26.jpg\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"0\",\n" +
                "        \"sub_sport_type\": []\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"7\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Hiking\\/trekking\",\n" +
                "        \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/hiking_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/7060f24946d283cb433c94ad4030b038.jpg\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"0\",\n" +
                "        \"sub_sport_type\": []\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"8\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Climbing\",\n" +
                "        \"icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/Climbing_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/132.148.17.145\\/~hyperlinkserver\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/63b8e12bb3ce3baec64ab343bd70900a.jpg\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"0\",\n" +
                "        \"sub_sport_type\": []\n" +
                "      }\n" +
                "    ],\n" +
                "    \"language\": [\n" +
                "      {\n" +
                "        \"id\": \"1\",\n" +
                "        \"name\": \"Spanish\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-22 11:53:41\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"2\",\n" +
                "        \"name\": \"German\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-22 11:53:41\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"3\",\n" +
                "        \"name\": \"French\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-22 11:53:41\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"4\",\n" +
                "        \"name\": \"English\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-22 11:53:41\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        return new Gson().fromJson(json, FilterGetData.class);

    }


    public static List<Service> getLocalServicesWeb() {
        if (serviceDrwable == null) {
            serviceDrwable = new ArrayList<>();

            Service service = new Service();
            service.setServiceImage("http://132.148.17.145/~hyperlinkserver/kooloco/assets/upload/sports_icon/Climbing_3x.png");
            service.setName("Climbing");
            service.setSubServices(getSubService());

            Service service1 = new Service();
            service1.setServiceImage("http://132.148.17.145/~hyperlinkserver/kooloco/assets/upload/sports_icon/hiking_3x.png");
            service1.setName("Hiking");
            service1.setSubServices(getSubService());

            Service service2 = new Service();
            service2.setServiceImage("http://132.148.17.145/~hyperlinkserver/kooloco/assets/upload/sports_icon/Bike.png");
            service2.setName("Biking");
            service2.setSubServices(getSubService());

            Service service3 = new Service();
            service3.setServiceImage("http://132.148.17.145/~hyperlinkserver/kooloco/assets/upload/sports_icon/skiing_3x.png");
            service3.setName("Skiing");
            service3.setSubServices(getSubService());

            Service service4 = new Service();
            service4.setServiceImage("http://132.148.17.145/~hyperlinkserver/kooloco/assets/upload/sports_icon/surfing_3x.png");
            service4.setName("Surfing");
            service4.setSubServices(getSubService());

            Service service5 = new Service();
            service5.setServiceImage("http://132.148.17.145/~hyperlinkserver/kooloco/assets/upload/sports_icon/snow_3x.png");
            service5.setName("Snow");
            service5.setSubServices(getSubService());

            Service service6 = new Service();
            service6.setServiceImage("http://132.148.17.145/~hyperlinkserver/kooloco/assets/upload/sports_icon/mountaineering_3x.png");
            service6.setName("Mountaineering");
            service6.setSubServices(getSubService());

            serviceDrwable.add(service);
            serviceDrwable.add(service1);
            serviceDrwable.add(service2);
            serviceDrwable.add(service3);
            serviceDrwable.add(service4);
            serviceDrwable.add(service5);
            serviceDrwable.add(service6);

        }
        return serviceDrwable;
    }


    public static List<String> getFilDuration() {
        List<String> strings = new ArrayList<>();

        strings.add("0 - 4 hours");
        strings.add("5 hours - 1 days");

        strings.add("2 - 3 days");

        strings.add("4 day more");

        return strings;
    }

    public static List<OtherDetailsFieldsSelect> getLevelExp() {
        String json = "[\n" +
                "  {\n" +
                "    \"id\": \"2\",\n" +
                "    \"name\": \"Beginner\",\n" +
                "    \"is_active\": \"1\",\n" +
                "    \"insertdate\": \"2017-11-22 11:53:41\",\n" +
                "    \"is_selected\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"3\",\n" +
                "    \"name\": \"Intermediate\",\n" +
                "    \"is_active\": \"1\",\n" +
                "    \"insertdate\": \"2017-11-22 11:53:41\",\n" +
                "    \"is_selected\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"4\",\n" +
                "    \"name\": \"Advanced\",\n" +
                "    \"is_active\": \"1\",\n" +
                "    \"insertdate\": \"2017-11-22 11:53:41\",\n" +
                "    \"is_selected\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5\",\n" +
                "    \"name\": \"Pro\",\n" +
                "    \"is_active\": \"1\",\n" +
                "    \"insertdate\": \"2017-11-22 11:53:41\",\n" +
                "    \"is_selected\": \"0\"\n" +
                "  }\n" +
                "]";

        return new Gson().fromJson(json, new TypeToken<List<OtherDetailsFieldsSelect>>() {
        }.getType());
    }

    public static List<OtherDetailsFieldsSelect> getPerfectExp() {
        String json = "[\n" +
                "  {\n" +
                "    \"id\": \"1\",\n" +
                "    \"name\": \"Families\",\n" +
                "    \"is_active\": \"1\",\n" +
                "    \"insertdate\": \"2017-11-22 11:53:41\",\n" +
                "    \"is_selected\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"2\",\n" +
                "    \"name\": \"Women\",\n" +
                "    \"is_active\": \"1\",\n" +
                "    \"insertdate\": \"2017-11-22 11:53:41\",\n" +
                "    \"is_selected\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"3\",\n" +
                "    \"name\": \"Adults\",\n" +
                "    \"is_active\": \"1\",\n" +
                "    \"insertdate\": \"2017-11-22 11:53:41\",\n" +
                "    \"is_selected\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"4\",\n" +
                "    \"name\": \"Kids\",\n" +
                "    \"is_active\": \"1\",\n" +
                "    \"insertdate\": \"2017-11-22 11:53:41\",\n" +
                "    \"is_selected\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"5\",\n" +
                "    \"name\": \"Teens\",\n" +
                "    \"is_active\": \"1\",\n" +
                "    \"insertdate\": \"2017-11-22 11:53:41\",\n" +
                "    \"is_selected\": \"0\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"6\",\n" +
                "    \"name\": \"Groups\",\n" +
                "    \"is_active\": \"1\",\n" +
                "    \"insertdate\": \"2017-11-22 11:53:41\",\n" +
                "    \"is_selected\": \"0\"\n" +
                "  }\n" +
                "]";

        return new Gson().fromJson(json, new TypeToken<List<OtherDetailsFieldsSelect>>() {
        }.getType());
    }


    public static ExperienceResponse getExpMeetingResponse() {
        String json = "{\"id\":\"25\",\"local_id\":\"2\",\"title\":\"Sdfsdfs\",\"sport_id\":\"19\",\"activity_id\":\"2\",\"description\":\"Fsdfs fs\",\"activity_address\":\"mhesana, Kalapi Nagar, Nagalpur, Mehsana, Gujarat 384002, India\",\"activity_latitude\":\"23.587960727723246\",\"activity_longitude\":\"72.36932516098024\",\"cancellation_policy\":\"0\",\"city\":\"Mehsana\",\"state\":\"Gujarat\",\"country\":\"India\",\"rate\":\"0.00\",\"maximum_participant\":\"0\",\"is_active\":\"1\",\"is_approve\":\"0\",\"last_update\":\"0000-00-00 00:00:00\",\"insertdate\":\"2018-05-02 11:40:02\",\"media\":[{\"image\":\"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/local\\/experience\\/images\\/2_1525241395029.jpg\",\"id\":\"40\",\"experience_id\":\"25\",\"file\":\"2_1525241395029.jpg\",\"media_type\":\"I\",\"is_active\":\"0\",\"insertdate\":\"2018-05-02 11:40:02\"}]}";
        return new Gson().fromJson(json, ExperienceResponse.class);
    }


    public static ExpereinceNew getExpDet() {
        String json = "{\"id\":\"13\",\"local_id\":\"2\",\"title\":\"Test\",\"sport_id\":\"0\",\"activity_id\":\"0\",\"description\":\"Tedtset s\",\"activity_address\":\"\",\"activity_latitude\":\"\",\"activity_longitude\":\"\",\"cancellation_policy\":\"0\",\"city\":\"\",\"state\":\"\",\"country\":\"\",\"rate\":\"0.00\",\"maximum_participant\":\"0\",\"is_active\":\"1\",\"is_approve\":\"0\",\"last_update\":\"0000-00-00 00:00:00\",\"insertdate\":\"2018-05-01 19:11:02\",\"image\":\"https:\\/\\/s3-eu-west-1.amazonaws.com\\/hlink-bucket\\/kooloco\\/local\\/experience\\/images\\/123456789.jpg\",\"price\":\"00\"}";
        return new Gson().fromJson(json, ExpereinceNew.class);
    }


    public static List<Service> getOnBoardService() {
        String json = "[\n" +
                "      {\n" +
                "        \"id\": \"1\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Surfing\",\n" +
                "        \"icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/surfing_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/category-box-06.jpg\",\n" +
                "        \"boarding_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/on-boarding\\/surfing_3x.png\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"0\",\n" +
                "        \"sub_sport_type\": []\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"2\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Skiing\",\n" +
                "        \"icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/skiing_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/category-box-02.jpg\",\n" +
                "        \"boarding_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/on-boarding\\/skiing_3x.png\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"1\",\n" +
                "        \"sub_sport_type\": [\n" +
                "          {\n" +
                "            \"id\": \"9\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Slope\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"boarding_icon\": \"\",\n" +
                "            \"parent_id\": \"2\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"10\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Freeride\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"boarding_icon\": \"\",\n" +
                "            \"parent_id\": \"2\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"11\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Backcountry\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"boarding_icon\": \"\",\n" +
                "            \"parent_id\": \"2\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"12\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Freestyle\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"boarding_icon\": \"\",\n" +
                "            \"parent_id\": \"2\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"3\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Snowboarding\",\n" +
                "        \"icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/snow_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/snowboarding.jpg\",\n" +
                "        \"boarding_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/on-boarding\\/snow_3x.png\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"1\",\n" +
                "        \"sub_sport_type\": [\n" +
                "          {\n" +
                "            \"id\": \"13\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Slope\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"boarding_icon\": \"\",\n" +
                "            \"parent_id\": \"3\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"14\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Freeride\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"boarding_icon\": \"\",\n" +
                "            \"parent_id\": \"3\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"15\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Backcountry\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"boarding_icon\": \"\",\n" +
                "            \"parent_id\": \"3\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"16\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Freestyle\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"boarding_icon\": \"\",\n" +
                "            \"parent_id\": \"3\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"4\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Biking\",\n" +
                "        \"icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/Bike.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/category-box-01.jpg\",\n" +
                "        \"boarding_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/on-boarding\\/Bike.png\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"1\",\n" +
                "        \"sub_sport_type\": [\n" +
                "          {\n" +
                "            \"id\": \"17\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"BMX (Skate parks or street)\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"boarding_icon\": \"\",\n" +
                "            \"parent_id\": \"4\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"18\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"MTB\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"boarding_icon\": \"\",\n" +
                "            \"parent_id\": \"4\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"id\": \"19\",\n" +
                "            \"user_id\": \"0\",\n" +
                "            \"name\": \"Downhill\",\n" +
                "            \"icon\": \"\",\n" +
                "            \"site_icon\": \"default.png\",\n" +
                "            \"boarding_icon\": \"\",\n" +
                "            \"parent_id\": \"4\",\n" +
                "            \"is_admin_approve\": \"1\",\n" +
                "            \"is_active\": \"1\",\n" +
                "            \"insertdate\": \"2017-11-16 16:52:28\",\n" +
                "            \"is_selected\": \"0\"\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"5\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Skateboarding\",\n" +
                "        \"icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/skate_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/category-box-04.jpg\",\n" +
                "        \"boarding_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/on-boarding\\/skate_3x.png\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"0\",\n" +
                "        \"sub_sport_type\": []\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"6\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Mountaineering\",\n" +
                "        \"icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/mountaineering_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/default.png\",\n" +
                "        \"boarding_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/on-boarding\\/mountaineering_3x.png\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"0\",\n" +
                "        \"sub_sport_type\": []\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"7\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Hiking\\/trekking\",\n" +
                "        \"icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/hiking_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/category-box-03.jpg\",\n" +
                "        \"boarding_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/on-boarding\\/hiking_3x.png\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"0\",\n" +
                "        \"sub_sport_type\": []\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"8\",\n" +
                "        \"user_id\": \"0\",\n" +
                "        \"name\": \"Climbing\",\n" +
                "        \"icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/Climbing_3x.png\",\n" +
                "        \"site_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/site\\/category-box-05.jpg\",\n" +
                "        \"boarding_icon\": \"http:\\/\\/192.168.1.131\\/project\\/kooloco\\/assets\\/upload\\/sports_icon\\/on-boarding\\/category-box-05.jpg\",\n" +
                "        \"parent_id\": \"0\",\n" +
                "        \"is_admin_approve\": \"1\",\n" +
                "        \"is_active\": \"1\",\n" +
                "        \"insertdate\": \"2017-11-16 00:00:00\",\n" +
                "        \"is_selected\": \"0\",\n" +
                "        \"is_expandable\": \"0\",\n" +
                "        \"sub_sport_type\": []\n" +
                "      }\n" +
                "    ]";
        return new Gson().fromJson(json, new TypeToken<List<Service>>() {
        }.getType());
    }

    public static List<CancellationNewPolicy> getCancellationPolicyNews() {

        List<CancellationNewPolicy> cancellationNewPolicies = new ArrayList<>();

        CancellationNewPolicy cancellationNewPolicy;
        cancellationNewPolicy = new CancellationNewPolicy();

        cancellationNewPolicy.setId("1");
        cancellationNewPolicy.setTitle("7 Days and more");
        cancellationNewPolicy.setRefaund("80% refund");

        cancellationNewPolicies.add(cancellationNewPolicy);

        cancellationNewPolicy = new CancellationNewPolicy();
        cancellationNewPolicy.setId("2");
        cancellationNewPolicy.setTitle("1-7 Days");
        cancellationNewPolicy.setRefaund("50% refund");

        cancellationNewPolicies.add(cancellationNewPolicy);

        cancellationNewPolicy = new CancellationNewPolicy();
        cancellationNewPolicy.setId("3");
        cancellationNewPolicy.setTitle("0-1 Days");
        cancellationNewPolicy.setRefaund("Non-refundable");

        cancellationNewPolicies.add(cancellationNewPolicy);

        return cancellationNewPolicies;
    }


    public static List<Bank> getBanks() {
        List<Bank> banks = new ArrayList<>();

        Bank bank = new Bank();
        bank.setSelect(true);
        bank.setBankId("1");
        bank.setBankName("Panjab National bank");
        bank.setAccountNumber("**************1110");

        Bank bank1 = new Bank();
        bank1.setSelect(false);
        bank1.setBankId("1");
        bank1.setBankName("Panjab National bank 1");
        bank1.setAccountNumber("**************1111");

        Bank bank2 = new Bank();
        bank2.setSelect(false);
        bank2.setBankId("2");
        bank2.setBankName("Panjab National bank 1");
        bank2.setAccountNumber("**************1112");

        banks.add(bank);
        banks.add(bank1);
        banks.add(bank2);

        return banks;
    }

    private final static Country[] COUNTRIES = {
            new Country("AD", "Andorra", "+376", R.drawable.flag_ad, "EUR"),
            new Country("AE", "United Arab Emirates", "+971", R.drawable.flag_ae, "AED"),
            new Country("AF", "Afghanistan", "+93", R.drawable.flag_af, "AFN"),
            new Country("AG", "Antigua and Barbuda", "+1", R.drawable.flag_ag, "XCD"),
            new Country("AI", "Anguilla", "+1", R.drawable.flag_ai, "XCD"),
            new Country("AL", "Albania", "+355", R.drawable.flag_al, "ALL"),
            new Country("AM", "Armenia", "+374", R.drawable.flag_am, "AMD"),
            new Country("AO", "Angola", "+244", R.drawable.flag_ao, "AOA"),
            new Country("AQ", "Antarctica", "+672", R.drawable.flag_aq, "USD"),
            new Country("AR", "Argentina", "+54", R.drawable.flag_ar, "ARS"),
            new Country("AS", "American Samoa", "+1", R.drawable.flag_as, "USD"),
            new Country("AT", "Austria", "+43", R.drawable.flag_at, "EUR"),
            new Country("AU", "Australia", "+61", R.drawable.flag_au, "AUD"),
            new Country("AW", "Aruba", "+297", R.drawable.flag_aw, "AWG"),
            new Country("AX", "Aland Islands", "+358", R.drawable.flag_ax, "EUR"),
            new Country("AZ", "Azerbaijan", "+994", R.drawable.flag_az, "AZN"),
            new Country("BA", "Bosnia and Herzegovina", "+387", R.drawable.flag_ba, "BAM"),
            new Country("BB", "Barbados", "+1", R.drawable.flag_bb, "BBD"),
            new Country("BD", "Bangladesh", "+880", R.drawable.flag_bd, "BDT"),
            new Country("BE", "Belgium", "+32", R.drawable.flag_be, "EUR"),
            new Country("BF", "Burkina Faso", "+226", R.drawable.flag_bf, "XOF"),
            new Country("BG", "Bulgaria", "+359", R.drawable.flag_bg, "BGN"),
            new Country("BH", "Bahrain", "+973", R.drawable.flag_bh, "BHD"),
            new Country("BI", "Burundi", "+257", R.drawable.flag_bi, "BIF"),
            new Country("BJ", "Benin", "+229", R.drawable.flag_bj, "XOF"),
            new Country("BL", "Saint Barthelemy", "+590", R.drawable.flag_bl, "EUR"),
            new Country("BM", "Bermuda", "+1", R.drawable.flag_bm, "BMD"),
            new Country("BN", "Brunei Darussalam", "+673", R.drawable.flag_bn, "BND"),
            new Country("BO", "Bolivia, Plurinational State of", "+591", R.drawable.flag_bo, "BOB"),
            new Country("BQ", "Bonaire", "+599", R.drawable.flag_bq, "USD"),
            new Country("BR", "Brazil", "+55", R.drawable.flag_br, "BRL"),
            new Country("BS", "Bahamas", "+1", R.drawable.flag_bs, "BSD"),
            new Country("BT", "Bhutan", "+975", R.drawable.flag_bt, "BTN"),
            new Country("BV", "Bouvet Island", "+47", R.drawable.flag_bv, "NOK"),
            new Country("BW", "Botswana", "+267", R.drawable.flag_bw, "BWP"),
            new Country("BY", "Belarus", "+375", R.drawable.flag_by, "BYR"),
            new Country("BZ", "Belize", "+501", R.drawable.flag_bz, "BZD"),
            new Country("CA", "Canada", "+1", R.drawable.flag_ca, "CAD"),
            new Country("CC", "Cocos (Keeling) Islands", "+61", R.drawable.flag_cc, "AUD"),
            new Country("CD", "Congo, The Democratic Republic of the", "+243", R.drawable.flag_cd, "CDF"),
            new Country("CF", "Central African Republic", "+236", R.drawable.flag_cf, "XAF"),
            new Country("CG", "Congo", "+242", R.drawable.flag_cg, "XAF"),
            new Country("CH", "Switzerland", "+41", R.drawable.flag_ch, "CHF"),
            new Country("CI", "Ivory Coast", "+225", R.drawable.flag_ci, "XOF"),
            new Country("CK", "Cook Islands", "+682", R.drawable.flag_ck, "NZD"),
            new Country("CL", "Chile", "+56", R.drawable.flag_cl, "CLP"),
            new Country("CM", "Cameroon", "+237", R.drawable.flag_cm, "XAF"),
            new Country("CN", "China", "+86", R.drawable.flag_cn, "CNY"),
            new Country("CO", "Colombia", "+57", R.drawable.flag_co, "COP"),
            new Country("CR", "Costa Rica", "+506", R.drawable.flag_cr, "CRC"),
            new Country("CU", "Cuba", "+53", R.drawable.flag_cu, "CUP"),
            new Country("CV", "Cape Verde", "+238", R.drawable.flag_cv, "CVE"),
            new Country("CW", "Curacao", "+599", R.drawable.flag_cw, "ANG"),
            new Country("CX", "Christmas Island", "+61", R.drawable.flag_cx, "AUD"),
            new Country("CY", "Cyprus", "+357", R.drawable.flag_cy, "EUR"),
            new Country("CZ", "Czech Republic", "+420", R.drawable.flag_cz, "CZK"),
            new Country("DE", "Germany", "+49", R.drawable.flag_de, "EUR"),
            new Country("DJ", "Djibouti", "+253", R.drawable.flag_dj, "DJF"),
            new Country("DK", "Denmark", "+45", R.drawable.flag_dk, "DKK"),
            new Country("DM", "Dominica", "+1", R.drawable.flag_dm, "XCD"),
            new Country("DO", "Dominican Republic", "+1", R.drawable.flag_do, "DOP"),
            new Country("DZ", "Algeria", "+213", R.drawable.flag_dz, "DZD"),
            new Country("EC", "Ecuador", "+593", R.drawable.flag_ec, "USD"),
            new Country("EE", "Estonia", "+372", R.drawable.flag_ee, "EUR"),
            new Country("EG", "Egypt", "+20", R.drawable.flag_eg, "EGP"),
            new Country("EH", "Western Sahara", "+212", R.drawable.flag_eh, "MAD"),
            new Country("ER", "Eritrea", "+291", R.drawable.flag_er, "ERN"),
            new Country("ES", "Spain", "+34", R.drawable.flag_es, "EUR"),
            new Country("ET", "Ethiopia", "+251", R.drawable.flag_et, "ETB"),
            new Country("FI", "Finland", "+358", R.drawable.flag_fi, "EUR"),
            new Country("FJ", "Fiji", "+679", R.drawable.flag_fj, "FJD"),
            new Country("FK", "Falkland Islands (Malvinas)", "+500", R.drawable.flag_fk, "FKP"),
            new Country("FM", "Micronesia, Federated States of", "+691", R.drawable.flag_fm, "USD"),
            new Country("FO", "Faroe Islands", "+298", R.drawable.flag_fo, "DKK"),
            new Country("FR", "France", "+33", R.drawable.flag_fr, "EUR"),
            new Country("GA", "Gabon", "+241", R.drawable.flag_ga, "XAF"),
            new Country("GB", "United Kingdom", "+44", R.drawable.flag_gb, "GBP"),
            new Country("GD", "Grenada", "+1", R.drawable.flag_gd, "XCD"),
            new Country("GE", "Georgia", "+995", R.drawable.flag_ge, "GEL"),
            new Country("GF", "French Guiana", "+594", R.drawable.flag_gf, "EUR"),
            new Country("GG", "Guernsey", "+44", R.drawable.flag_gg, "GGP"),
            new Country("GH", "Ghana", "+233", R.drawable.flag_gh, "GHS"),
            new Country("GI", "Gibraltar", "+350", R.drawable.flag_gi, "GIP"),
            new Country("GL", "Greenland", "+299", R.drawable.flag_gl, "DKK"),
            new Country("GM", "Gambia", "+220", R.drawable.flag_gm, "GMD"),
            new Country("GN", "Guinea", "+224", R.drawable.flag_gn, "GNF"),
            new Country("GP", "Guadeloupe", "+590", R.drawable.flag_gp, "EUR"),
            new Country("GQ", "Equatorial Guinea", "+240", R.drawable.flag_gq, "XAF"),
            new Country("GR", "Greece", "+30", R.drawable.flag_gr, "EUR"),
            new Country("GS", "South Georgia and the South Sandwich Islands", "+500", R.drawable.flag_gs,
                    "GBP"),
            new Country("GT", "Guatemala", "+502", R.drawable.flag_gt, "GTQ"),
            new Country("GU", "Guam", "+1", R.drawable.flag_gu, "USD"),
            new Country("GW", "Guinea-Bissau", "+245", R.drawable.flag_gw, "XOF"),
            new Country("GY", "Guyana", "+595", R.drawable.flag_gy, "GYD"),
            new Country("HK", "Hong Kong", "+852", R.drawable.flag_hk, "HKD"),
            new Country("HM", "Heard Island and McDonald Islands", "+000", R.drawable.flag_hm, "AUD"),
            new Country("HN", "Honduras", "+504", R.drawable.flag_hn, "HNL"),
            new Country("HR", "Croatia", "+385", R.drawable.flag_hr, "HRK"),
            new Country("HT", "Haiti", "+509", R.drawable.flag_ht, "HTG"),
            new Country("HU", "Hungary", "+36", R.drawable.flag_hu, "HUF"),
            new Country("ID", "Indonesia", "+62", R.drawable.flag_id, "IDR"),
            new Country("IE", "Ireland", "+353", R.drawable.flag_ie, "EUR"),
            new Country("IL", "Israel", "+972", R.drawable.flag_il, "ILS"),
            new Country("IM", "Isle of Man", "+44", R.drawable.flag_im, "GBP"),
            new Country("IN", "India", "+91", R.drawable.flag_in, "INR"),
            new Country("IO", "British Indian Ocean Territory", "+246", R.drawable.flag_io, "USD"),
            new Country("IQ", "Iraq", "+964", R.drawable.flag_iq, "IQD"),
            new Country("IR", "Iran, Islamic Republic of", "+98", R.drawable.flag_ir, "IRR"),
            new Country("IS", "Iceland", "+354", R.drawable.flag_is, "ISK"),
            new Country("IT", "Italy", "+39", R.drawable.flag_it, "EUR"),
            new Country("JE", "Jersey", "+44", R.drawable.flag_je, "JEP"),
            new Country("JM", "Jamaica", "+1", R.drawable.flag_jm, "JMD"),
            new Country("JO", "Jordan", "+962", R.drawable.flag_jo, "JOD"),
            new Country("JP", "Japan", "+81", R.drawable.flag_jp, "JPY"),
            new Country("KE", "Kenya", "+254", R.drawable.flag_ke, "KES"),
            new Country("KG", "Kyrgyzstan", "+996", R.drawable.flag_kg, "KGS"),
            new Country("KH", "Cambodia", "+855", R.drawable.flag_kh, "KHR"),
            new Country("KI", "Kiribati", "+686", R.drawable.flag_ki, "AUD"),
            new Country("KM", "Comoros", "+269", R.drawable.flag_km, "KMF"),
            new Country("KN", "Saint Kitts and Nevis", "+1", R.drawable.flag_kn, "XCD"),
            new Country("KP", "North Korea", "+850", R.drawable.flag_kp, "KPW"),
            new Country("KR", "South Korea", "+82", R.drawable.flag_kr, "KRW"),
            new Country("KW", "Kuwait", "+965", R.drawable.flag_kw, "KWD"),
            new Country("KY", "Cayman Islands", "+345", R.drawable.flag_ky, "KYD"),
            new Country("KZ", "Kazakhstan", "+7", R.drawable.flag_kz, "KZT"),
            new Country("LA", "Lao People's Democratic Republic", "+856", R.drawable.flag_la, "LAK"),
            new Country("LB", "Lebanon", "+961", R.drawable.flag_lb, "LBP"),
            new Country("LC", "Saint Lucia", "+1", R.drawable.flag_lc, "XCD"),
            new Country("LI", "Liechtenstein", "+423", R.drawable.flag_li, "CHF"),
            new Country("LK", "Sri Lanka", "+94", R.drawable.flag_lk, "LKR"),
            new Country("LR", "Liberia", "+231", R.drawable.flag_lr, "LRD"),
            new Country("LS", "Lesotho", "+266", R.drawable.flag_ls, "LSL"),
            new Country("LT", "Lithuania", "+370", R.drawable.flag_lt, "LTL"),
            new Country("LU", "Luxembourg", "+352", R.drawable.flag_lu, "EUR"),
            new Country("LV", "Latvia", "+371", R.drawable.flag_lv, "LVL"),
            new Country("LY", "Libyan Arab Jamahiriya", "+218", R.drawable.flag_ly, "LYD"),
            new Country("MA", "Morocco", "+212", R.drawable.flag_ma, "MAD"),
            new Country("MC", "Monaco", "+377", R.drawable.flag_mc, "EUR"),
            new Country("MD", "Moldova, Republic of", "+373", R.drawable.flag_md, "MDL"),
            new Country("ME", "Montenegro", "+382", R.drawable.flag_me, "EUR"),
            new Country("MF", "Saint Martin", "+590", R.drawable.flag_mf, "EUR"),
            new Country("MG", "Madagascar", "+261", R.drawable.flag_mg, "MGA"),
            new Country("MH", "Marshall Islands", "+692", R.drawable.flag_mh, "USD"),
            new Country("MK", "Macedonia, The Former Yugoslav Republic of", "+389", R.drawable.flag_mk,
                    "MKD"),
            new Country("ML", "Mali", "+223", R.drawable.flag_ml, "XOF"),
            new Country("MM", "Myanmar", "+95", R.drawable.flag_mm, "MMK"),
            new Country("MN", "Mongolia", "+976", R.drawable.flag_mn, "MNT"),
            new Country("MO", "Macao", "+853", R.drawable.flag_mo, "MOP"),
            new Country("MP", "Northern Mariana Islands", "+1", R.drawable.flag_mp, "USD"),
            new Country("MQ", "Martinique", "+596", R.drawable.flag_mq, "EUR"),
            new Country("MR", "Mauritania", "+222", R.drawable.flag_mr, "MRO"),
            new Country("MS", "Montserrat", "+1", R.drawable.flag_ms, "XCD"),
            new Country("MT", "Malta", "+356", R.drawable.flag_mt, "EUR"),
            new Country("MU", "Mauritius", "+230", R.drawable.flag_mu, "MUR"),
            new Country("MV", "Maldives", "+960", R.drawable.flag_mv, "MVR"),
            new Country("MW", "Malawi", "+265", R.drawable.flag_mw, "MWK"),
            new Country("MX", "Mexico", "+52", R.drawable.flag_mx, "MXN"),
            new Country("MY", "Malaysia", "+60", R.drawable.flag_my, "MYR"),
            new Country("MZ", "Mozambique", "+258", R.drawable.flag_mz, "MZN"),
            new Country("NA", "Namibia", "+264", R.drawable.flag_na, "NAD"),
            new Country("NC", "New Caledonia", "+687", R.drawable.flag_nc, "XPF"),
            new Country("NE", "Niger", "+227", R.drawable.flag_ne, "XOF"),
            new Country("NF", "Norfolk Island", "+672", R.drawable.flag_nf, "AUD"),
            new Country("NG", "Nigeria", "+234", R.drawable.flag_ng, "NGN"),
            new Country("NI", "Nicaragua", "+505", R.drawable.flag_ni, "NIO"),
            new Country("NL", "Netherlands", "+31", R.drawable.flag_nl, "EUR"),
            new Country("NO", "Norway", "+47", R.drawable.flag_no, "NOK"),
            new Country("NP", "Nepal", "+977", R.drawable.flag_np, "NPR"),
            new Country("NR", "Nauru", "+674", R.drawable.flag_nr, "AUD"),
            new Country("NU", "Niue", "+683", R.drawable.flag_nu, "NZD"),
            new Country("NZ", "New Zealand", "+64", R.drawable.flag_nz, "NZD"),
            new Country("OM", "Oman", "+968", R.drawable.flag_om, "OMR"),
            new Country("PA", "Panama", "+507", R.drawable.flag_pa, "PAB"),
            new Country("PE", "Peru", "+51", R.drawable.flag_pe, "PEN"),
            new Country("PF", "French Polynesia", "+689", R.drawable.flag_pf, "XPF"),
            new Country("PG", "Papua New Guinea", "+675", R.drawable.flag_pg, "PGK"),
            new Country("PH", "Philippines", "+63", R.drawable.flag_ph, "PHP"),
            new Country("PK", "Pakistan", "+92", R.drawable.flag_pk, "PKR"),
            new Country("PL", "Poland", "+48", R.drawable.flag_pl, "PLN"),
            new Country("PM", "Saint Pierre and Miquelon", "+508", R.drawable.flag_pm, "EUR"),
            new Country("PN", "Pitcairn", "+872", R.drawable.flag_pn, "NZD"),
            new Country("PR", "Puerto Rico", "+1", R.drawable.flag_pr, "USD"),
            new Country("PS", "Palestinian Territory, Occupied", "+970", R.drawable.flag_ps, "ILS"),
            new Country("PT", "Portugal", "+351", R.drawable.flag_pt, "EUR"),
            new Country("PW", "Palau", "+680", R.drawable.flag_pw, "USD"),
            new Country("PY", "Paraguay", "+595", R.drawable.flag_py, "PYG"),
            new Country("QA", "Qatar", "+974", R.drawable.flag_qa, "QAR"),
            new Country("RE", "Reunion", "+262", R.drawable.flag_re, "EUR"),
            new Country("RO", "Romania", "+40", R.drawable.flag_ro, "RON"),
            new Country("RS", "Serbia", "+381", R.drawable.flag_rs, "RSD"),
            new Country("RU", "Russia", "+7", R.drawable.flag_ru, "RUB"),
            new Country("RW", "Rwanda", "+250", R.drawable.flag_rw, "RWF"),
            new Country("SA", "Saudi Arabia", "+966", R.drawable.flag_sa, "SAR"),
            new Country("SB", "Solomon Islands", "+677", R.drawable.flag_sb, "SBD"),
            new Country("SC", "Seychelles", "+248", R.drawable.flag_sc, "SCR"),
            new Country("SD", "Sudan", "+249", R.drawable.flag_sd, "SDG"),
            new Country("SE", "Sweden", "+46", R.drawable.flag_se, "SEK"),
            new Country("SG", "Singapore", "+65", R.drawable.flag_sg, "SGD"),
            new Country("SH", "Saint Helena, Ascension and Tristan Da Cunha", "+290", R.drawable.flag_sh,
                    "SHP"),
            new Country("SI", "Slovenia", "+386", R.drawable.flag_si, "EUR"),
            new Country("SJ", "Svalbard and Jan Mayen", "+47", R.drawable.flag_sj, "NOK"),
            new Country("SK", "Slovakia", "+421", R.drawable.flag_sk, "EUR"),
            new Country("SL", "Sierra Leone", "+232", R.drawable.flag_sl, "SLL"),
            new Country("SM", "San Marino", "+378", R.drawable.flag_sm, "EUR"),
            new Country("SN", "Senegal", "+221", R.drawable.flag_sn, "XOF"),
            new Country("SO", "Somalia", "+252", R.drawable.flag_so, "SOS"),
            new Country("SR", "Suriname", "+597", R.drawable.flag_sr, "SRD"),
            new Country("SS", "South Sudan", "+211", R.drawable.flag_ss, "SSP"),
            new Country("ST", "Sao Tome and Principe", "+239", R.drawable.flag_st, "STD"),
            new Country("SV", "El Salvador", "+503", R.drawable.flag_sv, "SVC"),
            new Country("SX", "Sint Maarten", "+1", R.drawable.flag_sx, "ANG"),
            new Country("SY", "Syrian Arab Republic", "+963", R.drawable.flag_sy, "SYP"),
            new Country("SZ", "Swaziland", "+268", R.drawable.flag_sz, "SZL"),
            new Country("TC", "Turks and Caicos Islands", "+1", R.drawable.flag_tc, "USD"),
            new Country("TD", "Chad", "+235", R.drawable.flag_td, "XAF"),
            new Country("TF", "French Southern Territories", "+262", R.drawable.flag_tf, "EUR"),
            new Country("TG", "Togo", "+228", R.drawable.flag_tg, "XOF"),
            new Country("TH", "Thailand", "+66", R.drawable.flag_th, "THB"),
            new Country("TJ", "Tajikistan", "+992", R.drawable.flag_tj, "TJS"),
            new Country("TK", "Tokelau", "+690", R.drawable.flag_tk, "NZD"),
            new Country("TL", "East Timor", "+670", R.drawable.flag_tl, "USD"),
            new Country("TM", "Turkmenistan", "+993", R.drawable.flag_tm, "TMT"),
            new Country("TN", "Tunisia", "+216", R.drawable.flag_tn, "TND"),
            new Country("TO", "Tonga", "+676", R.drawable.flag_to, "TOP"),
            new Country("TR", "Turkey", "+90", R.drawable.flag_tr, "TRY"),
            new Country("TT", "Trinidad and Tobago", "+1", R.drawable.flag_tt, "TTD"),
            new Country("TV", "Tuvalu", "+688", R.drawable.flag_tv, "AUD"),
            new Country("TW", "Taiwan", "+886", R.drawable.flag_tw, "TWD"),
            new Country("TZ", "Tanzania, United Republic of", "+255", R.drawable.flag_tz, "TZS"),
            new Country("UA", "Ukraine", "+380", R.drawable.flag_ua, "UAH"),
            new Country("UG", "Uganda", "+256", R.drawable.flag_ug, "UGX"),
            new Country("UM", "U.S. Minor Outlying Islands", "+1", R.drawable.flag_um, "USD"),
            new Country("US", "United States", "+1", R.drawable.flag_us, "USD"),
            new Country("UY", "Uruguay", "+598", R.drawable.flag_uy, "UYU"),
            new Country("UZ", "Uzbekistan", "+998", R.drawable.flag_uz, "UZS"),
            new Country("VA", "Holy See (Vatican City State)", "+379", R.drawable.flag_va, "EUR"),
            new Country("VC", "Saint Vincent and the Grenadines", "+1", R.drawable.flag_vc, "XCD"),
            new Country("VE", "Venezuela, Bolivarian Republic of", "+58", R.drawable.flag_ve, "VEF"),
            new Country("VG", "Virgin Islands, British", "+1", R.drawable.flag_vg, "USD"),
            new Country("VI", "Virgin Islands, U.S.", "+1", R.drawable.flag_vi, "USD"),
            new Country("VN", "Vietnam", "+84", R.drawable.flag_vn, "VND"),
            new Country("VU", "Vanuatu", "+678", R.drawable.flag_vu, "VUV"),
            new Country("WF", "Wallis and Futuna", "+681", R.drawable.flag_wf, "XPF"),
            new Country("WS", "Samoa", "+685", R.drawable.flag_ws, "WST"),
            new Country("XK", "Kosovo", "+383", R.drawable.flag_xk, "EUR"),
            new Country("YE", "Yemen", "+967", R.drawable.flag_ye, "YER"),
            new Country("YT", "Mayotte", "+262", R.drawable.flag_yt, "EUR"),
            new Country("ZA", "South Africa", "+27", R.drawable.flag_za, "ZAR"),
            new Country("ZM", "Zambia", "+260", R.drawable.flag_zm, "ZMW"),
            new Country("ZW", "Zimbabwe", "+263", R.drawable.flag_zw, "USD"),
    };

    public static List<Country> getCountryList() {
        List<Country> countries = Arrays.asList(COUNTRIES);

        Collections.sort(countries, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        return countries;
    }

}
