package com.playlistappkotlin.ext


class Constants {
    companion object {
        const val TIMBER_CRASH_LINE_POSITION = 5

        // DATABASE
        const val DATABASE_VERSION = 1
        const val DATABASE_FILE_NAME = "playlistapp.sqlite "

        const val FORMAT_WEB_SERVICE = "json"

        // ENDPOINT
        const val ENDPOINT_API_BASE = "?api_key={api_key}&format={format}"
        const val ENDPOINT_TRACK_LIST = "&method=geo.gettoptracks"

        // REQUEST NUMBERS
        private const val CHANGE_IMAGE = 0
        const val REQUEST_SETTINGS_ADD_NEW_RECIPIENT = CHANGE_IMAGE + 1
        const val REQUEST_DECELERATE_PRICE = CHANGE_IMAGE + 2
        const val REQUEST_CODE_NOTIFICATION = CHANGE_IMAGE + 3

        const val GOOGLE_APIS_PROJECT_NUMBER = "629410927983"

        const val PUSH_TEXT = "text"
        const val ORDER_DETAIL_TYPE_DECLARE = "declarate"


        const val DEFAULT_FONT = "fonts/arial.ttf"
        const val CUSTOM_ATTR_SCHEMAS = "http://schemas.android.com/apk/res-auto"

        val SET_NOTIFICATION = "com.playlistapp.SET_NOTIFICATION"

        val PREF_NAME = "AppPreferences"

        val TIMEOUT_RETRY_INITIALIZING_NETWORK_LISTENER = 500L // 0.5s

        val DEFAULT_TRACK_LIMIT_COUNT = 10
        val DEFAULT_TRACK_COUNTRY = "Latvia"

        // MAIN
        val EXTRA_FRAGMENT_POSITION = "extra_fragment_position"
        val EXTRA_MENU_ITEM_ID = "extra_menu_item_id"

        // WEB
        val EXTRA_WEB_URL = "extra_web_url"
        val EXTRA_WEB_TITLE = "extra_web_title"

        // PUSH NOTIFICATION
        val EXTRA_PUSH_TRACKING = "extra_push_tracking"
        val EXTRA_PUSH_COUNTRY = "extra_push_country"
        val EXTRA_PUSH_STATUS = "extra_push_status"
        val EXTRA_PUSH_TYPE = "extra_push_type"

        // ARGUMENTS
        val ARGUMENT_COUNTRY_TYPE = "argument_country_type"
        val ARGUMENT_ORDER_ITEM_TYPE = "argument_order_item_type"
        val ARGUMENT_TRACKING_ID = "argument_tracking_id"

        // ORDER
        val EXTRA_ORDER_ITEM = "extra_order_item"
        val EXTRA_ORDER_TITLE = "extra_order_title"
        val EXTRA_ORDER_ID = "extra_order_id"

        // SETTINGS
        val ARGUMENT_RECIPIENT = "argument_recipient"

        val EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    }
}
