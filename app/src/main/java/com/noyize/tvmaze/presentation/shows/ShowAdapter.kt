package com.noyize.tvmaze.presentation.shows

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.noyize.tvmaze.R
import com.noyize.tvmaze.databinding.ItemShowBinding
import com.noyize.tvmaze.module.model.Show

class ShowAdapter(
    private val listener: OnClick
) : ListAdapter<Show, RecyclerView.ViewHolder>(DiffCallback) {

    interface OnClick {
        fun onShowClick(show: Show)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ShowViewHolder).bind(getItem(position))
    }

    private inner class ShowViewHolder(private val binding: ItemShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(show: Show) {
            binding.apply {
                val dotSeparator = itemView.context.getString(R.string.dot_separator)
                showImage.load(show.imageUrl)
                name.text = show.name
                summary.text = show.summary
                detail.text =
                    "${show.rating}  $dotSeparator  ${show.language}  $dotSeparator  ${show.status}"
                root.setOnClickListener {
                    listener.onShowClick(show)
                }
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Show>() {
        override fun areItemsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Show, newItem: Show): Boolean {
            return oldItem == newItem
        }
    }
}