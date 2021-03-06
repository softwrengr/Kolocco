package com.kooloco.ui.visitor.home.adapter;

import android.content.Context;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.model.LocalNew;
import com.kooloco.model.Service;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;

/**
 * Created by hlink44 on 14/9/17.
 */

public class HomeLocalAdapter extends RecyclerView.Adapter<HomeLocalAdapter.ViewHolder> {
    Context context;
    CallBack callBack;
    List<LocalNew> localNews;


    private VelocityTracker velocityTracker;
    private float velocity;

    public HomeLocalAdapter(Context context, List<LocalNew> localNews, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.localNews = localNews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_new_row_local, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Picasso.with(context).load(localNews.get(position).getLocalImagesNew().get(0).getLocalImageUrl()).resizeDimen(R.dimen.dp_260,R.dimen.dp_160).centerCrop().placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageView);

        holder.customTextViewName.setText(localNews.get(position).getFirstName() + " " + localNews.get(position).getLastName());

        holder.viewFast.setVisibility((position == 0) ? View.VISIBLE : View.GONE);

        float rate = 0.0f;

        try {
            rate = Float.parseFloat(localNews.get(position).getRate());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        holder.ratingBar.setRating(rate);

        holder.textViewRateCount.setText("(" + localNews.get(position).getRateCount() + ")");

        holder.linearLayoutExpRoot.setClickable(true);

        holder.checkboxFav.setChecked(false);

        holder.customTextViewLocation.setText(localNews.get(position).getLocation());

        holder.textViewCurrencyType.setText(BaseActivity.currency);

        holder.textViewExpPrice.setText(localNews.get(position).getPrice());


        Picasso.with(context).load(localNews.get(position).getProfileImage()).placeholder(R.drawable.user_round).resize(250,250).centerCrop().transform(new CircleTransform()).error(R.drawable.user_round).into(holder.imageViewProfile);

        List<Service> servicesTemp = new ArrayList<>();

        if (localNews.get(position).getServices().size() <= 3) {
            servicesTemp.addAll(localNews.get(position).getServices());
        } else {
            for (int i = 0; i < 3; i++) {
                servicesTemp.add(localNews.get(position).getServices().get(i));
            }
        }

        holder.recyclerViewService.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewService.setAdapter(new HomeServiceAdapter(context, servicesTemp, true, pos -> {

        }));
        holder.linearLayoutExpRoot.setOnClickListener(view -> callBack.onClickItem(localNews.get(position), position));


        holder.checkboxFav.setOnClickListener(view -> {
            holder.checkboxFav.setScaleX(0.5f);
            holder.checkboxFav.setScaleY(0.5f);
            animIcon(holder.checkboxFav, DynamicAnimation.SCALE_X, 1f);
            animIcon(holder.checkboxFav, DynamicAnimation.SCALE_Y, 1f);

        });
        holder.imageViewVerify.setVisibility(localNews.get(position).getIsVerifed().equalsIgnoreCase("1") ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return localNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        PorterShapeImageView imageView;
        @BindView(R.id.checkboxFav)
        CheckBox checkboxFav;
        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.flowLayoutImgProfile)
        FrameLayout flowLayoutImgProfile;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.ratingBar)
        AppCompatRatingBar ratingBar;
        @BindView(R.id.customTextViewLocation)
        AppCompatTextView customTextViewLocation;
        @BindView(R.id.textViewExpPrice)
        AppCompatTextView textViewExpPrice;
        @BindView(R.id.recyclerViewService)
        RecyclerView recyclerViewService;
        @BindView(R.id.linearLayoutMain)
        LinearLayout linearLayoutMain;
        @BindView(R.id.linearLayoutExpRoot)
        LinearLayout linearLayoutExpRoot;
        @BindView(R.id.viewFast)
        View viewFast;
        @BindView(R.id.textViewRateCount)
        AppCompatTextView textViewRateCount;
        @BindView(R.id.imageViewVerify)
        ImageView imageViewVerify;
        @BindView(R.id.textViewCurrencyType)
        AppCompatTextView textViewCurrencyType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(LocalNew localNews, int pos);
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

