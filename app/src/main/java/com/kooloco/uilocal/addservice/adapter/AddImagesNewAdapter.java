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
import com.kooloco.model.Certifications;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

/**
 * Created by hlink44 on 14/9/17.
 */

public class AddImagesNewAdapter extends RecyclerView.Adapter<AddImagesNewAdapter.ViewHolder> {
    Context context;
    List<Certifications> certifications;
    CallBack callBack;

    private int ITEM_TYPE = 1;
    private int FOOTER_TYPE = 2;

    boolean isEdit = false;

    public AddImagesNewAdapter(Context context, List<Certifications> certifications, boolean isEdit, CallBack callBack) {
        this.context = context;
        this.certifications = certifications;
        this.callBack = callBack;
        this.isEdit = isEdit;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_local_add_images, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.textViewTitle.setText(certifications.get(position).getTitles());

        holder.imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickDelete(certifications.get(position));
            }
        });

        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClickEdit(certifications.get(position));
            }
        });

        if (!certifications.get(position).getImageUrl().isEmpty()) {
            Glide.with(context).load(certifications.get(position).getImageUrl()).asBitmap().placeholder(R.drawable.place).into(holder.imageViewAddImages);
        }


    }

    @Override
    public int getItemCount() {
        return certifications.size()/* + 1*/;
    }

    @Override
    public int getItemViewType(int position) {
    /*    if (certifications.size() == position) {
            return FOOTER_TYPE;
        }*/
        return ITEM_TYPE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewAddImages)
        PorterShapeImageView imageViewAddImages;

        @BindView(R.id.textViewTitle)
        AppCompatTextView textViewTitle;

        @BindView(R.id.imageViewEdit)
        ImageView imageViewEdit;
        @BindView(R.id.imageViewClose)
        ImageView imageViewClose;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {

        void onClickEdit(Certifications certifications);

        void onClickDelete(Certifications certifications);

    }
}
