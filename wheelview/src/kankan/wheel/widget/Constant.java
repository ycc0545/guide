package kankan.wheel.widget;

public class Constant {
    public static final int REQ_REGISTER = 0x0001;
    public static final int REQ_LOGIN = 0x0002;

    public static final int RESULT_LOGIN = 0x0003;

    public static final String KEY_WEBTYPE = "web_type";
    public static final String KEY_WEBURL = "web_url";
    public static final String KEY_ACTION_BAR_COLOR = "ActionBarColor";
    public static final String KEY_SHOW_SHARE = "ShowShare";
    public static final String KEY_EVENT_PAGE_TYPE = "EventPageType";

    public static final int WEBTYPE_REGISTER = 0;
    public static final int WEBTYPE_LOGIN = 1;
    public static final int WEBTYPE_SYSTEM_BLOG = 2;
    public static final int WEBTYPE_WITH_LOCALE = 3;

    public static final String KEEP_XML = "keeper";
    public static final String KEY_KEEP_UID = "uid";
    public static final String KEY_KEEP_SECURITY = "security";
    public static final String KEY_KEEP_SYNC_TIME = "sync_time";
    public static final String KEY_KEEP_SYNC_REAL_STEP_TIME = "sync_real_step_time";
    public static final String KEY_KEEP_REALTIME_STEPS = "dynamic_realtime_steps";
    public static final String KEY_KEEP_SYNC_BRACELET_TIME = "sync_bracelet_time";
    public static final String KEY_KEEP_BRACELET_BT_NAME = "bracelet_bt_name";
    public static final String KEY_KEEP_BRACELET_MAC_ADDRESS = "bracelet_mac_address";

    public static final String KEY_KEEP_PERSON_INFO_NICKNAME = "person_info_nickname";
    public static final String KEY_KEEP_PERSON_INFO_AVATAR_URL = "person_info_avatar_url";
    public static final String KEY_KEEP_PERSON_INFO_AVATAR_PATH = "person_info_avatar_path";
    public static final String KEY_KEEP_PERSON_INFO_UID = "person_info_uid";
    public static final String KEY_KEEP_PERSON_INFO_HEIGHT = "person_info_height";
    public static final String KEY_KEEP_PERSON_INFO_WEIGHT = "person_info_weight";
    public static final String KEY_KEEP_PERSON_INFO_TARGET_WEIGHT = "person_info_target_weight";
    public static final String KEY_KEEP_PERSON_INFO_GENDER = "person_info_gender";
    public static final String KEY_KEEP_PERSON_INFO_AGE = "person_info_age";
    public static final String KEY_KEEP_PERSON_INFO_SIGNATURE = "person_info_signature";
    public static final String KEY_KEEP_PERSON_INFO_SH = "person_info_sh";
    public static final String KEY_KEEP_PERSON_INFO_LOCATION = "person_info_location";
    public static final String KEY_KEEP_PERSON_INFO_TOTAL_SPORT_DATA = "person_info_total_sport_data";
    public static final String KEY_KEEP_PERSON_INFO_NEED_SYNC_SERVER = "person_info_need_sync_server";
    public static final String KEY_KEEP_PERSON_INFO_MILI_CONFIG = "person_info_mili_config";
    public static final String KEY_KEEP_PERSON_INFO_BIRTH = "person_info_birthday";

    public static final String KEY_KEEP_DEVICE_ID = "device_id";

    public static final String KEY_KEEP_PUSH_ALIAS = "push_alias";

    public static final String KEY_MILIAO_ICON_URL = "miliao_icon_url";
    public static final String KEY_MILIAO_NICK_NAME = "miliao_nick_name";
    public static final String KEY_HAS_BINDED = "miliao_has_binded";

    public static final String KEY_KEEP_UUID = "push_uuid";

    public static final String KEY_KEEP_PARTNER_UPDATE_TIME = "partner_update_time";

    public static final int MODE_SLIENT = 0;
    public static final int MODE_WALKING = 1;
    public static final int MODE_RUNNING = 2;
    public static final int MODE_NONWEAR = 3;
    public static final int MODE_REM = 4;
    public static final int MODE_NREM = 5;
    public static final int MODE_CHARGING = 6;
    public static final int MODE_ONBED = 7;

    public static final int MODE_USER = 100;
    public static final byte INVALID_BYTE = 127;
    public static final byte BLANK_MODE = 126;
    public static final byte BLANK_ACTIVITY = 0;
    public static final byte BLANK_STEP = 0;

    public static final String WeixinAppId = "wx28e2610e92fbe111";
    // 34ef92b36ab6feff6e6e9dec480a2b8d

