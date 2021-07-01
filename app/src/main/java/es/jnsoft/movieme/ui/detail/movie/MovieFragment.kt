package es.jnsoft.movieme.ui.detail.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.databinding.FragmentMovieBinding
import es.jnsoft.movieme.ui.detail.BaseDetailFragment
import es.jnsoft.movieme.utils.BindingAdapters.setIcon

@AndroidEntryPoint
class MovieFragment : BaseDetailFragment() {

    private val args: MovieFragmentArgs by navArgs()

    private val viewModel: MovieViewModel by viewModels()

    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater)

        setupBindings()

        viewModel.movie.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    binding.movie = result.value
                }
                is Result.Failure -> {
                    showSnackBar(result.message ?: "An error occurred")
                }
            }
        })

        viewModel.isElementInDb.observe(viewLifecycleOwner, { isSaved ->
            binding.movieFab.setIcon(isSaved)
        })

        viewModel.showFab.observe(viewLifecycleOwner, { showFab ->
            if (showFab) {
                binding.movieFab.visibility = View.VISIBLE
            } else {
                binding.movieFab.visibility = View.GONE
            }
        })

        viewModel.elementId.value = args.element.movieDbId

        return binding.root
    }

    override fun setupBindings() {
        binding.apply {
            lifecycleOwner = this@MovieFragment
            viewModel = viewModel
            moviePoster.transitionName = args.element.poster
            movieBackIcon.setOnClickListener {
                findNavController().popBackStack()
            }
            movieFab.setOnClickListener {
                viewModel?.onFabClick()
            }
        }
    }
}