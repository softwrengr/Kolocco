package com.kooloco.ui.profile.fragment;
/**
 * Created by hlink44 on 27/9/17.
 */

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kooloco.R;
import com.kooloco.data.URLFactory;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.HelpAndFaq;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.profile.adapter.HelpAndFaqAdapter;
import com.kooloco.ui.profile.presenter.HelpAndFaqPresenter;
import com.kooloco.ui.profile.view.HelpAndFaqView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HelpAndFaqFragment extends BaseFragment<HelpAndFaqPresenter, HelpAndFaqView> implements HelpAndFaqView {

    @BindView(R.id.recyclerHelpAndFaq)
    RecyclerView recyclerHelpAndFaq;


    @Override
    protected int createLayout() {
        return R.layout.fragment_help_and_faq;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected HelpAndFaqView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        presenter.getData();
    }

    @Override
    public void setData(List<HelpAndFaq> data) {
        recyclerHelpAndFaq.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerHelpAndFaq.setAdapter(new HelpAndFaqAdapter(getActivity(), data));
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    @OnClick(R.id.buttonGiveFeedback)
    public void onClick() {
        try {
            String url = URLFactory.FEEDBACKURL;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
