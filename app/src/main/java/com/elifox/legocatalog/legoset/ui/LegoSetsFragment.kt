package com.elifox.legocatalog.legoset.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.elifox.legocatalog.databinding.FragmentLegosetsBinding
import com.elifox.legocatalog.di.InjectorUtils
import com.elifox.legocatalog.util.ConnectivityUtil
import com.elifox.legocatalog.util.setTitle

class LegoSetsFragment : Fragment() {

    private val args: LegoSetsFragmentArgs by navArgs()
    private val viewModel: LegoSetsViewModel by viewModels {
        InjectorUtils.provideLegoSetsViewModelFactory(requireContext(),
                ConnectivityUtil.isConnected(context!!),
                if (args.themeId == -1) null else args.themeId)
    }
    private val adapter: LegoSetAdapter by lazy { LegoSetAdapter() }
    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(activity) }
    private val gridLayoutManager: GridLayoutManager by lazy { GridLayoutManager(activity, 3) }
    private lateinit var binding: FragmentLegosetsBinding

    private var isLinearLayoutManager: Boolean = true

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLegosetsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        setLayoutManager()
        binding.recyclerView.adapter = adapter
        args.themeName?.let { setTitle(it) }

        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.elifox.legocatalog.R.menu.menu_grid, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            com.elifox.legocatalog.R.id.grid -> {
                isLinearLayoutManager = !isLinearLayoutManager
                setLayoutManager()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // TODO first loading binding.progressBar.visibility = View.GONE
    private fun subscribeUi(adapter: LegoSetAdapter) {
        viewModel.legoSets.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    private fun setLayoutManager() {
        val recyclerView = binding.recyclerView

        var scrollPosition = 0
        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
        }

        recyclerView.layoutManager =
                if (isLinearLayoutManager) linearLayoutManager else gridLayoutManager
        recyclerView.scrollToPosition(scrollPosition)
    }

}
