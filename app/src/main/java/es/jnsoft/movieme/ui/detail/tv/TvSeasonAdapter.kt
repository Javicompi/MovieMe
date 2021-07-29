package es.jnsoft.movieme.ui.detail.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.jnsoft.movieme.data.network.model.tv.Tv
import es.jnsoft.movieme.databinding.ListItemSeassonBinding

class TvSeasonAdapter :
    ListAdapter<Tv.Season, TvSeasonAdapter.ViewHolder>(TvSeasonDiffUtilCallBack()) {

    private var expandedPosition = RecyclerView.NO_POSITION
    private var prevExpandedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isExpanded = position == expandedPosition
        holder.binding.listItemSeassonDetails.visibility = if (isExpanded) View.VISIBLE else View.GONE
        if (isExpanded) prevExpandedPosition = position
        holder.itemView.setOnClickListener {
            expandedPosition = if (isExpanded) RecyclerView.NO_POSITION else position
            notifyItemChanged(prevExpandedPosition)
            notifyItemChanged(expandedPosition)
        }
        holder.bind(getItem(position))
    }

    //TODO yet to implement the clickListener
    class ViewHolder private constructor(val binding: ListItemSeassonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Tv.Season) {
            binding.seasson = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSeassonBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class TvSeasonDiffUtilCallBack : DiffUtil.ItemCallback<Tv.Season>() {

    override fun areItemsTheSame(oldItem: Tv.Season, newItem: Tv.Season): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Tv.Season, newItem: Tv.Season): Boolean {
        return oldItem == newItem
    }
}