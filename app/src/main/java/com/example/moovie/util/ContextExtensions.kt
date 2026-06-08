package com.example.moovie.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent

/**
 * Traverses context wrappers to find the underlying Activity context.
 */
tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

/**
 * Safely starts an Activity by either using the underlying Activity context
 * or adding the FLAG_ACTIVITY_NEW_TASK flag if called from a non-Activity context.
 */
fun Context.startActivitySafe(intent: Intent) {
    val activity = this.findActivity()
    if (activity != null) {
        activity.startActivity(intent)
    } else {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
    }
}
