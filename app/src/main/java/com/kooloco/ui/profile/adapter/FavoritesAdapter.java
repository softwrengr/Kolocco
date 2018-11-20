package com.kooloco.ui.profile.adapter;

import android.content.Context;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.Favorites;
import com.kooloco.model.Order;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;

/**
 * Created by hlink44 on 14/9/17.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    Context context;
    List<Favorites> favorites;
    private CallBack callBack;
    private VelocityTracker velocityTracker;
    private float velocity;

    public FavoritesAdapter(Context context, List<Favorites> favorites, CallBack callBack) {
        this.context = context;
        this.favorites = favorites;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_fav, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context).load(favorites.get(position).getImageUrl()).into(holder.imageViewProfile);
        holder.customTextViewName.setText(favorites.get(position).getName());

        holder.customTextViewServiceType.setText(favorites.get(position).getServiceType());


        holder.customTextViewServiceName.setText(favorites.get(position).getService());
        holder.customTextViewPrice.setText(favorites.get(position).getPrice());

        holder.customTextViewDate.setText(favorites.get(position).getDay());
        holder.customTextViewMonth.setText(favorites.get(position).getMonth());
        holder.customTextViewAddress.setText(favorites.get(position).getLocation());

        holder.checkboxFav.setChecked(favorites.get(position).isFav());

        holder.checkboxFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.checkboxFav.setScaleX(0.5f);
                holder.checkboxFav.setScaleY(0.5f);
                animIcon(holder.checkboxFav, DynamicAnimation.SCALE_X, 1f);
                animIcon(holder.checkboxFav, DynamicAnimation.SCALE_Y, 1f);

                new android.os.Handler().postDelayed(() -> callBack.onClickUnFav(favorites.get(position)), 700);

            }
        });

        holder.customTextViewDistance.setText(favorites.get(position).getDistance());
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.customTextViewDate)
        AppCompatTextView customTextViewDate;
        @BindView(R.id.customTextViewMonth)
        AppCompatTextView customTextViewMonth;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.customTextViewServiceType)
        AppCompatTextView customTextViewServiceType;
        @BindView(R.id.customTextViewServiceName)
        AppCompatTextView customTextViewServiceName;
        @BindView(R.id.customTextViewAddress)
        AppCompatTextView customTextViewAddress;
        @BindView(R.id.checkboxFav)
        CheckBox checkboxFav;
        @BindView(R.id.customTextViewPrice)
        AppCompatTextView customTextViewPrice;
        @BindView(R.id.customTextViewDistance)
        AppCompatTextView customTextViewDistance;
        @BindView(R.id.linearLayoutContent)
        LinearLayout linearLayoutContent;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onClickItem(getAdapterPosition());
                }
            });
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {

        void onClickUnFav(Favorites favorites);

        void onClickItem(int pos);

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
