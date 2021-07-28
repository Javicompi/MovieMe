package es.jnsoft.movieme.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import es.jnsoft.movieme.R
import es.jnsoft.movieme.data.network.model.trend.TrendMediaType
import es.jnsoft.movieme.databinding.FragmentFavouritesBinding

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val viewModel: FavouritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavouritesBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val columnCount = resources.getInteger(R.integer.trend_column_count)
        binding.favouritesRecyclerview.layoutManager = GridLayoutManager(activity, columnCount)
        val adapter = FavouritesAdapter(FavouriteClickListener {element, poster ->
            val extras = FragmentNavigatorExtras(poster to element.poster)
            val action = if (element.mediaType == TrendMediaType.MOVIE.value) {
                FavouritesFragmentDirections.actionFragmentFavouritesToFragmentMovie(
                    element = element
                )
            } else {
                FavouritesFragmentDirections.actionFragmentFavouritesToFragmentTv(
                    element = element
                )
            }
            findNavController().navigate(action, extras)
        })

        binding.favouritesRecyclerview.adapter = adapter

        viewModel.favourites.observe(viewLifecycleOwner, { favourites ->
            adapter.submitList(favourites)
            binding.favouritesRecyclerview.isVisible = favourites.isNotEmpty()
            binding.nothingFavouritesTextview.isVisible = favourites.isEmpty()
        })

        viewModel.showSnackBar.observe(viewLifecycleOwner, { errorMessage ->
            Snackbar.make(this.requireView(), errorMessage, Snackbar.LENGTH_LONG).show()
        })

        return binding.root
    }
}