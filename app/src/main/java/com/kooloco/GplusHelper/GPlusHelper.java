package com.kooloco.GplusHelper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Chirag on 13/06/2016.
 */
public class GPlusHelper implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private Context context;
    private FragmentActivity activity;
    private int RC_SIGN_IN = 5;
    private IGooglePlusTaskComplete callback;

    public GPlusHelper(FragmentActivity activity, Context context) {

        this.activity = activity;
        this.context = context;
        if (mGoogleApiClient==null){
            initialize();
        }

    }

    private void initialize() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(activity /* FragmentActivity */, 15,this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void onLogin(int requestCode, IGooglePlusTaskComplete<GoogleSignInAccount> callback) {
        RC_SIGN_IN = requestCode;
        this.callback = callback;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void connectClient() {
        mGoogleApiClient.connect();
    }

    public void disconnectClient() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void destroyClient(){
        mGoogleApiClient.stopAutoManage(activity);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void OnResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("Test", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if (callback != null)
                callback.onTaskComplete(result.isSuccess(), acct);

        } else {
            // Signed out, show unauthenticated UI.
            if (callback != null)
                callback.onTaskComplete(result.isSuccess(), null);

        }
    }


}
