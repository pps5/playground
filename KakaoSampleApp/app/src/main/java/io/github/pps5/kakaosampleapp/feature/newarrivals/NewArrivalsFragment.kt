package io.github.pps5.kakaosampleapp.feature.newarrivals

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import io.github.pps5.kakaosampleapp.R
import io.github.pps5.kakaosampleapp.common.vo.Resource
import io.github.pps5.kakaosampleapp.data.entity.Entry
import io.github.pps5.kakaosampleapp.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject

class NewArrivalsFragment : Fragment(), NewArrivalsAdapter.OnClickItemListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: NewArrivalViewModel by inject()
    private val adapter = NewArrivalsAdapter(this)

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

    override fun onClick(entry: Entry) {
        val action = NewArrivalsFragmentDirections
            .actionHomeFragmentToEventDetailFragment(entry.title, entry.summary, entry.link.url)
        findNavController().navigate(action)
    }
}