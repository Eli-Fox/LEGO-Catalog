package com.elifox.legocatalog.legoset.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elifox.legocatalog.R
import com.elifox.legocatalog.databinding.FragmentLegosetsBinding
import com.elifox.legocatalog.di.InjectorUtils
import com.elifox.legocatalog.ui.GridSpacingItemDecoration
import com.elifox.legocatalog.ui.VerticalItemDecoration
import com.elifox.legocatalog.ui.setTitle
import com.elifox.legocatalog.util.ConnectivityUtil

class LegoSetsFragment : Fragment() {

    private val args: LegoSetsFragmentArgs by navArgs()
    private val viewModel: LegoSetsViewModel by viewModels {
        InjectorUtils.provideLegoSetsViewModelFactory(requireContext(),
                ConnectivityUtil.isConnected(context!!),
                if (args.themeId == -1) null else args.themeId)
    }
    private val adapter: LegoSetAdapter by lazy { LegoSetAdapter() }

    private val linearLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(activity) }
    private val gridLayoutManager: GridLayoutManager by lazy { GridLayoutManager(activity, SPAN_COUNT) }

    private val linearDecoration: RecyclerView.ItemDecoration by lazy { VerticalItemDecoration(
            resources.getDimension(R.dimen.margin_normal).toInt()) }
    private val gridDecoration: RecyclerView.ItemDecoration by lazy { GridSpacingItemDecoration(
            SPAN_COUNT, resources.getDimension(R.dimen.margin_grid).toInt()) }
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
        inflater.inflate(R.menu.menu_grid, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.grid -> {
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

        if (isLinearLayoutManager) {
            recyclerView.removeItemDecoration(gridDecoration)
            recyclerView.addItemDecoration(linearDecoration)
            recyclerView.layoutManager = linearLayoutManager
        } else {
            recyclerView.removeItemDecoration(linearDecoration)
            recyclerView.addItemDecoration(gridDecoration)
            recyclerView.layoutManager = gridLayoutManager
        }

        recyclerView.scrollToPosition(scrollPosition)
    }

    companion object {
        const val SPAN_COUNT = 3
    }

}
