

package com.elifox.legocatalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.elifox.legocatalog.adapters.GardenPlantingAdapter
import com.elifox.legocatalog.databinding.FragmentGardenBinding
import com.elifox.legocatalog.utilities.InjectorUtils
import com.elifox.legocatalog.viewmodels.GardenPlantingListViewModel

class GardenFragment : Fragment() {

    private val viewModel: GardenPlantingListViewModel by viewModels {
        InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGardenBinding.inflate(inflater, container, false)
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter
        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        viewModel.gardenPlantings.observe(viewLifecycleOwner) { plantings ->
            binding.hasPlantings = !plantings.isNullOrEmpty()
        }

        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) { result ->
            if (!result.isNullOrEmpty())
                adapter.submitList(result)
        }
    }
}
