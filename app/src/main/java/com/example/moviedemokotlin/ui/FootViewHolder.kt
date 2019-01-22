package com.example.moviedemokotlin.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.moviedemokotlin.R

/**
 * Author: created by MarkYoung on 22/01/2019 14:58
 */
class FootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
    val tvState: TextView = itemView.findViewById(R.id.foot_view_item_tv)
    val tvLine1: TextView = itemView.findViewById(R.id.tv_line1)
    val tvLine2: TextView = itemView.findViewById(R.id.tv_line2)
}