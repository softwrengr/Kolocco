package com.kooloco.ui.myexperience.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.constant.Common;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hlink44 on 14/9/17.
 */

public class MyExperienceAdapter extends RecyclerView.Adapter<MyExperienceAdapter.ViewHolder> {
    Context context;

    private CallBack callBack;
    List<ExperienceDetails> homes;

    public MyExperienceAdapter(Context context, List<ExperienceDetails> homes, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.homes = homes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_my_experience, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.viewTop.setVisibility((position != 0) ? View.VISIBLE : View.INVISIBLE);

        //Set Data
        holder.customTextViewLocalName.setText(homes.get(position).getFirstname() + " " + homes.get(position).getLastname());
        Picasso.with(context).load(homes.get(position).getProfileImage()).transform(new CircleTransform()).into(holder.imageViewProfile);

        holder.imageViewProfile.setOnClickListener(view -> {
            callBack.onClickImage(position, homes.get(position));
        });
        holder.customTextViewServiceTypeValue.setText(TimeConvertUtils.dateTimeConvertLocalToLocal(homes.get(position).getBookingDate(), "yyyy-MM-dd", "dd MMMM, yyyy"));


        holder.customTextViewTitle.setText(homes.get(position).getExperience().getTitle());

        Picasso.with(context).load(homes.get(position).getExperience().getExperience_url()).placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageViewExp);

        Picasso.with(context).load(homes.get(position).getExperience().getImage_url()).placeholder(R.drawable.place).error(R.drawable.place).into(holder.imageView);

        holder.textViewCurrencyType.setText(homes.get(position).getVisitorCurrency());

        holder.textViewExpPrice.setText("" + homes.get(position).getTotalPrice());


        String durationText = "";
        if (homes.get(position).getMultipleDay().equalsIgnoreCase("1")) {
            durationText = homes.get(position).getDuration() + " " + context.getResources().getString(R.string.schdule_price_days);
        } else {
            durationText = homes.get(position).getDuration() + " " + context.getResources().getString(R.string.hours_exp);
        }

        holder.customTextViewDuration.setText(" " + durationText);


        holder.customTextViewParticipants.setText(" " + homes.get(position).getExtraParticipant() + " " + context.getResources().getString(R.string.add_paeticipants));

        holder.customTextViewLocation.setText(" " + homes.get(position).getExperience().getCity() + ", " + homes.get(position).getExperience().getCountry());

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickItem(position, homes.get(position));
            }
        };
        holder.linearLayoutMain.setOnClickListener(onClickListener);

        //holder.imageView.setOnClickListener(onClickListener);


        String status = homes.get(position).getStatus().toLowerCase();

        String statusText = "";

        switch (status) {
            case Common.OrderDetails.PENDING:
                status = context.getString(R.string.pending_for_approval);
                statusText = context.getString(R.string.pending_for_approval);
                break;
            case Common.OrderDetails.ACCEPTED:
                status = context.getString(R.string.ready_start);
                statusText = context.getString(R.string.ready_start);
                break;
            case Common.OrderDetails.COMPLETED:
                status = Common.OrderDetails.COMPLETED;
                statusText = context.getString(R.string.order_status_completed);
                break;
            case Common.OrderDetails.REJECTED:
                status = Common.OrderDetails.REJECTED;
                statusText = context.getString(R.string.order_status_rejected);
                break;
            case Common.OrderDetails.DECLINE:
                status = Common.OrderDetails.DECLINE;
                statusText = context.getString(R.string.order_status_decline);
                break;
            default:
                status = Common.OrderDetails.PENDING;
                statusText = context.getString(R.string.pending_for_approval);
                break;

        }


        status = status.substring(0, 1).toUpperCase() + status.substring(1);


        statusText = statusText.substring(0, 1).toUpperCase() + statusText.substring(1);

        holder.textViewStatus.setText(statusText);

        holder.textViewStatus.setTextColor(context.getResources().getColor(R.color.yellow));

        if (homes.get(position).getStatus().equalsIgnoreCase(Common.OrderDetails.PENDING)) {
            holder.textViewStatus.setTextColor(context.getResources().getColor(R.color.yellow));
        } else if (homes.get(position).getStatus().equalsIgnoreCase(Common.OrderDetails.ACCEPTED) || homes.get(position).getStatus().equalsIgnoreCase(Common.OrderDetails.COMPLETED)) {
            holder.textViewStatus.setTextColor(context.getResources().getColor(R.color.green));
        } else if (homes.get(position).getStatus().equalsIgnoreCase(Common.OrderDetails.REJECTED)) {
            holder.textViewStatus.setTextColor(context.getResources().getColor(R.color.red));
        }


        float rate = 0.0f;

        try {
            rate = Float.parseFloat(homes.get(position).getExperience().getRate());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        holder.ratingBar.setRating(rate);

        holder.textViewReviewCount.setText("(" + homes.get(position).getExperience().getRateCount() + ")");
    }

    @Override
    public int getItemCount() {
        return homes.size();
    }

    @OnClick(R.id.linearLayoutMain)
    public void onViewClicked() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.viewTop)
        View viewTop;
        @BindView(R.id.imageViewLive)
        ImageView imageViewLive;
        @BindView(R.id.viewBottom)
        View viewBottom;
        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.customTextViewLocalName)
        AppCompatTextView customTextViewLocalName;
        @BindView(R.id.customTextViewServiceTypeValue)
        AppCompatTextView customTextViewServiceTypeValue;
        @BindView(R.id.imageView)
        PorterShapeImageView imageView;
        @BindView(R.id.customTextViewTitle)
        AppCompatTextView customTextViewTitle;
        @BindView(R.id.textViewExpPrice)
        AppCompatTextView textViewExpPrice;
        @BindView(R.id.imageViewExp)
        ImageView imageViewExp;
        @BindView(R.id.customTextViewDuration)
        AppCompatTextView customTextViewDuration;
        @BindView(R.id.customTextViewParticipants)
        AppCompatTextView customTextViewParticipants;
        @BindView(R.id.customTextViewLocation)
        AppCompatTextView customTextViewLocation;
        @BindView(R.id.linearLayoutMain)
        LinearLayout linearLayoutMain;
        @BindView(R.id.linearLayoutExpRoot)
        LinearLayout linearLayoutExpRoot;
        @BindView(R.id.textViewStatus)
        AppCompatTextView textViewStatus;
        @BindView(R.id.ratingBar)
        AppCompatRatingBar ratingBar;
        @BindView(R.id.textViewReviewCount)
        AppCompatTextView textViewReviewCount;

        @BindView(R.id.textViewCurrencyType)
        AppCompatTextView textViewCurrencyType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(int position, ExperienceDetails experienceDetails);

        void onClickImage(int position, ExperienceDetails experienceDetails);

    }

}
