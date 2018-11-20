package com.kooloco.ui.base;


import com.kooloco.constant.LocalOrderAction;
import com.kooloco.model.Order;

import io.reactivex.disposables.Disposable;

/**
 * Created by hlink21 on 25/4/16.
 */
public interface RootView {

    void showMessage(String message);

    void showMessageFullTime(String message);


    void showLoader();

    void hideLoader();

    void hideKeyBoard();

    void showKeyBoard();

    void onError(Throwable throwable);

    void addDisposable(Disposable disposable);

    void uploadImages(String fileName, String uploadType, BaseFragment.CallBackUploadImages callBackUploadImages);

    void localOrderAction(Order order, LocalOrderAction localOrderAction, BaseFragment.CallBackOrderAction callBackOrderAction);

    void filterData(BaseFragment.CallBackFilter callBackFilter, boolean isClear, boolean isHome);


    void deleteRecentChat(String orderId);

    void hideUploadImageDialog();

    void clearAllNotification(String name);

    void changeAppLanguage(String language);

    void changeAppLanguageSetDefault();

}
