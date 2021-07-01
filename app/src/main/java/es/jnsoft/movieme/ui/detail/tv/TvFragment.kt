package es.jnsoft.movieme.ui.detail.tv

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.databinding.FragmentTvBinding
import es.jnsoft.movieme.ui.detail.BaseDetailFragment
import es.jnsoft.movieme.utils.BindingAdapters.setIcon

@AndroidEntryPoint
class TvFragment : BaseDetailFragment() {

    private val args: TvFragmentArgs by navArgs()

    private val viewModel: TvViewModel by viewModels()

    private lateinit var binding: FragmentTvBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.tv.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    binding.tv = result.value
                }
                is Result.Failure -> {
                    showSnackBar(result.message ?: "An error occurred")
                }
            }
        })

        viewModel.isElementInDb.observe(viewLifecycleOwner, { isSaved ->
            binding.tvFab.setIcon(isSaved)
        })

        viewModel.showFab.observe(viewLifecycleOwner, { showFab ->
            if (showFab) {
                binding.tvFab.visibility = View.VISIBLE
            } else {
                binding.tvFab.visibility = View.GONE
            }
        })

        viewModel.elementId.value = args.element.movieDbId

        setupBindings()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBindings()
    }

    override fun setupBindings() {
        binding.apply {
            tvPoster.transitionName = args.element.poster
            tvBackIcon.setOnClickListener {
                findNavController().popBackStack()
            }
            tvFab.setOnClickListener {
                Log.d("TvFragment", "Fab clicked!")
                viewModel?.onFabClick()
            }
        }
    }
}