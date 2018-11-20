package com.kooloco.ui.visitor.home.adapter;

import android.content.Context;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.data.datasource.DatabaseCacheDataSource;
import com.kooloco.model.ExpFavDataBase;
import com.kooloco.model.ExpereinceNew;
import com.kooloco.ui.base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;

/**
 * Created by hlink44 on 14/9/17.
 */

public class HomeExperienceAdapter extends RecyclerView.Adapter<HomeExperienceAdapter.ViewHolder> {
    Context context;
    CallBack callBack;
    List<ExpereinceNew> expereinceNews;
    private VelocityTracker velocityTracker;
    private float velocity;
    DatabaseCacheDataSource databaseCacheDataSource;

    public HomeExperienceAdapter(Context context, List<ExpereinceNew> expereinceNews, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.expereinceNews = expereinceNews;
        databaseCacheDataSource = new DatabaseCacheDataSource();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_new_row_exp, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context).load(expereinceNews.get(position).getImage_url()).resizeDimen(R.dimen.dp_260,R.dimen.dp_160).centerCrop().placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageView);

        holder.linearLayoutExpRoot.setClickable(true);

        holder.customTextViewTitle.setText(expereinceNews.get(position).getTitle());

        holder.viewFast.setVisibility((position == 0) ? View.VISIBLE : View.GONE);

        float rate = 0.0f;

        try {
            rate = Float.parseFloat(expereinceNews.get(position).getRate());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        ExpFavDataBase dataFavExp = databaseCacheDataSource.getDataFavExp(expereinceNews.get(position).getId(), expereinceNews.get(position).getIsFavorite());

        if (dataFavExp != null) {
            holder.checkboxFav.setChecked(dataFavExp.getIsFav().equalsIgnoreCase("1"));
        }
/*
        Realm.getDefaultInstance().where(ExpFavDataBase.class).findAll().addChangeListener((realmModel, changeSet) -> {
            if (realmModel != null) {
                for (ExpFavDataBase expFavDataBase : realmModel) {
                    if (expFavDataBase.getId().equalsIgnoreCase(expereinceNews.get(position).getId())) {
                        holder.checkboxFav.setChecked(expFavDataBase.getIsFav().equalsIgnoreCase("1"));
                    }
                }
            }
        });*/

        holder.ratingBar.setRating(rate);

        holder.textViewRateCount.setText("(" + expereinceNews.get(position).getRateCount() + ")");

        holder.customTextViewLocation.setText(expereinceNews.get(position).getLocation());

        holder.textExpCurrency.setText(BaseActivity.currency);

        holder.textViewExpPrice.setText(expereinceNews.get(position).getPrice());

        if (!expereinceNews.get(position).getExperience_url().isEmpty()) {
            holder.imageViewExp.setVisibility(View.VISIBLE);
            Picasso.with(context).load(expereinceNews.get(position).getExperience_url()).placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageViewExp);

        } else {
            holder.imageViewExp.setVisibility(View.INVISIBLE);
        }

        holder.linearLayoutExpRoot.setOnClickListener(view -> {
            callBack.onClickItem(expereinceNews.get(position), position);
        });

        // holder.checkboxFav.setChecked(false);


        holder.checkboxFav.setOnClickListener(view -> {
            holder.checkboxFav.setScaleX(0.5f);
            holder.checkboxFav.setScaleY(0.5f);
            animIcon(holder.checkboxFav, DynamicAnimation.SCALE_X, 1f);
            animIcon(holder.checkboxFav, DynamicAnimation.SCALE_Y, 1f);

            callBack.onClickFav(expereinceNews.get(position), position);

        });

    }

    @Override
    public int getItemCount() {
        return expereinceNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        PorterShapeImageView imageView;
        @BindView(R.id.customTextViewTitle)
        AppCompatTextView customTextViewTitle;
        @BindView(R.id.ratingBar)
        AppCompatRatingBar ratingBar;
        @BindView(R.id.textViewRateCount)
        AppCompatTextView textViewRateCount;
        @BindView(R.id.customTextViewLocation)
        AppCompatTextView customTextViewLocation;
        @BindView(R.id.imageViewExp)
        ImageView imageViewExp;
        @BindView(R.id.textViewExpPrice)
        AppCompatTextView textViewExpPrice;
        @BindView(R.id.linearLayoutExpRoot)
        LinearLayout linearLayoutExpRoot;
        @BindView(R.id.viewFast)
        View viewFast;
        @BindView(R.id.checkboxFav)
        CheckBox checkboxFav;
        @BindView(R.id.textExpCurrency)
        AppCompatTextView textExpCurrency;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(ExpereinceNew expereinceNew, int pos);

        void onClickFav(ExpereinceNew expereinceNew, int pos);
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

