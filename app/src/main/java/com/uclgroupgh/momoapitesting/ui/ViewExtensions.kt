package com.uclgroupgh.momoapitesting.ui

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.balysv.materialripple.MaterialRippleLayout
import com.uclgroupgh.momoapitesting.R

fun Fragment.setTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar!!.title = title
}

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}

fun SwipeRefreshLayout.hide(){
    isRefreshing = false
}

fun SwipeRefreshLayout.show(){
    isRefreshing = true
}

fun AppCompatButton.enable() {
    isEnabled = true
    setTextColor(ContextCompat.getColor(context, R.color.white))
}
fun AppCompatButton.disable() {
    isEnabled = false
    setTextColor(ContextCompat.getColor(context, R.color.primaryTextColor))
}

fun LinearLayout.hide() {
    visibility = View.GONE
}