package com.kooloco.ui.authantication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.maps.model.LatLng;
import com.kooloco.GplusHelper.GPlusHelper;
import com.kooloco.GplusHelper.IGooglePlusTaskComplete;
import com.kooloco.R;
import com.kooloco.di.component.ActivityComponent;
import com.kooloco.facebookhelper.FbHelper;
import com.kooloco.facebookhelper.FbResponse;
import com.kooloco.facebookhelper.IFacebookTaskComplet;
import com.kooloco.model.FbGoogleData;
import com.kooloco.ui.base.BaseActivity;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.navigation.AppNavigationProvider;
import com.kooloco.util.FaceBookImageAsync;
import com.kooloco.util.ImagePicker;
import com.kooloco.util.LocationManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by hlink44 on 23/8/17.
 */

public class AuthActivity extends AppNavigationProvider {
    private static final int RC_SIGN_IN = 25;
    GPlusHelper gPlusHelper;
    FbHelper fb;
    String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gPlusHelper = new GPlusHelper(AuthActivity.this, getApplicationContext());
        fb = new FbHelper(this, getApplicationContext());
        openLogin().add(false);

        getLocationManager().triggerLocation(true, new LocationManager.LocationListener() {
            @Override
            public void onLocationAvailable(LatLng latLng) {
                BaseFragment.latitude = latLng.latitude;
                BaseFragment.longitude = latLng.longitude;
            }

            @Override
            public void onFail(LocationManager.LocationListener.Status status) {

            }
        });
    }

    @Override
    public int findFragmentPlaceHolder() {
        return R.id.placeHolder;
    }

    @Override
    public int findContentView() {
        return R.layout.activity_auth;
    }

    @Override
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            gPlusHelper.OnResult(requestCode, resultCode, data);
        }
        fb.setCallbackManager(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gPlusHelper.disconnectClient();
    }

    public void loginGoogle(final CallBack callBack) {
        gPlusHelper.onLogin(RC_SIGN_IN, new IGooglePlusTaskComplete<GoogleSignInAccount>() {
            @Override
            public void onTaskComplete(boolean success, @Nullable GoogleSignInAccount data) {
                if (success) {
                    final FbGoogleData fbGoogleData = new FbGoogleData();
                    fbGoogleData.setIsSocialId(data.getId());
                    fbGoogleData.setFirstName(data.getDisplayName());
                    fbGoogleData.setLastName("");
                    fbGoogleData.setEmail(data.getEmail());
                    fbGoogleData.setSignType("G");
                    if (data.getPhotoUrl() != null) {
                        fbGoogleData.setImageUrl(data.getPhotoUrl().toString());
                    } else {
                        fbGoogleData.setImageUrl("");
                    }
                    callBack.onSuccess(fbGoogleData);

/*
                    if (data.getPhotoUrl().toString().isEmpty()) {
                        fbGoogleData.setImageUrl("");
                        callBack.onSuccess(fbGoogleData);
                    } else {
                        fbImageDownload(data.getPhotoUrl().toString(), new CallBackImage() {
                            @Override
                            public void onSuccess(String url) {

                            }
                        });
                    }
*/

                    // Toast.makeText(getApplicationContext(), "Welcome " + data.getDisplayName(), Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(getApplicationContext(), "Fail to login", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void loginFacebook(final CallBack callBack) {
        fb.onLogin(new IFacebookTaskComplet<FbResponse>() {
            @Override
            public void onTaskComplete(boolean success, @Nullable FbResponse data) {
                if (success) {
                    try {
                        if (!data.getEmail().isEmpty()) {
                            final FbGoogleData fbGoogleData = new FbGoogleData();
                            fbGoogleData.setIsSocialId(data.getId());

                            if(data.getName()!=null){
                                fbGoogleData.setFirstName(data.getName());
                            }else {
                                fbGoogleData.setFirstName("");

                            }
                            fbGoogleData.setLastName("");
                            fbGoogleData.setEmail(data.getEmail());
                            fbGoogleData.setSignType("F");
                            String url = "http://graph.facebook.com/" + data.getId() + "/picture?type=large";
                            fbGoogleData.setImageUrl(url);
                            callBack.onSuccess(fbGoogleData);

                        }

                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        gPlusHelper.connectClient();
    }

    public interface CallBack {
        void onSuccess(FbGoogleData data);

    }

    public interface CallBackImage {
        void onSuccess(String url);
    }

    private void fbImageDownload(String imageUrl, final CallBackImage callBackImage) {
        FaceBookImageAsync faceBookImageAsync = new FaceBookImageAsync(AuthActivity.this, imageUrl, new FaceBookImageAsync.onImageDownloadComplete() {
            @Override
            public void getDownloadBitmap(Bitmap bitmap) {
                try {
                    callBackImage.onSuccess(storeImageToCache(bitmap));
                } catch (Exception e) {
                    callBackImage.onSuccess("");
                }
            }
        });
        faceBookImageAsync.execute();
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
