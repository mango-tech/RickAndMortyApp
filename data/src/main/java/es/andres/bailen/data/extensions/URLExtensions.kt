package es.andres.bailen.data.extensions

import android.net.Uri
import java.net.URL

fun String.getQueryParameter(paramName: String): Int? {
    return try {
        Uri.parse(this).getQueryParameter(paramName)?.toInt()
    }catch (e: Exception) {
        e.printStackTrace()
        null
    }
}