package com.example.moviedemokotlin.ui

import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviedemokotlin.R
import com.example.moviedemokotlin.data.model.Movie

/**
 * Author: created by MarkYoung on 22/01/2019 11:44
 */
class MoviesAdapter(private var movies: MutableList<Movie>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val TYPE_ITEM = 1
    private val TYPE_FOOT = 2

    companion object {
        @JvmField
        val PULL_UP_TO_LOAD = 0
        val LOADING_MORE = 1
        val NO_MORE = 2
    }

    private var loadState = PULL_UP_TO_LOAD

    fun resetData(){
        movies.clear()
    }

    fun addMovies(newMovies: List<Movie>){
            movies.addAll(newMovies)
            notifyItemRangeChanged(movies.size - newMovies.size, movies.size)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            TYPE_ITEM -> MovieViewHolder(DataBindingUtil
                    .inflate(LayoutInflater.from(viewGroup.context), R.layout.movie_item,viewGroup,false))
            else -> FootViewHolder(LayoutInflater
                    .from(viewGroup.context).inflate(R.layout.foot_item,viewGroup,false))
        }
    }

    override fun getItemCount(): Int {
        return if (movies.size == 0) 0 else movies.size + 1
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, p1: Int) {

        when(viewHolder){
            is MovieViewHolder -> viewHolder.movieItemBinding.movie = movies[p1]
            is FootViewHolder -> {
                if (p1 == 0) {
                    viewHolder.progressBar.visibility = View.GONE
                    viewHolder.tvLine1.visibility = View.GONE
                    viewHolder.tvLine2.visibility = View.GONE
                    viewHolder.tvState.text = ""
                }

                when(loadState){
                    PULL_UP_TO_LOAD -> {
                        viewHolder.progressBar.visibility = View.GONE
                        viewHolder.tvLine1.visibility = View.GONE
                        viewHolder.tvLine2.visibility = View.GONE
                        viewHolder.tvState.setText(R.string.pull_up_to_load_more)
                    }
                    LOADING_MORE -> {
                        viewHolder.progressBar.visibility = View.VISIBLE
                        viewHolder.tvLine1.visibility = View.GONE
                        viewHolder.tvLine2.visibility = View.GONE
                        viewHolder.tvState.setText(R.string.loading)
                    }
                    NO_MORE -> {
                        viewHolder.progressBar.visibility = View.GONE
                        viewHolder.tvLine1.visibility = View.VISIBLE
                        viewHolder.tvLine2.visibility = View.VISIBLE
                        viewHolder.tvState.setText(R.string.no_more_data)
                    }
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager: RecyclerView.LayoutManager? = recyclerView.layoutManager;
        if(layoutManager is GridLayoutManager)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
                override fun getSpanSize(p0: Int): Int {
                    return if (getItemViewType(p0) == TYPE_FOOT) layoutManager.spanCount else 1
                }
            }

    }

    override fun getItemViewType(position: Int): Int {

        return when(position){
            itemCount-1 -> TYPE_FOOT
            else -> TYPE_ITEM
        }
    }

    fun changeLoadState(loadState: Int) {
        this.loadState = loadState
        notifyItemRangeChanged(movies.size, 1)
    }
}