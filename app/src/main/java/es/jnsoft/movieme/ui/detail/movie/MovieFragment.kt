package es.jnsoft.movieme.ui.detail.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.databinding.FragmentMovieBinding
import es.jnsoft.movieme.utils.BindingAdapters.setIcon
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val args: MovieFragmentArgs by navArgs()

    private val viewModel: MovieViewModel by viewModels()

    private lateinit var binding: FragmentMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

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
            binding.elementFab.setIcon(isSaved)
        })

        viewModel.showFab.observe(viewLifecycleOwner, { showFab ->
            if (showFab) {
                binding.elementFab.visibility = View.VISIBLE
            } else {
                binding.elementFab.visibility = View.GONE
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       setupBindings()
        viewModel.elementId.value = args.element.movieDbId
    }

    private fun setupBindings() {
        binding.apply {
            element = args.element
            elementPoster.transitionName = args.element.poster
            elementBackIcon.setOnClickListener {
                findNavController().popBackStack()
            }
            elementFab.setOnClickListener {
                viewModel?.onFabClick()
            }
        }
    }

    private fun showSnackBar(error: String) {
        Snackbar.make(this.requireView(), error, Snackbar.LENGTH_LONG).show()
    }
}