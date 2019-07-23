package com.elifox.legocatalog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.elifox.legocatalog.adapters.LegoSetAdapter
import com.elifox.legocatalog.databinding.FragmentLegosetListBinding
import com.elifox.legocatalog.utilities.InjectorUtils
import com.elifox.legocatalog.viewmodels.LegoSetListViewModel

class LegoSetListFragment : Fragment() {

    private val viewModel: LegoSetListViewModel by viewModels {
        InjectorUtils.providePlantListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLegosetListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = LegoSetAdapter()
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_zone -> {
                updateData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(adapter: LegoSetAdapter) {
        viewModel.legoSets.observe(viewLifecycleOwner) { legoSets ->
            /**
             *  Plant may return null, but the [observe] extension function assumes it will not be null.
             *  So there will be a warning（Condition `legoSets != null` is always `true`） here.
             *  I am not sure if the database return data type should be defined as nullable, Such as `LiveData<List<Plant>?>` .
             */
            if (legoSets != null) adapter.submitList(legoSets)
        }
    }

    private fun updateData() {
        with(viewModel) {
            if (isFiltered()) {
                clearGrowZoneNumber()
            } else {
                setGrowZoneNumber(9)
            }
        }
    }
}