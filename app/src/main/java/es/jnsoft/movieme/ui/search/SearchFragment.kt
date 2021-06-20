package es.jnsoft.movieme.ui.search

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import es.jnsoft.movieme.R
import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.data.network.model.search.toElement
import es.jnsoft.movieme.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        val columnCount = resources.getInteger(R.integer.search_column_count)
        binding.searchRecyclerview.layoutManager = GridLayoutManager(activity, columnCount)

        val adapter = SearchAdapter(SearchClickListener { search, poster ->
            Log.d("SearchFragment", "Id: ${search.id}")
            val extras = FragmentNavigatorExtras(poster to search.posterPath!!)
            val action = SearchFragmentDirections.actionNavigationSearchToElementFragment(
                element = search.toElement()
            )
            findNavController().navigate(action, extras)
        })

        binding.searchRecyclerview.adapter = adapter

        viewModel.searchList.observe(viewLifecycleOwner, { searchResult ->
            if (searchResult is Result.Success) {
                binding.nothingSearchTextview.isVisible = searchResult.value.isEmpty()
                binding.searchRecyclerview.isVisible = searchResult.value.isNotEmpty()
                adapter.submitList(searchResult.value)
            } else {
                val message = (searchResult as Result.Failure).message ?: "Unknown error"
                showSnackBar(message)
            }
        })

        return binding.root
    }

    private fun showSnackBar(errorMessage: String) {
        val bottomNav =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        Snackbar.make(bottomNav, errorMessage, Snackbar.LENGTH_LONG).apply {
            anchorView = bottomNav
        }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        setTextListener(searchView)
    }

    private fun setTextListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isNotEmpty() == true && newText.length >= 3) viewModel.search(newText)
                return true
            }
        })
    }
}