package com.example.mvvm_test.retrofit

object GithubService {
    private const val GITHUB_URL = "https://api.github.com"
    val client = BaseService().getClient(GITHUB_URL)?.create(GithubApi::class.java)
}