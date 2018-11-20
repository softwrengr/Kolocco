package com.kooloco.crop;

import java.io.File;

/**
 * on 4/11/16.
 */

public class CropMyImage {
    private File file;
    private int requestCode;
    private String title;

    public CropMyImage(File file, int requestCode, String title) {
        this.file = file;
        this.requestCode = requestCode;
        this.title = title;
    }


}
