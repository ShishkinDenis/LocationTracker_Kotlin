package com.shishkindenis.loginmodule.util.bindingAdapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("progressBarVisibility")
fun progressBarVisibility(view: View, progressBarIsShown: Boolean) {
    if (progressBarIsShown) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}