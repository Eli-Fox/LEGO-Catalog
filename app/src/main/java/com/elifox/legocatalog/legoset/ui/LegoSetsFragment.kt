package com.elifox.legocatalog.legoset.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.elifox.legocatalog.databinding.FragmentLegosetsBinding
import com.elifox.legocatalog.di.InjectorUtils



class LegoSetsFragment : Fragment() {

    private val args: LegoSetsFragmentArgs by navArgs()
    private val viewModel: LegoSetsViewModel by viewModels {
        InjectorUtils.provideLegoSetsViewModelFactory(requireContext(), args.themeId)
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
        args.themeName?.let { setToolbarTitle(it) }

        subscribeUi(binding, adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.elifox.legocatalog.R.menu.menu_filter, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            com.elifox.legocatalog.R.id.filter_zone -> {
                updateData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(binding: FragmentLegosetsBinding, adapter: LegoSetAdapter) {
        viewModel.legoSets.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

// TODO paging with database + network
/*
    private fun subscribeUi(binding: FragmentLegosetsBinding, adapter: LegoSetAdapter) {
        viewModel.legoSets.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    result.data?.let { adapter.submitList(it) }
                }
                LOADING -> binding.progressBar.visibility = View.VISIBLE
                ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root, result.message!!,
                            Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
    */

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


    private fun setToolbarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar!!.title = title
    }
}
