package io.github.pps5.kakaosampleapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.pps5.kakaosampleapp.data.entity.Entry
import io.github.pps5.kakaosampleapp.databinding.NewArrivalItemBinding

class NewArrivalsAdapter : RecyclerView.Adapter<NewArrivalsAdapter.ViewHolder>() {

    private val newArrivals = mutableListOf<Entry>()

    fun setNewArrivals(list: List<Entry>) {
        newArrivals.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewArrivalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = newArrivals.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.entry = newArrivals[position]
    }

    class ViewHolder(val binding: NewArrivalItemBinding) : RecyclerView.ViewHolder(binding.root)
}

