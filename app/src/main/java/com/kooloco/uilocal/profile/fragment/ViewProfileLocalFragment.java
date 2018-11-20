package com.kooloco.uilocal.profile.fragment;
/**
 * Created by hlink44 on 9/10/17.
 */

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.kooloco.R;
import com.kooloco.constant.EventBusAction;
import com.kooloco.data.URLFactory;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.CertificateInfo;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.model.ProfileStatus;
import com.kooloco.model.SubService;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.uilocal.expereince.adapter.ExperiencesAdapter;
import com.kooloco.uilocal.profile.adapter.SportImageAdapter;
import com.kooloco.uilocal.profile.adapter.ViewProfileCertificatesAndAchivments;
import com.kooloco.uilocal.profile.presenter.ViewProfileLocalPresenter;
import com.kooloco.uilocal.profile.view.ViewProfileLocalView;
import com.kooloco.util.StaticMap;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ViewProfileLocalFragment extends BaseFragment<ViewProfileLocalPresenter, ViewProfileLocalView> implements ViewProfileLocalView {


    DashboardDetails dashboardDetails;
    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewRatingValue)
    AppCompatTextView customTextViewRatingValue;
    @BindView(R.id.ratingView)
    FrameLayout ratingView;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.customTextViewLocalName)
    AppCompatTextView customTextViewLocalName;
    @BindView(R.id.customTextViewEditProfile)
    AppCompatTextView customTextViewEditProfile;
    @BindView(R.id.customTextViewCurrency)
    AppCompatTextView customTextViewCurrency;
    @BindView(R.id.customTextViewLanguages)
    AppCompatTextView customTextViewLanguages;
    @BindView(R.id.customTextViewAppointmentValue)
    AppCompatTextView customTextViewAppointmentValue;
    @BindView(R.id.customTextViewOrderHistory)
    AppCompatTextView customTextViewOrderHistory;
    @BindView(R.id.recyclerViewExp)
    RecyclerView recyclerViewExp;
    @BindView(R.id.customTextViewNTMY)
    AppCompatTextView customTextViewNTMY;
    @BindView(R.id.viewNTYM)
    View viewNTYM;
    @BindView(R.id.imageViewStaticMap)
    ImageView imageViewStaticMap;
    @BindView(R.id.frameLayoutMap)
    FrameLayout frameLayoutMap;
    @BindView(R.id.textViewLocationEdit)
    AppCompatTextView textViewLocationEdit;
    @BindView(R.id.textViewNoDataLocation)
    AppCompatTextView textViewNoDataLocation;
    @BindView(R.id.customTextViewLocation)
    AppCompatTextView customTextViewLocation;
    @BindView(R.id.textViewCertificates)
    AppCompatTextView textViewCertificates;
    @BindView(R.id.textViewNoDataCertificates)
    AppCompatTextView textViewNoDataCertificates;
    @BindView(R.id.viewCerticicates)
    View viewCerticicates;
    @BindView(R.id.recyclerCertificates)
    RecyclerView recyclerCertificates;
    @BindView(R.id.textViewAchievements)
    AppCompatTextView textViewAchievements;
    @BindView(R.id.textViewNoDataAchivements)
    AppCompatTextView textViewNoDataAchivements;
    @BindView(R.id.viewAchivements)
    View viewAchivements;
    @BindView(R.id.recyclerAchievements)
    RecyclerView recyclerAchievements;
    @BindView(R.id.customTextViewOrgName)
    AppCompatTextView customTextViewOrgName;
    @BindView(R.id.customTextViewOrgDescWithLocation)
    AppCompatTextView customTextViewOrgDescWithLocation;
    @BindView(R.id.imageViewOrgImage)
    ImageView imageViewOrgImage;
    @BindView(R.id.linearLayoutOrganization)
    LinearLayout linearLayoutOrganization;
    @BindView(R.id.linearLayoutOrg)
    LinearLayout linearLayoutOrg;
    @BindView(R.id.textViewExperienseImageEdit)
    AppCompatTextView textViewExperienseImageEdit;
    @BindView(R.id.textViewNoDataExperienceImages)
    AppCompatTextView textViewNoDataExperienceImages;
    @BindView(R.id.viewExpereince)
    View viewExpereince;
    @BindView(R.id.recyclerViewImage)
    RecyclerView recyclerViewImage;
    List<ExpereinceNew> expereinceNews;
    @BindView(R.id.textViewNoDataExperiences)
    AppCompatTextView textViewNoDataExperiences;
    private ExperiencesAdapter experiencesAdapter;

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_view_profile;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ViewProfileLocalView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        presenter.getData();
        customTextViewEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.openEditProfile();
            }
        });
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
    }

    @Override
    public void setData(DashboardDetails data) {
        //Set profile data
        dashboardDetails = data;
        if (!data.getImageUrl().isEmpty()) {
            Picasso.with(getActivity()).load(data.getImageUrl()).transform(new CircleTransform()).placeholder(R.drawable.user_round).into(imageViewProfile);
        }

        customTextViewLocalName.setText(data.getLocalName());
        customTextViewLocalName.setText(data.getLocalName());


        String staticMapUrl = StaticMap.getUrl(getActivity(), data.getLatitude(), data.getLongitude(), URLFactory.LOCAL);

        Glide.with(getActivity()).load(staticMapUrl).asBitmap().into(imageViewStaticMap);


        customTextViewRatingValue.setText(data.getLocalRating());
        customTextViewNTMY.setText(data.getLocalNTMY());


        customTextViewLanguages.setText(data.getLocalLanguages());
        customTextViewCurrency.setText(data.getCurrency());
        customTextViewAppointmentValue.setText(data.getAppointmentCount());


        //Set certificates data
        recyclerCertificates.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerCertificates.setAdapter(new ViewProfileCertificatesAndAchivments(getActivity(), data.getCertificateInfos(), new ViewProfileCertificatesAndAchivments.CallBack() {
            @Override
            public void onClickItem(int position, CertificateInfo certificateInfo) {
                openInfoCertficates(certificateInfo);
            }

            @Override
            public void onClickEdit(int position, CertificateInfo certificateInfo) {
                presenter.openCertification();
            }
        }));

        customTextViewLocation.setText(data.getLocalLocation());

        //Set achievements data
        recyclerAchievements.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerAchievements.setAdapter(new ViewProfileCertificatesAndAchivments(getActivity(), data.getAchivmentsInfo(), new ViewProfileCertificatesAndAchivments.CallBack() {
            @Override
            public void onClickItem(int position, CertificateInfo certificateInfo) {
                openInfoCertficates(certificateInfo);
            }

            @Override
            public void onClickEdit(int position, CertificateInfo certificateInfo) {
                presenter.openEditAchievements();
            }
        }));

        //Set organization data

        if (data.getIsAffilated().equalsIgnoreCase("1")) {
            linearLayoutOrg.setVisibility(View.VISIBLE);
            customTextViewOrgName.setText(data.getOrganizationVisitor().getOrganisationName());
            customTextViewOrgDescWithLocation.setText(data.getOrganizationVisitor().getOrganisationDescription() + "\n" + data.getOrganizationVisitor().getOrganisationAddress());
            Picasso.with(getActivity()).load(data.getOrganizationVisitor().getOrganisationLogo()).placeholder(R.drawable.user_round).transform(new CircleTransform()).into(imageViewOrgImage);
        } else {
            linearLayoutOrg.setVisibility(View.GONE);
        }


        //Set Experiance image

        recyclerViewImage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewImage.setAdapter(new SportImageAdapter(getActivity(), data.getLocalImages()));

        EventBus.getDefault().post(EventBusAction.UPDATELISTLOCAL);


        setVisibilityIcon(dashboardDetails);
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClickedBack() {
        goBack();
    }

    @OnClick({R.id.customTextViewOrgName, R.id.textViewExperienseImageEdit,R.id.textViewLanguages ,R.id.linearLayoutOrganization, R.id.textViewCertificates, R.id.textViewAchievements, R.id.textViewLocationEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.customTextViewOrgName:
            case R.id.linearLayoutOrganization:
                presenter.openOrganizationDetails(dashboardDetails.getOrganizationVisitor());
                break;
            case R.id.textViewExperienseImageEdit:
                presenter.openAddImages();
                break;
            case R.id.textViewCertificates:
                presenter.openCertification();
                break;
            case R.id.textViewLanguages:
                presenter.openSpeakLanguage();
                break;
            case R.id.textViewAchievements:
                presenter.openEditAchievements();
                break;
            case R.id.textViewLocationEdit:
                presenter.openSetLocation(dashboardDetails);
                break;

        }
    }

    private String getSubService(List<SubService> subServices) {
        String s = "";
        for (SubService subService : subServices) {
            if (s.isEmpty()) {
                s = s + subService.getName();
            } else {
                s = s + ", " + subService.getName();

            }
        }
        return s;
    }

    private void setVisibilityIcon(DashboardDetails dashboardDetails) {

        int editDrawable = R.drawable.accept_ord_detail_edit;
        int addDrawable = R.drawable.plus_profile;

        if (dashboardDetails.getLocalNTMY() == null) {
            dashboardDetails.setLocalNTMY("");
        }


        //Nice to meet You
        if (customTextViewNTMY != null) {
            customTextViewNTMY.setVisibility((dashboardDetails.getLocalNTMY().isEmpty()) ? View.GONE : View.VISIBLE);
        }

        if (viewNTYM != null) {
            viewNTYM.setVisibility((dashboardDetails.getLocalNTMY().isEmpty()) ? View.GONE : View.VISIBLE);
        }


        expereinceNews = new ArrayList<>();
        expereinceNews.addAll(dashboardDetails.getExpereinceNews());

        experiencesAdapter = new ExperiencesAdapter(getActivity(), expereinceNews, new ExperiencesAdapter.CallBack() {
            @Override
            public void onClickItem(ExpereinceNew expereinceNew, int pos) {

                int stepPendingCount = 0;
                for (ProfileStatus profileStatus : expereinceNew.getProgress()) {
                    if (profileStatus.getIsComplete().equalsIgnoreCase("0")) {
                        stepPendingCount = stepPendingCount + 1;
                    }
                }

                if (stepPendingCount == 0) {
                    presenter.openExpDetails(expereinceNew);
                } else {
                    showDialogWithOkButton(getString(R.string.error_message_exp_first), getString(R.string.var_ok), new CallBackDeleteAlert() {
                        @Override
                        public void onSuccess(boolean isSuccess) {
                            presenter.openEditExperience(expereinceNew);
                        }
                    });
                }

            }

            @Override
            public void onClickDelete(ExpereinceNew expereinceNew, int pos) {
                showDialogDeleteWithAnimation(getString(R.string.delete_exp), isSuccess -> {
                    if (isSuccess) {
                        presenter.callWsDeleteExp(pos, expereinceNew);
                    }
                });
            }

            @Override
            public void onClickEdit(ExpereinceNew expereinceNew, int pos) {
                presenter.openEditExperience(expereinceNew);
            }
        });

        noExpFound();

        recyclerViewExp.setNestedScrollingEnabled(false);
        recyclerViewExp.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewExp.setAdapter(experiencesAdapter);

        if (dashboardDetails.getLocalLocation() == null) {
            dashboardDetails.setLocalLocation("");
        }

        //Set Location data
        if (textViewLocationEdit != null) {
            textViewLocationEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, (dashboardDetails.getLocalLocation().isEmpty()) ? addDrawable : editDrawable, 0);
        }
        if (textViewNoDataLocation != null) {
            textViewNoDataLocation.setVisibility((dashboardDetails.getLocalLocation().isEmpty()) ? View.VISIBLE : View.GONE);
        }
        if (frameLayoutMap != null) {
            frameLayoutMap.setVisibility((dashboardDetails.getLocalLocation().isEmpty()) ? View.GONE : View.VISIBLE);
        }

        if (customTextViewLocation != null) {
            customTextViewLocation.setVisibility((dashboardDetails.getLocalLocation().isEmpty()) ? View.GONE : View.VISIBLE);
        }


        //Set Certificates
        if (textViewCertificates != null) {
            textViewCertificates.setCompoundDrawablesWithIntrinsicBounds(0, 0, (dashboardDetails.getCertificateInfos().size() == 0) ? addDrawable : editDrawable, 0);
        }
        if (textViewNoDataCertificates != null) {
            textViewNoDataCertificates.setVisibility((dashboardDetails.getCertificateInfos().size() == 0) ? View.VISIBLE : View.GONE);
        }
        if (viewCerticicates != null) {
            viewCerticicates.setVisibility((dashboardDetails.getCertificateInfos().size() == 0) ? View.GONE : View.VISIBLE);
        }

        //Set Achievements

        if (textViewAchievements != null) {
            textViewAchievements.setCompoundDrawablesWithIntrinsicBounds(0, 0, (dashboardDetails.getAchivmentsInfo().size() == 0) ? addDrawable : editDrawable, 0);
        }
        if (textViewNoDataAchivements != null) {
            textViewNoDataAchivements.setVisibility((dashboardDetails.getAchivmentsInfo().size() == 0) ? View.VISIBLE : View.GONE);
        }

        if (viewAchivements != null) {
            viewAchivements.setVisibility((dashboardDetails.getAchivmentsInfo().size() == 0) ? View.GONE : View.VISIBLE);
        }


        if (dashboardDetails.getCancelPolicy() == null) {
            dashboardDetails.setCancelPolicy("");
        }


        //Set Experience
        if (textViewExperienseImageEdit != null) {
            textViewExperienseImageEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, (dashboardDetails.getLocalImages().size() == 0) ? addDrawable : editDrawable, 0);
        }
        if (textViewNoDataExperienceImages != null) {
            textViewNoDataExperienceImages.setVisibility((dashboardDetails.getLocalImages().size() == 0) ? View.VISIBLE : View.GONE);
        }

        if (viewExpereince != null) {
            viewExpereince.setVisibility((dashboardDetails.getLocalImages().size() == 0) ? View.GONE : View.VISIBLE);
        }

    }

    @Override
    public void deleteExp(int pos, ExpereinceNew expereinceNew) {

        expereinceNews.remove(expereinceNew);
        noExpFound();
        if (experiencesAdapter != null)
            experiencesAdapter.notifyDataSetChanged();
    }


    private void noExpFound() {
        if (textViewNoDataExperiences != null) {
            textViewNoDataExperiences.setVisibility((expereinceNews.size() == 0) ? View.VISIBLE : View.GONE);
        }
    }

}
