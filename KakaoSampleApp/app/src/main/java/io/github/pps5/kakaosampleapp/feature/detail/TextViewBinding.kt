package io.github.pps5.kakaosampleapp.feature.detail

import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:html")
fun TextView.setHtml(html: String) {
    fun String.toHtml() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this).toString()
    }
    this.text = html.toHtml().toHtml()
}