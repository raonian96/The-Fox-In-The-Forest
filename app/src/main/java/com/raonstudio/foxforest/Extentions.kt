package com.raonstudio.foxforest

import android.content.Context
import android.content.Intent
import org.jetbrains.anko.toast

fun Context.startActivity(cls: Class<*>) {
    startActivity(Intent(this, cls))
}

fun Context.networkErrorToast() {
    toast(R.string.network_error_message)
}