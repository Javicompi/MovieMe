package es.jnsoft.movieme.ui.trend

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import es.jnsoft.movieme.data.network.model.trend.Trend
import es.jnsoft.movieme.databinding.ListItemTrendBinding

class TrendPagingAdapter(val clickListener: TrendPagingClickListener) :
    PagingDataAdapter<Trend, TrendPagingAdapter.ViewHolder>(TrendPagingDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, clickListener) }
    }

    class ViewHolder private constructor(val binding: ListItemTrendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Trend, clickListener: TrendPagingClickListener) {
            binding.trend = item
            binding.clickListener = clickListener
            binding.listItemTrendPoster.transitionName = item.posterPath
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTrendBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class TrendPagingClickListener(val clickListener: (trend: Trend, poster: ImageView) -> Unit) {
    fun onClick(trend: Trend, poster: ImageView) = clickListener(trend, poster)
}

class TrendPagingDiffUtil : DiffUtil.ItemCallback<Trend>() {

    override fun areItemsTheSame(oldItem: Trend, newItem: Trend): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Trend, newItem: Trend): Boolean {
        return oldItem == newItem
    }
}