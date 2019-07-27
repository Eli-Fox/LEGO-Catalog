package com.elifox.legocatalog.legoset.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elifox.legocatalog.databinding.ListItemLegosetBinding
import com.elifox.legocatalog.legoset.data.LegoSet

/**
 * Adapter for the [RecyclerView] in [LegoSetsFragment].
 */
class LegoSetAdapter : PagedListAdapter<LegoSet, LegoSetAdapter.ViewHolder>(LegoSettDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val legoSet = getItem(position)
        legoSet?.let {
            holder.apply {
                bind(createOnClickListener(legoSet.id), legoSet, isGridLayoutManager())
                itemView.tag = legoSet
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemLegosetBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private fun createOnClickListener(id: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = LegoSetsFragmentDirections.actionToLegosetDetailFragment(id)
            it.findNavController().navigate(direction)
        }
    }

    private fun isGridLayoutManager() = recyclerView.layoutManager is GridLayoutManager

    class ViewHolder(private val binding: ListItemLegosetBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: LegoSet,
                 isGridLayoutManager: Boolean) {
            binding.apply {
                clickListener = listener
                legoSet = item
                title.visibility = if (isGridLayoutManager) View.GONE else View.VISIBLE
                executePendingBindings()
            }
        }
    }
}

private class LegoSettDiffCallback : DiffUtil.ItemCallback<LegoSet>() {

    override fun areItemsTheSame(oldItem: LegoSet, newItem: LegoSet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LegoSet, newItem: LegoSet): Boolean {
        return oldItem == newItem
    }
}