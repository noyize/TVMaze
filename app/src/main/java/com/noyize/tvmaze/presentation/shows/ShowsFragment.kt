package com.noyize.tvmaze.presentation.shows

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.noyize.tvmaze.R
import com.noyize.tvmaze.databinding.FragmentShowsBinding
import com.noyize.tvmaze.module.model.Show
import com.noyize.tvmaze.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ShowsFragment : Fragment(R.layout.fragment_shows), ShowAdapter.OnClick {

    private lateinit var binding: FragmentShowsBinding
    private val viewModel by viewModels<ShowsViewModel>()
    private val showAdapter by lazy { ShowAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShowsBinding.bind(view)

        setUpRecyclerView()
        observeShows()
    }

    private fun setUpRecyclerView() {
        with(binding.recyclerView) {
            adapter = showAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeShows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.shows.collect { shows ->
                    when (shows) {
                        is Resource.Loading -> showLoading(true)
                        is Resource.Success -> setShows(shows.value)
                        is Resource.Error -> showError(shows.error)
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        with(binding) {
            progressBar.isVisible = show
            recyclerView.isVisible = !show
        }
    }

    private fun setShows(shows: List<Show>) {
        showLoading(false)
        showAdapter.submitList(shows)
    }

    private fun showError(message: String) {
        showLoading(false)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun onShowClick(show: Show) {
        findNavController().navigate(
            ShowsFragmentDirections.actionShowsFragmentToDetailFragment(
                id = show.id,
                name = show.name
            )
        )
    }
}