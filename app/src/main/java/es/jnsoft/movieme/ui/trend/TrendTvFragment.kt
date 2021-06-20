package es.jnsoft.movieme.ui.trend

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.jnsoft.movieme.NavGraphDirections
import es.jnsoft.movieme.R
import es.jnsoft.movieme.data.network.model.trend.TrendTimeWindow
import es.jnsoft.movieme.data.network.model.trend.toElement
import es.jnsoft.movieme.databinding.FragmentTvTrendBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TrendTvFragment : BaseTrendFragment() {

    private val viewModel: TrendViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTvTrendBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val columnCount = resources.getInteger(R.integer.trend_column_count)
        binding.tvsTrendRecyclerview.layoutManager = GridLayoutManager(activity, columnCount)

        val adapter = TrendPagingAdapter(TrendPagingClickListener { trend, poster ->
            Log.d("TrendTvFragment", trend.title ?: trend.id.toString())
            val extras = FragmentNavigatorExtras(poster to trend.posterPath!!)
            val action =
                NavGraphDirections.actionGlobalNavigationElement(element = trend.toElement())
            findNavController().navigate(action, extras)
        })

        binding.tvsTrendRecyclerview.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.tvList.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            adapter.loadStateFlow.collectLatest { state ->
                binding.tvsTrendRecyclerview.isVisible = adapter.itemCount >= 1
                binding.nothingTvTextview.isVisible = adapter.itemCount < 1
                binding.tvsTrendProgressbar.isVisible = state.refresh is LoadState.Loading
                if (state.refresh is LoadState.Error) {
                    showSnackBar("An error occurred while loading data")
                }
            }
        }

        viewModel.showSnackBar.observe(viewLifecycleOwner, { errorMessage ->
            showSnackBar(errorMessage)
        })

        return binding.root
    }

    override fun loadData(timeWindow: TrendTimeWindow) {
        viewModel.setTvFilter(timeWindow)
    }
}