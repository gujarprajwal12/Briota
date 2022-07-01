//
//package com.example.briota.integration_layer.utilities
//
//import android.content.Context
//import android.content.SharedPreferences
//
//internal class SharedPreferenceManager(context: Context) {
//    private val mPref: SharedPreferences
//
//    init {
//        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//    }
//
//    // for boolean value
//    fun writeBoolean(key: String, value: Boolean) {
//        mPref.edit().putBoolean(key, value).apply()
//    }
//
//    fun readBoolean(key: String, defValue: Boolean): Boolean {
//        return mPref.getBoolean(key, defValue)
//    }
//
//    // for integer value
//    fun writeInteger(key: String, value: Int) {
//        mPref.edit().putInt(key, value).apply()
//
//    }
//
//    fun readInteger(key: String, defValue: Int): Int {
//        return mPref.getInt(key, defValue)
//    }
//
//    // for String value
//    fun writeString(key: String, value: String): String {
//        mPref.edit().putString(key, value).apply()
//
//        return key
//    }
//
//    fun readString(key: String): String? {
//
//        return mPref.getString(key, null)
//    }
//
//    fun readString(key: String, defValue: String): String? {
//        return mPref.getString(key, defValue)
//    }
//
//    // for float value
//    fun writeFloat(key: String, value: Float) {
//        mPref.edit().putFloat(key, value).apply()
//    }
//
//    fun readFloat(key: String, defValue: Float): Float {
//        return mPref.getFloat(key, defValue)
//    }
//
//    // for long value
//    fun writeLong(key: String, value: Long) {
//        mPref.edit().putLong(key, value).apply()
//    }
//
//    fun readLong(key: String, defValue: Long): Long {
//        return mPref.getLong(key, defValue)
//    }
//
//    fun clear(): Boolean {
//        return mPref.edit()
//            .clear()
//            .commit()
//    }
//
//
//    companion object {
//        var KEY_LOGIN_STATUS = "login_status"
//        var KEY_USER_NAME = "userName"
//        var KEY_EMAIL = "email"
//        var ACCESS_KEY_TOKEN = "Bearer"
//        var BEARER_KEY_TOKEN = "token"
//        var Mobile_No = "phone"
//        var KEY_LOGIN_USER_ID="login_userd"
//        var KEY_PASSWORD="key_pass"
//
//        private var sInstance: SharedPreferenceManager? = null
//        val PREF_NAME = "com.Briota"
//
//        @Synchronized
//        fun initializeInstance(context: Context) {
//            if (sInstance == null) {
//                sInstance = SharedPreferenceManager(context)
//            }
//        }
//
//        val instance: SharedPreferenceManager?
//            @Synchronized get() {
//                if (sInstance == null) {
//
//                }
//                return sInstance
//            }
//
//
//    }
//}
