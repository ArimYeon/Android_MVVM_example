package com.example.mvvm_test.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_test.GithubRepository
import com.example.mvvm_test.retrofit.GithubRepositoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val githubRepository: GithubRepository) : ViewModel() {

    private val _githubRepositories: MutableLiveData<List<GithubRepositoryModel>> = MutableLiveData()
    val githubRepositories = _githubRepositories

    fun requestGithubRepositories(query: String){
        Log.d("inputText", query)
        CoroutineScope(Dispatchers.IO).launch {
            githubRepository.getRepositories(query)?.let { response ->
                if (response.isSuccessful){
                    response.body()?.let {
                        _githubRepositories.postValue(it.items)
                    }
                }
            }
        }
    }
}