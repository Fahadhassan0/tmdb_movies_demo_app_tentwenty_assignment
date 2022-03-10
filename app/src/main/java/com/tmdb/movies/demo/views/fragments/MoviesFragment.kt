package com.tmdb.movies.demo.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.tmdb.movies.demo.R
import com.tmdb.movies.demo.base.BaseFragment
import com.tmdb.movies.demo.data.MovieItem
import com.tmdb.movies.demo.databinding.FragmentMoviesBinding
import com.tmdb.movies.demo.listeners.MoviesAdapterListener
import com.tmdb.movies.demo.viewmodels.MoviesViewModel
import com.tmdb.movies.demo.views.adapters.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : BaseFragment() {

    @Inject
    lateinit var adapter: MoviesAdapter
    private val moviesViewModel: MoviesViewModel by activityViewModels()
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    // This property is only valid between onCreateView and
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun clicks() {
        binding.ivSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    override fun initRef() {
        //videos adapter
        initAdapter()

        //refresh layout
        binding.swipeRefreshLayout.isRefreshing = true

        //get movies
        getMovies()

        //refreshing movies
        binding.swipeRefreshLayout.setOnRefreshListener {
            getMovies()
        }

    }

    private fun getMovies() {
        moviesViewModel.getUpcomingMovies().observe(viewLifecycleOwner) {
            binding.swipeRefreshLayout.isRefreshing = false
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