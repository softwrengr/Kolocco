package com.kooloco.ui.webOpen.fragment;
/**
 * Created by hlink on 22/8/18.
 */

import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.kooloco.R;
import com.kooloco.Startup;
import com.kooloco.constant.Common;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.webOpen.presenter.WebOpenPresenter;
import com.kooloco.ui.webOpen.view.WebOpenView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class WebOpenFragment extends BaseFragment<WebOpenPresenter, WebOpenView> implements WebOpenView {

    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;

    @BindView(R.id.webview)
    WebView webview;

    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;

    private String title;
    private String webUrl;


    @Override
    protected int createLayout() {
        return R.layout.fragment_web_open;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected WebOpenView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        toolbarTitle.setText(title);


        showLoader();
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView1, int newProgress) {

                if (newProgress == 100) {
                    // Page loading finish
                    WebOpenFragment.this.hideLoader();
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            forceToSetLocal();
        }

        webview.loadUrl(webUrl);

    }

    @OnClick(R.id.imageViewBack)
    public void onClickBack() {
        goBack();
    }

    @Override
    public void setData(String title, String url) {
        this.title = title;
        this.webUrl = url;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        webview.destroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
