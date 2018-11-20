package com.kooloco.ui.visitor.home.adapter;

import android.content.Context;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.DashboardDetails;
import com.kooloco.model.Service;
import com.kooloco.util.picaso.CircleTransform;
import com.like.LikeButton;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;

/**
 * Created by hlink44 on 14/9/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    private CallBack callBack;
    List<DashboardDetails> homes;
    FragmentManager childFragmentManager;
    private VelocityTracker velocityTracker;
    private float velocity;

    public HomeAdapter(Context context, List<DashboardDetails> homes, FragmentManager childFragmentManager, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.homes = homes;
        this.childFragmentManager = childFragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_home, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.pageIndicatorView.setCount(homes.get(position).getLocalImages().size());
        holder.pageIndicatorView.setAnimationType(AnimationType.WORM);
        holder.pageIndicatorView.setStrokeWidth(2);

        //Set Data
        holder.customTextViewName.setText(homes.get(position).getLocalName());
        holder.customTextViewRatingValue.setText(homes.get(position).getLocalRating());

        Picasso.with(context).load(homes.get(position).getImageUrl()).transform(new CircleTransform()).into(holder.imageViewProfile);

        holder.imageViewProfile.setOnClickListener(view -> {
            callBack.onClickImage(position, homes.get(position));
        });


        String Address = "";

        if (homes.get(position).getLocalCity().isEmpty()) {

            holder.customTextViewDistance.setText(homes.get(position).getLocalCity() + ", " + homes.get(position).getLocalCountry());

        } else {

        }

        holder.customTextViewDistance.setText(homes.get(position).getLocalCity() + ", " + homes.get(position).getLocalCountry());


        holder.checkboxFav.setChecked(homes.get(position).isFav());

        final HomeSlider homeSlider = new HomeSlider(context, homes.get(position).getLocalImages(), new HomeSlider.CallBack() {
            @Override
            public void onClick() {
                callBack.onClickItem(position, homes.get(position));
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerViewImageSlide.setLayoutManager(layoutManager);
        holder.recyclerViewImageSlide.setAdapter(homeSlider);
        holder.recyclerViewImageSlide.setOnFlingListener(null);

        SnapHelper snapHelper = new LinearSnapHelper();

        snapHelper.attachToRecyclerView(holder.recyclerViewImageSlide);

        //holder.starButton.setLiked(true);

        holder.recyclerViewImageSlide.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                holder.pageIndicatorView.setSelection(firstVisibleItemPosition);
            }
        });


        holder.imageViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemPosition != 0) {
                    holder.recyclerViewImageSlide.smoothScrollToPosition(firstVisibleItemPosition - 1);
                }
            }
        });

        holder.imageViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemPosition != homes.get(position).getLocalImages().size() - 1) {
                    holder.recyclerViewImageSlide.smoothScrollToPosition(firstVisibleItemPosition + 1);
                }
            }
        });

        //Set service adapter

        List<Service> servicesTemp = new ArrayList<>();

        if (homes.get(position).getServices().size() <= 3) {
            servicesTemp.addAll(homes.get(position).getServices());
        } else {
            for (int i = 0; i < 3; i++) {
                servicesTemp.add(homes.get(position).getServices().get(i));
            }
        }

        holder.ratingBar.setRating(Float.parseFloat(homes.get(position).getLocalRating()));

        holder.customTextViewLowestPrice.setText(context.getString(R.string.currency) + " " + homes.get(position).getLowPrice() + context.getString(R.string.hr_new));

        holder.recyclerViewService.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewService.setAdapter(new HomeServiceAdapter(context, servicesTemp, true, pos -> callBack.onClickItem(position, homes.get(position))));

        //Set service type
        holder.recyclerServiceType.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerServiceType.setAdapter(new HomeServiceTypeAdapter(context, homes.get(position).getServicesTypes()));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickItem(position, homes.get(position));
            }
        };
        holder.linearLayoutMain.setOnClickListener(onClickListener);
        holder.customTextViewName.setOnClickListener(onClickListener);

        holder.checkboxFav.setOnClickListener(view -> {
            holder.checkboxFav.setScaleX(0.5f);
            holder.checkboxFav.setScaleY(0.5f);
            animIcon(holder.checkboxFav, DynamicAnimation.SCALE_X, 1f);
            animIcon(holder.checkboxFav, DynamicAnimation.SCALE_Y, 1f);

        });
    }

    @Override
    public int getItemCount() {
        return homes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recyclerViewImageSlide)
        RecyclerView recyclerViewImageSlide;
        @BindView(R.id.imageViewLeft)
        ImageView imageViewLeft;
        @BindView(R.id.imageViewRight)
        ImageView imageViewRight;
        @BindView(R.id.checkboxFav)
        CheckBox checkboxFav;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.recyclerServiceType)
        RecyclerView recyclerServiceType;
        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.customTextViewRatingValue)
        AppCompatTextView customTextViewRatingValue;
        @BindView(R.id.ratingView)
        FrameLayout ratingView;
        @BindView(R.id.customTextViewDistance)
        AppCompatTextView customTextViewDistance;
        @BindView(R.id.recyclerViewService)
        RecyclerView recyclerViewService;
        @BindView(R.id.linearLayoutMain)
        LinearLayout linearLayoutMain;
        @BindView(R.id.viewPager)
        ViewPager viewPager;
        @BindView(R.id.pageIndicatorView)
        PageIndicatorView pageIndicatorView;
        @BindView(R.id.star_button)
        LikeButton starButton;

        @BindView(R.id.ratingBar)
        AppCompatRatingBar ratingBar;
        @BindView(R.id.customTextViewLowestPrice)
        AppCompatTextView customTextViewLowestPrice;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(int position, DashboardDetails dashboardDetails);

        void onClickImage(int position, DashboardDetails dashboardDetails);

    }


    private void animIcon(View view, DynamicAnimation.ViewProperty viewProperty, float fromPosition) {
        velocityTracker = VelocityTracker.obtain();
        SpringAnimation anim = new SpringAnimation(view, viewProperty, fromPosition);
        anim.getSpring().setStiffness(STIFFNESS_LOW);
        anim.getSpring().setDampingRatio(DAMPING_RATIO_HIGH_BOUNCY);
        velocityTracker.computeCurrentVelocity(2000);
        velocity = velocityTracker.getYVelocity();
        anim.setStartVelocity(velocity);
        anim.start();
    }
}
