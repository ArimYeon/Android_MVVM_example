package com.example.mvvm_test

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm_test.databinding.ItemGithubRepositoryBinding
import com.example.mvvm_test.retrofit.GithubRepositoryModel
import com.example.mvvm_test.viewmodel.MainViewModel

class GithubRepositoryItemHolder(
    binding: ItemGithubRepositoryBinding,
    listener: GithubRepositoryAdapter.OnGithubRepositoryClickListener?)
    : RecyclerView.ViewHolder(binding.root){
    private val _binding = binding

    init{
        _binding.root.setOnClickListener { listener?.onItemClick(adapterPosition) }
    }
    fun bind(item : GithubRepositoryModel, viewModel: MainViewModel){
        Glide.with(_binding.avatarView.context).load(item.owner.avatarUrl).into(_binding.avatarView)
        _binding.viewModel = viewModel
        _binding.item = item
        _binding.executePendingBindings()
    }
}