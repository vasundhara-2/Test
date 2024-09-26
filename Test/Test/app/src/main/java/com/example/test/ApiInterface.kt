package com.example.test

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/posts")
    suspend fun getUserData() : Response<List<User>>
}