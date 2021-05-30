package com.shishkindenis.loginmodule.util.bindingAdapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("showIfCodeSent")
fun showIfCodeSent(view: View, codeSent: Boolean) {
    if (codeSent) {
        view.isEnabled = true
    }
}
