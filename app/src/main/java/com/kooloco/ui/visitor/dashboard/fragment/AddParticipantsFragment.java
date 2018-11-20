package com.kooloco.ui.visitor.dashboard.fragment;
/**
 * Created by hlink44 on 25/8/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.core.Session;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.VisitorBooking;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.visitor.dashboard.adapter.AddParticipantsAdapter;
import com.kooloco.ui.visitor.dashboard.presenter.AddParticipantsPresenter;
import com.kooloco.ui.visitor.dashboard.view.AddParticipantsView;
import com.kooloco.util.Validator;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddParticipantsFragment extends BaseFragment<AddParticipantsPresenter, AddParticipantsView> implements AddParticipantsView {

    @BindView(R.id.customTextViewValue)
    AppCompatTextView customTextViewValue;
    @BindView(R.id.recyclerAddParticipants)
    RecyclerView recyclerAddParticipants;
    List<String> strings;

    int selectValue = 0;
    AddParticipantsAdapter addParticipantsAdapter;

    @Inject
    Validator validator;
    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewName)
    AppCompatTextView customTextViewName;
    @BindView(R.id.customTextViewServiceType)
    AppCompatTextView customTextViewServiceType;
    @BindView(R.id.customTextViewServiceTypeValue)
    AppCompatTextView customTextViewServiceTypeValue;
    @BindView(R.id.customTextViewServiceHour)
    AppCompatTextView customTextViewServiceHour;
    @BindView(R.id.imageViewUserProfile)
    ImageView imageViewUserProfile;
    @BindView(R.id.customTextViewUserName)
    AppCompatTextView customTextViewUserName;
    @BindView(R.id.imageViewClose)
    ImageView imageViewClose;
    @BindView(R.id.linearLayoutAddPartUser)
    LinearLayout linearLayoutAddPartUser;
    @BindView(R.id.minus)
    FrameLayout minus;
    @BindView(R.id.plus)
    FrameLayout plus;
    @BindView(R.id.textViewLabelDuration)
    AppCompatTextView textViewLabelDuration;

    private VisitorBooking visitorBooking;

    @Inject
    Session session;

    int maxPart;

    @Override
    protected int createLayout() {
        return R.layout.fragment_visitor_add_participants;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }


    @Override
    protected AddParticipantsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
    /*    linearLayoutAddPartUser.setVisibility(View.VISIBLE);
        if (!session.getUser().getProfileImage().isEmpty()) {
            Picasso.with(getActivity()).load(session.getUser().getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewUserProfile);
        }
        textViewLabelDuration.setVisibility(visitorBooking.isLocalSelectTime() ? View.VISIBLE : View.INVISIBLE);

        customTextViewUserName.setText(session.getUser().getEmail());

        imageViewClose.setVisibility((visitorBooking.getIsGroupBookingExperience().equalsIgnoreCase("0")) ? View.INVISIBLE : View.VISIBLE);

        plus.setVisibility((visitorBooking.getIsGroupBookingExperience().equalsIgnoreCase("0")) ? View.INVISIBLE : View.VISIBLE);
        minus.setVisibility((visitorBooking.getIsGroupBookingExperience().equalsIgnoreCase("0")) ? View.INVISIBLE : View.VISIBLE);

        if (visitorBooking.getIsGroupBookingExperience().equalsIgnoreCase("0")) {
            maxPart = 1;
        } else {
            maxPart = Integer.parseInt(visitorBooking.getParticipantExperience());
        }

        if (!visitorBooking.getLocalImage().isEmpty()) {
            Picasso.with(getActivity()).load(visitorBooking.getLocalImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        }

        customTextViewName.setText(visitorBooking.getLocalName());
        customTextViewServiceType.setText(" ");

        customTextViewServiceTypeValue.setText(visitorBooking.getSportName() + " " + visitorBooking.getExperienceTitle() + " with ");

        customTextViewServiceHour.setText(visitorBooking.getUserSelectTime() + " " + getActivity().getResources().getString(R.string.hr));

        strings = new ArrayList<>();

        selectValue = strings.size();

        if (selectValue == 0) {
            selectValue = 1;
        }
        customTextViewValue.setText("" + selectValue);

        addParticipantsAdapter = new AddParticipantsAdapter(getActivity(), strings, new AddParticipantsAdapter.CallBack() {
            @Override
            public void onClickDelete(int position) {
                strings = addParticipantsAdapter.getStrings();
                if (linearLayoutAddPartUser.getVisibility() == View.GONE) {
                    if (strings.size() == 1) {
                        showMessage(getString(R.string.val_add_participants));
                    } else {
                        remove(position);
                    }
                } else {
                    remove(position);
                }
            }
        });

        recyclerAddParticipants.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerAddParticipants.setAdapter(addParticipantsAdapter);*/

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbarIsolatedAppointment(this.getClass().getSimpleName());
    }

  /*  @OnClick({R.id.plus, R.id.minus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.plus:
                isAdd(true);
                break;
            case R.id.minus:
                isAdd(false);
                break;
        }
    }
*/
/*    private void isAdd(boolean isAdd) {
        if (isAdd) {
            if ((selectValue - 1) < maxPart) {
                selectValue = selectValue + 1;
                strings = addParticipantsAdapter.getStrings();
                strings.add("");
                addParticipantsAdapter.notifyItemInserted(selectValue);
            }
        } else {
            if (selectValue != 1) {
                selectValue = selectValue - 1;
                strings = addParticipantsAdapter.getStrings();
                strings.remove((selectValue - 1));
                addParticipantsAdapter.notifyItemRemoved((selectValue - 1));
                if (selectValue == 1) {
                    hideKeyBoard();
                }
            }
        }

        customTextViewValue.setText("" + selectValue);
    }*/

    private void remove(int position) {
        if (selectValue != 1) {
            selectValue = selectValue - 1;

            if (position < strings.size()) {
                strings.remove(position);
                addParticipantsAdapter.notifyItemRemoved(position);
            }
            if (selectValue == 1) {
                hideKeyBoard();
            }
        }
        customTextViewValue.setText("" + selectValue);
    }

  /*  @OnClick(R.id.buttonNext)
    public void onViewClickedNext() {
        if (addParticipantsAdapter != null) {
            List<String> stringsData = addParticipantsAdapter.getStrings();

            boolean isValid = true;

            for (String s : stringsData) {
                if (s.isEmpty()) {
                    showMessage("Please enter add participant email");
                    isValid = false;
                    break;
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                        showMessage("Please enter valid add participant email");
                        isValid = false;
                        break;
                    }
                }
            }

*//*
            if (stringsData.size() == 0) {
                showMessage("Please add at least one extra participant");
                return;
            }
*//*

            if (isValid) {
                hideKeyBoard();
                String addUserEmail = "";

                if (linearLayoutAddPartUser.getVisibility() == View.VISIBLE) {
                    addUserEmail = session.getUser().getEmail();
                } else {
                    addUserEmail = "";
                }

                presenter.openAppointmentSummery(visitorBooking, addParticipantsAdapter.getStrings(), addUserEmail);
            }

        }
    }*/


    @OnClick({R.id.imageViewBack, R.id.textViewSkip, R.id.imageViewClose})
    public void onViewClickedTool(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                hideKeyBoard();
                goBack();
                break;
            case R.id.textViewSkip:
                hideKeyBoard();
                //presenter.openAppointmentSummery(visitorBooking);
                break;
            case R.id.imageViewClose:
                if (strings.size() == 0) {
                    showMessage(getString(R.string.val_add_participants));
                    return;
                }
                selectValue = selectValue - 1;
                customTextViewValue.setText("" + selectValue);
                linearLayoutAddPartUser.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void setBookingData(VisitorBooking visitorBooking) {
        this.visitorBooking = visitorBooking;
    }

}
