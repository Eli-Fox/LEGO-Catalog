package com.elifox.legocatalog.legoset.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elifox.legocatalog.databinding.ListItemLegosetBinding
import com.elifox.legocatalog.legoset.data.LegoSet

/**
 * Adapter for the [RecyclerView] in [LegoSetsFragment].
 */
class LegoSetAdapter : PagedListAdapter<LegoSet, LegoSetAdapter.ViewHolder>(LegoSettDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val legoSet = getItem(position)
        legoSet?.let {
            holder.apply {
                bind(createOnClickListener(legoSet.id), legoSet)
                itemView.tag = legoSet
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemLegosetBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(id: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = LegoSetsFragmentDirections.actionPlantListFragmentToPlantDetailFragment(id)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
            private val binding: ListItemLegosetBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: LegoSet) {
            binding.apply {
                clickListener = listener
                legoSet = item
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