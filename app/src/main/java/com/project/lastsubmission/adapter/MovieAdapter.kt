package com.project.lastsubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.lastsubmission.data.source.local.entitiy.MovieEntitiy
import com.project.lastsubmission.databinding.ItemMovieOrTvshowBinding
import com.project.lastsubmission.utils.BASE_URL_API_IMAGE
import com.project.lastsubmission.utils.POSTER_SIZE_W185
import com.project.lastsubmission.utils.loadImageMovie

class MovieAdapter : PagedListAdapter<MovieEntitiy, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var listener: ((MovieEntitiy) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemMovieOrTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntitiy) {
            binding.apply {
                movie.poster?.let {
                    poster.loadImageMovie("$BASE_URL_API_IMAGE$POSTER_SIZE_W185$it")
                }
                tvTitle.text = movie.title
                tvDesc.text = movie.desc

                listener?.let { itemView.setOnClickListener { it(movie) } }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) holder.bind(movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ItemMovieOrTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            return ViewHolder(it)
        }
    }

    fun onClick(listener: ((MovieEntitiy) -> Unit)?) {
        this.listener = listener
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntitiy>() {
            override fun areItemsTheSame(oldItem: MovieEntitiy, newItem: MovieEntitiy): Boolean =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: MovieEntitiy, newItem: MovieEntitiy): Boolean =
                oldItem == newItem

        }
    }
}