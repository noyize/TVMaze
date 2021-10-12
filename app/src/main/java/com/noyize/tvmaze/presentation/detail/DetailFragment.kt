package com.noyize.tvmaze.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.noyize.tvmaze.R
import com.noyize.tvmaze.databinding.FragmentDetailBinding
import com.noyize.tvmaze.module.model.ShowDetail
import com.noyize.tvmaze.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel by viewModels<DetailViewModel>()
    private val genreAdapter by lazy { GenreAdapter() }
    private lateinit var showDetail: ShowDetail


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        setHasOptionsMenu(true)
        setUpRecyclerView()
        observeShow()
    }

    private fun setUpRecyclerView(){
        binding.genreRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            adapter = genreAdapter
        }
    }

    private fun observeShow(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.showDetailState.collect { showDetail ->
                    when (showDetail)  {
                        is Resource.Loading -> showLoading(true)
                        is Resource.Success -> setShowDetails(showDetail.value)
                        is Resource.Error -> showError(showDetail.error)
                    }
                }
            }
        }
    }

    private  fun setShowDetails(showDetail: ShowDetail){
        showLoading(false)
        this.showDetail = showDetail
        with(binding){
            coverImage.load(showDetail.imageUrl)
            genreAdapter.submitList(showDetail.genres)
            summary.text = showDetail.summary

        }
    }

    private fun showLoading(show: Boolean){
        with(binding){
            progressBar.isVisible = show
            detailLayout.isVisible = !show
        }
    }
    private fun showError(message: String){
        showLoading(false)
        Snackbar.make(binding.root,message, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionVisitWebsite){
            openOfficialWebsite()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openOfficialWebsite() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(showDetail.website))
        startActivity(intent)
    }
}