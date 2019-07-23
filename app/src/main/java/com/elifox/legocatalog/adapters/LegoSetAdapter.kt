

package com.elifox.legocatalog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elifox.legocatalog.LegoSetListFragmentDirections
import com.elifox.legocatalog.data.LegoSet
import com.elifox.legocatalog.databinding.ListItemLegosetBinding

/**
 * Adapter for the [RecyclerView] in [LegoSetListFragment].
 */
class LegoSetAdapter : ListAdapter<LegoSet, LegoSetAdapter.ViewHolder>(LegoSettDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val legoSet = getItem(position)
        holder.apply {
            bind(createOnClickListener(legoSet.id), legoSet)
            itemView.tag = legoSet
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemLegosetBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(id: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = LegoSetListFragmentDirections.actionPlantListFragmentToPlantDetailFragment(id)
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