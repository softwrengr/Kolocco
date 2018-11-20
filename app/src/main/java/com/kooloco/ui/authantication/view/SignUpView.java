package com.kooloco.ui.authantication.view;
/**
 * Created by hlink44 on 24/8/17.
 */

import com.kooloco.model.FbGoogleData;
import com.kooloco.ui.base.RootView;

public interface SignUpView extends RootView {

    void setDataSignup(FbGoogleData fbGoogleData);

    void setDataSignupNew(FbGoogleData fbGoogleData);

}
