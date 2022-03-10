package com.tmdb.movies.demo.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.tmdb.movies.demo.base.BaseFragment
import com.tmdb.movies.demo.data.MovieItem
import com.tmdb.movies.demo.databinding.FragmentSearchBinding
import com.tmdb.movies.demo.listeners.MoviesAdapterListener
import com.tmdb.movies.demo.viewmodels.MoviesViewModel
import com.tmdb.movies.demo.views.adapters.MoviesSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchMoviesFragment : BaseFragment() {

    @Inject
    lateinit var adapter: MoviesSearchAdapter
    private val moviesViewModel: MoviesViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun clicks() {
        binding.ivBack.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    override fun initRef() {
        binding.searchEditText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                searchMovies(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchMovies(query)
                return false
            }
        })

        //movies adapter
        initAdapter()

    }

    //calling movies search api
    private fun searchMovies(newText: String?) {
        moviesViewModel.getSearchMovies(newText).observe(this) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)

        }
    }

    private fun initAdapter() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        adapter.setListener(object : MoviesAdapterListener {
            override fun onItemClick(item: MovieItem?) {
                val direction =
                    item?.id?.let {
                        MoviesFragmentDirections.actionHomeFragmentToMovieDetailsFragment(
                            it
                        )
                    }
                findNavController().navigate(direction!!)
            }
        })
    }


    // onDestroyView
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}