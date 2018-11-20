package com.kooloco.core;

import android.Manifest;

import java.io.File;

/**
 * Created by hlink21 on 27/4/16.
 */
public final class Common {

    public static final String IMAGE_URI = "image_uri";
    public static final String VIDEO_URI = "video_uri";
    public static final String SHARED_PREF_NAME = "application";
    public static final String ACTIVITY_FIRST_PAGE = "FirstPage";
    public static final String CURRENT_USER = "current_user";
    public static final String USER_BUNDLE = "UserBundle";
    public static final String USER_JSON = "UserJSON";
    public static final String USER_CURRENCY = "UserCURRENCY";
    public static final String LOCAL_CURRENCY = "LocalCURRENCY";

    public static final String APP_DIRECTORY = "YSP";
    public static final String SNAP_DIRECTORY = APP_DIRECTORY + File.separator + "Snap";

    public static final int REQUEST_PERMISSION = 1;
    public static final int REQUEST_CAMERA_PERMISSION = 1;
    public static final int REQUEST_GALLERY_PERMISSION = 2;

    public static final String[] PERMISSIONS_GALLERY = {Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final String[] PERMISSIONS_RECORD_VIDEO = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};

    public static final String IS_LOGIN = "is_login";

    public static final int TAP_TO_LOAD_LIMIT = 4;
    public static final int STORYBOARD_LOAD_LIMIT = 7;
    public static final String MESSAGE = "message";
    public static final String DISTANCE = "Distance";
    public static final String SERVER_TIMEZONE = "UTC";
    public static String IS_ON_SERVICE = "is_on_service";
    public static String EXPSELECTDATE = "expSelectDate";


    public static class RequestCode {
        public static final int REQUEST_TAKE_PHOTO = 1;
        public static final int RESULT_LOAD_IMAGE = 2;
        public static final int REQUEST_IMAGE_AND_VIDEO = 3;
        public static final int REQUEST_FROM_CAMERA = 4;
        public static final int REQUEST_TO_FINISH = 5;
        public static final int REQUEST = 10;
        public static final int REQUEST_TRIM_VIDEO = 11;
        public static final int CROP_IMAGE_ACTIVITY_REQUEST_CODE = 203;

    }

    public static class ResultCode {
        public static final int IMAGE_RESULT = 1;
        public static final int VIDEO_RESULT = 2;
        public static final int FINISH_ME = 444;
        public static final int MESSAGE = 10;

    }

    public static class RegX {
        public static final String USERNAME = "^[A-Za-z0-9_-]{3,25}$";
        public static final String FULL_NAME = "^(?!\\s)$";

    }

    public enum SelectionModes {
        NONE, SINGLE, MULTI
    }

    public static class VisitorRate {
        public static final String ON_TIME = "On Time";
        public static final String FRIENDLY = "Friendly";
        public static final String RESPECTFUL = "Respectful";
        public static final String ENJOYABLE = "Enjoyable";
    }

    public static class LocalRate {
        public static final String QUALITY_PRICE = "Quality-price";
        public static final String ARRIVAL_TIME = "Arrival Time";
        public static final String PROFESSIONALISM = "Professionalism";
        public static final String GUIDANCE = "Guidance";
        public static final String MEETING_SPOT = "Meeting Point";
        public static final String ACTIVITY_DURATION = "Activity Duration";
        public static final String Others = "Other";
    }

    public static class BankFields {
        public static final String BIC_SWIFT = "bic_swift";

        public static final String ACCOUNT_NUMBER = "account_number";

        public static final String IBAN_ACCOUNT_NUMBER = "iban_or_account_number";

        public static final String IBAN = "iban_number";

        public static final String BSB_BANK_ID = "bsb_bank_id";
        public static final String BSB_BRANCH_ID = "bsb_branch_id";

        public static final String BANK_CODE = "bank_code";
        public static final String BRANCH_CODE = "branch_code";

        public static final String TRANSIT_NUMBER = "transit_number";

        public static final String BRANCH_NAME = "branch_name";
        public static final String CITY = "city";

        public static final String IFSC_CODE = "ifsc_code";


        public static final String BANK_CODE_BRANCH_CODE = "bank_code_branch_code";

        public static final String CLABE_NUMBER = "clabe_number";
        public static final String ROUTING_NUMBER = "routing_number";

        public static final String BRANCH_SORTING_CODE = "branch_sorting_code";
        public static final String BUILDING_SOCIRTY_CODE = "building_society_account";

        public static final String PROVINCE = "province";
        public static final String ACCOUNT_TYPE = "account_type";

        public static final String TAX_ID = "tax_id";
    }
}
