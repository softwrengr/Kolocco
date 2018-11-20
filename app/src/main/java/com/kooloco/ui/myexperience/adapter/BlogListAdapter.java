package com.kooloco.ui.myexperience.adapter;

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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kooloco.R;
import com.kooloco.model.BlogDetails;
import com.kooloco.model.BlogMedia;
import com.kooloco.util.TimeConvertUtils;
import com.kooloco.util.picaso.CircleTransform;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;

/**
 * Created by hlink44 on 14/9/17.
 */

public class BlogListAdapter extends RecyclerView.Adapter<BlogListAdapter.ViewHolder> {
    Context context;
    private CallBack callBack;
    List<BlogDetails> homes;
    FragmentManager childFragmentManager;
    private VelocityTracker velocityTracker;
    private float velocity;

    public BlogListAdapter(Context context, FragmentManager childFragmentManager, List<BlogDetails> homes, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
        this.homes = homes;
        this.childFragmentManager = childFragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_blog, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.pageIndicatorView.setCount(homes.get(position).getBlogMedia().size());
        holder.pageIndicatorView.setAnimationType(AnimationType.WORM);
        holder.pageIndicatorView.setStrokeWidth(2);

        final BlogSlider homeSlider = new BlogSlider(context, homes.get(position).getBlogMedia(), new BlogSlider.CallBack() {
            @Override
            public void onClick(int positionSub, BlogMedia blogMedia) {
                if (blogMedia.getMediaType().equalsIgnoreCase("V")) {
                    callBack.onClickVideo(positionSub, blogMedia);
                } else {
                    callBack.onClickItem(position);
                }
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerViewImageSlide.setLayoutManager(layoutManager);
        holder.recyclerViewImageSlide.setAdapter(homeSlider);
        holder.recyclerViewImageSlide.setOnFlingListener(null);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(holder.recyclerViewImageSlide);

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
                if (firstVisibleItemPosition != 3) {
                    holder.recyclerViewImageSlide.smoothScrollToPosition(firstVisibleItemPosition + 1);
                }
            }
        });

        holder.linearLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickItem(position);
            }
        });


        holder.checkboxFav.setChecked(homes.get(position).getIsLike().equalsIgnoreCase("1"));
        //Set service adapter
        Picasso.with(context).load(homes.get(position).getProfileImage()).transform(new CircleTransform()).into(holder.imageViewProfile);

        holder.imageViewProfile.setOnClickListener(view -> callBack.onClickImage(position, homes.get(position)));
        holder.checkboxFav.setOnClickListener(view -> {
            callBack.onClickFav(position, homes.get(position));
            holder.checkboxFav.setScaleX(0.5f);
            holder.checkboxFav.setScaleY(0.5f);
            animIcon(holder.checkboxFav, DynamicAnimation.SCALE_X, 1f);
            animIcon(holder.checkboxFav, DynamicAnimation.SCALE_Y, 1f);
        });

        holder.customTextViewName.setText(homes.get(position).getFirstname() + " " + homes.get(position).getLastname());


        holder.ratingBar.setRating(Float.parseFloat(homes.get(position).getRate()));

        holder.customTextViewTag.setText(homes.get(position).getTags());

        holder.customTextViewAccept.setText(homes.get(position).getLikes());
        holder.customTextViewChat.setText(homes.get(position).getTotalComment());

        String date = TimeConvertUtils.dateAndTimeGet(TimeConvertUtils.dateTimeConvertServertToLocal(homes.get(position).getInsertdate(), "yyyy-MM-dd HH:mm:ss", "dd MMM, yyyy HH:mm:ss"), "dd MMM, yyyy HH:mm:ss");

        holder.customTextViewDate.setText(date);

        String text = "";
        text = homes.get(position).getTagline().replace("" + homes.get(position).getFirstname() + " " + homes.get(position).getLastname(), "<font color='" + context.getResources().getColor(R.color.buttonColor) + "'> " + homes.get(position).getFirstname() + " " + homes.get(position).getLastname() + " </font>");
        holder.customTextViewDesc.setText(Html.fromHtml(text));

    }

    @Override
    public int getItemCount() {
        return homes.size();
    }

    @OnClick(R.id.linearLayoutMain)
    public void onViewClicked() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.viewPager)
        ViewPager viewPager;
        @BindView(R.id.recyclerViewImageSlide)
        RecyclerView recyclerViewImageSlide;
        @BindView(R.id.indicator)
        CircleIndicator indicator;
        @BindView(R.id.pageIndicatorView)
        PageIndicatorView pageIndicatorView;
        @BindView(R.id.imageViewLeft)
        ImageView imageViewLeft;
        @BindView(R.id.imageViewRight)
        ImageView imageViewRight;
        @BindView(R.id.checkboxFav)
        CheckBox checkboxFav;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;
        @BindView(R.id.customTextViewDesc)
        AppCompatTextView customTextViewDesc;
        @BindView(R.id.ratingBar)
        AppCompatRatingBar ratingBar;
        @BindView(R.id.customTextViewTag)
        AppCompatTextView customTextViewTag;
        @BindView(R.id.imageViewProfile)
        ImageView imageViewProfile;
        @BindView(R.id.customTextViewAccept)
        AppCompatTextView customTextViewAccept;
        @BindView(R.id.customTextViewChat)
        AppCompatTextView customTextViewChat;
        @BindView(R.id.linearLayoutMain)
        LinearLayout linearLayoutMain;
        @BindView(R.id.customTextViewDate)
        AppCompatTextView customTextViewDate;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(int position);

        void onClickItemBlog(int position);

        void onClickVideo(int position, BlogMedia blogMedia);

        void onClickFav(int position, BlogDetails blogDetails);

        void onClickImage(int position, BlogDetails blogDetails);

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
