package com.elifox.legocatalog.legoset.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.elifox.legocatalog.data.PagingResult.Status.END_REACHED
import com.elifox.legocatalog.data.PagingResult.Status.ERROR
import com.elifox.legocatalog.databinding.FragmentLegosetsBinding
import com.elifox.legocatalog.di.InjectorUtils
import com.google.android.material.snackbar.Snackbar
import com.paginate.Paginate

class LegoSetsFragment : Fragment(), Paginate.Callbacks {

    private val args: LegoSetsFragmentArgs by navArgs()
    private val viewModel: LegoSetsViewModel by viewModels {
        InjectorUtils.provideLegoSetsViewModelFactory(requireContext(),
                if (args.themeId == -1) null else args.themeId)
    }
    private val adapter: LegoSetAdapter by lazy { LegoSetAdapter() }
    private lateinit var binding: FragmentLegosetsBinding

    /**
     * Pagination fields
     */
    private var page = 0
    private var loading = false
    private var hasLoadedAllItems = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLegosetsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.recyclerView.adapter = adapter
        args.themeName?.let { setToolbarTitle(it) }

        // TODO
        //Paginate.with(binding.recyclerView, this).build()
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onLoadMore() {
        if (loading) return
        loading = true
        viewModel.fetchPagedSets(++page).observe(viewLifecycleOwner) { result ->
            loading = false

            if (result.status == END_REACHED) hasLoadedAllItems = true
            if (result.status == ERROR) Snackbar.make(binding.root, result.message!!,
                    Snackbar.LENGTH_LONG).show()
        }
    }

    override fun isLoading(): Boolean = loading
    override fun hasLoadedAllItems(): Boolean = hasLoadedAllItems

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.elifox.legocatalog.R.menu.menu_filter, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            com.elifox.legocatalog.R.id.filter_zone -> {
                // TODO
                //updateData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // TODO first loading binding.progressBar.visibility = View.GONE
    private fun subscribeUi(adapter: LegoSetAdapter) {
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

    private fun setToolbarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar!!.title = title
    }
}
