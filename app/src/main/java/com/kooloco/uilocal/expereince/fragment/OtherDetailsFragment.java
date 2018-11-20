package com.kooloco.uilocal.expereince.fragment;
/**
 * Created by hlink on 16/4/18.
 */

import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.OtheDetailsResponse;
import com.kooloco.model.OtherDetailsFieldsSelect;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.expereince.adapter.ExperienceOtherDetailsSelectionFeildsAdapter;
import com.kooloco.uilocal.expereince.presenter.OtherDetailsPresenter;
import com.kooloco.uilocal.expereince.view.OtherDetailsView;
import com.kooloco.util.FirstCapEditText;
import com.kooloco.util.Validator;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hlink on 8/1/18.
 */

public class OtherDetailsFragment extends BaseFragment<OtherDetailsPresenter, OtherDetailsView> implements OtherDetailsView {

    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.editTextExpHighlights)
    FirstCapEditText editTextExpHighlights;
    @BindView(R.id.editTextExpInclude)
    FirstCapEditText editTextExpInclude;
    @BindView(R.id.editTextExpNotInclude)
    FirstCapEditText editTextExpNotInclude;
    @BindView(R.id.editTextHasTags)
    AppCompatEditText editTextHasTags;
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;
    @BindView(R.id.buttonNext)
    AppCompatButton buttonNext;
    @BindView(R.id.textViewRecommendedLevel)
    AppCompatEditText textViewRecommendedLevel;
    @BindView(R.id.recyclerRecommendedLevel)
    RecyclerView recyclerRecommendedLevel;
    @BindView(R.id.linearLayoutRecommendedLevel)
    LinearLayout linearLayoutRecommendedLevel;
    @BindView(R.id.textViewPerfectFor)
    AppCompatEditText textViewPerfectFor;
    @BindView(R.id.recyclerPerfectFor)
    RecyclerView recyclerPerfectFor;
    @BindView(R.id.linearLayoutPerfectFor)
    LinearLayout linearLayoutPerfectFor;
    @BindView(R.id.customTextViewAll)
    AppCompatTextView customTextViewAll;
    private boolean isEdit;
    List<String> hasTag = new ArrayList<>();
    @Inject
    Validator validator;

    List<OtherDetailsFieldsSelect> otherDetailsFieldsSelectsRec;
    List<OtherDetailsFieldsSelect> otherDetailsFieldsSelectsPerfect;
    ExperienceOtherDetailsSelectionFeildsAdapter experienceOtherDetailsSelectionFeildsAdapterRecom, experienceOtherDetailsSelectionFeildsAdapterPerfect;
    private String expId = "";

    @Override
    protected int createLayout() {
        return R.layout.exp_local_fragment_other_details;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected OtherDetailsView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        buttonNext.setText(isEdit ? getActivity().getResources().getString(R.string.button_done) : getActivity().getResources().getString(R.string.button_next));

        InputFilter[] inputFilters = {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.toString().matches("[a-zA-Z0-9]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        };

        editTextHasTags.setFilters(inputFilters);

        editTextHasTags.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (editTextHasTags.getText().toString().isEmpty()) {
                        return true;
                    }
                    onViewClickedAddTag();
                    return true;
                }
                return false;
            }
        });

        if (otherDetailsFieldsSelectsRec == null) {
            otherDetailsFieldsSelectsRec = new ArrayList<>();
        }

        if (otherDetailsFieldsSelectsPerfect == null) {
            otherDetailsFieldsSelectsPerfect = new ArrayList<>();

        }


        recyclerRecommendedLevel.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        experienceOtherDetailsSelectionFeildsAdapterRecom = new ExperienceOtherDetailsSelectionFeildsAdapter(getActivity(), otherDetailsFieldsSelectsRec, 3, new ExperienceOtherDetailsSelectionFeildsAdapter.CallBack() {
            @Override
            public void onClickItem(int position) {
                setDataTextView(textViewRecommendedLevel, otherDetailsFieldsSelectsRec);
            }

            @Override
            public void onClickMaximumError() {
                showMessage(getString(R.string.allow_maximum_3));
            }
        });

        recyclerRecommendedLevel.setAdapter(experienceOtherDetailsSelectionFeildsAdapterRecom);

        recyclerPerfectFor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        experienceOtherDetailsSelectionFeildsAdapterPerfect = new ExperienceOtherDetailsSelectionFeildsAdapter(getActivity(), otherDetailsFieldsSelectsPerfect, 3, new ExperienceOtherDetailsSelectionFeildsAdapter.CallBack() {
            @Override
            public void onClickItem(int position) {
                setDataTextView(textViewPerfectFor, otherDetailsFieldsSelectsPerfect);
            }

            @Override
            public void onClickMaximumError() {
                showMessage(getString(R.string.allow_maximum_3));
            }
        });
        recyclerPerfectFor.setAdapter(experienceOtherDetailsSelectionFeildsAdapterPerfect);

        presenter.getData(expId);
    }


    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setData(OtheDetailsResponse data) {
        otherDetailsFieldsSelectsPerfect.clear();
        otherDetailsFieldsSelectsPerfect.addAll(data.getPreferedFor());

        otherDetailsFieldsSelectsRec.clear();
        otherDetailsFieldsSelectsRec.addAll(data.getRecommended());

        if (experienceOtherDetailsSelectionFeildsAdapterRecom != null) {
            experienceOtherDetailsSelectionFeildsAdapterRecom.notifyDataSetChanged();
        }

        if (experienceOtherDetailsSelectionFeildsAdapterPerfect != null) {
            experienceOtherDetailsSelectionFeildsAdapterPerfect.notifyDataSetChanged();
        }

        editTextExpHighlights.setText(data.getHighlight());
        editTextExpInclude.setText(data.getIncluded());
        editTextExpNotInclude.setText(data.getNotIncluded());
        hasTag.addAll(data.getTags());
        setHasTag();

        if (isCheckSelectAllRecomand()) {

            customTextViewAll.setSelected(true);
            deSelectAllRecomand();

            if (textViewRecommendedLevel != null)
                textViewRecommendedLevel.setText(customTextViewAll.isSelected() ? getString(R.string.recomand_all) : "");

            if (recyclerRecommendedLevel != null)
                recyclerRecommendedLevel.setAlpha(!customTextViewAll.isSelected() ? 1.0f : 0.50f);

        } else {
            setDataTextView(textViewRecommendedLevel, otherDetailsFieldsSelectsRec);
        }

        setDataTextView(textViewPerfectFor, otherDetailsFieldsSelectsPerfect);
    }

    /**
     * It is used to check all select recom
     *
     * @return
     */
    private boolean isCheckSelectAllRecomand() {
        boolean isSelectAll = true;

        for (OtherDetailsFieldsSelect otherDetailsFieldsSelectTemp : otherDetailsFieldsSelectsRec) {
            if (!otherDetailsFieldsSelectTemp.isSelect()) {
                isSelectAll = false;
                break;
            }
        }
        return isSelectAll;
    }

    @Override
    public void setExpId(String expId) {
        this.expId = expId;
    }

    public void onViewClickedAddTag() {

        String str = editTextHasTags.getText().toString().trim();

        editTextHasTags.setText("");

        hasTag.add(str.trim());

        setHasTag();
    }

    private void setHasTag() {
        flowLayout.removeAllViews();

        for (String category : hasTag) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.row_has_tag, null);


            ((AppCompatTextView) view.findViewById(R.id.textViewName)).setText("#" + category);

            ((AppCompatTextView) view.findViewById(R.id.textViewName)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hasTag.remove(((AppCompatTextView) view.findViewById(R.id.textViewName)).getText().toString().substring(1));
                    setHasTag();
                }
            });

            flowLayout.addView(view);
        }

    }

    @OnClick({R.id.imageViewBack, R.id.buttonNext, R.id.textViewRecommendedLevel, R.id.textViewPerfectFor, R.id.customTextViewAll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonNext:
                try {
                    validator.submit(editTextExpHighlights).checkEmpty().errorMessage(R.string.val_other_details_list_exp_highlights).check();
                    validator.submit(editTextExpInclude).checkEmpty().errorMessage(R.string.val_other_details_include).check();
                    validator.submit(editTextExpNotInclude).checkEmpty().errorMessage(R.string.val_other_details_not_include).check();

                    validator.submit(textViewRecommendedLevel).checkEmpty().errorMessage(R.string.val_other_details_recommanded_level).check();
                    validator.submit(textViewPerfectFor).checkEmpty().errorMessage(R.string.val_other_details_perfect_for).check();

                    if (hasTag.size() == 0) {
                        throw new ApplicationException(getString(R.string.val_other_details_exp_tags));
                    }

                    presenter.callWs(expId, editTextExpHighlights.getText().toString().trim(), editTextExpInclude.getText().toString().trim(), editTextExpNotInclude.getText().toString().trim(), hasTag, otherDetailsFieldsSelectsRec, otherDetailsFieldsSelectsPerfect, customTextViewAll.isSelected(), isEdit);

                } catch (ApplicationException e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
                break;
            case R.id.textViewRecommendedLevel:
                textViewRecommendedLevel.setSelected(!textViewRecommendedLevel.isSelected());
                linearLayoutRecommendedLevel.setVisibility(textViewRecommendedLevel.isSelected() ? View.VISIBLE : View.GONE);
                break;
            case R.id.textViewPerfectFor:
                textViewPerfectFor.setSelected(!textViewPerfectFor.isSelected());
                linearLayoutPerfectFor.setVisibility(textViewPerfectFor.isSelected() ? View.VISIBLE : View.GONE);
                break;
            case R.id.customTextViewAll:
                customTextViewAll.setSelected(!customTextViewAll.isSelected());
                if (customTextViewAll.isSelected()) {
                    deSelectAllRecomand();
                } else {
                    if (experienceOtherDetailsSelectionFeildsAdapterRecom != null) {
                        experienceOtherDetailsSelectionFeildsAdapterRecom.setDisableAll(false);
                        experienceOtherDetailsSelectionFeildsAdapterRecom.notifyDataSetChanged();
                    }
                }

                textViewRecommendedLevel.setText(customTextViewAll.isSelected() ? getString(R.string.recomand_all) : "");
                recyclerRecommendedLevel.setAlpha(!customTextViewAll.isSelected() ? 1.0f : 0.50f);
                break;
        }
    }


    private void setDataTextView(AppCompatEditText editText, List<OtherDetailsFieldsSelect> data) {
        String lan = "";
        for (OtherDetailsFieldsSelect language : data) {
            if (language.isSelect()) {
                String s = language.getName();
                if (lan.isEmpty()) {
                    lan = lan + s;
                } else {
                    lan = lan + ", " + s;
                }
            }
        }
        editText.setText(lan);
    }

    private void deSelectAllRecomand() {
        List<OtherDetailsFieldsSelect> temp = new ArrayList<>();

        for (OtherDetailsFieldsSelect otherDetailsFieldsSelectTemp : otherDetailsFieldsSelectsRec) {
            otherDetailsFieldsSelectTemp.setSelect(false);
            temp.add(otherDetailsFieldsSelectTemp);
        }


        otherDetailsFieldsSelectsRec.clear();

        otherDetailsFieldsSelectsRec.addAll(temp);

        if (experienceOtherDetailsSelectionFeildsAdapterRecom != null) {
            experienceOtherDetailsSelectionFeildsAdapterRecom.setDisableAll(true);
            experienceOtherDetailsSelectionFeildsAdapterRecom.notifyDataSetChanged();
        }
    }

}
