package com.example.mvvm_test

import com.example.mvvm_test.retrofit.GithubService

class GithubRepository {
    private val githubClient = GithubService.client
    suspend fun getRepositories(query: String) = githubClient?.getRepositories(query)
}