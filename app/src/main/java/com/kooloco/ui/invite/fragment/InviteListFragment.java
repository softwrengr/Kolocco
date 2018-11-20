package com.kooloco.ui.invite.fragment;
/**
 * Created by hlink44 on 25/9/17.
 */

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.Invite;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.invite.adapter.InviteListAdapter;
import com.kooloco.ui.invite.presenter.InviteListPresenter;
import com.kooloco.ui.invite.view.InviteListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InviteListFragment extends BaseFragment<InviteListPresenter, InviteListView> implements InviteListView {

    @BindView(R.id.buttonInviteFriends)
    AppCompatButton buttonInviteFriends;
    @BindView(R.id.recyclerFriends)
    RecyclerView recyclerFriends;

    List<Invite> invites = new ArrayList<>();
    @BindView(R.id.linearLayoutSelectAll)
    LinearLayout linearLayoutSelectAll;
    @BindView(R.id.signupTermsAndConditions)
    AppCompatCheckBox signupTermsAndConditions;

    @Override
    protected int createLayout() {
        return R.layout.fragment_invite_list;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected InviteListView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        presenter.getData();
    }

    @Override
    public void setData(List<Invite> data) {
        invites.addAll(data);
        recyclerFriends.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerFriends.setAdapter(new InviteListAdapter(getActivity(), data, 0));
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }


    @OnClick(R.id.buttonInviteFriends)
    public void onViewClickedAll() {
        getActivity().finish();
    }

    @OnClick(R.id.linearLayoutSelectAll)
    public void onClick() {

        signupTermsAndConditions.setChecked(!signupTermsAndConditions.isChecked());
        recyclerFriends.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerFriends.setAdapter(new InviteListAdapter(getActivity(), invites, signupTermsAndConditions.isChecked() ? 1 : 0));

    }
}
