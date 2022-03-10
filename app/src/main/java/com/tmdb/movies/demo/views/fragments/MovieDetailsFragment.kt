package com.tmdb.movies.demo.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.tmdb.movies.demo.R
import com.tmdb.movies.demo.base.BaseFragment
import com.tmdb.movies.demo.data.MovieDetail
import com.tmdb.movies.demo.data.MovieVideos
import com.tmdb.movies.demo.databinding.FragmentMovieDetailsBinding
import com.tmdb.movies.demo.dialog.MovieTrailerDialog
import com.tmdb.movies.demo.utilities.toast
import com.tmdb.movies.demo.viewmodels.MoviesViewModel
import com.tmdb.movies.demo.views.adapters.GenresAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment() {

    @Inject
    lateinit var adapter: GenresAdapter
    private val moviesViewModel: MoviesViewModel by viewModels()
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailsFragmentArgs by navArgs()
    private var movieTrailer: MovieVideos? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun clicks() {
        binding.apply {
            toolbar.setNavigationOnClickListener {
                binding.root.findNavController().navigateUp()
            }

            btnWatchTrailer.setOnClickListener {
                if (!movieTrailer?.results.isNullOrEmpty()) {
                    val videoDialog = MovieTrailerDialog(movieTrailer?.results?.get(0)?.id)
                    videoDialog.show(childFragmentManager, null)
                } else {
                    toast(getString(R.string.message_no_video_found))
                }
            }
        }

    }

    override fun initRef() {

        //updating toolbar icon and text color when collapsed and expended
        binding.apply {
            appBarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { _, verticalOffset ->
                when {
                    abs(verticalOffset) == appBarLayout.totalScrollRange -> {
                        // Collapsed
                        updateToolbarUi(R.color.black)
                    }
                    verticalOffset == 0 -> {
                        // Expanded
                        updateToolbarUi(R.color.white)
                    }

                }
            })
        }


        //videos adapter
        initAdapter()
        //get movies
        getMovies()
        //get movie videos
        getMovieTrailers()
    }

    private fun getMovieTrailers() {
        moviesViewModel.getMovieVideos(args.movieId).observe(this) {
            movieTrailer = it
        }
    }

    private fun updateToolbarUi(color: Int) {
        binding.toolbar.navigationIcon?.setTint(
            ContextCompat.getColor(
                requireContext(),
                color
            )
        )
        binding.tvTitle.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                color
            )
        )

    }

    private fun getMovies() {
        moviesViewModel.getMovieById(args.movieId).observe(this) {
            handleResult(it)
        }
    }

    private fun handleResult(it: MovieDetail?) {

        binding.apply {
            movieItem = it
            adapter.submitList(it?.genres)
            executePendingBindings()
        }
    }


    private fun initAdapter() {
        binding.recyclerViewGenre.adapter = adapter
    }


    // onDestroyView
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}