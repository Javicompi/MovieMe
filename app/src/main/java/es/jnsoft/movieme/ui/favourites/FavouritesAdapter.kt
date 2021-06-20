package es.jnsoft.movieme.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.jnsoft.movieme.data.local.element.Element
import es.jnsoft.movieme.databinding.ListItemFavouriteBinding

class FavouritesAdapter(val clickListener: FavouriteClickListener) :
    ListAdapter<Element, FavouritesAdapter.ViewHolder>(ElementDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemFavouriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Element, clickListener: FavouriteClickListener) {
            binding.element = item
            binding.clickListener = clickListener
            binding.listItemFavouritePoster.transitionName = item.poster
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemFavouriteBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class FavouriteClickListener(val clickListener: (trend: Element, poster: ImageView) -> Unit) {
    fun onClick(trend: Element, poster: ImageView) = clickListener(trend, poster)
}

class ElementDiffCallback : DiffUtil.ItemCallback<Element>() {

    override fun areItemsTheSame(oldItem: Element, newItem: Element): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Element, newItem: Element): Boolean {
        return oldItem == newItem
    }
}