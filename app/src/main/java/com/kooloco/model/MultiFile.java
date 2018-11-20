package com.kooloco.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hlink on 22/1/18.
 */

public class MultiFile {
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("video_thumb")
    @Expose
    private String videpThumb;
    @SerializedName("media_type")
    @Expose
    private String mediaType;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getVidepThumb() {
        return videpThumb;
    }

    public void setVidepThumb(String videpThumb) {
        this.videpThumb = videpThumb;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
