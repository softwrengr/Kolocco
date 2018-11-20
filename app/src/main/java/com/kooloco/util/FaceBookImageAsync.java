package com.kooloco.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by  on 16/12/15.
 */
public class FaceBookImageAsync extends AsyncTask<Void, Integer, Void> {

    Bitmap bitmap;
    String url;
    onImageDownloadComplete onImageDownloadComplete;
    private File file;
    Context context;

    public FaceBookImageAsync(Context context, String url, onImageDownloadComplete onImageDownloadComplete) {
        this.url = url;
        this.onImageDownloadComplete = onImageDownloadComplete;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        bitmap = getBitmapFromURL(url);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        onImageDownloadComplete.getDownloadBitmap(bitmap);
    }

    public interface onImageDownloadComplete {
        public void getDownloadBitmap(Bitmap bitmap);
    }

    public Bitmap getBitmapFromURL(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
