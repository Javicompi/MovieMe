package es.jnsoft.movieme.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.jnsoft.movieme.data.network.model.search.Search
import es.jnsoft.movieme.databinding.ListItemSearchBinding

class SearchAdapter(val clickListener: SearchClickListener) :
    ListAdapter<Search, SearchAdapter.ViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Search, clickListener: SearchClickListener) {
            binding.search = item
            binding.clickListener = clickListener
            binding.listItemSearchPoster.transitionName = item.posterPath
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSearchBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class SearchClickListener(val clickListener: (search: Search, poster: ImageView) -> Unit) {
    fun onClick(search: Search, poster: ImageView) = clickListener(search, poster)
}

class SearchDiffCallback : DiffUtil.ItemCallback<Search>() {
    override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
        return oldItem == newItem
    }
}