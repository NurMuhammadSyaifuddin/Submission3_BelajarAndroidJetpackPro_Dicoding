package com.project.lastsubmission.ui.content.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.project.lastsubmission.adapter.TvShowAdapter
import com.project.lastsubmission.databinding.FragmentFavoriteTvShowBinding
import com.project.lastsubmission.ui.content.favorite.FavoriteViewModel
import com.project.lastsubmission.ui.detail.DetailActivity
import com.project.lastsubmission.utils.*
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FavoriteTvShowFragment : DaggerFragment() {

    private var _binding: FragmentFavoriteTvShowBinding? = null
    private val binding get() = _binding as FragmentFavoriteTvShowBinding

    private lateinit var viewModel: FavoriteViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteTvShowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // init
        viewModel =
            ViewModelProvider(activity as FragmentActivity, factory)[FavoriteViewModel::class.java]

        setUpRecycler()

        getFavoriteTvShow()
    }

    private fun getFavoriteTvShow() {
        binding.apply {
            viewModel.getFavoriteTvShow().observe(viewLifecycleOwner) {
                if (it != null) {
                    rvFavoriteTvshow.adapter?.let { adapter ->
                        when (adapter) {
                            is TvShowAdapter -> {
                                if (it.isNullOrEmpty()) {
                                    rvFavoriteTvshow.gone()
                                    enableEmptyStateFavoriteMovies()
                                } else {
                                    rvFavoriteTvshow.visible()
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

    private fun setUpRecycler() {
        binding.apply {
            rvFavoriteTvshow.adapter = TvShowAdapter().apply {
                onClick {
                    Intent(
                        activity as FragmentActivity,
                        DetailActivity::class.java
                    ).also { intent ->
                        intent.putExtra(DetailActivity.EXTRA_ID, it.tvShowId)
                        intent.putExtra(DetailActivity.EXTRA_TYPE, DetailActivity.TYPES[1])
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}