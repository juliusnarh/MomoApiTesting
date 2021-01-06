/*
 * Copyright (c) Universal Consulting Links Ghana 2019.
 */

package com.uclgroupgh.momoapitesting.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import okhttp3.Credentials
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

object AndroidUtils {
    internal var uploadid: String = ""

    /**
     * method to request for permissions
     *
     * @param activity
     */
    fun checkPermissions(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Dexter.withActivity(activity)
                .withPermissions(
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.WAKE_LOCK,
                    Manifest.permission.FOREGROUND_SERVICE

                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {/* ... */
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                    ) {/* ... */
                    }
                }).check()
        } else {
            Dexter.withActivity(activity)
                .withPermissions(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.WAKE_LOCK

                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {/* ... */
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                    ) {/* ... */
                    }
                }).check()
        }
    }

    fun hideKeyboard(ctx: Activity) {
        try {
            val inputManager =
                ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(ctx.currentFocus!!.windowToken, 0)
        } catch (ex: Exception) {
        }

    }


    fun savePreferenceData(activity: Activity?, key: String, value: String) {
        val prefs = activity?.getSharedPreferences(Constants.MAIN_PREFERENCE, Context.MODE_PRIVATE)
        prefs?.edit()?.putString(key, value)?.apply()
    }

    fun getPreferenceData(con: Context?, variable: String, defaultValue: String): String? {
        val prefs = con?.getSharedPreferences(Constants.MAIN_PREFERENCE, Context.MODE_PRIVATE)
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(con);
        return prefs?.getString(variable, defaultValue)
    }

    fun removePreferenceValue(activity: Activity, key: String) {
        val prefs = activity.getSharedPreferences(Constants.MAIN_PREFERENCE, Context.MODE_PRIVATE)
        prefs.edit().remove(key).apply()
    }

    fun clearPreference(con: Context): Boolean {
        return con.getSharedPreferences(Constants.MAIN_PREFERENCE, Context.MODE_PRIVATE).edit()
            .clear().commit()
    }

    fun clearBoardingPreference(activity: Activity) {
        val prefs = activity.getSharedPreferences(Constants.MAIN_PREFERENCE, Context.MODE_PRIVATE)
//        prefs.edit().remove(Constants.ADD_VEHICLE_PREF).apply()
    }

    /**
     * Generate uniqueuid random number
     *
     * @return
     */
    fun getUUID() = UUID.randomUUID().toString()


    fun whiteNotificationBar(activity: Activity, view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
            activity.window.statusBarColor = Color.WHITE
        }
    }

    fun basicAuth(username: String, password: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val auth = Credentials.basic(username, password)
            Log.e("AUTH", auth)
            auth
//            "Basic " + Base64.getEncoder().encodeToString("$username:$password".toByteArray())
        } else {
            ""
        }
    }
}