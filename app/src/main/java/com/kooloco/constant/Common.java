package com.kooloco.constant;

import android.Manifest;

public class Common {
    public static final String ACTIVITY_FIRST_PAGE = "first_page";
    public static final String SHARED_PREF_NAME = "prefMy";
    public static final int REQUEST_PERMISSION = 2;

    public static final String[] PERMISSIONS_GALLERY = {Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final String[] PERMISSIONS_READ_CONTACT = {Manifest.permission.READ_CONTACTS};

    public static final String[] PERMISSIONS_WRITE_SETTING = {Manifest.permission.WRITE_SETTINGS};

    public static final int REQUEST_CAMERA_PERMISSION = 5;
    public static final int REQUEST_GALLERY_PERMISSION = 4;
    public static final int REQUEST_READ_CONTACT = 5;
    public static final int REQUEST_WRITE_SETTING = 7;

    public static final String SUPPORT = "SUPPORT";
    public static final String HELP = "HELP";
    public static final String FAQ = "FAQ";

    public static final String APPLANG = "AppLang";

    public static final String WEBTITLE = "webTitle";
    public static final String WEBURL = "webUrl";


    public static final int FLATAMOUNT = 4;

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

    public class Crop {
        public static final String IMAGE = "image";
        public static final String REQUEST_CODE = "request_code";
        public static final String TITLE = "title";
        public static final String ISCUSTOMCROP = "isCustomCrop";

    }

    public class Visitor {
        public static final String ACCEPT_ORDER = "accept_order";
        public static final String COMPLETE_ORDER = "complete_order";
        public static final String DECLINE_ORDER = "decline_order";

        public static final String MODIFY_LOCATION = "modify_location";
        public static final String MODIFY_DURATION = "modify_duration";
        public static final String ACCEPT_OBJECTION = "accept_objection";
        public static final String MODIFY_OBJECTION = "modify_objection";

        public static final String PAYMENT_REQUEST = "payment_request";
        public static final String BLOG_COMMENT = "blog_comment";
        public static final String BLOG_LIKE = "blog_like";

        public static final String RATE_TO_LOCAL = "rate_to_local";

        public static final String ORGANIZATIONREQUEST = "organisation_request";

        public static final String NEW_CHAT_MESSAGE = "new_chat_message";

        public static final String NEW_LOCAL_ADMIN_APPROVE = "admin_approve";

        public static final int NEW_LOCAL_ADMIN_APPROVE_ID = 1256121;
    }

    public class Local {
        public static final String ORDER_REQUEST = "order_request";
        public static final String ACCEPT_MODIFICATION = "accept_modification";
        public static final String DECLINE_MODIFCATION = "decline_modification";

        public static final String NOTIFY_LOCATION = "notify_location";
        public static final String RECEIPT_OBJECTION = "receipt_objection";
        public static final String ACCEPT_OBJECTION = "accept_objection";
        public static final String PAYMENT_MADE = "payment_made";
        public static final String ACCEPT_ORG_REQUEST = "org_req_accept";
        public static final String REJECT_ORG_REQUEST = "org_req_reject";
        public static final String ORG_REQUEST = "organisation_request";

        public static final String RATE_TO_LOCAL = "rate_to_local";

        public static final String ORG_SET_PAYMENT_RULES = "organisation_set_payment_rule";
        public static final String ORG_DELETE_LOCAL = "organisation_deleted_local";
        public static final String ORG_EXIT_LOCAL = "exit_organisation";
        public static final String ORG_DELETE = "organisation_deleted";
        public static final String ORG_ADMIN_ACCEPTED = "admin_accept_org";


        public static final String NEW_CHAT_MESSAGE = "new_chat_message";

        public static final String EXP_ADMIN_ACCEPTED = "experience_approve";
        public static final String EXP_ADMIN_REJECT = "experience_rejected";

    }

    public class OrderDetails {
        public static final String ESCROW = "Escrow";
        public static final String PAID = "Paid";
        public static final String CANCEL = "Cancelled";
        public static final String REFUND = "Refund";

        public static final String PENDING = "pending";
        public static final String ACCEPTED = "accepted";
        public static final String REJECTED = "rejected";
        public static final String COMPLETED = "completed";
        public static final String DECLINE = "decline";
        public static final String CANCELED = "canceled";


    }

    public class FireStore {
        public static final String TAB_NAME_CHAT = "chats";
        public static final String TAB_NAME_RECENT_CHAT = "recent_chat";

        public static final String TAB_NAME_USER = "user";

        public static final String FIELD_SENDER_ID = "sender_id";
        public static final String FIELD_SENDER_NAME = "sender_name";
        public static final String FIELD_SENDER_IMAGE_URL = "sender_image_url";
        public static final String FIELD_SENDER_DEVICE_TYPE = "sender_device_type";
        public static final String FIELD_SENDER_DEVICE_TOKEN = "sender_device_token";

        public static final String FIELD_RECEIVER_ID = "receiver_id";
        public static final String FIELD_RECEIVER_NAME = "receiver_name";
        public static final String FIELD_RECEIVER_IMAGE_URL = "receiver_image_url";
        public static final String FIELD_RECEIVER_DEVICE_TYPE = "receiver_device_type";
        public static final String FIELD_RECEIVER_DEVICE_TOKEN = "receiver_device_token";

        public static final String FIELD_MESSAGE = "message";
        public static final String FIELD_MESSAGE_TYPE = "message_type";
        public static final String FIELD_CHAT_TIME = "chat_time";
        public static final String FIELD_CHAT_READ = "chat_read";
        public static final String FIELD_CHAT_TIME_UTC = "chat_time_utc";

        public static final String FIELD_CHAT_COUNT = "chat_count";

        public static final String FIELD_UNIQ_ID = "uniq_id";

        public static final String FIELD_IS_ONLINE = "is_Online";

        public static final String FIELD_CHAT_LOCAL_ID = "local_id";
        public static final String FIELD_CHAT_ORDER_ID = "order_id";
        public static final String FIELD_CHAT_VISITOR_ID = "visitor_id";

    }

}
