package io.github.pps5.kakaosampleapp.feature.newarrivals

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.pps5.kakaosampleapp.common.vo.Resource
import io.github.pps5.kakaosampleapp.data.entity.Entry
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

private const val PUBLISHED_DATE_TIME_FORMAT = "yyyy年M月d日"
private val formatter = DateTimeFormatter.ofPattern(PUBLISHED_DATE_TIME_FORMAT)

@BindingAdapter(value = ["bind:published"])
fun TextView.setPublished(published: ZonedDateTime) {
    this.text = formatter.format(published)
}

@BindingAdapter(value = ["bind:newArrivals"])
fun RecyclerView.setNewArrivals(newArrivals: Resource<List<Entry>>?) {
    if (this.adapter !is NewArrivalsAdapter) {
        throw IllegalStateException("${this::class.simpleName} should be set adapter of NewArrivalsAdapter")
    }
    if (newArrivals?.status != Resource.Status.Success || newArrivals.value.isNullOrEmpty()) {
        return
    }
    (this.adapter as NewArrivalsAdapter).setNewArrivals(newArrivals.value)
}