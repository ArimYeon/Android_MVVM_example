package com.example.mvvm_test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_test.databinding.ItemGithubRepositoryBinding
import com.example.mvvm_test.retrofit.GithubRepositoryModel
import com.example.mvvm_test.viewmodel.MainViewModel

class GithubRepositoryAdapter(viewModel: MainViewModel, repositories: List<GithubRepositoryModel>)
    : RecyclerView.Adapter<GithubRepositoryItemHolder>(){

    interface OnGithubRepositoryClickListener{
        fun onItemClick(position: Int)
    }
    var listener: OnGithubRepositoryClickListener? = null

    private val mainViewModel = viewModel
    private var mDataList = repositories

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepositoryItemHolder {
        val binding: ItemGithubRepositoryBinding = ItemGithubRepositoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return GithubRepositoryItemHolder(binding, listener)
    }

    override fun getItemCount(): Int{
        return mDataList.size
    }

    override fun onBindViewHolder(holder: GithubRepositoryItemHolder, position: Int) {
        holder.bind(mDataList[position], mainViewModel)
    }

    fun getItem(position: Int) = mDataList[position]

    fun update(repos: List<GithubRepositoryModel>){
        mDataList = repos
        notifyDataSetChanged()
    }
}