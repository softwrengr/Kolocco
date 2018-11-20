package com.kooloco.ui.myexperience.fragment;
/**
 * Created by hlink44 on 6/11/17.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.core.AppPreferences;
import com.kooloco.core.Session;
import com.kooloco.data.URLFactory;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.imagevideo.ImageAndVideoPicker;
import com.kooloco.imagevideo.ImagePickerPath;
import com.kooloco.model.AddImages;
import com.kooloco.model.BlogDetails;
import com.kooloco.model.BlogMedia;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.model.MultiFile;
import com.kooloco.model.OrderDetails;
import com.kooloco.model.Quation;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.myexperience.adapter.BlogImagesAdapter;
import com.kooloco.ui.myexperience.presenter.CreateBlogPresenter;
import com.kooloco.ui.myexperience.view.CreateBlogView;
import com.kooloco.util.StaticMap;
import com.kooloco.util.Utils;
import com.kooloco.util.picaso.CircleTransform;
import com.nex3z.flowlayout.FlowLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.ContentValues.TAG;

public class CreateBlogFragment extends BaseFragment<CreateBlogPresenter, CreateBlogView> implements CreateBlogView {

    @BindView(R.id.rbA1)
    RadioButton rbA1;
    @BindView(R.id.rbA2)
    RadioButton rbA2;
    @BindView(R.id.rbA3)
    RadioButton rbA3;
    @BindView(R.id.rbA4)
    RadioButton rbA4;
    @BindView(R.id.editTextHasTags)
    AppCompatEditText editTextHasTags;
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;

    List<String> hasTag = new ArrayList<>();
    @BindView(R.id.textViewText)
    AppCompatTextView textViewText;
    @BindView(R.id.textViewDesc)
    AppCompatTextView textViewDesc;

    List<AddImages> addImages = new ArrayList<>();
    @BindView(R.id.recyclerImages)
    RecyclerView recyclerImages;
    BlogImagesAdapter blogImagesAdapter;
    @BindView(R.id.customTexteditTextWriteNow)
    AppCompatEditText customTexteditTextWriteNow;
    @BindView(R.id.imageViewAdd)
    ImageView imageViewAdd;
    @BindView(R.id.ratingBar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.customTextViewTime)
    AppCompatTextView customTextViewTime;
    @BindView(R.id.customTextViewNameProfile)
    AppCompatTextView customTextViewNameProfile;
    @BindView(R.id.imageViewProfileLocal)
    ImageView imageViewProfileLocal;
    @BindView(R.id.customTextViewRatingValue)
    AppCompatTextView customTextViewRatingValue;
    @BindView(R.id.ratingView)
    FrameLayout ratingView;
    @BindView(R.id.customTextViewLocation)
    AppCompatTextView customTextViewLocation;
    @BindView(R.id.frameLayoutMap)
    FrameLayout frameLayoutMap;
    @BindView(R.id.buttonShareYourExperience)
    AppCompatButton buttonShareYourExperience;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.customTextViewIntroYourSelf)
    AppCompatTextView customTextViewIntroYourSelf;
    @BindView(R.id.imageViewStaticMap)
    PorterShapeImageView imageViewStaticMap;
    @BindView(R.id.customTextViewQ)
    AppCompatTextView customTextViewQ;

    private int height, width, marginLeft;
    private int seconds;
    private File thumbOfImage;
    private String pathVideo = "";
    private String mediaType = "";
    private Uri imageUri;
    private String mainImage = "";
    private ExperienceDetails experienceDetails;

    List<Quation> data = new ArrayList<>();

    @Inject
    AppPreferences appPreferences;

    @Inject
    Session session;
    private BlogDetails blogDetails;

    @Override
    protected int createLayout() {
        return R.layout.fragment_create_blog;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected CreateBlogView createView() {
        return this;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void bindData() {

        rbA1.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.opensans_regular));
        rbA2.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.opensans_regular));
        rbA3.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.opensans_regular));
        rbA4.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.opensans_regular));

        presenter.getQuation();

        setAnsSelect(0);


        String text = "" + experienceDetails.getTagLineOne().replace("" + experienceDetails.getFirstname() + " " + experienceDetails.getLastname() + "", "<font color='" + getActivity().getResources().getColor(R.color.buttonColor) + "'> " + experienceDetails.getFirstname() + " " + experienceDetails.getLastname() + " </font>");
        textViewText.setText(Html.fromHtml(text));

        text = "" + experienceDetails.getTagLineTwo().replace("" + session.getUser().getFirstname() + " " + session.getUser().getLastname() + "", "<font color='" + getActivity().getResources().getColor(R.color.buttonColor) + "'>" + session.getUser().getFirstname() + " " + session.getUser().getLastname() + " </font>");
        textViewDesc.setText(Html.fromHtml(text));

        customTextViewTime.setText(experienceDetails.getDuration() + " hr");

        customTextViewNameProfile.setText(experienceDetails.getFirstname() + " " + experienceDetails.getLastname());
        Picasso.with(getActivity()).load(experienceDetails.getProfileImage()).transform(new CircleTransform()).into(imageViewProfileLocal);
        customTextViewIntroYourSelf.setText(experienceDetails.getIntroYourSelf());
        customTextViewRatingValue.setText(experienceDetails.getRate());


        String staticMapUrl = StaticMap.getUrl(getActivity(), experienceDetails.getMeetingLatitude(), experienceDetails.getMeetingLongitude(), URLFactory.MEETING);

        Glide.with(getActivity()).load(staticMapUrl).asBitmap().into(imageViewStaticMap);

        customTextViewLocation.setText(experienceDetails.getMeetingAddress());

        InputFilter[] inputFilters = {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.toString().matches("[a-zA-Z]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        };

        editTextHasTags.setFilters(inputFilters);

        editTextHasTags.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (editTextHasTags.getText().toString().isEmpty()) {
                        return true;
                    }
                    onViewClickedAddTag();
                    return true;
                }
                return false;
            }
        });


        blogImagesAdapter = new BlogImagesAdapter(getActivity(), addImages, new BlogImagesAdapter.CallBack() {
            @Override
            public void onClick(AddImages addImagesD) {
                if (addImagesD.isVideo()) {
                    playVideo(addImagesD.getVideoPath());
                }
            }

            @Override
            public void onDelete(AddImages addImagesD) {
                if (addImagesD.getSetId().isEmpty()) {
                    addImages.remove(addImagesD);
                    blogImagesAdapter.notifyDataSetChanged();
                } else {
                    presenter.callDeleteMediea(addImagesD);
                }
            }
        });

        recyclerImages.setAdapter(blogImagesAdapter);
        recyclerImages.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        if (appPreferences.getBoolean("isEditBlog")) {
            if (blogDetails != null) {
                setData(blogDetails);
                appPreferences.putBoolean("isEditBlog", false);
            }
        }
        if (blogDetails != null) {
            buttonShareYourExperience.setText(experienceDetails.getIsPublished().equalsIgnoreCase("1") ? getActivity().getResources().getString(R.string.edit_you_experience) : getActivity().getResources().getString(R.string.share_you_experience));
        }
    }

    private void setData(BlogDetails blogDetails) {


        buttonShareYourExperience.setText(experienceDetails.getIsPublished().equalsIgnoreCase("1") ? getActivity().getResources().getString(R.string.edit_you_experience) : getActivity().getResources().getString(R.string.share_you_experience));

        ratingBar.setRating(Float.parseFloat(blogDetails.getRate()));

        customTexteditTextWriteNow.setText(blogDetails.getDescription());

        if (!blogDetails.getTags().isEmpty()) {

            String[] strings = blogDetails.getTags().split(",");

            for (int i = 0; i < strings.length; i++) {
                hasTag.add(strings[i].replace("#", "").trim());
            }

        }

        setHasTag();

        for (BlogMedia blogMedia : blogDetails.getBlogMedia()) {
            if (blogMedia.getMediaType().equalsIgnoreCase("I")) {
                AddImages addImage = new AddImages();
                addImage.setSetId(blogMedia.getId());
                addImage.setVideo(false);
                addImage.setImageUrl(blogMedia.getFile());
                addImages.add(addImage);
            } else {
                AddImages addImage = new AddImages();
                addImage.setSetId(blogMedia.getId());
                addImage.setImageUrl(blogMedia.getVideoThumb());
                addImage.setVideoPath(blogMedia.getFile());
                addImage.setVideo(true);
                addImages.add(addImage);
            }
        }

        blogImagesAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    @OnClick({R.id.rbA1, R.id.rbA2, R.id.rbA3, R.id.rbA4, R.id.imageViewAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rbA1:
                setAnsSelect(0);
                break;
            case R.id.rbA2:
                setAnsSelect(1);
                break;
            case R.id.rbA3:
                setAnsSelect(2);
                break;
            case R.id.rbA4:
                setAnsSelect(3);
                break;
            case R.id.imageViewAdd:
                presenter.pickVideoOrImage(new ImageAndVideoPicker.ImageCallBack() {
                    @Override
                    public void sendImage(ImagePickerPath imagePicker) {
                        if (imagePicker.isPick()) {
                            mediaType = (imagePicker.isVideo() ? "V" : "P");
                            if (imagePicker.isVideo()) {
                                pathVideo = imagePicker.getImagePath();
                                mainImage = "";
                                videoToImage(pathVideo);
                            } else {
                                pathVideo = "";
                                mainImage = imagePicker.getImagePath();
                                // Glide.with(getContext()).load(mainImage).into(addPostImageViewMainImage);

                                presenter.openImageFilter(mainImage, new ImageFilterFragment.imageFilterCallback() {
                                    @Override
                                    public void imageCallBack(String url) {

                                        AddImages addImage = new AddImages();
                                        addImage.setImageUrl(url);
                                        addImages.add(addImage);
                                        addImage.setVideo(false);
                                        blogImagesAdapter.notifyDataSetChanged();
                                    }
                                });

                            }
                        }
                    }
                });

                /*presenter.imagePick(new ImagePicker.ImagePickerResult() {
                    @Override
                    public void onResult(String path) {
                        AddImages addImage = new AddImages();
                        addImage.setImageUrl(path);
                        addImages.add(addImage);
                        blogImagesAdapter.notifyDataSetChanged();
                    }
                });*/
                break;
        }
    }

    public void videoToImage(String path) {
        try {
            Uri uri = Uri.parse(path);
            Bitmap bitmapSample;
            //  Bitmap bitmap;
            if (Utils.isVideoFile(path)) {
                bitmapSample = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
            } else {
                bitmapSample = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
            }

            pathVideo = path;
            thumbOfImage = Utils.saveImage(bitmapSample);
            height = bitmapSample.getHeight();
            width = bitmapSample.getWidth();


            mediaType = "V";
            thumbOfImage = Utils.saveImage(bitmapSample);

            AddImages addImage = new AddImages();
            addImage.setImageUrl(thumbOfImage.toString());
            addImages.add(addImage);
            addImage.setVideoPath(path);

            addImage.setVideo(true);
            blogImagesAdapter.notifyDataSetChanged();

//            Glide.with(getContext()).load(thumbOfImage).into(addPostImageViewMainImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAnsSelect(int position) {
        rbA1.setChecked((position == 0));
        rbA2.setChecked((position == 1));
        rbA3.setChecked((position == 2));
        rbA4.setChecked((position == 3));
    }

    private int getSelectAns() {
        if (rbA1.isChecked()) {
            return 0;

        } else if (rbA2.isChecked()) {
            return 2;

        } else if (rbA3.isChecked()) {
            return 1;

        } else if (rbA4.isChecked()) {
            return 3;
        }
        return 0;
    }

    public void onViewClickedAddTag() {

        //Has tag funcanality
/*
        String str = editTextHasTags.getText().toString().trim();

        editTextHasTags.setText("");

        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
        Matcher mat = MY_PATTERN.matcher(str);

        while (mat.find()) {
            //System.out.println(mat.group(1));
            hasTag.add(mat.group(1));
        }
*/
        String str = editTextHasTags.getText().toString().trim();

        editTextHasTags.setText("");

        hasTag.add(str.trim());


        setHasTag();
    }

    private void setHasTag() {
        flowLayout.removeAllViews();

        for (String category : hasTag) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.row_has_tag, null);


            ((AppCompatTextView) view.findViewById(R.id.textViewName)).setText("#" + category);

            ((AppCompatTextView) view.findViewById(R.id.textViewName)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hasTag.remove(((AppCompatTextView) view.findViewById(R.id.textViewName)).getText().toString());
                    setHasTag();
                }
            });

            flowLayout.addView(view);
        }

    }


    @OnClick(R.id.buttonShareYourExperience)
    public void onViewClickedShared() {

        if (addImages.size() == 0) {
            showMessage(getString(R.string.select_add_images));
            return;
        }
        if (customTexteditTextWriteNow.getText().toString().isEmpty()) {
            showMessage("Please enter your experience");
            return;
        }
        if (hasTag.size() == 0) {
            showMessage("Please enter hash tag");
            return;
        }

       // showLoader();
        uploadImagesVideo(getFileData(), getVideoThum(), "publishExperience", new CallBackUploadImagesMulti() {
            @Override
            public void onCallBack(List<MultiFile> imagePath) {
                presenter.callWs(experienceDetails, ratingBar.getRating(), customTexteditTextWriteNow.getText().toString(), hasTag, imagePath, data.get(0).getQuestion(), data, getSelectAns());
            }
        });
    }

    @Override
    public void setExperienceDetails(ExperienceDetails experienceDetails) {
        this.experienceDetails = experienceDetails;
    }

    @Override
    public void setQuation(List<Quation> data) {
        this.data = data;

        customTextViewQ.setText(data.get(0).getQuestion());

        rbA1.setText(data.get(0).getAnswer().get(0).getAnswer());
        rbA2.setText(data.get(0).getAnswer().get(2).getAnswer());
        rbA3.setText(data.get(0).getAnswer().get(1).getAnswer());
        rbA4.setText(data.get(0).getAnswer().get(3).getAnswer());
    }

    @Override
    public void setBlogDetails(BlogDetails data) {
        this.blogDetails = data;
    }

    @Override
    public void deleteMedia(AddImages addImage) {
        addImages.remove(addImage);
        blogImagesAdapter.notifyDataSetChanged();
    }


    private List<String> getFileData() {
        List<String> strings = new ArrayList<>();

        for (AddImages addImage : addImages) {
            if (addImage.getSetId().isEmpty()) {
                if (addImage.getVideoPath().isEmpty()) {
                    strings.add(addImage.getImageUrl());
                } else {
                    strings.add(addImage.getVideoPath());
                }
            }
        }

        return strings;
    }

    private List<String> getVideoThum() {
        List<String> strings = new ArrayList<>();

        for (AddImages addImage : addImages) {
            if (addImage.getSetId().isEmpty()) {
                if (!addImage.getVideoPath().isEmpty()) {
                    strings.add(addImage.getImageUrl());
                }
            }
        }

        return strings;
    }

    @OnClick(R.id.frameLayoutMap)
    public void onViewClickedMap() {
        presenter.openMapScreen(experienceDetails);
    }

}
