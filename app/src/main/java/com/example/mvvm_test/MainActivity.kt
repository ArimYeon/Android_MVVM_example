package com.example.mvvm_test

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_test.databinding.ActivityMainBinding
import com.example.mvvm_test.retrofit.GithubRepositoryModel
import com.example.mvvm_test.viewmodel.MainViewModel
import com.example.mvvm_test.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerAdapter: GithubRepositoryAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initButton()
        initViewModel()
        initAdapter()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initButton(){
        binding.searchButton.setOnClickListener { onSearchClick() }
    }

    private fun initViewModel(){
        viewModelFactory = MainViewModelFactory(GithubRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.githubRepositories.observe(this, Observer {
            updateRepositories(it)
        })
    }

    private fun initAdapter(){
        val repo: MutableList<GithubRepositoryModel> = arrayListOf()
        recyclerAdapter = GithubRepositoryAdapter(viewModel, repo).apply {
            listener = object : GithubRepositoryAdapter.OnGithubRepositoryClickListener {
                override fun onItemClick(position: Int) {
                    recyclerAdapter.getItem(position).run {
                        openGithub(htmlUrl)
                    }
                }
            }
        }
        recyclerView = binding.githubReposView
        recyclerView.run{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerAdapter
        }
    }

    private fun updateRepositories(repos: List<GithubRepositoryModel>){
        recyclerAdapter.update(repos)
    }

    private fun openGithub(url: String) {
        try {
            val uri = Uri.parse(url)
            Intent(Intent.ACTION_VIEW, uri).run {
                startActivity(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun onSearchClick(){
        val q = binding.inputView.text.toString()
        binding.inputView.run{
            viewModel.requestGithubRepositories(q)
            text.clear()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        currentFocus?.run {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(windowToken, 0)
        }
    }
}