package com.kooloco.ui.expereince.fragment;
/**
 * Created by hlink on 23/4/18.
 */

import android.os.SystemClock;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
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
import com.kooloco.model.AddParticipants;
import com.kooloco.model.ExperienceBookingFlow;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.expereince.presenter.ExperinceAddParticipantsPresenter;
import com.kooloco.ui.expereince.view.ExperinceAddParticipantsView;
import com.kooloco.ui.visitor.dashboard.adapter.AddParticipantsAdapter;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class ExperinceAddParticipantsFragment extends BaseFragment<ExperinceAddParticipantsPresenter, ExperinceAddParticipantsView> implements ExperinceAddParticipantsView {

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
    @BindView(R.id.customTextViewExpTitle)
    AppCompatTextView customTextViewExpTitle;
    @BindView(R.id.textViewSelectDate)
    AppCompatTextView textViewSelectDate;
    @BindView(R.id.textViewSelectTime)
    AppCompatTextView textViewSelectTime;
    @BindView(R.id.textViewPrice)
    AppCompatTextView textViewPrice;
    @BindView(R.id.becomALocalEditTextName)
    AppCompatEditText becomALocalEditTextName;
    @BindView(R.id.minus)
    FrameLayout minus;
    @BindView(R.id.customTextViewValue)
    AppCompatTextView customTextViewValue;
    @BindView(R.id.plus)
    FrameLayout plus;
    @BindView(R.id.imageViewUserProfile)
    ImageView imageViewUserProfile;
    @BindView(R.id.customTextViewUserName)
    AppCompatTextView customTextViewUserName;
    @BindView(R.id.imageViewClose)
    ImageView imageViewClose;
    @BindView(R.id.linearLayoutAddPartUser)
    LinearLayout linearLayoutAddPartUser;
    @BindView(R.id.recyclerAddParticipants)
    RecyclerView recyclerAddParticipants;
    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;
    List<AddParticipants> strings;

    int selectValue = 0;
    int maxPart;
    AddParticipantsAdapter addParticipantsAdapter;
    long mLastClickTime = 0;
    long mLastClickTimeDelete = 0;

    @Inject
    Session session;
    @BindView(R.id.textViewCurrencyType)
    AppCompatTextView textViewCurrencyType;
    private ExperienceBookingFlow experienceBookingFlow;

    @Override
    protected int createLayout() {
        return R.layout.ecperience_add_participants_fragment;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ExperinceAddParticipantsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        setData();
    }

    private void setData() {

        setHeadData();


        textViewCurrencyType.setText(BaseActivity.currency);

        customTextViewUserName.setText(session.getUser().getEmail());

        Picasso.with(getActivity()).load(session.getUser().getProfileImage()).transform(new CircleTransform()).placeholder(R.drawable.user_round).error(R.drawable.user_round).into(imageViewUserProfile);

        textViewSelectDate.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(experienceBookingFlow.getSelectDate(), "yyyy-MM-dd", "dd MMM yyyy"));


        String time;

        if (experienceBookingFlow.getSchedulePrice().getIsMultipleDay().equalsIgnoreCase("1")) {
            time = getActivity().getResources().getString(R.string.schdule_price_start_time) + " " + TimeConvertUtils.dateTimeConvertLocalToLocal(experienceBookingFlow.getSchedulePrice().getStartTime(), "HH:mm:ss", "hh:mm a") + " " + getActivity().getResources().getString(R.string.schdule_and_price_for) + " " + experienceBookingFlow.getSchedulePrice().getDurationInDays() + " " + getActivity().getResources().getString(R.string.schdule_price_days);
        } else {
            time = "" + getActivity().getResources().getString(R.string.sch_from) + " " + TimeConvertUtils.dateTimeConvertLocalToLocal(experienceBookingFlow.getSchedulePrice().getStartTime(), "HH:mm:ss", "hh:mm a") + " " + getActivity().getResources().getString(R.string.sch_to) + " " + TimeConvertUtils.dateTimeConvertLocalToLocal(experienceBookingFlow.getSchedulePrice().getEndTime(), "HH:mm:ss", "hh:mm a");
        }

        textViewSelectTime.setText(time);
        textViewPrice.setText(experienceBookingFlow.getSchedulePrice().getPrice());

        imageViewClose.setVisibility((experienceBookingFlow.getSchedulePrice().getReminParticipant().equalsIgnoreCase("1")) ? View.INVISIBLE : View.VISIBLE);

        plus.setBackground((experienceBookingFlow.getSchedulePrice().getReminParticipant().equalsIgnoreCase("1")) ? getActivity().getDrawable(R.drawable.add_part_plus) : getActivity().getResources().getDrawable(R.drawable.add_participant_plus));
        minus.setBackground((experienceBookingFlow.getSchedulePrice().getReminParticipant().equalsIgnoreCase("1")) ? getActivity().getDrawable(R.drawable.add_part_minus) : getActivity().getResources().getDrawable(R.drawable.add_participant_minus));

        plus.setClickable(!experienceBookingFlow.getSchedulePrice().getReminParticipant().equalsIgnoreCase("1"));
        minus.setClickable(!experienceBookingFlow.getSchedulePrice().getReminParticipant().equalsIgnoreCase("1"));

        maxPart = Integer.parseInt(experienceBookingFlow.getSchedulePrice().getReminParticipant()) - 1;

        strings = new ArrayList<>();

        selectValue = strings.size();

        if (selectValue == 0) {
            selectValue = 1;
        }


        customTextViewValue.setText("" + selectValue);

        addParticipantsAdapter = new AddParticipantsAdapter(getActivity(), strings, new AddParticipantsAdapter.CallBack() {
            @Override
            public void onClickDelete(int position) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();


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
        recyclerAddParticipants.setAdapter(addParticipantsAdapter);

        recyclerAddParticipants.setItemViewCacheSize(30);
    }

    private void remove(int position) {
        if (selectValue != 1) {
            selectValue = selectValue - 1;

            if (position < strings.size()) {
                strings.remove(position);
                addParticipantsAdapter.notifyDataSetChanged();
            }
            if (selectValue == 1) {
                hideKeyBoard();
            }
        }
        customTextViewValue.setText("" + selectValue);
        setPriceData();

    }

    @OnClick({R.id.imageViewBack, R.id.minus, R.id.plus, R.id.imageViewClose, R.id.buttonNext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                hideKeyBoard();
                goBack();
                break;
            case R.id.minus:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
                    break;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                isAdd(false);
                break;
            case R.id.plus:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
                    break;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                isAdd(true);
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
            case R.id.buttonNext:
                if (addParticipantsAdapter != null) {

                    List<String> stringsData = new ArrayList<>();
                    for (AddParticipants addParticipants : strings) {
                        stringsData.add(addParticipants.getEmail());
                    }

                    boolean isValid = true;

                    for (String s : stringsData) {
                        if (s.isEmpty()) {
                            isValid = true;
                        } else {
                            if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                                showMessage(getString(R.string.val_aprticipant_email));
                                isValid = false;
                                break;
                            }
                        }
                    }

                    if (isValid) {
                        hideKeyBoard();
                        String addUserEmail = "";

                        if (linearLayoutAddPartUser.getVisibility() == View.VISIBLE) {
                            addUserEmail = session.getUser().getEmail();
                        } else {
                            addUserEmail = "";
                        }

                        List<String> stringsDataApi = new ArrayList<>();
                        for (AddParticipants addParticipants : strings) {
                            stringsDataApi.add(addParticipants.getEmail());
                        }


                        presenter.openAppointmentSummery(experienceBookingFlow, stringsDataApi, addUserEmail);
                    }

                }
                break;
        }
    }

    private void isAdd(boolean isAdd) {
        if (isAdd) {
            if ((selectValue - 1) < maxPart) {
                selectValue = selectValue + 1;
                strings.add(new AddParticipants());
                addParticipantsAdapter.notifyDataSetChanged();
            }
        } else {
            if (selectValue != 1) {
                selectValue = selectValue - 1;
                strings.remove((selectValue - 1));
                addParticipantsAdapter.notifyDataSetChanged();
                if (selectValue == 1) {
                    hideKeyBoard();
                }
            }
        }

        customTextViewValue.setText("" + selectValue);

        setPriceData();

    }

    private void setHeadData() {
        customTextViewLocalName.setText(experienceBookingFlow.getLocalName());
        Picasso.with(getActivity()).load(experienceBookingFlow.getLocalProfile()).transform(new CircleTransform()).placeholder(R.drawable.user_round).error(R.drawable.user_round).into(imageViewProfile);
        customTextViewExpTitle.setText(experienceBookingFlow.getExpereinceNew().getTitle());
    }

    @Override
    public void setExpBookingFlowData(ExperienceBookingFlow experienceBookingFlow) {
        this.experienceBookingFlow = experienceBookingFlow;
    }

    private void setPriceData() {
        Double price = 0.0;

        try {
            price = Double.parseDouble(experienceBookingFlow.getSchedulePrice().getPrice());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        textViewPrice.setText(convertFormat(roundDouble(price * selectValue)));
    }
}
