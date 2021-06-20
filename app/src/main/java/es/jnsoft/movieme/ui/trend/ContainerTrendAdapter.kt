package es.jnsoft.movieme.ui.trend

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.jnsoft.movieme.data.network.model.trend.TrendMediaType

class ContainerTrendAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(
        TrendMovieFragment(),
        TrendTvFragment(),
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        val fragment = fragments[position]
        fragment.arguments = Bundle().apply {
            when(position) {
                0 -> putSerializable("trendFilter", TrendMediaType.MOVIE)
                else -> putSerializable("trendFilter", TrendMediaType.TV)
            }
        }
        return fragment
    }
}