    public static final String MI_SUBS_DEFAULT_TOPIC = "millet";
    public static final String MI_SUBS_DEFAULT_CATEGORY = "test";

    public static final String WEIBO_APP_KEY = "2045436852";

    public static final int LOAD_DATA_COMPLETE = 0;

    public static final String KEY_KEEP_PERSON_INFO_DEVICE_ID = "device_id";
    public static final String KEY_KEEP_PERSON_INFO_ALARMS = "alarms";

    public static final int HEIGHT_MIN = 40;
    public static final int HEIGHT_MAX = 230;
    public static final int WEIGHT_MIN = 2;
    public static final int WEIGHT_MAX = 150;
    public static final int AGE_START = 0;
    public static final int AGE_END = 100;
    public static final int GOAL_MAX_STEPS = 30000;
    public static final int GOAL_MIN_STEPS = 2000;
    public static final int IN_COMING_CALL_TIME_START = 2;
    public static final int IN_COMING_CALL_TIME_END = 30;

    public static final int MIN_LATENCY = 39;
    public static final int MAX_LATENCY = 480;

    public static final int MIN_LATENCY_MIN = 39;
    public static final int MIN_LATENCY_MAX = 49;

    public static final int MAX_LATENCY_MIN = 460;
    public static final int MAX_LATENCY_MAX = 500;

    public static final int HEIGHT_DEFAULT = 170;
    public static final int WEIGHT_DEFAULT = 60;
    public static final int TEXT_LEN_MIN = 1;
    public static final int TEXT_LEN_MAX = 20;

    public static final String ACTION_SET_MAX_LATENCY = "com.xiaomi.hm.health.set_max_latency";
    public static final int SET_LATENCY_DELAY_TIMEOUT = 2 * 60 * 1000;
    public static final int FEMALE = 0;
    public static final int MALE = 1;

    public static final String ACTION_DEVICE_BIND_APPLICATION = "com.xiaomi.hm.health.ACTION_DEVICE_BIND_APPLICATION";
    public static final String ACTION_DEVICE_UNBIND_APPLICATION = "com.xiaomi.hm.health.ACTION_DEVICE_UNBIND_APPLICATION";

    public static final int PIE_CHART_MODE_NORMAL = 0;
    public static final int PIE_CHART_MODE_BATTERY_NORMAL = 1;
    public static final int PIE_CHART_MODE_BATTERY_LOADING = 2;
    public static final int PIE_CHART_MODE_WEIGHT_NORMAL = 3;
    public static final int PIE_CHART_MODE_WEIGHT_LOADING = 4;

    public static final boolean CHECK_PCB_VERSION = false;

    public static final int WHEEL_HEIGHT_LARGE = 50; // dp
    public static final int WHEEL_CENTER_TEXT_SIZE_LARGE = 16;
    public static final int WHEEL_NORMAL_TEXT_SIZE_LARGE = 15;
    public static final int WHEEL_NORMAL_LIGHT_TEXT_SIZE_LARGE = 15;

    public static final int WHEEL_CENTER_TEXT_SIZE = 11;
    public static final int WHEEL_NORMAL_TEXT_SIZE = 10;
    public static final int WHEEL_NORMAL_LIGHT_TEXT_SIZE = 9;

    public static final int DEFAULT_WHEEL_ITEM_H_ALARM = 46; // dp
    public static final int WHEEL_CENTER_TEXT_SIZE_ALARM = 8;
    public static final int WHEEL_NORMAL_TEXT_SIZE_ALARM = 7;
    public static final int WHEEL_NORMAL_LIGHT_TEXT_SIZE_ALARM = 7;

    public static final int WHEEL_CENTER_UNIT_TEXT_SIZE = 10;
    public static final int WHEEL_CENTER_UNIT_TEXT_SIZE_LARGE = 12;
    public static final float WHEEL_CENTER_UNIT_TEXT_X_OFF = 28F;
    public static final float WHEEL_CENTER_UNIT_TEXT_Y_OFF = 1.5F;
    public static final float WHEEL_CENTER_UNIT_TEXT_X_OFF_LARGE = 47F;
    public static final float WHEEL_CENTER_UNIT_TEXT_X_OFF_LARGE_1 = 25F;
    public static final float WHEEL_CENTER_UNIT_TEXT_Y_OFF_LARGE = 5.5F;
    public static final int WHEEL_CENTER_UNIT_TEXT_COLOR = R.color.content_color;
    public static final int DEFAULT_WHEEL_ITEM_H = 32; // dp
    public static final int DEFAULT_WHEEL_NORMAL_COLOR_LIGHT = 0x88999999;
    public static final int WHEEL_VALUE_GAP = 1; // Value gap, e.g. set goal:
    // 8000 = 8 * 1000;

