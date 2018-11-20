package com.kooloco.data.datasource;


import com.kooloco.core.AppExecutors;
import com.kooloco.data.entity.ResponseEntity;
import com.kooloco.exception.ErrorCode;
import com.kooloco.exception.ServerException;
import com.kooloco.model.Response;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by hlink21 on 3/1/17.
 */

public class BaseDataSource {

    private final AppExecutors appExecutors;

    public BaseDataSource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    protected <T extends Response> Single<T> execute(Single<T> observable) {
        return observable
                .subscribeOn(Schedulers.from(appExecutors.networkIO()))
                .observeOn(Schedulers.from(appExecutors.mainThread())).doOnSuccess(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        if (t.getResponseCode() == ErrorCode.INVALID_FAILAR || t.getResponseCode() == ErrorCode.NO_DATA_FOUND || t.getResponseCode() == ErrorCode.ACCOUNT_INACTIVE || t.getResponseCode() == ErrorCode.OTP_VERIFICATION || t.getResponseCode() == ErrorCode.RATING_ALLREADY) {
                            throw new ServerException(t.getMessage(), t.getResponseCode());
                        }
                    }
                });
    }

    protected <T extends ResponseEntity> Single<T> executeMapper(Single<T> observable) {
        return observable
                .subscribeOn(Schedulers.from(appExecutors.networkIO()))
                .observeOn(Schedulers.from(appExecutors.mainThread())).doOnSuccess(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        if (t.responseCode == ErrorCode.INVALID_FAILAR || t.responseCode == ErrorCode.NO_DATA_FOUND || t.responseCode == ErrorCode.ACCOUNT_INACTIVE || t.responseCode == ErrorCode.OTP_VERIFICATION) {
                            throw new ServerException(t.message, t.responseCode);
                        }
                    }
                });
    }
}
