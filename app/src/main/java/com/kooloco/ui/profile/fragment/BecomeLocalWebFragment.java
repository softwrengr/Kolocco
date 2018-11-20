package com.kooloco.ui.profile.fragment;
/**
 * Created by hlink on 19/3/18.
 */

import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.kooloco.R;
import com.kooloco.data.URLFactory;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.presenter.BecomeLocalWebPresenter;
import com.kooloco.ui.profile.view.BecomeLocalWebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class BecomeLocalWebFragment extends BaseFragment<BecomeLocalWebPresenter, BecomeLocalWebView> implements BecomeLocalWebView {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.buttonStratNow)
    AppCompatButton buttonStratNow;

    @Override
    protected int createLayout() {
        return R.layout.fragment_becom_local_web;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected BecomeLocalWebView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        showLoader();

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView1, int newProgress) {

                if (newProgress == 100) {
                    // Page loading finish
                    BecomeLocalWebFragment.this.hideLoader();
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            forceToSetLocal();
        }

        webview.loadUrl(URLFactory.BECOME_LOCAL_WEB_URL);
    }


    @OnClick(R.id.buttonStratNow)
    public void onClick() {
        presenter.openBecomeLocalScreen();
    }

    @OnClick(R.id.imageViewBack)
    public void onClickBack() {
        goBack();
    }
}
