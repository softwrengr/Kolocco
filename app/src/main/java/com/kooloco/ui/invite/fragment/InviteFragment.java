package com.kooloco.ui.invite.fragment;
/**
 * Created by hlink44 on 25/9/17.
 */

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;

import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.invite.presenter.InvitePresenter;
import com.kooloco.ui.invite.view.InviteView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class InviteFragment extends BaseFragment<InvitePresenter, InviteView> implements InviteView {

    @BindView(R.id.customTextViewText)
    AppCompatTextView customTextViewText;

    @Inject
    Session session;
    @BindView(R.id.textViewReferralCode)
    AppCompatTextView textViewReferralCode;
    @BindView(R.id.textViewReferralAmount)
    AppCompatTextView textViewReferralAmount;

    @Override
    protected int createLayout() {
        return R.layout.fragment_invite;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected InviteView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        String text = getActivity().getResources().getString(R.string.invite_friends_text_1).replace("$$$", "<font color='" + getActivity().getResources().getColor(R.color.white) + "'> <b>CHF 10</b> </font>");
        text = text.replace("###", "<font color='" + getActivity().getResources().getColor(R.color.white) + "'> <b>CHF 5</b> </font>");

        customTextViewText.setText(Html.fromHtml(text));

        textViewReferralCode.setText(Html.fromHtml("<b>" + session.getUser().getReferralCode() + "</b>"));

        String textnew = getString(R.string.referral_amount_new, session.getUser().getReferralAmount()).replace("CHF " + session.getUser().getReferralAmount(), "<b>CHF " + session.getUser().getReferralAmount() + "</b>");

        textViewReferralAmount.setText(Html.fromHtml(textnew));

    }


    @OnClick(R.id.buttonInviteFriends)
    public void onViewClicked() {
        shareIntent(session.getUser().getReferralCode());
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClickedBack() {
        goBack();
    }

    private void shareIntent(String code) {
        String shareBody = getString(R.string.referral, code) + "\n" + "https://kooloco.page.link/referral";
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.kooloco_referral));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));
    }

    @OnClick(R.id.linearLayoutTermsConditions)
    public void onClick() {
        presenter.openWebOpenView(getString(R.string.toolbat_terms_conditions));
    }
}
