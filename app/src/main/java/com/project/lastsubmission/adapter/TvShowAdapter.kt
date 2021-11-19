package com.project.lastsubmission.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.lastsubmission.data.source.local.entitiy.TvShowEntity
import com.project.lastsubmission.databinding.ItemMovieOrTvshowBinding
import com.project.lastsubmission.utils.BASE_URL_API_IMAGE
import com.project.lastsubmission.utils.POSTER_SIZE_W185
import com.project.lastsubmission.utils.loadImageTvShow

class TvShowAdapter : PagedListAdapter<TvShowEntity, TvShowAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var listener: ((TvShowEntity) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemMovieOrTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvshow: TvShowEntity) {
            binding.apply {
                tvshow.poster?.let {
                    poster.loadImageTvShow("$BASE_URL_API_IMAGE$POSTER_SIZE_W185$it")
                }
                tvTitle.text = tvshow.title
                tvDesc.text = tvshow.desc

                listener?.let { itemView.setOnClickListener { it(tvshow) } }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tvshow = getItem(position)
        if (tvshow != null) holder.bind(tvshow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ItemMovieOrTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            return ViewHolder(it)
        }
    }

    fun onClick(listener: ((TvShowEntity) -> Unit)?) {
        this.listener = listener
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean =
                oldItem.tvShowId == newItem.tvShowId

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean =
                oldItem == newItem

        }
    }
}