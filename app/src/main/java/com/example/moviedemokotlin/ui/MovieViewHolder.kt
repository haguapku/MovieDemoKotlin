package com.example.moviedemokotlin.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.moviedemokotlin.api.OnItemClick
import com.example.moviedemokotlin.databinding.MovieItemBinding

/**
 * Author: created by MarkYoung on 22/01/2019 13:41
 */
class MovieViewHolder(val movieItemBinding: MovieItemBinding, private val onItemClick: OnItemClick)
    : RecyclerView.ViewHolder(movieItemBinding.root), View.OnClickListener {

    init {
        movieItemBinding.root.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            onItemClick.onItemClick(view, adapterPosition)
        }
    }

}