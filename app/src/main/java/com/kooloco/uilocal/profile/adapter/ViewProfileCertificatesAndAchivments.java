package com.kooloco.uilocal.profile.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.model.CertificateInfo;
import com.kooloco.util.picaso.Rounded;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hlink44 on 14/9/17.
 */

public class ViewProfileCertificatesAndAchivments extends RecyclerView.Adapter<ViewProfileCertificatesAndAchivments.ViewHolder> {
    Context context;
    List<CertificateInfo> certificateInfos;
    CallBack callBack;

    public ViewProfileCertificatesAndAchivments(Context context, List<CertificateInfo> certificateInfos, CallBack callBack) {
        this.context = context;
        this.certificateInfos = certificateInfos;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_view_profile_certificate_archivment_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == 0) {
            holder.space.setVisibility(View.VISIBLE);
            holder.space1.setVisibility(View.VISIBLE);
        } else {
            holder.space.setVisibility(View.GONE);
            holder.space1.setVisibility(View.GONE);
        }
        holder.customTextViewName.setText(certificateInfos.get(position).getName());

        if (!certificateInfos.get(position).getImageUrl().isEmpty()) {
            Picasso.with(context).load(certificateInfos.get(position).getImageUrl()).placeholder(R.drawable.place).into(holder.imageViewService);
        }

        holder.imageViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickItem(position, certificateInfos.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return certificateInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewService)
        PorterShapeImageView imageViewService;
        @BindView(R.id.space)
        View space;
        @BindView(R.id.customTextViewName)
        AppCompatTextView customTextViewName;

        @BindView(R.id.space1)
        View space1;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void onClickItem(int position, CertificateInfo certificateInfo);

        void onClickEdit(int position, CertificateInfo certificateInfo);
    }
}
