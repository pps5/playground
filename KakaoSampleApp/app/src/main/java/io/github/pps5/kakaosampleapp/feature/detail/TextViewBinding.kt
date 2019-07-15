package io.github.pps5.kakaosampleapp.feature.detail

import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.github.pps5.kakaosampleapp.common.util.toHtmlSpanned

@BindingAdapter("bind:html")
fun TextView.setHtml(html: String) {
    this.text = html.toHtmlSpanned()
}