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
import es.jnsoft.movieme.R
import es.jnsoft.movieme.data.network.model.trend.TrendTimeWindow
import es.jnsoft.movieme.data.network.model.trend.toElement
import es.jnsoft.movieme.databinding.FragmentMovieTrendBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TrendMovieFragment : BaseTrendFragment() {

    private val viewModel: TrendViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieTrendBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val columnCount = resources.getInteger(R.integer.trend_column_count)
        binding.moviesTrendRecyclerview.layoutManager = GridLayoutManager(activity, columnCount)

        val adapter = TrendPagingAdapter(TrendPagingClickListener { trend, poster ->
            val extras = FragmentNavigatorExtras(poster to trend.posterPath!!)
            val action = ContainerTrendFragmentDirections.actionFragmentTrendToFragmentMovie(
                element = trend.toElement()
            )
            findNavController().navigate(action, extras)
        })

        binding.moviesTrendRecyclerview.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.movieList.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            adapter.loadStateFlow.collectLatest { state ->
                binding.moviesTrendRecyclerview.isVisible = adapter.itemCount >= 1
                binding.nothingMovieTextview.isVisible = adapter.itemCount < 1
                binding.moviesTrendProgressbar.isVisible = state.refresh is LoadState.Loading
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
        viewModel.setMovieFilter(timeWindow)
    }
}