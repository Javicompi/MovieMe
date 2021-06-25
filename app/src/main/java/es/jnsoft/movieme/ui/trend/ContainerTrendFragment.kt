package es.jnsoft.movieme.ui.trend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import es.jnsoft.movieme.R

class ContainerTrendFragment : Fragment() {

    private lateinit var pagerAdapter: ContainerTrendAdapter

    private lateinit var pager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_trend_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pagerAdapter = ContainerTrendAdapter(this)
        pager = view.findViewById(R.id.trend_viewpager)
        pager.adapter = pagerAdapter
        val tabLayout = view.findViewById<TabLayout>(R.id.trend_tablayout)
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.trend_movies)
                else -> getString(R.string.trend_tvs)
            }
        }.attach()
    }
}