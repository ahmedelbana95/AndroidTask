package com.example.task

import android.content.Context
import android.content.SharedPreferences

/**
 * created by ahmed elbana 10/2/2018
 */


class SharedPrefManager(internal var mContext: Context) {

    private var mSharedPreferences: SharedPreferences = mContext.getSharedPreferences(
        Constant.SharedPrefKey.SHARED_PREF_NAME,
        Context.MODE_PRIVATE
    )

    private var mEditor: SharedPreferences.Editor

    init {
        mEditor = mSharedPreferences.edit()
    }

    var input: String
        get() {
            return mSharedPreferences.getString(Constant.SharedPrefKey.INPUT, "")!!
        }
        set(input) {
            mEditor.putString(Constant.SharedPrefKey.INPUT, input)
            mEditor.apply()
        }

}
