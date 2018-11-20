package com.kooloco.uilocal.addservice.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kooloco.R;
import com.kooloco.model.Country;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    Context context;
    List<Country> countries;

    private CallBack callBack;

    public CountryAdapter(Context context, List<Country> countries, CallBack callBack) {
        this.context = context;
        this.countries = countries;
        this.callBack = callBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_select_country, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(context).load(countries.get(position).getFlag()).into(holder.imageCountry);
        holder.textViewCountryCode.setText(countries.get(position).getDialCode());
        holder.textViewCountryName.setText(countries.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageCountry)
        AppCompatImageView imageCountry;
        @BindView(R.id.textViewCountryName)
        AppCompatTextView textViewCountryName;
        @BindView(R.id.textViewCountryCode)
        AppCompatTextView textViewCountryCode;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                callBack.onItemClick(getAdapterPosition());
            });

        }

    }

    public interface CallBack {

        void onItemClick(int position);

    }

}