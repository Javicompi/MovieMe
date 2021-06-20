package es.jnsoft.movieme.ui.trend

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import es.jnsoft.movieme.R
import es.jnsoft.movieme.data.network.model.trend.TrendTimeWindow

abstract class BaseTrendFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        loadData(TrendTimeWindow.DAY)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.trend_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter_trend_day -> {
                loadData(TrendTimeWindow.DAY)
                true
            }
            R.id.action_filter_trend_week -> {
                loadData(TrendTimeWindow.WEEK)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    abstract fun loadData(timeWindow: TrendTimeWindow)

    fun showSnackBar(errorMessage: String) {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        Snackbar.make(bottomNav, errorMessage, Snackbar.LENGTH_LONG).apply {
            anchorView = bottomNav
        }.show()
    }
}