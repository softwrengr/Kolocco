package com.kooloco.uilocal.profile.adapter;

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
import com.kooloco.util.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class EditAchievementsAdapter extends RecyclerView.Adapter<EditAchievementsAdapter.ViewHolder> {
    Context context;
    List<Certifications> certifications;
    CallBack callBack;



    public EditAchievementsAdapter(Context context, List<Certifications> certifications, CallBack callBack) {
        this.context = context;
        this.certifications = certifications;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_edit_achivements, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.editTextCertificateTitle.setText(certifications.get(position).getTitles());
        holder.editTextCertificateSubType.setText(certifications.get(position).getSubType());

        holder.editTextCertificateType.setText(certifications.get(position).getType());
        holder.editTextCertificateDesc.setText(certifications.get(position).getDesc());

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

        if (!certifications.get(position).getImageUrl().isEmpty()) {
            Glide.with(context).load(certifications.get(position).getImageUrl()).asBitmap().into(holder.imageViewCertificate);
        } else {
            holder.imageViewCertificate.setImageDrawable(context.getResources().getDrawable(R.drawable.drawable_black_filter));
        }

        holder.editTextCertificateSubType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickSelectSubSportType(position, certifications.get(position));
            }
        });


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
                callBack.onCheckValidation(holder.editTextCertificateTitle, holder.editTextCertificateDesc, certifications.get(position));
            }
        });

        holder.flowLayout.removeAllViews();

        for (String s : Temp.getAddTags()) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_time, null);
            ((AppCompatTextView) view.findViewById(R.id.customTextViewTag)).setText(s);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setSelected(!view.isSelected());
                }
            });
            holder.flowLayout.addView(view);
        }
    }

    @Override
    public int getItemCount() {
        return certifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewClose)
        ImageView imageViewClose;
        @BindView(R.id.imageViewCertificate)
        PorterShapeImageView imageViewCertificate;
        @BindView(R.id.editTextCertificateTitle)
        AppCompatEditText editTextCertificateTitle;
        @BindView(R.id.editTextCertificateType)
        AppCompatEditText editTextCertificateType;
        @BindView(R.id.editTextCertificateDesc)
        AppCompatEditText editTextCertificateDesc;
        @BindView(R.id.flowLayout)
        FlowLayout flowLayout;
        @BindView(R.id.buttonSave)
        AppCompatButton buttonSave;
        @BindView(R.id.editTextCertificateSubType)
        AppCompatEditText editTextCertificateSubType;

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

        void onCheckValidation(AppCompatEditText appCompatEditTextTitle, AppCompatEditText appCompatEditTextDesc, Certifications certificationsData);


    }

}
