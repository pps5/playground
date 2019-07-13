package io.github.pps5.kakaosampleapp.presentation.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.pps5.kakaosampleapp.Resource
import io.github.pps5.kakaosampleapp.data.entity.Entry
import io.github.pps5.kakaosampleapp.presentation.adapter.NewArrivalsAdapter

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