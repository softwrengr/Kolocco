package com.kooloco.uilocal.addservice.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.data.temp.Temp;
import com.kooloco.model.Certifications;
import com.kooloco.model.Tag;
import com.kooloco.util.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

/**
 * Created by hlink44 on 14/9/17.
 */

public class UploadAchievementsAdapter extends RecyclerView.Adapter<UploadAchievementsAdapter.ViewHolder> {
    Context context;
    List<Certifications> certifications;
    CallBack callBack;

    private int ITEM_TYPE = 1;
    private int FOOTER_TYPE = 2;

    public UploadAchievementsAdapter(Context context, List<Certifications> certifications, CallBack callBack) {
        this.context = context;
        this.certifications = certifications;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_TYPE) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_add__b, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_upload_achivements, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (holder.getItemViewType() == FOOTER_TYPE) {
            holder.imageViewAddSport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onClickAdd();
                }
            });

            holder.buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onClickNext();
                }
            });
            return;
        }

        holder.editTextCertificateTitle.setText(certifications.get(position).getTitles());
        holder.editTextCertificateType.setText(certifications.get(position).getType());
        holder.editTextCertificateDesc.setText(certifications.get(position).getDesc());
        holder.editTextCertificateSubType.setText(certifications.get(position).getSubType());

        if (certifications.get(position).getSportActivity() != null) {
            holder.editTextCertificateSubType.setVisibility(certifications.get(position).getSportActivity().isExpand() ? View.VISIBLE : View.INVISIBLE);
            holder.viewBottomLine.setVisibility(certifications.get(position).getSportActivity().isExpand() ? View.VISIBLE : View.INVISIBLE);
        } else {
            holder.editTextCertificateSubType.setVisibility(View.VISIBLE);
            holder.viewBottomLine.setVisibility(View.VISIBLE);
        }

        holder.imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickDelete(position);
            }
        });

        holder.imageViewCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickSelectImage(position);
            }
        });

        holder.editTextCertificateSubType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickSelectSubSportType(position, certifications.get(position));
            }
        });


        holder.imageViewFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imageViewCertificate.performClick();
            }
        });

        holder.imageViewCertificate.setVisibility(!certifications.get(position).getImageUrl().isEmpty() ? View.VISIBLE : View.INVISIBLE);

        holder.imageViewFilter.setVisibility(certifications.get(position).getImageUrl().isEmpty() ? View.VISIBLE : View.INVISIBLE);

        if (!certifications.get(position).getImageUrl().isEmpty()) {
            Glide.with(context).load(certifications.get(position).getImageUrl()).asBitmap().into(holder.imageViewCertificate);
        }

        holder.editTextCertificateType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickSelectSportType(position);
            }
        });

        holder.editTextCertificateTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    Certifications certifications1 = certifications.get(position);
                    certifications1.setTitles("" + charSequence);
                    certifications.set(position, certifications1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.editTextCertificateDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Certifications certifications1 = certifications.get(position);
                certifications1.setDesc("" + charSequence);
                certifications.set(position, certifications1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onCheckValidation(holder.editTextCertificateTitle, holder.editTextCertificateDesc, certifications.get(position), position);
            }
        });

        holder.flowLayout.removeAllViews();

        for (int i = 0; i < certifications.get(position).getTags().size(); i++) {

            final Tag tag = certifications.get(position).getTags().get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_time, null);
            ((AppCompatTextView) view.findViewById(R.id.customTextViewTag)).setText(tag.getName());
            view.setSelected(tag.isSelect());

            final int finalI = i;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setSelected(!view.isSelected());
                    tag.setSelect(view.isSelected());
                    certifications.get(position).getTags().set(finalI, tag);

                }
            });

            holder.flowLayout.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return certifications.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (certifications.size() == position) {
            return FOOTER_TYPE;
        }
        return ITEM_TYPE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.imageViewClose)
        ImageView imageViewClose;

        @Nullable
        @BindView(R.id.imageViewCertificate)
        PorterShapeImageView imageViewCertificate;

        @Nullable
        @BindView(R.id.editTextCertificateTitle)
        AppCompatEditText editTextCertificateTitle;

        @Nullable
        @BindView(R.id.editTextCertificateType)
        AppCompatEditText editTextCertificateType;

        @Nullable
        @BindView(R.id.editTextCertificateDesc)
        AppCompatEditText editTextCertificateDesc;


        @Nullable
        @BindView(R.id.flowLayout)
        FlowLayout flowLayout;

        @Nullable
        @BindView(R.id.buttonSave)
        AppCompatButton buttonSave;

        @Nullable
        @BindView(R.id.imageViewFilter)
        ImageView imageViewFilter;

        @Nullable
        @BindView(R.id.editTextCertificateSubType)
        AppCompatEditText editTextCertificateSubType;

        @Nullable
        @BindView(R.id.imageViewAddSport)
        ImageView imageViewAddSport;

        @Nullable
        @BindView(R.id.buttonNext)
        AppCompatButton buttonNext;

        @Nullable
        @BindView(R.id.viewBottomLine)
        View viewBottomLine;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickDelete(int position);

        void onClickSelectImage(int position);

        void onClickSelectSportType(int position);

        void onClickSelectSubSportType(int position, Certifications certifications);

        void onCheckValidation(AppCompatEditText appCompatEditTextTitle, AppCompatEditText appCompatEditTextDesc, Certifications certificationsData, int position);

        void onClickAdd();

        void onClickNext();

    }

}
