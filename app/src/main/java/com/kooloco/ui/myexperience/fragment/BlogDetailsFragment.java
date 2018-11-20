package com.kooloco.ui.myexperience.fragment;
/**
 * Created by hlink44 on 1/11/17.
 */

import android.app.Dialog;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kooloco.R;
import com.kooloco.data.URLFactory;
import com.kooloco.data.temp.Temp;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.BlogDetails;
import com.kooloco.model.BlogMedia;
import com.kooloco.model.Review;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.myexperience.adapter.BlogPagerSlider;
import com.kooloco.ui.myexperience.adapter.BlogSlider;
import com.kooloco.ui.myexperience.presenter.BlogDetailsPresenter;
import com.kooloco.ui.myexperience.view.BlogDetailsView;
import com.kooloco.ui.visitor.dashboard.adapter.DashboardReviewAdapter;
import com.kooloco.ui.visitor.dashboard.adapter.DialogImageSlider;
import com.kooloco.util.StaticMap;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class BlogDetailsFragment extends BaseFragment<BlogDetailsPresenter, BlogDetailsView> implements BlogDetailsView {

    @BindView(R.id.imageViewProfile)
    ImageView imageViewProfile;
    @BindView(R.id.customTextViewName)
    AppCompatTextView customTextViewName;
    @BindView(R.id.customTextViewDesc)
    AppCompatTextView customTextViewDesc;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.recyclerViewImageSlide)
    RecyclerView recyclerViewImageSlide;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;
    @BindView(R.id.customTextViewDescText)
    AppCompatTextView customTextViewDescText;
    @BindView(R.id.ratingBar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.customTextViewDate)
    AppCompatTextView customTextViewDate;
    @BindView(R.id.customTextViewTime)
    AppCompatTextView customTextViewTime;
    @BindView(R.id.customTextViewLocation)
    AppCompatTextView customTextViewLocation;
    @BindView(R.id.frameLayoutMap)
    FrameLayout frameLayoutMap;
    @BindView(R.id.customTextViewNameProfile)
    AppCompatTextView customTextViewNameProfile;
    @BindView(R.id.imageViewProfileLocal)
    ImageView imageViewProfileLocal;
    @BindView(R.id.customTextViewRatingValue)
    AppCompatTextView customTextViewRatingValue;
    @BindView(R.id.ratingView)
    FrameLayout ratingView;
    @BindView(R.id.buttonBook)
    AppCompatButton buttonBook;
    @BindView(R.id.customTextViewRatingCount)
    AppCompatTextView customTextViewRatingCount;
    @BindView(R.id.customTextViewAccept)
    AppCompatTextView customTextViewAccept;
    @BindView(R.id.recyclerReviews)
    RecyclerView recyclerReviews;
    @BindView(R.id.editTextMessage)
    AppCompatEditText editTextMessage;
    @BindView(R.id.textViewSend)
    FrameLayout textViewSend;
    LinearLayoutManager layoutManager;
    Dialog dialog;
    @BindView(R.id.textViewTags)
    AppCompatTextView textViewTags;
    @BindView(R.id.textViewIntroduction)
    AppCompatTextView textViewIntroduction;
    private BlogDetails blogDetails;
    @BindView(R.id.imageViewStaticMap)
    ImageView imageViewStaticMap;
    List<Review> data;
    DashboardReviewAdapter dashboardReviewAdapter;

    @Override
    protected int createLayout() {
        return R.layout.fragment_blog_details;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected BlogDetailsView createView() {
        return this;
    }

    @Override
    protected void bindData() {

        data = new ArrayList<>();
        recyclerReviews.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        dashboardReviewAdapter = new DashboardReviewAdapter(getActivity(), data);
        recyclerReviews.setAdapter(dashboardReviewAdapter);

        customTextViewName.setText(blogDetails.getFirstname() + " " + blogDetails.getLastname());
        Picasso.with(getActivity()).load(blogDetails.getProfileImage()).transform(new CircleTransform()).into(imageViewProfile);


        imageViewProfile.setOnClickListener(view -> imageOpenZoom(blogDetails.getProfileImage()));

        String text = "" + blogDetails.getTagline().replace("" + blogDetails.getLocalInfo().getFirstname() + " " + blogDetails.getLocalInfo().getLastname(), "<font color='" + getActivity().getResources().getColor(R.color.buttonColor) + "'> " + blogDetails.getLocalInfo().getFirstname() + " " + blogDetails.getLocalInfo().getLastname() + " </font>");
        customTextViewDesc.setText(Html.fromHtml(text));

        text = "" + buttonBook.getText().toString().replace("###", "<font color='" + getActivity().getResources().getColor(R.color.white) + "'><b> " + blogDetails.getLocalInfo().getFirstname() + " " + blogDetails.getLocalInfo().getLastname() + " </b></font>");
        buttonBook.setText(Html.fromHtml(text));

        viewPager.setAdapter(new BlogPagerSlider(getChildFragmentManager(), blogDetails.getBlogMedia()));
        pageIndicatorView.setViewPager(viewPager);
        pageIndicatorView.setAnimationType(AnimationType.WORM);
        pageIndicatorView.setStrokeWidth(2);

        BlogSlider dashboardSlider = new BlogSlider(getActivity(), blogDetails.getBlogMedia(), new BlogSlider.CallBack() {
            @Override
            public void onClick(int position, BlogMedia blogMedia) {
                if (blogMedia.getMediaType().equalsIgnoreCase("V")) {
                    playVideo(blogMedia.getFile());
                }
            }

        });

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewImageSlide.setLayoutManager(layoutManager);
        recyclerViewImageSlide.setAdapter(dashboardSlider);
        recyclerViewImageSlide.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewImageSlide);

        recyclerViewImageSlide.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                viewPager.setCurrentItem(firstVisibleItemPosition);
            }
        });

        customTextViewDescText.setText(blogDetails.getDescription());

        customTextViewTime.setText(blogDetails.getDuration() + " hr");

        customTextViewDate.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(blogDetails.getBookingDate(), "yyyy-MM-dd", "dd MMMM, yyyy"));

        ratingBar.setRating(Float.parseFloat(blogDetails.getRate()));

        textViewTags.setText(blogDetails.getTags());


        String staticMapUrl = StaticMap.getUrl(getActivity(), blogDetails.getMeetingLatitude(), blogDetails.getMeetingLongitude(), URLFactory.MEETING);

        Glide.with(getActivity()).load(staticMapUrl).asBitmap().into(imageViewStaticMap);
        customTextViewLocation.setText(blogDetails.getMeetingAddress());

        customTextViewNameProfile.setText(blogDetails.getLocalInfo().getFirstname() + " " + blogDetails.getLocalInfo().getLastname());
        textViewIntroduction.setText(blogDetails.getLocalInfo().getIntroYourSelf());
        Picasso.with(getActivity()).load(blogDetails.getLocalInfo().getProfileImage()).transform(new CircleTransform()).into(imageViewProfileLocal);


        customTextViewAccept.setText(blogDetails.getLikes());

        presenter.getComments(blogDetails.getId());
    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    public void showDialog() {

        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_slide_image, null, false);

        final AppCompatTextView textViewCurrent = view.findViewById(R.id.textViewCurrent);

        AppCompatTextView textViewTotal = view.findViewById(R.id.textViewTotal);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerImageSlider);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new DialogImageSlider(getActivity(), Temp.getLocalImageSlider()));
        recyclerView.setOnFlingListener(null);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (textViewCurrent != null) {
                    firstVisibleItemPosition = firstVisibleItemPosition + 1;
                    textViewCurrent.setText("" + firstVisibleItemPosition);
                }
            }
        });


        textViewTotal.setText("/" + com.kooloco.ui.visitor.home.temp.Temp.getDrwableList().size());

        dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.drwable_background_tra);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        dialog.setContentView(view);

        dialog.show();


    }

    @OnClick(R.id.textViewSend)
    public void onViewClickedSend() {
        if (editTextMessage.getText().toString().isEmpty()) {
            showMessage(getString(R.string.val_write_your_comment));
            return;
        }
        presenter.addComent(blogDetails.getId(), editTextMessage.getText().toString().trim());
    }

    @Override
    public void setData(BlogDetails blogDetails) {
        this.blogDetails = blogDetails;
    }

    @Override
    public void setDataReview(List<Review> data) {
        this.data.addAll(data);
        if (dashboardReviewAdapter != null) {
            dashboardReviewAdapter.notifyDataSetChanged();
        }
        setSize();
    }

    private void setSize() {
        customTextViewRatingCount.setText("(" + data.size() + ")");
    }

    @Override
    public void setDataComments(Review dataD) {
        editTextMessage.setText("");
        data.add(dataD);
        if (dashboardReviewAdapter != null) {
            dashboardReviewAdapter.notifyItemInserted(data.size() - 1);
        }
        setSize();
    }

    @OnClick(R.id.frameLayoutMap)
    public void onViewClickedMap() {
        presenter.openMapScreen(blogDetails);
    }
}
