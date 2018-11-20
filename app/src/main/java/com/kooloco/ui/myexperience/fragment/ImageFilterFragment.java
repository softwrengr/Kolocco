package com.kooloco.ui.myexperience.fragment;
/**
 * Created by hlink on 22/1/18.
 */

import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.ImageFilter;
import com.kooloco.ui.authantication.AuthActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.myexperience.adapter.ImageFilterListAdapter;
import com.kooloco.ui.myexperience.presenter.ImageFilterPresenter;
import com.kooloco.ui.myexperience.view.ImageFilterView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;

import static com.facebook.FacebookSdk.getCacheDir;

/**
 * Created by hlink on 8/1/18.
 */

public class ImageFilterFragment extends BaseFragment<ImageFilterPresenter, ImageFilterView> implements ImageFilterView {

    @BindView(R.id.imageViewFilterMain)
    ImageView imageViewFilterMain;
    @BindView(R.id.recyclerNotification)
    RecyclerView recyclerNotification;
    private String imageUrl = "";
    int selectPosition = 0;
    private imageFilterCallback callBack;

    @Override
    protected int createLayout() {
        return R.layout.fragment_image_filter;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected ImageFilterView createView() {
        return this;
    }

    @Override
    protected void bindData() {
        Glide.with(getActivity()).load(imageUrl).asBitmap().into(imageViewFilterMain);

        //Glide.with(getActivity()).load(imageUrl).asBitmap().transform(new CropCircleTransformation(getActivity())).into(imageViewFilterMain);


        List<ImageFilter> imageFilters = new ArrayList<>();


        ImageFilter imageFilter = new ImageFilter();
        imageFilter.setImageFilterName("None");

        ImageFilter imageFilter1 = new ImageFilter();
        imageFilter1.setImageFilterName("None1");

        ImageFilter imageFilter2 = new ImageFilter();
        imageFilter2.setImageFilterName("None2");

        ImageFilter imageFilter3 = new ImageFilter();
        imageFilter3.setImageFilterName("None3");

        ImageFilter imageFilter4 = new ImageFilter();
        imageFilter4.setImageFilterName("None4");

        ImageFilter imageFilter5 = new ImageFilter();
        imageFilter5.setImageFilterName("None5");

        ImageFilter imageFilter6 = new ImageFilter();
        imageFilter6.setImageFilterName("None6");

        imageFilters.add(imageFilter);
        imageFilters.add(imageFilter1);
        imageFilters.add(imageFilter2);
        imageFilters.add(imageFilter3);
        imageFilters.add(imageFilter4);
        imageFilters.add(imageFilter5);
        imageFilters.add(imageFilter6);

        recyclerNotification.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNotification.setAdapter(new ImageFilterListAdapter(getActivity(), imageFilters, imageUrl, new ImageFilterListAdapter.CallBack() {
            @Override
            public void onClickItem(int position) {
                selectPosition = position;
                if (position == 0) {
                    Glide.with(getActivity()).load(imageUrl).asBitmap().into(imageViewFilterMain);
                } else if (position == 1) {
                    Glide.with(getActivity()).load(imageUrl).asBitmap().transform(new InvertFilterTransformation(getActivity())).into(imageViewFilterMain);

                } else if (position == 2) {
                    Glide.with(getActivity()).load(imageUrl).asBitmap().transform(new ToonFilterTransformation(getActivity())).into(imageViewFilterMain);

                } else if (position == 3) {
                    Glide.with(getActivity()).load(imageUrl).asBitmap().transform(new SepiaFilterTransformation(getActivity())).into(imageViewFilterMain);

                } else if (position == 4) {
                    Glide.with(getActivity()).load(imageUrl).asBitmap().transform(new ContrastFilterTransformation(getActivity())).into(imageViewFilterMain);

                } else if (position == 5) {
                    Glide.with(getActivity()).load(imageUrl).asBitmap().transform(new BrightnessFilterTransformation(getActivity())).into(imageViewFilterMain);

                } else if (position == 6) {
                    Glide.with(getActivity()).load(imageUrl).asBitmap().transform(new SketchFilterTransformation(getActivity())).into(imageViewFilterMain);

                }
            }
        }));
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void setCallBack(imageFilterCallback callBack) {
        this.callBack = callBack;
    }

    @OnClick(R.id.toolbarDone)
    public void onClick() {
        int position = selectPosition;
        Transformation<Bitmap> bitmapTransformation = null;

        if (position == 0) {

        } else if (position == 1) {
            bitmapTransformation = new InvertFilterTransformation(getActivity());
        } else if (position == 2) {
            bitmapTransformation = new ToonFilterTransformation(getActivity());
        } else if (position == 3) {
            bitmapTransformation = new SepiaFilterTransformation(getActivity());
        } else if (position == 4) {
            bitmapTransformation = new ContrastFilterTransformation(getActivity());
        } else if (position == 5) {
            bitmapTransformation = new BrightnessFilterTransformation(getActivity());
        } else if (position == 6) {
            bitmapTransformation = new SketchFilterTransformation(getActivity());
        }

        if (position == 0) {
            callBack.imageCallBack(imageUrl);
            goBack();
        } else {
            Glide.with(getActivity()).load(imageUrl).asBitmap().transform(bitmapTransformation).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    String imageUrlFilter = storeImageToCache(resource);
                    callBack.imageCallBack(imageUrlFilter);
                    goBack();
                }
            });

        }

    }

    public interface imageFilterCallback {
        void imageCallBack(String url);
    }


    public String storeImageToCache(Bitmap data) {
        Bitmap thumbnail = null;
        try {
            Date dateTime = new Date();
            thumbnail = data;
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            String filenamePath = "tmp2" + System.currentTimeMillis() + ".jpg";
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File outputDir = getCacheDir();
            File file = new File(outputDir.getPath() + "/" + filenamePath);
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();
            return file.getAbsolutePath().toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
