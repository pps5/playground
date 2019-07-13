package io.github.pps5.kakaosampleapp.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.github.pps5.kakaosampleapp.R
import io.github.pps5.kakaosampleapp.Resource
import io.github.pps5.kakaosampleapp.databinding.FragmentHomeBinding
import io.github.pps5.kakaosampleapp.presentation.adapter.NewArrivalsAdapter
import io.github.pps5.kakaosampleapp.presentation.viewmodel.NewArrivalViewModel
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: NewArrivalViewModel by inject()
    private val adapter = NewArrivalsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false).also {
            it.newArrivalsRecycler.adapter = adapter
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }
        viewModel.newArrivals.observe(this, Observer {
            when (it.status) {
                Resource.Status.Loading -> binding.newArrivalsLoading.show()
                else -> binding.newArrivalsLoading.hide()
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}