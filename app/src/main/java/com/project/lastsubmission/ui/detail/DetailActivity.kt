package com.project.lastsubmission.ui.detail

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.project.lastsubmission.R
import com.project.lastsubmission.data.source.local.entitiy.MovieEntitiy
import com.project.lastsubmission.data.source.local.entitiy.TvShowEntity
import com.project.lastsubmission.databinding.ActivityDetailBinding
import com.project.lastsubmission.utils.*
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        intent.extras?.let {
            val type = it.getInt(EXTRA_TYPE)

            if (type == TYPES[0]){
                getDetailMovie()
            }else{
                getDetailTvShow()
            }
        }


    }

    private fun getDetailMovie() {
        binding.apply {
            intent.extras?.let {
                val id = it.getInt(EXTRA_ID)

                viewModel.getDetailMovie(id).observe(this@DetailActivity){ data ->
                    setUpToolbar(data.title)
                    imgDetailHightlight.loadImageMovie("$BASE_URL_API_IMAGE$POSTER_SIZE_W780${data.poster}")
                    imgDetailPoster.loadImageMovie("$BASE_URL_API_IMAGE$POSTER_SIZE_W185${data.imgPreview}")
                    tvTitle.text = data.title
                    tvDesc.text = data.desc

                    statusFavorite(data.isFavorite)

                    fabFavorite.setOnClickListener {
                        setFavorite(data)
                    }
                }
            }
        }
    }

    private fun statusFavorite(state: Boolean) {
        binding.apply {
            if (state) fabFavorite.setImageResource(R.drawable.ic_favorite_true)
            else fabFavorite.setImageResource(R.drawable.ic_favorite_false)
        }
    }

    private fun setFavorite(data: Any?) {
        when(data){
            is MovieEntitiy -> {
                if (data.isFavorite){
                    binding.root.showSnackBar("${data.title} Removed From Favorite")
                }else{
                    binding.root.showSnackBar("${data.title} Added to Favorite")
                }
                viewModel.setFavoriteMovie(data)
            }
            is TvShowEntity -> {
                if (data.isFavorite){
                    binding.root.showSnackBar("${data.title} Removed From Favorite")
                }else{
                    binding.root.showSnackBar("${data.title} Added to Favorite")
                }
                viewModel.setFavoriteTvShow(data)
            }
        }
    }

    private fun getDetailTvShow() {
        binding.apply {
            intent.extras?.let {
                val id = it.getInt(EXTRA_ID)

                viewModel.getDetailTvShow(id).observe(this@DetailActivity){ data ->
                    setUpToolbar(data.title)
                    imgDetailHightlight.loadImageTvShow("$BASE_URL_API_IMAGE$POSTER_SIZE_W780${data.poster}")
                    imgDetailPoster.loadImageTvShow("$BASE_URL_API_IMAGE$POSTER_SIZE_W185${data.imgPreview}")
                    tvTitle.text = data.title
                    tvDesc.text = data.desc

                    statusFavorite(data.isFavorite)

                    fabFavorite.setOnClickListener {
                        setFavorite(data)
                    }
                }
            }
        }
    }

    private fun setUpToolbar(title: String?) {
        binding.apply {
            detailToolbar.title = title
            setSupportActionBar(detailToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object{
        const val EXTRA_ID = "extra_data"
        const val EXTRA_TYPE = "extra_type"
        val TYPES = intArrayOf(R.string.movie, R.string.tvshow)
    }
}