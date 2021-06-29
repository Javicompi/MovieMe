package es.jnsoft.movieme.ui.detail.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.databinding.FragmentTvBinding
import es.jnsoft.movieme.ui.detail.BaseDetailFragment

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
            TODO("Not yet implemented")
        })

        viewModel.showFab.observe(viewLifecycleOwner, { showFab ->
            TODO("Not yet implemented")
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBindings()
        viewModel.elementId.value = args.element.movieDbId
    }

    override fun setupBindings() {
        TODO("Not yet implemented")
        binding.apply {
            elementBackdrop.transitionName = args.element.poster

        }
    }
}