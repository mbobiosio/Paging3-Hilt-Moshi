package com.example.apitesting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.apitesting.databinding.LayoutFooterLoadStateBinding


// this is footer of the recycleView it will show the progress bar while scrolling
class LoadingStateAdapter() : LoadStateAdapter<LoadingStateAdapter.ViewHolder>()

{
    inner  class ViewHolder(private val binding: LayoutFooterLoadStateBinding) : RecyclerView.ViewHolder(binding.root)

    {
        fun bind(loadState: LoadState)
        {
            binding.apply()
            {
                progressBar.isVisible = loadState is LoadState.Loading
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder
    {
        val view = LayoutFooterLoadStateBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState)
    {
        holder.bind(loadState)
    }


}