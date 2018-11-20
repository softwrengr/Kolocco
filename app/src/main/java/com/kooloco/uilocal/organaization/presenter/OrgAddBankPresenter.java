package com.kooloco.uilocal.organaization.presenter;/**
 * Created by hlink44 on 11/10/17.
 */

import com.google.gson.Gson;
import com.kooloco.core.Session;
import com.kooloco.data.repository.KoolocoRepository;
import com.kooloco.di.PerActivity;
import com.kooloco.exception.ServerException;
import com.kooloco.model.Bank;
import com.kooloco.model.CountryList;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.OrgBankSetPaymentRules;
import com.kooloco.model.Response;
import com.kooloco.model.UploadDocument;
import com.kooloco.model.User;
import com.kooloco.ui.base.BasePresenter;
import com.kooloco.uilocal.organaization.view.OrgAddBankView;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.SubscribeWithView;
import com.kooloco.util.TimeConvertUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@PerActivity
public class OrgAddBankPresenter extends BasePresenter<OrgAddBankView> {
    @Inject
    Session session;

    @Inject
    KoolocoRepository koolocoRepository;

    @Inject
    public OrgAddBankPresenter() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void imagePick(ImagePicker.ImagePickerResult imagePickerResult) {
        navigator.pickImage(imagePickerResult);
    }


    public void openLocalApp() {

        navigator.startHomeLocalActivity().byFinishingAll().start();
    }

    public void callWs(boolean isEdit, String firstName, String lastName, String bankName, String address, String zipCode, String dateOfBirth, String document, OrgBankSetPaymentRules orgBankSetPaymentRules, String orgId, String countryId, Map<String, String> fields,String additionalInformation) {
        view.showLoader();

        Map<String, String> map = new HashMap<>();

        map.put("local_id", session.getUser().getId());
        map.put("first_name", firstName);
        map.put("last_name", lastName);
        map.put("dob_month", TimeConvertUtils.dateTimeConvertLocalToLocal(dateOfBirth, "dd MMM, yyyy", "MM"));
        map.put("dob_year", TimeConvertUtils.dateTimeConvertLocalToLocal(dateOfBirth, "dd MMM, yyyy", "yyyy"));
        map.put("dob_day", TimeConvertUtils.dateTimeConvertLocalToLocal(dateOfBirth, "dd MMM, yyyy", "dd"));
        map.put("bank_name", bankName);
        map.put("zip", zipCode);
        map.put("address", address);

        map.put("country_id", countryId);
        map.put("form_field", new Gson().toJson(fields));

        map.put("bank_for", "O");
        map.put("organisation_id", orgId);

        map.put("additional_information", additionalInformation);

        File file = new File(document);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("document", file.getName(), requestFile);


        koolocoRepository.uploadDocument(body).subscribe(new SubscribeWithView<Response<UploadDocument>>(view) {
            @Override
            public void onSuccess(Response<UploadDocument> uploadImageResponse) {

                map.put("doc_path", uploadImageResponse.getData().getImage());


                koolocoRepository.setBankDetails(map).subscribe(new SubscribeWithView<Response<DashboardDetails>>(view) {
                    @Override
                    public void onSuccess(Response<DashboardDetails> organizationDetailsResponse) {
                        User user = session.getUser();

                        user.setRate(organizationDetailsResponse.getData().getLocalRating());
                        user.setFirstname(organizationDetailsResponse.getData().getFirstname());
                        user.setLastname(organizationDetailsResponse.getData().getLastname());
                        user.setIsWantToCompelte(organizationDetailsResponse.getData().getIsWantToCompelte());
                        user.setProfileStatuses(organizationDetailsResponse.getData().getProfileStatuses());
                        user.setIsAdminApprove(organizationDetailsResponse.getData().getIsAdminApprove());
                        user.setIsAffilated(organizationDetailsResponse.getData().getIsAffilated());
                        session.setUser(user);

                        if (isEdit) {
                            view.clearData();
                            if (orgBankSetPaymentRules.isEdit()) {
                                callEditPaymentRules(orgBankSetPaymentRules.getMap());
                            } else {
                                callAddPaymentRules(orgBankSetPaymentRules.getMap());
                            }
                        } else {
                            view.hideLoader();
                            view.clearData();
                            getData(orgId);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.hideLoader();
                    }
                });

            }

            @Override
            public void onError(Throwable e) {

                super.onError(e);

                view.hideLoader();
            }
        });

    }

    public void getCountryList() {
        Map<String, String> map = new HashMap<>();

        koolocoRepository.getCountryList(map).subscribe(new SubscribeWithView<Response<List<CountryList>>>(view) {
            @Override
            public void onSuccess(Response<List<CountryList>> listResponse) {
                view.setDataCountry(listResponse.getData());
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.setDataCountry(new ArrayList<>());
                view.hideLoader();
            }
        });
    }

    public void getData(String orgId) {

        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("bank_for", "O");
        map.put("organisation_id", orgId);

        koolocoRepository.getBankList(map).subscribe(new SubscribeWithView<Response<List<Bank>>>(view) {
            @Override
            public void onSuccess(Response<List<Bank>> listResponse) {
                view.setDataBank(listResponse.getData());
                getCountryList();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ServerException) {
                    ServerException exception = (ServerException) e;
                    if (exception.getServerCode() != 0) {
                        super.onError(e);
                    } else if (exception.getServerCode() == 0) {
                        view.setDataBank(new ArrayList<>());
                        getCountryList();
                    }
                } else {
                    super.onError(e);
                }

            }
        });


    }

    public void callChooseSelect(Bank bank) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", session.getUser().getId());
        map.put("bank_id", bank.getBankId());
        map.put("bank_for", "O");

        koolocoRepository.changeDefaultBank(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.showMessage(listResponse.getMessage());
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void callDelete(int position, Bank bank) {
        view.showLoader();
        Map<String, String> map = new HashMap<>();
        map.put("bank_id", bank.getBankId());
        map.put("user_id", session.getUser().getId());
        map.put("bank_for", "O");


        koolocoRepository.deleteBank(map).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.deleteData(position);
                view.hideLoader();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });

    }


    public void callAddPaymentRules(Map<String, String> param) {

        koolocoRepository.setPaymentRule(param).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();

                navigator.openChooseLangaugeView().clearHistory("OrgAddBank");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

    public void callEditPaymentRules(Map<String, String> param) {

        koolocoRepository.editPaymentRule(param).subscribe(new SubscribeWithView<Response>(view) {
            @Override
            public void onSuccess(Response listResponse) {
                view.hideLoader();

                navigator.openChooseLangaugeView().clearHistory("OrgAddBank");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.hideLoader();
            }
        });
    }

}
