package com.project.lastsubmission.ui.content.favorite.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.project.lastsubmission.adapter.MovieAdapter
import com.project.lastsubmission.databinding.FragmentFavoriteMovieBinding
import com.project.lastsubmission.ui.content.favorite.FavoriteViewModel
import com.project.lastsubmission.ui.detail.DetailActivity
import com.project.lastsubmission.utils.*
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FavoriteMovieFragment : DaggerFragment() {

    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding as FragmentFavoriteMovieBinding

    private lateinit var viewModel: FavoriteViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // init
        viewModel = ViewModelProvider(
            activity as FragmentActivity,
            factory
        )[FavoriteViewModel::class.java]

        setUpRecyclerView()

        getFavoriteMovies()
    }

    private fun setUpRecyclerView() {
        binding.apply {
            rvFavoriteMovie.adapter = MovieAdapter().apply {
                onClick {
                    Intent(
                        activity as FragmentActivity,
                        DetailActivity::class.java
                    ).also { intent ->
                        intent.putExtra(DetailActivity.EXTRA_ID, it.movieId)
                        intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TYPES[0])
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun getFavoriteMovies() {
        binding.apply {
            viewModel.getFavoriteMovie().observe(viewLifecycleOwner) {
                if (it != null) {
                    rvFavoriteMovie.adapter?.let { adapter ->
                        when (adapter) {
                            is MovieAdapter -> {
                                if (it.isNullOrEmpty()) {
                                    rvFavoriteMovie.gone()
                                    enableEmptyStateFavoriteMovies()
                                } else {
                                    rvFavoriteMovie.visible()
                                    disableEmptyStateFavoriteMovies()
                                    adapter.submitList(it)
                                    adapter.notifyDataSetChanged()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun enableEmptyStateFavoriteMovies() {
        binding.apply {
            showEmptyFavorite(imgEmptyState, titleEmptyState, descEmptyState)
        }
    }

    private fun disableEmptyStateFavoriteMovies() {
        binding.apply {
            hideEmptyFavorite(imgEmptyState, titleEmptyState, descEmptyState)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

