package com.example.xardcalamityfiles.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object UriUtils {
    fun takePersistableUriPermission(context: Context, uri: Uri) {
        try {
            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri, takeFlags)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}
