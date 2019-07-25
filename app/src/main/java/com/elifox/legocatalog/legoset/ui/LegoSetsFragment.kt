package com.elifox.legocatalog.legoset.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.elifox.legocatalog.R
import com.elifox.legocatalog.data.Result.Status.*
import com.elifox.legocatalog.databinding.FragmentLegosetsBinding
import com.elifox.legocatalog.di.InjectorUtils
import com.google.android.material.snackbar.Snackbar

class LegoSetsFragment : Fragment() {

    private val viewModel: LegoSetsViewModel by viewModels {
        InjectorUtils.providePlantListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLegosetsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = LegoSetAdapter()
        binding.recyclerView.adapter = adapter
        subscribeUi(binding, adapter)

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

    private fun subscribeUi(binding: FragmentLegosetsBinding, adapter: LegoSetAdapter) {
        viewModel.legoSets.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    result.data?.let { adapter.submitList(it) }
                }
                LOADING -> binding.progressBar.visibility = View.VISIBLE
                ERROR -> Snackbar.make(binding.root, result.message!!,
                        Snackbar.LENGTH_LONG).show()
            }
        }
    }


    // TODO
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