    public static final int WHEEL_MODE_HOUR_24 = 0x9;
    public static final int WHEEL_MODE_HOUR_12 = 0x10;
    public static final int WHEEL_MODE_AM_PM = 0x11;
    public static final int WHEEL_MODE_HEAD_CLOSE = 0x12;
    public static final int WHEEL_MODE_WEIGHT_WITH_DECIMAL = 0x13;

    public static final int REQ_SET_NEXT = 0x6;
    public static final int YOUNG_AGE = 17;

    public static final String REF_DAYS = "Days";

    public static final int SYNC_DATA_DELTA = 2 * 60 * 1000;
    public static final int AVATAR_W = 320;
    public static final int AVATAR_H = 320;
    public static final String REF_SEARCH_DEV_MODE = "REF_SEARCH_DEV_MODE";
    public static final String REF_PROGRESS_INFO = "REF_PROGRESS_INFO";
    public static final String REF_PROGRESS_CANCELABLE = "REF_PROGRESS_CANCELABLE";

    public static final byte DEVICE_BATTERY_NORMAL = 0;
    public static final byte DEVICE_BATTERY_LOW = 1;
    public static final byte DEVICE_BATTERY_CHARGING = 2;
    public static final byte DEVICE_BATTERY_CHARGING_FULL = 3;
    public static final byte DEVICE_BATTERY_CHARGE_OFF = 4;

    public static final int NO_NEED_BIND = 0;
    public static final int NEED_BIND = 1;

    public static final String NOTIFICATION_KEY = "notification_enter";
    public static final int NOTIFICATION_VALUE = 1;
    public static final int LOW_BATTERY_LEVEL = 10;
    public static final String ACTION_OPEN_SETTINGS_PAGE = "ACTION_OPEN_SETTINGS_PAGE";
    public static final String ACTION_OPEN_ALARM_PAGE = "ACTION_OPEN_ALARM_PAGE";
    public static final String STR_FORMAT = "utf-8";
    public static final float CYCLE_METER = 400;
    public static final float MARATHON_KM = 42 * 1000; // 42km
    public static final int MAX_BATTERY_USED_DAYS = 30;

    public static final int RE_GOALS_NORMAL = 8000;
    public static final int RE_GOALS_HIGH = 12000;

    public static final String LOCALE_EN_PARAM = "_locale=en";
    public static final String LOCALE_CHN_PARAM = "_locale=zh_CN";
    public static final String LOCALE_TW_PARAM = "_locale=zh_TW";

    public static final long UNBIND_WATI_MS = 3 * 1000;
    public static final String REF_LUA_VERSION = "Lua_version";
    public static final int BASE_MILI_YEAR_OFFSET = 2000;
    public static final String REF_PIC_URL = "pic_url";
    public static final String SHARE_FILE_NAME = "share.jpg";
    public static final String SHARE_PREVIEW_FILE_NAME = "share_preview.jpg";
    public static final String DISLIKE = "dislike";
    public static final String REF_GAME_FAILED = "REF_GAME_FAILED";
    public static final String DEFAULT_LUA_VERSION = "20990101001";
    public static final String REF_AGREE_USER_AGREEMENT = "agree_user_agreement";
    public static final String REF_LAZY_DAYS = "ref_lazy_days";
    public static final String LAZY_DAY_SPLIT = ":";
    public static final String REF_LAZY_DAYS_ALGO_START_DATE = "ref_manual_algo_start_date";
    public static final String KEY_MANUAL_LAZY_DAY_START_DATE = "algo_start_date";
    public static final String KEY_MANUAL_LAZY_DAYS = "lazydays";
    public static final String REF_PUSH_LUA_ITEM = "push_lua_item";
    public static final String REF_PUSH_INTENT = "push_intent";

    public static final int PERSON_DEFAULT_YEAR = 1990;
    public static final int PERSON_DEFAULT_MONTH = 1;

    public static final String REF_UX_CONFIG = "ref_UX_config";
    public static final String REF_LAST_BINDED = "ref_last_binded";
    public static final String REF_LAST_WELCOME_DATE = "ref_last_welcome_date";
    public static final String REF_GOAL = "ref_goal";

    public static final String REF_TRASH_CLEANED = "ref_trash_cleaned";
    public static final String REF_TRACK_THUMB_CLEANED = "ref_track_thumb_cleaned";
    public static final String REF_SENSORHUB_SUPPORTED = "ref_sensorhub_supported";
    public static final int ONE_DAY_LONG = 24 * 60 * 60 * 1000; // a day in
    // milliseconds

    public static final float MARATHON_DISTANCE = 42.195f;
    public static final String KEY_SHARE_TOPIC = "ref_share_topic";
}
